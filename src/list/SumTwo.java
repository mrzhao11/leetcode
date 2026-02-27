package list;

public class SumTwo {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(-1); // 虚拟头节点
        ListNode h = pre;
        ListNode h1 = l1;
        ListNode h2 = l2;
        int cur = 0;
        int next = 0;
        while (h1 != null && h2 != null) {
            cur = h1.val + h2.val + next;
            next = cur / 10; // 进位
            cur = cur % 10; // 当前位的值
            h.next = new ListNode(cur);
            h1 = h1.next;
            h2 = h2.next;
            h = h.next;
        }
        // 如果两个链表长度不一样，继续处理剩余的部分
        while (h1 != null) {
            cur = h1.val + next;
            next = cur / 10;
            cur = cur % 10;
            h.next = new ListNode(cur);
            h1 = h1.next;
            h = h.next;
        }
        while (h2 != null) {
            cur = h2.val + next;
            next = cur / 10;
            cur = cur % 10;
            h.next = new ListNode(cur);
            h2 = h2.next;
            h = h.next;
        }
        // 最后如果还有进位，添加一个新的节点
        if (next != 0) {
            h.next = new ListNode(next);
        }

        return pre.next;
    }

    public static void main(String[] args) {
        // 测试代码
        SumTwo solution = new SumTwo();
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
        ListNode result = solution.addTwoNumbers(l1, l2);
        // 输出结果
        while(result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }
}
