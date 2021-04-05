package com.google.firebase.firestore.model.mutation;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.ObjectValue;
import com.google.firebase.firestore.util.Assert;

public final class VerifyMutation extends Mutation {
    public VerifyMutation(DocumentKey key, Precondition precondition) {
        super(key, precondition);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return hasSameKeyAndPrecondition((VerifyMutation) o);
    }

    public int hashCode() {
        return keyAndPreconditionHashCode();
    }

    public String toString() {
        return "VerifyMutation{" + keyAndPreconditionToString() + "}";
    }

    public MaybeDocument applyToRemoteDocument(MaybeDocument maybeDoc, MutationResult mutationResult) {
        throw Assert.fail("VerifyMutation should only be used in Transactions.", new Object[0]);
    }

    public MaybeDocument applyToLocalView(MaybeDocument maybeDoc, MaybeDocument baseDoc, Timestamp localWriteTime) {
        throw Assert.fail("VerifyMutation should only be used in Transactions.", new Object[0]);
    }

    public ObjectValue extractBaseValue(MaybeDocument maybeDoc) {
        throw Assert.fail("VerifyMutation should only be used in Transactions.", new Object[0]);
    }
}
