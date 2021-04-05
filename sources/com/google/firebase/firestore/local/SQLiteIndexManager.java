package com.google.firebase.firestore.local;

import com.google.firebase.firestore.local.MemoryIndexManager;
import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.util.Assert;
import java.util.ArrayList;
import java.util.List;

final class SQLiteIndexManager implements IndexManager {
    private final MemoryIndexManager.MemoryCollectionParentIndex collectionParentsCache = new MemoryIndexManager.MemoryCollectionParentIndex();

    /* renamed from: db */
    private final SQLitePersistence f419db;

    SQLiteIndexManager(SQLitePersistence persistence) {
        this.f419db = persistence;
    }

    public void addToCollectionParentIndex(ResourcePath collectionPath) {
        Assert.hardAssert(collectionPath.length() % 2 == 1, "Expected a collection path.", new Object[0]);
        if (this.collectionParentsCache.add(collectionPath)) {
            this.f419db.execute("INSERT OR REPLACE INTO collection_parents (collection_id, parent) VALUES (?, ?)", collectionPath.getLastSegment(), EncodedPath.encode((ResourcePath) collectionPath.popLast()));
        }
    }

    public List<ResourcePath> getCollectionParents(String collectionId) {
        ArrayList<ResourcePath> parentPaths = new ArrayList<>();
        this.f419db.query("SELECT parent FROM collection_parents WHERE collection_id = ?").binding(collectionId).forEach(SQLiteIndexManager$$Lambda$1.lambdaFactory$(parentPaths));
        return parentPaths;
    }
}
