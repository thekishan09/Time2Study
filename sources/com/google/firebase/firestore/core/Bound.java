package com.google.firebase.firestore.core;

import com.google.firebase.firestore.core.OrderBy;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.Values;
import com.google.firebase.firestore.util.Assert;
import com.google.firestore.p012v1.Value;
import java.util.List;

public final class Bound {
    private final boolean before;
    private final List<Value> position;

    public Bound(List<Value> position2, boolean before2) {
        this.position = position2;
        this.before = before2;
    }

    public List<Value> getPosition() {
        return this.position;
    }

    public boolean isBefore() {
        return this.before;
    }

    public String canonicalString() {
        StringBuilder builder = new StringBuilder();
        if (this.before) {
            builder.append("b:");
        } else {
            builder.append("a:");
        }
        boolean first = true;
        for (Value indexComponent : this.position) {
            if (!first) {
                builder.append(",");
            }
            first = false;
            builder.append(Values.canonicalId(indexComponent));
        }
        return builder.toString();
    }

    public boolean sortsBeforeDocument(List<OrderBy> orderBy, Document document) {
        Assert.hardAssert(this.position.size() <= orderBy.size(), "Bound has more components than query's orderBy", new Object[0]);
        int comparison = 0;
        for (int i = 0; i < this.position.size(); i++) {
            OrderBy orderByComponent = orderBy.get(i);
            Value component = this.position.get(i);
            if (orderByComponent.field.equals(FieldPath.KEY_PATH)) {
                Assert.hardAssert(Values.isReferenceValue(component), "Bound has a non-key value where the key path is being used %s", component);
                comparison = DocumentKey.fromName(component.getReferenceValue()).compareTo(document.getKey());
            } else {
                Value docValue = document.getField(orderByComponent.getField());
                Assert.hardAssert(docValue != null, "Field should exist since document matched the orderBy already.", new Object[0]);
                comparison = Values.compare(component, docValue);
            }
            if (orderByComponent.getDirection().equals(OrderBy.Direction.DESCENDING)) {
                comparison *= -1;
            }
            if (comparison != 0) {
                break;
            }
        }
        if (this.before != 0) {
            if (comparison <= 0) {
                return true;
            }
        } else if (comparison < 0) {
            return true;
        }
        return false;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bound bound = (Bound) o;
        if (this.before != bound.before || !this.position.equals(bound.position)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((int) this.before) * true) + this.position.hashCode();
    }

    public String toString() {
        return "Bound{before=" + this.before + ", position=" + this.position + '}';
    }
}
