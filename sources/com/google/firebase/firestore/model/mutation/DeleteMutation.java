package com.google.firebase.firestore.model.mutation;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.NoDocument;
import com.google.firebase.firestore.model.ObjectValue;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.util.Assert;

public final class DeleteMutation extends Mutation {
    public DeleteMutation(DocumentKey key, Precondition precondition) {
        super(key, precondition);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return hasSameKeyAndPrecondition((DeleteMutation) o);
    }

    public int hashCode() {
        return keyAndPreconditionHashCode();
    }

    public String toString() {
        return "DeleteMutation{" + keyAndPreconditionToString() + "}";
    }

    public MaybeDocument applyToRemoteDocument(MaybeDocument maybeDoc, MutationResult mutationResult) {
        verifyKeyMatches(maybeDoc);
        Assert.hardAssert(mutationResult.getTransformResults() == null, "Transform results received by DeleteMutation.", new Object[0]);
        return new NoDocument(getKey(), mutationResult.getVersion(), true);
    }

    public MaybeDocument applyToLocalView(MaybeDocument maybeDoc, MaybeDocument baseDoc, Timestamp localWriteTime) {
        verifyKeyMatches(maybeDoc);
        if (!getPrecondition().isValidFor(maybeDoc)) {
            return maybeDoc;
        }
        return new NoDocument(getKey(), SnapshotVersion.NONE, false);
    }

    public ObjectValue extractBaseValue(MaybeDocument maybeDoc) {
        return null;
    }
}
