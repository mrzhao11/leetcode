package sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class bucketSort {
    public static int[] bucketSort(int[] arr) {
        if(arr.length < 2) return arr;
        int n = arr.length, k = n / 3, min = arr[0], max = arr[0]; // k=n/3 个桶
        for (int i = 1; i < n; i++) { // 确定 min 和 max
            min = Math.min(min, arr[i]);
            max = Math.max(max, arr[i]);
        }
        List<ArrayList<Integer>> buckets = new ArrayList<>(k);
        for (int i = 0; i < k; i++) { // k 个桶
            buckets.add(new ArrayList<>()); // 每个桶是一个ArrayList<Integer>
        }
        double interval = (max - min) * 1.0 / (k - 1); // 桶间隔
        for (int num : arr) { // 遍历arr，根据元素值将所有元素装入对应值区间的桶中
            int bucketIdx = (int) ((num - min) / interval); // arr[i] (num) 元素应该装入的桶的下标
            buckets.get(bucketIdx).add(num); // 装入对应桶中
        }
        for (ArrayList<Integer> bucket : buckets) {
            Collections.sort(bucket); // 桶内排序(调用库函数，从小到大)
        }
        int index = 0;
        for (ArrayList<Integer> bucket : buckets) {
            for (int sortedNum : bucket) {
                arr[index] = sortedNum; // 复用输入数组arr
                index++;
            }
        }
        return arr;
    }
}
