package sort;

public class selectionSort {
    public static int[] selectionSort(int[] arr) {
        if (arr.length < 2) return arr;
        for (int i = 0; i < arr.length - 1; i++) { // 当前 N-1 个元素排好后，最后一个元素无需执行，故 i < arr.length - 1
            int minIdx = i;
            for (int j = i + 1; j < arr.length; j++) { // 找到本轮执行中最小的元素，将最小值下标赋值给 minIdx
                if (arr[j] < arr[minIdx]) minIdx = j;
            }
            swap.swap(arr, i, minIdx);
        }
        return arr;
    }
}
