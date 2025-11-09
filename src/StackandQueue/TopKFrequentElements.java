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
        int[] ans = new int[k];
        // 自定义小根堆排序规则
        PriorityQueue<Integer> pq = new PriorityQueue<>(
                (x,y) -> Integer.compare(mp.get(x),mp.get(y))
        );
        for (int num : mp.keySet()) {
            pq.offer(num);
            if (pq.size() > k){
                pq.poll();
            }
        }
        int idx = 0;
        while(!pq.isEmpty()){
            ans[idx++] = pq.poll();
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
        System.out.println(Arrays.toString(topKFrequent(nums,k)));
    }
}
