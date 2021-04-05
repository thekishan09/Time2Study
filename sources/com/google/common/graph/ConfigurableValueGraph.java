package com.google.common.graph;

import com.google.common.base.Preconditions;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class ConfigurableValueGraph<N, V> extends AbstractValueGraph<N, V> {
    private final boolean allowsSelfLoops;
    protected long edgeCount;
    private final boolean isDirected;
    protected final MapIteratorCache<N, GraphConnections<N, V>> nodeConnections;
    private final ElementOrder<N> nodeOrder;

    ConfigurableValueGraph(AbstractGraphBuilder<? super N> builder) {
        this(builder, builder.nodeOrder.createMap(builder.expectedNodeCount.mo18233or(10).intValue()), 0);
    }

    ConfigurableValueGraph(AbstractGraphBuilder<? super N> builder, Map<N, GraphConnections<N, V>> nodeConnections2, long edgeCount2) {
        this.isDirected = builder.directed;
        this.allowsSelfLoops = builder.allowsSelfLoops;
        this.nodeOrder = builder.nodeOrder.cast();
        this.nodeConnections = nodeConnections2 instanceof TreeMap ? new MapRetrievalCache<>(nodeConnections2) : new MapIteratorCache<>(nodeConnections2);
        this.edgeCount = Graphs.checkNonNegative(edgeCount2);
    }

    public Set<N> nodes() {
        return this.nodeConnections.unmodifiableKeySet();
    }

    public boolean isDirected() {
        return this.isDirected;
    }

    public boolean allowsSelfLoops() {
        return this.allowsSelfLoops;
    }

    public ElementOrder<N> nodeOrder() {
        return this.nodeOrder;
    }

    public Set<N> adjacentNodes(N node) {
        return checkedConnections(node).adjacentNodes();
    }

    public Set<N> predecessors(N node) {
        return checkedConnections(node).predecessors();
    }

    public Set<N> successors(N node) {
        return checkedConnections(node).successors();
    }

    public boolean hasEdgeConnecting(N nodeU, N nodeV) {
        return hasEdgeConnecting_internal(Preconditions.checkNotNull(nodeU), Preconditions.checkNotNull(nodeV));
    }

    public boolean hasEdgeConnecting(EndpointPair<N> endpoints) {
        Preconditions.checkNotNull(endpoints);
        return isOrderingCompatible(endpoints) && hasEdgeConnecting_internal(endpoints.nodeU(), endpoints.nodeV());
    }

    @NullableDecl
    public V edgeValueOrDefault(N nodeU, N nodeV, @NullableDecl V defaultValue) {
        return edgeValueOrDefault_internal(Preconditions.checkNotNull(nodeU), Preconditions.checkNotNull(nodeV), defaultValue);
    }

    @NullableDecl
    public V edgeValueOrDefault(EndpointPair<N> endpoints, @NullableDecl V defaultValue) {
        validateEndpoints(endpoints);
        return edgeValueOrDefault_internal(endpoints.nodeU(), endpoints.nodeV(), defaultValue);
    }

    /* access modifiers changed from: protected */
    public long edgeCount() {
        return this.edgeCount;
    }

    /* access modifiers changed from: protected */
    public final GraphConnections<N, V> checkedConnections(N node) {
        GraphConnections<N, V> connections = this.nodeConnections.get(node);
        if (connections != null) {
            return connections;
        }
        Preconditions.checkNotNull(node);
        throw new IllegalArgumentException("Node " + node + " is not an element of this graph.");
    }

    /* access modifiers changed from: protected */
    public final boolean containsNode(@NullableDecl N node) {
        return this.nodeConnections.containsKey(node);
    }

    /* access modifiers changed from: protected */
    public final boolean hasEdgeConnecting_internal(N nodeU, N nodeV) {
        GraphConnections<N, V> connectionsU = this.nodeConnections.get(nodeU);
        return connectionsU != null && connectionsU.successors().contains(nodeV);
    }

    /* access modifiers changed from: protected */
    public final V edgeValueOrDefault_internal(N nodeU, N nodeV, V defaultValue) {
        GraphConnections<N, V> connectionsU = this.nodeConnections.get(nodeU);
        V value = connectionsU == null ? null : connectionsU.value(nodeV);
        return value == null ? defaultValue : value;
    }
}
