package BinaryTree;

import java.util.*;

public class zigzaglevel {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine().trim(); // 读取一行输入（例如：1 2 3 -1 -1 4 -1 -1 5 -1 -1）

        String[] parts = line.split("\\s+"); // 按空格分割输入字符串，得到每个数字的字符串表示
        TreeNode root = buildTree(parts); // 构建二叉树

        List<List<Integer>> res = zigzagLevelOrder(root);

        for (List<Integer> level : res) {
            for (int i = 0; i < level.size(); i++) {
                if (i > 0) System.out.print(" ");
                System.out.print(level.get(i));
            }
            System.out.println();
        }
    }

    // 构建二叉树（层序输入，-1表示null）
    static TreeNode buildTree(String[] arr) {
        if (arr.length == 0 || arr[0].equals("-1")) return null;

        TreeNode root = new TreeNode(Integer.parseInt(arr[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int index = 1;

        // 使用队列进行层序构建
        while (!queue.isEmpty() && index < arr.length) {
            TreeNode node = queue.poll();

            // 左子树
            if (index < arr.length && !arr[index].equals("-1")) {
                node.left = new TreeNode(Integer.parseInt(arr[index]));
                queue.offer(node.left);
            }
            index++;

            // 右子树
            if (index < arr.length && !arr[index].equals("-1")) {
                node.right = new TreeNode(Integer.parseInt(arr[index]));
                queue.offer(node.right);
            }
            index++;
        }

        return root;
    }

    // 锯齿形层序遍历
    static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int size = queue.size();
            // 这里使用 LinkedList 是为了方便在两端添加元素，ArrayList 只能在末尾添加
            List<Integer> level = new LinkedList<>();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();

                if (leftToRight) {
                    level.addLast(node.val); // 从左到右添加
                } else {
                    level.addFirst(node.val); // 从右到左添加
                }

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            res.add(level);
            leftToRight = !leftToRight; // 方向切换
        }

        return res;
    }
}
