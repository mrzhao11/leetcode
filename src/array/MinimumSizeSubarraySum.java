package array;
import java.util.*;

// 滑动窗口  双指针


//给定一个含有 n 个正整数的数组和一个正整数 target 。
//找出该数组中满足其总和大于等于 target 的长度最小的
// 子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。
// 如果不存在符合条件的子数组，返回 0 。

public class MinimumSizeSubarraySum {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for(int i = 0 ; i < n ; i++){
            nums[i] = sc.nextInt();
        }
        int tar =  sc.nextInt();
        int ans = minSubarrayLen(nums, tar);
        System.out.println(ans);
    }
    private static int minSubarrayLen(int[] nums,int target){
        int l=0;
        int r=0;
        int sum=0;
        int ans=Integer.MAX_VALUE;
        while(l < nums.length){
            // 滑动窗口
            // 1.当前和小于目标则窗口右移（此时右边界未到最大）
            // 2.当前和大于等于target则比较ans并更新，同时左移
            // 3.移动到最后也未找到直接break
            if(sum<target && r<nums.length){
                sum += nums[r];
                r++;
            }else if(sum>=target){
                ans=Math.min(r-l,ans);
                sum -= nums[l];
                l++;
            }else{
                //sum <target && r == nums.length
                break;
            }
        }
        return ans == Integer.MAX_VALUE ? 0 :ans;
    }

    //挑选子数组，里面至多有两种数字
    public int totalFruit(int[] nums) {
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

    //给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。
    // 如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
    public String minWindow(String s, String t) {
        if (t.length() > s.length()) {
            return "";
        }

        int l = 0;
        String ans = null;

        // 统计 t 中每个字符的频率
        Map<Character, Integer> mp = new HashMap<>();
        for (char c : t.toCharArray()) {
            mp.put(c, mp.getOrDefault(c, 0) + 1);
        }

        for (int r = 0; r < s.length(); r++) {
            if (mp.containsKey(s.charAt(r))) {
                mp.put(s.charAt(r), mp.get(s.charAt(r)) - 1);
            }

            // 判断窗口是否已覆盖
            boolean allCovered = mp.values().stream().allMatch(v -> v <=0);

            // 收缩左边界
            while (allCovered) {
                String tmp = s.substring(l, r + 1);
                if (ans == null || tmp.length() < ans.length()) {
                    ans = tmp;
                }
                if (mp.containsKey(s.charAt(l))) {
                    mp.put(s.charAt(l), mp.get(s.charAt(l)) + 1);
                }
                l++;
                allCovered = mp.values().stream().allMatch(v -> v <= 0);
            }
        }

        return ans == null ? "" : ans;
    }
}

