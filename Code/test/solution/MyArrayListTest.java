package solution;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyArrayListTest {

    @Test
    void add() {
        MyArrayList<Integer> mal = new MyArrayList<>();
        mal.add(2);
        mal.add(3);
        mal.add(4);
        mal.add(0, 1);

        assertEquals(1, mal.get(0));
        assertEquals(2, mal.get(1));
        assertEquals(3, mal.get(2));
        assertEquals(4, mal.get(3));
    }

    @Test
    void clear() {
        MyArrayList<Integer> mal = new MyArrayList<>();
        mal.add(1);
        mal.add(2);
        mal.add(3);

        mal.clear();

        assertEquals(0, mal.size());
    }

    @Test
    void contains() {
        MyArrayList<Integer> mal = new MyArrayList<>();
        mal.add(1);
        mal.add(2);
        mal.add(3);

        assertEquals(true, mal.contains(1));
        assertEquals(false, mal.contains(4));
    }

    @Test
    void get() {
        MyArrayList<Integer> mal = new MyArrayList<>();
        mal.add(1);

        assertEquals(1, mal.get(0));
    }

    @Test
    void indexOf() {
        MyArrayList<Integer> mal = new MyArrayList<>();
        mal.add(1);
        mal.add(2);
        mal.add(3);
        mal.add(4);

        assertEquals(1, mal.indexOf(2));
        assertEquals(-1, mal.indexOf(5));
    }

    @Test
    void isEmpty() {
        MyArrayList<Integer> mal = new MyArrayList<>();

        assertEquals(true, mal.isEmpty());

        mal.add(1);

        assertEquals(false, mal.isEmpty());
    }

    @Test
    void remove() {
        MyArrayList<Integer> mal = new MyArrayList<>();

        mal.add(1);
        mal.add(3);
        mal.add(5);

        mal.remove(2);
        assertEquals(false, mal.contains(5));
        assertEquals(2, mal.size());
    }

    @Test
    void set() {
        MyArrayList<Integer> mal = new MyArrayList<>();

        mal.add(1);
        mal.add(5);
        mal.add(3);

        mal.set(1, 2);
        assertEquals(2, mal.get(1));
    }
}