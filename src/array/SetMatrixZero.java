package array;

public class SetMatrixZero {
    // 给定一个 m x n 的矩阵。如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。请使用 原地 算法。
    // 方法一：使用标记数组，时间复杂度O(mn)，空间复杂度O(m+n)
    public static void setZeroes(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean[] row = new boolean[m]; // 标记行内是否有0
        boolean[] col = new boolean[n]; // 标记列内是否有0
        // 第一次遍历，标记行和列
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = col[j] = true;
                }
            }
        }
        // 第二次遍历，根据标记数组将对应行列置0
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (row[i] || col[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    // 方法二：使用矩阵的第一行和第一列作为标记数组，时间复杂度O(mn)，空间复杂度O(1)
    public static void setZeroesOptimized(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean flagCol0 = false, flagRow0 = false;
        // 标记第一列和第一行是否有0
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                flagCol0 = true;
            }
        }
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {
                flagRow0 = true;
            }
        }
        // 使用第一行和第一列作为标记数组
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // 如果当前元素为0，则将对应的第一行和第一列元素置0
                if (matrix[i][j] == 0) {
                    matrix[i][0] = matrix[0][j] = 0;
                }
            }
        }
        // 根据第一行和第一列的标记将对应行列置0
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // 如果对应的第一行或第一列元素为0，则将当前元素置0
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        // 最后根据标记将第一行和第一列置0
        // 如果标记为true，说明第一列或第一行有0，需要将其置0
        if (flagCol0) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
        if (flagRow0) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1}
        };
        setZeroesOptimized(matrix);
        // 输出结果
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
