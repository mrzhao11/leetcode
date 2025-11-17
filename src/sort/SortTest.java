package sort;

import java.util.*;

public class SortTest {
    public static void main(String[] args) {
        int[] data = {5, 3, 8, 1, 2};
        System.out.println("原始数组: " + Arrays.toString(data));

        bubbleSort bs = new bubbleSort();
        int[] sorted = bs.bubbleSort1(data.clone());
        System.out.println("bubbleSort1 排序后: " + Arrays.toString(sorted));
    }
}
