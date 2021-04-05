package com.google.common.collect;

import com.google.common.collect.ImmutableSortedMap;

abstract class ImmutableSortedMapFauxverideShim<K, V> extends ImmutableMap<K, V> {
    ImmutableSortedMapFauxverideShim() {
    }

    @Deprecated
    public static <K, V> ImmutableSortedMap.Builder<K, V> builder() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public static <K, V> ImmutableSortedMap.Builder<K, V> builderWithExpectedSize(int expectedSize) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <K, V> ImmutableSortedMap<K, V> m489of(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <K, V> ImmutableSortedMap<K, V> m490of(K k, V v, K k2, V v2) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <K, V> ImmutableSortedMap<K, V> m491of(K k, V v, K k2, V v2, K k3, V v3) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <K, V> ImmutableSortedMap<K, V> m492of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    /* renamed from: of */
    public static <K, V> ImmutableSortedMap<K, V> m493of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        throw new UnsupportedOperationException();
    }
}
