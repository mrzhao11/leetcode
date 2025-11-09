package BinaryTree;
import java.util.*;

public class SymmetricTree {
    // 递归
    public static boolean isSymmetric(TreeNode root) {
        return compare(root.left,root.right);
    }
    private static boolean compare(TreeNode left, TreeNode right){
        // 确定递归终止条件
        if(left == null && right != null) return false;
        if(left != null && right == null) return false;
        if(left == null && right == null) return true;
        if(left.val != right.val) return false;
        // 处理二者值相等的情况，比较内侧和外侧节点
        boolean outside =compare(left.left, right.right);
        boolean inside =compare(left.right, right.left);
        boolean issame = outside && inside;
        return issame;
    }

    //迭代
    public static boolean isSymmetricIter(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        if(root == null) return true;
        q.offer(root.left);
        q.offer(root.right);
        while (!q.isEmpty()) {
            TreeNode leftNode = q.poll();
            TreeNode rightNode = q.poll();
            if (leftNode == null && rightNode == null) {
                continue;
            }
            if (leftNode == null && rightNode != null) {
                return false;
            }
            if (leftNode != null && rightNode == null) {
                return false;
            }
            if (leftNode.val != rightNode.val) {
                return false;
            }
            q.offer(leftNode.left);
            q.offer(rightNode.right);
            q.offer(leftNode.right);
            q.offer(rightNode.left);
        }
        return true;
    }

    // AVL 求节点的高度
    // 注意高度（下到上）——后序遍历 和深度（上到下）——前序遍历 不同
    public static boolean isBalanced(TreeNode root) {
        return height(root) != -1;
    }
    private static int height(TreeNode node) {
        // 终止逻辑
        if (node == null) return 0;

        int lh = height(node.left);
        // 左子树已经不平衡
        if (lh == -1) return -1;

        int rh = height(node.right);
        // 右子树已经不平衡
        if (rh == -1) return -1;

        if (Math.abs(lh - rh) > 1) return -1;

        return Math.max(lh, rh) + 1;
        // 高度定义：某节点高度 = max(左高度, 右高度) + 1
    }

    public static void main(String[] args) {
        // 1. 手动构建一棵树
        //       1
        //      / \
        //     2   2
        //    /     \
        //   3       3
        TreeNode root = new TreeNode(1);
        root.left  = new TreeNode(2);
        root.right = new TreeNode(2);
        root.left.left  = new TreeNode(3);
        root.right.right = new TreeNode(3);

        System.out.println(isSymmetric(root));
        System.out.println(isSymmetricIter(root));
        System.out.println(isBalanced(root));
    }
}
