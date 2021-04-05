package com.google.firebase.firestore.model;

import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.Util;
import com.google.firestore.p012v1.ArrayValue;
import com.google.firestore.p012v1.ArrayValueOrBuilder;
import com.google.firestore.p012v1.MapValue;
import com.google.firestore.p012v1.Value;
import com.google.protobuf.NullValue;
import com.google.protobuf.Timestamp;
import com.google.type.LatLng;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Values {
    public static final Value NAN_VALUE = ((Value) Value.newBuilder().setDoubleValue(Double.NaN).build());
    public static final Value NULL_VALUE = ((Value) Value.newBuilder().setNullValue(NullValue.NULL_VALUE).build());
    public static final int TYPE_ORDER_ARRAY = 9;
    public static final int TYPE_ORDER_BLOB = 6;
    public static final int TYPE_ORDER_BOOLEAN = 1;
    public static final int TYPE_ORDER_GEOPOINT = 8;
    public static final int TYPE_ORDER_MAP = 10;
    public static final int TYPE_ORDER_NULL = 0;
    public static final int TYPE_ORDER_NUMBER = 2;
    public static final int TYPE_ORDER_REFERENCE = 7;
    public static final int TYPE_ORDER_SERVER_TIMESTAMP = 4;
    public static final int TYPE_ORDER_STRING = 5;
    public static final int TYPE_ORDER_TIMESTAMP = 3;

    /* renamed from: com.google.firebase.firestore.model.Values$1 */
    static /* synthetic */ class C19051 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase;

        static {
            int[] iArr = new int[Value.ValueTypeCase.values().length];
            $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase = iArr;
            try {
                iArr[Value.ValueTypeCase.NULL_VALUE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[Value.ValueTypeCase.BOOLEAN_VALUE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[Value.ValueTypeCase.INTEGER_VALUE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[Value.ValueTypeCase.DOUBLE_VALUE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[Value.ValueTypeCase.TIMESTAMP_VALUE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[Value.ValueTypeCase.STRING_VALUE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[Value.ValueTypeCase.BYTES_VALUE.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[Value.ValueTypeCase.REFERENCE_VALUE.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[Value.ValueTypeCase.GEO_POINT_VALUE.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[Value.ValueTypeCase.ARRAY_VALUE.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[Value.ValueTypeCase.MAP_VALUE.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
        }
    }

    public static int typeOrder(Value value) {
        switch (C19051.$SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[value.getValueTypeCase().ordinal()]) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 2;
            case 5:
                return 3;
            case 6:
                return 5;
            case 7:
                return 6;
            case 8:
                return 7;
            case 9:
                return 8;
            case 10:
                return 9;
            case 11:
                if (ServerTimestamps.isServerTimestamp(value)) {
                    return 4;
                }
                return 10;
            default:
                throw Assert.fail("Invalid value type: " + value.getValueTypeCase(), new Object[0]);
        }
    }

    public static boolean equals(Value left, Value right) {
        int leftType;
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null || (leftType = typeOrder(left)) != typeOrder(right)) {
            return false;
        }
        if (leftType == 2) {
            return numberEquals(left, right);
        }
        if (leftType == 4) {
            return ServerTimestamps.getLocalWriteTime(left).equals(ServerTimestamps.getLocalWriteTime(right));
        }
        if (leftType == 9) {
            return arrayEquals(left, right);
        }
        if (leftType != 10) {
            return left.equals(right);
        }
        return objectEquals(left, right);
    }

    private static boolean numberEquals(Value left, Value right) {
        if (left.getValueTypeCase() == Value.ValueTypeCase.INTEGER_VALUE && right.getValueTypeCase() == Value.ValueTypeCase.INTEGER_VALUE) {
            return left.equals(right);
        }
        if (left.getValueTypeCase() == Value.ValueTypeCase.DOUBLE_VALUE && right.getValueTypeCase() == Value.ValueTypeCase.DOUBLE_VALUE && Double.doubleToLongBits(left.getDoubleValue()) == Double.doubleToLongBits(right.getDoubleValue())) {
            return true;
        }
        return false;
    }

    private static boolean arrayEquals(Value left, Value right) {
        ArrayValue leftArray = left.getArrayValue();
        ArrayValue rightArray = right.getArrayValue();
        if (leftArray.getValuesCount() != rightArray.getValuesCount()) {
            return false;
        }
        for (int i = 0; i < leftArray.getValuesCount(); i++) {
            if (!equals(leftArray.getValues(i), rightArray.getValues(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean objectEquals(Value left, Value right) {
        MapValue leftMap = left.getMapValue();
        MapValue rightMap = right.getMapValue();
        if (leftMap.getFieldsCount() != rightMap.getFieldsCount()) {
            return false;
        }
        for (Map.Entry<String, Value> entry : leftMap.getFieldsMap().entrySet()) {
            if (!entry.getValue().equals(rightMap.getFieldsMap().get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }

    public static boolean contains(ArrayValueOrBuilder haystack, Value needle) {
        for (Value haystackElement : haystack.getValuesList()) {
            if (equals(haystackElement, needle)) {
                return true;
            }
        }
        return false;
    }

    public static int compare(Value left, Value right) {
        int leftType = typeOrder(left);
        int rightType = typeOrder(right);
        if (leftType != rightType) {
            return Util.compareIntegers(leftType, rightType);
        }
        switch (leftType) {
            case 0:
                return 0;
            case 1:
                return Util.compareBooleans(left.getBooleanValue(), right.getBooleanValue());
            case 2:
                return compareNumbers(left, right);
            case 3:
                return compareTimestamps(left.getTimestampValue(), right.getTimestampValue());
            case 4:
                return compareTimestamps(ServerTimestamps.getLocalWriteTime(left), ServerTimestamps.getLocalWriteTime(right));
            case 5:
                return left.getStringValue().compareTo(right.getStringValue());
            case 6:
                return Util.compareByteStrings(left.getBytesValue(), right.getBytesValue());
            case 7:
                return compareReferences(left.getReferenceValue(), right.getReferenceValue());
            case 8:
                return compareGeoPoints(left.getGeoPointValue(), right.getGeoPointValue());
            case 9:
                return compareArrays(left.getArrayValue(), right.getArrayValue());
            case 10:
                return compareMaps(left.getMapValue(), right.getMapValue());
            default:
                throw Assert.fail("Invalid value type: " + leftType, new Object[0]);
        }
    }

    private static int compareNumbers(Value left, Value right) {
        if (left.getValueTypeCase() == Value.ValueTypeCase.DOUBLE_VALUE) {
            double leftDouble = left.getDoubleValue();
            if (right.getValueTypeCase() == Value.ValueTypeCase.DOUBLE_VALUE) {
                return Util.compareDoubles(leftDouble, right.getDoubleValue());
            }
            if (right.getValueTypeCase() == Value.ValueTypeCase.INTEGER_VALUE) {
                return Util.compareMixed(leftDouble, right.getIntegerValue());
            }
        } else if (left.getValueTypeCase() == Value.ValueTypeCase.INTEGER_VALUE) {
            long leftLong = left.getIntegerValue();
            if (right.getValueTypeCase() == Value.ValueTypeCase.INTEGER_VALUE) {
                return Util.compareLongs(leftLong, right.getIntegerValue());
            }
            if (right.getValueTypeCase() == Value.ValueTypeCase.DOUBLE_VALUE) {
                return Util.compareMixed(right.getDoubleValue(), leftLong) * -1;
            }
        }
        throw Assert.fail("Unexpected values: %s vs %s", left, right);
    }

    private static int compareTimestamps(Timestamp left, Timestamp right) {
        int cmp = Util.compareLongs(left.getSeconds(), right.getSeconds());
        if (cmp != 0) {
            return cmp;
        }
        return Util.compareIntegers(left.getNanos(), right.getNanos());
    }

    private static int compareReferences(String leftPath, String rightPath) {
        String[] leftSegments = leftPath.split("/", -1);
        String[] rightSegments = rightPath.split("/", -1);
        int minLength = Math.min(leftSegments.length, rightSegments.length);
        for (int i = 0; i < minLength; i++) {
            int cmp = leftSegments[i].compareTo(rightSegments[i]);
            if (cmp != 0) {
                return cmp;
            }
        }
        return Util.compareIntegers(leftSegments.length, rightSegments.length);
    }

    private static int compareGeoPoints(LatLng left, LatLng right) {
        int comparison = Util.compareDoubles(left.getLatitude(), right.getLatitude());
        if (comparison == 0) {
            return Util.compareDoubles(left.getLongitude(), right.getLongitude());
        }
        return comparison;
    }

    private static int compareArrays(ArrayValue left, ArrayValue right) {
        int minLength = Math.min(left.getValuesCount(), right.getValuesCount());
        for (int i = 0; i < minLength; i++) {
            int cmp = compare(left.getValues(i), right.getValues(i));
            if (cmp != 0) {
                return cmp;
            }
        }
        return Util.compareIntegers(left.getValuesCount(), right.getValuesCount());
    }

    private static int compareMaps(MapValue left, MapValue right) {
        Iterator<Map.Entry<String, Value>> iterator1 = new TreeMap(left.getFieldsMap()).entrySet().iterator();
        Iterator<Map.Entry<String, Value>> iterator2 = new TreeMap(right.getFieldsMap()).entrySet().iterator();
        while (iterator1.hasNext() && iterator2.hasNext()) {
            Map.Entry<String, Value> entry1 = iterator1.next();
            Map.Entry<String, Value> entry2 = iterator2.next();
            int keyCompare = entry1.getKey().compareTo(entry2.getKey());
            if (keyCompare != 0) {
                return keyCompare;
            }
            int valueCompare = compare(entry1.getValue(), entry2.getValue());
            if (valueCompare != 0) {
                return valueCompare;
            }
        }
        return Util.compareBooleans(iterator1.hasNext(), iterator2.hasNext());
    }

    public static String canonicalId(Value value) {
        StringBuilder builder = new StringBuilder();
        canonifyValue(builder, value);
        return builder.toString();
    }

    private static void canonifyValue(StringBuilder builder, Value value) {
        switch (C19051.$SwitchMap$com$google$firestore$v1$Value$ValueTypeCase[value.getValueTypeCase().ordinal()]) {
            case 1:
                builder.append("null");
                return;
            case 2:
                builder.append(value.getBooleanValue());
                return;
            case 3:
                builder.append(value.getIntegerValue());
                return;
            case 4:
                builder.append(value.getDoubleValue());
                return;
            case 5:
                canonifyTimestamp(builder, value.getTimestampValue());
                return;
            case 6:
                builder.append(value.getStringValue());
                return;
            case 7:
                builder.append(Util.toDebugString(value.getBytesValue()));
                return;
            case 8:
                canonifyReference(builder, value);
                return;
            case 9:
                canonifyGeoPoint(builder, value.getGeoPointValue());
                return;
            case 10:
                canonifyArray(builder, value.getArrayValue());
                return;
            case 11:
                canonifyObject(builder, value.getMapValue());
                return;
            default:
                throw Assert.fail("Invalid value type: " + value.getValueTypeCase(), new Object[0]);
        }
    }

    private static void canonifyTimestamp(StringBuilder builder, Timestamp timestamp) {
        builder.append(String.format("time(%s,%s)", new Object[]{Long.valueOf(timestamp.getSeconds()), Integer.valueOf(timestamp.getNanos())}));
    }

    private static void canonifyGeoPoint(StringBuilder builder, LatLng latLng) {
        builder.append(String.format("geo(%s,%s)", new Object[]{Double.valueOf(latLng.getLatitude()), Double.valueOf(latLng.getLongitude())}));
    }

    private static void canonifyReference(StringBuilder builder, Value value) {
        Assert.hardAssert(isReferenceValue(value), "Value should be a ReferenceValue", new Object[0]);
        builder.append(DocumentKey.fromName(value.getReferenceValue()));
    }

    private static void canonifyObject(StringBuilder builder, MapValue mapValue) {
        List<String> keys = new ArrayList<>(mapValue.getFieldsMap().keySet());
        Collections.sort(keys);
        builder.append("{");
        boolean first = true;
        for (String key : keys) {
            if (!first) {
                builder.append(",");
            } else {
                first = false;
            }
            builder.append(key);
            builder.append(":");
            canonifyValue(builder, mapValue.getFieldsOrThrow(key));
        }
        builder.append("}");
    }

    private static void canonifyArray(StringBuilder builder, ArrayValue arrayValue) {
        builder.append("[");
        for (int i = 0; i < arrayValue.getValuesCount(); i++) {
            canonifyValue(builder, arrayValue.getValues(i));
            if (i != arrayValue.getValuesCount() - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
    }

    public static boolean isInteger(Value value) {
        return value != null && value.getValueTypeCase() == Value.ValueTypeCase.INTEGER_VALUE;
    }

    public static boolean isDouble(Value value) {
        return value != null && value.getValueTypeCase() == Value.ValueTypeCase.DOUBLE_VALUE;
    }

    public static boolean isNumber(Value value) {
        return isInteger(value) || isDouble(value);
    }

    public static boolean isArray(Value value) {
        return value != null && value.getValueTypeCase() == Value.ValueTypeCase.ARRAY_VALUE;
    }

    public static boolean isReferenceValue(Value value) {
        return value != null && value.getValueTypeCase() == Value.ValueTypeCase.REFERENCE_VALUE;
    }

    public static boolean isNullValue(Value value) {
        return value != null && value.getValueTypeCase() == Value.ValueTypeCase.NULL_VALUE;
    }

    public static boolean isNanValue(Value value) {
        return value != null && Double.isNaN(value.getDoubleValue());
    }

    public static boolean isMapValue(Value value) {
        return value != null && value.getValueTypeCase() == Value.ValueTypeCase.MAP_VALUE;
    }

    public static Value refValue(DatabaseId databaseId, DocumentKey key) {
        return (Value) Value.newBuilder().setReferenceValue(String.format("projects/%s/databases/%s/documents/%s", new Object[]{databaseId.getProjectId(), databaseId.getDatabaseId(), key.toString()})).build();
    }
}
