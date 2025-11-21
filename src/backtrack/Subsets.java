package backtrack;

import java.util.*;

public class Subsets {
    // 条件去重 if (i > startindex && nums[i] == nums[i-1]) continue;
    // 适用于数组中有重复元素的情况，需要先排序，再进行剪枝，我们只关心“组合”，不在乎原数组中元素的相对顺序

    // hashset去重 Set<Integer> used = new HashSet<>();
    // 适用于数组中有重复元素的情况，只要本层使用过该元素，就跳过
    // 用于不能打乱原数组顺序，又要去重的情况，比如非递减子序列问题

    // 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
    static List<List<Integer>> res = new ArrayList<>();
    static List<Integer> path = new ArrayList<>();
    public static List<List<Integer>> subsets(int[] nums) {
        // 注意：如果数组中有重复元素，需要先排序
        // Arrays.sort(nums);
        backtracking(nums,0);
        return res;
    }

    public static void backtracking(int[] nums,int startindex){
        res.add(new ArrayList<>(path));
        if (startindex >= nums.length) {
            return;
        }
        for (int i = startindex;i < nums.length;i++){
            // 如果数组里面包含重复元素，需要剪枝
            // if (i > startindex && nums[i] == nums[i-1]) continue;

            path.add(nums[i]);
            backtracking(nums,i+1);
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
        Set<Integer> used = new HashSet<>();
        for (int i = startindex; i < nums.length; i++) {
            if (used.contains(nums[i])) continue;
            if (path1.size() == 0 || nums[i] >= path1.get(path1.size() - 1)) {
                used.add(nums[i]);
                path1.add(nums[i]);
                backtracking1(nums, i + 1);
                path1.remove(path1.size() - 1);
            }

        }
    }

    // 给定一个 没有重复 数字的整数数组 nums ，返回其 所有可能的全排列 。
    static List<List<Integer>> res2 = new ArrayList<>();
    static List<Integer> path2 = new ArrayList<>();
    static boolean[] used;
    public static List<List<Integer>> permute(int[] nums) {
        used = new boolean[nums.length];
        backtracking2(nums);
        return res2;
    }
    public static void backtracking2(int[] nums){
        if(path2.size() == nums.length){
            res2.add(new ArrayList<>(path2));
            return;
        }
        // 如果数组中没有重复元素
        // Set<Integer> set = new HashSet<>();
        for (int i = 0;i < nums.length;i++){
            // 纵向去重：每个元素只能使用一次
            // 防止同一个数在一条递归路径上被多次使用（例如 [1,2,3] 中 1 被重复用）
            if(used[i]) continue;
            // 如果数组中有重复元素
            // 横向去重：同一层树枝不能重复使用相同元素
            // 防止同一层中（兄弟节点）出现重复值（例如 [1,1,2]）
            // if(set.contains(nums[i])) continue;
            // set.add(nums[i]);
            used[i] = true;
            path2.add(nums[i]);
            backtracking2(nums);
            path2.remove(path2.size() - 1);
            used[i] = false;
        }
    }
    // 如果含重复数字全排列不使用Set去重
    public List<List<Integer>> permuteUnique(int[] nums) {
        used = new boolean[nums.length];
        Arrays.sort(nums); // 先排序
        backtracking2(nums);
        return res2;
    }
    public void backtracking2_unique(int[] nums){
        if(path2.size() == nums.length){
            res2.add(new ArrayList<>(path2));
            return;
        }
        for (int i = 0;i < nums.length;i++){
            if(used[i]) continue;
            // 横向去重
            if(i > 0 && nums[i] == nums[i-1] && used[i-1]) continue;
            used[i] = true;
            path2.add(nums[i]);
            backtracking2_unique(nums);
            path2.remove(path2.size() - 1);
            used[i] = false;
        }
    }

}
