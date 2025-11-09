package hashtable;
import java.util.*;

public class ThreeSum {
    public static List<List<Integer>> threesum(int[] nums){
        List<List<Integer>> ans = new ArrayList<>();
        Arrays.sort(nums);
        for(int i = 0;i<nums.length-2;i++){
            // 跳过重复的nums[i]
            if(i>0&&nums[i]==nums[i-1]) continue;
            int left = i + 1;
            int right = nums.length-1;
            while(left < right){
                int sum = nums[i] + nums[left] + nums[right];
                if(sum == 0){
                    ans.add(Arrays.asList(nums[i],nums[left],nums[right]));
                    // 跳过重复的 left
                    while(left < right && nums[left] == nums[left + 1]) left++;
                    // 跳过重复的 right
                    while(left < right && nums[right] == nums[right - 1]) right--;
                    right--;
                    left++;
                }else if(sum > 0){
                    right--;
                }else{
                    left++;
                }
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
        List<List<Integer>> res = threesum(nums);
        System.out.println(res);
    }
}

// 四数之和 思路类似

//class Solution {
//    public List<List<Integer>> fourSum(int[] nums, int target) {
//        List<List<Integer>> ans = new ArrayList<>();
//        Arrays.sort(nums);  // 排序
//
//        int n = nums.length;
//        for (int i = 0; i < n - 3; i++) {
//            if (i > 0 && nums[i] == nums[i - 1]) continue; // 去重 i
//            for (int j = i + 1; j < n - 2; j++) {
//                if (j > i + 1 && nums[j] == nums[j - 1]) continue; // 去重 j
//
//                int left = j + 1, right = n - 1;
//                while (left < right) {
//                    long sum = (long)nums[i] + nums[j] + nums[left] + nums[right]; // 防止溢出
//                    if (sum == target) {
//                        ans.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
//
//                        // 跳过重复 left
//                        while (left < right && nums[left] == nums[left + 1]) left++;
//                        // 跳过重复 right
//                        while (left < right && nums[right] == nums[right - 1]) right--;
//
//                        left++;
//                        right--;
//                    } else if (sum < target) {
//                        left++;
//                    } else {
//                        right--;
//                    }
//                }
//            }
//        }
//        return ans;
//    }
//}
