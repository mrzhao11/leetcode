package StackandQueue;

import java.util.ArrayDeque;
import java.util.Deque;

public class DecodeString {

    // 给定一个经过编码的字符串，返回它解码后的字符串。
    // 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
    // 你可以认为输入字符串总是有效的；没有额外的空格，且输入字符串中所有的方括号都是成对出现的。
    // 输入: s = "3[a]2[bc]"   输出: "aaabcbc"
    public static String decodeString(String s) {
        Deque<Integer> countStack = new ArrayDeque<>(); // 记录重复次数的栈
        Deque<StringBuilder> strStack = new ArrayDeque<>(); // 记录字符串的栈

        StringBuilder cur = new StringBuilder(); // 当前处理的字符串
        int num = 0;

        for (char c : s.toCharArray()) {

            // 数字，有可能是多位数字
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            }

            // 遇到 '['，保存现场
            else if (c == '[') {
                countStack.push(num); // 记录重复次数
                strStack.push(cur); // 记录当前字符串
                num = 0; // 重置数字
                cur = new StringBuilder(); // 重置当前字符串
            }

            // 遇到 ']'，出栈并拼接
            else if (c == ']') {
                int repeat = countStack.pop();
                StringBuilder prev = strStack.pop(); // 取出之前的字符串

                for (int i = 0; i < repeat; i++) {
                    prev.append(cur);
                }
                cur = prev; // 更新当前字符串
            }

            // 普通字符
            else {
                cur.append(c);
            }
        }

        return cur.toString();
    }

    public static void main(String[] args) {
        String s = "3[a2[c]]";
        String result = decodeString(s);
        System.out.println(result); // 输出: accaccacc
      }
}
