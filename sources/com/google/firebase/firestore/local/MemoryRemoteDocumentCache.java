package com.google.firebase.firestore.local;

import android.util.Pair;
import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentCollections;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.util.Assert;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

final class MemoryRemoteDocumentCache implements RemoteDocumentCache {
    /* access modifiers changed from: private */
    public ImmutableSortedMap<DocumentKey, Pair<MaybeDocument, SnapshotVersion>> docs = ImmutableSortedMap.Builder.emptyMap(DocumentKey.comparator());
    private final MemoryPersistence persistence;

    MemoryRemoteDocumentCache(MemoryPersistence persistence2) {
        this.persistence = persistence2;
    }

    public void add(MaybeDocument document, SnapshotVersion readTime) {
        Assert.hardAssert(!readTime.equals(SnapshotVersion.NONE), "Cannot add document to the RemoteDocumentCache with a read time of zero", new Object[0]);
        this.docs = this.docs.insert(document.getKey(), new Pair(document, readTime));
        this.persistence.getIndexManager().addToCollectionParentIndex((ResourcePath) document.getKey().getPath().popLast());
    }

    public void remove(DocumentKey key) {
        this.docs = this.docs.remove(key);
    }

    public MaybeDocument get(DocumentKey key) {
        Pair<MaybeDocument, SnapshotVersion> entry = this.docs.get(key);
        if (entry != null) {
            return (MaybeDocument) entry.first;
        }
        return null;
    }

    public Map<DocumentKey, MaybeDocument> getAll(Iterable<DocumentKey> keys) {
        Map<DocumentKey, MaybeDocument> result = new HashMap<>();
        for (DocumentKey key : keys) {
            result.put(key, get(key));
        }
        return result;
    }

    public ImmutableSortedMap<DocumentKey, Document> getAllDocumentsMatchingQuery(Query query, SnapshotVersion sinceReadTime) {
        Assert.hardAssert(!query.isCollectionGroupQuery(), "CollectionGroup queries should be handled in LocalDocumentsView", new Object[0]);
        ImmutableSortedMap<DocumentKey, Document> result = DocumentCollections.emptyDocumentMap();
        ResourcePath queryPath = query.getPath();
        Iterator<Map.Entry<DocumentKey, Pair<MaybeDocument, SnapshotVersion>>> iterator = this.docs.iteratorFrom(DocumentKey.fromPath((ResourcePath) queryPath.append("")));
        while (iterator.hasNext()) {
            Map.Entry<DocumentKey, Pair<MaybeDocument, SnapshotVersion>> entry = iterator.next();
            if (!queryPath.isPrefixOf(entry.getKey().getPath())) {
                break;
            }
            MaybeDocument maybeDoc = (MaybeDocument) entry.getValue().first;
            if ((maybeDoc instanceof Document) && ((SnapshotVersion) entry.getValue().second).compareTo(sinceReadTime) > 0) {
                Document doc = (Document) maybeDoc;
                if (query.matches(doc)) {
                    result = result.insert(doc.getKey(), doc);
                }
            }
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public Iterable<MaybeDocument> getDocuments() {
        return new DocumentIterable();
    }

    /* access modifiers changed from: package-private */
    public long getByteSize(LocalSerializer serializer) {
        long count = 0;
        Iterator<MaybeDocument> it = new DocumentIterable().iterator();
        while (it.hasNext()) {
            count += (long) serializer.encodeMaybeDocument(it.next()).getSerializedSize();
        }
        return count;
    }

    private class DocumentIterable implements Iterable<MaybeDocument> {
        private DocumentIterable() {
        }

        public Iterator<MaybeDocument> iterator() {
            final Iterator<Map.Entry<DocumentKey, Pair<MaybeDocument, SnapshotVersion>>> iterator = MemoryRemoteDocumentCache.this.docs.iterator();
            return new Iterator<MaybeDocument>() {
                public boolean hasNext() {
                    return iterator.hasNext();
                }

                public MaybeDocument next() {
                    return (MaybeDocument) ((Pair) ((Map.Entry) iterator.next()).getValue()).first;
                }
            };
        }
    }
}
