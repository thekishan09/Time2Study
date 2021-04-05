package com.google.firebase.firestore.model.mutation;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.ObjectValue;
import com.google.firebase.firestore.util.Assert;

public final class SetMutation extends Mutation {
    private final ObjectValue value;

    public SetMutation(DocumentKey key, ObjectValue value2, Precondition precondition) {
        super(key, precondition);
        this.value = value2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SetMutation that = (SetMutation) o;
        if (!hasSameKeyAndPrecondition(that) || !this.value.equals(that.value)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (keyAndPreconditionHashCode() * 31) + this.value.hashCode();
    }

    public String toString() {
        return "SetMutation{" + keyAndPreconditionToString() + ", value=" + this.value + "}";
    }

    public MaybeDocument applyToRemoteDocument(MaybeDocument maybeDoc, MutationResult mutationResult) {
        verifyKeyMatches(maybeDoc);
        Assert.hardAssert(mutationResult.getTransformResults() == null, "Transform results received by SetMutation.", new Object[0]);
        return new Document(getKey(), mutationResult.getVersion(), this.value, Document.DocumentState.COMMITTED_MUTATIONS);
    }

    public MaybeDocument applyToLocalView(MaybeDocument maybeDoc, MaybeDocument baseDoc, Timestamp localWriteTime) {
        verifyKeyMatches(maybeDoc);
        if (!getPrecondition().isValidFor(maybeDoc)) {
            return maybeDoc;
        }
        return new Document(getKey(), getPostMutationVersion(maybeDoc), this.value, Document.DocumentState.LOCAL_MUTATIONS);
    }

    public ObjectValue getValue() {
        return this.value;
    }

    public ObjectValue extractBaseValue(MaybeDocument maybeDoc) {
        return null;
    }
}
