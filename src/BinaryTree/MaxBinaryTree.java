package BinaryTree;
import com.sun.source.tree.Tree;

import java.util.*;

// 给定一个不重复的整数数组nums 。最大二叉树可以用下面的算法从nums 递归地构建:
//1. 创建一个根节点，其值为nums 中的最大值。
//2. 递归地在最大值左边的子数组前缀上构建左子树。
//3. 递归地在最大值 右边 的子数组后缀上构建右子树。
//返回nums 构建的 最大二叉树 。

public class MaxBinaryTree {

//    // 递归解法 时间复杂度较高
//    public static TreeNode constructMaximumBinaryTree(int[] nums) {
//        return build(nums,0,nums.length-1);
//    }
//
//    private static TreeNode build(int[] nums, int l, int r){
//        if(l > r) return null;
//        int idx = l;
//        for(int i = l + 1;i <= r;i++) {
//            if(nums[i] > nums[idx]) idx = i;
//        }
//        TreeNode root = new TreeNode(nums[idx]);
//        root.left = build(nums, l, idx - 1);
//        root.right = build(nums, idx+1, r);
//        return root;
//    }

    // 单调栈
    // 栈底到栈顶严格递减，弹出的节点挂到当前元素左侧，未被弹出的栈顶把当前元素挂到其右侧
    public static TreeNode constructMaximumBinaryTree(int[] nums) {
        Deque<TreeNode> st = new ArrayDeque<>();
        for(int x : nums){
            TreeNode cur = new TreeNode(x);

            // 比当前小的全都弹出
            while(!st.isEmpty() && st.peek().val < x){
                cur.left = st.pop(); // 被弹出的最后一个会成为cur.left
                // 被弹出的“前几个”节点并没有丢，通过“右孩子指针”串成了一条链
            }

            if(!st.isEmpty()) {
                st.peek().right = cur;
            }
            st.push(cur);
        }
        TreeNode root = null;
        while(!st.isEmpty()){
            root = st.pop();
        }
        printTree(root);
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
        int[] nums = new int[length];
        for(int i =0;i<length;i++){
            nums[i] = sc.nextInt();
        }
        TreeNode root = constructMaximumBinaryTree(nums);
    }
}
