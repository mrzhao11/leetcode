package sort;

import java.util.Arrays;

public class radixSort {
    // 使用计数数组的基数排序
    public static int[] radixSort(int[] arr) {
        if (arr.length < 2) return arr;
        int n = arr.length, max = Math.abs(arr[0]);
        for (int i = 1; i < n; i++) { // 找到 arr 中绝对值最大者
            max = Math.max(max, Math.abs(arr[i]));
        }
        int width = 0, base = 10;
        while (max != 0) { // 求最大数的位数
            width++;
            max /= base;
        }
        int[] sortedArr = new int[n];
        for (int i = 0; i < width; i++) { // 每一轮都对当前位(基数)执行一次计数排序
            int[] countArr = new int[19]; // 为应对有负数的情况，countArr范围为[-9, 9]
            for (int num : arr) { // 根据每一个数字当前位的数字，累计相应位置的计数
                int bucketIdx = (num % base) / (base / 10) + 9; // 求当前位上的 (+9处理负数)
                countArr[bucketIdx]++;
            }
            for (int j = 1; j < countArr.length; j++) { // 变形
                countArr[j] += countArr[j - 1];
            }
            for (int j = n - 1; j >= 0; j--) { // 逆序输出保持稳定性
                int countIdx = (arr[j] % base) / (base / 10) + 9; // arr[j] 元素当前位对应 countArr 中的下标
                int sortedIdx = countArr[countIdx] - 1; // 在排序后数组中的下标
                sortedArr[sortedIdx] = arr[j]; // 在排序后数组中填入值
                countArr[countIdx]--; // countArr[countIdx] 已排序一位，下一个该位置的数的排位要靠前一位
            }
            arr = Arrays.copyOf(sortedArr, n); // 完成当前位的计数排序后将排序结果拷贝回原数组
            base *= 10; // base进一位，准备下一轮对高一位的计数排序
        }
        return arr;
    }

    // 不使用计数数组的基数排序
    public static int[] radixSortWithoutCount(int[] arr) {
        if (arr.length < 2) return arr;
        int n = arr.length, max = Math.abs(arr[0]); // 找到arr中绝对值最大者
        for (int i = 1; i < arr.length; i++) {
            max = Math.max(max, Math.abs(arr[i]));
        }
        int width = 0, base = 10;
        while (max != 0) { // 求最大数的位数
            width++;
            max /= base;
        }
        int[][] buckets = new int[19][n + 1]; // 19个桶处理负数，n+1 的用意是让每个桶的第一个位置保存该桶的元素个数
        for (int i = 0; i < width; i++) { // 在每一位上将数组中所有具有该位的数字装入对应桶中
            for (int num : arr) {
                int idx = (num % base) / (base / 10) + 9;
                int cnt = buckets[idx][0];
                buckets[idx][cnt + 1] = num;
                buckets[idx][0]++;
            }
            int arrIdx = 0;
            for (int j = 0; j < buckets.length; j++) { // 将当前所有桶的数按桶序，桶内从低到高输出为本轮排序结果
                for (int k = 1; k <= buckets[j][0]; k++) {
                    arr[arrIdx++] = buckets[j][k];
                }
            }
            for (int[] bucket : buckets) bucket[0] = 0; // 每一轮过后将桶计数归零
            base *= 10; // 调整base
        }
        return arr;
    }

}
