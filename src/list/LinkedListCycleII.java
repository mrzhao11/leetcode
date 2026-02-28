package list;
import java.util.*;

//环形链表  快慢指针

public class LinkedListCycleII {
    // 检测链表是否有环，并返回环的入口节点
    public static ListNode detectCycle(ListNode head) {
        if (head == null) return null;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                ListNode index = head;
                while (index != slow) {
                    index = index.next;
                    slow = slow.next;
                }
                return index; // 环的入口节点
            }
        }
        return null; // 无环
    }

    // 从输入构建链表（带环）
    public static ListNode buildList(int[] arr, int pos) {
        if (arr.length == 0) return null;
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        ListNode cycleEntry = null;
        for (int i = 0; i < arr.length; i++) {
            cur.next = new ListNode(arr[i]);
            cur = cur.next;
            // 记录环入口节点
            if (i == pos) {
                cycleEntry = cur;
            }
        }
        if (cycleEntry != null) {
            cur.next = cycleEntry; // 建立环
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 输入：n 个节点 + 环入口位置 pos
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        int pos = sc.nextInt(); // 环入口位置，-1 表示无环

        ListNode head = buildList(arr, pos);

        ListNode entry = detectCycle(head);

        if (entry == null) {
            System.out.println("null");
        } else {
            System.out.println(entry.val);
        }
    }
}

// 链表相交
 class Solution {
     public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
         if (headA == null || headB == null) return null;

         ListNode pA = headA;
         ListNode pB = headB;

         // 最多走 m+n 步
         while (pA != pB) {
             pA = (pA == null) ? headB : pA.next;
             pB = (pB == null) ? headA : pB.next;
         }
         return pA; // 相交点 或 null
     }
    // 辅助方法：数组转链表
    public static ListNode buildList(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int x : arr) {
            cur.next = new ListNode(x);
            cur = cur.next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        // 模拟输入数据
        // A: 4 -> 1 -> [8 -> 4 -> 5]
        // B: 5 -> 6 -> 1 -> [8 -> 4 -> 5]
        int[] commonArr = {8, 4, 5};
        int[] aOnly = {4, 1};
        int[] bOnly = {5, 6, 1};

        // 1. 构造公共部分
        ListNode common = buildList(commonArr);

        // 2. 构造 A 链表并连接
        ListNode headA = buildList(aOnly);
        if (headA == null) {
            headA = common;
        } else {
            ListNode cur = headA;
            while (cur.next != null) cur = cur.next;
            cur.next = common; // 关键：接上公共部分
        }

        // 3. 构造 B 链表并连接
        ListNode headB = buildList(bOnly);
        if (headB == null) {
            headB = common;
        } else {
            ListNode cur = headB;
            while (cur.next != null) cur = cur.next;
            cur.next = common; // 关键：接上同一个 common 引用
        }

        // 4. 测试
        ListNode result = getIntersectionNode(headA, headB);
        if (result != null) {
            System.out.println(result.val);
        } else {
            System.out.println("null");
        }

    }
 }
// 用两个指针 pA、pB，分别从 headA、headB 出发。
// 每次同时向前走一步。
// 当某个指针走到结尾时，不是停下，而是跳到另一个链表的头继续走。
// 如果两个链表有交点，那么两个指针一定会在交点处相遇；
// 如果没有交点，那么最终两个指针都会变成 null，同时退出循环。

