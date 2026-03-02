package list;
import java.util.*;

//class ListNode {
//    int val;
//    ListNode next;
//    ListNode(int val) {
//        this.val = val;
//    }
//}

public class ReverseList {

    // 反转链表  迭代法
    public static ListNode reverseList(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;
        ListNode tmp = null; //临时存储
        while (cur != null) {
            tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }

    // 反转链表   递归法
    public static ListNode reverseList1(ListNode head){
        if (head == null || head.next ==null) return head; // 终止条件
        ListNode newHead = reverseList1(head.next); // 递归调用
        head.next.next = head; // 改指针
        head.next = null; // 断开指针
        return newHead;
    }
//    返回类型 function(参数) {
//        if (终止条件) return 最简单情况结果;   // ① base case
//        子问题结果 = function(更小的参数);       // ② recursion
//        根据子问题结果做一些操作;              // ③ combine
//        return 最终结果;
//    }

    // 反转链表 II  反转链表的一部分
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (head == null) return null;
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next; // pre指向left的前一个节点
        }

        ListNode cur = pre.next;
        // 反转[left, right]之间的节点
        for (int i = 0; i < right - left; i++) {
            ListNode next = cur.next; // next指向cur的下一个节点

            cur.next = next.next; // cur指向next的下一个节点，断开cur和next的连接
            next.next = pre.next; // next指向pre的下一个节点，连接next和pre
            pre.next = next; // pre指向next，连接pre和next
        }
        return dummy.next;
    }


    // 从数组构建链表
    public static ListNode buildList(int[] arr) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int x : arr) {
            cur.next = new ListNode(x);
            cur = cur.next;
        }
        return dummy.next;
    }

    // 打印链表
    public static void printList(ListNode head) {
        ListNode cur = head;
        while (cur != null) {
            System.out.print(cur.val);
            if (cur.next != null) System.out.print(" ");
            cur = cur.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        ListNode head = buildList(arr);
        printList(head);
        ListNode newHead = reverseList(head);
        printList(newHead);

//        // 读取多组数据，直到输入结束
//        while (sc.hasNextInt()) {
//            int n = sc.nextInt();
//
//            if (n == 0) {
//                System.out.println("list is empty");
//                continue; // 跳过本次循环，继续看下一组数据
//            }
//
//            int[] arr = new int[n];
//            for (int i = 0; i < n; i++) {
//                arr[i] = sc.nextInt();
//            }
//
//            // 1. 构建并打印原链表
//            ListNode head = buildList(arr);
//            printList(head);
//
//            // 2. 反转并打印新链表
//            ListNode newHead = reverseList(head);
//            printList(newHead);
//        }
    }
}
