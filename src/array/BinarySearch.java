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
            } else if (nums[middle] < target) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return -1;
    }
    // 如果排序数组中存在可重复元素，则分别寻找最左边界和最右边界
    // 找最左边界时即便找到也要往左缩，同理找最右边界也是如此

    //给你一个非负整数 x ，计算并返回 x 的 算术平方根 。
    //由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。  不允许用pow or sqrt
    public int mySqrt(int x) {
        if (x == 0 || x == 1) return x;
        int min = 0;
        int max = x;
        while (min <= max) {
            int middle = min + (max - min) / 2;
            long sq = (long) middle * middle; // 防止溢出
            if (sq == x) {
                return middle;
            } else if (sq > x) {
                max = middle - 1;
            } else {
                min = middle + 1;
            }
        }
        return max;
    }

    // 在排序数组中查找元素的第一个和最后一个位置
    public int[] searchRange(int[] nums, int target) {
        int left = leftsearch(nums, target);
        int right = rightsearch(nums, target);
        // 情况 1：target 比数组所有元素都小 或 都大
        if (left == nums.length || right < 0) {
            return new int[] { -1, -1 };
        }

        // 情况 2：target 在数组范围内，但不存在
        if (nums[left] != target || nums[right] != target) {
            return new int[] { -1, -1 };
        }

        // 情况 3：target 存在
        return new int[] { left, right };

    }

    public int leftsearch(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        // 找左边界
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return right + 1;
    }

    public int rightsearch(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left - 1;
    }

    // 有序矩阵中的第K小元素
    // 给你一个 n x n 的矩阵 matrix ，其中每行和每列元素均按升序排序，请你找出并返回这个矩阵中第 k 小的元素。
    // 思想：二分法，先确定二分的范围，即矩阵中的最小值和最大值，然后在这个范围内进行二分查找。
    // 每次计算中间值 mid 后，统计矩阵中小于等于 mid 的元素个数，如果个数大于等于 k，则说明第 k 小的元素在左半部分，否则在右半部分。
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int left = matrix[0][0];
        int right = matrix[n - 1][n - 1];
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (check(matrix, mid, k, n)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    // 检查矩阵中小于等于mid的元素个数是否大于等于k
    public boolean check(int[][] matrix, int mid, int k, int n) {
        int count = 0;
        int j = n - 1; // 从每行的最后一个元素开始统计
        for (int i = 0; i < n; i++) {
            while (j >= 0 && matrix[i][j] > mid) {
                j--; // 如果当前元素大于mid，向左移动
            }
            count += (j + 1); // 统计当前行中小于等于mid的元素个数，j+1是因为j是索引，从0开始计数
        }
        return count >= k; // 如果个数大于等于k，说明第k小的元素在左半部分
    }
}



