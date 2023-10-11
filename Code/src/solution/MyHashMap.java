package solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyHashMap<K, V> extends MyBetterMap<K, V> implements Map<K, V> {

    // 재해시하기 전 하위 맵당 평균 엔트리 개수
    protected static final double FACTOR = 1.0;

    @Override
    public V put(K key, V value) {
        V oldValue = super.put(key, value);

        //System.out.println("Put " + key + " in " + map + " size now " + map.size());

        // 하위 맵당 엔트리의 개수가 임계치를 넘지 않는지 확인
        if (size() > maps.size() * FACTOR) {
            rehash();
        }
        return oldValue;
    }

    /**
     * 호출될 때마다 내장된 맵의 개수 k가 2배가 되어야 함
     */
    protected void rehash() {
        // 엔트리 수집
        List<MyLinearMap<K, V>> oldMaps = new ArrayList<>(maps.size());
        oldMaps.addAll(maps);

        // 맵의 크기 2배
        makeMaps(maps.size()*2);

        // 엔트리 다시 넣기
        maps.addAll(oldMaps);
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new MyHashMap<String, Integer>();
        for (int i=0; i<10; i++) {
            map.put(i+"", i);
        }
        Integer value = map.get("3");
        System.out.println(value);
    }
}