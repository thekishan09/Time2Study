package com.google.firebase.firestore.model.mutation;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.ObjectValue;
import com.google.firebase.firestore.model.UnknownDocument;
import com.google.firebase.firestore.util.Assert;
import com.google.firestore.p012v1.Value;
import java.util.ArrayList;
import java.util.List;

public final class TransformMutation extends Mutation {
    private final List<FieldTransform> fieldTransforms;

    public TransformMutation(DocumentKey key, List<FieldTransform> fieldTransforms2) {
        super(key, Precondition.exists(true));
        this.fieldTransforms = fieldTransforms2;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TransformMutation that = (TransformMutation) o;
        if (!hasSameKeyAndPrecondition(that) || !this.fieldTransforms.equals(that.fieldTransforms)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (keyAndPreconditionHashCode() * 31) + this.fieldTransforms.hashCode();
    }

    public String toString() {
        return "TransformMutation{" + keyAndPreconditionToString() + ", fieldTransforms=" + this.fieldTransforms + "}";
    }

    public List<FieldTransform> getFieldTransforms() {
        return this.fieldTransforms;
    }

    public MaybeDocument applyToRemoteDocument(MaybeDocument maybeDoc, MutationResult mutationResult) {
        verifyKeyMatches(maybeDoc);
        Assert.hardAssert(mutationResult.getTransformResults() != null, "Transform results missing for TransformMutation.", new Object[0]);
        if (!getPrecondition().isValidFor(maybeDoc)) {
            return new UnknownDocument(getKey(), mutationResult.getVersion());
        }
        Document doc = requireDocument(maybeDoc);
        return new Document(getKey(), mutationResult.getVersion(), transformObject(doc.getData(), serverTransformResults(doc, mutationResult.getTransformResults())), Document.DocumentState.COMMITTED_MUTATIONS);
    }

    public MaybeDocument applyToLocalView(MaybeDocument maybeDoc, MaybeDocument baseDoc, Timestamp localWriteTime) {
        verifyKeyMatches(maybeDoc);
        if (!getPrecondition().isValidFor(maybeDoc)) {
            return maybeDoc;
        }
        Document doc = requireDocument(maybeDoc);
        return new Document(getKey(), doc.getVersion(), transformObject(doc.getData(), localTransformResults(localWriteTime, maybeDoc, baseDoc)), Document.DocumentState.LOCAL_MUTATIONS);
    }

    public ObjectValue extractBaseValue(MaybeDocument maybeDoc) {
        ObjectValue.Builder baseObject = null;
        for (FieldTransform transform : this.fieldTransforms) {
            Value existingValue = null;
            if (maybeDoc instanceof Document) {
                existingValue = ((Document) maybeDoc).getField(transform.getFieldPath());
            }
            Value coercedValue = transform.getOperation().computeBaseValue(existingValue);
            if (coercedValue != null) {
                if (baseObject == null) {
                    baseObject = ObjectValue.newBuilder();
                }
                baseObject.set(transform.getFieldPath(), coercedValue);
            }
        }
        if (baseObject != null) {
            return baseObject.build();
        }
        return null;
    }

    private Document requireDocument(MaybeDocument maybeDoc) {
        Assert.hardAssert(maybeDoc instanceof Document, "Unknown MaybeDocument type %s", maybeDoc);
        Document doc = (Document) maybeDoc;
        Assert.hardAssert(doc.getKey().equals(getKey()), "Can only transform a document with the same key", new Object[0]);
        return doc;
    }

    private List<Value> serverTransformResults(MaybeDocument baseDoc, List<Value> serverTransformResults) {
        ArrayList<Value> transformResults = new ArrayList<>(this.fieldTransforms.size());
        Assert.hardAssert(this.fieldTransforms.size() == serverTransformResults.size(), "server transform count (%d) should match field transform count (%d)", Integer.valueOf(serverTransformResults.size()), Integer.valueOf(this.fieldTransforms.size()));
        for (int i = 0; i < serverTransformResults.size(); i++) {
            FieldTransform fieldTransform = this.fieldTransforms.get(i);
            TransformOperation transform = fieldTransform.getOperation();
            Value previousValue = null;
            if (baseDoc instanceof Document) {
                previousValue = ((Document) baseDoc).getField(fieldTransform.getFieldPath());
            }
            transformResults.add(transform.applyToRemoteDocument(previousValue, serverTransformResults.get(i)));
        }
        return transformResults;
    }

    private List<Value> localTransformResults(Timestamp localWriteTime, MaybeDocument maybeDoc, MaybeDocument baseDoc) {
        ArrayList<Value> transformResults = new ArrayList<>(this.fieldTransforms.size());
        for (FieldTransform fieldTransform : this.fieldTransforms) {
            TransformOperation transform = fieldTransform.getOperation();
            Value previousValue = null;
            if (maybeDoc instanceof Document) {
                previousValue = ((Document) maybeDoc).getField(fieldTransform.getFieldPath());
            }
            if (previousValue == null && (baseDoc instanceof Document)) {
                previousValue = ((Document) baseDoc).getField(fieldTransform.getFieldPath());
            }
            transformResults.add(transform.applyToLocalView(previousValue, localWriteTime));
        }
        return transformResults;
    }

    private ObjectValue transformObject(ObjectValue objectValue, List<Value> transformResults) {
        Assert.hardAssert(transformResults.size() == this.fieldTransforms.size(), "Transform results length mismatch.", new Object[0]);
        ObjectValue.Builder builder = objectValue.toBuilder();
        for (int i = 0; i < this.fieldTransforms.size(); i++) {
            builder.set(this.fieldTransforms.get(i).getFieldPath(), transformResults.get(i));
        }
        return builder.build();
    }
}
