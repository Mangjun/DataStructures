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

        // 컴파일러 경고 무시
        @SuppressWarnings("unchecked")
        Comparable<? super K> k = (Comparable<? super K>) target;

        // 실제 탐색
        // 최적화 코드
        Node node = root;
        while(node != null) {
            int cmp = k.compareTo(node.key);
            if(cmp < 0)
                node = node.left;
            else if(cmp > 0)
                node = node.right;
            else
                return node;
        // 내가 짠 코드
//        Node findNode = root;
//        while(findNode != null) {
//            // target이 더 클 시
//            if(k.compareTo(findNode.key) > 0) {
//                findNode = findNode.right;
//            }
//            // target이 더 작을 시
//            else if(k.compareTo(findNode.key) < 0) {
//                findNode = findNode.left;
//            }
//            // 같을 때
//            else {
//                return findNode;
//            }
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
        // 최적화 코드
        // 재귀의 기저 사례를 검사
        if(node == null) {
            return false;
        }
        // 원하는 것을 찾았는지 확인
        if(equals(target, node.value)) {
            return true;
        }
        // 왼쪽 하위 트리에서 target을 찾는 재귀호출
        if(containsValueHelper(node.left, target)) {
            return true;
        }
        // 오른쪽 하위 트리에서 target을 찾는 재귀호출
        if(containsValueHelper(node.right, target)) {
            return true;
        }
        // 내가 짠 코드
//        for (V value : values()) {
//            if(equals(target, value)) {
//                return true;
//            }
//        }
//        return false;
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
        // 최적화 코드
        Set<K> set = new LinkedHashSet<>();
        addInOrder(root, set);
        return set;
        // 내가 짠 코드
//        Set<K> set = new LinkedHashSet<K>();
//        Deque<Node> stack = new LinkedList<Node>();
//        stack.push(root);
//        while (!stack.isEmpty()) {
//            Node node = stack.pop();
//            if (node == null) continue;
//            set.add(node.key);
//            stack.push(node.left);
//            stack.push(node.right);
//        }
//        return set;
    }

    // 트리의 중위 순회
    private void addInOrder(Node node, Set<K> set) {
        if(node == null) return;
        addInOrder(node.left, set);
        set.add(node.key);
        addInOrder(node.right, set);
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
        // 최적화 코드
        Comparable<? super K> k = (Comparable<? super K>) key;
        int cmp = k.compareTo(node.key);

        if(cmp < 0) {
            if(node.left == null) {
                node.left = new Node(key, value);
                size++;
                return null;
            } else {
                return putHelper(node.left, key, value);
            }
        }
        if(cmp > 0) {
            if(node.right == null) {
                node.right = new Node(key, value);
                size++;
                return null;
            } else {
                return putHelper(node.right, key, value);
            }
        }
        V oldValue = node.value;
        node.value = value;
        return oldValue;
        // 내가 짠 코드
//        Node oldNode = findNode(key);
//        if(oldNode == null) {
//            Node newNode = makeNode(key, value);
//            Node findNode = root;
//            Comparable<? super K> k = (Comparable<? super K>) key;
//            while(findNode != null) {
//                if(k.compareTo(findNode.key) > 0) {
//                    if(findNode.right != null) {
//                        findNode = findNode.right;
//                    } else {
//                        findNode.right = newNode;
//                        size++;
//                        return null;
//                    }
//                } else {
//                    if(findNode.left != null) {
//                        findNode = findNode.left;
//                    } else {
//                        findNode.left = newNode;
//                        size++;
//                        return null;
//                    }
//                }
//            }
//            return null;
//        } else {
//            V oldValue = oldNode.value;
//            oldNode.value = value;
//            return oldValue;
//        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry: map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(Object key) {
        Node node = findNode(key);
        // 1번째 경우 - 단말 노드
        if(node.left == null && node.right == null) {
            V oldValue = node.value;
            node = null;
            return oldValue;
        }
        // 2번째 경우 - 자식이 1개인 노드
        if(node.left == null && node.right != null) {
            V oldValue = node.value;
            node = node.left;
            return oldValue;
        }

        if(node.left != null && node.right == null) {
            V oldValue = node.value;
            node = node.right;
            return oldValue;
        }
        // 3번째 경우 - 자식이 2개인 노드
        if(node.left != null && node.right != null) {
            V oldValue = node.value;
//            Comparable<? super V> v = (Comparable<? super V>) oldValue;
//            List<V> value = (List<V>) values();
//            int min = v.compareTo(value.get(0));
//            V minValue = value.get(0);
//            for(V var : value) {
//                int cmp = v.compareTo(var);
//                if(min > cmp) {
//                    minValue = var;
//                }
//            }
            return oldValue;
        }
        return null;
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