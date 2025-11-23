package greedy;

public class JumpGame {
    // 给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
    // 数组中的每个元素代表你在该位置可以跳跃的最大长度。
    // 判断你是否能够到达最后一个下标。
    public static boolean canJump(int[] nums) {
        int cover = 0; // 目前能到达的最远位置
        // 只遍历当前可达范围[0, cover]内的下标i
        for (int i = 0; i <= cover; i++) {
            cover = Math.max(cover, i + nums[i]); // 站在位置 i，更新能到达的最远位置
            if (cover >= nums.length - 1) return true;
        }
        return false;
    }

    // 给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
    // 数组中的每个元素代表你在该位置可以跳跃的最大长度。
    // 你的目标是使用最少的跳跃次数到达数组的最后一个下标。
    // 假设你总是可以到达数组的最后一个下标。
    public static int jump(int[] nums) {
        if (nums == null || nums.length == 0 || nums.length == 1) {
            return 0;
        }
        int count=0;          // 跳的步数
        int curDistance = 0;  // 当前已用了count步，能覆盖到的最远位置
        int maxDistance = 0;  // 在当前边界内，下一步能覆盖到的最远位置

        for (int i = 0; i < nums.length; i++) {
            maxDistance = Math.max(maxDistance, i + nums[i]);

            // 下一步就能到达终点
            if (maxDistance >= nums.length - 1) {
                count++;
                break;
            }

            // 到达当前边界，更新边界，并增加步数
            if (i == curDistance) {
                curDistance = maxDistance;
                count++;
            }
        }
        return count;
    }


    public static void main(String[] args) {
        int[] nums = {2,3,1,1,4};
        System.out.println(canJump(nums)); // 输出 true

        int[] nums2 = {2,3,0,1,4};
        System.out.println(jump(nums2)); // 输出 2
    }
}
