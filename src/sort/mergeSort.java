package sort;

public class mergeSort {
    public static int[] mergeSort(int[] arr) {
        if (arr.length < 2) return arr;
        int[] tmpArr = new int[arr.length];
        mergeSort(arr, tmpArr, 0, arr.length - 1);
        return arr;
    }
    // mergeSort 递归方法
    private static void mergeSort(int[] arr, int[] tmpArr, int l, int r) {
        if(l < r) {
            int c = l + (r - l) / 2;
            mergeSort(arr, tmpArr, l, c); // 排左半部分
            mergeSort(arr, tmpArr, c + 1, r); // 排右半部分
            merge(arr, tmpArr, l, c, r);
        }
    }
    // 非原地合并方法，假设左半和右半已经有序，此时合并两个有序段
    private static void merge(int[] arr, int[] tmpArr, int l, int c, int r) {
        int lh = l, rh = c + 1, h = l; // lh: left head, rh: right head, h: tmpArr head
        while (lh <= c && rh <= r) {
            tmpArr[h++] = arr[lh] <= arr[rh] ? arr[lh++] : arr[rh++];
        }
        while (lh <= c) tmpArr[h++] = arr[lh++]; // 左半边还有剩余，加入 tmpArr 末尾
        while (rh <= r) tmpArr[h++] = arr[rh++]; // 右半边还有剩余，加入 tmpArr 末尾
        for(; l <= r; l++) arr[l] = tmpArr[l]; // 将 tmpArr 拷回 arr 中
    }
}
