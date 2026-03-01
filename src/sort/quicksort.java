package sort;

import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class quicksort {
//    // 首位轴
//    // 单轴快排：首位为轴
//    public static int[] quickSortSimple(int[] arr) {
//        if (arr.length < 2) return arr;
//        quickSortSimple(arr, 0, arr.length - 1);
//        return arr;
//    }
//    // 单轴快排递归方法：首位为轴
//    private static void quickSortSimple(int[] arr, int l, int r) {
//        if (l < r) { // 若left == right，表示此时 arr 只有一个元素，即为基准情形，完成递归
//            int p = partition(arr, l, r);
//            quickSortSimple(arr, l, p - 1);
//            quickSortSimple(arr, p + 1, r);
//        }
//    }
//
//    // 三数取中轴
//    // 单轴快排：三数取中
//    public static int[] quickSortMedian3(int[] arr) {
//        if (arr.length < 2) return arr;
//        quickSortMedian3(arr, 0, arr.length - 1);
//        return arr;
//    }
//
//    // 单轴快排递归方法：三数取中
//    private static void quickSortMedian3(int[] arr, int l, int r) {
//        if (l < r) {
//            median3(arr, l, r); // 执行median3将左，中，右三数中值放到left位置上
//            int p = partition(arr, l, r);
//            quickSortMedian3(arr, l, p - 1);
//            quickSortMedian3(arr, p + 1, r);
//        }
//    }
//
//
//    // 将left, center, right下标三个数中，大小居中者放到left下标处
//    private static void median3(int[]arr, int l, int r) {
//        int c = l + (r - l) / 2;
//        if (arr[l] > arr[c]) swap.swap(arr, l, c); // 左中，大者居中
//        if (arr[c] > arr[r]) swap.swap(arr, c, r); // 中右，大者居右，此时最大者居右
//        if (arr[c] > arr[l]) swap.swap(arr, l, c); // 左中，大者居左，此时中者居左
//    }
//
//    // 随机轴
//    // 单轴快排：随机轴
//    public static int[] quickSortRandom(int[] arr) {
//        if (arr.length < 2) return arr;
//        quickSortRandom(arr, 0, arr.length - 1);
//        return arr;
//    }
//
//    // 单轴快排递归方法：随机轴
//    private static void quickSortRandom(int[] arr, int l, int r) {
//        if (l < r) {
//            int randIdx = new Random().nextInt(r - l) + l + 1; // 在 [left + 1, right] 范围内的随机值
//            swap.swap(arr, l, randIdx); // arr[l] 与它之后的某个数交换
//            int p = partition(arr, l, r);
//            quickSortRandom(arr, l, p - 1);
//            quickSortRandom(arr, p + 1, r);
//        }
//    }
//
//    // partition 方法
//    private static int partition(int[] arr, int l, int r) {
//        int j = l + 1;
//        for (int i = j; i <= r; i++) {
//            if (arr[i] < arr[l]) {
//                swap.swap(arr, i, j); // 交换后的 arr[j] 为当前最后一个小于主轴元素的元素
//                j++;
//            }
//        }
//        swap.swap(arr, l, j - 1); // 主轴元素归位
//        return j - 1;
//    }

    // 三路快排
    // 三路快排：随机轴 + 三路划分
    public static int[] quickSort3(int[] arr) {
        if (arr.length < 2) return arr;
        quickSort(arr, 0, arr.length - 1);
        return arr;
    }

    // 三路快排递归方法：随机轴 + 三路划分
    private static void quickSort(int[] arr, int l, int r) {
        if (l < r) {
            int randIdx = ThreadLocalRandom.current().nextInt(l, r + 1); // 在 [left, right] 范围内的随机值
            swap.swap(arr, l, randIdx); // arr[l] 与它之后的某个数交换
            int pivot = arr[l]; // 以随机轴为基准元素

            // 三路划分，lt指向小于轴的区域的右边界，gt指向大于轴的区域的左边界，i用于扫描
            int lt = l;
            int gt = r;
            int i = l + 1;

            // 如果当前元素小于轴，则与lt位置交换，lt和i都右移
            // 如果当前元素大于轴，则与gt位置交换，gt左移，i不变
            // 如果当前元素等于轴，则i右移
            while (i <= gt) {
                if (arr[i] < pivot) {
                    swap.swap(arr, i, lt);
                    lt++;
                    i++;
                } else if (arr[i] > pivot) {
                    swap.swap(arr, i, gt);
                    gt--;
                } else {
                    i++;
                }
            }

            // 现在：
            // [l ... lt-1] < pivot
            // [lt ... gt]  == pivot
            // [gt+1 ... r] > pivot

            quickSort(arr, l, lt - 1);
            quickSort(arr, gt + 1, r);
        }
    }
}


class Solution {
    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        return quickSelect3(nums, 0, n - 1, n - k); // 第 k 大元素在排序后数组中的索引为 n - k
    }

    // 三路快排思想的快速选择算法
    // arr：输入数组
    // l：当前处理的子数组的左边界
    // r：当前处理的子数组的右边界
    // target：我们要找到的第 k 大元素在排序后数组中的索引
    private int quickSelect3(int[] arr, int l, int r, int target) {
        if (l == r) return arr[l]; // 递归终止条件

        // 随机选择一个轴，并交换到最左边
        int idx = ThreadLocalRandom.current().nextInt(l, r + 1); // 在 [l, r] 范围内的随机值
        swap(arr, l, idx);
        int pivot = arr[l];

        // 三路划分，lt指向小于轴的区域的右边界，gt指向大于轴的区域的左边界，i用于扫描
        int lt = l;
        int gt = r;
        int i = l + 1;

        // 如果当前元素小于轴，则与lt位置交换，lt和i都右移
        // 如果当前元素大于轴，则与gt位置交换，gt左移，i不变
        // 如果当前元素等于轴，则i右移
        while (i <= gt) {
            if (arr[i] < pivot) {
                swap(arr, i, lt);
                lt++;
                i++;
            } else if (arr[i] > pivot) {
                swap(arr, i, gt);
                gt--;
            } else {
                i++;
            }
        }

        // 现在：
        // [l ... lt-1] < pivot
        // [lt ... gt]  == pivot
        // [gt+1 ... r] > pivot

        if (target >= lt && target <= gt)
            return pivot;
        else if (target < lt)
            return quickSelect3(arr, l, lt - 1, target);
        else
            return quickSelect3(arr, gt + 1, r, target);
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 最小堆解法
    public int findKthLargestHeap(int[] nums, int k) {
        // 创建一个小顶堆，限制大小为 k
        PriorityQueue<Integer> pq = new PriorityQueue<>(k);

        for (int num : nums) {
            if (pq.size() < k) {
                // 1. 如果堆还没满，直接加进去
                pq.offer(num);
            } else if (num > pq.peek()) {
                // 2. 如果堆满了，且当前元素比堆顶大
                pq.poll();    // 弹出最小的（第 k+1 大及以后）
                pq.offer(num); // 加入当前的，保持堆里依然是目前最大的 k 个
            }
        }

        // 遍历结束，堆顶就是第 k 大
        return pq.peek();
    }
}
