package com.google.common.collect;

import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

public abstract class ImmutableBiMap<K, V> extends ImmutableMap<K, V> implements BiMap<K, V> {
    public abstract ImmutableBiMap<V, K> inverse();

    /* renamed from: of */
    public static <K, V> ImmutableBiMap<K, V> m419of() {
        return RegularImmutableBiMap.EMPTY;
    }

    /* renamed from: of */
    public static <K, V> ImmutableBiMap<K, V> m420of(K k1, V v1) {
        CollectPreconditions.checkEntryNotNull(k1, v1);
        return new RegularImmutableBiMap(new Object[]{k1, v1}, 1);
    }

    /* renamed from: of */
    public static <K, V> ImmutableBiMap<K, V> m421of(K k1, V v1, K k2, V v2) {
        CollectPreconditions.checkEntryNotNull(k1, v1);
        CollectPreconditions.checkEntryNotNull(k2, v2);
        return new RegularImmutableBiMap(new Object[]{k1, v1, k2, v2}, 2);
    }

    /* renamed from: of */
    public static <K, V> ImmutableBiMap<K, V> m422of(K k1, V v1, K k2, V v2, K k3, V v3) {
        CollectPreconditions.checkEntryNotNull(k1, v1);
        CollectPreconditions.checkEntryNotNull(k2, v2);
        CollectPreconditions.checkEntryNotNull(k3, v3);
        return new RegularImmutableBiMap(new Object[]{k1, v1, k2, v2, k3, v3}, 3);
    }

    /* renamed from: of */
    public static <K, V> ImmutableBiMap<K, V> m423of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        CollectPreconditions.checkEntryNotNull(k1, v1);
        CollectPreconditions.checkEntryNotNull(k2, v2);
        CollectPreconditions.checkEntryNotNull(k3, v3);
        CollectPreconditions.checkEntryNotNull(k4, v4);
        return new RegularImmutableBiMap(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4}, 4);
    }

    /* renamed from: of */
    public static <K, V> ImmutableBiMap<K, V> m424of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        CollectPreconditions.checkEntryNotNull(k1, v1);
        CollectPreconditions.checkEntryNotNull(k2, v2);
        CollectPreconditions.checkEntryNotNull(k3, v3);
        CollectPreconditions.checkEntryNotNull(k4, v4);
        CollectPreconditions.checkEntryNotNull(k5, v5);
        return new RegularImmutableBiMap(new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5}, 5);
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    public static <K, V> Builder<K, V> builderWithExpectedSize(int expectedSize) {
        CollectPreconditions.checkNonnegative(expectedSize, "expectedSize");
        return new Builder<>(expectedSize);
    }

    public static final class Builder<K, V> extends ImmutableMap.Builder<K, V> {
        public Builder() {
        }

        Builder(int size) {
            super(size);
        }

        public Builder<K, V> put(K key, V value) {
            super.put(key, value);
            return this;
        }

        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            super.put(entry);
            return this;
        }

        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            super.putAll(map);
            return this;
        }

        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> entries) {
            super.putAll(entries);
            return this;
        }

        public Builder<K, V> orderEntriesByValue(Comparator<? super V> valueComparator) {
            super.orderEntriesByValue(valueComparator);
            return this;
        }

        public ImmutableBiMap<K, V> build() {
            if (this.size == 0) {
                return ImmutableBiMap.m419of();
            }
            sortEntries();
            this.entriesUsed = true;
            return new RegularImmutableBiMap(this.alternatingKeysAndValues, this.size);
        }
    }

    public static <K, V> ImmutableBiMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        if (map instanceof ImmutableBiMap) {
            ImmutableBiMap<K, V> bimap = (ImmutableBiMap) map;
            if (!bimap.isPartialView()) {
                return bimap;
            }
        }
        return copyOf(map.entrySet());
    }

    public static <K, V> ImmutableBiMap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> entries) {
        return new Builder(entries instanceof Collection ? ((Collection) entries).size() : 4).putAll(entries).build();
    }

    ImmutableBiMap() {
    }

    public ImmutableSet<V> values() {
        return inverse().keySet();
    }

    /* access modifiers changed from: package-private */
    public final ImmutableSet<V> createValues() {
        throw new AssertionError("should never be called");
    }

    @Deprecated
    public V forcePut(K k, V v) {
        throw new UnsupportedOperationException();
    }

    private static class SerializedForm extends ImmutableMap.SerializedForm {
        private static final long serialVersionUID = 0;

        SerializedForm(ImmutableBiMap<?, ?> bimap) {
            super(bimap);
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            return createMap(new Builder<>());
        }
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new SerializedForm(this);
    }
}
