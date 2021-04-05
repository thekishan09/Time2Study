package com.google.firebase.firestore.core;

import com.google.firebase.firestore.core.Filter;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.Values;
import com.google.firebase.firestore.util.Assert;
import com.google.firestore.p012v1.Value;
import java.util.Arrays;

public class FieldFilter extends Filter {
    private final FieldPath field;
    private final Filter.Operator operator;
    private final Value value;

    protected FieldFilter(FieldPath field2, Filter.Operator operator2, Value value2) {
        this.field = field2;
        this.operator = operator2;
        this.value = value2;
    }

    public Filter.Operator getOperator() {
        return this.operator;
    }

    public FieldPath getField() {
        return this.field;
    }

    public Value getValue() {
        return this.value;
    }

    public static FieldFilter create(FieldPath path, Filter.Operator operator2, Value value2) {
        if (path.isKeyField()) {
            if (operator2 == Filter.Operator.IN) {
                return new KeyFieldInFilter(path, value2);
            }
            boolean z = (operator2 == Filter.Operator.ARRAY_CONTAINS || operator2 == Filter.Operator.ARRAY_CONTAINS_ANY) ? false : true;
            Assert.hardAssert(z, operator2.toString() + "queries don't make sense on document keys", new Object[0]);
            return new KeyFieldFilter(path, operator2, value2);
        } else if (Values.isNullValue(value2)) {
            if (operator2 == Filter.Operator.EQUAL) {
                return new FieldFilter(path, operator2, value2);
            }
            throw new IllegalArgumentException("Invalid Query. Null supports only equality comparisons (via whereEqualTo()).");
        } else if (Values.isNanValue(value2)) {
            if (operator2 == Filter.Operator.EQUAL) {
                return new FieldFilter(path, operator2, value2);
            }
            throw new IllegalArgumentException("Invalid Query. NaN supports only equality comparisons (via whereEqualTo()).");
        } else if (operator2 == Filter.Operator.ARRAY_CONTAINS) {
            return new ArrayContainsFilter(path, value2);
        } else {
            if (operator2 == Filter.Operator.IN) {
                return new InFilter(path, value2);
            }
            if (operator2 == Filter.Operator.ARRAY_CONTAINS_ANY) {
                return new ArrayContainsAnyFilter(path, value2);
            }
            return new FieldFilter(path, operator2, value2);
        }
    }

    public boolean matches(Document doc) {
        Value other = doc.getField(this.field);
        return other != null && Values.typeOrder(other) == Values.typeOrder(this.value) && matchesComparison(Values.compare(other, this.value));
    }

    /* renamed from: com.google.firebase.firestore.core.FieldFilter$1 */
    static /* synthetic */ class C18911 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$firestore$core$Filter$Operator;

        static {
            int[] iArr = new int[Filter.Operator.values().length];
            $SwitchMap$com$google$firebase$firestore$core$Filter$Operator = iArr;
            try {
                iArr[Filter.Operator.LESS_THAN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[Filter.Operator.LESS_THAN_OR_EQUAL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[Filter.Operator.EQUAL.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[Filter.Operator.GREATER_THAN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$firebase$firestore$core$Filter$Operator[Filter.Operator.GREATER_THAN_OR_EQUAL.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean matchesComparison(int comp) {
        int i = C18911.$SwitchMap$com$google$firebase$firestore$core$Filter$Operator[this.operator.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        if (i != 5) {
                            throw Assert.fail("Unknown FieldFilter operator: %s", this.operator);
                        } else if (comp >= 0) {
                            return true;
                        } else {
                            return false;
                        }
                    } else if (comp > 0) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (comp == 0) {
                    return true;
                } else {
                    return false;
                }
            } else if (comp <= 0) {
                return true;
            } else {
                return false;
            }
        } else if (comp < 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isInequality() {
        return Arrays.asList(new Filter.Operator[]{Filter.Operator.LESS_THAN, Filter.Operator.LESS_THAN_OR_EQUAL, Filter.Operator.GREATER_THAN, Filter.Operator.GREATER_THAN_OR_EQUAL}).contains(this.operator);
    }

    public String getCanonicalId() {
        return getField().canonicalString() + getOperator().toString() + Values.canonicalId(getValue());
    }

    public String toString() {
        return this.field.canonicalString() + " " + this.operator + " " + this.value;
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof FieldFilter)) {
            return false;
        }
        FieldFilter other = (FieldFilter) o;
        if (this.operator != other.operator || !this.field.equals(other.field) || !this.value.equals(other.value)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (((((37 * 31) + this.operator.hashCode()) * 31) + this.field.hashCode()) * 31) + this.value.hashCode();
    }
}
