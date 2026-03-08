package BinaryTree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

// 二叉树的序列化与反序列化
public class Codec {

    // Encodes a tree to a single string.
    // 采用前序遍历的方式进行序列化，遇到 null 节点时用 "null" 表示
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        dfs(root, sb);
        return sb.toString();
    }

    private void dfs(TreeNode root, StringBuilder sb) {
        if(root == null){
            sb.append("null,");
            return;
        }

        sb.append(root.val).append(",");
        dfs(root.left, sb);
        dfs(root.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] arr = data.split(",");
        Queue<String> queue = new LinkedList<>(Arrays.asList(arr)); // 将数组转换成队列，方便按顺序处理每个节点的值
        return build(queue);
    }

    // 递归函数作用：从队列中依次取出节点值，构建二叉树
    private TreeNode build(Queue<String> queue) {
        String val = queue.poll();

        if(val.equals("null")){
            return null;
        }

        TreeNode node = new TreeNode(Integer.parseInt(val));

        node.left = build(queue);
        node.right = build(queue);

        return node;
    }
}
