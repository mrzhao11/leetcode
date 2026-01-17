package dp;

import java.util.ArrayDeque;
import java.util.Deque;

public class LongestValidParentheses {

    // 给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
    public static int longestValidParenthesesDp(String s) {
        int n = s.length();
        int[] dp = new int[n];// 以i结尾的最长有效括号子串的长度
        if (n == 0) return 0;
        dp[0] = 0;

        // 只有当s.charAt(i) == ')'时，才可能形成有效括号子串
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == ')') {
                // 情况1：s.charAt(i-1) == '('，形成"()"
                if (s.charAt(i - 1) == '(') {
                    if (i - 2 >= 0) {
                        dp[i] = dp[i - 2] + 2;
                    } else {
                        dp[i] = 2;
                    }
                } else { // 情况2：s.charAt(i-1) == ')'
                    // 找到与s.charAt(i)匹配的'('的位置
                    // i - dp[i-1]是上一个有效括号子串的起始位置，减1就是与当前')'匹配的'('的位置
                    int j = i - dp[i - 1] - 1;
                    // 如果找到了匹配的'('
                    if (j >= 0 && s.charAt(j) == '(') {
                        dp[i] = dp[i - 1] + 2;
                        if (j - 1 >= 0) { // 加上j前面的有效括号子串长度
                            dp[i] += dp[j - 1];
                        }
                    }
                }
            }
        }
        int res = 0;
        for (int x : dp) {
            res = Math.max(res, x);
        }
        return res;
    }

    public static int longestValidParenthesesStack(String s) {
        // 栈里存的下标，只有两种合法身份：
        // 1️⃣ “还没被匹配的左括号 ( 的下标”
        // 2️⃣ “最近一个无法被匹配的右括号 ) 的下标（断点）”
        // 3️⃣ 哨兵：-1，方便计算长度
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1); // 哨兵
        int res = 0;

        // 两种情况会入栈：
        // 1️⃣ 遇到 '('，将下标入栈
        // 2️⃣ pop后栈空了，说明当前')'没有匹配的'('，将当前位置入栈作为新的“还没被匹配的位置”
        // 这意味着，任何时候栈最多只有一个 ) 的下标，且一定在栈底（除了哨兵-1）
        // 也就是如果pop后栈不空，栈顶元素一定是某个还没被匹配的 '(' 的下标

        // 遇到 '('，将下标入栈，代表还没被匹配的位置
        // 遇到 ')'，弹出栈顶元素，计算当前有效长度
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else { // ')'
                stack.pop();

                // 栈空了，说明当前')'没有匹配的'('，将当前位置入栈作为新的“还没被匹配的位置”
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {// 栈不空，计算当前有效长度
                    // 当前有效长度 = 当前下标 - 栈顶下标
                    res = Math.max(res, i - stack.peek());
                }
            }
        }
        return res;
    }

    // 双指针
    // 时间复杂度O(n)，空间复杂度O(1)
    // 从左到右扫描字符串，记录左括号和右括号的数量
    // 当左右括号数量相等时，更新最长有效长度
    // 如果右括号数量大于左括号数量，说明当前子串无效，重置左右括号数量
    // 但是这种方式无法处理左括号多于右括号的情况
    // 因此需要再从右到左扫描一次字符串，处理左括号多于右括号的情况
    public static int longestValidParentheses(String s) {
        int left = 0, right = 0, res = 0;

        // 从左到右
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') left++;
            else right++;

            if (left == right) {
                res = Math.max(res, 2 * right);
            } else if (right > left) {
                left = right = 0;
            }
        }

        // 从右到左
        left = right = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') left++;
            else right++;

            if (left == right) {
                res = Math.max(res, 2 * left);
            } else if (left > right) {
                left = right = 0;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        String s = "(()())";
        System.out.println(longestValidParenthesesDp(s));
        System.out.println(longestValidParenthesesStack(s));
        System.out.println(longestValidParentheses(s));
    }
}
