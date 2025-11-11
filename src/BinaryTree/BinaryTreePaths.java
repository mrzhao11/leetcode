package BinaryTree;
import java.util.*;

public class BinaryTreePaths {
    public static List<String> binaryTreePaths(TreeNode root){
        List<String> res = new ArrayList<>();
        if(root ==null) return res;
        List<Integer> paths = new ArrayList<>();
        traversal(root,paths,res);
        return res;
    }
    // 递归法 + 回溯
    private static void traversal(TreeNode root, List<Integer> paths, List<String> res){
        paths.add(root.val);
        // 终止条件
        if(root.left == null && root.right == null) {
            StringBuilder sb = new StringBuilder();
            for(int i = 0;i<paths.size()-1; i++){
                sb.append(paths.get(i)).append("->");
            }
            // 处理最后一个节点
            sb.append(paths.get(paths.size()-1));
            res.add(sb.toString());
            return;
        }

        if(root.left != null){
            traversal(root.left, paths,res);
            paths.remove(paths.size()-1); // 回溯
        }
        if(root.right != null){
            traversal(root.right, paths,res);
            paths.remove(paths.size()-1); // 回溯
        }
    }

    // 迭代法
    public static List<String> binaryTreePathsIter(TreeNode root) {
        List<String> res = new ArrayList<>();
        if (root == null) return res;

        // 节点栈
        Deque<TreeNode> nodeStack = new ArrayDeque<>();
        // 路径栈
        Deque<String> pathStack = new ArrayDeque<>();

        nodeStack.push(root);
        pathStack.push(String.valueOf(root.val));  // 123 - “123”

        while (!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            String path = pathStack.pop();

            // 叶子：收集结果
            if (node.left == null && node.right == null) {
                res.add(path);
                continue;
            }

            // 注意先 push 右再 push 左，这样弹出时会先处理左分支（像前序）
            if (node.right != null) {
                nodeStack.push(node.right);
                pathStack.push(path + "->" + node.right.val);
            }
            if (node.left != null) {
                nodeStack.push(node.left);
                pathStack.push(path + "->" + node.left.val);
            }
        }
        return res;
    }

    // 路径总和
    // 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。
    // 判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 。
    // 如果存在，返回 true ；否则，返回 false 。
    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if(root == null) return false;
        targetSum -= root.val;
        if(root.left == null && root.right == null) {
            return targetSum == 0;
        }
        if(root.left!=null){
            boolean l = hasPathSum(root.left, targetSum);
            if(l) return true;
        }
        if(root.right!=null){
            boolean r = hasPathSum(root.right, targetSum);
            if(r) return true;
        }
        return false;
    }

    // 路径总和||
    // 给你二叉树的根节点 root 和一个整数目标和 targetSum
    // 找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
    public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<Integer> paths = new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        dfs(root, targetSum, paths, res);
        return res;
    }

    private static void dfs(TreeNode node, int target, List<Integer> path, List<List<Integer>> res) {
        path.add(node.val);
        if (node.left == null && node.right == null) {
            if(target == node.val)
                res.add(new ArrayList<>(path));
        }
        if (node.left != null) {
            dfs(node.left, target - node.val, path, res);
            path.remove(path.size() - 1);
        }
        if (node.right != null) {
            dfs(node.right, target - node.val, path, res);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        // 1. 手动构建一棵树
        //       1
        //      / \
        //     2   3
        //    / \   \
        //   4   5   6
        TreeNode root = new TreeNode(1);
        root.left  = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left  = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(6);

        System.out.println(binaryTreePaths(root));
        System.out.println(binaryTreePathsIter(root));
    }
}
