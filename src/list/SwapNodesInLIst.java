package list;
import java.util.*;

public class SwapNodesInLIst {
    //迭代法
    public static ListNode SwapNodes(ListNode head){
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = dummy;
        while(cur.next!=null&&cur.next.next!=null){
            ListNode first = cur.next;
            ListNode second = cur.next.next;
            ListNode nextpair = second.next;

            cur.next = second;
            second.next = first;
            first.next = nextpair;

            cur = first;
        }
        return dummy.next;
    }
    //交换一对节点
    // cur -> first -> second -> nextPair
    // 变成 cur -> second -> first -> nextPair

    //递归法
    public static ListNode SwapNodes1 (ListNode head) {
        if (head == null || head.next == null) return head; // 1. 递归出口

        ListNode next = head.next;            // 2. 第二个节点
        ListNode newNode = SwapNodes1(next.next); // 3. 递归处理后续链表
        next.next = head;                     // 4. 第二个节点 → 第一个节点
        head.next = newNode;                  // 5. 第一个节点 → 后续交换完成的链表

        return next;                          // 6. 返回新的头节点
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

        // 输入链表长度
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        // 构建链表
        ListNode head = buildList(arr);

        // 反转链表
        ListNode newHead = SwapNodes(head);

        // 输出结果
        printList(newHead);
    }
}
