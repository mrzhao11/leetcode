package list;

public class SumTwo {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 虚拟头节点
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        int carry = 0; // 进位

        // 遍历两个链表
        while(l1 != null || l2 != null || carry != 0) {
            int sum = carry; // 当前位的和
            if(l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if(l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }

            // 计算新的进位和当前节点的值
            // 例: sum = 15 -> carry = 1, node.val = 5
            carry = sum / 10; // 更新进位
            cur.next = new ListNode(sum % 10); // 创建新节点
            cur = cur.next;
        }
        return dummy.next;
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
