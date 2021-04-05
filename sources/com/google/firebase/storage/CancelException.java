package com.google.firebase.storage;

import java.io.IOException;

/* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
class CancelException extends IOException {
    CancelException() {
        super("The operation was canceled.");
    }
}
