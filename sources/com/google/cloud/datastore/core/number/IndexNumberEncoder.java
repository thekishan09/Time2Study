package com.google.cloud.datastore.core.number;

import com.google.common.primitives.UnsignedBytes;

public class IndexNumberEncoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int DOUBLE_EXPONENT_BIAS = 1023;
    static final int DOUBLE_MIN_EXPONENT = -1022;
    static final int DOUBLE_SIGNIFICAND_BITS = 52;
    static final long DOUBLE_SIGN_BIT = Long.MIN_VALUE;
    static final int EXP1_END = 4;
    static final int EXP2_END = 20;
    static final int EXP3_END = 148;
    static final int EXP4_END = 1172;
    public static final int MAX_ENCODED_BYTES = 11;
    static final int NEGATIVE_INFINITE_EXPONENT = Integer.MIN_VALUE;
    static final int POSITIVE_INFINITE_EXPONENT = Integer.MAX_VALUE;
    static final int SIGNIFICAND_BITS = 64;

    private IndexNumberEncoder() {
    }

    public static int encodeLong(boolean descending, long value, byte[] buffer, int offset) {
        boolean negative;
        if (value == 0) {
            return encodeZero(buffer, offset);
        }
        boolean negative2 = descending;
        if (value < 0) {
            value = -value;
            negative = !negative2;
        } else {
            negative = negative2;
        }
        int leadingZeros = Long.numberOfLeadingZeros(value);
        int exponent = 63 - leadingZeros;
        return encodeNumber(negative, exponent, (((1 << exponent) ^ -1) & value) << (leadingZeros + 1), buffer, offset);
    }

    public static int encodeDouble(boolean descending, double value, byte[] buffer, int offset) {
        long significand;
        int exponent;
        if (value == 0.0d) {
            return encodeZero(buffer, offset);
        }
        long doubleBits = Double.doubleToLongBits(value);
        boolean invertEncoding = (value < 0.0d) ^ descending;
        int exponent2 = ((int) ((doubleBits >>> 52) & 2047)) - DOUBLE_EXPONENT_BIAS;
        long significand2 = 4503599627370495L & doubleBits;
        if (exponent2 < DOUBLE_MIN_EXPONENT) {
            int leadingZeros = Long.numberOfLeadingZeros(significand2);
            exponent = exponent2 - (leadingZeros - 12);
            significand = (significand2 & ((1 << (63 - leadingZeros)) ^ -1)) << (leadingZeros + 1);
        } else if (exponent2 <= DOUBLE_EXPONENT_BIAS) {
            exponent = exponent2;
            significand = significand2 << 12;
        } else if (significand2 != 0) {
            int offset2 = offset + 1;
            buffer[offset] = 0;
            int i = offset2 + 1;
            buffer[offset2] = 96;
            return 2;
        } else if (invertEncoding) {
            int offset3 = offset + 1;
            buffer[offset] = 0;
            int i2 = offset3 + 1;
            buffer[offset3] = UnsignedBytes.MAX_POWER_OF_TWO;
            return 2;
        } else {
            int i3 = offset + 1;
            buffer[offset] = -1;
            return 1;
        }
        return encodeNumber(invertEncoding, exponent, significand, buffer, offset);
    }

    private static int encodeZero(byte[] buffer, int offset) {
        buffer[offset] = UnsignedBytes.MAX_POWER_OF_TWO;
        return 1;
    }

    private static int encodeNumber(boolean invertEncoding, int exponent, long significand, byte[] buffer, int offset) {
        long significand2;
        int lastByte;
        int lastByte2 = offset;
        int inverter = invertEncoding ? 255 : 0;
        int exponentMask = 0;
        if (exponent < 0) {
            exponent = -exponent;
            exponentMask = 255;
        }
        if (exponent < 4) {
            int significandStart = exponent + 1;
            lastByte = 192 | (1 << significandStart) | (((int) (significand >>> (64 - significandStart))) & ((1 << significandStart) - 2));
            significand2 = significand << exponent;
            if (exponentMask != 0) {
                lastByte ^= (-1 << significandStart) & 126;
            }
        } else if (exponent < 20) {
            buffer[lastByte2] = (byte) ((224 | (exponent - 4)) ^ ((exponentMask & 127) ^ inverter));
            lastByte = topSignificandByte(significand);
            significand2 = significand << 7;
            lastByte2++;
        } else if (exponent < EXP3_END) {
            int exponent2 = exponent - 20;
            int bufferPos = lastByte2 + 1;
            buffer[lastByte2] = (byte) ((240 | (exponent2 >>> 4)) ^ ((exponentMask & 127) ^ inverter));
            long significand3 = significand << 4;
            buffer[bufferPos] = (byte) ((((exponent2 << 4) & 240) | ((int) (significand >>> 60))) ^ ((exponentMask & 240) ^ inverter));
            int lastByte3 = topSignificandByte(significand3);
            significand2 = significand3 << 7;
            lastByte = lastByte3;
            lastByte2 = bufferPos + 1;
        } else if (exponent < EXP4_END) {
            int exponent3 = exponent - 148;
            int bufferPos2 = lastByte2 + 1;
            buffer[lastByte2] = (byte) ((248 | (exponent3 >>> 8)) ^ ((exponentMask & 127) ^ inverter));
            buffer[bufferPos2] = (byte) ((exponent3 & 255) ^ ((exponentMask & 255) ^ inverter));
            int lastByte4 = topSignificandByte(significand);
            significand2 = significand << 7;
            lastByte = lastByte4;
            lastByte2 = bufferPos2 + 1;
        } else {
            throw new IllegalStateException("unimplemented");
        }
        while (significand2 != 0) {
            buffer[lastByte2] = (byte) ((lastByte | 1) ^ inverter);
            lastByte = topSignificandByte(significand2);
            significand2 <<= 7;
            lastByte2++;
        }
        buffer[lastByte2] = (byte) (lastByte ^ inverter);
        return (lastByte2 + 1) - offset;
    }

    private static int topSignificandByte(long significand) {
        return ((int) (significand >>> 56)) & 254;
    }
}
