package array;

import java.util.HashMap;
import java.util.Map;

public class SubarraySumEqualsK {

    // 给定一个整数数组 nums 和一个整数 k ，请你统计并返回该数组中和为 k 的连续子数组的个数。
    // 子数组 是数组中的一个连续部分。
    public static int subarraySum(int[] nums, int k) {
        int res = 0;
        int prefixSum = 0;

        // key：前缀和，value：该前缀和出现的次数
        Map<Integer, Integer> map = new HashMap<>();
        // 前缀和为0出现1次，方便计算从数组开头就符合条件的子数组
        map.put(0, 1);

        // 前缀和
        // prefixSum[i] = nums[0] + nums[1] + ... + nums[i]
        // 子数组和
        // sum(i,j) = prefixSum[j] - prefixSum[i-1] = k
        // => prefixSum[i-1] = prefixSum[j] - k
        // 因此，我们每次计算出 prefixSum[j] 后，去 map 中查找有多少 prefixSum[i-1] = prefixSum[j] - k 出现过
        for (int num : nums) {
            prefixSum += num; // 当前前缀和

            // 1️⃣ 先查：有多少 prefixSum - k 出现过
            if (map.containsKey(prefixSum - k)) {
                res += map.get(prefixSum - k);
            }

            // 2️⃣ 再存当前前缀和
            map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
        }

        return res;
    }
}
