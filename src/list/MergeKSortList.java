package list;

import java.util.PriorityQueue;

public class MergeKSortList {
    // 合并 k 个有序链表，使用分治法
    public static ListNode mergeKLists(ListNode[] lists) {
        return merge(lists, 0, lists.length - 1);
    }

    // 优先队列法
    // 链表本身是有序的，那么全局最小值一定在各个链表的头节点中，因此我们可以使用一个最小堆（优先队列）来存储各个链表的头节点
    public static ListNode mergeKListsPQ(ListNode[] lists) {
        if(lists == null || lists.length == 0) return null;
        // 优先队列（最小值优先），按节点值排序
        PriorityQueue<ListNode> pq =
                new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));

        for(ListNode list : lists) {
            if(list != null) {
                pq.offer(list);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;

        // 不断取出最小节点，加入结果链表
        while(!pq.isEmpty()) {
            ListNode node = pq.poll(); // 取出最小节点
            cur.next = node;
            cur = cur.next;
            if(node.next != null) {
                pq.offer(node.next); // 把最小节点的下一个节点加入优先队列
            }
        }
        return dummy.next;
    }

    public static ListNode merge(ListNode[] lists, int left, int right) {
        // 递归终止条件
        // 如果 left 超过 right，说明没有链表可合并，返回 null
        // 如果 left 等于 right，说明只有一个链表，直接返回该链表
        if (left > right) return null;
        if (left == right) return lists[left];

        int mid = left + (right - left) / 2; // 防止溢出
        ListNode l1 = merge(lists, left, mid);
        ListNode l2 = merge(lists, mid + 1, right);

        return mergeTwoLists(l1, l2);
    }

    private static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
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
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(5);

        ListNode l2 = new ListNode(1);
        l2.next = new ListNode(3);
        l2.next.next = new ListNode(4);

        ListNode l3 = new ListNode(2);
        l3.next = new ListNode(6);

        ListNode[] lists = new ListNode[]{l1, l2, l3};

        ListNode result = mergeKLists(lists);
        // 输出结果
        while (result != null) {
            System.out.print(result.val + " -> ");
            result = result.next;
        }
        System.out.println("null");
    }
}

