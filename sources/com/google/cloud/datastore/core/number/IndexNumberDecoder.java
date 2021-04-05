package com.google.cloud.datastore.core.number;

public class IndexNumberDecoder {
    private String doubleResultRepProblemMessage;
    private String longResultRepProblemMessage;
    private double resultAsDouble;
    private long resultAsLong;
    private int resultExponent;
    private boolean resultNegative;
    private long resultSignificand;

    public IndexNumberDecoder() {
        reset();
    }

    public void reset() {
        this.longResultRepProblemMessage = "No bytes decoded.";
        this.doubleResultRepProblemMessage = "No bytes decoded.";
    }

    public boolean isResultLong() {
        updateResultLongState();
        return this.longResultRepProblemMessage.isEmpty();
    }

    public long resultAsLong() {
        updateResultLongState();
        if (this.longResultRepProblemMessage.isEmpty()) {
            return this.resultAsLong;
        }
        throw new IllegalArgumentException(this.longResultRepProblemMessage);
    }

    public boolean isResultDouble() {
        updateResultDoubleState();
        return this.doubleResultRepProblemMessage.isEmpty();
    }

    private void updateResultLongState() {
        if (this.longResultRepProblemMessage == null) {
            this.longResultRepProblemMessage = "";
            int i = this.resultExponent;
            if (i == Integer.MAX_VALUE) {
                if (this.resultSignificand != 0) {
                    this.longResultRepProblemMessage = "NaN is not an integer.";
                } else if (this.resultNegative) {
                    this.longResultRepProblemMessage = "+Infinity is not an integer.";
                } else {
                    this.longResultRepProblemMessage = "-Infinity is not an integer.";
                }
            } else if (i == Integer.MIN_VALUE && this.resultSignificand == 0) {
                this.resultAsLong = 0;
            } else {
                int i2 = this.resultExponent;
                if (i2 < 0) {
                    this.longResultRepProblemMessage = "Number is not an integer.";
                } else if (i2 >= 64) {
                    this.longResultRepProblemMessage = "Number is outside the long range.";
                } else if (i2 != 63) {
                    int numSignicandBits = 64 - Long.numberOfTrailingZeros(this.resultSignificand);
                    int i3 = this.resultExponent;
                    if (i3 < numSignicandBits) {
                        this.longResultRepProblemMessage = "Number is not an integer.";
                        return;
                    }
                    long longValue = (this.resultSignificand >>> ((63 - i3) + 1)) ^ (1 << i3);
                    if (this.resultNegative) {
                        longValue = -longValue;
                    }
                    this.resultAsLong = longValue;
                } else if (this.resultSignificand != 0 || !this.resultNegative) {
                    this.longResultRepProblemMessage = "Number is outside the long range.";
                } else {
                    this.resultAsLong = Long.MIN_VALUE;
                }
            }
        }
    }

    public double resultAsDouble() {
        updateResultDoubleState();
        if (this.doubleResultRepProblemMessage.isEmpty()) {
            return this.resultAsDouble;
        }
        throw new IllegalArgumentException(this.doubleResultRepProblemMessage);
    }

    private void updateResultDoubleState() {
        if (this.doubleResultRepProblemMessage == null) {
            this.doubleResultRepProblemMessage = "";
            int i = this.resultExponent;
            long j = 0;
            if (i == Integer.MAX_VALUE) {
                if (this.resultSignificand != 0) {
                    this.resultAsDouble = Double.NaN;
                } else if (this.resultNegative) {
                    this.resultAsDouble = Double.NEGATIVE_INFINITY;
                } else {
                    this.resultAsDouble = Double.POSITIVE_INFINITY;
                }
            } else if (i == Integer.MIN_VALUE && this.resultSignificand == 0) {
                this.resultAsDouble = 0.0d;
            } else if (64 - Long.numberOfTrailingZeros(this.resultSignificand) > 52) {
                this.doubleResultRepProblemMessage = "Number has too many significant bits for a double.";
            } else {
                long j2 = this.resultSignificand >>> 12;
                this.resultSignificand = j2;
                int i2 = this.resultExponent;
                if (i2 >= -1022) {
                    this.resultExponent = i2 + 1023;
                } else {
                    int adjustment = -1022 - i2;
                    long unadjustedSignificand = this.resultSignificand;
                    long j3 = j2 >>> adjustment;
                    this.resultSignificand = j3;
                    if ((j3 << adjustment) != unadjustedSignificand) {
                        this.doubleResultRepProblemMessage = "Number has too many significant bits for a subnormal double.";
                    }
                    this.resultSignificand |= 1 << (52 - adjustment);
                    this.resultExponent = 0;
                }
                long doubleValueAsLong = this.resultSignificand | (((long) this.resultExponent) << 52);
                if (this.resultNegative) {
                    j = Long.MIN_VALUE;
                }
                this.resultAsDouble = Double.longBitsToDouble(j | doubleValueAsLong);
            }
        }
    }

    public int decode(boolean descending, byte[] buffer, int offset) {
        int inverter;
        long significand;
        int bufferPos;
        int bufferPos2;
        int bufferPos3 = offset;
        int bufferPos4 = bufferPos3 + 1;
        int b = buffer[bufferPos3] & 255;
        int exponentInverter = 0;
        boolean invertEncoding = (b & 128) == 0;
        int inverter2 = invertEncoding ? 255 : 0;
        int b2 = b ^ inverter2;
        boolean negative = invertEncoding ^ descending;
        boolean exponentNegative = (b2 & 64) == 0;
        if (exponentNegative) {
            exponentInverter = 255;
        }
        long significand2 = 0;
        int writeBit = 64;
        int marker = decodeMarker(b2 ^ exponentInverter);
        if (marker == -4) {
            inverter = inverter2;
            if (!exponentNegative) {
                bufferPos = 0;
            } else {
                throw new IllegalArgumentException("Invalid encoded number: exponent negative zero is invalid");
            }
        } else if (marker == -3 || marker == -2 || marker == -1) {
            inverter = inverter2;
            bufferPos = marker + 4;
            writeBit = 64 - bufferPos;
            boolean z = invertEncoding;
            significand2 = 0 | (((long) (b2 & (((-1 << (bufferPos + 1)) ^ -1) & 126))) << (writeBit - 1));
        } else if (marker == 1) {
            inverter = inverter2;
            bufferPos = ((b2 ^ exponentInverter) & 15) + 4;
            b2 = (buffer[bufferPos4] & 255) ^ inverter;
            writeBit = 64 - 7;
            significand2 = 0 | decodeTrailingSignificandByte(b2, writeBit);
            boolean z2 = invertEncoding;
            bufferPos4++;
        } else if (marker == 2) {
            inverter = inverter2;
            int bufferPos5 = bufferPos4 + 1;
            int b3 = (buffer[bufferPos4] & 255) ^ inverter;
            int i = (b3 ^ exponentInverter) >>> 4;
            int writeBit2 = 64 - 4;
            b2 = (buffer[bufferPos5] & 255) ^ inverter;
            writeBit = writeBit2 - 7;
            significand2 = 0 | ((((long) b3) & 15) << writeBit2) | decodeTrailingSignificandByte(b2, writeBit);
            boolean z3 = invertEncoding;
            bufferPos = (i | (((b2 ^ exponentInverter) & 7) << 4)) + 20;
            bufferPos4 = bufferPos5 + 1;
        } else if (marker == 3) {
            inverter = inverter2;
            int bufferPos6 = bufferPos4 + 1;
            int i2 = ((buffer[bufferPos4] & 255) ^ inverter) ^ exponentInverter;
            b2 = (buffer[bufferPos6] & 255) ^ inverter;
            writeBit = 64 - 7;
            significand2 = 0 | decodeTrailingSignificandByte(b2, writeBit);
            boolean z4 = invertEncoding;
            bufferPos = (i2 | ((3 & (b2 ^ exponentInverter)) << 8)) + 148;
            bufferPos4 = bufferPos6 + 1;
        } else if (marker == 6) {
            int i3 = inverter2;
            if (invertEncoding) {
                if (exponentNegative) {
                    recordNumber(negative, Integer.MIN_VALUE, 0);
                } else {
                    int bufferPos7 = bufferPos4 + 1;
                    int b4 = buffer[bufferPos4] & 255;
                    if (b4 == 128) {
                        recordNumber(negative, Integer.MAX_VALUE, 0);
                    } else if (b4 == 96) {
                        recordNumber(negative, Integer.MAX_VALUE, 1);
                    } else {
                        throw new IllegalArgumentException("Invalid encoded byte array");
                    }
                    bufferPos4 = bufferPos7;
                }
            } else if (exponentNegative) {
                recordNumber(negative, Integer.MIN_VALUE, 0);
            } else {
                recordNumber(negative, Integer.MAX_VALUE, 0);
            }
            return bufferPos4 - offset;
        } else {
            throw new IllegalArgumentException("Invalid encoded byte array");
        }
        while ((b2 & 1) != 0) {
            int bufferPos8 = bufferPos2 + 1;
            b2 = (buffer[bufferPos2] & 255) ^ inverter;
            int writeBit3 = writeBit3 - 7;
            if (writeBit3 >= 0) {
                significand |= decodeTrailingSignificandByte(b2, writeBit3);
                bufferPos2 = bufferPos8;
            } else {
                significand |= ((long) (b2 & 254)) >>> (-(writeBit3 - 1));
                writeBit3 = 0;
                if ((b2 & 1) == 0) {
                    bufferPos2 = bufferPos8;
                } else {
                    throw new IllegalArgumentException("Invalid encoded byte array: overlong sequence");
                }
            }
        }
        if (exponentNegative) {
            bufferPos = -bufferPos;
        }
        recordNumber(negative, bufferPos, significand);
        return bufferPos2 - offset;
    }

    private void recordNumber(boolean negative, int exponent, long significand) {
        this.longResultRepProblemMessage = null;
        this.doubleResultRepProblemMessage = null;
        this.resultNegative = negative;
        this.resultExponent = exponent;
        this.resultSignificand = significand;
    }

    static int decodeMarker(int byteValue) {
        boolean leadingOne = (byteValue & 32) != 0;
        if (leadingOne) {
            byteValue ^= 255;
        }
        int leader = 5 - (31 - Integer.numberOfLeadingZeros(byteValue & 63));
        return leadingOne ? leader : -leader;
    }

    private static long decodeTrailingSignificandByte(int value, int position) {
        return ((long) (value & 254)) << (position - 1);
    }
}
