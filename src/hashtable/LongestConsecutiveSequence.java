package hashtable;

import java.util.*;
public class LongestConsecutiveSequence {
    // 最长连续序列
    // 给定一个未排序的整数数组 nums ，找出数字连续的最长序列的长度。
    // 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
    public int longestConsecutive(int[] nums) {

        if(nums.length == 0) return 0;
        // HashSet 去重并存储元素
        Set<Integer> set = new HashSet<>();
        for(int num : nums){
            set.add(num);
        }
        int maxLen = 0; // 记录最长长度

        for (int x : set) {
            // 只从序列的起点开始计数
            if (!set.contains(x - 1)) {
                int cur = x; // 当前数字
                int len = 1; // 当前长度
                // 向后扩展序列
                while (set.contains(cur + 1)) {
                    cur++;
                    len++;
                }

                maxLen = Math.max(maxLen, len);
            }
        }

        return maxLen;
    }


    // 字符串的排列
    // 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
    // 换句话说，s1 的排列之一是 s2 的 子串。
    // 思想 是滑动窗口，维护一个长度为 s1.length() 的窗口，在 s2 上滑动，检查窗口内的字符是否与 s1 的字符匹配。
    public boolean checkInclusion(String s1, String s2) {
        int n = s1.length(), m = s2.length();
        if (n > m) {
            return false;
        }
        int[] cnt = new int[26]; // 统计 s1 中每个字符的出现次数
        for (int i = 0; i < n; ++i) {
            int x = s1.charAt(i) - 'a';
            --cnt[x]; // 先把 s1 中的字符计数减去，后面在滑动窗口中遇到相同字符时再加回来
        }

        int left = 0;
        for (int right = 0; right < m; ++right) {
            int x = s2.charAt(right) - 'a'; // 当前窗口右边界字符
            ++cnt[x]; // 加回当前字符的计数
            // 如果当前字符的计数大于 0，说明窗口内该字符出现的次数超过了 s1 中的次数，需要收缩窗口
            while (cnt[x] > 0) {
                --cnt[s2.charAt(left) - 'a']; // 收缩窗口左边界，减去左边界字符的计数
                ++left;
            }
            // 如果窗口大小等于 s1 的长度，说明找到了一个排列
            if (right - left + 1 == n) {
                return true;
            }
        }
        return false;
    }

}
