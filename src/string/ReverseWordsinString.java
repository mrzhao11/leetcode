package string;

import java.util.*;

public class ReverseWordsinString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 读取整行字符串（可能包含空格）
        String s = sc.nextLine();

        Solution sol = new Solution();
        String result = sol.reverseWords(s);
        System.out.println(result);
    }
}

class Solution {
    public String reverseWords(String s) {
        // 1. 去掉多余空格
        StringBuilder sb = removeSpaces(s);
        // 2. 整体反转
        reverse(sb, 0, sb.length() - 1);
        // 3. 逐个单词反转
        reverseEachWord(sb);
        return sb.toString();
    }

    // 去除首尾空格，并压缩中间多余空格
    private StringBuilder removeSpaces(String s) {
        int start = 0, end = s.length() - 1;
        while (start <= end && s.charAt(start) == ' ') start++;
        while (end >= start && s.charAt(end) == ' ') end--;

        StringBuilder sb = new StringBuilder();
        while (start <= end) {
            char c = s.charAt(start);
            if (c != ' ' || (sb.length() > 0 && sb.charAt(sb.length() - 1) != ' ')) {
                sb.append(c);
            }
            start++;
        }
        return sb;
    }

    // 翻转区间 [l, r]
    private void reverse(StringBuilder sb, int l, int r) {
        while (l < r) {
            char temp = sb.charAt(l);
            sb.setCharAt(l, sb.charAt(r));
            sb.setCharAt(r, temp);
            l++;
            r--;
        }
    }

    // 翻转每个单词
    private void reverseEachWord(StringBuilder sb) {
        int n = sb.length();
        int start = 0, end = 0;
        while (start < n) {
            while (end < n && sb.charAt(end) != ' ') end++;
            reverse(sb, start, end - 1);
            start = end + 1;
            end = start;
        }
    }
}
