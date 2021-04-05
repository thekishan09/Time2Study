package com.google.firebase.database;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class DatabaseError {
    public static final int DATA_STALE = -1;
    public static final int DISCONNECTED = -4;
    public static final int EXPIRED_TOKEN = -6;
    public static final int INVALID_TOKEN = -7;
    public static final int MAX_RETRIES = -8;
    public static final int NETWORK_ERROR = -24;
    public static final int OPERATION_FAILED = -2;
    public static final int OVERRIDDEN_BY_SET = -9;
    public static final int PERMISSION_DENIED = -3;
    public static final int UNAVAILABLE = -10;
    public static final int UNKNOWN_ERROR = -999;
    public static final int USER_CODE_EXCEPTION = -11;
    public static final int WRITE_CANCELED = -25;
    private static final Map<String, Integer> errorCodes;
    private static final Map<Integer, String> errorReasons;
    private final int code;
    private final String details;
    private final String message;

    static {
        HashMap hashMap = new HashMap();
        errorReasons = hashMap;
        hashMap.put(-1, "The transaction needs to be run again with current data");
        errorReasons.put(-2, "The server indicated that this operation failed");
        errorReasons.put(-3, "This client does not have permission to perform this operation");
        errorReasons.put(-4, "The operation had to be aborted due to a network disconnect");
        errorReasons.put(-6, "The supplied auth token has expired");
        errorReasons.put(-7, "The supplied auth token was invalid");
        errorReasons.put(-8, "The transaction had too many retries");
        errorReasons.put(-9, "The transaction was overridden by a subsequent set");
        errorReasons.put(-10, "The service is unavailable");
        errorReasons.put(-11, "User code called from the Firebase Database runloop threw an exception:\n");
        errorReasons.put(-24, "The operation could not be performed due to a network error");
        errorReasons.put(-25, "The write was canceled by the user.");
        errorReasons.put(Integer.valueOf(UNKNOWN_ERROR), "An unknown error occurred");
        HashMap hashMap2 = new HashMap();
        errorCodes = hashMap2;
        hashMap2.put("datastale", -1);
        errorCodes.put("failure", -2);
        errorCodes.put("permission_denied", -3);
        errorCodes.put("disconnected", -4);
        errorCodes.put("expired_token", -6);
        errorCodes.put("invalid_token", -7);
        errorCodes.put("maxretries", -8);
        errorCodes.put("overriddenbyset", -9);
        errorCodes.put("unavailable", -10);
        errorCodes.put("network_error", -24);
        errorCodes.put("write_canceled", -25);
    }

    public static DatabaseError fromStatus(String status) {
        return fromStatus(status, (String) null);
    }

    public static DatabaseError fromStatus(String status, String reason) {
        return fromStatus(status, reason, (String) null);
    }

    public static DatabaseError fromCode(int code2) {
        if (errorReasons.containsKey(Integer.valueOf(code2))) {
            return new DatabaseError(code2, errorReasons.get(Integer.valueOf(code2)), (String) null);
        }
        throw new IllegalArgumentException("Invalid Firebase Database error code: " + code2);
    }

    public static DatabaseError fromStatus(String status, String reason, String details2) {
        Integer code2 = errorCodes.get(status.toLowerCase());
        if (code2 == null) {
            code2 = Integer.valueOf(UNKNOWN_ERROR);
        }
        return new DatabaseError(code2.intValue(), reason == null ? errorReasons.get(code2) : reason, details2);
    }

    public static DatabaseError fromException(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return new DatabaseError(-11, errorReasons.get(-11) + stringWriter.toString());
    }

    private DatabaseError(int code2, String message2) {
        this(code2, message2, (String) null);
    }

    private DatabaseError(int code2, String message2, String details2) {
        this.code = code2;
        this.message = message2;
        this.details = details2 == null ? "" : details2;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getDetails() {
        return this.details;
    }

    public String toString() {
        return "DatabaseError: " + this.message;
    }

    public DatabaseException toException() {
        return new DatabaseException("Firebase Database error: " + this.message);
    }
}
