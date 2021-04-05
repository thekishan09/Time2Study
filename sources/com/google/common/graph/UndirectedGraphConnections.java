package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

final class UndirectedGraphConnections<N, V> implements GraphConnections<N, V> {
    private final Map<N, V> adjacentNodeValues;

    private UndirectedGraphConnections(Map<N, V> adjacentNodeValues2) {
        this.adjacentNodeValues = (Map) Preconditions.checkNotNull(adjacentNodeValues2);
    }

    /* renamed from: of */
    static <N, V> UndirectedGraphConnections<N, V> m529of() {
        return new UndirectedGraphConnections<>(new HashMap(2, 1.0f));
    }

    static <N, V> UndirectedGraphConnections<N, V> ofImmutable(Map<N, V> adjacentNodeValues2) {
        return new UndirectedGraphConnections<>(ImmutableMap.copyOf(adjacentNodeValues2));
    }

    public Set<N> adjacentNodes() {
        return Collections.unmodifiableSet(this.adjacentNodeValues.keySet());
    }

    public Set<N> predecessors() {
        return adjacentNodes();
    }

    public Set<N> successors() {
        return adjacentNodes();
    }

    public V value(N node) {
        return this.adjacentNodeValues.get(node);
    }

    public void removePredecessor(N node) {
        Object removeSuccessor = removeSuccessor(node);
    }

    public V removeSuccessor(N node) {
        return this.adjacentNodeValues.remove(node);
    }

    public void addPredecessor(N node, V value) {
        Object addSuccessor = addSuccessor(node, value);
    }

    public V addSuccessor(N node, V value) {
        return this.adjacentNodeValues.put(node, value);
    }
}
