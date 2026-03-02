package list;

public class Reorder {
    // 重排链表
    // 给定一个单链表 L：L0→L1→…→Ln-1→Ln ，将其重新排列后变为：L0→Ln→L1→Ln-1→L2→Ln-2→…
    // 不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
    public void reorderList(ListNode head) {
        if(head == null) return;
        // 快慢指针找到链表中点
        ListNode slow = head;
        ListNode fast = head;
        while(fast.next!=null && fast.next.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode l1 = head;
        ListNode l2 = slow.next;
        slow.next = null;
        // 反转后半部分链表
        l2 = reverseList(l2);
        // 合并两条链表
        mergeList(l1,l2);

    }

    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    // 合并两条链表，l1和l2交替连接，直到其中一条链表被完全连接完
    public void mergeList(ListNode l1,ListNode l2){
        // l1_tmp和l2_tmp分别保存l1和l2的下一个节点，防止链表断开
        ListNode l1_tmp;
        ListNode l2_tmp;
        while (l1 != null && l2 != null) {
            l1_tmp = l1.next;
            l2_tmp = l2.next;

            l1.next = l2;
            l1 = l1_tmp;

            l2.next = l1;
            l2 = l2_tmp;
        }
    }
}
