package com.google.firebase.firestore.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;

public class BackgroundQueue implements Executor {
    private Semaphore completedTasks = new Semaphore(0);
    private int pendingTaskCount = 0;

    public void execute(Runnable task) {
        this.pendingTaskCount++;
        Executors.BACKGROUND_EXECUTOR.execute(BackgroundQueue$$Lambda$1.lambdaFactory$(this, task));
    }

    static /* synthetic */ void lambda$execute$0(BackgroundQueue backgroundQueue, Runnable task) {
        task.run();
        backgroundQueue.completedTasks.release();
    }

    public void drain() throws InterruptedException {
        this.completedTasks.acquire(this.pendingTaskCount);
        this.pendingTaskCount = 0;
    }
}
