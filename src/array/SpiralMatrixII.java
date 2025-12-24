package array;
import java.util.*;
//螺旋矩阵
public class SpiralMatrixII {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] nums = generateMatrix(n);

        int m = sc.nextInt();
        int k = sc.nextInt();
        int[][] matrix = new int[m][k];
        for(int i=0;i<m;i++){
            for(int j=0;j<k;j++){
                matrix[i][j] = sc.nextInt();
            }
        }
        List<Integer> res = spiralOrder(matrix);

        System.out.println(res);
        System.out.println(Arrays.deepToString(nums));
        rotate(nums);
        for(int[] row : nums){
            for(int val : row){
                System.out.print(val + " ");
            }
            System.out.println();
        }

    }

    // 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
    public static List<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length; //行数
        int n = matrix[0].length; //列数
        List<Integer> res = new ArrayList<>();

        int top = 0, bottom = m - 1;
        int left = 0, right = n - 1;

        // 循环遍历矩阵的边界，逐层向内收缩
        while (top <= bottom && left <= right) {
            for (int i = left; i <= right; i++) {
                res.add(matrix[top][i]);
            }
            top++;

            for (int i = top; i <= bottom; i++) {
                res.add(matrix[i][right]);
            }
            right--;

            // 防止重复遍历
            if (top <= bottom) {
                for (int i = right; i >= left; i--) {
                    res.add(matrix[bottom][i]);
                }
                bottom--;
            }
            // 防止重复遍历
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    res.add(matrix[i][left]);
                }
                left++;
            }
        }
        return res;
    }

    // 给你一个正整数 n ，生成一个包含 1 到 n² 所有元素，且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。
    public static int[][] generateMatrix(int n){
        int[][] res = new int[n][n];
        int top = 0;
        int bottom = n;
        int left = 0;
        int right = n;
        int count = 1;
        while( left < right && top < bottom){
            // 左闭右开
            // 左到右
            for(int i=left;i<right;i++){
                res[top][i] = count;
                count++;
            }
            top++;
            // 上到下
            for(int i=top;i<bottom;i++){
                res[i][right-1] = count;
                count++;
            }
            right--;
            // 右到左
            for(int i=right-1;i>=left;i--){
                res[bottom-1][i] = count;
                count++;
            }
            bottom--;
            // 下到上
            for(int i=bottom-1;i>=top;i--){
                res[i][left] = count;
                count++;
            }
            left++;
        }
        return res;
    }

    // 给定一个 n x n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
    // 你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。
    // 矩阵先沿主对角线翻转，再水平翻转
    public static void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
        for (int i = 0; i < n; i++) {
            reverse(matrix[i], 0, n - 1);
        }
    }
    private static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int tmp = nums[start];
            nums[start] = nums[end];
            nums[end] = tmp;
            start++;
            end--;
        }
    }

    // 编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有以下特性：
    // 每行的元素从左到右升序排列; 每列的元素从上到下升序排列。
    public static boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length; // 行数
        int n = matrix[0].length; // 列数

        int row = 0;
        int col = n - 1; // 从右上角开始
        // 类似二叉搜索树的搜索过程，右上角元素作为根节点，往左走比它小，往下走比它大
        while (row < m && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                col--;      // 排除一整列
            } else {
                row++;      // 排除一整行
            }
        }
        return false;
    }
}
