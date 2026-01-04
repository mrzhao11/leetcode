package BinaryTree;

import java.util.HashMap;
import java.util.Map;

public class PathSumIII {

    // 给定一个二叉树的根节点 root 和一个整数目标和 targetSum ，求该二叉树中节点值之和等于目标和的路径数。
    // 路径 不需要从根节点开始，也不需要在叶子节点结束，
    // 但路径方向必须是向下的（只能从父节点到子节点）。

    int res = 0;
    Map<Long, Integer> map = new HashMap<>(); // 前缀和 -> 出现次数

    public int pathSum(TreeNode root, int targetSum) {
        // 前缀和为 0 出现 1 次（空路径）
        map.put(0L, 1);
        dfs(root, 0L, targetSum);
        return res;
    }

    // 递归函数作用：遍历以 node 为当前节点的子树，顺便计算路径数
    // curSum：从根节点到当前节点的前缀和
    // targetSum：目标和
    private void dfs(TreeNode node, long curSum, int targetSum) {
        if (node == null) return;

        curSum += node.val;

        // 统计以当前节点为终点的路径数
        if(map.containsKey(curSum-targetSum)){
            res += map.get(curSum-targetSum);
        }

        // 当前前缀和加入 map
        map.put(curSum, map.getOrDefault(curSum, 0) + 1);

        dfs(node.left, curSum, targetSum);
        dfs(node.right, curSum, targetSum);

        // 回溯：移除当前节点的前缀和
        map.put(curSum, map.get(curSum) - 1);
    }

    public static void main(String[] args) {
        PathSumIII solution = new PathSumIII();

        // 构建测试用例
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(-3);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(2);
        root.right.right = new TreeNode(11);
        root.left.left.left = new TreeNode(3);
        root.left.left.right = new TreeNode(-2);
        root.left.right.right = new TreeNode(1);

        int targetSum = 8;

        // 计算路径数
        int result = solution.pathSum(root, targetSum);
        System.out.println("路径数: " + result); // 输出结果
    }
}
