package backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 排列问题
// 使用used数组控制树层的选择，确保在同一层树层上不会重复选择之前的元素，从而避免重复排列
// 如果数组中有重复元素，需要先排序，再进行剪枝，剪枝条件为 if (i > 0 && nums[i] == nums[i-1] && !used[i-1]) continue;

// 条件去重 if (i > startindex && nums[i] == nums[i-1]) continue;
// 适用于数组中有重复元素的情况，需要先排序，再进行剪枝，我们只关心“组合”，不在乎原数组中元素的相对顺序

// hashset去重 Set<Integer> used = new HashSet<>();
// 适用于数组中有重复元素的情况，只要本层使用过该元素，就跳过
// 用于不能打乱原数组顺序，又要去重的情况，比如非递减子序列问题

public class Permute {
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
        // 如果数组中有重复元素，不能使用used数组去重，因为同一个数在不同位置可能被多次使用（例如 [1,2,1] 中 1 在不同位置），需要使用HashSet去重
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
            // 纵向去重：每个元素只能使用一次
            if(used[i]) continue;
            // 横向去重：同一层树枝不能重复使用相同元素
            // 如果当前元素和前一个元素相同，并且前一个元素没有被使用过，说明前一个元素在同一层树枝上已经被使用过了，所以当前元素也不能使用
            if(i > 0 && nums[i] == nums[i-1] && !used[i-1]) continue;
            used[i] = true;
            path2.add(nums[i]);
            backtracking2_unique(nums);
            path2.remove(path2.size() - 1);
            used[i] = false;
        }
    }

    // 排列序列
    // 给出集合 [1,2,3,…,n]，其所有元素共有 n! 种排列。按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：
    // "123"
    // "132"
    // "213"
    // "231"
    // "312"
    // "321"
    // 给定 n 和 k，返回第 k 个排列。
    // 例如，输入 n = 3, k = 3，输出 "213"
    public String getPermutation(int n, int k) {
        int[] factorial = calculateFactorial(n);
        boolean[] used = new boolean[n + 1];
        StringBuilder path = new StringBuilder();
        dfs(0, n, k, path, used, factorial);
        return path.toString();
    }

    // index 表示当前确定了多少个数字
    // n 表示总共有多少个数字
    // k 表示还剩多少个排列需要跳过
    // path 表示当前已经确定的数字
    // used 表示哪些数字已经被使用过了
    // factorial 是阶乘数组
    private void dfs(int index, int n, int k, StringBuilder path, boolean[] used, int[] factorial) {
        if (index == n) {
            return;
        }

        // 计算还未确定的数字的全排列的个数，第 1 次进入的时候是 n - 1
        int count = factorial[n - 1 - index];
        for (int i = 1; i <= n; i++) {
            if (used[i]) {
                continue;
            }
            // 如果还未确定的数字的全排列的个数小于 k，说明第 k 个排列不在以 i 开头的子树上，需要跳过这些排列
            if (count < k) {
                k -= count;
                continue;
            }
            path.append(i);
            used[i] = true;
            dfs(index + 1, n, k, path, used, factorial);
            return;
        }
    }

    // 计算阶乘数组，factorial[i] 表示 i 的阶乘
    // 例如，factorial[0] = 1, factorial[1] = 1, factorial[2] = 2, factorial[3] = 6, ...
    // 主要作用是为了在 dfs 中快速计算还未确定的数字的全排列的个数，避免重复计算阶乘
    private int[] calculateFactorial(int n) {
        int[] factorial = new int[n + 1];
        factorial[0] = 1;
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }
        return factorial;
    }
}
