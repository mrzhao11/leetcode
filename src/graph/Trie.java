package graph;

import java.util.HashMap;
import java.util.Map;


// 前缀树（Trie）实现
//Trie 树是一种树形结构，用于存储一个字符串集合。其特点是：
// 每个节点表示一个字符。
// 从根节点到某个节点的路径表示一个字符串。
// 前缀共享：相同的前缀只需存储一次，从而节省空间和加速检索。
//Trie 树的每个节点通常有以下属性：
// children：一个字典（或哈希表），存储当前节点的所有子节点。
// isEnd：一个布尔值，标记当前节点是否为某个单词的结尾。
public class Trie {

    // 每个节点是一个字典，存储字符到TrieNode的映射
    private class TrieNode {
        Map<Character, TrieNode> children; // 使用Map来表示当前节点的子节点，键是字符，值是对应的子节点
        // 例如：children.get('a') 返回表示字符 'a' 的子节点
        boolean isEnd; // 标记是否为单词的结尾

        // 构造方法
        public TrieNode() {
            children = new HashMap<>();
            isEnd = false;
        }
    }

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // 插入单词
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            // 如果当前字符没有对应的子节点，就创建一个新的节点
            if (!node.children.containsKey(c)) {
                node.children.put(c, new TrieNode());
            }
            node = node.children.get(c); // 移动到子节点
        }
        node.isEnd = true;  // 设置最后一个字符节点为单词结尾
    }

    // 查找单词是否存在
    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node = node.children.get(c);
            if (node == null) {
                return false;  // 没有找到对应的子节点，返回false
            }
        }
        return node.isEnd;  // 最后一个节点是否是单词的结尾
    }

    // 判断是否有单词以给定前缀开始
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            node = node.children.get(c);
            if (node == null) {
                return false;  // 没有找到对应的子节点，返回false
            }
        }
        return true;  // 遍历完成后，说明有单词以prefix为前缀
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println(trie.search("apple"));   // 返回 true
        System.out.println(trie.search("app"));     // 返回 false
        System.out.println(trie.startsWith("app")); // 返回 true
        trie.insert("app");
        System.out.println(trie.search("app"));     // 返回 true
    }
}
