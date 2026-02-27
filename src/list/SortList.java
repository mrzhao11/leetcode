package list;

public class SortList {
    // 排序链表，自顶向下归并排序
    // 首先找到链表的中点，然后递归地对左右两部分进行排序，最后合并两个有序链表
    public static ListNode sortList(ListNode head) {
        // 递归终止条件：0 或 1 个节点
        if (head == null || head.next == null) {
            return head;
        }

        // 快慢指针找中点
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 断链
        ListNode mid = slow.next;
        slow.next = null;

        // 递归排序左右两段
        ListNode left = sortList(head);
        ListNode right = sortList(mid);

        // 合并两个有序链表
        return mergeSortList(left, right);
    }

    // 排序链表，自底向上归并排序
    // 不使用递归，通过不断合并长度为 subLen 的子链表来实现排序
    public static ListNode sortListBottomUp(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 计算链表长度
        int length = 0;
        ListNode p = head;
        while (p != null) {
            length++;
            p = p.next;
        }

        // 虚拟头节点，方便合并
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // subLen 表示当前子链表长度：1, 2, 4, 8...
        // subLen <<= 1 相当于 subLen *= 2,即每次翻倍
        // 也就是每次先两两合并，再四四合并，以此类推，直到子链表长度大于等于链表总长度
        for (int subLen = 1; subLen < length; subLen <<= 1) {

            ListNode prev = dummy;
            ListNode curr = dummy.next;

            while (curr != null) {

                // 切出第一个长度为 subLen 的链表
                ListNode head1 = curr;
                for (int i = 1; i < subLen && curr.next != null; i++) {
                    curr = curr.next;
                }

                // 切出第二个长度为 subLen 的链表
                ListNode head2 = curr.next;
                curr.next = null;      // 断开
                curr = head2; // 移动到第二个子链表的起点
                // 允许第二个子链表长度不足 subLen
                for (int i = 1; i < subLen && curr != null && curr.next != null; i++) {
                    curr = curr.next;
                }

                // 保存下一轮起点
                ListNode next = null;
                if (curr != null) {
                    next = curr.next;
                    curr.next = null; // 再断一次
                }

                // 合并 head1 和 head2
                ListNode merged = mergeSortList(head1, head2);

                // 接回到已排序部分
                prev.next = merged;
                while (prev.next != null) {
                    prev = prev.next;
                }

                curr = next; // 移动到下一轮的起点
            }
        }

        return dummy.next;
    }

    // 合并两个有序链表递归写法 空间复杂度 O(n)
//    private static ListNode mergeSortList(ListNode l1,ListNode l2){
//        if(l1 == null){
//            return l2;
//        }else if(l2 == null){
//            return l1;
//        }else if(l1.val < l2.val){
//            l1.next = mergeSortList(l1.next,l2);
//            return l1;
//        }else{
//            l2.next= mergeSortList(l1,l2.next);
//            return l2;
//        }
//    }
    // 合并两个有序链表迭代写法 空间复杂度 O(1)
    private static ListNode mergeSortList(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
        }

        if (l1 != null) {
            tail.next = l1;
        } else {
            tail.next = l2;
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        // 测试代码
        ListNode head = new ListNode(4);
        head.next = new ListNode(2);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(3);

        ListNode result = sortList(head);
        // 输出结果
        while(result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }

        ListNode head2 = new ListNode(4);
        head2.next = new ListNode(2);
        head2.next.next = new ListNode(1);
        head2.next.next.next = new ListNode(3);

        ListNode result2 = sortListBottomUp(head2);
        System.out.println();
        // 输出结果
        while(result2 != null) {
            System.out.print(result2.val + " ");
            result2 = result2.next;
        }
    }
}
