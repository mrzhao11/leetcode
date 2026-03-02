package greedy;

public class MaxSubarray {
    // 最大连续子数组和
    public static int maxSubArray(int[] nums) {
        // dpi 以nums[i]结尾的子数组的最大和
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int res = dp[0];
        if (nums.length == 1) return res;
        for (int i = 1; i < nums.length; i++) {
            // 以nums[i]结尾的子数组的最大和，要么是以nums[i-1]结尾的子数组加上nums[i]，要么就是单独的nums[i]
            dp[i] = Math.max(nums[i] + dp[i - 1], nums[i]);
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    // 如果要求返回子数组本身
    public static int[] maxSubArrayWithIndices(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int res = dp[0];
        int start = 0; // 最终起始位置
        int end = 0; // 最终结束位置
        int tempStart = 0; // 当前dp[i]的对应子数组的起始位置
        if (nums.length == 1) return new int[]{start, end};
        for (int i = 1; i < nums.length; i++) {
            if (dp[i - 1] > 0) {
                dp[i] = nums[i] + dp[i - 1];
                // 此时延续之前的起点，tempStart 不变
            } else {
                dp[i] = nums[i];
                tempStart = i; // 前面是负贡献，从当前位置重新开始计算起点
            }

            // 更新全局最大值，并同步最终的起止坐标
            if (dp[i] > res) {
                res = dp[i];
                start = tempStart;
                end = i;
            }
        }
        return new int[]{start, end}; // 返回最大子数组的起止索引
    }

    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubArray(nums)); // 输出 6
    }
}
