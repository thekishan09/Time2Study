package com.google.common.hash;

import com.google.common.base.Ascii;
import com.google.common.primitives.Longs;
import com.google.common.primitives.UnsignedBytes;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import sun.misc.Unsafe;

final class LittleEndianByteArray {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final LittleEndianBytes byteArray;

    private enum JavaLittleEndianBytes implements LittleEndianBytes {
        INSTANCE {
            public long getLongLittleEndian(byte[] source, int offset) {
                return Longs.fromBytes(source[offset + 7], source[offset + 6], source[offset + 5], source[offset + 4], source[offset + 3], source[offset + 2], source[offset + 1], source[offset]);
            }

            public void putLongLittleEndian(byte[] sink, int offset, long value) {
                long mask = 255;
                for (int i = 0; i < 8; i++) {
                    sink[offset + i] = (byte) ((int) ((value & mask) >> (i * 8)));
                    mask <<= 8;
                }
            }
        }
    }

    private interface LittleEndianBytes {
        long getLongLittleEndian(byte[] bArr, int i);

        void putLongLittleEndian(byte[] bArr, int i, long j);
    }

    static {
        LittleEndianBytes theGetter = JavaLittleEndianBytes.INSTANCE;
        try {
            if ("amd64".equals(System.getProperty("os.arch"))) {
                theGetter = ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN) ? UnsafeByteArray.UNSAFE_LITTLE_ENDIAN : UnsafeByteArray.UNSAFE_BIG_ENDIAN;
            }
        } catch (Throwable th) {
        }
        byteArray = theGetter;
    }

    static long load64(byte[] input, int offset) {
        return byteArray.getLongLittleEndian(input, offset);
    }

    static long load64Safely(byte[] input, int offset, int length) {
        long result = 0;
        int limit = Math.min(length, 8);
        for (int i = 0; i < limit; i++) {
            result |= (((long) input[offset + i]) & 255) << (i * 8);
        }
        return result;
    }

    static void store64(byte[] sink, int offset, long value) {
        byteArray.putLongLittleEndian(sink, offset, value);
    }

    static int load32(byte[] source, int offset) {
        return (source[offset] & UnsignedBytes.MAX_VALUE) | ((source[offset + 1] & UnsignedBytes.MAX_VALUE) << 8) | ((source[offset + 2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((source[offset + 3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN);
    }

    static boolean usingUnsafe() {
        return byteArray instanceof UnsafeByteArray;
    }

    private enum UnsafeByteArray implements LittleEndianBytes {
        UNSAFE_LITTLE_ENDIAN {
            public long getLongLittleEndian(byte[] array, int offset) {
                return UnsafeByteArray.theUnsafe.getLong(array, ((long) offset) + ((long) UnsafeByteArray.BYTE_ARRAY_BASE_OFFSET));
            }

            public void putLongLittleEndian(byte[] array, int offset, long value) {
                UnsafeByteArray.theUnsafe.putLong(array, ((long) UnsafeByteArray.BYTE_ARRAY_BASE_OFFSET) + ((long) offset), value);
            }
        },
        UNSAFE_BIG_ENDIAN {
            public long getLongLittleEndian(byte[] array, int offset) {
                return Long.reverseBytes(UnsafeByteArray.theUnsafe.getLong(array, ((long) offset) + ((long) UnsafeByteArray.BYTE_ARRAY_BASE_OFFSET)));
            }

            public void putLongLittleEndian(byte[] array, int offset, long value) {
                long littleEndianValue = Long.reverseBytes(value);
                UnsafeByteArray.theUnsafe.putLong(array, ((long) UnsafeByteArray.BYTE_ARRAY_BASE_OFFSET) + ((long) offset), littleEndianValue);
            }
        };
        
        /* access modifiers changed from: private */
        public static final int BYTE_ARRAY_BASE_OFFSET = 0;
        /* access modifiers changed from: private */
        public static final Unsafe theUnsafe = null;

        static {
            Class<byte[]> cls;
            Unsafe unsafe = getUnsafe();
            theUnsafe = unsafe;
            BYTE_ARRAY_BASE_OFFSET = unsafe.arrayBaseOffset(cls);
            if (theUnsafe.arrayIndexScale(cls) != 1) {
                throw new AssertionError();
            }
        }

        private static Unsafe getUnsafe() {
            try {
                return Unsafe.getUnsafe();
            } catch (SecurityException e) {
                try {
                    return (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() {
                        public Unsafe run() throws Exception {
                            Class<Unsafe> k = Unsafe.class;
                            for (Field f : k.getDeclaredFields()) {
                                f.setAccessible(true);
                                Object x = f.get((Object) null);
                                if (k.isInstance(x)) {
                                    return k.cast(x);
                                }
                            }
                            throw new NoSuchFieldError("the Unsafe");
                        }
                    });
                } catch (PrivilegedActionException e2) {
                    throw new RuntimeException("Could not initialize intrinsics", e2.getCause());
                }
            }
        }
    }

    private LittleEndianByteArray() {
    }
}
