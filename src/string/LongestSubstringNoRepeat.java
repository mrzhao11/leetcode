package string;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LongestSubstringNoRepeat {
    // 无重复字符的最长子串
    // 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>(); // 存储字符及其最新出现的位置
        int res = 0;

        // 维护一个滑动窗口 [l, r]
        // 当右指针遇到重复字符时，窗口需要恢复到无重复，左指针跳到重复字符上一次出现位置的下一个位置
        // 每一步都更新最长长度
        for (int l = 0, r = 0; r < s.length(); r++) {
            char c = s.charAt(r);

            // 如果当前字符已经在窗口中出现过，左指针需要跳到上一次出现位置的下一个位置，保证窗口内没有重复字符
            if (map.containsKey(c)) {
                // 注意：左指针只能向右移动，不能回退，所以需要取最大值
                l = Math.max(l, map.get(c) + 1);
            }

            map.put(c, r);
            res = Math.max(res, r - l + 1);
        }

        return res;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int length = lengthOfLongestSubstring(s);
        System.out.println(length);
    }
}