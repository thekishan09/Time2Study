package com.google.firebase.firestore.local;

import com.google.firebase.firestore.model.ResourcePath;
import com.google.firebase.firestore.util.Assert;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

class MemoryIndexManager implements IndexManager {
    private final MemoryCollectionParentIndex collectionParentsIndex = new MemoryCollectionParentIndex();

    MemoryIndexManager() {
    }

    public void addToCollectionParentIndex(ResourcePath collectionPath) {
        this.collectionParentsIndex.add(collectionPath);
    }

    public List<ResourcePath> getCollectionParents(String collectionId) {
        return this.collectionParentsIndex.getEntries(collectionId);
    }

    static class MemoryCollectionParentIndex {
        private final HashMap<String, HashSet<ResourcePath>> index = new HashMap<>();

        MemoryCollectionParentIndex() {
        }

        /* access modifiers changed from: package-private */
        public boolean add(ResourcePath collectionPath) {
            boolean z = true;
            if (collectionPath.length() % 2 != 1) {
                z = false;
            }
            Assert.hardAssert(z, "Expected a collection path.", new Object[0]);
            String collectionId = collectionPath.getLastSegment();
            ResourcePath parentPath = (ResourcePath) collectionPath.popLast();
            HashSet<ResourcePath> existingParents = this.index.get(collectionId);
            if (existingParents == null) {
                existingParents = new HashSet<>();
                this.index.put(collectionId, existingParents);
            }
            return existingParents.add(parentPath);
        }

        /* access modifiers changed from: package-private */
        public List<ResourcePath> getEntries(String collectionId) {
            HashSet<ResourcePath> existingParents = this.index.get(collectionId);
            return existingParents != null ? new ArrayList(existingParents) : Collections.emptyList();
        }
    }
}
