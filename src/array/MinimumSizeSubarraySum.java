package array;

import java.util.*;

// 滑动窗口  双指针


public class MinimumSizeSubarraySum {
    // 209. 长度最小的子数组
    //给定一个含有 n 个正整数的数组和一个正整数 target 。
    //找出该数组中满足其总和大于等于 target 的长度最小的
    // 子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。
    // 如果不存在符合条件的子数组，返回 0 。

    private static int minSubarrayLen(int[] nums, int target) {
        int l = 0;
        int r = 0;
        int sum = 0;
        int ans = Integer.MAX_VALUE;
        while (l < nums.length) {
            // 滑动窗口
            // 1.当前和小于目标则窗口右移（此时右边界未到最大）
            // 2.当前和大于等于target则比较ans并更新，同时左移
            // 3.移动到最后也未找到直接break
            if (sum < target && r < nums.length) {
                sum += nums[r];
                r++;
            } else if (sum >= target) {
                ans = Math.min(r - l, ans);
                sum -= nums[l];
                l++;
            } else {
                //sum <target && r == nums.length
                break;
            }
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }

    //904. 水果成篮
    //挑选子数组，里面至多有两种数字
    public static int totalFruit(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        //key nums[i]的值    value 上面的值在[l,r]中出现的次数
        int l = 0, ans = 0;

        for (int r = 0; r < nums.length; r++) {
            // 1. 加入右边的水果
            if (map.containsKey(nums[r])) {
                map.put(nums[r], map.get(nums[r]) + 1);
            } else {
                map.put(nums[r], 1);
            }

            // 2. 如果水果种类超过 2，就收缩左边界
            while (map.size() > 2) {
                map.put(nums[l], map.get(nums[l]) - 1); // 左边水果减1
                if (map.get(nums[l]) == 0) {           // 如果数量为0，移除
                    map.remove(nums[l]);
                }
                l++;
            }

            // 3. 更新最大结果
            ans = Math.max(ans, r - l + 1);
        }
        return ans;
    }

    // 76. 最小覆盖子串
    //给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。
    // 如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
    public static String minWindow(String s, String t) {
        int m = s.length();
        int n = t.length();
        if(m < n) return "";
        int l = 0,r = 0;
        int count = Integer.MAX_VALUE;
        String res = "";
        int need = n;
        // 统计t中字符出现的次数
        Map<Character,Integer> map = new HashMap<>();
        for(char c : t.toCharArray()){
            map.put(c,map.getOrDefault(c,0)+1);
        }
        while(r < m){
            char c = s.charAt(r);
            // 如果当前字符在t中，更新map和n
            if(map.containsKey(c)){
                if(map.get(c)>0){
                    need--;
                }
                map.put(c,map.get(c) - 1);
            }
            r++;// 右移右指针
            // 当n为0时，说明当前窗口已经包含t中所有字符，尝试收缩左边界
            while(need == 0){
                // 更新结果，记录最小长度和子串
                if(r - l < count){
                    count = r - l;
                    res = s.substring(l,r);
                }
                char leftchar = s.charAt(l);
                if(map.containsKey(leftchar)){
                    if(map.get(leftchar) >=0){
                        need++;
                    }
                    map.put(leftchar,map.get(leftchar)+1);
                }
                l++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        int tar = sc.nextInt();
        int ans = minSubarrayLen(nums, tar);
        System.out.println(ans);


    }
}

