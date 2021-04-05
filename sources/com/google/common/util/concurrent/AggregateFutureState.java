package com.google.common.util.concurrent;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class AggregateFutureState {
    private static final AtomicHelper ATOMIC_HELPER;
    private static final Logger log;
    /* access modifiers changed from: private */
    public volatile int remaining;
    /* access modifiers changed from: private */
    public volatile Set<Throwable> seenExceptions = null;

    /* access modifiers changed from: package-private */
    public abstract void addInitialException(Set<Throwable> set);

    static /* synthetic */ int access$310(AggregateFutureState x0) {
        int i = x0.remaining;
        x0.remaining = i - 1;
        return i;
    }

    static {
        SafeAtomicHelper helper;
        Class<AggregateFutureState> cls = AggregateFutureState.class;
        log = Logger.getLogger(cls.getName());
        Throwable thrownReflectionFailure = null;
        try {
            helper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(cls, Set.class, "seenExceptions"), AtomicIntegerFieldUpdater.newUpdater(cls, "remaining"));
        } catch (Throwable reflectionFailure) {
            thrownReflectionFailure = reflectionFailure;
            helper = new SynchronizedAtomicHelper();
        }
        ATOMIC_HELPER = helper;
        if (thrownReflectionFailure != null) {
            log.log(Level.SEVERE, "SafeAtomicHelper is broken!", thrownReflectionFailure);
        }
    }

    AggregateFutureState(int remainingFutures) {
        this.remaining = remainingFutures;
    }

    /* access modifiers changed from: package-private */
    public final Set<Throwable> getOrInitSeenExceptions() {
        Set<Throwable> seenExceptionsLocal = this.seenExceptions;
        if (seenExceptionsLocal != null) {
            return seenExceptionsLocal;
        }
        Set<Throwable> seenExceptionsLocal2 = Sets.newConcurrentHashSet();
        addInitialException(seenExceptionsLocal2);
        ATOMIC_HELPER.compareAndSetSeenExceptions(this, (Set<Throwable>) null, seenExceptionsLocal2);
        return this.seenExceptions;
    }

    /* access modifiers changed from: package-private */
    public final int decrementRemainingAndGet() {
        return ATOMIC_HELPER.decrementAndGetRemainingCount(this);
    }

    private static abstract class AtomicHelper {
        /* access modifiers changed from: package-private */
        public abstract void compareAndSetSeenExceptions(AggregateFutureState aggregateFutureState, Set<Throwable> set, Set<Throwable> set2);

        /* access modifiers changed from: package-private */
        public abstract int decrementAndGetRemainingCount(AggregateFutureState aggregateFutureState);

        private AtomicHelper() {
        }
    }

    private static final class SafeAtomicHelper extends AtomicHelper {
        final AtomicIntegerFieldUpdater<AggregateFutureState> remainingCountUpdater;
        final AtomicReferenceFieldUpdater<AggregateFutureState, Set<Throwable>> seenExceptionsUpdater;

        SafeAtomicHelper(AtomicReferenceFieldUpdater seenExceptionsUpdater2, AtomicIntegerFieldUpdater remainingCountUpdater2) {
            super();
            this.seenExceptionsUpdater = seenExceptionsUpdater2;
            this.remainingCountUpdater = remainingCountUpdater2;
        }

        /* access modifiers changed from: package-private */
        public void compareAndSetSeenExceptions(AggregateFutureState state, Set<Throwable> expect, Set<Throwable> update) {
            this.seenExceptionsUpdater.compareAndSet(state, expect, update);
        }

        /* access modifiers changed from: package-private */
        public int decrementAndGetRemainingCount(AggregateFutureState state) {
            return this.remainingCountUpdater.decrementAndGet(state);
        }
    }

    private static final class SynchronizedAtomicHelper extends AtomicHelper {
        private SynchronizedAtomicHelper() {
            super();
        }

        /* access modifiers changed from: package-private */
        public void compareAndSetSeenExceptions(AggregateFutureState state, Set<Throwable> expect, Set<Throwable> update) {
            synchronized (state) {
                if (state.seenExceptions == expect) {
                    Set unused = state.seenExceptions = update;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public int decrementAndGetRemainingCount(AggregateFutureState state) {
            int access$300;
            synchronized (state) {
                AggregateFutureState.access$310(state);
                access$300 = state.remaining;
            }
            return access$300;
        }
    }
}
