package sort;

public class insertSort {
    // 简单插入排序：内层用 for
    public static int[] insertionSort(int[] arr) {
        if (arr.length < 2) return arr;
        for (int i = 1; i < arr.length; i++) {
            int target = arr[i], j = i - 1;
            for (; j >= 0; j--) {
                if(target < arr[j]) arr[j + 1] = arr[j];
                else break;
            }
            arr[j + 1] = target;
        }
        return arr;
    }

    // 折半插入排序
    // 内层用二分查找法找到插入位置
    public static int[] insertSortBinary(int[] arr) {
        if (arr.length < 2) return arr;
        // n - 1 轮次执行
        for (int i = 1; i < arr.length; i++) {
            // 通过二分查找得到插入位置
            int target = arr[i];
            int pos = binarySearch(arr, 0, i - 1, target);
            for (int j = i; j > pos; j--) { // 移动
                arr[j] = arr[j - 1];
            }
            arr[pos] = target; // 插入
        }
        return arr;
    }

    // 在 arr[l..r] 范围内查找 target 应插入的位置
    private static int binarySearch(int[] arr, int l, int r, int target){
        while(l <= r){
            int c = l + (r - l) / 2;
            if(arr[c] <= target) l = c + 1;
            else r = c - 1;
        }
        return l;
    }
}
