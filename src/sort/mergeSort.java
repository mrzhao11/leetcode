package sort;

public class mergeSort {
    // 归并排序
    // 时间复杂度 O(nlogn)，空间复杂度 O(n)，稳定排序
    // 采用分治思想，将数组不断二分，直到子数组长度为1，然后合并有序子数组
    public static int[] mergeSort1(int[] arr) {
        if (arr.length < 2) return arr;
        int[] tmpArr = new int[arr.length]; // 辅助数组，用于合并时存放有序元素
        mergeSort(arr, tmpArr, 0, arr.length - 1);
        return arr;
    }
    // mergeSort 递归方法
    private static void mergeSort(int[] arr, int[] tmpArr, int l, int r) {
        if(l < r) {
            int c = l + (r - l) / 2;
            mergeSort(arr, tmpArr, l, c); // 排左半部分
            mergeSort(arr, tmpArr, c + 1, r); // 排右半部分
            merge(arr, tmpArr, l, c, r); // 合并两个有序部分
        }
    }
    // 非原地合并方法，假设左半和右半已经有序，此时合并两个有序段
    private static void merge(int[] arr, int[] tmpArr, int l, int c, int r) {
        int lh = l, rh = c + 1, h = l; // lh: left head, rh: right head, h: tmpArr head
        // 比较左右两半的元素，依次放入 tmpArr 中
        while (lh <= c && rh <= r) {
            if (arr[lh] <=  arr[rh]) tmpArr[h++] = arr[lh++]; // 左半边元素较小，放入 tmpArr
            else tmpArr[h++] = arr[rh++]; // 右半边元素较小，放入 tmpArr
        }
        while (lh <= c) tmpArr[h++] = arr[lh++]; // 左半边还有剩余，加入 tmpArr 末尾
        while (rh <= r) tmpArr[h++] = arr[rh++]; // 右半边还有剩余，加入 tmpArr 末尾
        for(; l <= r; l++) arr[l] = tmpArr[l]; // 将 tmpArr 拷回 arr 中
    }
}
