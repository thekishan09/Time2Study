package com.google.firebase.firestore.core;

import com.google.firebase.firestore.core.Filter;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.Values;
import com.google.firebase.firestore.util.Assert;
import com.google.firestore.p012v1.Value;

public class ArrayContainsAnyFilter extends FieldFilter {
    ArrayContainsAnyFilter(FieldPath field, Value value) {
        super(field, Filter.Operator.ARRAY_CONTAINS_ANY, value);
        Assert.hardAssert(Values.isArray(value), "ArrayContainsAnyFilter expects an ArrayValue", new Object[0]);
    }

    public boolean matches(Document doc) {
        Value other = doc.getField(getField());
        if (!Values.isArray(other)) {
            return false;
        }
        for (Value val : other.getArrayValue().getValuesList()) {
            if (Values.contains(getValue().getArrayValue(), val)) {
                return true;
            }
        }
        return false;
    }
}
