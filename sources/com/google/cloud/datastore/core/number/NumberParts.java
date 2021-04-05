package com.google.cloud.datastore.core.number;

public final class NumberParts {
    private static final int DOUBLE_EXPONENT_BIAS = 1023;
    private static final int DOUBLE_MIN_EXPONENT = -1022;
    private static final int DOUBLE_SIGNIFICAND_BITS = 52;
    private static final long DOUBLE_SIGN_BIT = Long.MIN_VALUE;
    static final int NEGATIVE_INFINITE_EXPONENT = Integer.MIN_VALUE;
    static final int POSITIVE_INFINITE_EXPONENT = Integer.MAX_VALUE;
    static final int SIGNIFICAND_BITS = 64;
    private final int exponent;
    private final boolean negative;
    private final long significand;

    private NumberParts(boolean negative2, int exponent2, long significand2) {
        this.negative = negative2;
        this.exponent = exponent2;
        this.significand = significand2;
    }

    public boolean negative() {
        return this.negative;
    }

    public int exponent() {
        return this.exponent;
    }

    public long significand() {
        return this.significand;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NumberParts)) {
            return false;
        }
        NumberParts that = (NumberParts) o;
        if (this.negative == that.negative && this.exponent == that.exponent && this.significand == that.significand) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        long j = this.significand;
        return (((((int) this.negative) * true) + this.exponent) * 31) + ((int) (j ^ (j >>> 32)));
    }

    public boolean isZero() {
        return exponent() == Integer.MIN_VALUE && significand() == 0;
    }

    public boolean isNaN() {
        return exponent() == Integer.MAX_VALUE && significand() != 0;
    }

    public boolean isInfinite() {
        return exponent() == Integer.MAX_VALUE && significand() == 0;
    }

    public static NumberParts create(boolean negative2, int exponent2, long significand2) {
        if (exponent2 != Integer.MAX_VALUE || significand2 == 0 || (negative2 && significand2 == 1)) {
            return new NumberParts(negative2, exponent2, significand2);
        }
        throw new IllegalArgumentException("Invalid number parts: non-normalized NaN");
    }

    public static NumberParts fromLong(long value) {
        if (value == 0) {
            return create(false, Integer.MIN_VALUE, 0);
        }
        boolean negative2 = false;
        if (value < 0) {
            negative2 = true;
            value = -value;
        }
        int leadingZeros = Long.numberOfLeadingZeros(value);
        int binaryExponent = 63 - leadingZeros;
        return create(negative2, binaryExponent, (((1 << binaryExponent) ^ -1) & value) << (leadingZeros + 1));
    }

    public static NumberParts fromDouble(double value) {
        long significand2;
        long doubleBits = Double.doubleToLongBits(value);
        boolean negative2 = value < 0.0d;
        int exponent2 = ((int) ((doubleBits >>> 52) & 2047)) - DOUBLE_EXPONENT_BIAS;
        long significand3 = 4503599627370495L & doubleBits;
        if (exponent2 < DOUBLE_MIN_EXPONENT) {
            if (significand3 == 0) {
                return create(false, Integer.MIN_VALUE, 0);
            }
            int leadingZeros = Long.numberOfLeadingZeros(significand3);
            significand2 = (significand3 & ((1 << (63 - leadingZeros)) ^ -1)) << (leadingZeros + 1);
            exponent2 -= leadingZeros - 12;
        } else if (exponent2 <= DOUBLE_EXPONENT_BIAS) {
            significand2 = significand3 << 12;
        } else if (significand3 != 0) {
            return create(true, Integer.MAX_VALUE, 1);
        } else {
            if (negative2) {
                return create(true, Integer.MAX_VALUE, 0);
            }
            return create(false, Integer.MAX_VALUE, 0);
        }
        return create(negative2, exponent2, significand2);
    }

    public NumberParts negate() {
        if (isZero() || isNaN()) {
            return this;
        }
        return create(!negative(), exponent(), significand());
    }

    public boolean representableAsDouble() {
        return doubleRepresentationError() == null;
    }

    public boolean representableAsLong() {
        return longRepresentationError() == null;
    }

    public double asDouble() {
        long exponent2;
        String representationError = doubleRepresentationError();
        if (representationError != null) {
            throw new IllegalArgumentException(representationError);
        } else if (isZero()) {
            return 0.0d;
        } else {
            if (isInfinite()) {
                return negative() ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            }
            if (isNaN()) {
                return Double.NaN;
            }
            long exponent3 = (long) exponent();
            long significand2 = significand() >>> 12;
            if (exponent3 >= -1022) {
                exponent2 = exponent3 + 1023;
            } else {
                int adjustment = -1022 - exponent();
                significand2 = (significand2 >>> adjustment) | (1 << (52 - adjustment));
                exponent2 = 0;
            }
            return Double.longBitsToDouble(significand2 | (exponent2 << 52) | (negative() ? DOUBLE_SIGN_BIT : 0));
        }
    }

    public long asLong() {
        String representationError = longRepresentationError();
        if (representationError != null) {
            throw new IllegalArgumentException(representationError);
        } else if (isZero()) {
            return 0;
        } else {
            if (exponent() == 63) {
                return DOUBLE_SIGN_BIT;
            }
            long result = (significand() >>> ((63 - exponent()) + 1)) ^ (1 << exponent());
            if (negative()) {
                return -result;
            }
            return result;
        }
    }

    private static String doubleRepresentationError() {
        return null;
    }

    private String longRepresentationError() {
        if (isZero()) {
            return null;
        }
        if (isInfinite()) {
            return "Invalid encoded long " + this + ": Infinity is not a long";
        } else if (isNaN()) {
            return "Invalid encoded long " + this + ": NaN is not a long";
        } else if (exponent() == 63) {
            if (significand() == 0 && negative()) {
                return null;
            }
            return "Invalid encoded long " + this + ": overflow";
        } else if (exponent() < 0 || exponent() > 63) {
            return "Invalid encoded long " + this + ": exponent " + exponent() + " too large";
        } else {
            if (exponent() >= 64 - Long.numberOfTrailingZeros(significand())) {
                return null;
            }
            return "Invalid encoded long " + this + ": contains fractional part";
        }
    }
}
