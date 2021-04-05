package com.google.firebase.firestore.model.mutation;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.ObjectValue;
import com.google.firebase.firestore.model.UnknownDocument;
import com.google.firebase.firestore.util.Assert;
import com.google.firestore.p012v1.Value;

public final class PatchMutation extends Mutation {
    private final FieldMask mask;
    private final ObjectValue value;

    public PatchMutation(DocumentKey key, ObjectValue value2, FieldMask mask2, Precondition precondition) {
        super(key, precondition);
        this.value = value2;
        this.mask = mask2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PatchMutation that = (PatchMutation) o;
        if (!hasSameKeyAndPrecondition(that) || !this.value.equals(that.value)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (keyAndPreconditionHashCode() * 31) + this.value.hashCode();
    }

    public String toString() {
        return "PatchMutation{" + keyAndPreconditionToString() + ", mask=" + this.mask + ", value=" + this.value + "}";
    }

    public ObjectValue getValue() {
        return this.value;
    }

    public FieldMask getMask() {
        return this.mask;
    }

    public MaybeDocument applyToRemoteDocument(MaybeDocument maybeDoc, MutationResult mutationResult) {
        verifyKeyMatches(maybeDoc);
        Assert.hardAssert(mutationResult.getTransformResults() == null, "Transform results received by PatchMutation.", new Object[0]);
        if (!getPrecondition().isValidFor(maybeDoc)) {
            return new UnknownDocument(getKey(), mutationResult.getVersion());
        }
        return new Document(getKey(), mutationResult.getVersion(), patchDocument(maybeDoc), Document.DocumentState.COMMITTED_MUTATIONS);
    }

    public MaybeDocument applyToLocalView(MaybeDocument maybeDoc, MaybeDocument baseDoc, Timestamp localWriteTime) {
        verifyKeyMatches(maybeDoc);
        if (!getPrecondition().isValidFor(maybeDoc)) {
            return maybeDoc;
        }
        return new Document(getKey(), getPostMutationVersion(maybeDoc), patchDocument(maybeDoc), Document.DocumentState.LOCAL_MUTATIONS);
    }

    public ObjectValue extractBaseValue(MaybeDocument maybeDoc) {
        return null;
    }

    private ObjectValue patchDocument(MaybeDocument maybeDoc) {
        ObjectValue data;
        if (maybeDoc instanceof Document) {
            data = ((Document) maybeDoc).getData();
        } else {
            data = ObjectValue.emptyObject();
        }
        return patchObject(data);
    }

    private ObjectValue patchObject(ObjectValue obj) {
        ObjectValue.Builder builder = obj.toBuilder();
        for (FieldPath path : this.mask.getMask()) {
            if (!path.isEmpty()) {
                Value newValue = this.value.get(path);
                if (newValue == null) {
                    builder.delete(path);
                } else {
                    builder.set(path, newValue);
                }
            }
        }
        return builder.build();
    }
}
