package BinaryTree;

import java.util.*;

public class Traversal {

    // ---------- 前序：递归 ----------
    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorder(root, result);
        return result;
    }

    private static void preorder(TreeNode root, List<Integer> result) {
        if (root == null) return;
        result.add(root.val);
        preorder(root.left, result);
        preorder(root.right, result);
    }

    // ---------- 前序：迭代 ----------
    public static List<Integer> preorderTraversalIter(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        Deque<TreeNode> st = new ArrayDeque<>();
        st.push(root);                               // 先把 root 入栈
        while (!st.isEmpty()) {
            TreeNode node = st.pop();
            result.add(node.val);                    // 根
            if (node.right != null) st.push(node.right); // 先右后左 -> 出栈时先处理左
            if (node.left != null) st.push(node.left);
        }
        return result;
    }

    // ---------- 中序：递归 ----------
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
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
        // 如果栈非空或当前节点不为空，则继续
        // 当前节点非空代表还没到最左叶子节点，栈非空代表还有节点没访问
        while (cur != null || !st.isEmpty()) {
            while (cur != null) {        // 一路向左压栈
                st.push(cur);
                cur = cur.left;
            }
            cur = st.pop();              // 到最左，开始访问
            result.add(cur.val);
            cur = cur.right;             // 转向右子树，继续上述过程
        }
        return result;
    }

    // ---------- 后序：递归 ----------
    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorder(root, result);
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
        while (!st.isEmpty()) {
            TreeNode node = st.pop();
            result.add(node.val);            // 先收集 根-右-左 的顺序
            if (node.left != null) st.push(node.left);   // 先压左、再压右 -> 出栈顺序为 右 再 左
            if (node.right != null) st.push(node.right);
        }
        Collections.reverse(result);         // 反转成 左-右-根（真·后序）
        return result;
    }

// ========= 层序遍历（返回 List<List<Integer>>） =========

    // 递归：DFS 按层收集 -> 直接返回 levels
    public static List<List<Integer>> levelOrderTraversal(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        levelDfs(root, 0, levels);
        return levels;
    }

    // 函数作用，将当前节点 node 放入结果集res中第 depth 层对应的列表中
    private static void levelDfs(TreeNode node, int depth, List<List<Integer>> levels) {
        if (node == null) return;
        if (levels.size() == depth) levels.add(new ArrayList<>()); // 首次到该层，新建桶
        levels.get(depth).add(node.val);
        levelDfs(node.left, depth + 1, levels);
        levelDfs(node.right, depth + 1, levels);
    }

    // 迭代：BFS（队列），按层收集
    public static List<List<Integer>> levelOrderTraversalIter(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        if (root == null) {
            return levels;
        }

        Deque<TreeNode> q = new ArrayDeque<>();
        q.offerLast(root);
        while (!q.isEmpty()) {
            int size = q.size();              // 当前层节点数
            List<Integer> lv = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode node = q.pollFirst();     // 出队当前层
                lv.add(node.val);
                if (node.left != null) q.offerLast(node.left);
                if (node.right != null) q.offerLast(node.right);
            }
            levels.add(lv);
        }
        return levels;
    }
    // 如果是锯齿层序遍历（zigzag）
    // 则在迭代的基础上，增加一个 boolean 变量 isLefttoRight 来控制当前层的添加顺序
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Deque<TreeNode> q = new LinkedList<>();
        q.offer(root);
        boolean isLefttoRight = true;
        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> lv = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if(isLefttoRight){
                    lv.addLast(node.val);
                }else{
                    lv.addFirst(node.val);
                }
                if (node.left != null) q.offer(node.left);
                if (node.right != null) q.offer(node.right);
            }
            isLefttoRight = !isLefttoRight;
            res.add(lv);
        }
        return res;
    }


    // ================= 构建树（层序数组）=================
    // 示例输入是一个层序遍历的数组，null 代表空节点，例如：[1,2,3,4,5,null,6]
    public static TreeNode buildTree(Integer[] arr) {
        if (arr.length == 0 || arr[0] == null) return null;

        TreeNode root = new TreeNode(arr[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1; // 从数组第二个元素开始，依次为每个节点添加左右孩子

        while (!queue.isEmpty() && i < arr.length) {
            TreeNode curr = queue.poll();

            // 左孩子
            if (i < arr.length && arr[i] != null) {
                curr.left = new TreeNode(arr[i]);
                queue.offer(curr.left);
            }
            i++;

            // 右孩子
            if (i < arr.length && arr[i] != null) {
                curr.right = new TreeNode(arr[i]);
                queue.offer(curr.right);
            }
            i++;
        }

        return root;
    }


    public static void main(String[] args) {
//        // 1. 手动构建一棵树
//        //       1
//        //      / \
//        //     2   3
//        //    / \   \
//        //   4   5   6
//        TreeNode root = new TreeNode(1);
//        root.left  = new TreeNode(2);
//        root.right = new TreeNode(3);
//        root.left.left  = new TreeNode(4);
//        root.left.right = new TreeNode(5);
//        root.right.right = new TreeNode(6);
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入树的层序遍历（用逗号分隔，空节点用 null 表示）：");
        String input = sc.nextLine(); // 例如输入：1,2,3,4,5,null,6
        String[] parts = input.split(",");
        Integer[] arr = new Integer[parts.length];
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].trim(); // 去除可能的空格
            // 如果是 "null"，则对应位置为 null；否则解析为整数
            arr[i] = part.equals("null") ? null : Integer.parseInt(part);
        }
        TreeNode root = buildTree(arr);

        // 2. 测试前序遍历
        System.out.println("前序遍历（递归）: " + preorderTraversal(root));
        System.out.println("前序遍历（迭代）: " + preorderTraversalIter(root));

        // 3. 测试中序遍历
        System.out.println("中序遍历（递归）: " + inorderTraversal(root));
        System.out.println("中序遍历（迭代）: " + inorderTraversalIter(root));

        // 4. 测试后序遍历
        System.out.println("后序遍历（递归）: " + postorderTraversal(root));
        System.out.println("后序遍历（迭代）: " + postorderTraversalIter(root));

        // 5. 测试层序遍历
        System.out.println("层序遍历（递归）: " + levelOrderTraversal(root));
        System.out.println("层序遍历（迭代）: " + levelOrderTraversalIter(root));
    }

}
