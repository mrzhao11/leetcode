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

        // 输入链表长度
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        // 构建链表
        ListNode head = buildList(arr);

        // 反转链表
        ListNode newHead = reverseList(head);

        // 输出结果
        printList(newHead);
    }
}
