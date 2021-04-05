package com.google.firebase.firestore.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.util.Log;
import com.google.firebase.firestore.local.MemoryIndexManager;
import com.google.firebase.firestore.local.SQLitePersistence;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.proto.Target;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Consumer;
import com.google.firebase.firestore.util.Preconditions;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.ArrayList;
import java.util.List;

class SQLiteSchema {
    static final int INDEXING_SUPPORT_VERSION = 12;
    private static final int SEQUENCE_NUMBER_BATCH_SIZE = 100;
    static final int VERSION = 11;

    /* renamed from: db */
    private final SQLiteDatabase f425db;
    private final LocalSerializer serializer;

    SQLiteSchema(SQLiteDatabase db, LocalSerializer serializer2) {
        this.f425db = db;
        this.serializer = serializer2;
    }

    /* access modifiers changed from: package-private */
    public void runMigrations() {
        runMigrations(0, 11);
    }

    /* access modifiers changed from: package-private */
    public void runMigrations(int fromVersion) {
        runMigrations(fromVersion, 11);
    }

    /* access modifiers changed from: package-private */
    public void runMigrations(int fromVersion, int toVersion) {
        if (fromVersion < 1 && toVersion >= 1) {
            createV1MutationQueue();
            createV1TargetCache();
            createV1RemoteDocumentCache();
        }
        if (fromVersion < 3 && toVersion >= 3 && fromVersion != 0) {
            dropV1TargetCache();
            createV1TargetCache();
        }
        if (fromVersion < 4 && toVersion >= 4) {
            ensureTargetGlobal();
            addTargetCount();
        }
        if (fromVersion < 5 && toVersion >= 5) {
            addSequenceNumber();
        }
        if (fromVersion < 6 && toVersion >= 6) {
            removeAcknowledgedMutations();
        }
        if (fromVersion < 7 && toVersion >= 7) {
            ensureSequenceNumbers();
        }
        if (fromVersion < 8 && toVersion >= 8) {
            createV8CollectionParentsIndex();
        }
        if (fromVersion < 9 && toVersion >= 9) {
            if (!hasReadTime()) {
                addReadTime();
            } else {
                dropLastLimboFreeSnapshotVersion();
            }
        }
        if (fromVersion == 9 && toVersion >= 10) {
            dropLastLimboFreeSnapshotVersion();
        }
        if (fromVersion < 11 && toVersion >= 11) {
            rewriteCanonicalIds();
        }
        if (fromVersion < 12 && toVersion >= 12) {
            Preconditions.checkState(Persistence.INDEXING_SUPPORT_ENABLED);
            createLocalDocumentsCollectionIndex();
        }
    }

    private void ifTablesDontExist(String[] tables, Runnable fn) {
        String msg;
        boolean tablesFound = false;
        String allTables = "[" + TextUtils.join(", ", tables) + "]";
        for (int i = 0; i < tables.length; i++) {
            String table = tables[i];
            boolean tableFound = tableExists(table);
            if (i == 0) {
                tablesFound = tableFound;
            } else if (tableFound != tablesFound) {
                String msg2 = "Expected all of " + allTables + " to either exist or not, but ";
                if (tablesFound) {
                    msg = msg2 + tables[0] + " exists and " + table + " does not";
                } else {
                    msg = msg2 + tables[0] + " does not exist and " + table + " does";
                }
                throw new IllegalStateException(msg);
            }
        }
        if (!tablesFound) {
            fn.run();
            return;
        }
        Log.d("SQLiteSchema", "Skipping migration because all of " + allTables + " already exist");
    }

    private void createV1MutationQueue() {
        ifTablesDontExist(new String[]{"mutation_queues", "mutations", "document_mutations"}, SQLiteSchema$$Lambda$1.lambdaFactory$(this));
    }

    static /* synthetic */ void lambda$createV1MutationQueue$0(SQLiteSchema sQLiteSchema) {
        sQLiteSchema.f425db.execSQL("CREATE TABLE mutation_queues (uid TEXT PRIMARY KEY, last_acknowledged_batch_id INTEGER, last_stream_token BLOB)");
        sQLiteSchema.f425db.execSQL("CREATE TABLE mutations (uid TEXT, batch_id INTEGER, mutations BLOB, PRIMARY KEY (uid, batch_id))");
        sQLiteSchema.f425db.execSQL("CREATE TABLE document_mutations (uid TEXT, path TEXT, batch_id INTEGER, PRIMARY KEY (uid, path, batch_id))");
    }

    private void removeAcknowledgedMutations() {
        new SQLitePersistence.Query(this.f425db, "SELECT uid, last_acknowledged_batch_id FROM mutation_queues").forEach(SQLiteSchema$$Lambda$2.lambdaFactory$(this));
    }

    static /* synthetic */ void lambda$removeAcknowledgedMutations$2(SQLiteSchema sQLiteSchema, Cursor mutationQueueEntry) {
        String uid = mutationQueueEntry.getString(0);
        long lastAcknowledgedBatchId = mutationQueueEntry.getLong(1);
        new SQLitePersistence.Query(sQLiteSchema.f425db, "SELECT batch_id FROM mutations WHERE uid = ? AND batch_id <= ?").binding(uid, Long.valueOf(lastAcknowledgedBatchId)).forEach(SQLiteSchema$$Lambda$14.lambdaFactory$(sQLiteSchema, uid));
    }

    /* access modifiers changed from: private */
    public void removeMutationBatch(String uid, int batchId) {
        SQLiteStatement mutationDeleter = this.f425db.compileStatement("DELETE FROM mutations WHERE uid = ? AND batch_id = ?");
        mutationDeleter.bindString(1, uid);
        mutationDeleter.bindLong(2, (long) batchId);
        Assert.hardAssert(mutationDeleter.executeUpdateDelete() != 0, "Mutatiohn batch (%s, %d) did not exist", uid, Integer.valueOf(batchId));
        this.f425db.execSQL("DELETE FROM document_mutations WHERE uid = ? AND batch_id = ?", new Object[]{uid, Integer.valueOf(batchId)});
    }

    private void createV1TargetCache() {
        ifTablesDontExist(new String[]{"targets", "target_globals", "target_documents"}, SQLiteSchema$$Lambda$3.lambdaFactory$(this));
    }

    static /* synthetic */ void lambda$createV1TargetCache$3(SQLiteSchema sQLiteSchema) {
        sQLiteSchema.f425db.execSQL("CREATE TABLE targets (target_id INTEGER PRIMARY KEY, canonical_id TEXT, snapshot_version_seconds INTEGER, snapshot_version_nanos INTEGER, resume_token BLOB, last_listen_sequence_number INTEGER,target_proto BLOB)");
        sQLiteSchema.f425db.execSQL("CREATE INDEX query_targets ON targets (canonical_id, target_id)");
        sQLiteSchema.f425db.execSQL("CREATE TABLE target_globals (highest_target_id INTEGER, highest_listen_sequence_number INTEGER, last_remote_snapshot_version_seconds INTEGER, last_remote_snapshot_version_nanos INTEGER)");
        sQLiteSchema.f425db.execSQL("CREATE TABLE target_documents (target_id INTEGER, path TEXT, PRIMARY KEY (target_id, path))");
        sQLiteSchema.f425db.execSQL("CREATE INDEX document_targets ON target_documents (path, target_id)");
    }

    private void dropV1TargetCache() {
        if (tableExists("targets")) {
            this.f425db.execSQL("DROP TABLE targets");
        }
        if (tableExists("target_globals")) {
            this.f425db.execSQL("DROP TABLE target_globals");
        }
        if (tableExists("target_documents")) {
            this.f425db.execSQL("DROP TABLE target_documents");
        }
    }

    private void createV1RemoteDocumentCache() {
        ifTablesDontExist(new String[]{"remote_documents"}, SQLiteSchema$$Lambda$4.lambdaFactory$(this));
    }

    private void createLocalDocumentsCollectionIndex() {
        ifTablesDontExist(new String[]{"collection_index"}, SQLiteSchema$$Lambda$5.lambdaFactory$(this));
    }

    private void ensureTargetGlobal() {
        if (!(DatabaseUtils.queryNumEntries(this.f425db, "target_globals") == 1)) {
            this.f425db.execSQL("INSERT INTO target_globals (highest_target_id, highest_listen_sequence_number, last_remote_snapshot_version_seconds, last_remote_snapshot_version_nanos) VALUES (?, ?, ?, ?)", new String[]{"0", "0", "0", "0"});
        }
    }

    private void addTargetCount() {
        if (!tableContainsColumn("target_globals", "target_count")) {
            this.f425db.execSQL("ALTER TABLE target_globals ADD COLUMN target_count INTEGER");
        }
        long count = DatabaseUtils.queryNumEntries(this.f425db, "targets");
        ContentValues cv = new ContentValues();
        cv.put("target_count", Long.valueOf(count));
        this.f425db.update("target_globals", cv, (String) null, (String[]) null);
    }

    private void addSequenceNumber() {
        if (!tableContainsColumn("target_documents", "sequence_number")) {
            this.f425db.execSQL("ALTER TABLE target_documents ADD COLUMN sequence_number INTEGER");
        }
    }

    private boolean hasReadTime() {
        boolean hasReadTimeSeconds = tableContainsColumn("remote_documents", "read_time_seconds");
        boolean hasReadTimeNanos = tableContainsColumn("remote_documents", "read_time_nanos");
        Assert.hardAssert(hasReadTimeSeconds == hasReadTimeNanos, "Table contained just one of read_time_seconds or read_time_nanos", new Object[0]);
        if (!hasReadTimeSeconds || !hasReadTimeNanos) {
            return false;
        }
        return true;
    }

    private void addReadTime() {
        this.f425db.execSQL("ALTER TABLE remote_documents ADD COLUMN read_time_seconds INTEGER");
        this.f425db.execSQL("ALTER TABLE remote_documents ADD COLUMN read_time_nanos INTEGER");
    }

    private void dropLastLimboFreeSnapshotVersion() {
        new SQLitePersistence.Query(this.f425db, "SELECT target_id, target_proto FROM targets").forEach(SQLiteSchema$$Lambda$6.lambdaFactory$(this));
    }

    static /* synthetic */ void lambda$dropLastLimboFreeSnapshotVersion$6(SQLiteSchema sQLiteSchema, Cursor cursor) {
        int targetId = cursor.getInt(0);
        try {
            Target targetProto = (Target) ((Target.Builder) Target.parseFrom(cursor.getBlob(1)).toBuilder()).clearLastLimboFreeSnapshotVersion().build();
            sQLiteSchema.f425db.execSQL("UPDATE targets SET target_proto = ? WHERE target_id = ?", new Object[]{targetProto.toByteArray(), Integer.valueOf(targetId)});
        } catch (InvalidProtocolBufferException e) {
            throw Assert.fail("Failed to decode Query data for target %s", Integer.valueOf(targetId));
        }
    }

    private void ensureSequenceNumbers() {
        Long boxedSequenceNumber = (Long) new SQLitePersistence.Query(this.f425db, "SELECT highest_listen_sequence_number FROM target_globals LIMIT 1").firstValue(SQLiteSchema$$Lambda$7.lambdaFactory$());
        Assert.hardAssert(boxedSequenceNumber != null, "Missing highest sequence number", new Object[0]);
        long sequenceNumber = boxedSequenceNumber.longValue();
        SQLiteStatement tagDocument = this.f425db.compileStatement("INSERT INTO target_documents (target_id, path, sequence_number) VALUES (0, ?, ?)");
        SQLitePersistence.Query untaggedDocumentsQuery = new SQLitePersistence.Query(this.f425db, "SELECT RD.path FROM remote_documents AS RD WHERE NOT EXISTS (SELECT TD.path FROM target_documents AS TD WHERE RD.path = TD.path AND TD.target_id = 0) LIMIT ?").binding(100);
        boolean[] resultsRemaining = new boolean[1];
        do {
            resultsRemaining[0] = false;
            untaggedDocumentsQuery.forEach(SQLiteSchema$$Lambda$8.lambdaFactory$(resultsRemaining, tagDocument, sequenceNumber));
        } while (resultsRemaining[0]);
    }

    static /* synthetic */ void lambda$ensureSequenceNumbers$8(boolean[] resultsRemaining, SQLiteStatement tagDocument, long sequenceNumber, Cursor row) {
        boolean z = true;
        resultsRemaining[0] = true;
        tagDocument.clearBindings();
        tagDocument.bindString(1, row.getString(0));
        tagDocument.bindLong(2, sequenceNumber);
        if (tagDocument.executeInsert() == -1) {
            z = false;
        }
        Assert.hardAssert(z, "Failed to insert a sentinel row", new Object[0]);
    }

    private void createV8CollectionParentsIndex() {
        ifTablesDontExist(new String[]{"collection_parents"}, SQLiteSchema$$Lambda$9.lambdaFactory$(this));
        Consumer<ResourcePath> addEntry = SQLiteSchema$$Lambda$10.lambdaFactory$(new MemoryIndexManager.MemoryCollectionParentIndex(), this.f425db.compileStatement("INSERT OR REPLACE INTO collection_parents (collection_id, parent) VALUES (?, ?)"));
        new SQLitePersistence.Query(this.f425db, "SELECT path FROM remote_documents").forEach(SQLiteSchema$$Lambda$11.lambdaFactory$(addEntry));
        new SQLitePersistence.Query(this.f425db, "SELECT path FROM document_mutations").forEach(SQLiteSchema$$Lambda$12.lambdaFactory$(addEntry));
    }

    static /* synthetic */ void lambda$createV8CollectionParentsIndex$10(MemoryIndexManager.MemoryCollectionParentIndex cache, SQLiteStatement addIndexEntry, ResourcePath collectionPath) {
        if (cache.add(collectionPath)) {
            String collectionId = collectionPath.getLastSegment();
            addIndexEntry.clearBindings();
            addIndexEntry.bindString(1, collectionId);
            addIndexEntry.bindString(2, EncodedPath.encode((ResourcePath) collectionPath.popLast()));
            addIndexEntry.execute();
        }
    }

    private boolean tableContainsColumn(String table, String column) {
        return getTableColumns(table).indexOf(column) != -1;
    }

    /* access modifiers changed from: package-private */
    public List<String> getTableColumns(String table) {
        Cursor c = null;
        List<String> columns = new ArrayList<>();
        try {
            SQLiteDatabase sQLiteDatabase = this.f425db;
            c = sQLiteDatabase.rawQuery("PRAGMA table_info(" + table + ")", (String[]) null);
            int nameIndex = c.getColumnIndex("name");
            while (c.moveToNext()) {
                columns.add(c.getString(nameIndex));
            }
            return columns;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

    private void rewriteCanonicalIds() {
        new SQLitePersistence.Query(this.f425db, "SELECT target_id, target_proto FROM targets").forEach(SQLiteSchema$$Lambda$13.lambdaFactory$(this));
    }

    static /* synthetic */ void lambda$rewriteCanonicalIds$13(SQLiteSchema sQLiteSchema, Cursor cursor) {
        int targetId = cursor.getInt(0);
        try {
            String updatedCanonicalId = sQLiteSchema.serializer.decodeTargetData(Target.parseFrom(cursor.getBlob(1))).getTarget().getCanonicalId();
            sQLiteSchema.f425db.execSQL("UPDATE targets SET canonical_id  = ? WHERE target_id = ?", new Object[]{updatedCanonicalId, Integer.valueOf(targetId)});
        } catch (InvalidProtocolBufferException e) {
            throw Assert.fail("Failed to decode Query data for target %s", Integer.valueOf(targetId));
        }
    }

    private boolean tableExists(String table) {
        return !new SQLitePersistence.Query(this.f425db, "SELECT 1=1 FROM sqlite_master WHERE tbl_name = ?").binding(table).isEmpty();
    }
}
