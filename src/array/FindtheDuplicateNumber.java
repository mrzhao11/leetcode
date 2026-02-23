package array;

public class FindtheDuplicateNumber {

    // 给定一个包含 n + 1 个整数的数组 nums，其数字都在 1 到 n 之间（包括 1 和 n）
    // 可知至少存在一个重复的整数。假设只有一个重复的整数，找出这个重复的数。

    // 二分查找
    // 抽屉原理：如果把 n + 1 个物品放到 n 个抽屉里，至少有一个抽屉会包含两个或更多的物品
    // 本题抽屉是数字范围 1 到 n，物品是数组中的 n + 1 个数字
    public static int findDuplicatebinary(int[] nums) {
        int n = nums.length;
        int left = 1, right = n - 1;
        int ans = -1;

        // 不在数组中查找，在数值范围内查找
        // 在区间 [1, mid] 里的数字个数如果大于 mid，则重复的数字在区间 [1, mid] 里
        // 否则在区间 [mid + 1, n] 里
        while (left <= right) {
            int mid = (left + right) >> 1;

            // 统计 <= mid 的个数
            int cnt = 0;
            for (int num : nums) {
                if (num <= mid) {
                    cnt++;
                }
            }

            // 如果 <= mid 的数多于 mid，说明重复数在左半边
            if (cnt > mid) {
                ans = mid; // 记录可能的答案
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    // 快慢指针（Floyd 判圈算法）
    // 例：nums = [3,1,3,4,2]
    // 索引：   0 1 2 3 4
    // 数值：   3 1 3 4 2
    // 可以将数组看作一个链表，索引表示节点位置，数值表示指向下一个节点的位置
    // 0 -> 3 -> 4 -> 2 -> 3 -> 4 -> 2 -> ...
    // 链表中存在环，环的入口就是重复的数字
    public int findDuplicate(int[] nums) {
        int slow = 0, fast = 0;

        // 第一阶段：找相遇点（一定会在环内相遇）
        do {
            slow = nums[slow];          // slow 走一步
            fast = nums[nums[fast]];   // fast 走两步
        } while (slow != fast);

        // 第二阶段：找环入口
        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        // 相遇点就是重复的数
        return slow;
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 4, 2, 2};
        System.out.println(findDuplicatebinary(nums)); // 输出 2
    }
}
