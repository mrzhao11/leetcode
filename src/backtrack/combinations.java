package backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// 一般startIndex是为了控制树枝的选择，更多的是解决顺序无关的组合问题，确保在同一层树枝上不会重复选择之前的元素，从而避免重复组合
// 而used数组是为了控制树层的选择，更多的是解决顺序有关的排列问题，确保在同一层树层上不会重复选择之前的元素，从而避免重复排列

// 二者核心区别在于：
// startIndex主要用于组合/子集问题，确保在同一层树枝上不会重复选择之前的元素，从而避免重复组合
// used数组主要用于排列问题，确保在同一层树层上不会重复选择之前的元素，从而避免重复排列

// startIndex 代表本层搜索从哪个位置开始，控制后面只能往右选，不能往回选
// backtracking(n,k,i)代表允许重复使用当前元素
// backtracking(n,k,i+1)代表不允许重复使用当前元素
public class combinations {
    // 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
    static List<List<Integer>> result= new ArrayList<>();
    static List<Integer> path = new ArrayList<>();
    public static List<List<Integer>> combine(int n, int k) {
        backtracking(n,k,1);
        return result;
    }

    public static void backtracking(int n,int k,int startIndex){
        if (path.size() == k){
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = startIndex;i <= n;i++){
            // 可以剪枝优化为 i <= n - (k - path.size()) + 1
            // path.size()已经选择了的个数，还需要选择k - path.size()个数，+1是因为i是闭区间
            path.add(i);
            backtracking(n,k,i+1);
            path.remove(path.size() - 1);
        }
    }

    // 找出所有相加之和为 n 的 k 个数的组合，且满足下列条件：
    // 只使用数字1到9
    // 每个数字 最多使用一次
    static int sum = 0;
    static List<List<Integer>> res = new ArrayList<>();
    static List<Integer> path1 = new ArrayList<>();
    public static List<List<Integer>> combinationSum3(int k, int n) {
        backtracking1(k,n,1);
        return res;
    }
    public static void backtracking1(int k,int n,int startindex) {
        if(sum > n) return;
        if(sum == n && path1.size() == k) {
            res.add(new ArrayList<>(path1));
            return;
        }
        for(int i = startindex; i<=9;i++){
            sum += i;
            path1.add(i);
            backtracking1(k,n,i+1);
            path1.remove(path1.size()-1);
            sum -= i;
        }
    }

    // 电话号码的字母组合
    static List<String> res1 = new ArrayList<>();
    public static List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return res1;
        }
        String[] numString = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        backTracking(digits, numString, 0);
        return res1;
    }

    static StringBuilder temp = new StringBuilder();

    // num代表当前处理的是第几个数字
    public static void backTracking(String digits, String[] numString, int num) {
        if (num == digits.length()) {
            res1.add(temp.toString());
            return;
        }

        int index = digits.charAt(num) - '0'; // 获取当前数字对应的索引
        String str = numString[index]; // 获取当前数字对应的字符串
        // for循环枚举当前数字对应的字符串中的每个字符
        for (int i = 0; i < str.length(); i++) {
            temp.append(str.charAt(i));
            backTracking(digits, numString, num + 1);
            temp.deleteCharAt(temp.length() - 1);
        }
    }


    // 给定 n 对括号，编写一个函数来生成所有可能的并且 有效的 括号组合。
    // 1. 左括号数量小于 n 时，可以放置左括号
    // 2. 右括号数量小于左括号数量时，可以放置右括号
    // 终止条件：当左右括号数量均为 n 时，得到一个合法的括号组合
    List<String> res3 = new ArrayList<>();
    public List<String> generateParenthesis(int n) {
        backtracking(n, 0, 0, new StringBuilder());
        return res3;
    }


    // left 表示已经放置的左括号数量，right 表示已经放置的右括号数量
    private void backtracking(int n, int left, int right, StringBuilder path3) {
        // 终止条件：长度达到 2 * n
        if (path3.length() == 2 * n) {
            res3.add(path3.toString());
            return;
        }

        // 选择左括号：只要还没用完
        if (left < n) {
            path3.append('(');
            backtracking(n, left + 1, right, path3);
            path3.deleteCharAt(path3.length() - 1); // 回溯
        }

        // 选择右括号：右括号数量不能超过左括号
        if (right < left) {
            path3.append(')');
            backtracking(n, left, right + 1, path3);
            path3.deleteCharAt(path3.length() - 1); // 回溯
        }
    }

//    // 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
//    // candidates 中的数字可以无限制重复被选取。
//    List<List<Integer>> res = new ArrayList<>();
//    List<Integer> path = new ArrayList<>();
//    public List<List<Integer>> combinationSum(int[] candidates, int target) {
//        int sum = 0;
//        backtracking(target,sum,candidates,0);
//        return res;
//    }
//    public void backtracking(int target, int sum,int[] nums,int startindex){
//        if(sum == target) {
//            res.add(new ArrayList<>(path));
//            return;
//        }
//        if(sum > target) return;
//        for(int i =startindex;i<nums.length ;i++) {
//            sum += nums[i];
//            path.add(nums[i]);
//            backtracking(target,sum,nums,i);
//            path.remove(path.size()-1);
//            sum -= nums[i];
//        }
//    }


//    // 给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
//    // candidates 中的每个数字在每个组合中只能使用一次。
//    // 注意：解集不能包含重复的组合。
//    List<List<Integer>> res = new ArrayList<>();
//    List<Integer> path = new ArrayList<>();
//
//    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
//        Arrays.sort(candidates);
//        backtracking(target, 0, candidates, 0);
//        return res;
//    }
//
//    public void backtracking(int target, int sum, int[] nums, int startindex) {
//        if (sum == target) {
//            res.add(new ArrayList<>(path));
//            return;
//        }
//        if (sum > target)
//            return;
//        for (int i = startindex; i < nums.length; i++) {
//            if (i > startindex && nums[i] == nums[i - 1]) { // 同一层树枝剪枝
//                continue;
//            }
//            sum += nums[i];
//            path.add(nums[i]);
//            backtracking(target, sum, nums, i + 1);
//            path.remove(path.size() - 1);
//            sum -= nums[i];
//        }
//    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        List<List<Integer>> combinations = combine(n, k);
        System.out.println("所有可能的组合为：");
        for (List<Integer> combination : combinations) {
            System.out.println(combination);
        }

        List<List<Integer>> combinationSum3 = combinationSum3(k, n);
        System.out.println("所有相加之和为 " + n + " 的 " + k + " 个数的组合为：");
        for (List<Integer> combination : combinationSum3) {
            System.out.println(combination);
        }

        List<String> letterCombinations = letterCombinations("23");
        System.out.println("电话号码 " + n + " 的字母组合为：");
        for (String combination : letterCombinations) {
            System.out.println(combination);
        }
    }
}
