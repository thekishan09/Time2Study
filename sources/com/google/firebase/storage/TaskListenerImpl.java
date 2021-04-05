package com.google.firebase.storage;

import android.app.Activity;
import android.os.Build;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.StorageTask.ProvideError;
import com.google.firebase.storage.internal.ActivityLifecycleListener;
import com.google.firebase.storage.internal.SmartHandler;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
class TaskListenerImpl<ListenerTypeT, ResultT extends StorageTask.ProvideError> {
    private final HashMap<ListenerTypeT, SmartHandler> handlerMap = new HashMap<>();
    private final Queue<ListenerTypeT> listenerQueue = new ConcurrentLinkedQueue();
    private OnRaise<ListenerTypeT, ResultT> onRaise;
    private int targetStates;
    private StorageTask<ResultT> task;

    /* compiled from: com.google.firebase:firebase-storage@@19.1.1 */
    interface OnRaise<ListenerTypeT, ResultT> {
        void raise(ListenerTypeT listenertypet, ResultT resultt);
    }

    public TaskListenerImpl(StorageTask<ResultT> task2, int targetInternalStates, OnRaise<ListenerTypeT, ResultT> onRaise2) {
        this.task = task2;
        this.targetStates = targetInternalStates;
        this.onRaise = onRaise2;
    }

    public int getListenerCount() {
        return Math.max(this.listenerQueue.size(), this.handlerMap.size());
    }

    public void addListener(Activity activity, Executor executor, ListenerTypeT listener) {
        SmartHandler handler;
        Preconditions.checkNotNull(listener);
        boolean shouldFire = false;
        synchronized (this.task.getSyncObject()) {
            if ((this.task.getInternalState() & this.targetStates) != 0) {
                shouldFire = true;
            }
            this.listenerQueue.add(listener);
            handler = new SmartHandler(executor);
            this.handlerMap.put(listener, handler);
            if (activity != null) {
                if (Build.VERSION.SDK_INT >= 17) {
                    Preconditions.checkArgument(!activity.isDestroyed(), "Activity is already destroyed!");
                }
                ActivityLifecycleListener.getInstance().runOnActivityStopped(activity, listener, TaskListenerImpl$$Lambda$1.lambdaFactory$(this, listener));
            }
        }
        if (shouldFire) {
            handler.callBack(TaskListenerImpl$$Lambda$2.lambdaFactory$(this, listener, this.task.snapState()));
        }
    }

    public void onInternalStateChanged() {
        if ((this.task.getInternalState() & this.targetStates) != 0) {
            ResultT snappedState = this.task.snapState();
            for (ListenerTypeT c : this.listenerQueue) {
                ListenerTypeT finalCallback = c;
                SmartHandler handler = this.handlerMap.get(c);
                if (handler != null) {
                    handler.callBack(TaskListenerImpl$$Lambda$3.lambdaFactory$(this, finalCallback, snappedState));
                }
            }
        }
    }

    public void removeListener(ListenerTypeT listener) {
        Preconditions.checkNotNull(listener);
        synchronized (this.task.getSyncObject()) {
            this.handlerMap.remove(listener);
            this.listenerQueue.remove(listener);
            ActivityLifecycleListener.getInstance().removeCookie(listener);
        }
    }
}
