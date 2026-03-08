package list;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

// 设计一个缓存，支持 get / put 都是 O(1)，并且在容量满时淘汰“最近最少使用”的数据。
// HashMap + 双向链表实现 LRUCache，HashMap 用于快速访问结点，双向链表用于维护结点的使用顺序
public class LRUCache {

    // 数据结构：双向链表结点
    class Node {
        int key, value; // 结点的键和值
        Node prev, next; // 结点的前驱和后继

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private int capacity; // 缓存的容量
    private Map<Integer, Node> map; // 哈希表，存储键与结点的映射，方便快速访问结点
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

// 如果是带过期时间的且线程安全的LRU
class LRUWithTTL {
    private final ReentrantLock lock = new ReentrantLock(); // 锁对象，用于同步访问缓存

    // 结点类，包含键、值、过期时间和指向下一个结点的指针
    static class Node {
        int key, value;
        long expireTime; // 过期时间，单位为毫秒
        Node prev, next;

        Node(int key, int value, long ttl) {
            this.key = key;
            this.value = value;
            this.expireTime = System.currentTimeMillis() + ttl; // 设置过期时间为当前时间加上 TTL
        }
    }

    Node head, tail;
    Map<Integer, Node> map;
    int capacity;

    public LRUWithTTL(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(0, 0, 0); // dummy head
        tail = new Node(0, 0, 0); // dummy tail
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        lock.lock();
        try {
            if (!map.containsKey(key)) return -1;
            cleanExpired();
            Node node = map.get(key);
            if (System.currentTimeMillis() > node.expireTime) {
                // 结点已过期，移除结点并返回 -1
                remove(node);
                map.remove(key);
                return -1;
            }
            // 结点未过期，移动到头部并返回值
            remove(node);
            moveToHead(node);
            return node.value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void put(int key, int value, long ttl) {
        lock.lock();
        try {
            cleanExpired();
            if (map.containsKey(key)) {
                Node node = map.get(key);
                node.value = value;
                node.expireTime = System.currentTimeMillis() + ttl; // 更新过期时间
                remove(node);
                moveToHead(node);
            } else {
                if (map.size() == capacity) {
                    // 容量已满，移除尾部结点（最久未使用）
                    Node lru = tail.prev;
                    remove(lru);
                    map.remove(lru.key);
                }
                Node newNode = new Node(key, value, ttl);
                moveToHead(newNode);
                map.put(key, newNode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    // 定期清理过期结点，可以在 get/put 操作前调用，或者使用单独的线程定时调用
    private void cleanExpired() {
        long now = System.currentTimeMillis();
        Node cur = tail.prev;

        // 从尾部开始检查结点，如果结点过期则移除，直到遇到未过期的结点或到达头部
        while (cur != head && now > cur.expireTime) {
            Node prev = cur.prev;
            remove(cur);
            map.remove(cur.key);
            cur = prev;
        }
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }
}

