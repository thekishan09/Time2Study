package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ImmutableSet<E> extends ImmutableCollection<E> implements Set<E> {
    private static final int CUTOFF = 751619276;
    private static final double DESIRED_LOAD_FACTOR = 0.7d;
    static final int MAX_TABLE_SIZE = 1073741824;
    @NullableDecl
    @LazyInit
    private transient ImmutableList<E> asList;

    public abstract UnmodifiableIterator<E> iterator();

    /* renamed from: of */
    public static <E> ImmutableSet<E> m469of() {
        return RegularImmutableSet.EMPTY;
    }

    /* renamed from: of */
    public static <E> ImmutableSet<E> m470of(E element) {
        return new SingletonImmutableSet(element);
    }

    /* renamed from: of */
    public static <E> ImmutableSet<E> m471of(E e1, E e2) {
        return construct(2, e1, e2);
    }

    /* renamed from: of */
    public static <E> ImmutableSet<E> m472of(E e1, E e2, E e3) {
        return construct(3, e1, e2, e3);
    }

    /* renamed from: of */
    public static <E> ImmutableSet<E> m473of(E e1, E e2, E e3, E e4) {
        return construct(4, e1, e2, e3, e4);
    }

    /* renamed from: of */
    public static <E> ImmutableSet<E> m474of(E e1, E e2, E e3, E e4, E e5) {
        return construct(5, e1, e2, e3, e4, e5);
    }

    @SafeVarargs
    /* renamed from: of */
    public static <E> ImmutableSet<E> m475of(E e1, E e2, E e3, E e4, E e5, E e6, E... others) {
        Preconditions.checkArgument(others.length <= 2147483641, "the total number of elements must fit in an int");
        Object[] elements = new Object[(others.length + 6)];
        elements[0] = e1;
        elements[1] = e2;
        elements[2] = e3;
        elements[3] = e4;
        elements[4] = e5;
        elements[5] = e6;
        System.arraycopy(others, 0, elements, 6, others.length);
        return construct(elements.length, elements);
    }

    /* access modifiers changed from: private */
    public static <E> ImmutableSet<E> construct(int n, Object... elements) {
        if (n == 0) {
            return m469of();
        }
        if (n == 1) {
            return m470of(elements[0]);
        }
        int tableSize = chooseTableSize(n);
        Object[] table = new Object[tableSize];
        int mask = tableSize - 1;
        int hashCode = 0;
        int uniques = 0;
        for (int i = 0; i < n; i++) {
            Object element = ObjectArrays.checkElementNotNull(elements[i], i);
            int hash = element.hashCode();
            int j = Hashing.smear(hash);
            while (true) {
                int index = j & mask;
                Object value = table[index];
                if (value == null) {
                    elements[uniques] = element;
                    table[index] = element;
                    hashCode += hash;
                    uniques++;
                    break;
                } else if (value.equals(element) != 0) {
                    break;
                } else {
                    j++;
                }
            }
        }
        Arrays.fill(elements, uniques, n, (Object) null);
        if (uniques == 1) {
            return new SingletonImmutableSet(elements[0], hashCode);
        }
        if (chooseTableSize(uniques) < tableSize / 2) {
            return construct(uniques, elements);
        }
        return new RegularImmutableSet(shouldTrim(uniques, elements.length) ? Arrays.copyOf(elements, uniques) : elements, hashCode, table, mask, uniques);
    }

    /* access modifiers changed from: private */
    public static boolean shouldTrim(int actualUnique, int expectedUnique) {
        return actualUnique < (expectedUnique >> 1) + (expectedUnique >> 2);
    }

    static int chooseTableSize(int setSize) {
        int setSize2 = Math.max(setSize, 2);
        boolean z = true;
        if (setSize2 < CUTOFF) {
            int tableSize = Integer.highestOneBit(setSize2 - 1) << 1;
            while (true) {
                double d = (double) tableSize;
                Double.isNaN(d);
                if (d * DESIRED_LOAD_FACTOR >= ((double) setSize2)) {
                    return tableSize;
                }
                tableSize <<= 1;
            }
        } else {
            if (setSize2 >= 1073741824) {
                z = false;
            }
            Preconditions.checkArgument(z, "collection too large");
            return 1073741824;
        }
    }

    public static <E> ImmutableSet<E> copyOf(Collection<? extends E> elements) {
        if ((elements instanceof ImmutableSet) && !(elements instanceof SortedSet)) {
            ImmutableSet<E> set = (ImmutableSet) elements;
            if (!set.isPartialView()) {
                return set;
            }
        }
        Object[] array = elements.toArray();
        return construct(array.length, array);
    }

    public static <E> ImmutableSet<E> copyOf(Iterable<? extends E> elements) {
        if (elements instanceof Collection) {
            return copyOf((Collection) elements);
        }
        return copyOf(elements.iterator());
    }

    public static <E> ImmutableSet<E> copyOf(Iterator<? extends E> elements) {
        if (!elements.hasNext()) {
            return m469of();
        }
        E first = elements.next();
        if (!elements.hasNext()) {
            return m470of(first);
        }
        return new Builder().add((Object) first).addAll((Iterator) elements).build();
    }

    public static <E> ImmutableSet<E> copyOf(E[] elements) {
        int length = elements.length;
        if (length == 0) {
            return m469of();
        }
        if (length != 1) {
            return construct(elements.length, (Object[]) elements.clone());
        }
        return m470of(elements[0]);
    }

    ImmutableSet() {
    }

    /* access modifiers changed from: package-private */
    public boolean isHashCodeFast() {
        return false;
    }

    public boolean equals(@NullableDecl Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ImmutableSet) || !isHashCodeFast() || !((ImmutableSet) object).isHashCodeFast() || hashCode() == object.hashCode()) {
            return Sets.equalsImpl(this, object);
        }
        return false;
    }

    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }

    public ImmutableList<E> asList() {
        ImmutableList<E> result = this.asList;
        if (result != null) {
            return result;
        }
        ImmutableList<E> createAsList = createAsList();
        this.asList = createAsList;
        return createAsList;
    }

    /* access modifiers changed from: package-private */
    public ImmutableList<E> createAsList() {
        return ImmutableList.asImmutableList(toArray());
    }

    private static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        final Object[] elements;

        SerializedForm(Object[] elements2) {
            this.elements = elements2;
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            return ImmutableSet.copyOf((E[]) this.elements);
        }
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new SerializedForm(toArray());
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }

    public static <E> Builder<E> builderWithExpectedSize(int expectedSize) {
        CollectPreconditions.checkNonnegative(expectedSize, "expectedSize");
        return new Builder<>(expectedSize);
    }

    public static class Builder<E> extends ImmutableCollection.ArrayBasedBuilder<E> {
        private int hashCode;
        @NullableDecl
        Object[] hashTable;

        public Builder() {
            super(4);
        }

        Builder(int capacity) {
            super(capacity);
            this.hashTable = new Object[ImmutableSet.chooseTableSize(capacity)];
        }

        public Builder<E> add(E element) {
            Preconditions.checkNotNull(element);
            if (this.hashTable == null || ImmutableSet.chooseTableSize(this.size) > this.hashTable.length) {
                this.hashTable = null;
                super.add(element);
                return this;
            }
            addDeduping(element);
            return this;
        }

        public Builder<E> add(E... elements) {
            if (this.hashTable != null) {
                for (E e : elements) {
                    add((Object) e);
                }
            } else {
                super.add(elements);
            }
            return this;
        }

        private void addDeduping(E element) {
            int mask = this.hashTable.length - 1;
            int hash = element.hashCode();
            int i = Hashing.smear(hash);
            while (true) {
                int i2 = i & mask;
                Object[] objArr = this.hashTable;
                Object previous = objArr[i2];
                if (previous == null) {
                    objArr[i2] = element;
                    this.hashCode += hash;
                    super.add(element);
                    return;
                } else if (!previous.equals(element)) {
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        }

        public Builder<E> addAll(Iterable<? extends E> elements) {
            Preconditions.checkNotNull(elements);
            if (this.hashTable != null) {
                for (E e : elements) {
                    add((Object) e);
                }
            } else {
                super.addAll(elements);
            }
            return this;
        }

        public Builder<E> addAll(Iterator<? extends E> elements) {
            Preconditions.checkNotNull(elements);
            while (elements.hasNext()) {
                add((Object) elements.next());
            }
            return this;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: com.google.common.collect.ImmutableSet} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v18, resolved type: com.google.common.collect.RegularImmutableSet} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v9, resolved type: com.google.common.collect.RegularImmutableSet} */
        /* JADX WARNING: type inference failed for: r0v6, types: [com.google.common.collect.ImmutableSet<E>] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.google.common.collect.ImmutableSet<E> build() {
            /*
                r8 = this;
                int r0 = r8.size
                if (r0 == 0) goto L_0x005b
                r1 = 1
                if (r0 == r1) goto L_0x0051
                java.lang.Object[] r0 = r8.hashTable
                if (r0 == 0) goto L_0x003d
                int r0 = r8.size
                int r0 = com.google.common.collect.ImmutableSet.chooseTableSize(r0)
                java.lang.Object[] r2 = r8.hashTable
                int r2 = r2.length
                if (r0 != r2) goto L_0x003d
                int r0 = r8.size
                java.lang.Object[] r2 = r8.contents
                int r2 = r2.length
                boolean r0 = com.google.common.collect.ImmutableSet.shouldTrim(r0, r2)
                if (r0 == 0) goto L_0x002a
                java.lang.Object[] r0 = r8.contents
                int r2 = r8.size
                java.lang.Object[] r0 = java.util.Arrays.copyOf(r0, r2)
                goto L_0x002c
            L_0x002a:
                java.lang.Object[] r0 = r8.contents
            L_0x002c:
                r3 = r0
                com.google.common.collect.RegularImmutableSet r0 = new com.google.common.collect.RegularImmutableSet
                int r4 = r8.hashCode
                java.lang.Object[] r5 = r8.hashTable
                int r2 = r5.length
                int r6 = r2 + -1
                int r7 = r8.size
                r2 = r0
                r2.<init>(r3, r4, r5, r6, r7)
                goto L_0x004b
            L_0x003d:
                int r0 = r8.size
                java.lang.Object[] r2 = r8.contents
                com.google.common.collect.ImmutableSet r0 = com.google.common.collect.ImmutableSet.construct(r0, r2)
                int r2 = r0.size()
                r8.size = r2
            L_0x004b:
                r8.forceCopy = r1
                r1 = 0
                r8.hashTable = r1
                return r0
            L_0x0051:
                java.lang.Object[] r0 = r8.contents
                r1 = 0
                r0 = r0[r1]
                com.google.common.collect.ImmutableSet r0 = com.google.common.collect.ImmutableSet.m470of(r0)
                return r0
            L_0x005b:
                com.google.common.collect.ImmutableSet r0 = com.google.common.collect.ImmutableSet.m469of()
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ImmutableSet.Builder.build():com.google.common.collect.ImmutableSet");
        }
    }
}
