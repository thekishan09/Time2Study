package com.google.firebase.firestore.local;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;

public final class LocalWriteResult {
    private final int batchId;
    private final ImmutableSortedMap<DocumentKey, MaybeDocument> changes;

    LocalWriteResult(int batchId2, ImmutableSortedMap<DocumentKey, MaybeDocument> changes2) {
        this.batchId = batchId2;
        this.changes = changes2;
    }

    public int getBatchId() {
        return this.batchId;
    }

    public ImmutableSortedMap<DocumentKey, MaybeDocument> getChanges() {
        return this.changes;
    }
}
