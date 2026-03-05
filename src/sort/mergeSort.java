package sort;

public class mergeSort {
    // 归并排序
    // 归并排序的核心思想是分治法，将数组分成两半，分别对两半进行排序，然后将排序好的两半合并成一个有序的数组。
    // 时间复杂度：O(n log n)，空间复杂度：O(n)（需要一个辅助数组来合并）
    public int[] sortArray(int[] nums) {
        if (nums == null || nums.length <= 1) return nums;
        int[] temp = new int[nums.length];   // 只申请一次辅助数组，主要用于合并过程
        mergeSort(nums, 0, nums.length - 1, temp);
        return nums;
    }

    // nums：待排序数组，left：左边界，right：右边界，temp：辅助数组
    private void mergeSort(int[] nums, int left, int right, int[] temp) {
        if (left >= right) return;

        int mid = left + (right - left) / 2; // 防止溢出

        mergeSort(nums, left, mid, temp);
        mergeSort(nums, mid + 1, right, temp);

        merge(nums, left, mid, right, temp);
    }

    // 将 nums[left..mid] 和 nums[mid+1..right] 两个有序子数组合并成一个有序数组
    private void merge(int[] nums, int left, int mid, int right, int[] temp) {
        // 归并过程：使用三个指针 i, j, k 分别指向左半部分、右半部分和 temp 数组的当前位置
        int i = left, j = mid + 1, k = left;

        // 比较两个子数组的当前元素，将较小的元素放入 temp 数组，并移动对应的指针
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }
        // 将左半部分剩余的元素（如果有）放入 temp 数组
        while (i <= mid) {
            temp[k++] = nums[i++];
        }
        // 将右半部分剩余的元素（如果有）放入 temp 数组
        while (j <= right) {
            temp[k++] = nums[j++];
        }
        // 将 temp 数组中的元素复制回 nums 数组的对应位置
        for (int p = left; p <= right; p++) {
            nums[p] = temp[p];
        }
    }
}
