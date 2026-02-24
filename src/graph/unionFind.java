package graph;

import java.util.*;

public class unionFind {
    // ===== 并查集 =====
    // 并查集（Union-Find）是一种数据结构，用于处理一些不交集的合并及查询问题。
    // 主要思想是将元素分成若干个不交的集合，每个集合有一个代表（根节点），通过路径压缩和按秩合并来优化性能。
    // 主要操作：
    // 1. find(x)：找到元素 x 所在的集合的代表（根节点）。
    // 2. union(a, b)：将元素 a 和 b 所在的集合合并成一个集合。
    // 3. add(x)：添加一个新的元素 x，初始时它自己是一个独立的集合。
    static class UnionFind {
        Map<Integer, Integer> parent = new HashMap<>(); // 节点 -> 父节点
        Map<Integer, Integer> rank = new HashMap<>(); // 节点 -> 秩（树的高度）

        // 初始化节点，每个节点自己是一个集合
        void add(int x) {
            if (!parent.containsKey(x)) {
                parent.put(x, x); // 父节点指向自己
                rank.put(x, 1); // 初始秩为 1
            }
        }

        // 查找根（路径压缩），将路径上的节点直接连接到根节点上，优化后续查询效率
        int find(int x) {
            if (parent.get(x) != x) { // 如果 x 不是根节点
                parent.put(x, find(parent.get(x))); // 递归查找根节点，并路径压缩
            }
            return parent.get(x); // 返回根节点
        }

        // 合并（按秩合并），将两个集合合并，较小的树挂在较大的树下面，优化树的高度
        void union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);
            if (rootA == rootB) return;

            int rankA = rank.get(rootA); // 获取 rootA 的秩
            int rankB = rank.get(rootB); // 获取 rootB 的秩

            if (rankA < rankB) {
                parent.put(rootA, rootB); // 将 rootA 挂在 rootB 下面
            } else if (rankA > rankB) {
                parent.put(rootB, rootA); // 将 rootB 挂在 rootA 下面
            } else {
                parent.put(rootB, rootA); // 将 rootB 挂在 rootA 下面
                rank.put(rootA, rankA + 1); // 如果两棵树秩相同，合并后 rootA 的秩增加 1
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UnionFind uf = new UnionFind();

        List<Integer> nums = new ArrayList<>();

        // 读取所有输入整数
        while (sc.hasNextInt()) {
            nums.add(sc.nextInt());
        }

        // 每两个数是一对关系
        for (int i = 0; i + 1 < nums.size(); i += 2) {
            int a = nums.get(i); // 获取第一个数
            int b = nums.get(i + 1); // 获取第二个数
            uf.add(a); // 添加 a 到并查集
            uf.add(b); // 添加 b 到并查集
            uf.union(a, b); // 合并 a 和 b 所在的集合
        }

        // 按 root 分组
        Map<Integer, List<Integer>> groups = new HashMap<>(); // root -> 组内元素列表
        for (int x : uf.parent.keySet()) {
            int root = uf.find(x); // 找到 x 的根节点
            if (!groups.containsKey(root)) {
                groups.put(root, new ArrayList<>()); // 如果 root 不存在，创建一个新的列表
            }
            groups.get(root).add(x); // 将 x 添加到对应 root 的列表中
        }

        // 组内排序
        List<List<Integer>> res = new ArrayList<>();
        for (List<Integer> g : groups.values()) {
            Collections.sort(g);
            res.add(g);
        }

        // 按每组最小值排序
        res.sort(Comparator.comparingInt(a -> a.get(0)));

        // 输出
        for (List<Integer> g : res) {
            for (int i = 0; i < g.size(); i++) {
                if (i > 0) System.out.print(" ");
                System.out.print(g.get(i));
            }
            System.out.println();
        }
    }
}
