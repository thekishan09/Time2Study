package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class DirectedGraphConnections<N, V> implements GraphConnections<N, V> {
    private static final Object PRED = new Object();
    /* access modifiers changed from: private */
    public final Map<N, Object> adjacentNodeValues;
    /* access modifiers changed from: private */
    public int predecessorCount;
    /* access modifiers changed from: private */
    public int successorCount;

    private static final class PredAndSucc {
        /* access modifiers changed from: private */
        public final Object successorValue;

        PredAndSucc(Object successorValue2) {
            this.successorValue = successorValue2;
        }
    }

    private DirectedGraphConnections(Map<N, Object> adjacentNodeValues2, int predecessorCount2, int successorCount2) {
        this.adjacentNodeValues = (Map) Preconditions.checkNotNull(adjacentNodeValues2);
        this.predecessorCount = Graphs.checkNonNegative(predecessorCount2);
        this.successorCount = Graphs.checkNonNegative(successorCount2);
        Preconditions.checkState(predecessorCount2 <= adjacentNodeValues2.size() && successorCount2 <= adjacentNodeValues2.size());
    }

    /* renamed from: of */
    static <N, V> DirectedGraphConnections<N, V> m523of() {
        return new DirectedGraphConnections<>(new HashMap(4, 1.0f), 0, 0);
    }

    static <N, V> DirectedGraphConnections<N, V> ofImmutable(Set<N> predecessors, Map<N, V> successorValues) {
        Map<N, Object> adjacentNodeValues2 = new HashMap<>();
        adjacentNodeValues2.putAll(successorValues);
        for (N predecessor : predecessors) {
            Object value = adjacentNodeValues2.put(predecessor, PRED);
            if (value != null) {
                adjacentNodeValues2.put(predecessor, new PredAndSucc(value));
            }
        }
        return new DirectedGraphConnections<>(ImmutableMap.copyOf(adjacentNodeValues2), predecessors.size(), successorValues.size());
    }

    public Set<N> adjacentNodes() {
        return Collections.unmodifiableSet(this.adjacentNodeValues.keySet());
    }

    public Set<N> predecessors() {
        return new AbstractSet<N>() {
            public UnmodifiableIterator<N> iterator() {
                final Iterator<Map.Entry<N, Object>> entries = DirectedGraphConnections.this.adjacentNodeValues.entrySet().iterator();
                return new AbstractIterator<N>() {
                    /* access modifiers changed from: protected */
                    public N computeNext() {
                        while (entries.hasNext()) {
                            Map.Entry<N, Object> entry = (Map.Entry) entries.next();
                            if (DirectedGraphConnections.isPredecessor(entry.getValue())) {
                                return entry.getKey();
                            }
                        }
                        return endOfData();
                    }
                };
            }

            public int size() {
                return DirectedGraphConnections.this.predecessorCount;
            }

            public boolean contains(@NullableDecl Object obj) {
                return DirectedGraphConnections.isPredecessor(DirectedGraphConnections.this.adjacentNodeValues.get(obj));
            }
        };
    }

    public Set<N> successors() {
        return new AbstractSet<N>() {
            public UnmodifiableIterator<N> iterator() {
                final Iterator<Map.Entry<N, Object>> entries = DirectedGraphConnections.this.adjacentNodeValues.entrySet().iterator();
                return new AbstractIterator<N>() {
                    /* access modifiers changed from: protected */
                    public N computeNext() {
                        while (entries.hasNext()) {
                            Map.Entry<N, Object> entry = (Map.Entry) entries.next();
                            if (DirectedGraphConnections.isSuccessor(entry.getValue())) {
                                return entry.getKey();
                            }
                        }
                        return endOfData();
                    }
                };
            }

            public int size() {
                return DirectedGraphConnections.this.successorCount;
            }

            public boolean contains(@NullableDecl Object obj) {
                return DirectedGraphConnections.isSuccessor(DirectedGraphConnections.this.adjacentNodeValues.get(obj));
            }
        };
    }

    public V value(N node) {
        Object value = this.adjacentNodeValues.get(node);
        if (value == PRED) {
            return null;
        }
        if (value instanceof PredAndSucc) {
            return ((PredAndSucc) value).successorValue;
        }
        return value;
    }

    public void removePredecessor(N node) {
        Object previousValue = this.adjacentNodeValues.get(node);
        if (previousValue == PRED) {
            this.adjacentNodeValues.remove(node);
            int i = this.predecessorCount - 1;
            this.predecessorCount = i;
            Graphs.checkNonNegative(i);
        } else if (previousValue instanceof PredAndSucc) {
            this.adjacentNodeValues.put(node, ((PredAndSucc) previousValue).successorValue);
            int i2 = this.predecessorCount - 1;
            this.predecessorCount = i2;
            Graphs.checkNonNegative(i2);
        }
    }

    public V removeSuccessor(Object node) {
        Object obj;
        Object previousValue = this.adjacentNodeValues.get(node);
        if (previousValue == null || previousValue == (obj = PRED)) {
            return null;
        }
        if (previousValue instanceof PredAndSucc) {
            this.adjacentNodeValues.put(node, obj);
            int i = this.successorCount - 1;
            this.successorCount = i;
            Graphs.checkNonNegative(i);
            return ((PredAndSucc) previousValue).successorValue;
        }
        this.adjacentNodeValues.remove(node);
        int i2 = this.successorCount - 1;
        this.successorCount = i2;
        Graphs.checkNonNegative(i2);
        return previousValue;
    }

    public void addPredecessor(N node, V v) {
        Object previousValue = this.adjacentNodeValues.put(node, PRED);
        if (previousValue == null) {
            int i = this.predecessorCount + 1;
            this.predecessorCount = i;
            Graphs.checkPositive(i);
        } else if (previousValue instanceof PredAndSucc) {
            this.adjacentNodeValues.put(node, previousValue);
        } else if (previousValue != PRED) {
            this.adjacentNodeValues.put(node, new PredAndSucc(previousValue));
            int i2 = this.predecessorCount + 1;
            this.predecessorCount = i2;
            Graphs.checkPositive(i2);
        }
    }

    public V addSuccessor(N node, V value) {
        Object previousValue = this.adjacentNodeValues.put(node, value);
        if (previousValue == null) {
            int i = this.successorCount + 1;
            this.successorCount = i;
            Graphs.checkPositive(i);
            return null;
        } else if (previousValue instanceof PredAndSucc) {
            this.adjacentNodeValues.put(node, new PredAndSucc(value));
            return ((PredAndSucc) previousValue).successorValue;
        } else if (previousValue != PRED) {
            return previousValue;
        } else {
            this.adjacentNodeValues.put(node, new PredAndSucc(value));
            int i2 = this.successorCount + 1;
            this.successorCount = i2;
            Graphs.checkPositive(i2);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static boolean isPredecessor(@NullableDecl Object value) {
        return value == PRED || (value instanceof PredAndSucc);
    }

    /* access modifiers changed from: private */
    public static boolean isSuccessor(@NullableDecl Object value) {
        return (value == PRED || value == null) ? false : true;
    }
}
