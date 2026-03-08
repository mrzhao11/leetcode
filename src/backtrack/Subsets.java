package backtrack;

import java.util.*;

// 子集问题
// 通常使用startIndex控制树枝的选择，确保在同一层树枝上不会重复选择之前的元素，从而避免重复组合
// 如果含有重复元素的子集问题，需要先排序，再进行剪枝，剪枝条件为 if (i > startIndex && nums[i] == nums[i-1]) continue;
public class Subsets {

    // 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
    static List<List<Integer>> res = new ArrayList<>();
    static List<Integer> path = new ArrayList<>();

    public static List<List<Integer>> subsets(int[] nums) {
        // 注意：如果数组中有重复元素，需要先排序
        // Arrays.sort(nums);
        backtracking(nums, 0);
        return res;
    }

    public static void backtracking(int[] nums, int startindex) {
        res.add(new ArrayList<>(path));
//        if (startindex >= nums.length) { 多余的剪枝，因为for循环已经控制了边界
//            return;
//        }
        for (int i = startindex; i < nums.length; i++) {
            // 如果数组里面包含重复元素，需要剪枝
            // if (i > startindex && nums[i] == nums[i-1]) continue;

            path.add(nums[i]);
            backtracking(nums, i + 1);
            path.remove(path.size() - 1);
        }
    }

    // 给定一个整数数组 nums ，返回所有长度至少为 2 的 非递减 子序列 。
    // 你可以按 任意顺序 返回答案。
    static List<List<Integer>> res1 = new ArrayList<>();
    static List<Integer> path1 = new ArrayList<>();

    public List<List<Integer>> findSubsequences(int[] nums) {
        backtracking1(nums, 0);
        return res1;
    }

    public void backtracking1(int[] nums, int startindex) {
        if (path1.size() >= 2) {
            res1.add(new ArrayList<>(path1));
        }
        // 用于记录同一层使用过的元素，避免重复使用
        // 这里使用hashset去重，因为同一层树层上可能出现重复元素（例如 [4,6,7,7] 中 7 在同一层树层上出现两次），需要使用HashSet去重
        Set<Integer> used = new HashSet<>();
        for (int i = startindex; i < nums.length; i++) {
            // 这里使用Hashset主要是为了保证原数组的顺序不被打乱，因为非递减子序列问题要求子序列中的元素顺序与原数组一致，所以不能使用排序加剪枝的方式去重
            if (used.contains(nums[i])) continue;
            // 非递减子序列要求当前元素 nums[i] 大于等于 path1 中的最后一个元素，才能继续往下搜索
            if (path1.size() == 0 || nums[i] >= path1.get(path1.size() - 1)) {
                used.add(nums[i]);
                path1.add(nums[i]);
                backtracking1(nums, i + 1);
                path1.remove(path1.size() - 1);
            }
        }
    }
}
