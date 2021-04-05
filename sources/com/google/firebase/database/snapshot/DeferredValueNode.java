package com.google.firebase.database.snapshot;

import com.google.firebase.database.snapshot.LeafNode;
import com.google.firebase.database.snapshot.Node;
import java.util.Map;

public class DeferredValueNode extends LeafNode<DeferredValueNode> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Map<Object, Object> value;

    public DeferredValueNode(Map<Object, Object> value2, Node priority) {
        super(priority);
        this.value = value2;
    }

    public Object getValue() {
        return this.value;
    }

    public String getHashRepresentation(Node.HashVersion version) {
        return getPriorityHash(version) + "deferredValue:" + this.value;
    }

    public DeferredValueNode updatePriority(Node priority) {
        return new DeferredValueNode(this.value, priority);
    }

    /* access modifiers changed from: protected */
    public LeafNode.LeafType getLeafType() {
        return LeafNode.LeafType.DeferredValue;
    }

    /* access modifiers changed from: protected */
    public int compareLeafValues(DeferredValueNode other) {
        return 0;
    }

    public boolean equals(Object other) {
        if (!(other instanceof DeferredValueNode)) {
            return false;
        }
        DeferredValueNode otherDeferredValueNode = (DeferredValueNode) other;
        if (!this.value.equals(otherDeferredValueNode.value) || !this.priority.equals(otherDeferredValueNode.priority)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.value.hashCode() + this.priority.hashCode();
    }
}
