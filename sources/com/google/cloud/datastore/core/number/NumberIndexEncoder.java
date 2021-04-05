package com.google.cloud.datastore.core.number;

import com.google.common.primitives.UnsignedBytes;
import java.util.Arrays;

public class NumberIndexEncoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final byte[] ENCODED_NAN = {0, 96};
    private static final byte[] ENCODED_NEGATIVE_INFINITY = {0, UnsignedBytes.MAX_POWER_OF_TWO};
    private static final byte[] ENCODED_POSITIVE_INFINITY = {-1};
    private static final byte[] ENCODED_ZERO = {UnsignedBytes.MAX_POWER_OF_TWO};
    private static final int EXP1_END = 4;
    private static final int EXP2_END = 20;
    private static final int EXP3_END = 148;
    private static final int EXP4_END = 1172;
    private static final int MAX_ENCODED_BYTES = 11;

    public static byte[] encodeDouble(double value) {
        return encode(NumberParts.fromDouble(value));
    }

    public static byte[] encodeLong(long value) {
        return encode(NumberParts.fromLong(value));
    }

    public static byte[] encode(NumberParts parts) {
        int lastByte;
        long significand;
        if (parts.isZero()) {
            return copyOf(ENCODED_ZERO);
        }
        if (parts.isNaN()) {
            return copyOf(ENCODED_NAN);
        }
        if (!parts.isInfinite()) {
            int exponent = parts.exponent();
            long significand2 = parts.significand();
            byte[] buffer = new byte[11];
            int lastByte2 = 0;
            int inverter = parts.negative() ? 255 : 0;
            int exponentMask = 0;
            if (exponent < 0) {
                exponent = -exponent;
                exponentMask = 255;
            }
            if (exponent < 4) {
                int significandStart = exponent + 1;
                lastByte = 192 | (1 << significandStart) | (((int) (significand2 >>> (64 - significandStart))) & ((1 << significandStart) - 2));
                significand = significand2 << exponent;
                if (exponentMask != 0) {
                    lastByte ^= (-1 << significandStart) & 126;
                }
            } else if (exponent < 20) {
                buffer[0] = (byte) ((224 | (exponent - 4)) ^ ((exponentMask & 127) ^ inverter));
                lastByte = topSignificandByte(significand2);
                significand = significand2 << 7;
                lastByte2 = 0 + 1;
            } else if (exponent < EXP3_END) {
                int exponent2 = exponent - 20;
                int bufferPos = 0 + 1;
                buffer[0] = (byte) ((240 | (exponent2 >>> 4)) ^ ((exponentMask & 127) ^ inverter));
                long significand3 = significand2 << 4;
                buffer[bufferPos] = (byte) ((((exponent2 << 4) & 240) | ((int) (significand2 >>> 60))) ^ ((exponentMask & 240) ^ inverter));
                int lastByte3 = topSignificandByte(significand3);
                significand = significand3 << 7;
                lastByte = lastByte3;
                lastByte2 = bufferPos + 1;
            } else if (exponent < EXP4_END) {
                int exponent3 = exponent - 148;
                int bufferPos2 = 0 + 1;
                buffer[0] = (byte) ((248 | (exponent3 >>> 8)) ^ ((exponentMask & 127) ^ inverter));
                buffer[bufferPos2] = (byte) ((exponent3 & 255) ^ ((exponentMask & 255) ^ inverter));
                int lastByte4 = topSignificandByte(significand2);
                significand = significand2 << 7;
                lastByte = lastByte4;
                lastByte2 = bufferPos2 + 1;
            } else {
                throw new IllegalStateException("unimplemented");
            }
            while (significand != 0) {
                buffer[lastByte2] = (byte) ((lastByte | 1) ^ inverter);
                lastByte = topSignificandByte(significand);
                significand <<= 7;
                lastByte2++;
            }
            buffer[lastByte2] = (byte) (lastByte ^ inverter);
            return Arrays.copyOf(buffer, lastByte2 + 1);
        } else if (parts.negative()) {
            return copyOf(ENCODED_NEGATIVE_INFINITY);
        } else {
            return copyOf(ENCODED_POSITIVE_INFINITY);
        }
    }

    public static double decodeDouble(byte[] bytes) {
        return decode(bytes).parts().asDouble();
    }

    public static long decodeLong(byte[] bytes) {
        return decode(bytes).parts().asLong();
    }

    public static final class DecodedNumberParts {
        private final int bytesRead;
        private final NumberParts parts;

        private DecodedNumberParts(int bytesRead2, NumberParts parts2) {
            this.bytesRead = bytesRead2;
            this.parts = parts2;
        }

        public int bytesRead() {
            return this.bytesRead;
        }

        public NumberParts parts() {
            return this.parts;
        }

        static DecodedNumberParts create(int bytesRead2, NumberParts parts2) {
            return new DecodedNumberParts(bytesRead2, parts2);
        }
    }

    public static DecodedNumberParts decode(byte[] bytes) {
        int inverter;
        long significand;
        int bufferPos;
        int exponent;
        int b;
        int exponent2;
        NumberParts parts;
        byte[] bArr = bytes;
        if (bArr.length >= 1) {
            int bufferPos2 = 0 + 1;
            int b2 = bArr[0] & 255;
            boolean negative = (b2 & 128) == 0;
            int inverter2 = negative ? 255 : 0;
            int b3 = b2 ^ inverter2;
            boolean exponentNegative = (b3 & 64) == 0;
            int exponentInverter = exponentNegative ? 255 : 0;
            long significand2 = 0;
            int writeBit = 64;
            int marker = decodeMarker(b3 ^ exponentInverter);
            if (marker == -4) {
                int b4 = b3;
                int bufferPos3 = bufferPos2;
                inverter = inverter2;
                if (!exponentNegative) {
                    bufferPos = 0;
                    exponent = bufferPos3;
                    b = b4;
                } else {
                    throw new IllegalArgumentException("Invalid encoded number " + Arrays.toString(bytes) + ": exponent negative zero is invalid");
                }
            } else if (marker == -3 || marker == -2 || marker == -1) {
                inverter = inverter2;
                bufferPos = marker + 4;
                writeBit = 64 - bufferPos;
                significand2 = 0 | (((long) (b3 & ((-1 ^ (-1 << (bufferPos + 1))) & 126))) << (writeBit - 1));
                exponent = bufferPos2;
                b = b3;
            } else if (marker == 1) {
                inverter = inverter2;
                if (bArr.length >= 2) {
                    bufferPos = ((b3 ^ exponentInverter) & 15) + 4;
                    b = (bArr[bufferPos2] & 255) ^ inverter;
                    writeBit = 64 - 7;
                    significand2 = 0 | decodeTrailingSignificandByte(b, writeBit);
                    exponent = bufferPos2 + 1;
                } else {
                    throw new IllegalArgumentException("Invalid encoded byte array");
                }
            } else if (marker == 2) {
                inverter = inverter2;
                if (bArr.length >= 3) {
                    int bufferPos4 = bufferPos2 + 1;
                    int b5 = (bArr[bufferPos2] & 255) ^ inverter;
                    int i = (b5 ^ exponentInverter) >>> 4;
                    int writeBit2 = 64 - 4;
                    b = (bArr[bufferPos4] & 255) ^ inverter;
                    writeBit = writeBit2 - 7;
                    significand2 = decodeTrailingSignificandByte(b, writeBit) | ((((long) b5) & 15) << writeBit2) | 0;
                    bufferPos = (i | (((b3 ^ exponentInverter) & 7) << 4)) + 20;
                    exponent = bufferPos4 + 1;
                } else {
                    throw new IllegalArgumentException("Invalid encoded byte array");
                }
            } else if (marker == 3) {
                inverter = inverter2;
                if (bArr.length >= 3) {
                    int bufferPos5 = bufferPos2 + 1;
                    int exponent3 = ((((bArr[bufferPos2] & 255) ^ inverter) ^ exponentInverter) | (((b3 ^ exponentInverter) & 3) << 8)) + EXP3_END;
                    b = (bArr[bufferPos5] & 255) ^ inverter;
                    writeBit = 64 - 7;
                    significand2 = 0 | decodeTrailingSignificandByte(b, writeBit);
                    bufferPos = exponent3;
                    exponent = bufferPos5 + 1;
                } else {
                    throw new IllegalArgumentException("Invalid encoded byte array");
                }
            } else if (marker == 6) {
                int i2 = inverter2;
                if (negative) {
                    if (exponentNegative) {
                        parts = NumberParts.create(true, Integer.MIN_VALUE, 0);
                    } else if (bArr.length >= 2) {
                        int bufferPos6 = bufferPos2 + 1;
                        int b6 = bArr[bufferPos2] & 255;
                        if (b6 == 128) {
                            parts = NumberParts.create(true, Integer.MAX_VALUE, 0);
                            bufferPos2 = bufferPos6;
                        } else if (b6 == 96) {
                            parts = NumberParts.create(true, Integer.MAX_VALUE, 1);
                            bufferPos2 = bufferPos6;
                        } else {
                            throw new IllegalArgumentException("Invalid encoded byte array");
                        }
                    } else {
                        throw new IllegalArgumentException("Invalid encoded byte array");
                    }
                } else if (exponentNegative) {
                    parts = NumberParts.create(false, Integer.MIN_VALUE, 0);
                } else {
                    parts = NumberParts.create(false, Integer.MAX_VALUE, 0);
                }
                return DecodedNumberParts.create(bufferPos2, parts);
            } else {
                throw new IllegalArgumentException("Invalid encoded byte array");
            }
            while ((b & 1) != 0) {
                if (exponent < bArr.length) {
                    int bufferPos7 = exponent + 1;
                    b = (bArr[exponent] & 255) ^ inverter;
                    int writeBit3 = writeBit3 - 7;
                    if (writeBit3 >= 0) {
                        significand |= decodeTrailingSignificandByte(b, writeBit3);
                        exponent2 = bufferPos7;
                    } else {
                        significand |= ((long) (b & 254)) >>> (-(writeBit3 - 1));
                        writeBit3 = 0;
                        if ((b & 1) == 0) {
                            exponent2 = bufferPos7;
                        } else {
                            throw new IllegalArgumentException("Invalid encoded byte array: overlong sequence");
                        }
                    }
                } else {
                    throw new IllegalArgumentException("Invalid encoded byte array");
                }
            }
            if (exponentNegative) {
                bufferPos = -bufferPos;
            }
            return DecodedNumberParts.create(exponent, NumberParts.create(negative, bufferPos, significand));
        }
        throw new IllegalArgumentException("Invalid encoded byte array");
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

    private static int topSignificandByte(long significand) {
        return ((int) (significand >>> 56)) & 254;
    }

    private static byte[] copyOf(byte[] value) {
        return (byte[]) value.clone();
    }
}
