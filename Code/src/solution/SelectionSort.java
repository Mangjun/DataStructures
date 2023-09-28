package solution;

/**
 * 선택 정렬
 * 1. 배열에서 가장 작은 값을 찾은 후 index를 기억
 * 2. 처음이랑 위치를 바꿈
 * 3. 배열의 길이 - 1때까지 반복
 */
public class SelectionSort {

    /**
     * 배열에서 i와 j의 위치에 있는 값을 바꿈
     */
    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 가장 작은 값이 있는 index 반환
     */
    public static int minIndex(int[] array, int n) {
        int min_index = n;

        for(int i = n; i < array.length; i++) {
            if(array[min_index] > array[i]) {
                min_index = i;
            }
        }

        return min_index;
    }

}
