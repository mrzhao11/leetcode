package graph;

import java.util.ArrayDeque;
import java.util.Queue;

public class Orange {
    // 腐烂的橙子
    // 在给定的网格中，每个单元格可以有以下三个值之一：
    // 0 代表空单元格；
    // 1 代表新鲜的橙子；
    // 2 代表腐烂的橙子。
    // 每分钟，任何与腐烂的橙子（在四个正方向上）相邻的新鲜橙子都会腐烂。
    // 返回直到单元格中没有新鲜橙子为止所必须经过的最小分钟数。如果不可能，则返回 -1。
    static int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

    public static int orangesRotting(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int freshOranges = 0;
        Queue<int[]> queue = new ArrayDeque<>();

        // 首先将所有腐烂的橙子加入队列，并统计新鲜橙子的数量
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) { // 腐烂的橙子
                    queue.offer(new int[] { i, j });
                } else if (grid[i][j] == 1) { // 新鲜的橙子
                    freshOranges++;
                }
            }
        }

        int min = 0;

        // BFS开始腐烂过程，类似于树的层序遍历
        while (!queue.isEmpty() && freshOranges > 0) {
            int size = queue.size();

            // 处理当前层的所有腐烂橙子
            for (int i = 0; i < size; i++) {
                int[] cur = queue.poll();
                int x = cur[0], y = cur[1];

                for (int[] dir : dirs) {
                    int nx = x + dir[0], ny = y + dir[1];
                    // 检查是否越界且是新鲜橙子
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] == 1) {
                        grid[nx][ny] = 2; // 腐烂这个橙子
                        freshOranges--;    // 减少新鲜橙子的数量
                        queue.offer(new int[] {nx, ny}); // 将腐烂橙子的相邻位置加入队列
                    }
                }
            }

            min++; // 经过一轮扩展，时间增加1分钟
        }

        return freshOranges == 0 ? min : -1;
    }

    public static void main(String[] args) {
        int[][] grid = {
            {2,1,1},
            {1,1,0},
            {0,1,1}
        };
        System.out.println(orangesRotting(grid)); // 输出 4
    }
}
