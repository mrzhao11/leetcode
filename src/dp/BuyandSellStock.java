package dp;

public class BuyandSellStock {

    // 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
    // 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。
    // 返回 你可以获得的 最大利润 。 如果你无法获得任何利润，返回 0 。
    // dp + 状态机
    public static int maxProfit(int[] prices) {
        int n = prices.length;
        // dp[i][0] 表示第i天持有股票时的最大利润
        // dp[i][1] 表示第i天不持有股票时的最大利润
        int[][] dp = new int[n][2];

        dp[0][0] = -prices[0]; // 第一天买入股票，利润为负
        dp[0][1] = 0; // 第一天不买入股票，利润为0

        for(int i = 1;i < n; i++){
            // 第i天持有股票，有两种情况：
            // 1. 第i-1天也持有股票，利润为dp[i-1][0]
            // 2. 第i天买入股票，利润为 -prices[i]，如果今天买入股票，说明之前没有买入过，所以利润就是 -prices[i]
            dp[i][0] = Math.max(dp[i-1][0],-prices[i]);
            // 第i天不持有股票，有两种情况：
            // 1. 第i-1天也不持有股票，利润为dp[i-1][1]
            // 2. 第i天卖出股票，利润为 dp[i-1][0] + prices[i]
            dp[i][1] = Math.max(dp[i-1][1],dp[i-1][0]+prices[i]);
        }
        // 最后一天不持有股票时的利润最大
        return dp[n-1][1];
    }

    // 允许多次买卖股票
    public static int maxProfit2(int[] prices) {
        int n = prices.length;
        // dp[i][0] 表示第i天持有股票时的最大利润
        // dp[i][1] 表示第i天不持有股票时的最大利润
        int[][] dp = new int[n][2];

        dp[0][0] = -prices[0]; // 第一天买入股票，利润为负
        dp[0][1] = 0; // 第一天不买入股票，利润为0

        for(int i = 1;i < n; i++){
            // 第i天持有股票，有两种情况：
            // 1. 第i-1天也持有股票，利润为dp[i-1][0]
            // 2. 第i天买入股票，利润为 dp[i-1][1] - prices[i]
            // 注意这里买入股票时，利润要基于前一天不持有股票
            dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1]-prices[i]);
            // 第i天不持有股票，有两种情况：
            // 1. 第i-1天也不持有股票，利润为dp[i-1][1]
            // 2. 第i天卖出股票，利润为 dp[i-1][0] + prices[i]
            dp[i][1] = Math.max(dp[i-1][1],dp[i-1][0]+prices[i]);
        }
        // 最后一天不持有股票时的利润最大
        return dp[n-1][1];
    }

    // 允许最多两次买卖股票
    public static int maxProfit3(int[] prices) {
        int n = prices.length;
        // dp[i][0] 第i天未进行任何操作的最大利润
        // dp[i][1] 第i天进行第一次买入操作的最大利润
        // dp[i][2] 第i天进行第一次卖出操作的最大利润
        // dp[i][3] 第i天进行第二次买入操作的最大利润
        // dp[i][4] 第i天进行第二次卖出操作的最大利润
        int[][] dp = new int[n][5];

        // dp[0][0] = 0;
        dp[0][1] = -prices[0];
        // dp[0][2] = 0;
        dp[0][3] = -prices[0];
        // dp[0][4] = 0;

        for(int i = 1;i < n; i++){
            // dp[i][0] = dp[i-1][0];
            dp[i][1] = Math.max(dp[i-1][1],dp[i-1][0]-prices[i]);
            dp[i][2] = Math.max(dp[i-1][2],dp[i-1][1]+prices[i]);
            dp[i][3] = Math.max(dp[i-1][3],dp[i-1][2]-prices[i]);
            dp[i][4] = Math.max(dp[i-1][4],dp[i-1][3]+prices[i]);
        }
        return dp[n-1][4];
    }

    // 允许最多k次买卖股票
    public static int maxProfit4(int k, int[] prices) {
        int n = prices.length;
        // dp[i][j] 表示第i天进行第j次操作（买入或卖出）的最大利润，j为奇数表示买入，偶数表示卖出
        int[][] dp = new int[n][2 * k + 1];

        for (int i = 0; i < 2 * k + 1; i++) {
            if (i % 2 == 1)
                dp[0][i] = -prices[0]; // 买入股票，利润为负
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < 2 * k + 1; j++) {
                if (j % 2 == 1) {
                    // 买入股票
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - 1] - prices[i]);
                } else {
                    // 卖出股票
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - 1] + prices[i]);
                }
            }
        }
        return dp[n - 1][2 * k];
    }

    // 交易后有冷冻期（即卖出股票后，下一天不能买入股票）,允许多次买卖股票
    public static int maxProfitFrozen(int[] prices) {
        int n = prices.length;

        // 0持有状态 1空仓&非冷冻期（可随时购买） 2今天刚卖出 3冷冻期
        int[][] dp = new int[n][4];
        dp[0][0] = -prices[0];

        for (int i = 1; i < n; i++) {
            // 0持有状态 可以从前一天的持有状态转移过来，或者今天买入（前一天非冷冻期或冷冻期都可以买入）
            dp[i][0] = Math.max(Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]), dp[i - 1][3] - prices[i]);
            // 1空仓&非冷冻期 可以从前一天的非冷冻期或冷冻期转移过来
            dp[i][1] = Math.max(dp[i-1][1], dp[i-1][3]);
            // 2今天刚卖出 只能从前一天的持有状态转移过来
            dp[i][2] = dp[i-1][0] + prices[i];
            // 3冷冻期 只能从前一天的刚卖出状态转移过来
            dp[i][3] = dp[i-1][2];
        }
        // 最后一天不持有股票时的利润最大
        return Math.max(dp[n - 1][3], Math.max(dp[n - 1][1], dp[n - 1][2]));
    }

    // 每次交易需要支付手续费fee，允许多次买卖股票
    public static int maxProfitFee(int[] prices, int fee) {
        int n = prices.length;
        // dp[i][0] 表示第i天持有股票时的最大利润
        // dp[i][1] 表示第i天不持有股票时的最大利润
        int[][] dp = new int[n][2];

        dp[0][0] = -prices[0];
        dp[0][1] = 0;

        for (int i = 1; i < n; i++) {
            // 第i天持有股票
            // 两种情况：继续持有，或者今天买入（基于前一天不持有股票）
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] - prices[i]);
            // 第i天不持有股票
            // 两种情况：继续不持有，或者今天卖出（需要扣除手续费fee）
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] + prices[i] - fee);
        }
        return dp[n - 1][1];
    }

    public static void main(String[] args) {
        int[] prices = {7,1,5,3,6,4};
        System.out.println(maxProfit(prices)); // 输出 5

        int[] prices2 = {7,6,4,3,1};
        System.out.println(maxProfit2(prices2)); // 输出 0

        int[] prices3 = {3,3,5,0,0,3,1,4};
        System.out.println(maxProfit3(prices3)); // 输出 6

        int k = 2;
        int[] prices4 = {3,2,6,5,0,3};
        System.out.println(maxProfit4(k, prices4)); // 输出 7

        int[] prices5 = {1,2,3,0,2};
        System.out.println(maxProfitFrozen(prices5)); // 输出 3

        int[] prices6 = {1,3,2,8,4,9};
        int fee = 2;
        System.out.println(maxProfitFee(prices6, fee)); // 输出 8
    }
}
