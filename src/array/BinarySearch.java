package array;

// 二分法
import java.util.*;

public class BinarySearch {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 输入数组长度
        int n = sc.nextInt();
        int[] nums = new int[n];
        // 输入数组
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        // 输入目标值
        int target = sc.nextInt();
        // 调用二分查找
        int result = search(nums, target);
        // 输出结果（索引或 -1）
        System.out.println(result);
    }

//    // 二分查找方法 闭区间[left, right] target一定在区间内包含两端
//    public static int search(int[] nums, int target) {
//        int left = 0;
//        int right = nums.length - 1;
//        while (left <= right) {
//            int middle = left + (right - left) / 2;
//            if (nums[middle] == target) {
//                return middle;
//            }else if(nums[middle] < target) {
//                left = middle + 1;
//            }else {
//                right = middle - 1;
//            }
//        }
//        return -1;
//    }
// 二分查找方法 闭区间[left, right） target一定在区间内不包含右端
public static int search(int[] nums, int target) {
    int left = 0;
    int right = nums.length;   //左闭右开
    while (left < right) {
        int middle = left + (right - left) / 2;
        if (nums[middle] == target) {
            return middle;
        }else if(nums[middle] < target) {
            left = middle + 1;
        }else {
            right = middle;
        }
    }
    return -1;
}
}

// 如果排序数组中存在可重复元素，则分别寻找最左边界和最右边界
// 找最左边界时即便找到也要往左缩，同理找最右边界也是如此


//给你一个非负整数 x ，计算并返回 x 的 算术平方根 。
//由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。  不允许用pow or sqrt
class Solution {
    public int mySqrt(int x) {
        if(x==0 || x==1) return x;
        int min=0;
        int max=x;
        while(min<=max){
            int middle = min+(max-min) / 2;
            long sq = (long) middle * middle; // 防止溢出
            if(sq==x){
                return middle;
            }else if(sq>x){
                max = middle -1;
            }else{
                min = middle +1;
            }
        }
        return max;
    }
}