package BinaryTree;

import java.util.*;

public class LowestCommonAncestorBT {

    /**
     * 契约（第1步）：
     * 对于以 root 为根的这棵子树，
     * 返回：
     * - 若 p 和 q 都在这棵子树中：返回它们的最近公共祖先节点；
     * - 若只有 p 或 q 在这棵子树中：返回找到的那个节点；
     * - 若两个都不在：返回 null。
     * <p>
     * 这个函数的返回值始终都是“当前子树中，与 (p, q) 相关的最有价值的信息”。
     * 最顶层最终返回的才是整棵树的 LCA。
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        // 第2步：基本情况 Base Case
        // --------------------------------------------------
        // 1）root == null：空树 → 这片子树里没有 p 或 q
        // 2）root == p 或 root == q：找到了其中一个目标节点，
        //    按合同该返回这个节点，交给父节点判断
        if (root == null || root == p || root == q)
            return root;


        // 第3步：递归步骤 Recursive Step
        // --------------------------------------------------
        // 将“在这棵子树中找 LCA”的任务，拆分为两个更小的问题：
        //   - 在左子树找 LCA（或部分结果）
        //   - 在右子树找 LCA（或部分结果）
        // 递归假设：lowestCommonAncestor() 在更小的子树中已经能正确返回契约规定的结果
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);


        // 第4步：合并子解 Combine Results
        // --------------------------------------------------
        // 根据 left 和 right（子问题的返回结果）构造“当前子树”的答案

        // 情况1：左右都没找到（都返回 null）
        // → 当前这棵子树里没有 p 和 q
        if (left == null && right == null) {
            return null;
        }

        // 情况2：右边找到（left=null, right=非空）
        // → 当前子树只从右子树得到信息，把这个信息传递给上层
        else if (left == null && right != null) {
            return right;
        }

        // 情况3：左边找到（left=非空, right=null）
        // → 同理，将左边的结果往上传
        else if (left != null && right == null) {
            return left;
        }

        // 情况4：左右均非空（left != null && right != null）
        // → 说明 p 和 q 分别在左右两边
        // → 当前节点 root 就是最近公共祖先
        else {
            return root;
        }
    }

//    // 二叉搜索树的最近公共祖先
      // 从上到下遍历，第一次遇到节点在pq区间内即是最近最近公共祖先
//    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
//        if (root.val > p.val && root.val > q.val)
//            return lowestCommonAncestor(root.left, p, q);
//        if (root.val < p.val && root.val < q.val)
//            return lowestCommonAncestor(root.right, p, q);
//        return root;
//    }

    public static void main(String[] args) {

        /**
         * 构造一棵示例二叉树：
         *
         *             3
         *           /   \
         *          5     1
         *         / \   / \
         *        6  2  0   8
         *          / \
         *         7   4
         *
         * 测试用例：
         *   LCA(5, 1) = 3
         *   LCA(6, 4) = 5
         */

        TreeNode root = new TreeNode(3);

        root.left = new TreeNode(5);
        root.right = new TreeNode(1);

        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);

        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);

        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);

        // 指定 p 和 q（引用树里的实际节点）
        TreeNode p = root.left;                // 5
        TreeNode q = root.right;               // 1

        TreeNode lca = lowestCommonAncestor(root, p, q);

        System.out.println("LCA of " + p.val + " and " + q.val + " = " + lca.val);

        // 再测一次
        TreeNode p2 = root.left.left;          // 6
        TreeNode q2 = root.left.right.right;   // 4

        TreeNode lca2 = lowestCommonAncestor(root, p2, q2);

        System.out.println("LCA of " + p2.val + " wwand " + q2.val + " = " + lca2.val);
    }


}
