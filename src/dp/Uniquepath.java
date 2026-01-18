package dp;

public class Uniquepath {
    // 不同路径
    // 一个机器人位于一个m x n网格的左上角，机器人每次只能向下或向右移动一步，机器人试图达到网格的右下角
    // 计算机器人从左上角到右下角共有多少条不同的路径
    public static int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++)
            dp[i][0] = 1; // 第一列只有一种路径
        for (int j = 0; j < n; j++)
            dp[0][j] = 1; // 第一行只有一种路径
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    // 不同路径 II
    // 一个机器人位于一个m x n网格的左上角，机器人每次只能向下或向右移动一步
    // 网格中有一些障碍物，障碍物用1表示，空位用0表示
    // 计算机器人从左上角到右下角共有多少条不同的路径
    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        if(obstacleGrid[0][0] == 1 || obstacleGrid[m-1][n-1] == 1) return 0;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++){
            if(obstacleGrid[i][0] != 1){
                dp[i][0] = 1;
            }else break; // 遇到障碍物后，后面的格子都不可达
        }
        for (int j = 0; j < n; j++){
            if(obstacleGrid[0][j] != 1){
                dp[0][j] = 1;
            }else break; // 遇到障碍物后，后面的格子都不可达
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) { // 如果当前格子是障碍物
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[m-1][n-1];
    }

    // 最小路径和
    // 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，
    // 使得路径上的数字总和为最小。 说明：每次只能向下或者向右移动一步。
    public static int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        // 初始化第一列
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        // 初始化第一行
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }

    public static void main(String[] args) {
        int m = 3;
        int n = 7;
        System.out.println(uniquePaths(m, n));

        int[][] obstacleGrid = {
            {0,0,0},
            {0,1,0},
            {0,0,0}
        };
        System.out.println(uniquePathsWithObstacles(obstacleGrid));

        int[][] grid = {
            {1,3,1},
            {1,5,1},
            {4,2,1}
        };
        System.out.println(minPathSum(grid)); // 输出7


    }
}
