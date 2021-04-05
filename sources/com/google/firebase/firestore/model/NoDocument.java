package com.google.firebase.firestore.model;

public final class NoDocument extends MaybeDocument {
    private boolean hasCommittedMutations;

    public NoDocument(DocumentKey key, SnapshotVersion version, boolean hasCommittedMutations2) {
        super(key, version);
        this.hasCommittedMutations = hasCommittedMutations2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NoDocument that = (NoDocument) o;
        if (this.hasCommittedMutations != that.hasCommittedMutations || !getVersion().equals(that.getVersion()) || !getKey().equals(that.getKey())) {
            return false;
        }
        return true;
    }

    public boolean hasPendingWrites() {
        return hasCommittedMutations();
    }

    public boolean hasCommittedMutations() {
        return this.hasCommittedMutations;
    }

    public int hashCode() {
        return (((getKey().hashCode() * 31) + (this.hasCommittedMutations ? 1 : 0)) * 31) + getVersion().hashCode();
    }

    public String toString() {
        return "NoDocument{key=" + getKey() + ", version=" + getVersion() + ", hasCommittedMutations=" + hasCommittedMutations() + "}";
    }
}
