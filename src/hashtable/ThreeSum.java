package hashtable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ThreeSum {

    // 三数之和
    // 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
    // 注意：答案中不可以包含重复的三元组。
    public static List<List<Integer>> threesum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        // 遍历从 0 到 n-3 的每个元素，作为三元组中的第一个元素
        for (int i = 0; i < nums.length - 2; i++) {
            // 跳过重复的nums[i]
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    ans.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // 跳过重复的 left
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    // 跳过重复的 right
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    // 同时移动 left 和 right，继续寻找下一组
                    right--;
                    left++;
                } else if (sum > 0) {
                    right--;
                } else {
                    left++;
                }
            }
        }
        return ans;
    }

    // 三数之和最接近
    // 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。
    // 返回这三个数的和。假定每组输入只存在唯一答案。
    public static int threesumcloset(int[] nums, int target) {
        Arrays.sort(nums); // 排序
        int ans = nums[0] + nums[1] + nums[2]; // 初始化为前三个数的和
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue; // 跳过重复的 nums[i]

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == target) return sum; // 直接返回，因为已经是最接近的了

                // 如果当前的 sum 更接近 target，就更新 ans
                if (Math.abs(sum - target) < Math.abs(ans - target)) {
                    ans = sum;
                }
                // 根据 sum 与 target 的关系移动指针
                if (sum > target) {
                    right--;
                    while (left < right && nums[right] == nums[right + 1]) right--; // 跳过重复的 right
                } else {
                    left++;
                    while (left < right && nums[left] == nums[left - 1]) left++; // 跳过重复的 left
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        int target = sc.nextInt();
        List<List<Integer>> res = threesum(nums);
        System.out.println(res);
        System.out.println(threesumcloset(nums, target));
    }
}

// 四数之和 思路类似

//class Solution {
//    public List<List<Integer>> fourSum(int[] nums, int target) {
//        List<List<Integer>> ans = new ArrayList<>();
//        Arrays.sort(nums);  // 排序
//
//        int n = nums.length;
//        for (int i = 0; i < n - 3; i++) {
//            if (i > 0 && nums[i] == nums[i - 1]) continue; // 去重 i
//            for (int j = i + 1; j < n - 2; j++) {
//                if (j > i + 1 && nums[j] == nums[j - 1]) continue; // 去重 j
//
//                int left = j + 1, right = n - 1;
//                while (left < right) {
//                    long sum = (long)nums[i] + nums[j] + nums[left] + nums[right]; // 防止溢出
//                    if (sum == target) {
//                        ans.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
//
//                        // 跳过重复 left
//                        while (left < right && nums[left] == nums[left + 1]) left++;
//                        // 跳过重复 right
//                        while (left < right && nums[right] == nums[right - 1]) right--;
//
//                        left++;
//                        right--;
//                    } else if (sum < target) {
//                        left++;
//                    } else {
//                        right--;
//                    }
//                }
//            }
//        }
//        return ans;
//    }
//}
