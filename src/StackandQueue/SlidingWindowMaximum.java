package StackandQueue;
import java.util.*;
//给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。
//你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
//返回 滑动窗口中的最大值 。

import java.util.ArrayDeque;

public class SlidingWindowMaximum {
    public static int[] maxSlidingWindow(int[] nums, int k){
        Deque<Integer> dq = new ArrayDeque<>(); // 存索引
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        int idx = 0;
        for(int i = 0;i < n; i++){
            // 保证dq头一直在窗口内
            while(!dq.isEmpty() && dq.peekFirst() < i - k + 1){
                dq.pollFirst();
            }

            // 维护一个单调递减栈
            while(!dq.isEmpty() && nums[i] > nums[dq.peekLast()]) {
                dq.pollLast();  // 新入队元素比尾大就poll
            }
            dq.offerLast(i);

            // 当i增长到第一个k范围就可以存结果数组
            if(i >= k - 1) {
                ans[idx++] = nums[dq.peekFirst()];
            }
        }
        return ans;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for(int i = 0;i<n;i++){
            nums[i] = sc.nextInt();
        }
        int k = sc.nextInt();
        System.out.println(Arrays.toString(maxSlidingWindow(nums,k)));
    }
}
