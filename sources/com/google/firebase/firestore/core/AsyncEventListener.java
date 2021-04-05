package com.google.firebase.firestore.core;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import java.util.concurrent.Executor;

public class AsyncEventListener<T> implements EventListener<T> {
    private final EventListener<T> eventListener;
    private final Executor executor;
    private volatile boolean muted = false;

    public AsyncEventListener(Executor executor2, EventListener<T> eventListener2) {
        this.executor = executor2;
        this.eventListener = eventListener2;
    }

    public void onEvent(T value, FirebaseFirestoreException error) {
        this.executor.execute(AsyncEventListener$$Lambda$1.lambdaFactory$(this, value, error));
    }

    static /* synthetic */ void lambda$onEvent$0(AsyncEventListener asyncEventListener, Object value, FirebaseFirestoreException error) {
        if (!asyncEventListener.muted) {
            asyncEventListener.eventListener.onEvent(value, error);
        }
    }

    public void mute() {
        this.muted = true;
    }
}
