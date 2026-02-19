package dp;

// 01背包问题的特征
// 1. 是否是在“选 or 不选”某些东西，只能选一次
// 2. 是否有“容量”限制，容量可以是重量，花费，时间等
// 3. 是否要求“最大值”或“最小值”，如最大价值

public class bag01 {
    // 0-1背包问题
    // 有N件物品和一个容量为W的背包，每件物品只能选择放入背包或不放入背包
    // 每件物品有重量weight[i]和价值value[i]
    // 求解在不超过背包容量的前提下，能够获得的最大价值
    // i表示物品，j表示背包容量
    // dp[i][j] 表示从下标为[0-i]的物品里任意取，放进容量为j的背包，价值总和最大是多少
    // 例：dp[1][4]的来源有两种情况：
    // 不放入第1件物品：dp[0][4]，此时背包容量为4，只有第0件物品可选
    // 放入第1件物品：dp[0][4-weight[1]] + value[1]
    public static int knapsack01(int[] weight, int[] value, int W) {
        int N = weight.length;
        // dp[i][j]: 前i件物品，容量为j的背包，能获得的最大价值
        int[][] dp = new int[N][W + 1];

        // java默认初始化为0。可省略
//        for(int i = 0; i < N; i++){
//            dp[i][0] = 0; // 背包容量为0，价值为0
//        }

        for(int i = 0;i <= W; i++){
            if(weight[0] > i){
                dp[0][i] = 0; // 第0件物品重量大于背包容量，不能放入
            }else{
                dp[0][i] = value[0]; // 第0件物品重量小于等于背包容量，可以放入
            }
        }

        // i从1开始，因为第0件物品已经初始化过了
        // j从0开始，因为背包容量0..W都是合法状态
        for(int i = 1; i < N; i++){
            for(int j = 0; j <= W; j++){
                if(weight[i] > j){
                    dp[i][j] = dp[i - 1][j]; // 第i件物品重量大于背包容量，不能放入
                }else{
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
                    // 不放入第i件物品：dp[i-1][j]
                    // 放入第i件物品：dp[i-1][j-weight[i]] + value[i]
                }
            }
        }
        return dp[N-1][W];
    }

    // 滚动数组01背包
    public static int knapsack01_optimized(int[] weight, int[] value, int W) {
        int N = weight.length;
        // dp[j]: 容量为j的背包，能获得的最大价值
        int[] dp = new int[W + 1];
        // java默认初始化为0。可省略
//        for(int j = 0; j <= W; j++){
//            dp[j] = 0; // 背包容量为0，价值为0
//        }
        for(int i = 0; i < N; i++){
            // 逆序遍历背包容量，保证每件物品只使用一次
            // 一维 dp 复用了两层状态：旧层（dp[i-1]) 和 新层（dp[i])。
            // 倒序保证 j-w 用的永远是旧层。
            // 正序会提前更新 j-w，使其变成新层，从而重复使用当前物品。
            for(int j = W; j >= weight[i]; j--){
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
                // 不放入第i件物品：dp[j]
                // 放入第i件物品：dp[j-weight[i]] + value[i]
            }
        }
        return dp[W];
    }

    // 分割等和子集
    // 给定一个只包含正整数的非空数组，判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等
    // 本题的本质是能否把容量为 sum/2 的背包装满
    // 重量 = 价值 = nums[i]，重量和价值本质上是一样
    public static boolean canPartition(int[] nums) {
        int sum = 0;
        for(int num : nums){
            sum += num;
        }
        // 如果总和是奇数，不能分割成两个相等的子集
        if(sum % 2 != 0) return false;
        int target = sum / 2;
        int N = nums.length;
        // dp[j] 表示：容量为 j 的背包，能装入的最大“重量和”（即选到的数字和）是多少
        int[] dp = new int[target + 1];
        for(int i = 0; i < N; i++) {
            for (int j = target; j >= nums[i]; j--) {
                // dp[j] 不选当前数字 nums[i]
                // dp[j - nums[i]] + nums[i] 选当前数字 nums[i]
                dp[j] = Math.max(dp[j], dp[j - nums[i]] + nums[i]);
            }
        }
        return dp[target] == target; // 如果容量为 target 的背包装满了，说明可以分割成两个相等的子集
    }

    // 最后一块石头的重量 II
    // 有一堆石头，每块石头的重量都是正整数。
    // 每一回合，从中选出两块石头，然后将它们一起粉碎。
    // 假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
    // 如果 x == y，那么两块石头都会被完全粉碎；如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y - x。
    // 最后，最多只会剩下一块石头。返回此石头的最小可能重量。
    // 本题的本质是将石头分成两堆，使得两堆石头的重量差最小
    // 和分割等和子集类似
    public static int lastStoneWeightII(int[] stones) {
        int sum = 0;
        for(int num : stones){
            sum += num;
        }
        int target = sum / 2;
        int N = stones.length;
        // dp[j] 表示：容量为 j 的背包，能装入的最大“重量和”（即选到的石头重量和）是多少
        int[] dp = new int[target + 1];
        for(int i = 0; i < N; i++) {
            for (int j = target; j >= stones[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j - stones[i]] + stones[i]);
            }
        }
        return (sum - dp[target]) - dp[target]; // 总重量减去两倍的较小堆重量，即为最小可能剩余重量
    }

    // 01背包问题 + 组合计数
    // 给你一个整数数组 nums 和一个整数 target 。
    // 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 。
    // 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
    public static int findTargetSumWays(int[] nums, int target) {
        // 所有加号的数计为x
        // 所有减号的数计为y
        // x+y=sum  x-y=target  x=(target+sum)/2
        // 问题转化为：从nums中选出一些数，使得这些数的和为x，有多少种选法
        int n = nums.length;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }

        // 如果target的绝对值大于sum，说明不可能组成target
        if (Math.abs(target) > sum) return 0;
        // 如果(target + sum)是奇数，说明无法整除2，无法组成target
        if ((target + sum) % 2 == 1) return 0;
        int bagsize = 0;
        bagsize = (target + sum) / 2;

        // dp[j] 表示：容量为 j 的背包，有多少种方法装满它，j这里代表和
        int[] dp = new int[bagsize + 1];
        dp[0] = 1; // 初始化：容量为0的背包，有1种方法（什么都不选）

        // dp[j] += dp[j - nums[i]]
        // 如果选择了当前数字 nums[i]，那么就要看容量为 j - nums[i] 的背包，有多少种方法装满它
        // dp[j]本身代表容量为j的背包已有的装满方法数，此时没有使用nums[i]
        // 而dp[j - nums[i]]代表使用nums[i]后，剩余容量的装满方法数
        // 两者相加，得到容量为j的背包的总装满方法数 += 操作
        for (int i = 0; i < n; i++) {
            for (int j = bagsize; j >= nums[i]; j--) {
                dp[j] += dp[j - nums[i]];
            }
        }
        return dp[bagsize];
    }

    // 01背包问题变种：二维背包问题
    // 给你一个二进制字符串数组 strs 和两个整数 m 和 n 。
    // 请你找出并返回 strs 的最大子集的大小，该子集中 最多 有 m 个 0 和 n 个 1 。
    // 如果 x 的所有元素也是 y 的元素，那么 x 是 y 的 子集
    public static int findMaxForm(String[] strs, int m, int n) {
        // dp[i][j] 表示：最多有 i 个 0 和 j 个 1 的子集的最大大小
        int[][] dp = new int[m+1][n+1];
        // 遍历每个字符串
        for (String str : strs) {
            // 统计字符串中0和1的数量
            int zeros = 0, ones = 0;
            for (char c : str.toCharArray()) {
                if (c == '0')
                    zeros++;
                else
                    ones++;
            }
            for (int i = m; i >= zeros; i--) {
                for (int j = n; j >= ones; j--) {
                    // dp[i][j] 不选当前字符串
                    // dp[i - zeros][j - ones] + 1 选当前字符串
                    dp[i][j] = Math.max(dp[i][j], dp[i - zeros][j - ones] + 1);
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        int[] weight = {2, 3, 4, 5};
        int[] value = {3, 4, 5, 6};
        int W = 5;
        System.out.println(knapsack01(weight, value, W)); // 输出最大价值
        System.out.println(knapsack01_optimized(weight, value, W)); // 输出最大价值

        int[] nums = {1, 5, 11, 5};
        System.out.println(canPartition(nums)); // 输出true
        int[] stones = {2,7,4,1,8,1};
        System.out.println(lastStoneWeightII(stones)); // 输出1

        int[] nums2 = {1, 1, 1, 1, 1};
        int target = 3;
        System.out.println(findTargetSumWays(nums2, target)); // 输出5

        String[] strs = {"10", "0001", "111001", "1", "0"};
        int m = 5, n = 3;
        System.out.println(findMaxForm(strs, m, n)); // 输出4
    }
}
