package solution;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static solution.SelectionSort.*;

class SelectionSortTest {

    @Test
    public void 선택정렬() {
        int[] array = {9,7,5,3,1,8,6,4,2};

        for(int i = 0; i < array.length - 1; i++) {
            swap(array, i, minIndex(array,i));
        }

        System.out.println(Arrays.toString(array));
    }
}