package solution;

import java.util.Map;

public class MyFixedHashMap<K, V> extends MyHashMap<K, V> implements Map<K, V> {

    private int size = 0;

    @Override
    public void clear() {
        super.clear();
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        MyLinearMap<K, V> map = chooseMap(key);
        size -= map.size();
        V oldValue = map.put(key, value);
        size += map.size();

        if (size() > maps.size() * FACTOR) {
            size = 0;
            rehash();
        }
        return oldValue;
    }

    @Override
    public V remove(Object key) {
        MyLinearMap<K, V> map = chooseMap(key); // 올바른 하위 맵을 찾음
        size -= map.size(); // 하위 맵의 크기를 뺌
        V oldValue = map.remove(key);
        size += map.size(); // 하위 맵의 새로운 크기를 더함
        return oldValue;
    }

    @Override
    public int size() {
        return size;
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new MyFixedHashMap<String, Integer>();
        for (int i=0; i<10; i++) {
            map.put(i+"", i);
        }
        Integer value = map.get("3");
        System.out.println(value);
    }
}