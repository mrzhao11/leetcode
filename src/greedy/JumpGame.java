package greedy;

public class JumpGame {
    // 55. 跳跃游戏
    // 给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
    // 数组中的每个元素代表你在该位置可以跳跃的最大长度。
    // 判断你是否能够到达最后一个下标。
    public static boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }
        int cover = 0; // 目前能到达的最远位置
        // 只遍历当前可达范围[0, cover]内的下标i
        for (int i = 0; i <= cover; i++) {
            cover = Math.max(cover, i + nums[i]); // 站在位置 i，更新能到达的最远位置
            if (cover >= nums.length - 1) return true;
        }
        return false;
    }

    // 45. 跳跃游戏 II
    // 给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
    // 数组中的每个元素代表你在该位置可以跳跃的最大长度。
    // 你的目标是使用最少的跳跃次数到达数组的最后一个下标。
    // 假设你总是可以到达数组的最后一个下标。
    public static int jump(int[] nums) {
        if (nums == null || nums.length == 0 || nums.length == 1) {
            return 0;
        }
        int count = 0; //跳跃次数
        int curDistance = 0; //当前覆盖的最大区域，即当前能到达的最远位置
        int nextDistance = 0; //下一步可达的最大区域

        for (int i = 0; i < nums.length; i++) {
            //在可覆盖区域内更新最大的覆盖区域
            nextDistance = Math.max(nextDistance, i + nums[i]);

            //走到当前覆盖的最大区域时，更新下一步可达的最大区域
            if (i == curDistance) {
                count++;
                curDistance = nextDistance;
                //如果当前覆盖的最大区域已经到达或超过最后一个位置，跳出循环
                if (curDistance >= nums.length - 1) {
                    break;
                }
            }
        }
        return count;
    }


    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 1, 4};
        System.out.println(canJump(nums)); // 输出 true

        int[] nums2 = {2, 3, 0, 1, 4};
        System.out.println(jump(nums2)); // 输出 2
    }
}
