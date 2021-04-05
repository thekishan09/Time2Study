package com.google.firebase.firestore.core;

import com.google.firebase.firestore.core.Filter;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.Values;
import com.google.firebase.firestore.util.Assert;
import com.google.firestore.p012v1.Value;

public class InFilter extends FieldFilter {
    InFilter(FieldPath field, Value value) {
        super(field, Filter.Operator.IN, value);
        Assert.hardAssert(Values.isArray(value), "InFilter expects an ArrayValue", new Object[0]);
    }

    public boolean matches(Document doc) {
        Value other = doc.getField(getField());
        return other != null && Values.contains(getValue().getArrayValue(), other);
    }
}
