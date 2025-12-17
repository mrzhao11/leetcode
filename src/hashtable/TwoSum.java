package hashtable;

import java.util.*;

public class TwoSum {
    // 两数之和
    // 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回它们的数组下标。
    // 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
    public static int[] twoSum(int[] nums, int target) {
        // Map存放 数值和对应的索引
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int need = target - nums[i]; // 需要的数值
            // 查找是否存在需要的数值
            if (map.containsKey(need)) {
                // 因为答案唯一，找到后直接返回
                return new int[]{map.get(need), i};
            }
            map.put(nums[i], i);
        }
        // 未找到返回空数组
        return new int[0];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 输入数组长度
        int n = sc.nextInt();
        int[] nums = new int[n];

        // 输入数组元素
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        // 输入目标值
        int target = sc.nextInt();

        // 调用函数
        int[] ans = twoSum(nums, target);

        // 输出结果
        if (ans.length == 2) {
            System.out.println(ans[0] + " " + ans[1]);
        } else {
            System.out.println("No solution");
        }
    }
}
