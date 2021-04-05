package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.snapshot.LeafNode;
import com.google.firebase.database.snapshot.Node;

public class StringNode extends LeafNode<StringNode> {
    private final String value;

    public StringNode(String value2, Node priority) {
        super(priority);
        this.value = value2;
    }

    public Object getValue() {
        return this.value;
    }

    /* renamed from: com.google.firebase.database.snapshot.StringNode$1 */
    static /* synthetic */ class C18761 {

        /* renamed from: $SwitchMap$com$google$firebase$database$snapshot$Node$HashVersion */
        static final /* synthetic */ int[] f407x2aed15f4;

        static {
            int[] iArr = new int[Node.HashVersion.values().length];
            f407x2aed15f4 = iArr;
            try {
                iArr[Node.HashVersion.V1.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f407x2aed15f4[Node.HashVersion.V2.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public String getHashRepresentation(Node.HashVersion version) {
        int i = C18761.f407x2aed15f4[version.ordinal()];
        if (i == 1) {
            return getPriorityHash(version) + "string:" + this.value;
        } else if (i == 2) {
            return getPriorityHash(version) + "string:" + Utilities.stringHashV2Representation(this.value);
        } else {
            throw new IllegalArgumentException("Invalid hash version for string node: " + version);
        }
    }

    public StringNode updatePriority(Node priority) {
        return new StringNode(this.value, priority);
    }

    /* access modifiers changed from: protected */
    public LeafNode.LeafType getLeafType() {
        return LeafNode.LeafType.String;
    }

    /* access modifiers changed from: protected */
    public int compareLeafValues(StringNode other) {
        return this.value.compareTo(other.value);
    }

    public boolean equals(Object other) {
        if (!(other instanceof StringNode)) {
            return false;
        }
        StringNode otherStringNode = (StringNode) other;
        if (!this.value.equals(otherStringNode.value) || !this.priority.equals(otherStringNode.priority)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.value.hashCode() + this.priority.hashCode();
    }
}
