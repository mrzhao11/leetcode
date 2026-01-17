package dp;

public class ClimbStair {
    // 爬楼梯问题
    // 每次可以爬1层或2层楼梯，求爬到第n层楼梯有多少种方法
    public static int climbStairs(int n){
        if(n <= 1) return n;
        // dp[i] 爬到第i层楼梯有dp[i]种方法
        int[] dp = new int[n + 1];
        dp[1] = 1; // 第一层只有一种方法
        dp[2] = 2; // 第二层有两种方法
        for(int i = 3; i <= n; i++){
            dp[i] = dp[i - 1] + dp[i - 2]; // 爬到第i层的方法等于爬到第i-1层和i-2层的方法之和
        }
        return dp[n];
    }

    // 使用最小花费爬楼梯
    // cost[i]表示爬到第i层楼梯的花费，可以选择从第i-1层或第i-2层爬上去
    // 返回爬到楼梯顶部的最小花费，可以从第0层或第1层开始   从第0层或第1层开始是不需要花费的
    public static int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        // dp[i]表示爬到第i层楼梯的最小花费
        int[] dp = new int[n + 1];

        dp[0] = 0; // 从第0层开始花费为0
        dp[1] = 0; // 从第1层开始花费为0
        for (int i = 2; i <= n; i++) {
            // 选择从第i-1层或第i-2层爬上去，取最小花费
            dp[i] = Math.min(dp[i-1]+cost[i-1],dp[i-2]+cost[i-2]); // 爬到第i层的最小花费
        }
        return dp[n];
    }

    public static int integerBreak(int n) {
        // dp[i]表示将整数i拆分后得到的最大乘积
        int[] dp = new int[n+1];
        dp[2] = 1;
        // i拆分成j和i-j两部分，j从1到i/2遍历，因为超过一半的拆分是重复的
        for (int i = 3; i <= n ; i++) {
            for (int j = 1; j <= i / 2; j++) {
                // 内层取最大值，比较不拆分和继续拆分的情况
                // 不拆分： (i - j) * j，继续拆分：dp[i - j] * j
                // 外层表示所有拆分方式的最大值
                dp[i] = Math.max(dp[i], Math.max((i - j) * j, dp[i - j] * j));
            }
        }
        return dp[n];
    }

    // 不同的二叉搜索树
    // 给定一个整数n，求以1到n为节点组成的不同的二叉搜索树的个数
    // 卡特兰数公式问题， dp[i] = sum(dp[j-1] * dp[i-j])，1<=j<=i
    public static int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        // 考虑每一个节点 j（1 ≤ j ≤ i）都作为根节点的情况。
        // 根节点	左子树节点数量	右子树节点数量	左右子树能形成的 BST 数量
        // j	       j-1	            i-j	          dp[j-1] * dp[i-j]
        // 左子树节点数 = j - 1（值比根小）
        // 右子树节点数 = i - j（值比根大）
        // 左右子树是独立的，所以组合数要相乘
        for (int i = 1; i <= n; i++) { // i个节点
            for (int j = 1; j <= i; j++) { // j作为根节点
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        int n = 5;
        System.out.println(climbStairs(n));

        int[] cost = {10, 15, 20};
        System.out.println(minCostClimbingStairs(cost));

        int num = 10;
        System.out.println(integerBreak(num));

        int treeNum = 3;
        System.out.println(numTrees(treeNum));
    }
}
