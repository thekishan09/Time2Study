package com.google.firebase.firestore.local;

/* compiled from: SQLiteSchema */
final /* synthetic */ class SQLiteSchema$$Lambda$9 implements Runnable {
    private final SQLiteSchema arg$1;

    private SQLiteSchema$$Lambda$9(SQLiteSchema sQLiteSchema) {
        this.arg$1 = sQLiteSchema;
    }

    public static Runnable lambdaFactory$(SQLiteSchema sQLiteSchema) {
        return new SQLiteSchema$$Lambda$9(sQLiteSchema);
    }

    public void run() {
        this.arg$1.f425db.execSQL("CREATE TABLE collection_parents (collection_id TEXT, parent TEXT, PRIMARY KEY(collection_id, parent))");
    }
}
