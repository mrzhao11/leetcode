package BinaryTree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class MaxPathSum {
    // 二叉树中的最大路径和
    // 路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。
    // 同一个节点在一条路径序列中 至多出现一次 。 该路径至少包含一个节点，且不一定经过根节点。
    private int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxGain(root);
        return maxSum;
    }

    // 考虑最小的情况
    // 1. 根+左+右
    // 2. 根+左
    // 3. 根+右
    // 4. 根
    // 5. 左
    // 6. 右
    // 只有2.3.4会向上传递贡献值
    // 可以分为两种情况：可以向上传递贡献值的和不可以向上传递贡献值的，二者取最大值
    // 注意递归函数要返回可以向上传递贡献值的情况，因为递归函数的返回值是给父节点使用的
    private int maxGain(TreeNode node) {
        if (node == null) {
            return 0;
        }

        // 递归计算左右子节点的最大贡献值
        // 如果子节点的贡献值为负，则不如不选择该子节点，因此我们只取大于0的贡献值
        int leftGain = Math.max(maxGain(node.left), 0);
        int rightGain = Math.max(maxGain(node.right), 0);

        // 可以向上传递贡献值的情况，即根节点加上左右子节点中较大的贡献值
        int can = node.val + Math.max(leftGain, rightGain);
        // 不可以向上传递贡献值的情况，即根节点加上左右子节点的贡献值
        int cant = node.val + leftGain + rightGain;

        maxSum = Math.max(maxSum, Math.max(can, cant)); // 更新全局最大路径和

        // 返回节点的最大贡献值
        return can;
    }

    // 从数组构建二叉树的辅助函数，方便测试
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
//        TreeNode root = new TreeNode(-10);
//        root.left = new TreeNode(9);
//        root.right = new TreeNode(20);
//        root.right.left = new TreeNode(15);
//        root.right.right = new TreeNode(7);
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] parts = input.split(",");
        Integer[] arr = new Integer[parts.length];
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].trim();
            arr[i] = part.equals("null") ? null : Integer.parseInt(part);
        }
        TreeNode root = buildTree(arr);// 示例输入：-10,9,20,null,null,15,7

        MaxPathSum solution = new MaxPathSum();
        int result = solution.maxPathSum(root);
        System.out.println("最大路径和: " + result); // 输出: 最大路径和: 42
    }
}
