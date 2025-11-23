package greedy;

public class MaxSubarray {
    // 最大连续子数组和
    public static int maxSubArray(int[] nums) {
        if(nums.length == 1) return nums[0];
        int sum = Integer.MIN_VALUE; // 记录目前为止的最大和
        int count = 0; // 以当前位置结尾的最好的连续子数组的和
        for(int i = 0; i < nums.length; i++) {
            count += nums[i];
            sum = Math.max(sum, count);
            if (count < 0) {
                count = 0; // 如果当前和为负数，那后面的子数组加上它只会更小，重置为0
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println(maxSubArray(nums)); // 输出 6
    }
}
