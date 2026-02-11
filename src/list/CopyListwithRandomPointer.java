package list;

import java.util.HashMap;
import java.util.Map;

public class CopyListwithRandomPointer {
    // 随机指针链表的复制
    // 给定一个链表，每个节点包含一个额外增加的随机指针，该指针可以指向链表中的任何节点或空节点。
    // 要求返回这个链表的深拷贝。深拷贝应该正好由原链表中每个节点的一个新节点组成，新节点的值与原节点的值相同，
    // 且新节点的 next 指针和 random 指针都指向复制链表中的新节点。
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList(Node head) {
        // 使用哈希表存储原节点到新节点的映射
        Map<Node, Node> map = new HashMap<>();
        Node curr = head;
        // 第一次遍历，创建新节点并存储映射关系
        while (curr != null) {
            map.put(curr, new Node(curr.val));
            curr = curr.next;
        }
        // 第二次遍历，设置新节点的 next 和 random 指针
        curr = head;
        while (curr != null) {
            Node newNode = map.get(curr); // 获取对应的新节点
            newNode.next = map.get(curr.next); // 设置 next 指针
            newNode.random = map.get(curr.random); // 设置 random 指针
            curr = curr.next;
        }
        return map.get(head); // 返回新链表的头节点
    }

    public Node copyRandomListOptimized(Node head) {
        if (head == null) return null;

        // 第一步：在每个节点后面插入一个新节点
        Node curr = head;
        while (curr != null) {
            Node newNode = new Node(curr.val);
            newNode.next = curr.next;
            curr.next = newNode;
            curr = newNode.next;
        }

        // 第二步：设置新节点的 random 指针
        curr = head;
        while (curr != null) {
            if (curr.random != null) {
                curr.next.random = curr.random.next;
            }
            curr = curr.next.next;
        }

        // 第三步：拆分链表，恢复原链表并提取新链表
        curr = head;
        Node newHead = head.next;
        while (curr != null) {
            Node newNode = curr.next; // 当前节点对应的新节点
            curr.next = newNode.next; // 恢复原链表的 next 指针
            if (newNode.next != null) {// 设置新链表的 next 指针
                newNode.next = newNode.next.next;
            }
            curr = curr.next; // 移动到下一个原节点
        }

        return newHead;
    }

    public static void main(String[] args) {
        CopyListwithRandomPointer solution = new CopyListwithRandomPointer();
        Node node1 = solution.new Node(1);
        Node node2 = solution.new Node(2);
        node1.next = node2;
        node1.random = node2;
        node2.random = node2;

        Node copiedList = solution.copyRandomListOptimized(node1);
        System.out.println("Original Node1 Val: " + node1.val + ", Copied Node1 Val: " + copiedList.val);
        System.out.println("Original Node1 Random Val: " + (node1.random != null ? node1.random.val : "null") +
                           ", Copied Node1 Random Val: " + (copiedList.random != null ? copiedList.random.val : "null"));
    }
}
