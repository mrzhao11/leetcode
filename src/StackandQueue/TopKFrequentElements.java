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
        // 大顶堆，按频率排序,频率高的在前面
        PriorityQueue<Integer> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(mp.get(b), mp.get(a)));
        int[] res = new int[k];
        // 将所有元素加入堆中
        for (int num : mp.keySet()) {
            pq.offer(num);
        }

        // 取出前k个高频元素
        int idx = 0;
        while(!pq.isEmpty() && k!=0){
            res[idx++] = pq.poll();
            k--;
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
