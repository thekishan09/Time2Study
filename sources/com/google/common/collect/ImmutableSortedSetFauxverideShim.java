package com.google.common.collect;

import com.google.common.collect.ImmutableSortedSet;

abstract class ImmutableSortedSetFauxverideShim<E> extends ImmutableSet<E> {
    ImmutableSortedSetFauxverideShim() {
    }

    @Deprecated
    public static <E> ImmutableSortedSet.Builder<E> builder() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet.Builder<E> builderWithExpectedSize(int expectedSize) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <E> ImmutableSortedSet<E> m514of(E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <E> ImmutableSortedSet<E> m515of(E e, E e2) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <E> ImmutableSortedSet<E> m516of(E e, E e2, E e3) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <E> ImmutableSortedSet<E> m517of(E e, E e2, E e3, E e4) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <E> ImmutableSortedSet<E> m518of(E e, E e2, E e3, E e4, E e5) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <E> ImmutableSortedSet<E> m519of(E e, E e2, E e3, E e4, E e5, E e6, E... eArr) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <E> ImmutableSortedSet<E> copyOf(E[] eArr) {
        throw new UnsupportedOperationException();
    }
}
