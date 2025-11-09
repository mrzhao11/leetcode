package array;
import java.util.*;
//螺旋矩阵
public class SpiralMatrixII {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] nums = generateMatrix(n);
        System.out.println(Arrays.deepToString(nums));

    }
    private static int[][] generateMatrix(int n){
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
}
