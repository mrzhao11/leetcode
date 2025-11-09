package hashtable;

import java.util.*;

public class TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            int need = target - nums[i];
            if(map.containsKey(need)){
                return new int[]{map.get(need), i};
            }
            map.put(nums[i], i);
        }
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
        if(ans.length == 2){
            System.out.println(ans[0] + " " + ans[1]);
        } else {
            System.out.println("No solution");
        }
    }
}
