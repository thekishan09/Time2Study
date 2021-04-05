package com.google.firebase.storage.internal;

/* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
public class SleeperImpl implements Sleeper {
    public void sleep(int milliseconds) throws InterruptedException {
        Thread.sleep((long) milliseconds);
    }
}
