package solution;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyLinkedListTest {

    @Test
    public void indexOf() {
        MyLinkedList<Integer> mll = new MyLinkedList<>();
        mll.add(1);
        mll.add(2);
        mll.add(3);
        assertEquals(0, mll.indexOf(1));
        assertEquals(1, mll.indexOf(2));
        assertEquals(2, mll.indexOf(3));
    }

    @Test
    public void add() {
        MyLinkedList<Integer> mll = new MyLinkedList<>();
        mll.add(2);
        mll.add(3);
        mll.add(4);
        mll.add(0, 1);
        assertEquals(1, mll.get(0));
        assertEquals(1, mll.indexOf(2));
        assertEquals(2, mll.indexOf(3));
        assertEquals(4, mll.get(3));
    }

    @Test
    public void remove() {
        MyLinkedList<String> mll = new MyLinkedList<>();
        mll.add("1");
        mll.add("2");
        mll.add("4");
        mll.add("3");

        mll.remove(2);
        mll.remove(0);

        assertEquals("2", mll.get(0));
        assertEquals("3", mll.get(1));
    }
}