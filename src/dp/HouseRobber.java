package dp;

import BinaryTree.TreeNode;

public class HouseRobber {
    // 打家劫舍
    // 输入一个非负整数数组，表示每个房屋存放的钱数
    // 相邻的房屋有报警系统，不能同时偷相邻的房屋
    // 求在不触发报警系统的情况下，能够偷到的最大金额
    public static int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];
        if (n == 2) return Math.max(nums[0], nums[1]);
        // dp[i]表示偷到第i个房屋时，能够偷到的最大金额
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for(int i = 2; i < n; i++){
            // 偷第i个房屋，则不能偷第i-1个房屋，只能加上dp[i-2]
            // 不偷第i个房屋，则金额为dp[i-1]
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }

        return dp[n - 1];
    }// 如果房屋数组首尾相邻，可以考虑计算从0到n-2和从1到n-1然后比较最大值

    // 打家劫舍 + 树形dp
    public static int robT(TreeNode root) {
        int[] res = robTree(root);
        return Math.max(res[0], res[1]);
    }

    // 返回长度为2的数组：res[0] 不偷，res[1] 偷
    private static int[] robTree(TreeNode root) {
        // 递归终止条件
        if (root == null) return new int[2];

        // 后序遍历
        int[] left = robTree(root.left);
        int[] right = robTree(root.right);

        int[] dp = new int[2];

        // 不偷当前节点的最大收益，左右孩子可偷可不偷，取最大收益
        dp[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);

        // 偷当前节点的最大收益，此时左孩子和右孩子不能偷
        dp[1] = root.val + left[0] + right[0];

        return dp;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 1, 4, 5, 2, 7, 1, 3, 2};
        System.out.println(rob(nums));

        TreeNode t2 = new TreeNode(3);
        t2.left = new TreeNode(2);
        t2.right = new TreeNode(3);
        t2.left.right = new TreeNode(3);
        t2.right.right = new TreeNode(1);
        System.out.println("robT(example1) = " + robT(t2));  // 7
    }
}
