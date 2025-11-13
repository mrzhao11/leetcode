package BinaryTree;

import java.util.*;

public class FindModeinBST {
    static ArrayList<Integer> resList;
    static int maxCount; //历史最大值出现的次数
    static int count; //当前值出现的次数
    static TreeNode pre; // 上一个访问到的节点

    public static int[] findMode(TreeNode root) {
        resList = new ArrayList<>();
        maxCount = 0;
        count = 0;
        pre = null;
        findMode1(root);
        int[] res = new int[resList.size()];
        for (int i = 0; i < resList.size(); i++) {
            res[i] = resList.get(i);
        }
        return res;
    }

    public static void findMode1(TreeNode root) {
        if (root == null) {
            return;
        }

        // 左
        findMode1(root.left);

        // 中
        int rootValue = root.val;
        // 计数
        // pre null第一个节点 / 换了新值
        if (pre == null || rootValue != pre.val) {
            count = 1;
        } else {
            count++;
        }
        // 更新结果以及maxCount
        if (count > maxCount) {
            resList.clear();
            resList.add(rootValue);
            maxCount = count;
        } else if (count == maxCount) {
            resList.add(rootValue);
        }
        pre = root;

        // 右
        findMode1(root.right);
    }

    public static void main(String[] args) {
        // 1. 手动构建一棵树
        //       4
        //      / \
        //     2   5
        //    / \   \
        //   2   2   6
        TreeNode root = new TreeNode(4);
        root.left  = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left  = new TreeNode(2);
        root.left.right = new TreeNode(2);
        root.right.right = new TreeNode(6);

        System.out.println(Arrays.toString(findMode(root)));
    }
}
