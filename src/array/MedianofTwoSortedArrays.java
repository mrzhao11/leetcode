package array;

public class MedianofTwoSortedArrays {

    // 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。
    // 请你找出并返回这两个正序数组的 中位数 。
    // 算法的时间复杂度应该为 O(log (m+n)) 。

    // 本题思想：
    // 当总长度为奇数时，中位数为第 (m+n)/2 + 1 小的数
    // 当总长度为偶数时，中位数为第 (m+n)/2 和第 (m+n)/2 + 1 小的数的平均值
    // 因此问题转化为寻找两个有序数组的第 k 小的数
    // 假设两个有序数组为 A 和 B，我们要找第 k 小的数
    // 比较 A[k/2-1] 和 B[k/2-1] 的大小
    // 如果 A[k/2-1] < B[k/2-1]，则 A[0] 到 A[k/2-1] 这些数不可能是第 k 小的数
    // 因为即使 B[0] 到 B[k/2-1] 都小于 A[k/2-1]，加上 A[0] 到 A[k/2-1] 也只有 k-1 个数
    // 因此可以将 A 数组的前 k/2 个数全部排除，调整 k 的值，继续在剩下的数组中寻找第 k - k/2 小的数
    // 反之亦然
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        int total = m + n;
        if (total % 2 == 1) { // 奇数
            return getKth(nums1, nums2, total / 2 + 1);
        } else { // 偶数
            int left = getKth(nums1, nums2, total / 2);
            int right = getKth(nums1, nums2, total / 2 + 1);
            return (left + right) / 2.0;
        }
    }

    // 在 nums1 和 nums2 中找第 k 小的数
    private int getKth(int[] A, int[] B, int k) {
        int indexA = 0, indexB = 0; // 当前在 A 和 B 中的起始位置

        while (true) {
            // 情况 1：A 已经用完，此时说明第 k 小的数在 B 中，直接返回
            // 注意这里的 k 是相对于当前 indexB 的位置的, 因此要加上 indexB
            if (indexA == A.length) {
                return B[indexB + k - 1];
            }
            // 情况 2：B 已经用完，此时说明第 k 小的数在 A 中，直接返回
            if (indexB == B.length) {
                return A[indexA + k - 1];
            }
            // 情况 3：k == 1，说明要找的就是 A 和 B 中的最小值
            if (k == 1) {
                return Math.min(A[indexA], B[indexB]);
            }

            // 正常情况：比较第 k/2 个
            int half = k / 2;

            // 计算新的索引位置，注意不要越界
            int newIndexA = Math.min(indexA + half, A.length) - 1;
            int newIndexB = Math.min(indexB + half, B.length) - 1;

            int pivotA = A[newIndexA]; // 第 k/2 个数
            int pivotB = B[newIndexB]; // 第 k/2 个数

            // 排除较小的一部分
            if (pivotA <= pivotB) {
                // 排除 A[indexA ... newIndexA]
                k -= (newIndexA - indexA + 1); // 更新 k
                indexA = newIndexA + 1; // 更新 A 的起始位置
            } else {
                // 排除 B[indexB ... newIndexB]
                k -= (newIndexB - indexB + 1);
                indexB = newIndexB + 1;
            }
        }
    }


    public static void main(String[] args) {
        MedianofTwoSortedArrays solution = new MedianofTwoSortedArrays();
        int[] nums1 = {1, 3};
        int[] nums2 = {2};
        double median = solution.findMedianSortedArrays(nums1, nums2);
        System.out.println("Median is: " + median); // Output: Median is: 2.0
    }

}
