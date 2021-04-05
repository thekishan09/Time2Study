package com.google.firebase.firestore.model;

public abstract class MaybeDocument {
    private final DocumentKey key;
    private final SnapshotVersion version;

    public abstract boolean hasPendingWrites();

    MaybeDocument(DocumentKey key2, SnapshotVersion version2) {
        this.key = key2;
        this.version = version2;
    }

    public DocumentKey getKey() {
        return this.key;
    }

    public SnapshotVersion getVersion() {
        return this.version;
    }
}
