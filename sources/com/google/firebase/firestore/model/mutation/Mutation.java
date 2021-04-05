package com.google.firebase.firestore.model.mutation;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.ObjectValue;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.util.Assert;

public abstract class Mutation {
    private final DocumentKey key;
    private final Precondition precondition;

    public abstract MaybeDocument applyToLocalView(MaybeDocument maybeDocument, MaybeDocument maybeDocument2, Timestamp timestamp);

    public abstract MaybeDocument applyToRemoteDocument(MaybeDocument maybeDocument, MutationResult mutationResult);

    public abstract ObjectValue extractBaseValue(MaybeDocument maybeDocument);

    Mutation(DocumentKey key2, Precondition precondition2) {
        this.key = key2;
        this.precondition = precondition2;
    }

    public DocumentKey getKey() {
        return this.key;
    }

    public Precondition getPrecondition() {
        return this.precondition;
    }

    /* access modifiers changed from: package-private */
    public boolean hasSameKeyAndPrecondition(Mutation other) {
        return this.key.equals(other.key) && this.precondition.equals(other.precondition);
    }

    /* access modifiers changed from: package-private */
    public int keyAndPreconditionHashCode() {
        return (getKey().hashCode() * 31) + this.precondition.hashCode();
    }

    /* access modifiers changed from: package-private */
    public String keyAndPreconditionToString() {
        return "key=" + this.key + ", precondition=" + this.precondition;
    }

    /* access modifiers changed from: package-private */
    public void verifyKeyMatches(MaybeDocument maybeDoc) {
        if (maybeDoc != null) {
            Assert.hardAssert(maybeDoc.getKey().equals(getKey()), "Can only apply a mutation to a document with the same key", new Object[0]);
        }
    }

    static SnapshotVersion getPostMutationVersion(MaybeDocument maybeDoc) {
        if (maybeDoc instanceof Document) {
            return maybeDoc.getVersion();
        }
        return SnapshotVersion.NONE;
    }
}
