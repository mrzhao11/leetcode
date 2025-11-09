package array;
import java.util.*;
// 双指针
//给你一个按 非递减顺序 排序的整数数组 nums
// 返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。
public class SquaresSortedArray {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();//数组长度
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        int[] res = sortedArray(nums);

        for (int i = 0; i < n; i++) {
            System.out.print(res[i] + " ");
        }

    }
    private static int[] sortedArray(int[] nums) {
        // 首先找到正负数的分界点
        int r = 0;
        while(r < nums.length && nums[r] < 0){
            r++;
        }
        int l = r - 1;
        // 如果只有正数l=-1，只有负数

        int[] res = new int[nums.length];
        int i = 0;

        // 合并双指针 l >= 0排除了只有正数的情况，r < nums.length排除了只有负数的情况
        while (l >= 0 && r < nums.length) {
            if (nums[l] * nums[l] <= nums[r] * nums[r]) {
                res[i] = nums[l] * nums[l];
                i++;
                l--;
            } else {
                res[i] = nums[r] * nums[r];
                i++;
                r++;
            }
        }

        //处理剩余部分
        while (l >= 0) {
            res[i++] = nums[l] * nums[l];
            l--;
        }
        while (r < nums.length) {
            res[i++] = nums[r] * nums[r];
            r++;
        }

        return res;
    }
}
