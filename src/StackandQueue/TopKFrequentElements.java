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
            // 如果堆的元素个数大于等于 k，就需要将当前元素的出现次数与堆顶元素的出现次数进行比较。
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

    // 也可以使用桶排序，建立一个「桶」
    // 其中第 i 个桶存储出现次数为 i 的数字。由于一个数字出现的次数不可能超过 n，因此桶的大小为 n+1。
    public static int[] topKFrequent2(int[] nums, int k) {
        Map<Integer, Integer> mp = new HashMap<>(); // 统计每个数字出现的次数
        for (int i = 0; i < nums.length; i++) {
            mp.put(nums[i], mp.getOrDefault(nums[i], 0) + 1);
        }

        // 建立一个桶，其中第 i 个桶存储出现次数为 i 的数字
        List<Integer>[] bucket = new List[nums.length + 1];
        for (int key : mp.keySet()) {
            int freq = mp.get(key);
            if (bucket[freq] == null) {
                bucket[freq] = new ArrayList<>();
            }
            bucket[freq].add(key);
        }

        int[] res = new int[k];
        int index = 0; // 记录结果数组的索引
        // 外层循环从后往前遍历桶
        for (int i = bucket.length - 1; i >= 0 && index < k; i--) {
            if (bucket[i] != null) {
                // 内层循环遍历当前桶中的数字
                for (int num : bucket[i]) {
                    res[index++] = num;
                    if (index == k) {
                        break;
                    }
                }
            }
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
