package hashtable;

import java.util.concurrent.locks.ReentrantLock;

// 实现一个简单的哈希表，支持 put 和 get 操作
public class MyHashMap {

    // 哈希表的结点类，包含键、值和指向下一个结点的指针
    static class Node {
        int key;
        int value;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    // 哈希表的底层数组，长度为 16，使用链表法解决哈希冲突
    Node[] table = new Node[16];

    // 插入或更新键值对，如果键已存在则更新值，否则插入新结点
    void put(int key, int value) {
        int index = key & (table.length - 1); // 计算哈希值并映射到数组索引，使用位运算代替取模运算

        Node node = table[index]; // 获取哈希表中对应索引的链表头结点

        // 遍历链表，查找是否存在相同键的结点，如果存在则更新值并返回
        while (node != null) {
            if (node.key == key) {
                node.value = value;
                return;
            }
            node = node.next;
        }

        // 如果链表中不存在相同键的结点，则创建新结点并插入到链表头部
        Node newNode = new Node(key, value);
        newNode.next = table[index]; // 头插法，将新结点的 next 指向当前链表头结点
        table[index] = newNode; // 将新结点设置为链表头结点
    }

    int get(int key) {
        int index = key & (table.length - 1); // 计算哈希值并映射到数组索引

        Node node = table[index];
        while (node != null) {
            if (node.key == key) return node.value;
            node = node.next;
        }

        return -1;
    }
}

class MyConcurrentHashMap {
    // 哈希表的结点类，包含键、值和指向下一个结点的指针
    static class Node {
        int key;
        int value;
        Node next;

        Node(int k, int v) {
            key = k;
            value = v;
        }
    }

    // 哈希表的分段类，每个分段包含一个链表数组，长度为 16
    static class Segment {
        Node[] table = new Node[16];
    }

    Segment[] segments = new Segment[16]; // 哈希表的分段数组，长度为 16
    Object[] locks = new Object[16]; // 每个分段对应一个锁对象，用于同步访问

    // 构造函数，初始化分段和锁对象
    public MyConcurrentHashMap() {
        for (int i = 0; i < 16; i++) {
            segments[i] = new Segment();
            locks[i] = new Object();
        }
    }

    // 插入或更新键值对，如果键已存在则更新值，否则插入新结点
    // 先计算分段索引，然后在对应分段内进行操作，使用锁对象进行同步，确保线程安全
    void put(int key, int value) {

        int segmentIndex = key & (segments.length - 1); // 计算分段索引，使用位运算代替取模运算

        // 对于每个分段，使用对应的锁对象进行同步，确保线程安全
        synchronized (locks[segmentIndex]) {

            Segment seg = segments[segmentIndex]; // 获取对应分段

            int index = key & (seg.table.length - 1);

            Node node = seg.table[index];

            while (node != null) {
                if (node.key == key) {
                    node.value = value;
                    return;
                }
                node = node.next;
            }

            Node newNode = new Node(key, value);
            newNode.next = seg.table[index];
            seg.table[index] = newNode;
        }
    }

    int get(int key) {

        int segmentIndex = key & (segments.length - 1);

        Segment seg = segments[segmentIndex];

        int index = key & (seg.table.length - 1);

        Node node = seg.table[index];

        while (node != null) {
            if (node.key == key) return node.value;
            node = node.next;
        }

        return -1;
    }
}