package StackandQueue;
import java.util.*;

class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
    }
}

// 设计一个循环队列，支持以下操作：
// MyCircularQueue(k): 构造器，设置队列长度为 k 。
// Front: 从队首获取元素。如果队列为空，返回 -1 。
// Rear: 获取队尾元素。如果队列为空，返回 -1 。
// enQueue(value): 向循环队列插入一个元素。如果成功插入则返回真。
// deQueue(): 从循环队列中删除一个元素。如果成功删除则返回真。
// isEmpty(): 检查循环队列是否为空。
// isFull(): 检查循环队列是否已满。
public class MyCircularQueue {

    private ListNode head; // 队列的头结点，指向队首元素
    private ListNode tail; // 队列的尾结点，指向队尾元素
    private int capacity; // 队列的容量，表示队列中最多可以容纳的元素个数
    private int size; // 队列的当前大小，表示队列中当前元素的个数

    public MyCircularQueue(int k) {
        capacity = k;
        size = 0;
    }

    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        ListNode node = new ListNode(value);
        // 如果队列为空，则新结点既是头结点也是尾结点；否则，将新结点链接到尾结点的 next，并更新尾结点为新结点
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
        return true;
    }

    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        // 将头结点的 next 赋值给头结点，表示出队；如果出队后队列变为空，则将尾结点也置为 null
        ListNode node = head;
        head = head.next;
        size--;
        return true;
    }

    public int Front() {
        if (isEmpty()) {
            return -1;
        }
        return head.val;
    }

    public int Rear() {
        if (isEmpty()) {
            return -1;
        }
        return tail.val;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }
}
