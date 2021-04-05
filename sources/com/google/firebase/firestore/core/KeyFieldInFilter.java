package com.google.firebase.firestore.core;

import com.google.firebase.firestore.core.Filter;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.Values;
import com.google.firebase.firestore.util.Assert;
import com.google.firestore.p012v1.Value;
import java.util.ArrayList;
import java.util.List;

public class KeyFieldInFilter extends FieldFilter {
    private final List<DocumentKey> keys = new ArrayList();

    KeyFieldInFilter(FieldPath field, Value value) {
        super(field, Filter.Operator.IN, value);
        Assert.hardAssert(Values.isArray(value), "KeyFieldInFilter expects an ArrayValue", new Object[0]);
        for (Value element : value.getArrayValue().getValuesList()) {
            Assert.hardAssert(Values.isReferenceValue(element), "Comparing on key with IN, but an array value was not a ReferenceValue", new Object[0]);
            this.keys.add(DocumentKey.fromName(element.getReferenceValue()));
        }
    }

    public boolean matches(Document doc) {
        return this.keys.contains(doc.getKey());
    }
}
