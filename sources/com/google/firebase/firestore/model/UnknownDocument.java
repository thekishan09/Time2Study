package com.google.firebase.firestore.model;

public final class UnknownDocument extends MaybeDocument {
    public UnknownDocument(DocumentKey key, SnapshotVersion version) {
        super(key, version);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UnknownDocument that = (UnknownDocument) o;
        if (!getVersion().equals(that.getVersion()) || !getKey().equals(that.getKey())) {
            return false;
        }
        return true;
    }

    public boolean hasPendingWrites() {
        return true;
    }

    public int hashCode() {
        return (getKey().hashCode() * 31) + getVersion().hashCode();
    }

    public String toString() {
        return "UnknownDocument{key=" + getKey() + ", version=" + getVersion() + '}';
    }
}
