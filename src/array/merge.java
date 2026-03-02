package array;

public class merge {
    // 合并两个有序数组
    // 给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 nums1 成为一个有序数组。
    // 说明:
    // 初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。
    // 你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。

    // 思想：从后往前遍历，比较 nums1 和 nums2 的元素，较大的放在 nums1 的末尾
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        // 从后往前遍历，比较 nums1 和 nums2 的元素，较大的放在 nums1 的末尾
        int i = m - 1; // nums1 的最后一个元素的索引
        int j = n - 1; // nums2 的最后一个元素的索引
        int k = m + n - 1; // 合并后数组的最后一个元素的索引

        // 比较 nums1 和 nums2 的元素，较大的放在 nums1 的末尾
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }

        // 如果 nums2 中还有剩余元素，继续复制到 nums1 中
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }
    }


    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int m = 3;
        int[] nums2 = {2, 5, 6};
        int n = 3;

        merge(nums1, m, nums2, n);

        // 输出合并后的数组
        for (int num : nums1) {
            System.out.print(num + " ");
        }
    }
}
