package dp;

import java.util.Arrays;
import java.util.List;

public class bagfull {
    // 有N件物品和一个最多能背重量为W的背包。第i件物品的重量是weight[i]，得到的价值是value[i] 。
    // 每件物品都有无限个（也就是可以放入背包多次），求解将哪些物品装入背包里物品价值总和最大。
    // 完全背包和01背包问题唯一不同的地方就是，每种物品有无限件。
    public static int knapsackFull(int[] weight, int[] value, int W) {
        int N = weight.length;
        // dp[i][j]: 前i件物品可无限使用，容量为j的背包，能获得的最大价值
        int[][] dp = new int[N][W + 1];
        // java默认初始化为0。可省略
//        for(int i = 0; i < N; i++){
//            dp[i][0] = 0; // 背包容量为0，价值为0
//        }
        // 初始化第0行
        for(int j = 0; j <= W; j++){
            if(weight[0] > j){
                dp[0][j] = 0; // 第0件物品重量大于背包容量，不能放入
            }else{
                // 第0件物品重量小于等于背包容量，可以放入多次
                dp[0][j] = (j / weight[0]) * value[0];
            }
        }
        for(int i = 1; i < N; i++){
            for(int j = 0; j <= W; j++){
                if(weight[i] > j){
                    dp[i][j] = dp[i - 1][j]; // 第i件物品重量大于背包容量，不能放入
                }else{
                    // 不放入第i件物品：dp[i-1][j]
                    // 放入第i件物品：dp[i][j-weight[i]] + value[i]
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - weight[i]] + value[i]);
                }
            }
        }
        return dp[N-1][W];
    }
    // 完全背包和01背包的区别就在于：
    // 01背包放入第i件物品时，状态转移方程是 dp[i-1][j-weight[i]] + value[i]，表示只能从前一行状态转移过来
    // 完全背包放入第i件物品时，状态转移方程是 dp[i][j-weight[i]] + value[i]，表示可以从当前行状态转移过来，因为物品可以重复使用

    // 一维滚动数组
    public static int knapsackFull_optimized(int[] weight, int[] value, int W) {
        int N = weight.length;
        // dp[j]: 容量为j的背包，能获得的最大价值
        int[] dp = new int[W + 1];
        // java默认初始化为0。可省略
//        for(int j = 0; j <= W; j++){
//            dp[j] = 0; // 背包容量为0，价值为0
//        }
        for(int i = 0; i < N; i++){
            // 正序遍历背包容量，保证每件物品可以多次使用（与01背包相反）
            for(int j = weight[i]; j <= W; j++){
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
                // 不放入第i件物品：dp[j]
                // 放入第i件物品：dp[j-weight[i]] + value[i]
            }
        }
        return dp[W];
    }

    // 一维数组遍历顺序
    // 如果求组合数就是外层for循环遍历物品，内层for遍历背包——顺序无关
    // 物品在外层，背包在内层，每个物品只会在内层循环中被使用一次，保证了组合数的唯一性

    // 如果求排列数就是外层for遍历背包，内层for循环遍历物品——顺序有关
    // 背包在外层，物品在内层，每次内层循环都可以使用所有物品，保证了排列数的多样性

    // 完全背包 + 组合数问题
    // 给定不同面额的硬币 coins 和一个总金额 amount。
    // 编写函数来计算可以凑成总金额的硬币组合数。假设每一种面额的硬币有无限个。
    public static int change(int amount, int[] coins) {
        int n = coins.length;
        // dp[j]表示金额j的组合数
        int[] dp = new int[amount+1];
        dp[0] = 1; // 金额为0时，组合数为1（不选任何硬币）
        for(int i = 0;i < n;i++){ // 遍历硬币，即物品
            for(int j = coins[i];j<=amount;j++){ // 遍历金额，即背包，j如果小于coins[i]，无法凑出该金额
                dp[j] += dp[j - coins[i]];
                // 凑金额 j，那我就看“先凑 j - coin”有多少种方法，再加上这个 coin，就构成新的方案。
            }
        }
        return dp[amount];
    }

    // 完全背包 + 排列数问题
    // 给定一个由不同整数组成的数组 nums ，和一个目标整数 target 。
    // 请你从 nums 中找出并返回总和为 target 的元素组合的个数。
    public static int combinationSum4(int[] nums, int target) {
        int n = nums.length;
        // dp[i]表示目标和为i的排列数
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 0; i <= target; i++) { // 遍历背包
            for (int j = 0; j < n; j++) { // 遍历物品
                if (i >= nums[j]) { // 背包容量足够放下物品
                    dp[i] += dp[i - nums[j]];
                }
            }
        }
        return dp[target];
    }

    // 完全背包 + 最少硬币问题
    // 给定不同面额的硬币 coins 和一个总金额 amount。
    // 编写函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1
    public static int coinChange(int[] coins, int amount) {
        int n = coins.length;
        // dp[j]表示金额j的最少硬币数
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE); // 初始化为最大值，表示无法凑出该金额
        dp[0] = 0;
        // 物品是硬币，背包是目标金额
        for (int i = 0; i < n; i++) { // 遍历物品
            for (int j = coins[i]; j <= amount; j++) { // 遍历背包
                if (dp[j - coins[i]] != Integer.MAX_VALUE) { // 只有“先凑 j - coin”有解时，才进行状态转移
                    // 要凑金额 j，最后一步一定是：用了 1 枚面额为 coins[i] 的硬币，在此之前凑出了金额 j - coins[i]
                    // 那我就看“先凑 j - coin”最少需要多少个硬币，再加上这个 coin，就构成新的方案。
                    // 取原有方案和新方案的最小值
                    dp[j] = Math.min(dp[j], dp[j - coins[i]] + 1);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }

    // 完全背包 + 最少完全平方数
    // 给你一个整数 n ，返回 和为 n 的完全平方数的最少数量 。
    public static int numSquares(int n) {
        // dp[i]表示和为i的完全平方数的最少数量
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        // 物品是完全平方数 1,4,9,16,...
        // 背包是目标和 n
        for (int i = 1; i * i <= n; i++) { // 遍历物品，完全平方数
            int square = i * i; // 完全平方数
            for (int j = square; j <= n; j++) { // 遍历背包
                if (dp[j - square] != Integer.MAX_VALUE) // 只有“先凑 j - square”有解时，才进行状态转移
                    // 要凑平方数 j，最后一步一定是：用了 1 个完全平方数 square，在此之前凑出了金额 j - square
                    // 那我就看“先凑 j - square”最少需要多少个完全平方数，再加上这个 square，就构成新的方案。
                    // 取原有方案和新方案的最小值
                    dp[j] = Math.min(dp[j], dp[j - square] + 1);
            }
        }
        return dp[n];
    }

    // 完全背包 + 单词拆分   有顺序 排列数问题
    // 给定一个字符串 s 和一个字符串列表 wordDict 作为字典，
    // 判定 s 是否可以由空格拆分为一个或多个在字典中出现的单词。
    public static boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        // dp[i]表示s的前i个字符能否被wordDict拆分
        boolean[] dp = new boolean[n+1];
        dp[0] = true; // 空字符串可以被拆分
        for(int i = 1; i <= n;i++){ // 先遍历背包
            for(String word : wordDict){ // 再遍历物品
                int len = word.length();
                // i-len >=0 首先字符串前i个字符长度必须大于当前单词长度，保证不越界
                // dp[i-len]表示前i-len个字符能否被拆分
                // word.equals(s.substring(i-len,i))表示第i-len到i的子串是否在字典中
                if (i >= len && dp[i - len] && word.equals(s.substring(i - len, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        int[] weight = {1, 3, 4};
        int[] value = {15, 20, 30};
        int W = 4;
        System.out.println(knapsackFull(weight, value, W)); // 输出最大价值
        System.out.println(knapsackFull_optimized(weight, value, W)); // 输出最大价值

        int amount = 5;
        int[] coins = {1, 2, 5};
        System.out.println(change(amount, coins)); // 输出组合数
        System.out.println(combinationSum4(coins, amount)); // 输出排列数
        System.out.println(coinChange(coins, amount)); // 输出最少硬币数

        int n = 12;
        System.out.println(numSquares(n)); // 输出最少完全平方数

        String s = "leetcode";
        List<String> wordDict = Arrays.asList("leet", "code");
        System.out.println(wordBreak(s, wordDict)); // 输出true
    }
}
