package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.UnmodifiableIterator;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class Traverser<N> {

    private enum Order {
        PREORDER,
        POSTORDER
    }

    public abstract Iterable<N> breadthFirst(Iterable<? extends N> iterable);

    public abstract Iterable<N> breadthFirst(N n);

    public abstract Iterable<N> depthFirstPostOrder(Iterable<? extends N> iterable);

    public abstract Iterable<N> depthFirstPostOrder(N n);

    public abstract Iterable<N> depthFirstPreOrder(Iterable<? extends N> iterable);

    public abstract Iterable<N> depthFirstPreOrder(N n);

    public static <N> Traverser<N> forGraph(SuccessorsFunction<N> graph) {
        Preconditions.checkNotNull(graph);
        return new GraphTraverser(graph);
    }

    public static <N> Traverser<N> forTree(SuccessorsFunction<N> tree) {
        Preconditions.checkNotNull(tree);
        if (tree instanceof BaseGraph) {
            Preconditions.checkArgument(((BaseGraph) tree).isDirected(), "Undirected graphs can never be trees.");
        }
        if (tree instanceof Network) {
            Preconditions.checkArgument(((Network) tree).isDirected(), "Undirected networks can never be trees.");
        }
        return new TreeTraverser(tree);
    }

    private Traverser() {
    }

    private static final class GraphTraverser<N> extends Traverser<N> {
        /* access modifiers changed from: private */
        public final SuccessorsFunction<N> graph;

        GraphTraverser(SuccessorsFunction<N> graph2) {
            super();
            this.graph = (SuccessorsFunction) Preconditions.checkNotNull(graph2);
        }

        public Iterable<N> breadthFirst(N startNode) {
            Preconditions.checkNotNull(startNode);
            return breadthFirst(ImmutableSet.m470of(startNode));
        }

        public Iterable<N> breadthFirst(final Iterable<? extends N> startNodes) {
            Preconditions.checkNotNull(startNodes);
            if (Iterables.isEmpty(startNodes)) {
                return ImmutableSet.m469of();
            }
            for (N startNode : startNodes) {
                checkThatNodeIsInGraph(startNode);
            }
            return new Iterable<N>() {
                public Iterator<N> iterator() {
                    return new BreadthFirstIterator(startNodes);
                }
            };
        }

        public Iterable<N> depthFirstPreOrder(N startNode) {
            Preconditions.checkNotNull(startNode);
            return depthFirstPreOrder(ImmutableSet.m470of(startNode));
        }

        public Iterable<N> depthFirstPreOrder(final Iterable<? extends N> startNodes) {
            Preconditions.checkNotNull(startNodes);
            if (Iterables.isEmpty(startNodes)) {
                return ImmutableSet.m469of();
            }
            for (N startNode : startNodes) {
                checkThatNodeIsInGraph(startNode);
            }
            return new Iterable<N>() {
                public Iterator<N> iterator() {
                    return new DepthFirstIterator(startNodes, Order.PREORDER);
                }
            };
        }

        public Iterable<N> depthFirstPostOrder(N startNode) {
            Preconditions.checkNotNull(startNode);
            return depthFirstPostOrder(ImmutableSet.m470of(startNode));
        }

        public Iterable<N> depthFirstPostOrder(final Iterable<? extends N> startNodes) {
            Preconditions.checkNotNull(startNodes);
            if (Iterables.isEmpty(startNodes)) {
                return ImmutableSet.m469of();
            }
            for (N startNode : startNodes) {
                checkThatNodeIsInGraph(startNode);
            }
            return new Iterable<N>() {
                public Iterator<N> iterator() {
                    return new DepthFirstIterator(startNodes, Order.POSTORDER);
                }
            };
        }

        private void checkThatNodeIsInGraph(N startNode) {
            this.graph.successors(startNode);
        }

        private final class BreadthFirstIterator extends UnmodifiableIterator<N> {
            private final Queue<N> queue = new ArrayDeque();
            private final Set<N> visited = new HashSet();

            BreadthFirstIterator(Iterable<? extends N> roots) {
                for (N root : roots) {
                    if (this.visited.add(root)) {
                        this.queue.add(root);
                    }
                }
            }

            public boolean hasNext() {
                return !this.queue.isEmpty();
            }

            public N next() {
                N current = this.queue.remove();
                for (N neighbor : GraphTraverser.this.graph.successors(current)) {
                    if (this.visited.add(neighbor)) {
                        this.queue.add(neighbor);
                    }
                }
                return current;
            }
        }

        private final class DepthFirstIterator extends AbstractIterator<N> {
            private final Order order;
            private final Deque<GraphTraverser<N>.DepthFirstIterator.NodeAndSuccessors> stack = new ArrayDeque();
            private final Set<N> visited = new HashSet();

            DepthFirstIterator(Iterable<? extends N> roots, Order order2) {
                this.stack.push(new NodeAndSuccessors(null, roots));
                this.order = order2;
            }

            /* access modifiers changed from: protected */
            public N computeNext() {
                while (!this.stack.isEmpty()) {
                    GraphTraverser<N>.DepthFirstIterator.NodeAndSuccessors nodeAndSuccessors = (NodeAndSuccessors) this.stack.getFirst();
                    boolean firstVisit = this.visited.add(nodeAndSuccessors.node);
                    boolean produceNode = true;
                    boolean lastVisit = !nodeAndSuccessors.successorIterator.hasNext();
                    if ((!firstVisit || this.order != Order.PREORDER) && (!lastVisit || this.order != Order.POSTORDER)) {
                        produceNode = false;
                    }
                    if (lastVisit) {
                        this.stack.pop();
                    } else {
                        N successor = nodeAndSuccessors.successorIterator.next();
                        if (!this.visited.contains(successor)) {
                            this.stack.push(withSuccessors(successor));
                        }
                    }
                    if (produceNode && nodeAndSuccessors.node != null) {
                        return nodeAndSuccessors.node;
                    }
                }
                return endOfData();
            }

            /* access modifiers changed from: package-private */
            public GraphTraverser<N>.DepthFirstIterator.NodeAndSuccessors withSuccessors(N node) {
                return new NodeAndSuccessors(node, GraphTraverser.this.graph.successors(node));
            }

            private final class NodeAndSuccessors {
                @NullableDecl
                final N node;
                final Iterator<? extends N> successorIterator;

                NodeAndSuccessors(@NullableDecl N node2, Iterable<? extends N> successors) {
                    this.node = node2;
                    this.successorIterator = successors.iterator();
                }
            }
        }
    }

    private static final class TreeTraverser<N> extends Traverser<N> {
        /* access modifiers changed from: private */
        public final SuccessorsFunction<N> tree;

        TreeTraverser(SuccessorsFunction<N> tree2) {
            super();
            this.tree = (SuccessorsFunction) Preconditions.checkNotNull(tree2);
        }

        public Iterable<N> breadthFirst(N startNode) {
            Preconditions.checkNotNull(startNode);
            return breadthFirst(ImmutableSet.m470of(startNode));
        }

        public Iterable<N> breadthFirst(final Iterable<? extends N> startNodes) {
            Preconditions.checkNotNull(startNodes);
            if (Iterables.isEmpty(startNodes)) {
                return ImmutableSet.m469of();
            }
            for (N startNode : startNodes) {
                checkThatNodeIsInTree(startNode);
            }
            return new Iterable<N>() {
                public Iterator<N> iterator() {
                    return new BreadthFirstIterator(startNodes);
                }
            };
        }

        public Iterable<N> depthFirstPreOrder(N startNode) {
            Preconditions.checkNotNull(startNode);
            return depthFirstPreOrder(ImmutableSet.m470of(startNode));
        }

        public Iterable<N> depthFirstPreOrder(final Iterable<? extends N> startNodes) {
            Preconditions.checkNotNull(startNodes);
            if (Iterables.isEmpty(startNodes)) {
                return ImmutableSet.m469of();
            }
            for (N node : startNodes) {
                checkThatNodeIsInTree(node);
            }
            return new Iterable<N>() {
                public Iterator<N> iterator() {
                    return new DepthFirstPreOrderIterator(startNodes);
                }
            };
        }

        public Iterable<N> depthFirstPostOrder(N startNode) {
            Preconditions.checkNotNull(startNode);
            return depthFirstPostOrder(ImmutableSet.m470of(startNode));
        }

        public Iterable<N> depthFirstPostOrder(final Iterable<? extends N> startNodes) {
            Preconditions.checkNotNull(startNodes);
            if (Iterables.isEmpty(startNodes)) {
                return ImmutableSet.m469of();
            }
            for (N startNode : startNodes) {
                checkThatNodeIsInTree(startNode);
            }
            return new Iterable<N>() {
                public Iterator<N> iterator() {
                    return new DepthFirstPostOrderIterator(startNodes);
                }
            };
        }

        private void checkThatNodeIsInTree(N startNode) {
            this.tree.successors(startNode);
        }

        private final class BreadthFirstIterator extends UnmodifiableIterator<N> {
            private final Queue<N> queue = new ArrayDeque();

            BreadthFirstIterator(Iterable<? extends N> roots) {
                for (N root : roots) {
                    this.queue.add(root);
                }
            }

            public boolean hasNext() {
                return !this.queue.isEmpty();
            }

            public N next() {
                N current = this.queue.remove();
                Iterables.addAll(this.queue, TreeTraverser.this.tree.successors(current));
                return current;
            }
        }

        private final class DepthFirstPreOrderIterator extends UnmodifiableIterator<N> {
            private final Deque<Iterator<? extends N>> stack;

            DepthFirstPreOrderIterator(Iterable<? extends N> roots) {
                ArrayDeque arrayDeque = new ArrayDeque();
                this.stack = arrayDeque;
                arrayDeque.addLast(roots.iterator());
            }

            public boolean hasNext() {
                return !this.stack.isEmpty();
            }

            public N next() {
                Iterator<? extends N> iterator = this.stack.getLast();
                N result = Preconditions.checkNotNull(iterator.next());
                if (!iterator.hasNext()) {
                    this.stack.removeLast();
                }
                Iterator<? extends N> childIterator = TreeTraverser.this.tree.successors(result).iterator();
                if (childIterator.hasNext()) {
                    this.stack.addLast(childIterator);
                }
                return result;
            }
        }

        private final class DepthFirstPostOrderIterator extends AbstractIterator<N> {
            private final ArrayDeque<TreeTraverser<N>.DepthFirstPostOrderIterator.NodeAndChildren> stack;

            DepthFirstPostOrderIterator(Iterable<? extends N> roots) {
                ArrayDeque<TreeTraverser<N>.DepthFirstPostOrderIterator.NodeAndChildren> arrayDeque = new ArrayDeque<>();
                this.stack = arrayDeque;
                arrayDeque.addLast(new NodeAndChildren(null, roots));
            }

            /* access modifiers changed from: protected */
            public N computeNext() {
                while (!this.stack.isEmpty()) {
                    TreeTraverser<N>.DepthFirstPostOrderIterator.NodeAndChildren top = (NodeAndChildren) this.stack.getLast();
                    if (top.childIterator.hasNext()) {
                        this.stack.addLast(withChildren(top.childIterator.next()));
                    } else {
                        this.stack.removeLast();
                        if (top.node != null) {
                            return top.node;
                        }
                    }
                }
                return endOfData();
            }

            /* access modifiers changed from: package-private */
            public TreeTraverser<N>.DepthFirstPostOrderIterator.NodeAndChildren withChildren(N node) {
                return new NodeAndChildren(node, TreeTraverser.this.tree.successors(node));
            }

            private final class NodeAndChildren {
                final Iterator<? extends N> childIterator;
                @NullableDecl
                final N node;

                NodeAndChildren(@NullableDecl N node2, Iterable<? extends N> children) {
                    this.node = node2;
                    this.childIterator = children.iterator();
                }
            }
        }
    }
}
