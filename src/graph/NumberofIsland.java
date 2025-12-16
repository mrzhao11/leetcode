package graph;

import java.util.ArrayDeque;
import java.util.Queue;
public class NumberofIsland {
    // 岛屿数量
    // 给你一个由 '1'（陆地）和 '0'（水）组成的二维网格，请你计算网格中岛屿的数量。
    // 岛屿总是被水包围，并且每座岛屿只能由水平方向和竖直方向上相邻的陆地连接形成。
    // 你可以假设网格的四个边均被水包围。

    // DFS和BFS
    // DFS思路：
    // 遍历整个 grid
    // 遇到一个 1，说明发现了一个新岛屿
    // 立刻用 DFS 把这个岛屿的所有 1“淹没” 为 0
    // 每次 DFS 调用，岛屿数量 +1
    static int m,n; // 行列数
    static int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}}; // 右,左,下,上四个方向

    public static int numIslandsdfs(char[][] grid) {
        m = grid.length;
        n = grid[0].length;
        int count = 0; // 岛屿数量

        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if(grid[i][j] == '1'){ // 发现新岛屿
                    dfs(grid, i, j); // 淹没岛屿
                    count++;
                }
            }
        }
        return count;
    }
    private static void dfs(char[][] grid, int x, int y){
        // 终止条件，越界或遇到水则返回
        if(x<0 || x>=m || y<0 || y>=n || grid[x][y]=='0'){
            return;
        }
        grid[x][y] = '0'; // 标记为已访问
        // 向四个方向扩展
        for(int[] dir : dirs){
            int newX = x + dir[0];
            int newY = y + dir[1];
            dfs(grid, newX, newY);
        }
    }

    // BFS思路：
    // 遍历整个 grid
    // 遇到一个 1，说明发现了一个新岛屿
    // 立刻用 BFS 把这个岛屿的所有 1“淹没” 为 0
    // 每次 BFS 调用，岛屿数量 +1
    public static int numIslandbfs(char[][] grid) {
        m = grid.length;
        n = grid[0].length;
        int count = 0; // 岛屿数量
        Queue<int[]> queue = new ArrayDeque<>(); // BFS队列

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    grid[i][j] = '0'; // 标记为已访问
                    queue.offer(new int[]{i, j}); // 入队

                    while (!queue.isEmpty()) {
                        int[] cur = queue.poll(); // 出队
                        // 向四个方向扩展
                        for (int[] d : dirs) {
                            int nx = cur[0] + d[0];
                            int ny = cur[1] + d[1];
                            // 判断新坐标是否合法且为陆地
                            if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] == '1') {
                                grid[nx][ny] = '0';
                                queue.offer(new int[]{nx, ny});
                            }
                        }
                    }
                }
            }
        }
        return count;
    }
    // BFS和DFS区别只是在于遍历方式不同，BFS使用队列，DFS使用递归或栈

    // 并查集思路：
    // 把每个 1 看作一个节点
    // 遍历 grid，把相邻的 1 进行合并
    // 最后统计有多少个不同的根节点，即为岛屿数量
    static int[] parent; // 并查集父节点数组
    static int count; // 岛屿数量

    public static int numIslandsbingcha(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        parent = new int[m * n]; // 初始化并查集数组
        count = 0;

        // 初始化
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    int id = i * n + j; // 一维表示二维坐标，保证唯一性
                    // 先假设每个 1 自成一个岛屿
                    parent[id] = id; // 初始化父节点为自己
                    // parent[x] = y 表示 x 的父节点是 y
                    count++;
                }
            }
        }

        int[][] dirs = {{1,0},{0,1}}; // 只需向下和向右检查，避免重复合并
        // 合并阶段
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    int id1 = i * n + j;
                    // 向下和向右检查相邻节点
                    for (int[] d : dirs) {
                        int ni = i + d[0], nj = j + d[1];
                        // 判断新坐标是否合法且为陆地，如果是则合并
                        if (ni < m && nj < n && grid[ni][nj] == '1') {
                            int id2 = ni * n + nj;
                            union(id1, id2);
                        }
                    }
                }
            }
        }
        return count;
    }

    // 并查集查找和合并操作
    // 查找根节点
    static int find(int x) {
        // 路径压缩，找到根节点的同时压缩路径
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    // 合并两个节点所属的集合
    static void union(int x, int y) {
        int px = find(x); // 找到x的根节点
        int py = find(y); // 找到y的根节点
        // 合并不同的集合
        if (px != py) {
            parent[px] = py;
            count--;
        }
    }

    public static void main(String[] args) {
        char[][] grid = {
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
        };
        System.out.println("DFS岛屿数量: " + numIslandsdfs(grid));

        char[][] grid2 = {
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
        };
        System.out.println("BFS岛屿数量: " + numIslandbfs(grid2));

        char[][] grid3 = {
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
        };
        System.out.println("并查集岛屿数量: " + numIslandsbingcha(grid3));
    }
}
