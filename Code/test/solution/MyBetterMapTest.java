package solution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MyBetterMapTest {

    public Map<String, Integer> map;

    @BeforeEach
    public void setUp() throws Exception {
        map = new MyBetterMap<String, Integer>();
        map.put("One", 1);
        map.put("Two", 2);
        map.put("Three", 3);
    }

    @Test
    public void testClear() {
        map.clear();
        assertEquals(0, map.size());
    }

    @Test
    public void testContainsKey() {
        assertTrue(map.containsKey("Three"));
        assertFalse(map.containsKey("Four"));
    }

    @Test
    public void testContainsValue() {
        assertTrue(map.containsValue(3));
        assertFalse(map.containsValue(4));
    }

    @Test
    public void testGet() {
        assertEquals(3, map.get("Three"));
        assertNull(map.get("Four"));
    }

    @Test
    public void testIsEmpty() {
        assertFalse(map.isEmpty());
        map.clear();
        assertTrue(map.isEmpty());
    }

    @Test
    public void testKeySet() {
        Set<String> keySet = map.keySet();
        assertEquals(3, keySet.size());
        assertTrue(keySet.contains("Three"));
        assertFalse(keySet.contains("Four"));
    }

    @Test
    public void testPut() {
        map.put("One", 11);
        assertEquals(3, map.size());
        assertEquals(11, map.get("One"));

        map.put("Five", 5);
        assertEquals(4, map.size());
        assertEquals(5, map.get("Five"));
    }

    @Test
    public void testPutAll() {
        Map<String, Integer> m = new HashMap<String, Integer>();
        m.put("Six", 6);
        m.put("Seven", 7);
        m.put("Eight", 8);
        map.putAll(m);
        assertEquals(6, map.size());
    }

    @Test
    public void testRemove() {
        map.remove("One");
        assertEquals(2, map.size());
        assertNull(map.get("One"));
    }

    @Test
    public void testSize() {
        assertEquals(3, map.size());
    }

    @Test
    public void testValues() {
        Collection<Integer> keySet = map.values();
        assertEquals(3, keySet.size());
        assertTrue(keySet.contains(3));
        assertFalse(keySet.contains(4));
    }

}