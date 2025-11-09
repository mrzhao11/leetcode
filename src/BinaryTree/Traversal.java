package BinaryTree;
import java.util.*;

public class Traversal {

    private static void print(List<Integer> list){
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(list.get(i));
        }
        System.out.println();
    }

    // ---------- 前序：递归 ----------
    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorder(root, result);
        print(result);
        return result;
    }
    private static void preorder(TreeNode root, List<Integer> result){
        if (root == null) return;
        result.add(root.val);
        preorder(root.left, result);
        preorder(root.right, result);
    }

    // ---------- 前序：迭代 ----------
    public static List<Integer> preorderTraversalIter(TreeNode root){
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        Deque<TreeNode> st = new ArrayDeque<>();
        st.push(root);                               // 先把 root 入栈
        while(!st.isEmpty()){
            TreeNode node = st.pop();
            result.add(node.val);                    // 根
            if (node.right != null) st.push(node.right); // 先右后左 -> 出栈时先处理左
            if (node.left  != null) st.push(node.left);
        }
        print(result);
        return result;
    }

    // ---------- 中序：递归 ----------
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        print(result);
        return result;
    }
    private static void inorder(TreeNode root, List<Integer> list) {
        if (root == null) return;
        inorder(root.left, list);
        list.add(root.val);
        inorder(root.right, list);
    }

    // ---------- 中序：迭代 ----------
    public static List<Integer> inorderTraversalIter(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Deque<TreeNode> st = new ArrayDeque<>();
        TreeNode cur = root;
        while (cur != null || !st.isEmpty()){
            while (cur != null) {        // 一路向左压栈
                st.push(cur);
                cur = cur.left;
            }
            cur = st.pop();              // 到最左，开始访问
            result.add(cur.val);
            cur = cur.right;             // 转向右子树
        }
        print(result);
        return result;
    }

    // ---------- 后序：递归 ----------
    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorder(root, result);
        print(result);
        return result;
    }
    private static void postorder(TreeNode root, List<Integer> list) {
        if (root == null) return;
        postorder(root.left, list);
        postorder(root.right, list);
        list.add(root.val);
    }

    // ---------- 后序：迭代（反转法）----------
    // 逻辑：用类似前序遍历(根-右-左)，最后整体 reverse -> 左-右-根
    public static List<Integer> postorderTraversalIter(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        Deque<TreeNode> st = new ArrayDeque<>();
        st.push(root);
        while (!st.isEmpty()){
            TreeNode node = st.pop();
            result.add(node.val);            // 先收集 根-右-左 的顺序
            if (node.left  != null) st.push(node.left);   // 先压左、再压右 -> 出栈顺序为 右 再 左
            if (node.right != null) st.push(node.right);
        }
        Collections.reverse(result);         // 反转成 左-右-根（真·后序）
        print(result);
        return result;
    }

// ========= 层序遍历（返回 List<List<Integer>>） =========

    // 递归：DFS 按层收集 -> 直接返回 levels
    public static List<List<Integer>> levelOrderTraversal(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        levelDfs(root, 0, levels);
        printLevels(levels);                  // 调用即打印：每层一行
        return levels;
    }

    private static void levelDfs(TreeNode node, int depth, List<List<Integer>> levels){
        if (node == null) return;
        if (levels.size() == depth) levels.add(new ArrayList<>()); // 首次到该层，新建桶
        levels.get(depth).add(node.val);
        levelDfs(node.left,  depth + 1, levels);
        levelDfs(node.right, depth + 1, levels);
    }

    // 迭代：BFS（队列），按层收集
    public static List<List<Integer>> levelOrderTraversalIter(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        if (root == null) { printLevels(levels); return levels; }

        Deque<TreeNode> q = new ArrayDeque<>();
        q.offerLast(root);
        while (!q.isEmpty()){
            int size = q.size();              // 当前层节点数
            List<Integer> lv = new ArrayList<>(size);
            for (int i = 0; i < size; i++){
                TreeNode node = q.pollFirst();     // 出队当前层
                lv.add(node.val);
                if (node.left  != null) q.offerLast(node.left);
                if (node.right != null) q.offerLast(node.right);
            }
            levels.add(lv);
        }
        printLevels(levels);                  // 调用即打印：每层一行
        return levels;
    }

    // 打印工具：逐层打印（复用你已有的 print(List<Integer>)）
    private static void printLevels(List<List<Integer>> levels){
        for (List<Integer> lv : levels) {
            print(lv);
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

         preorderTraversal(root);
        // preorderTraversalIter(root);
        // inorderTraversal(root);
        // inorderTraversalIter(root);
        // postorderTraversal(root);
        // postorderTraversalIter(root);
        levelOrderTraversalIter(root);
    }

}
