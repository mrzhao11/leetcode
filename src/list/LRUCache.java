package list;

import java.util.HashMap;
import java.util.Map;

// 设计一个缓存，支持 get / put 都是 O(1)，并且在容量满时淘汰“最近最少使用”的数据。
// HashMap负责O(1)的查找，双向链表负责O(1)的插入和删除。
public class LRUCache {

    // 数据结构：双向链表结点
    class Node {
        int key,value; // 结点的键和值
        Node prev,next; // 结点的前驱和后继
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private int capacity; // 缓存的容量
    private Map<Integer, Node> map; // 哈希表，存储键和值对应的结点
    // 双向链表的头结点和尾结点，头部表示最近使用，尾部表示最久未使用
    private Node head, tail;

    // 构造函数，初始化缓存容量和数据结构
    public LRUCache(int capacity) {
        this.capacity = capacity;

        map = new HashMap<>();
        head = new Node(0, 0); // dummy head
        tail = new Node(0, 0); // dummy tail
        head.next = tail;
        tail.prev = head;
    }

    // 如果关键字key存在于缓存中，则返回其对应的值，否则返回 -1
    // 注意，每次访问结点后，都要将该结点移动到链表头部，表示最近使用
    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1; // 如果键不存在，返回 -1
        }
        Node node = map.get(key);
        remove(node); // 将结点从当前位置移除
        moveToHead(node); // 将结点插入到头部，表示最近使用
        return node.value; // 返回结点的值
    }

    // 如果关键字key已经存在，则变更其数据值；如果不存在，则插入该组「键-值」。
    // 当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
    // 注意，每次插入或更新结点后，都要将该结点移动到链表头部，表示最近使用
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value; // 更新结点的值
            remove(node); // 将结点从当前位置移除
            moveToHead(node); // 将结点插入到头部，表示最近使用
        } else {
            if (map.size() == capacity) {
                // 容量已满，移除尾部结点（最久未使用）
                Node lru = tail.prev;
                remove(lru);
                map.remove(lru.key);
            }
            Node newNode = new Node(key, value);
            moveToHead(newNode); // 将新结点插入到头部
            map.put(key, newNode); // 将新结点加入哈希表
        }
    }

    // 从链表中移除结点
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // 将结点移动到链表头部
    private void moveToHead(Node node) {
        // 将结点插入到链表头部
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);
        lruCache.put(1, 1); // 缓存是 {1=1}
        lruCache.put(2, 2); // 缓存是 {1=1, 2=2}
        System.out.println(lruCache.get(1));    // 返回 1
        lruCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        System.out.println(lruCache.get(2));    // 返回 -1 (未找到)
        lruCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        System.out.println(lruCache.get(1));    // 返回 -1 (未找到)
        System.out.println(lruCache.get(3));    // 返回 3
        System.out.println(lruCache.get(4));    // 返回 4
    }
}
