package StackandQueue;
import java.util.*;

// 给你一个整数数组 nums 和一个整数 k
// 请你返回其中出现频率前 k 高的元素。
public class TopKFrequentElements {
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> mp = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            mp.put(nums[i], mp.getOrDefault(nums[i], 0) + 1);
        }
        // 建立一个小顶堆，然后遍历「出现次数数组」，将每个数字的出现次数与堆顶元素进行比较。

        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> mp.get(a) - mp.get(b));
        for (int key : mp.keySet()) {
            // 如果堆的元素个数小于 k，就可以直接插入堆中。
            if (pq.size() < k) {
                pq.offer(key);
            }
            // 如果堆的元素个数等于 k，就需要将当前数字的出现次数与堆顶元素的出现次数进行比较。
            // 如果堆顶元素的出现次数更大，说明当前数字的出现次数不够大，无法进入堆中；否则，就将堆顶元素弹出，并将当前数字插入堆中。
            else if (mp.get(pq.peek()) < mp.get(key)) {
                pq.poll();
                pq.offer(key);
            }
        }
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = pq.poll();
        }
        return res;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] nums = new int[n];
        for(int i = 0;i<n;i++){
            nums[i] = sc.nextInt();
        }
        int k = sc.nextInt();
        System.out.println(Arrays.toString(topKFrequent(nums,k)));
    }
}
