package BinaryTree;

import java.util.*;

public class BuildTreeInorderPostorder {

//    public static TreeNode buildTree(int[] inorder, int[] postorder) {
//        return traversal(inorder, postorder);
//    }
//
//    private static TreeNode traversal(int[] inorder, int[] postorder) {
//        if(postorder.length == 0) return null;
//        int rootValue = postorder[postorder.length - 1];
//        TreeNode root = new TreeNode(rootValue);
//
//        if (postorder.length == 1) return root;
//
//        int idx = 0;
//        for(;idx<inorder.length-1;idx++){
//            if(inorder[idx] == root.val) break;
//        } // 1
//        int[] leftinorder = new int[idx]; // 1
//        int[] rightinorder = new int[inorder.length - idx - 1]; // 3
//        int[] leftpostorder = new int[leftinorder.length];
//        int[] rightpostorder = new int[rightinorder.length];
//        for(int i = 0; i<leftinorder.length;i++){
//            leftinorder[i] = inorder[i];
//        }
//        idx++;
//        for(int i = 0; i<rightinorder.length;i++){
//            rightinorder[i] = inorder[idx++];
//        }
//        for(int i = 0;i<leftpostorder.length;i++){
//            leftpostorder[i] = postorder[i];
//        }
//        int l = leftpostorder.length;
//        for(int i = 0;i<rightpostorder.length;i++){
//            rightpostorder[i] = postorder[l++];
//        }
//        root.left = traversal(leftinorder,leftpostorder);
//        root.right = traversal(rightinorder,rightpostorder);
//        return root;
//    }

    // 改进
    private static Map<Integer, Integer> indexMap; // inorder 值 -> 索引
    public static TreeNode buildTree(int[] inorder, int[] postorder) {
        // 建立哈希表，用 O(1) 查找根在 inorder 中的位置
        indexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            indexMap.put(inorder[i], i);
        }
        return traversal(inorder, 0, inorder.length - 1,
                postorder, 0, postorder.length - 1);
    }

    // 加了索引区间参数
    private static TreeNode traversal(int[] inorder, int inL, int inR,
                               int[] postorder, int postL, int postR) {
        if (postL > postR || inL > inR) return null;

        // 根节点是 postorder 的最后一个元素
        int rootValue = postorder[postR];
        TreeNode root = new TreeNode(rootValue);

        // 在 inorder 中找到根的位置
        int idx = indexMap.get(rootValue);
        int leftSize = idx - inL; // 左子树大小

        // 左右子树递归区间
        root.left = traversal(inorder, inL, idx - 1,
                postorder, postL, postL + leftSize - 1);
        root.right = traversal(inorder, idx + 1, inR,
                postorder, postL + leftSize, postR - 1);
        return root;
    }

    public static void printTree(TreeNode root) {
        if (root == null) {
            System.out.println("[]");
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                System.out.print("null ");
                continue;
            }
            System.out.print(node.val + " ");
            queue.offer(node.left);
            queue.offer(node.right);
        }
        System.out.println();
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int length = sc.nextInt();
        int[] inorder = new int[length];
        int[] postorder = new int[length];
        for(int i =0;i<length;i++){
            inorder[i] = sc.nextInt();
        }
        for(int i =0;i<length;i++){
            postorder[i] = sc.nextInt();
        }
        TreeNode root = buildTree(inorder,postorder);
        printTree(root);
    }
}
