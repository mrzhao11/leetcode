package backtrack;

import java.util.*;

public class Partitions {
    // 给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串，返回 s 所有可能的分割方案。
    static List<List<String>> res = new ArrayList<>();
    static List<String> path = new ArrayList<>();

    public static List<List<String>> partition(String s) {
        backtracking(s, 0);
        return res;
    }

    // startindex表示本次搜索的起始位置，当前这一刀切从startindex开始
    public static void backtracking(String s, int startindex) {
        if (startindex == s.length()) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = startindex; i < s.length(); i++) {
            // 截取子串,从startindex到i+1,左闭右开，即[startindex, i]
            String sub = s.substring(startindex, i + 1);
            if (!isPalindrome(sub)) {
                continue;
            }
            path.add(sub);
            backtracking(s, i + 1);
            path.remove(path.size() - 1);
        }
    }

    public static boolean isPalindrome(String s) {
        int i = 0;
        int j = s.length() - 1;
        while (i <= j) {
            if (s.charAt(i) != s.charAt(j))
                return false;
            i++;
            j--;
        }
        return true;
    }

    // 给定一个只包含数字的字符串 s ，复原它并返回所有可能的 IP 地址格式。
    static List<String> result = new ArrayList<>();
    public static List<String> restoreIpAddresses(String s) {
        StringBuilder sb = new StringBuilder(s);
        backTracking(sb, 0, 0);
        return result;
    }

    // pointnum表示已经放置了几个点
    private static void backTracking(StringBuilder s, int startIndex, int pointnum) {
        if (pointnum == 3) {
            // 为什么不直接添加呢？因为最后一个点后面还要验证一下是否合法
            if (isValid(s, startIndex, s.length() - 1)) {
                result.add(s.toString());
            }
            return;
        }
        for (int i = startIndex; i < s.length(); i++) {
            if (isValid(s, startIndex, i)) {
                s.insert(i + 1, '.'); // 在i+1位置放置点
                backTracking(s, i + 2, pointnum + 1); // 下一个起始位置是i+2
                s.deleteCharAt(i + 1); // 回溯，删除点
            } else {
                break;
            }
        }
    }

    public static boolean isValid(StringBuilder s, int start, int end) {
        if (start > end)
            return false;
        if (s.charAt(start) == '0' && start != end)
            return false;
        // 计算子串的数值
        long num = Long.parseLong(s.substring(start, end + 1));
        if (num > 255)
            return false;
        return true;
    }

    // 给出一个表示数字的字符串，将其分隔为5个片段，均不大于数字600，输出所有可能的划分组合.
    // 例如123622349237可以划分为1 236 22 349 237.
    public static List<List<String>> partition5(String s) {
        List<List<String>> res = new ArrayList<>();
        List<String> path = new ArrayList<>();
        backtracking5(s, 0, path, res);
        return res;
    }

    private static void backtracking5(String s, int startIndex, List<String> path, List<List<String>> res) {
        if (path.size() == 5) {
            if (startIndex == s.length()) {
                res.add(new ArrayList<>(path));
            }
            return;
        }
        for (int i = startIndex; i < s.length(); i++) {
            String sub = s.substring(startIndex, i + 1);
            if (isValid(sub)) {
                path.add(sub);
                backtracking5(s, i + 1, path, res);
                path.remove(path.size() - 1);
            } else {
                break;
            }
        }
    }

    // 判断字符串是否是一个合法的数字片段，不能以0开头且数值不大于600
    public static boolean isValid(String s) {
        if (s.charAt(0) == '0' && s.length() > 1) // 不能以0开头且长度大于1
            return false;
        long num = Long.parseLong(s);
        return num <= 600;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        List<List<String>> lists = partition(s);
        System.out.println(lists);

        String sb = "25525511135";
        List<String> res = restoreIpAddresses(sb);
        System.out.println(res);

        String s2 = "123622349237";
        List<List<String>> res2 = partition5(s2);
        System.out.println(res2);
    }
}
