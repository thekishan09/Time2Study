package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AbstractFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class ImmediateFuture<V> implements ListenableFuture<V> {
    private static final Logger log = Logger.getLogger(ImmediateFuture.class.getName());

    public abstract V get() throws ExecutionException;

    ImmediateFuture() {
    }

    public void addListener(Runnable listener, Executor executor) {
        Preconditions.checkNotNull(listener, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        try {
            executor.execute(listener);
        } catch (RuntimeException e) {
            Logger logger = log;
            Level level = Level.SEVERE;
            logger.log(level, "RuntimeException while executing runnable " + listener + " with executor " + executor, e);
        }
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    public V get(long timeout, TimeUnit unit) throws ExecutionException {
        Preconditions.checkNotNull(unit);
        return get();
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean isDone() {
        return true;
    }

    static class ImmediateSuccessfulFuture<V> extends ImmediateFuture<V> {
        static final ImmediateSuccessfulFuture<Object> NULL = new ImmediateSuccessfulFuture<>((Object) null);
        @NullableDecl
        private final V value;

        ImmediateSuccessfulFuture(@NullableDecl V value2) {
            this.value = value2;
        }

        public V get() {
            return this.value;
        }

        public String toString() {
            return super.toString() + "[status=SUCCESS, result=[" + this.value + "]]";
        }
    }

    static final class ImmediateFailedFuture<V> extends AbstractFuture.TrustedFuture<V> {
        ImmediateFailedFuture(Throwable thrown) {
            setException(thrown);
        }
    }

    static final class ImmediateCancelledFuture<V> extends AbstractFuture.TrustedFuture<V> {
        ImmediateCancelledFuture() {
            cancel(false);
        }
    }
}
