package solution;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * ArrayList 와 LinkedList 비교
 * add(끝) | ArrayList: 상수 시간, LinkedList: 선형
 * add(시작) | ArrayList: 선형, LinkedList: 상수 시간
 * add(일반) | ArrayList: 선형, LinkedList: 선형
 * get / set | ArrayList: 상수 시간, LinkedList: 선형
 * indexOf / lastIndexOf | ArrayList: 선형, LinkedList: 선형
 * isEmpty / size | ArrayList: 상수 시간, LinkedList: 상수 시간
 * remove(끝) | ArrayList: 상수 시간, LinkedList: 선형
 * remove(시작) | ArrayList: 선형, LinkedList: 상수 시간
 * remove(일반) | ArrayList: 선형, LinkedList: 선형
 *
 * 결론:
 * ArrayList: 끝에 추가 및 삭제와 get, set 에 이점
 * LinkedList: 시작에 추가 및 삭제에 이점
 */

public class MyLinkedList<E> implements List<E> {

    // 내부 클래스
    private class Node {
        public E data; // data
        public Node next; // 다음 Node 의 주소

        public Node(E data) {
            this.data = data;
            this.next = null;
        }

        @SuppressWarnings("unused")
        public Node(E data, Node next) {
            this.data = data;
            this.next = next;
        }

        public String toString() {
            return "Node(" + data.toString() + ")";
        }
        
    }

    private int size; // Node 의 개수
    private Node head; // 첫 번째 Node 에 대한 참조

    public MyLinkedList() {
        head = null;
        size = 0;
    }

    @Override
    public boolean add(E element) {
        if (head == null) {
            head = new Node(element);
        } else {
            Node node = head;
            // 마지막 Node 로 이동
            for ( ; node.next != null; node = node.next) {}
            node.next = new Node(element);
        }
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        // 최적화 코드
        if(index == 0) {
            head = new Node(element, head);
        } else {
            Node node = getNode(index - 1); // getNode에서 예외 처리
            node.next = new Node(element, node.next);
        }
        size++;
        /* 내가 쓴 코드
        if(index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if(index == 0) {
            Node node = head;
            Node newNode = new Node(element, node);
            head = newNode;
        } else {
            Node node = head;
            for(int i=0; i<index-1; node = node.next, i++) {}
            Node newNode = new Node(element, node.next);
            node.next = newNode;
        }
        size++;
         */
    } // 선형

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        boolean flag = true;
        for (E element: collection) {
            flag &= add(element);
        }
        return flag;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    } // 선형

    @Override
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object obj: collection) {
            if (!contains(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public E get(int index) {
        Node node = getNode(index);
        return node.data;
    } // 선형

    private Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node node = head;
        for (int i=0; i<index; i++) {
            node = node.next;
        }
        return node;
    } // 선형

    @Override
    public int indexOf(Object target) {
        Node node = head;
        for (int i=0; i<size; node = node.next, i++) {
            if(equals(target, node.data)) {
                return i;
            }
        }
        return -1;
    } // 선형 -> 없거나 마지막 요소라면 리스트 전체를 순회할 수도 있음

    private boolean equals(Object target, Object element) {
        if (target == null) {
            return element == null;
        } else {
            return target.equals(element);
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        E[] array = (E[]) toArray();
        return Arrays.asList(array).iterator();
    }

    @Override
    public int lastIndexOf(Object target) {
        Node node = head;
        int index = -1;
        for (int i=0; i<size; i++) {
            if (equals(target, node.data)) {
                index = i;
            }
            node = node.next;
        }
        return index;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public boolean remove(Object obj) {
        int index = indexOf(obj);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public E remove(int index) {
        // 최적화 코드
        E element = get(index);
        if(index == 0) {
            head = head.next;
        } else {
            Node node = getNode(index-1);
            node.next = node.next.next; // next 로 연결이 가능 -> 주소를 참조
        }
        size--;
        return element;
        /* 내가 쓴 코드
        Node node = getNode(index);
        if(index == 0) {
            head = node.next;
        } else {
            Node preNode = getNode(index - 1);
            preNode.next = node.next;
        }
        size--;
        return node.data;
         */
    } // 선형

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean flag = true;
        for (Object obj: collection) {
            flag &= remove(obj);
        }
        return flag;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E set(int index, E element) {
        Node node = getNode(index);
        E old = node.data;
        node.data = element;
        return old;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        // TODO: classify this and improve it.
        int i = 0;
        MyLinkedList<E> list = new MyLinkedList<E>();
        for (Node node=head; node != null; node = node.next) {
            if (i >= fromIndex && i <= toIndex) {
                list.add(node.data);
            }
            i++;
        }
        return list;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (Node node=head; node != null; node = node.next) {
            // System.out.println(node);
            array[i] = node.data;
            i++;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }
}
