package MonotonicStack;

import java.util.*;

public class Nextbiggerone {

    // 下一个更大元素 I
    // 给你两个 没有重复元素 的数组 nums1 和 nums2 ，其中 nums1 是 nums2 的子集。
    // 请你找出 nums1 中每个元素在 nums2 中的下一个更大元素。
    // nums1 中数字 x 的下一个更大元素是指 x 在 nums2 中对应位置的右侧的第一个比 x 大的元素。
    // 如果不存在，则输出 -1 。
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> next = new HashMap<>(); // 存每个元素的 next greater
        Deque<Integer> st = new ArrayDeque<>(); // 存 nums2 的“值”，保持单调递减

        for (int x : nums2) {
            while (!st.isEmpty() && x > st.peek()) {
                next.put(st.pop(), x); // x 是被弹出元素的 next greater
            }
            st.push(x);
        }
        // 栈里剩下的都没有 next greater，默认 -1（不放也行）

        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            res[i] = next.getOrDefault(nums1[i], -1);
        }
        return res;
    }

    // 下一个更大元素 II
    // 给定一个循环数组（最后一个元素的下一个元素是第一个元素），请输出每个元素的下一个更大元素。
    // 数字 x 的下一个更大元素是指 x 右侧第一个比 x 大的元素，这意味着你需要搜索它的下一个循环遍历的元素。
    // 如果不存在，则输出 -1。
    public static int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Arrays.fill(res, -1);

        Deque<Integer> st = new ArrayDeque<>();

        for (int i = 0; i < 2 * n; i++) {
            int idx = i % n; // 模拟循环数组

            while (!st.isEmpty() && nums[idx] > nums[st.peek()]) {
                res[st.pop()] = nums[idx];
            }

            if (i < n) {
                st.push(idx);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums1 = {4, 1, 2};
        int[] nums2 = {1, 3, 4, 2};
        int[] res1 = nextGreaterElement(nums1, nums2);
        System.out.println("Next greater elements for nums1 in nums2:");
        for (int val : res1) {
            System.out.print(val + " ");
        }
        System.out.println();

        int[] nums = {1, 2, 1};
        int[] res2 = nextGreaterElements(nums);
        System.out.println("Next greater elements in circular array:");
        for (int val : res2) {
            System.out.print(val + " ");
        }
    }
}
