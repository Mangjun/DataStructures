package solution;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 알고리즘
 * 상수 시간: 실행 시간이 입력 크기에 의존 하지 않음
 * 선형: 실행 시간이 입력의 크기에 비례 
 * 이차: 실행 시간이 n²에 비례
 * 대부분의 경우 반복문이 1개라면 선형, 반복문 2개가 중첩되었다면 이차
 */
public class MyArrayList<T> implements List<T> {

    private int size; // 데이터 개수
    private T[] array; // 데이터를 저장할 배열

    @SuppressWarnings("unchecked") // 지네릭스 타입을 사용한 경고를 무시함
    public MyArrayList() {
        array = (T[]) new Object[10];
        size = 0;
    }

    /**
     * n번 추가하면 요소 n개를 추가하고 n-2개를 복사
     * 총 연산 횟수: 2n - 2
     * 평균: 2 - 2/n
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean add(T element) {
        // 배열의 크기보다 데이터의 양이 많은 경우
        if(size >= array.length) {
            T[] bigArray = (T[]) new Object[array.length * 2];
            System.arraycopy(array, 0, bigArray, 0, array.length);
            array = bigArray;
        } // 선형
        array[size] = element;
        size++;
        return true;
    } // 상수 시간

    @Override
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        add(element);

        for (int i=size-1; i>index; i--) {
            array[i] = array[i-1];
        }

        array[index] = element;
    } // 선형

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        boolean flag = true;
        for (T element: collection) {
            flag &= add(element);
        }
        return flag;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object element: collection) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return array[index];
    } // 상수 시간

    @Override
    public int indexOf(Object target) {
        for(int i=0; i<size; i++) {
            if(equals(target, array[i])) {
                return i;
            }
        }
        return -1;
    } // 선형

    private boolean equals(Object target, Object element) {
        if (target == null) {
            return element == null;
        } else {
            return target.equals(element);
        }
    } // 상수 시간

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<T> iterator() {
        // make a copy of the array
        T[] copy = Arrays.copyOf(array, size);
        // make a list and return an iterator
        return Arrays.asList(copy).iterator();
    }

    @Override
    public int lastIndexOf(Object target) {
        // see notes on indexOf
        for (int i = size-1; i>=0; i--) {
            if (equals(target, array[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        // make a copy of the array
        T[] copy = Arrays.copyOf(array, size);
        // make a list and return an iterator
        return Arrays.asList(copy).listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        // make a copy of the array
        T[] copy = Arrays.copyOf(array, size);
        // make a list and return an iterator
        return Arrays.asList(copy).listIterator(index);
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
    public T remove(int index) {
       T element = get(index);
        for(int i=index; i<size-1; i++) {
            array[i] = array[i+1];
        }
        size--;
        return element;
    } // 선형(마지막 요소일 경우 상수 시간)

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean flag = true;
        for (Object obj: collection) {
            flag &= remove(obj);
        }
        return flag;
    } // collection의 크기가 상수: 선형, collection의 크기가 배열의 요소에 비례: 이차

    @Override
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(int index, T element) {
        T old = get(index); // get()에서 예외 처리
        array[index] = element;
        return old;
    } // 상수 시간

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException();
        }
        T[] copy = Arrays.copyOfRange(array, fromIndex, toIndex);
        return Arrays.asList(copy);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public <U> U[] toArray(U[] array) {
        throw new UnsupportedOperationException();
    }

}