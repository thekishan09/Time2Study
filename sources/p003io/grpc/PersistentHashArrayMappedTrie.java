package p003io.grpc;

import java.util.Arrays;

/* renamed from: io.grpc.PersistentHashArrayMappedTrie */
final class PersistentHashArrayMappedTrie<K, V> {
    private final Node<K, V> root;

    /* renamed from: io.grpc.PersistentHashArrayMappedTrie$Node */
    interface Node<K, V> {
        V get(K k, int i, int i2);

        Node<K, V> put(K k, V v, int i, int i2);

        int size();
    }

    PersistentHashArrayMappedTrie() {
        this((Node) null);
    }

    private PersistentHashArrayMappedTrie(Node<K, V> root2) {
        this.root = root2;
    }

    public int size() {
        Node<K, V> node = this.root;
        if (node == null) {
            return 0;
        }
        return node.size();
    }

    public V get(K key) {
        Node<K, V> node = this.root;
        if (node == null) {
            return null;
        }
        return node.get(key, key.hashCode(), 0);
    }

    public PersistentHashArrayMappedTrie<K, V> put(K key, V value) {
        if (this.root == null) {
            return new PersistentHashArrayMappedTrie<>(new Leaf(key, value));
        }
        return new PersistentHashArrayMappedTrie<>(this.root.put(key, value, key.hashCode(), 0));
    }

    /* renamed from: io.grpc.PersistentHashArrayMappedTrie$Leaf */
    static final class Leaf<K, V> implements Node<K, V> {
        private final K key;
        private final V value;

        public Leaf(K key2, V value2) {
            this.key = key2;
            this.value = value2;
        }

        public int size() {
            return 1;
        }

        public V get(K key2, int hash, int bitsConsumed) {
            if (this.key == key2) {
                return this.value;
            }
            return null;
        }

        public Node<K, V> put(K key2, V value2, int hash, int bitsConsumed) {
            int thisHash = this.key.hashCode();
            if (thisHash != hash) {
                return CompressedIndex.combine(new Leaf(key2, value2), hash, this, thisHash, bitsConsumed);
            }
            if (this.key == key2) {
                return new Leaf(key2, value2);
            }
            return new CollisionLeaf(this.key, this.value, key2, value2);
        }

        public String toString() {
            return String.format("Leaf(key=%s value=%s)", new Object[]{this.key, this.value});
        }
    }

    /* renamed from: io.grpc.PersistentHashArrayMappedTrie$CollisionLeaf */
    static final class CollisionLeaf<K, V> implements Node<K, V> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final K[] keys;
        private final V[] values;

        static {
            Class<PersistentHashArrayMappedTrie> cls = PersistentHashArrayMappedTrie.class;
        }

        CollisionLeaf(K key1, V value1, K key2, V value2) {
            this(new Object[]{key1, key2}, new Object[]{value1, value2});
        }

        private CollisionLeaf(K[] keys2, V[] values2) {
            this.keys = keys2;
            this.values = values2;
        }

        public int size() {
            return this.values.length;
        }

        public V get(K key, int hash, int bitsConsumed) {
            int i = 0;
            while (true) {
                K[] kArr = this.keys;
                if (i >= kArr.length) {
                    return null;
                }
                if (kArr[i] == key) {
                    return this.values[i];
                }
                i++;
            }
        }

        public Node<K, V> put(K key, V value, int hash, int bitsConsumed) {
            int thisHash = this.keys[0].hashCode();
            if (thisHash != hash) {
                return CompressedIndex.combine(new Leaf(key, value), hash, this, thisHash, bitsConsumed);
            }
            int indexOfKey = indexOfKey(key);
            int keyIndex = indexOfKey;
            if (indexOfKey != -1) {
                K[] kArr = this.keys;
                K[] newKeys = Arrays.copyOf(kArr, kArr.length);
                V[] newValues = Arrays.copyOf(this.values, this.keys.length);
                newKeys[keyIndex] = key;
                newValues[keyIndex] = value;
                return new CollisionLeaf(newKeys, newValues);
            }
            K[] newKeys2 = this.keys;
            K[] newKeys3 = Arrays.copyOf(newKeys2, newKeys2.length + 1);
            V[] newValues2 = Arrays.copyOf(this.values, this.keys.length + 1);
            K[] kArr2 = this.keys;
            newKeys3[kArr2.length] = key;
            newValues2[kArr2.length] = value;
            return new CollisionLeaf(newKeys3, newValues2);
        }

        private int indexOfKey(K key) {
            int i = 0;
            while (true) {
                K[] kArr = this.keys;
                if (i >= kArr.length) {
                    return -1;
                }
                if (kArr[i] == key) {
                    return i;
                }
                i++;
            }
        }

        public String toString() {
            StringBuilder valuesSb = new StringBuilder();
            valuesSb.append("CollisionLeaf(");
            for (int i = 0; i < this.values.length; i++) {
                valuesSb.append("(key=");
                valuesSb.append(this.keys[i]);
                valuesSb.append(" value=");
                valuesSb.append(this.values[i]);
                valuesSb.append(") ");
            }
            valuesSb.append(")");
            return valuesSb.toString();
        }
    }

    /* renamed from: io.grpc.PersistentHashArrayMappedTrie$CompressedIndex */
    static final class CompressedIndex<K, V> implements Node<K, V> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int BITS = 5;
        private static final int BITS_MASK = 31;
        final int bitmap;
        private final int size;
        final Node<K, V>[] values;

        static {
            Class<PersistentHashArrayMappedTrie> cls = PersistentHashArrayMappedTrie.class;
        }

        private CompressedIndex(int bitmap2, Node<K, V>[] values2, int size2) {
            this.bitmap = bitmap2;
            this.values = values2;
            this.size = size2;
        }

        public int size() {
            return this.size;
        }

        public V get(K key, int hash, int bitsConsumed) {
            int indexBit = indexBit(hash, bitsConsumed);
            if ((this.bitmap & indexBit) == 0) {
                return null;
            }
            return this.values[compressedIndex(indexBit)].get(key, hash, bitsConsumed + 5);
        }

        public Node<K, V> put(K key, V value, int hash, int bitsConsumed) {
            int indexBit = indexBit(hash, bitsConsumed);
            int compressedIndex = compressedIndex(indexBit);
            int i = this.bitmap;
            if ((i & indexBit) == 0) {
                Node<K, V>[] nodeArr = this.values;
                Node<K, V>[] newValues = new Node[(nodeArr.length + 1)];
                System.arraycopy(nodeArr, 0, newValues, 0, compressedIndex);
                newValues[compressedIndex] = new Leaf<>(key, value);
                Node<K, V>[] nodeArr2 = this.values;
                System.arraycopy(nodeArr2, compressedIndex, newValues, compressedIndex + 1, nodeArr2.length - compressedIndex);
                return new CompressedIndex(i | indexBit, newValues, size() + 1);
            }
            Node<K, V>[] nodeArr3 = this.values;
            Node<K, V>[] newValues2 = (Node[]) Arrays.copyOf(nodeArr3, nodeArr3.length);
            newValues2[compressedIndex] = this.values[compressedIndex].put(key, value, hash, bitsConsumed + 5);
            return new CompressedIndex(this.bitmap, newValues2, (size() + newValues2[compressedIndex].size()) - this.values[compressedIndex].size());
        }

        static <K, V> Node<K, V> combine(Node<K, V> node1, int hash1, Node<K, V> node2, int hash2, int bitsConsumed) {
            int indexBit1 = indexBit(hash1, bitsConsumed);
            int indexBit2 = indexBit(hash2, bitsConsumed);
            if (indexBit1 == indexBit2) {
                Node<K, V> node = combine(node1, hash1, node2, hash2, bitsConsumed + 5);
                return new CompressedIndex(indexBit1, new Node[]{node}, node.size());
            }
            if (uncompressedIndex(hash1, bitsConsumed) > uncompressedIndex(hash2, bitsConsumed)) {
                Node<K, V> nodeCopy = node1;
                node1 = node2;
                node2 = nodeCopy;
            }
            return new CompressedIndex(indexBit1 | indexBit2, new Node[]{node1, node2}, node1.size() + node2.size());
        }

        public String toString() {
            StringBuilder valuesSb = new StringBuilder();
            valuesSb.append("CompressedIndex(");
            valuesSb.append(String.format("bitmap=%s ", new Object[]{Integer.toBinaryString(this.bitmap)}));
            for (Node<K, V> value : this.values) {
                valuesSb.append(value);
                valuesSb.append(" ");
            }
            valuesSb.append(")");
            return valuesSb.toString();
        }

        private int compressedIndex(int indexBit) {
            return Integer.bitCount(this.bitmap & (indexBit - 1));
        }

        private static int uncompressedIndex(int hash, int bitsConsumed) {
            return (hash >>> bitsConsumed) & 31;
        }

        private static int indexBit(int hash, int bitsConsumed) {
            return 1 << uncompressedIndex(hash, bitsConsumed);
        }
    }
}
