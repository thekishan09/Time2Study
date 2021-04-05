package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.snapshot.LeafNode;
import com.google.firebase.database.snapshot.Node;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class LeafNode<T extends LeafNode> implements Node {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private String lazyHash;
    protected final Node priority;

    protected enum LeafType {
        DeferredValue,
        Boolean,
        Number,
        String
    }

    /* access modifiers changed from: protected */
    public abstract int compareLeafValues(T t);

    public abstract boolean equals(Object obj);

    /* access modifiers changed from: protected */
    public abstract LeafType getLeafType();

    public abstract int hashCode();

    LeafNode(Node priority2) {
        this.priority = priority2;
    }

    public boolean hasChild(ChildKey childKey) {
        return false;
    }

    public boolean isLeafNode() {
        return true;
    }

    public Node getPriority() {
        return this.priority;
    }

    public Node getChild(Path path) {
        if (path.isEmpty()) {
            return this;
        }
        if (path.getFront().isPriorityChildName()) {
            return this.priority;
        }
        return EmptyNode.Empty();
    }

    public Node updateChild(Path path, Node node) {
        ChildKey front = path.getFront();
        if (front == null) {
            return node;
        }
        if (!node.isEmpty() || front.isPriorityChildName()) {
            return updateImmediateChild(front, EmptyNode.Empty().updateChild(path.popFront(), node));
        }
        return this;
    }

    public boolean isEmpty() {
        return false;
    }

    public int getChildCount() {
        return 0;
    }

    public ChildKey getPredecessorChildKey(ChildKey childKey) {
        return null;
    }

    public ChildKey getSuccessorChildKey(ChildKey childKey) {
        return null;
    }

    public Node getImmediateChild(ChildKey name) {
        if (name.isPriorityChildName()) {
            return this.priority;
        }
        return EmptyNode.Empty();
    }

    public Object getValue(boolean useExportFormat) {
        if (!useExportFormat || this.priority.isEmpty()) {
            return getValue();
        }
        Map<String, Object> result = new HashMap<>();
        result.put(".value", getValue());
        result.put(".priority", this.priority.getValue());
        return result;
    }

    public Node updateImmediateChild(ChildKey name, Node node) {
        if (name.isPriorityChildName()) {
            return updatePriority(node);
        }
        if (node.isEmpty()) {
            return this;
        }
        return EmptyNode.Empty().updateImmediateChild(name, node).updatePriority(this.priority);
    }

    public String getHash() {
        if (this.lazyHash == null) {
            this.lazyHash = Utilities.sha1HexDigest(getHashRepresentation(Node.HashVersion.V1));
        }
        return this.lazyHash;
    }

    /* renamed from: com.google.firebase.database.snapshot.LeafNode$1 */
    static /* synthetic */ class C18741 {

        /* renamed from: $SwitchMap$com$google$firebase$database$snapshot$Node$HashVersion */
        static final /* synthetic */ int[] f404x2aed15f4;

        static {
            int[] iArr = new int[Node.HashVersion.values().length];
            f404x2aed15f4 = iArr;
            try {
                iArr[Node.HashVersion.V1.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f404x2aed15f4[Node.HashVersion.V2.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public String getPriorityHash(Node.HashVersion version) {
        int i = C18741.f404x2aed15f4[version.ordinal()];
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException("Unknown hash version: " + version);
        } else if (this.priority.isEmpty()) {
            return "";
        } else {
            return "priority:" + this.priority.getHashRepresentation(version) + ":";
        }
    }

    public Iterator<NamedNode> iterator() {
        return Collections.emptyList().iterator();
    }

    public Iterator<NamedNode> reverseIterator() {
        return Collections.emptyList().iterator();
    }

    private static int compareLongDoubleNodes(LongNode longNode, DoubleNode doubleNode) {
        return Double.valueOf((double) ((Long) longNode.getValue()).longValue()).compareTo((Double) doubleNode.getValue());
    }

    public int compareTo(Node other) {
        if (other.isEmpty()) {
            return 1;
        }
        if (other instanceof ChildrenNode) {
            return -1;
        }
        if ((this instanceof LongNode) && (other instanceof DoubleNode)) {
            return compareLongDoubleNodes((LongNode) this, (DoubleNode) other);
        }
        if (!(this instanceof DoubleNode) || !(other instanceof LongNode)) {
            return leafCompare((LeafNode) other);
        }
        return compareLongDoubleNodes((LongNode) other, (DoubleNode) this) * -1;
    }

    /* access modifiers changed from: protected */
    public int leafCompare(LeafNode<?> other) {
        LeafType thisLeafType = getLeafType();
        LeafType otherLeafType = other.getLeafType();
        if (thisLeafType.equals(otherLeafType)) {
            return compareLeafValues(other);
        }
        return thisLeafType.compareTo(otherLeafType);
    }

    public String toString() {
        String str = getValue(true).toString();
        if (str.length() <= 100) {
            return str;
        }
        return str.substring(0, 100) + "...";
    }
}
