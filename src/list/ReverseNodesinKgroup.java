package list;

public class ReverseNodesinKgroup {

    // K个一组翻转链表
    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;   // 上一组的尾
        ListNode end = dummy;   // 每组的尾

        while (true) {
            // 找到当前组的 end
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            if (end == null) break; // 不足 k 个，结束

            ListNode start = pre.next; // 当前组的头
            ListNode next = end.next; // 下一组的头

            // 断开并反转
            end.next = null;
            pre.next = reverse(start); // 反转后，pre 指向新的头节点
            start.next = next; // 原来的头节点变成了尾节点，指向下一组的头节点

            // 指针后移，进入下一组
            pre = start;
            end = pre;
        }

        return dummy.next;
    }

    // 反转链表，返回的是新的头节点，即原来的尾节点
    private static ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }

    public static void main(String[] args) {
        // 测试代码
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        int k = 2;
        ListNode result = reverseKGroup(head, k);
        // 输出结果
        while(result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }
}
