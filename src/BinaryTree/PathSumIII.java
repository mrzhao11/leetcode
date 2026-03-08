package BinaryTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    // 路经总和
    // 给你一个二叉树的根节点 root 和一个整数目标和 targetSum ，判断该二叉树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 。
    public boolean hasPathSum(TreeNode root, int targetSum) {
        List<Integer> paths = new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        if(root == null) return false;
        traversal(root,paths,res);
        for(int x : res) {
            if(x == targetSum) return true;
        }
        return false;
    }

    private void traversal(TreeNode root, List<Integer> paths, List<Integer> res) {
        if(root == null) return;
        paths.add(root.val);
        // 如果当前节点是叶子节点，计算路径和并加入结果列表
        if(root.left == null && root.right == null) {
            int sum = 0;
            for(int x : paths) {
                sum += x;
            }
            res.add(sum);
        }
        // 继续遍历左右子树，回溯时移除当前节点的值
        traversal(root.left,paths,res);
        traversal(root.right,paths,res);
        paths.remove(paths.size() - 1);
    }

    // 路经总和 递归优化
    public boolean hasPathSum2(TreeNode root, int targetSum) {
        if(root == null) return false;
        // 如果当前节点是叶子节点，判断路径和是否等于目标和
        if(root.left == null && root.right == null) {
            return root.val == targetSum;
        }
        // 继续遍历左右子树，递归时更新目标和为 targetSum - 当前节点值
        boolean left = hasPathSum2(root.left, targetSum - root.val);
        boolean right = hasPathSum2(root.right, targetSum - root.val);
        return left || right; // 只要左右子树有一条路径满足条件

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
