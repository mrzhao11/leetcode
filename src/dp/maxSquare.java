package dp;

import static MonotonicStack.rain.largestRectangleArea;

public class maxSquare {
    // 221. 最大正方形
    // 给定一个由 '0' 和 '1' 组成的二维矩阵，找出只包含 '1' 的最大正方形，并返回其面积。
    public int maximalSquare(char[][] matrix) {
        int maxSide = 0;
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return maxSide;
        }
        int rows = matrix.length, columns = matrix[0].length;

        // 以i j为右下角的最大正方形边长
        int[][] dp = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // 如果当前元素为 '1'
                if (matrix[i][j] == '1') {
                    // 如果在第一行或第一列，最大边长只能是1
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                        // 否则，取左、上、左上三个位置的最小值加1
                    } else {
                        // 以当前元素为右下角的最大正方形边长取决于左、上、左上三个位置的最小值加1
                        // 木桶原理：正方形的边长受限于最短的边
                        dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    }
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }
        int maxSquare = maxSide * maxSide;
        return maxSquare;
    }

    // 85. 最大矩形
    // 给定一个仅包含 '0' 和 '1' 的二维二进制矩阵，找出只包含 '1' 的最大矩形，并返回其面积。
    // 思路：将每一行看作一个直方图，计算每一行的直方图高度，然后使用单调栈算法计算每一行的最大矩形面积，最终返回最大的面积。
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) return 0;
        int cols = matrix[0].length;
        int[] heights = new int[cols];
        int maxArea = 0;

        for (int i = 0; i < matrix.length; i++) {
            // 1. 更新当前行的直方图高度
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    heights[j] += 1;
                } else {
                    heights[j] = 0;
                }
            }
            // 2. 计算当前行对应的直方图的最大矩形面积，逐行更新最大面积
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }
        return maxArea;
    }

}
