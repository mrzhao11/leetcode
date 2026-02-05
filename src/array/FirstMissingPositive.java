package array;

public class FirstMissingPositive {
    // 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
    // 你必须实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
    // 本次核心逻辑：一个数组长度为n
    // 那么存在两种情况：1.数值1 - n中有缺失 2.数值1 - n都不缺 这两种情况会出现一个结果：缺失的正整数一定在[1, n+1]之间
    // 因此，我们可以将数组中的值x放到下标x-1的位置上，这样遍历一遍数组后，我们再次遍历数组，找到第一个下标i位置上的值不是i+1的情况，即为缺失的正整数
    public static int firstMissingPositive(int[] nums) {

        // 值x放到下标x-1的位置，映射关系
        for (int i = 0; i < nums.length; i++) {
            // 只去判断正整数1到n的范围内的数，除此之外的数都不处理
            // 如果nums[i]在范围内（非正数和大于n的数直接跳过）且不在正确的位置上，就交换到正确的位置
            while (nums[i] >= 1 && nums[i] <= nums.length
                    && nums[i] != nums[nums[i] - 1]) {
                // 交换nums[i]和nums[nums[i]-1]
                int temp = nums[i];
                nums[i] = nums[temp - 1];
                nums[temp - 1] = temp;
            }
        }

        // 如果下标i位置上的值不是i+1，那么i+1就是缺失的正整数
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        // 否则，说明1 - n都出现了，缺失的正整数是n+1
        return nums.length + 1;
    }

    public static void main(String[] args) {
        int[] nums = {3,4,-1,1};
        System.out.println(firstMissingPositive(nums)); // 输出2
    }
}
