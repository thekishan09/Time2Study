package com.google.firebase.firestore.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.core.Query;
import com.google.firebase.firestore.local.SQLitePersistence;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.MutationBatch;
import com.google.firebase.firestore.proto.WriteBatch;
import com.google.firebase.firestore.remote.WriteStream;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Consumer;
import com.google.firebase.firestore.util.Preconditions;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class SQLiteMutationQueue implements MutationQueue {
    private static final int BLOB_MAX_INLINE_LENGTH = 1000000;

    /* renamed from: db */
    private final SQLitePersistence f420db;
    /* access modifiers changed from: private */
    public ByteString lastStreamToken;
    /* access modifiers changed from: private */
    public int nextBatchId;
    private final LocalSerializer serializer;
    private final String uid;

    SQLiteMutationQueue(SQLitePersistence persistence, LocalSerializer serializer2, User user) {
        this.f420db = persistence;
        this.serializer = serializer2;
        this.uid = user.isAuthenticated() ? user.getUid() : "";
        this.lastStreamToken = WriteStream.EMPTY_STREAM_TOKEN;
    }

    public void start() {
        loadNextBatchIdAcrossAllUsers();
        if (this.f420db.query("SELECT last_stream_token FROM mutation_queues WHERE uid = ?").binding(this.uid).first(SQLiteMutationQueue$$Lambda$1.lambdaFactory$(this)) == 0) {
            writeMutationQueueMetadata();
        }
    }

    private void loadNextBatchIdAcrossAllUsers() {
        List<String> uids = new ArrayList<>();
        this.f420db.query("SELECT uid FROM mutation_queues").forEach(SQLiteMutationQueue$$Lambda$2.lambdaFactory$(uids));
        this.nextBatchId = 0;
        for (String uid2 : uids) {
            this.f420db.query("SELECT MAX(batch_id) FROM mutations WHERE uid = ?").binding(uid2).forEach(SQLiteMutationQueue$$Lambda$3.lambdaFactory$(this));
        }
        this.nextBatchId++;
    }

    public boolean isEmpty() {
        return this.f420db.query("SELECT batch_id FROM mutations WHERE uid = ? LIMIT 1").binding(this.uid).isEmpty();
    }

    public void acknowledgeBatch(MutationBatch batch, ByteString streamToken) {
        this.lastStreamToken = (ByteString) Preconditions.checkNotNull(streamToken);
        writeMutationQueueMetadata();
    }

    public ByteString getLastStreamToken() {
        return this.lastStreamToken;
    }

    public void setLastStreamToken(ByteString streamToken) {
        this.lastStreamToken = (ByteString) Preconditions.checkNotNull(streamToken);
        writeMutationQueueMetadata();
    }

    private void writeMutationQueueMetadata() {
        this.f420db.execute("INSERT OR REPLACE INTO mutation_queues (uid, last_acknowledged_batch_id, last_stream_token) VALUES (?, ?, ?)", this.uid, -1, this.lastStreamToken.toByteArray());
    }

    public MutationBatch addMutationBatch(Timestamp localWriteTime, List<Mutation> baseMutations, List<Mutation> mutations) {
        int batchId = this.nextBatchId;
        this.nextBatchId++;
        MutationBatch batch = new MutationBatch(batchId, localWriteTime, baseMutations, mutations);
        int i = 3;
        this.f420db.execute("INSERT INTO mutations (uid, batch_id, mutations) VALUES (?, ?, ?)", this.uid, Integer.valueOf(batchId), this.serializer.encodeMutationBatch(batch).toByteArray());
        Set<DocumentKey> inserted = new HashSet<>();
        SQLiteStatement indexInserter = this.f420db.prepare("INSERT INTO document_mutations (uid, path, batch_id) VALUES (?, ?, ?)");
        for (Mutation mutation : mutations) {
            DocumentKey key = mutation.getKey();
            if (inserted.add(key)) {
                String path = EncodedPath.encode(key.getPath());
                SQLitePersistence sQLitePersistence = this.f420db;
                Object[] objArr = new Object[i];
                objArr[0] = this.uid;
                objArr[1] = path;
                objArr[2] = Integer.valueOf(batchId);
                sQLitePersistence.execute(indexInserter, objArr);
                this.f420db.getIndexManager().addToCollectionParentIndex((ResourcePath) key.getPath().popLast());
                i = 3;
            }
        }
        return batch;
    }

    public MutationBatch lookupMutationBatch(int batchId) {
        return (MutationBatch) this.f420db.query("SELECT SUBSTR(mutations, 1, ?) FROM mutations WHERE uid = ? AND batch_id = ?").binding(Integer.valueOf(BLOB_MAX_INLINE_LENGTH), this.uid, Integer.valueOf(batchId)).firstValue(SQLiteMutationQueue$$Lambda$4.lambdaFactory$(this, batchId));
    }

    public MutationBatch getNextMutationBatchAfterBatchId(int batchId) {
        return (MutationBatch) this.f420db.query("SELECT batch_id, SUBSTR(mutations, 1, ?) FROM mutations WHERE uid = ? AND batch_id >= ? ORDER BY batch_id ASC LIMIT 1").binding(Integer.valueOf(BLOB_MAX_INLINE_LENGTH), this.uid, Integer.valueOf(batchId + 1)).firstValue(SQLiteMutationQueue$$Lambda$5.lambdaFactory$(this));
    }

    public int getHighestUnacknowledgedBatchId() {
        return ((Integer) this.f420db.query("SELECT IFNULL(MAX(batch_id), ?) FROM mutations WHERE uid = ?").binding(-1, this.uid).firstValue(SQLiteMutationQueue$$Lambda$6.lambdaFactory$())).intValue();
    }

    public List<MutationBatch> getAllMutationBatches() {
        List<MutationBatch> result = new ArrayList<>();
        this.f420db.query("SELECT batch_id, SUBSTR(mutations, 1, ?) FROM mutations WHERE uid = ? ORDER BY batch_id ASC").binding(Integer.valueOf(BLOB_MAX_INLINE_LENGTH), this.uid).forEach(SQLiteMutationQueue$$Lambda$7.lambdaFactory$(this, result));
        return result;
    }

    public List<MutationBatch> getAllMutationBatchesAffectingDocumentKey(DocumentKey documentKey) {
        String path = EncodedPath.encode(documentKey.getPath());
        List<MutationBatch> result = new ArrayList<>();
        this.f420db.query("SELECT m.batch_id, SUBSTR(m.mutations, 1, ?) FROM document_mutations dm, mutations m WHERE dm.uid = ? AND dm.path = ? AND dm.uid = m.uid AND dm.batch_id = m.batch_id ORDER BY dm.batch_id").binding(Integer.valueOf(BLOB_MAX_INLINE_LENGTH), this.uid, path).forEach(SQLiteMutationQueue$$Lambda$8.lambdaFactory$(this, result));
        return result;
    }

    public List<MutationBatch> getAllMutationBatchesAffectingDocumentKeys(Iterable<DocumentKey> documentKeys) {
        List<Object> args = new ArrayList<>();
        for (DocumentKey key : documentKeys) {
            args.add(EncodedPath.encode(key.getPath()));
        }
        SQLitePersistence.LongQuery longQuery = new SQLitePersistence.LongQuery(this.f420db, "SELECT DISTINCT dm.batch_id, SUBSTR(m.mutations, 1, ?) FROM document_mutations dm, mutations m WHERE dm.uid = ? AND dm.path IN (", Arrays.asList(new Object[]{Integer.valueOf(BLOB_MAX_INLINE_LENGTH), this.uid}), args, ") AND dm.uid = m.uid AND dm.batch_id = m.batch_id ORDER BY dm.batch_id");
        List<MutationBatch> result = new ArrayList<>();
        Set<Integer> uniqueBatchIds = new HashSet<>();
        while (longQuery.hasMoreSubqueries()) {
            longQuery.performNextSubquery().forEach(SQLiteMutationQueue$$Lambda$9.lambdaFactory$(this, uniqueBatchIds, result));
        }
        if (longQuery.getSubqueriesPerformed() > 1) {
            Collections.sort(result, SQLiteMutationQueue$$Lambda$10.lambdaFactory$());
        }
        return result;
    }

    static /* synthetic */ void lambda$getAllMutationBatchesAffectingDocumentKeys$8(SQLiteMutationQueue sQLiteMutationQueue, Set uniqueBatchIds, List result, Cursor row) {
        int batchId = row.getInt(0);
        if (!uniqueBatchIds.contains(Integer.valueOf(batchId))) {
            uniqueBatchIds.add(Integer.valueOf(batchId));
            result.add(sQLiteMutationQueue.decodeInlineMutationBatch(batchId, row.getBlob(1)));
        }
    }

    public List<MutationBatch> getAllMutationBatchesAffectingQuery(Query query) {
        Assert.hardAssert(!query.isCollectionGroupQuery(), "CollectionGroup queries should be handled in LocalDocumentsView", new Object[0]);
        ResourcePath prefix = query.getPath();
        String prefixPath = EncodedPath.encode(prefix);
        String prefixSuccessorPath = EncodedPath.prefixSuccessor(prefixPath);
        List<MutationBatch> result = new ArrayList<>();
        this.f420db.query("SELECT dm.batch_id, dm.path, SUBSTR(m.mutations, 1, ?) FROM document_mutations dm, mutations m WHERE dm.uid = ? AND dm.path >= ? AND dm.path < ? AND dm.uid = m.uid AND dm.batch_id = m.batch_id ORDER BY dm.batch_id").binding(Integer.valueOf(BLOB_MAX_INLINE_LENGTH), this.uid, prefixPath, prefixSuccessorPath).forEach(SQLiteMutationQueue$$Lambda$11.lambdaFactory$(this, result, prefix.length() + 1));
        return result;
    }

    static /* synthetic */ void lambda$getAllMutationBatchesAffectingQuery$10(SQLiteMutationQueue sQLiteMutationQueue, List result, int immediateChildrenPathLength, Cursor row) {
        int batchId = row.getInt(0);
        int size = result.size();
        if ((size <= 0 || batchId != ((MutationBatch) result.get(size - 1)).getBatchId()) && EncodedPath.decodeResourcePath(row.getString(1)).length() == immediateChildrenPathLength) {
            result.add(sQLiteMutationQueue.decodeInlineMutationBatch(batchId, row.getBlob(2)));
        }
    }

    public void removeMutationBatch(MutationBatch batch) {
        SQLiteStatement mutationDeleter = this.f420db.prepare("DELETE FROM mutations WHERE uid = ? AND batch_id = ?");
        SQLiteStatement indexDeleter = this.f420db.prepare("DELETE FROM document_mutations WHERE uid = ? AND path = ? AND batch_id = ?");
        int batchId = batch.getBatchId();
        Assert.hardAssert(this.f420db.execute(mutationDeleter, this.uid, Integer.valueOf(batchId)) != 0, "Mutation batch (%s, %d) did not exist", this.uid, Integer.valueOf(batch.getBatchId()));
        for (Mutation mutation : batch.getMutations()) {
            DocumentKey key = mutation.getKey();
            this.f420db.execute(indexDeleter, this.uid, EncodedPath.encode(key.getPath()), Integer.valueOf(batchId));
            this.f420db.getReferenceDelegate().removeMutationReference(key);
        }
    }

    public void performConsistencyCheck() {
        if (isEmpty()) {
            List<ResourcePath> danglingMutationReferences = new ArrayList<>();
            this.f420db.query("SELECT path FROM document_mutations WHERE uid = ?").binding(this.uid).forEach(SQLiteMutationQueue$$Lambda$12.lambdaFactory$(danglingMutationReferences));
            Assert.hardAssert(danglingMutationReferences.isEmpty(), "Document leak -- detected dangling mutation references when queue is empty. Dangling keys: %s", danglingMutationReferences);
        }
    }

    /* access modifiers changed from: private */
    public MutationBatch decodeInlineMutationBatch(int batchId, byte[] bytes) {
        try {
            if (bytes.length < BLOB_MAX_INLINE_LENGTH) {
                return this.serializer.decodeMutationBatch(WriteBatch.parseFrom(bytes));
            }
            BlobAccumulator accumulator = new BlobAccumulator(bytes);
            while (accumulator.more) {
                this.f420db.query("SELECT SUBSTR(mutations, ?, ?) FROM mutations WHERE uid = ? AND batch_id = ?").binding(Integer.valueOf((accumulator.numChunks() * BLOB_MAX_INLINE_LENGTH) + 1), Integer.valueOf(BLOB_MAX_INLINE_LENGTH), this.uid, Integer.valueOf(batchId)).first(accumulator);
            }
            return this.serializer.decodeMutationBatch(WriteBatch.parseFrom(accumulator.result()));
        } catch (InvalidProtocolBufferException e) {
            throw Assert.fail("MutationBatch failed to parse: %s", e);
        }
    }

    private static class BlobAccumulator implements Consumer<Cursor> {
        private final ArrayList<ByteString> chunks = new ArrayList<>();
        /* access modifiers changed from: private */
        public boolean more = true;

        BlobAccumulator(byte[] firstChunk) {
            addChunk(firstChunk);
        }

        /* access modifiers changed from: package-private */
        public int numChunks() {
            return this.chunks.size();
        }

        /* access modifiers changed from: package-private */
        public ByteString result() {
            return ByteString.copyFrom((Iterable<ByteString>) this.chunks);
        }

        public void accept(Cursor row) {
            byte[] bytes = row.getBlob(0);
            addChunk(bytes);
            if (bytes.length < SQLiteMutationQueue.BLOB_MAX_INLINE_LENGTH) {
                this.more = false;
            }
        }

        private void addChunk(byte[] bytes) {
            this.chunks.add(ByteString.copyFrom(bytes));
        }
    }
}
