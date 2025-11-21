package backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

    public static void backTracking(String digits, String[] numString, int num) {
        if (num == digits.length()) {
            res1.add(temp.toString());
            return;
        }
        // 获取当前数字对应的字符串,digits.charAt(num) - '0'是将字符转换为对应的整数，如'2'转换为2
        String str = numString[digits.charAt(num) - '0'];
        for (int i = 0; i < str.length(); i++) {
            temp.append(str.charAt(i));
            backTracking(digits, numString, num + 1);
            temp.deleteCharAt(temp.length() - 1);
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
