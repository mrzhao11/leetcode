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

    // 函数作用：在以 root 为根的子树中，寻找 p 和 q 的最近公共祖先
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        // 第2步：基本情况 Base Case
        // --------------------------------------------------
        // 1）root == null：空树 → 这片子树里没有 p 或 q
        // 2）root == p 或 root == q：找到了其中一个目标节点，
        //    按合同该返回这个节点，交给父节点判断
        if (root == null || root == p || root == q)
            return root;


        // 第3步：递归步骤 Recursive Step
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

    // 最近公共祖先不使用递归，而是迭代的方式实现
    // 思路：用栈模拟DFS，记录每个节点的父节点；找到 p 和 q 后，分别记录它们到 root 的路径；最后从 root 方向找最后一个公共节点
    public TreeNode lowestCommonAncestorIterative(TreeNode root, TreeNode p, TreeNode q) {
        // 1️⃣ 用栈模拟DFS，记录每个节点的父节点
        Map<TreeNode, TreeNode> parent = new HashMap<>(); // key: 当前节点，value: 父节点
        Deque<TreeNode> stack = new ArrayDeque<>(); // 用来DFS遍历树

        parent.put(root, null); // 根节点没有父节点
        stack.push(root); // 从根节点开始DFS

        // 继续DFS，直到找到 p 和 q 的父节点
        while (!parent.containsKey(p) || !parent.containsKey(q)) {
            TreeNode node = stack.pop();

            if (node.left != null) {
                parent.put(node.left, node);
                stack.push(node.left);
            }

            if (node.right != null) {
                parent.put(node.right, node);
                stack.push(node.right);
            }
        }

        // 2️⃣ 从 p 开始向上爬，记录 p 的祖先节点，把p的祖先节点都放到一个集合里，方便后续查询
        Set<TreeNode> ancestors = new HashSet<>();
        while (p != null) {
            ancestors.add(p);
            p = parent.get(p); // 向上爬，直到 root（parent.get(root) == null）
        }

        // 3️⃣ 从 q 开始向上爬，找到第一个在 ancestors 里出现的节点，即为最近公共祖先
        while (!ancestors.contains(q)) {
            q = parent.get(q);
        }
        return q;
    }

    // 二叉搜索树的最近公共祖先
    // 从上到下遍历，第一次遇到节点在pq区间内即是最近公共祖先
    // 原理：BST的性质，左子树<根<右子树，所以如果p和q都小于根节点，则LCA在左子树；如果都大于根节点，则LCA在右子树；否则LCA就是当前根节点
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if (root.val > p.val && root.val > q.val)
            return lowestCommonAncestor1(root.left, p, q);
        if (root.val < p.val && root.val < q.val)
            return lowestCommonAncestor1(root.right, p, q);
        return root;
    }

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
