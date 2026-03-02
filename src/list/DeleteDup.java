package list;

public class DeleteDup {
    // 删除链表中的重复元素II
    // 给定一个排序链表，删除所有含有重复数字的节点，只保留原始链表中没有重复出现的数字。
    public static ListNode deleteDuplicates(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode dummy = new ListNode(0); // 哑节点
        dummy.next = head;
        ListNode prev = dummy; // prev指向当前不重复的最后一个节点
        ListNode curr = head; // curr指向当前正在检查的节点

        while(curr != null){
            // 如果curr和curr.next相等，说明curr是重复的
            if(curr.next != null && curr.val == curr.next.val){
                // 跳过所有与curr相等的节点
                while(curr.next != null && curr.val == curr.next.val){
                    curr = curr.next;
                }
                // 连接prev和curr.next，跳过所有重复节点
                prev.next = curr.next;
            }else{
                // 如果curr不重复，prev向前移动
                prev = prev.next;
            }
            // curr向前移动
            curr = curr.next;
        }
        return dummy.next; // 返回去掉重复元素后的链表头
    }
}
