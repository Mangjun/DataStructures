package solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyLinearMap<K, V> implements Map<K, V> {

    private List<Entry> entries = new ArrayList<Entry>();

    public class Entry implements Map.Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }
        @Override
        public V getValue() {
            return value;
        }
        @Override
        public V setValue(V newValue) {
            value = newValue;
            return value;
        }
    }

    @Override
    public void clear() {
        entries.clear();
    }

    @Override
    public boolean containsKey(Object target) {
        return findEntry(target) != null;
    }

    /**
     * key 값을 받아 entry를 검색
     * key 값을 포함하는 entry를 반환하거나 없다면 null을 반환
     * equals 사용
     */
    private Entry findEntry(Object target) {
        // 최적화 코드
        for(Entry entry : entries) {
            if(equals(target, entry.getKey())) {
                return entry;
            }
        }
        return null;

//        내가 짠 코드
//        for(int i=0; i<entries.size(); i++) {
//            if(target.equals(entries.get(i).getKey())) {
//                return entries.get(i);
//            }
//        }
//        return null;
    } // 선형

    private boolean equals(Object target, Object obj) {
        if (target == null) {
            return obj == null;
        }
        return target.equals(obj);
    } // 상수 시간

    @Override
    public boolean containsValue(Object target) {
        for (Map.Entry<K, V> entry: entries) {
            if (equals(target, entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * key 값이 존재한다면 value를 반환
     * 없다면 null을 반환
     */
    @Override
    public V get(Object key) {
        // 최적화 코드
        Entry entry = findEntry(key);
        if(entry == null) {
            return null;
        }
        return  entry.getValue();
//        내가 짠 코드
//        if(containsKey(key)) {
//            return findEntry(key).getValue();
//        } else {
//            return null;
//        }
    } // 선형 -> findEntry가  선형이어서

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<K>();
        for (Entry entry: entries) {
            set.add(entry.getKey());
        }
        return set;
    }

    /**
     * 새로운 entry 추가
     */
    @Override
    public V put(K key, V value) {
        // 최적화 코드
        Entry entry = findEntry(key);
        if(entry == null) {
            entries.add(new Entry(key, value));
            return null;
        } else {
            V oldValue = entry.getValue();
            entry.setValue(value);
            return oldValue;
        }
//        내가 짠 코드
//        if(containsKey(key)) {
//            return null;
//        } else {
//            entries.add(new Entry(key, value));
//            return value;
//        }
    } // 선형 -> findEntry가  선형이어서

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry: map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * key 값이 존재하면 삭제
     * 없다면 null을 반환
     */
    @Override
    public V remove(Object key) {
        // 최적화 코드
        Entry entry = findEntry(key);
        if(entry == null) {
            return null;
        } else {
            V value = entry.getValue();
            entries.remove(entry);
            return value;
        }
//        내가 짠 코드
//        if(containsKey(key)) {
//            V value = findEntry(key).getValue();
//            entries.remove(findEntry(key));
//            return value;
//        } else {
//            return null;
//        }
    } // 선형

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public Collection<V> values() {
        Set<V> set = new HashSet<V>();
        for (Entry entry: entries) {
            set.add(entry.getValue());
        }
        return set;
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new MyLinearMap<String, Integer>();
        map.put("Word1", 1);
        map.put("Word2", 2);
        Integer value = map.get("Word1");
        System.out.println(value);

        for (String key: map.keySet()) {
            System.out.println(key + ", " + map.get(key));
        }
    }

    protected Collection<? extends java.util.Map.Entry<K, V>> getEntries() {
        return entries;
    }
}