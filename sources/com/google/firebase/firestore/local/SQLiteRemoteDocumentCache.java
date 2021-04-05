package com.google.firebase.firestore.local;

import android.database.Cursor;
import com.google.firebase.Timestamp;
import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.local.SQLitePersistence;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentCollections;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.BackgroundQueue;
import com.google.firebase.firestore.util.Executors;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class SQLiteRemoteDocumentCache implements RemoteDocumentCache {

    /* renamed from: db */
    private final SQLitePersistence f424db;
    private final LocalSerializer serializer;

    SQLiteRemoteDocumentCache(SQLitePersistence persistence, LocalSerializer serializer2) {
        this.f424db = persistence;
        this.serializer = serializer2;
    }

    public void add(MaybeDocument maybeDocument, SnapshotVersion readTime) {
        Assert.hardAssert(!readTime.equals(SnapshotVersion.NONE), "Cannot add document to the RemoteDocumentCache with a read time of zero", new Object[0]);
        String path = pathForKey(maybeDocument.getKey());
        Timestamp timestamp = readTime.getTimestamp();
        MessageLite message = this.serializer.encodeMaybeDocument(maybeDocument);
        this.f424db.execute("INSERT OR REPLACE INTO remote_documents (path, read_time_seconds, read_time_nanos, contents) VALUES (?, ?, ?, ?)", path, Long.valueOf(timestamp.getSeconds()), Integer.valueOf(timestamp.getNanoseconds()), message.toByteArray());
        this.f424db.getIndexManager().addToCollectionParentIndex((ResourcePath) maybeDocument.getKey().getPath().popLast());
    }

    public void remove(DocumentKey documentKey) {
        String path = pathForKey(documentKey);
        this.f424db.execute("DELETE FROM remote_documents WHERE path = ?", path);
    }

    public MaybeDocument get(DocumentKey documentKey) {
        String path = pathForKey(documentKey);
        return (MaybeDocument) this.f424db.query("SELECT contents FROM remote_documents WHERE path = ?").binding(path).firstValue(SQLiteRemoteDocumentCache$$Lambda$1.lambdaFactory$(this));
    }

    public Map<DocumentKey, MaybeDocument> getAll(Iterable<DocumentKey> documentKeys) {
        List<Object> args = new ArrayList<>();
        for (DocumentKey key : documentKeys) {
            args.add(EncodedPath.encode(key.getPath()));
        }
        Map<DocumentKey, MaybeDocument> results = new HashMap<>();
        for (DocumentKey key2 : documentKeys) {
            results.put(key2, (Object) null);
        }
        SQLitePersistence.LongQuery longQuery = new SQLitePersistence.LongQuery(this.f424db, "SELECT contents FROM remote_documents WHERE path IN (", args, ") ORDER BY path");
        while (longQuery.hasMoreSubqueries()) {
            longQuery.performNextSubquery().forEach(SQLiteRemoteDocumentCache$$Lambda$2.lambdaFactory$(this, results));
        }
        return results;
    }

    static /* synthetic */ void lambda$getAll$1(SQLiteRemoteDocumentCache sQLiteRemoteDocumentCache, Map results, Cursor row) {
        MaybeDocument decoded = sQLiteRemoteDocumentCache.decodeMaybeDocument(row.getBlob(0));
        results.put(decoded.getKey(), decoded);
    }

    public ImmutableSortedMap<DocumentKey, Document> getAllDocumentsMatchingQuery(Query query, SnapshotVersion sinceReadTime) {
        SQLitePersistence.Query sqlQuery;
        Assert.hardAssert(!query.isCollectionGroupQuery(), "CollectionGroup queries should be handled in LocalDocumentsView", new Object[0]);
        ResourcePath prefix = query.getPath();
        int immediateChildrenPathLength = prefix.length() + 1;
        String prefixPath = EncodedPath.encode(prefix);
        String prefixSuccessorPath = EncodedPath.prefixSuccessor(prefixPath);
        Timestamp readTime = sinceReadTime.getTimestamp();
        BackgroundQueue backgroundQueue = new BackgroundQueue();
        ImmutableSortedMap<DocumentKey, Document>[] matchingDocuments = {DocumentCollections.emptyDocumentMap()};
        if (sinceReadTime.equals(SnapshotVersion.NONE)) {
            sqlQuery = this.f424db.query("SELECT path, contents FROM remote_documents WHERE path >= ? AND path < ?").binding(prefixPath, prefixSuccessorPath);
        } else {
            sqlQuery = this.f424db.query("SELECT path, contents FROM remote_documents WHERE path >= ? AND path < ?AND (read_time_seconds > ? OR (read_time_seconds = ? AND read_time_nanos > ?))").binding(prefixPath, prefixSuccessorPath, Long.valueOf(readTime.getSeconds()), Long.valueOf(readTime.getSeconds()), Integer.valueOf(readTime.getNanoseconds()));
        }
        sqlQuery.forEach(SQLiteRemoteDocumentCache$$Lambda$3.lambdaFactory$(this, immediateChildrenPathLength, backgroundQueue, query, matchingDocuments));
        try {
            backgroundQueue.drain();
        } catch (InterruptedException e) {
            Assert.fail("Interrupted while deserializing documents", e);
        }
        return matchingDocuments[0];
    }

    static /* synthetic */ void lambda$getAllDocumentsMatchingQuery$3(SQLiteRemoteDocumentCache sQLiteRemoteDocumentCache, int immediateChildrenPathLength, BackgroundQueue backgroundQueue, Query query, ImmutableSortedMap[] matchingDocuments, Cursor row) {
        if (EncodedPath.decodeResourcePath(row.getString(0)).length() == immediateChildrenPathLength) {
            (row.isLast() ? Executors.DIRECT_EXECUTOR : backgroundQueue).execute(SQLiteRemoteDocumentCache$$Lambda$4.lambdaFactory$(sQLiteRemoteDocumentCache, row.getBlob(1), query, matchingDocuments));
        }
    }

    static /* synthetic */ void lambda$getAllDocumentsMatchingQuery$2(SQLiteRemoteDocumentCache sQLiteRemoteDocumentCache, byte[] rawDocument, Query query, ImmutableSortedMap[] matchingDocuments) {
        MaybeDocument maybeDoc = sQLiteRemoteDocumentCache.decodeMaybeDocument(rawDocument);
        if ((maybeDoc instanceof Document) && query.matches((Document) maybeDoc)) {
            synchronized (sQLiteRemoteDocumentCache) {
                matchingDocuments[0] = matchingDocuments[0].insert(maybeDoc.getKey(), (Document) maybeDoc);
            }
        }
    }

    private String pathForKey(DocumentKey key) {
        return EncodedPath.encode(key.getPath());
    }

    /* access modifiers changed from: private */
    public MaybeDocument decodeMaybeDocument(byte[] bytes) {
        try {
            return this.serializer.decodeMaybeDocument(com.google.firebase.firestore.proto.MaybeDocument.parseFrom(bytes));
        } catch (InvalidProtocolBufferException e) {
            throw Assert.fail("MaybeDocument failed to parse: %s", e);
        }
    }
}
