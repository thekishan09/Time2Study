package com.google.firebase.firestore.util;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

class ThrottledForwardingExecutor implements Executor {
    private final Semaphore availableSlots;
    private final Executor executor;

    ThrottledForwardingExecutor(int maximumConcurrency, Executor executor2) {
        this.availableSlots = new Semaphore(maximumConcurrency);
        this.executor = executor2;
    }

    public void execute(Runnable command) {
        if (this.availableSlots.tryAcquire()) {
            try {
                this.executor.execute(ThrottledForwardingExecutor$$Lambda$1.lambdaFactory$(this, command));
            } catch (RejectedExecutionException e) {
                command.run();
            }
        } else {
            command.run();
        }
    }

    static /* synthetic */ void lambda$execute$0(ThrottledForwardingExecutor throttledForwardingExecutor, Runnable command) {
        command.run();
        throttledForwardingExecutor.availableSlots.release();
    }
}
