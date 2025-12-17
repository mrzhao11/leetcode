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
}
