package list;

import java.util.*;

public class DeleteDuplicate {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for(int i=0; i<n; i++) {
            arr[i] = sc.nextInt();
        }
        ListNode head = buildList(arr);
        ListNode head2 = buildList(arr);
        ListNode newHead = deleteDuplicates(head);
        ListNode newHead2 = deleteDuplicates2(head2);
        printList(newHead);
        printList(newHead2);

    }

    // buildlist
    public static ListNode buildList(int[] arr){
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for(int x : arr){
            cur.next = new ListNode(x);
            cur = cur.next;
        }
        return dummy.next;
    }

    // printList
    public static void printList(ListNode head){
        ListNode cur = head;
        while(cur!=null){
            System.out.print(cur.val);
            if(cur.next!=null) System.out.print(" ");
            cur = cur.next;
        }
        System.out.println();
    }

    // 删除排序链表中所有重复元素
    // 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
    public static ListNode deleteDuplicates(ListNode head) {
        if(head == null) return null;
        ListNode cur = head;
        while(cur.next != null){
            if(cur.val == cur.next.val){
                cur.next = cur.next.next; // 跳过重复节点
            } else {
                cur = cur.next; // 只有不重复时才移动cur
            }
        }
        return head;
    }

    // 给定一个排序链表，删除所有重复的元素，只留下不同的元素。
    public static ListNode deleteDuplicates2(ListNode head) {
        if(head == null) return null;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy; // prev指向当前不重复的最后一个节点
        ListNode cur = head;   // cur用于遍历链表

        while(cur != null){
            // 如果cur和cur.next相等，说明cur是重复节点，继续往后找直到找到不重复的节点
            while(cur.next != null && cur.val == cur.next.val){
                cur = cur.next; // 跳过重复节点
            }
            // 此时cur指向最后一个重复节点，如果prev.next == cur，说明prev和cur之间没有重复节点，prev可以直接移动到cur
            if(prev.next == cur){
                prev = prev.next; // 没有重复，prev移动到cur
            } else {
                prev.next = cur.next; // 有重复，跳过所有重复节点
            }
            cur = cur.next; // 继续遍历下一个节点
        }
        return dummy.next;
    }
}
