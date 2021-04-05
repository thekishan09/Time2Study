package com.google.api;

import com.google.protobuf.Internal;

public enum FieldBehavior implements Internal.EnumLite {
    FIELD_BEHAVIOR_UNSPECIFIED(0),
    OPTIONAL(1),
    REQUIRED(2),
    OUTPUT_ONLY(3),
    INPUT_ONLY(4),
    IMMUTABLE(5),
    UNRECOGNIZED(-1);
    
    public static final int FIELD_BEHAVIOR_UNSPECIFIED_VALUE = 0;
    public static final int IMMUTABLE_VALUE = 5;
    public static final int INPUT_ONLY_VALUE = 4;
    public static final int OPTIONAL_VALUE = 1;
    public static final int OUTPUT_ONLY_VALUE = 3;
    public static final int REQUIRED_VALUE = 2;
    private static final Internal.EnumLiteMap<FieldBehavior> internalValueMap = null;
    private final int value;

    static {
        internalValueMap = new Internal.EnumLiteMap<FieldBehavior>() {
            public FieldBehavior findValueByNumber(int number) {
                return FieldBehavior.forNumber(number);
            }
        };
    }

    public final int getNumber() {
        if (this != UNRECOGNIZED) {
            return this.value;
        }
        throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
    }

    @Deprecated
    public static FieldBehavior valueOf(int value2) {
        return forNumber(value2);
    }

    public static FieldBehavior forNumber(int value2) {
        if (value2 == 0) {
            return FIELD_BEHAVIOR_UNSPECIFIED;
        }
        if (value2 == 1) {
            return OPTIONAL;
        }
        if (value2 == 2) {
            return REQUIRED;
        }
        if (value2 == 3) {
            return OUTPUT_ONLY;
        }
        if (value2 == 4) {
            return INPUT_ONLY;
        }
        if (value2 != 5) {
            return null;
        }
        return IMMUTABLE;
    }

    public static Internal.EnumLiteMap<FieldBehavior> internalGetValueMap() {
        return internalValueMap;
    }

    public static Internal.EnumVerifier internalGetVerifier() {
        return FieldBehaviorVerifier.INSTANCE;
    }

    private static final class FieldBehaviorVerifier implements Internal.EnumVerifier {
        static final Internal.EnumVerifier INSTANCE = null;

        private FieldBehaviorVerifier() {
        }

        static {
            INSTANCE = new FieldBehaviorVerifier();
        }

        public boolean isInRange(int number) {
            return FieldBehavior.forNumber(number) != null;
        }
    }

    private FieldBehavior(int value2) {
        this.value = value2;
    }
}
