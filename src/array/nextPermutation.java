package array;

public class nextPermutation {
    // 31 下一个排列
    // 实现获取下一个排列的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
    // 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
    // 必须原地修改，只允许使用额外常数空间。
    // 例如，整数数组 [1,2,3] 的下一个排列是 [1,3,2]。
    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        // 如果i位置的数大于等于i+1位置的数，说明i位置的数不满足下一个更大的排列的条件，需要继续往左找
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        } // 从右往左找第一个上升的位置

        // 如果找到了上升的位置，说明存在下一个更大的排列
        // 从右往左找第一个比 nums[i] 大的位置 j，然后交换 nums[i] 和 nums[j]
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[i] >= nums[j]) {
                j--;
            }
            swap(nums, i, j);
        }
        // 最后将 i 位置右边的元素反转，得到下一个更大的排列
        reverse(nums, i + 1);
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public void reverse(int[] nums, int start) {
        int left = start, right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    public static void main(String[] args) {
        nextPermutation solution = new nextPermutation();
        int[] nums = {1, 2, 3};
        solution.nextPermutation(nums);
        for (int num : nums) {
            System.out.print(num + " ");
        }
    }
}
