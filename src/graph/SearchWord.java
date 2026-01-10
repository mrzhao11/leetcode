package graph;

public class SearchWord {
    // 79. 单词搜索
    // 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
    // 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
    static int m, n;
    static int[][] dirs = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

    public static boolean exist(char[][] board, String word) {
        m = board.length;
        n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, i, j, word, 0, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    // 从 (x, y) 出发，搜索 word 中 idx 位置的字符是否存在
    private static boolean dfs(char[][] board, int x, int y, String word, int idx, boolean[][] visited) {
        // 终止条件, 越界、已访问或字符不匹配
        if (x < 0 || x >= m || y < 0 || y >= n
                || visited[x][y]
                || board[x][y] != word.charAt(idx)) {
            return false;
        }
        // 找到最后一个字符
        if (idx == word.length() - 1)
            return true;
        visited[x][y] = true;
        // 向四个方向扩展
        for (int[] dir : dirs) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (dfs(board, newX, newY, word, idx + 1, visited)) {
                return true;
            }
        }
        visited[x][y] = false;
        return false;
    }

    public static void main(String[] args) {
        char[][] board = {
                {'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}
        };
        String word = "ABCCED";
        System.out.println(exist(board, word)); // true
    }
}
