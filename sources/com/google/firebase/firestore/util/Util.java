package com.google.firebase.firestore.util;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.cloud.datastore.core.number.NumberComparisonHelper;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.protobuf.ByteString;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import p003io.grpc.Status;
import p003io.grpc.StatusException;
import p003io.grpc.StatusRuntimeException;

public class Util {
    private static final String AUTO_ID_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int AUTO_ID_LENGTH = 20;
    private static final Comparator COMPARABLE_COMPARATOR = new Comparator<Comparable<?>>() {
        public int compare(Comparable left, Comparable right) {
            return left.compareTo(right);
        }
    };
    private static final Continuation<Void, Void> VOID_ERROR_TRANSFORMER = Util$$Lambda$2.lambdaFactory$();
    private static final Random rand = new SecureRandom();

    public static String autoId() {
        StringBuilder builder = new StringBuilder();
        int maxRandom = AUTO_ID_ALPHABET.length();
        for (int i = 0; i < 20; i++) {
            builder.append(AUTO_ID_ALPHABET.charAt(rand.nextInt(maxRandom)));
        }
        return builder.toString();
    }

    public static int compareBooleans(boolean b1, boolean b2) {
        if (b1 == b2) {
            return 0;
        }
        if (b1) {
            return 1;
        }
        return -1;
    }

    public static int compareIntegers(int i1, int i2) {
        if (i1 < i2) {
            return -1;
        }
        if (i1 > i2) {
            return 1;
        }
        return 0;
    }

    public static int compareLongs(long i1, long i2) {
        return NumberComparisonHelper.compareLongs(i1, i2);
    }

    public static int compareDoubles(double i1, double i2) {
        return NumberComparisonHelper.firestoreCompareDoubles(i1, i2);
    }

    public static int compareMixed(double doubleValue, long longValue) {
        return NumberComparisonHelper.firestoreCompareDoubleWithLong(doubleValue, longValue);
    }

    public static <T extends Comparable<T>> Comparator<T> comparator() {
        return COMPARABLE_COMPARATOR;
    }

    public static FirebaseFirestoreException exceptionFromStatus(Status error) {
        StatusException statusException = error.asException();
        return new FirebaseFirestoreException(statusException.getMessage(), FirebaseFirestoreException.Code.fromValue(error.getCode().value()), statusException);
    }

    private static Exception convertStatusException(Exception e) {
        if (e instanceof StatusException) {
            return exceptionFromStatus(((StatusException) e).getStatus());
        }
        if (e instanceof StatusRuntimeException) {
            return exceptionFromStatus(((StatusRuntimeException) e).getStatus());
        }
        return e;
    }

    public static Exception convertThrowableToException(Throwable t) {
        if (t instanceof Exception) {
            return convertStatusException((Exception) t);
        }
        return new Exception(t);
    }

    static /* synthetic */ Void lambda$static$0(Task task) throws Exception {
        if (task.isSuccessful()) {
            return (Void) task.getResult();
        }
        Exception e = convertStatusException(task.getException());
        if (e instanceof FirebaseFirestoreException) {
            throw e;
        }
        throw new FirebaseFirestoreException(e.getMessage(), FirebaseFirestoreException.Code.UNKNOWN, e);
    }

    public static Continuation<Void, Void> voidErrorTransformer() {
        return VOID_ERROR_TRANSFORMER;
    }

    public static List<Object> collectUpdateArguments(int fieldPathOffset, Object field, Object val, Object... fieldsAndValues) {
        if (fieldsAndValues.length % 2 != 1) {
            List<Object> argumentList = new ArrayList<>();
            argumentList.add(field);
            argumentList.add(val);
            Collections.addAll(argumentList, fieldsAndValues);
            int i = 0;
            while (i < argumentList.size()) {
                Object fieldPath = argumentList.get(i);
                if ((fieldPath instanceof String) || (fieldPath instanceof FieldPath)) {
                    i += 2;
                } else {
                    throw new IllegalArgumentException("Excepted field name at argument position " + (i + fieldPathOffset + 1) + " but got " + fieldPath + " in call to update.  The arguments to update should alternate between field names and values");
                }
            }
            return argumentList;
        }
        throw new IllegalArgumentException("Missing value in call to update().  There must be an even number of arguments that alternate between field names and values");
    }

    public static String toDebugString(ByteString bytes) {
        int size = bytes.size();
        StringBuilder result = new StringBuilder(size * 2);
        for (int i = 0; i < size; i++) {
            int value = bytes.byteAt(i) & 255;
            result.append(Character.forDigit(value >>> 4, 16));
            result.append(Character.forDigit(value & 15, 16));
        }
        return result.toString();
    }

    public static String typeName(Object obj) {
        return obj == null ? "null" : obj.getClass().getName();
    }

    public static void crashMainThread(RuntimeException exception) {
        new Handler(Looper.getMainLooper()).post(Util$$Lambda$1.lambdaFactory$(exception));
    }

    static /* synthetic */ void lambda$crashMainThread$1(RuntimeException exception) {
        throw exception;
    }

    public static int compareByteStrings(ByteString left, ByteString right) {
        int size = Math.min(left.size(), right.size());
        for (int i = 0; i < size; i++) {
            int thisByte = left.byteAt(i) & 255;
            int otherByte = right.byteAt(i) & 255;
            if (thisByte < otherByte) {
                return -1;
            }
            if (thisByte > otherByte) {
                return 1;
            }
        }
        return compareIntegers(left.size(), right.size());
    }
}
