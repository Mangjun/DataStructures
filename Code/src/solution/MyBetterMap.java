package solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyBetterMap<K, V> implements Map<K, V> {

    protected List<MyLinearMap<K, V>> maps;

    public MyBetterMap() {
        makeMaps(2);
    }

    protected void makeMaps(int k) {
        maps = new ArrayList<MyLinearMap<K, V>>(k);
        for (int i=0; i<k; i++) {
            maps.add(new MyLinearMap<K, V>());
        }
    }

    @Override
    public void clear() {
        for (int i=0; i<maps.size(); i++) {
            maps.get(i).clear();
        }
    }

    protected MyLinearMap<K, V> chooseMap(Object key) {
        int index = key==null ? 0 : Math.abs(key.hashCode()) % maps.size();
        return maps.get(index);
    }

    /**
     * chooseMap 사용
     * @return 키를 포함?
     */
    @Override
    public boolean containsKey(Object target) {
        return chooseMap(target).containsKey(target);
    } // 선형

    /**
     * chooseMap 사용 X
     * @return 값을 포함?
     */
    @Override
    public boolean containsValue(Object target) {
        for(MyLinearMap<K, V> entry : maps) {
            if(entry.containsValue(target)) {
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
        MyLinearMap<K, V> map = chooseMap(key);
        return map.get(key);
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<K>();
        for (MyLinearMap<K, V> map: maps) {
            set.addAll(map.keySet());
        }
        return set;
    }

    @Override
    public V put(K key, V value) {
        MyLinearMap<K, V> map = chooseMap(key);
        return map.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry: map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V remove(Object key) {
        MyLinearMap<K, V> map = chooseMap(key);
        return map.remove(key);
    }

    @Override
    public int size() {
        int total = 0;
        for (MyLinearMap<K, V> map: maps) {
            total += map.size();
        }
        return total;
    }

    @Override
    public Collection<V> values() {
        Set<V> set = new HashSet<V>();
        for (MyLinearMap<K, V> map: maps) {
            set.addAll(map.values());
        }
        return set;
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new MyBetterMap<String, Integer>();
        map.put("Word1", 1);
        map.put("Word2", 2);
        Integer value = map.get("Word1");
        System.out.println(value);

        for (String key: map.keySet()) {
            System.out.println(key + ", " + map.get(key));
        }
    }
}