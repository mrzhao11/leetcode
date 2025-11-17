package sort;

public class countSort {
    // 不稳定排序
    public static int[] countingSortUnstable(int[] arr) {
        if (arr.length < 2) return arr;
        int n = arr.length, min = arr[0], max = arr[0];
        for (int i = 1; i < n; i++) { // 确定 min 和 max
            min = Math.min(min, arr[i]);
            max = Math.max(max, arr[i]);
        }
        int[] countArr = new int[max - min + 1]; // arr 最多有 max-min+1 种数字
        // 区间 [min, max] 映射到 countArr 下标 [0, max-min]
        for (int i = 0; i < n; i++) { // 计数
            countArr[arr[i] - min]++; // arr[i] 的值出现一次，则 countArr[arr[i]-min] 加 1
        }
        int index = 0;
        for (int i = 0; i < countArr.length; i++) { // 值的种类
            for (int j = 0; j < countArr[i]; j++) { // 值出现几次
                arr[index] = i + min; // 排序
                index++;
            }
        }
        return arr;
    }

    // 稳定排序
    public static int[] countingSort(int[] arr) {
        if (arr.length < 2) return arr;
        int n = arr.length, min = arr[0], max = arr[0];
        for (int i = 1; i < n; i++) { // 确定 min 和 max
            min = Math.min(min, arr[i]);
            max = Math.max(max, arr[i]);
        }
        int[] countArr = new int[max - min + 1]; // arr 最多有 max-min+1 种数字
        for (int i = 0; i < n; i++) { // 计数
            countArr[arr[i] - min]++; // arr[i] 的值出现一次，则 countArr[arr[i]-min] 加 1
        }
        // countArr[i] 表示值 ≤ (i+min) 的元素个数总和
        // 假设 值 0 1 2，原始freq为 2 3 1
        // 则 countArr 应为 2 5 6
        for (int i = 1; i < countArr.length; i++) { // 前缀和
            countArr[i] += countArr[i - 1];
        }

        int[] sortedArr = new int[n]; // 根据 sortedArr, arr, countArr 三者关系完成 sortedArr 的输出
        for (int i = n - 1; i >= 0; i--) { // 逆序输出保持稳定性
            int countIdx = arr[i] - min; // arr[i] 元素对应 countArr 中的下标
            int sortedIdx = countArr[countIdx] - 1; // 在排序后数组中的下标
            sortedArr[sortedIdx] = arr[i]; // 在排序后数组中填入值
            countArr[countIdx]--; // countArr[countIdx] 已排序一位，下一个该位置的数的排位要靠前一位
        }
        return sortedArr;
    }
}
