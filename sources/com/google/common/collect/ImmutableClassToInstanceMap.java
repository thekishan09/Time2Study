package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Primitives;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable(containerOf = {"B"})
public final class ImmutableClassToInstanceMap<B> extends ForwardingMap<Class<? extends B>, B> implements ClassToInstanceMap<B>, Serializable {
    private static final ImmutableClassToInstanceMap<Object> EMPTY = new ImmutableClassToInstanceMap<>(ImmutableMap.m446of());
    private final ImmutableMap<Class<? extends B>, B> delegate;

    /* renamed from: of */
    public static <B> ImmutableClassToInstanceMap<B> m425of() {
        return EMPTY;
    }

    /* renamed from: of */
    public static <B, T extends B> ImmutableClassToInstanceMap<B> m426of(Class<T> type, T value) {
        return new ImmutableClassToInstanceMap<>(ImmutableMap.m447of(type, value));
    }

    public static <B> Builder<B> builder() {
        return new Builder<>();
    }

    public static final class Builder<B> {
        private final ImmutableMap.Builder<Class<? extends B>, B> mapBuilder = ImmutableMap.builder();

        public <T extends B> Builder<B> put(Class<T> key, T value) {
            this.mapBuilder.put(key, value);
            return this;
        }

        public <T extends B> Builder<B> putAll(Map<? extends Class<? extends T>, ? extends T> map) {
            for (Map.Entry<? extends Class<? extends T>, ? extends T> entry : map.entrySet()) {
                Class<? extends T> type = (Class) entry.getKey();
                this.mapBuilder.put(type, cast(type, entry.getValue()));
            }
            return this;
        }

        private static <B, T extends B> T cast(Class<T> type, B value) {
            return Primitives.wrap(type).cast(value);
        }

        public ImmutableClassToInstanceMap<B> build() {
            ImmutableMap<Class<? extends B>, B> map = this.mapBuilder.build();
            if (map.isEmpty()) {
                return ImmutableClassToInstanceMap.m425of();
            }
            return new ImmutableClassToInstanceMap<>(map);
        }
    }

    public static <B, S extends B> ImmutableClassToInstanceMap<B> copyOf(Map<? extends Class<? extends S>, ? extends S> map) {
        if (map instanceof ImmutableClassToInstanceMap) {
            return (ImmutableClassToInstanceMap) map;
        }
        return new Builder().putAll(map).build();
    }

    private ImmutableClassToInstanceMap(ImmutableMap<Class<? extends B>, B> delegate2) {
        this.delegate = delegate2;
    }

    /* access modifiers changed from: protected */
    public Map<Class<? extends B>, B> delegate() {
        return this.delegate;
    }

    @NullableDecl
    public <T extends B> T getInstance(Class<T> type) {
        return this.delegate.get(Preconditions.checkNotNull(type));
    }

    @Deprecated
    public <T extends B> T putInstance(Class<T> cls, T t) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: package-private */
    public Object readResolve() {
        return isEmpty() ? m425of() : this;
    }
}
