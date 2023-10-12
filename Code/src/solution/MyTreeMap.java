package solution;

import java.util.*;

public class MyTreeMap<K, V> implements Map<K, V> {

    private int size = 0;
    private Node root = null;

    protected class Node {
        public K key;
        public V value;
        public Node left = null;
        public Node right = null;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public boolean containsKey(Object target) {
        return findNode(target) != null;
    }

    /**
     * @param target 우리가 찾는 키
     * @return 키가 존재하면 Node, 없다면 null
     */
    private Node findNode(Object target) {
        if (target == null) {
            throw new IllegalArgumentException();
        }

        @SuppressWarnings("unchecked")
        Comparable<? super K> k = (Comparable<? super K>) target;

        Node findNode = root;

        while(findNode != null) {
            // target이 더 클 시
            if(k.compareTo(findNode.key) > 0) {
                findNode = findNode.right;
            }
            // target이 더 작을 시
            else if(k.compareTo(findNode.key) < 0) {
                findNode = findNode.left;
            }
            // 같을 때
            else {
                return findNode;
            }
        }

        return null;
    }

    private boolean equals(Object target, Object obj) {
        if (target == null) {
            return obj == null;
        }
        return target.equals(obj);
    }

    @Override
    public boolean containsValue(Object target) {
        return containsValueHelper(root, target);
    }

    private boolean containsValueHelper(Node node, Object target) {
        for (V value : values()) {
            if(equals(target, value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V get(Object key) {
        Node node = findNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new LinkedHashSet<K>();
        Deque<Node> stack = new LinkedList<Node>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node == null) continue;
            set.add(node.key);
            stack.push(node.left);
            stack.push(node.right);
        }
        return set;
    }

    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException();
        }
        if (root == null) {
            root = new Node(key, value);
            size++;
            return null;
        }
        return putHelper(root, key, value);
    }

    /**
     * @param node 노드
     * @param key 노드의 키
     * @param value 노드의 값
     * @return key가 이미 있다면 기존 값을 반환, key가 없다면 null을 반환
     */
    private V putHelper(Node node, K key, V value) {
        Node oldNode = findNode(key);
        if(oldNode == null) {
            Node newNode = makeNode(key, value);
            Node findNode = root;
            Comparable<? super K> k = (Comparable<? super K>) key;
            while(findNode != null) {
                if(k.compareTo(findNode.key) > 0) {
                    if(findNode.right != null) {
                        findNode = findNode.right;
                    } else {
                        findNode.right = newNode;
                        size++;
                        return null;
                    }
                } else {
                    if(findNode.left != null) {
                        findNode = findNode.left;
                    } else {
                        findNode.left = newNode;
                        size++;
                        return null;
                    }
                }
            }
            return null;
        } else {
            V oldValue = oldNode.value;
            oldNode.value = value;
            return oldValue;
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry: map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(Object key) {
        // OPTIONAL TODO: FILL THIS IN!
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Collection<V> values() {
        Set<V> set = new HashSet<V>();
        Deque<Node> stack = new LinkedList<Node>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node == null) continue;
            set.add(node.value);
            stack.push(node.left);
            stack.push(node.right);
        }
        return set;
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new MyTreeMap<String, Integer>();
        map.put("Word1", 1);
        map.put("Word2", 2);
        Integer value = map.get("Word1");
        System.out.println(value);

        for (String key: map.keySet()) {
            System.out.println(key + ", " + map.get(key));
        }
    }

    public MyTreeMap<K, V>.Node makeNode(K key, V value) {
        return new Node(key, value);
    }

    public void setTree(Node node, int size ) {
        this.root = node;
        this.size = size;
    }

    public int height() {
        return heightHelper(root);
    }

    private int heightHelper(Node node) {
        if (node == null) {
            return 0;
        }
        int left = heightHelper(node.left);
        int right = heightHelper(node.right);
        return Math.max(left, right) + 1;
    }
}