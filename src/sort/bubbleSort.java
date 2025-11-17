package sort;

public class bubbleSort {
    public int[] bubbleSort1(int[] arr) {
        if (arr.length < 2) return arr;
        // n - 1轮次执行，当前 n - 1 个元素排好后，最后一个元素无需执行，故i < arr.length - 1
        for (int i = 0; i < arr.length - 1; i++) {
            // 本轮执行是否有交换的标志，若无则false，若有则true
            boolean swapped = false;
            // 每轮循环，通过依次向右比较两个数，将本轮循环中最大的数放到最右
            for (int j = 1; j < arr.length - i; j++) {
                // 若左大于右则交换，并将swapped置为true
                if (arr[j - 1] > arr[j]) {
                    swap.swap(arr, j - 1, j);
                    swapped = true;
                }
            }
            // 若无交换，表示当前数组已完全排序，退出大循环
            if (!swapped) break;
        }
        return arr;
    }

    public int[] bubbleSort2(int[] arr){
        if(arr.length < 2) return arr;
        // 上一轮最后交换的下标，即发生 swap(num, i, j) 时的 i
        int lastSwappedIdx = arr.length - 1;
        // 若上一轮最后交换的下标 > 0 ，继续
        while(lastSwappedIdx > 0){
            // 当前轮次发生交换的下标
            int curSwappedIdx = 0;
            // 每一轮 i 只需要考察到 lastSwappedIdx 前一位
            for(int i = 0; i < lastSwappedIdx; i++){
                if(arr[i] > arr[i + 1]){
                    swap.swap(arr, i, i + 1);
                    curSwappedIdx = i; // 更新 curSwappedIdx
                }
            }
            // 若当前轮次未发生交换，则 lastSwappedIdx = 0 ，则在下一次 while 判断时退出，完成排序
            lastSwappedIdx = curSwappedIdx;
        }
        return arr;
    }
}
