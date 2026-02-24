package list;

import java.util.*;

public class DeleteDuplicate {

    // 链表节点定义
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 读取一行输入（例如：1 2 3 3 4 4 5）
        String line = sc.nextLine().trim();

        String[] parts = line.split("\\s+"); // 按空格分割输入字符串，得到每个数字的字符串表示

        // 构建链表
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        for (String p : parts) {
            int num = Integer.parseInt(p); // 将字符串转换为整数
            tail.next = new ListNode(num); // 创建新节点并连接到链表
            tail = tail.next; // 移动尾指针到新节点
        }

        // 删除所有重复元素
        ListNode head = deleteDuplicates(dummy.next);

        // 输出结果
        ListNode cur = head;
        boolean first = true; // 用于控制输出格式，避免在第一个元素前输出空格
        while (cur != null) {
            if (!first) System.out.print(" ");
            System.out.print(cur.val);
            first = false;
            cur = cur.next;
        }
    }

    // 删除排序链表中所有重复元素（只保留出现一次的节点）
    public static ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;  // 指向当前确定不重复的尾部

        while (head != null) {
            // 如果当前节点有重复
            if (head.next != null && head.val == head.next.val) {
                int val = head.val;
                // 跳过所有相同值的节点
                while (head != null && head.val == val) {
                    head = head.next;
                }
                prev.next = head; // 连接到下一个不同值节点
            } else {
                // 当前节点不重复，正常推进
                prev = prev.next;
                head = head.next;
            }
        }
        return dummy.next;
    }
}
