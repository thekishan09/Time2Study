package com.google.common.graph;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.errorprone.annotations.Immutable;
import java.util.Comparator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
public final class ElementOrder<T> {
    @NullableDecl
    private final Comparator<T> comparator;
    private final Type type;

    public enum Type {
        UNORDERED,
        INSERTION,
        SORTED
    }

    private ElementOrder(Type type2, @NullableDecl Comparator<T> comparator2) {
        this.type = (Type) Preconditions.checkNotNull(type2);
        this.comparator = comparator2;
        Preconditions.checkState((type2 == Type.SORTED) != (comparator2 != null) ? false : true);
    }

    public static <S> ElementOrder<S> unordered() {
        return new ElementOrder<>(Type.UNORDERED, (Comparator) null);
    }

    public static <S> ElementOrder<S> insertion() {
        return new ElementOrder<>(Type.INSERTION, (Comparator) null);
    }

    public static <S extends Comparable<? super S>> ElementOrder<S> natural() {
        return new ElementOrder<>(Type.SORTED, Ordering.natural());
    }

    public static <S> ElementOrder<S> sorted(Comparator<S> comparator2) {
        return new ElementOrder<>(Type.SORTED, comparator2);
    }

    public Type type() {
        return this.type;
    }

    public Comparator<T> comparator() {
        Comparator<T> comparator2 = this.comparator;
        if (comparator2 != null) {
            return comparator2;
        }
        throw new UnsupportedOperationException("This ordering does not define a comparator.");
    }

    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ElementOrder)) {
            return false;
        }
        ElementOrder<?> other = (ElementOrder) obj;
        if (this.type != other.type || !Objects.equal(this.comparator, other.comparator)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.type, this.comparator);
    }

    public String toString() {
        MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper((Object) this).add("type", (Object) this.type);
        Comparator<T> comparator2 = this.comparator;
        if (comparator2 != null) {
            helper.add("comparator", (Object) comparator2);
        }
        return helper.toString();
    }

    /* renamed from: com.google.common.graph.ElementOrder$1 */
    static /* synthetic */ class C15451 {
        static final /* synthetic */ int[] $SwitchMap$com$google$common$graph$ElementOrder$Type;

        static {
            int[] iArr = new int[Type.values().length];
            $SwitchMap$com$google$common$graph$ElementOrder$Type = iArr;
            try {
                iArr[Type.UNORDERED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$common$graph$ElementOrder$Type[Type.INSERTION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$common$graph$ElementOrder$Type[Type.SORTED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* access modifiers changed from: package-private */
    public <K extends T, V> Map<K, V> createMap(int expectedSize) {
        int i = C15451.$SwitchMap$com$google$common$graph$ElementOrder$Type[this.type.ordinal()];
        if (i == 1) {
            return Maps.newHashMapWithExpectedSize(expectedSize);
        }
        if (i == 2) {
            return Maps.newLinkedHashMapWithExpectedSize(expectedSize);
        }
        if (i == 3) {
            return Maps.newTreeMap(comparator());
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public <T1 extends T> ElementOrder<T1> cast() {
        return this;
    }
}
