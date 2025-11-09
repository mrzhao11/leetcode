package array;

import java.util.*;

// 原地移除等于val的元素，并返回剩余的k
// hint：双指针  快慢指针

public class RemoveElement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 输入数组长度
        int n = sc.nextInt();
        int[] nums = new int[n];

        // 输入数组元素
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        // 输入要删除的 val
        int val = sc.nextInt();

        // 调用方法
        int k = removeElement(nums, val);

        // 输出结果长度
        System.out.println(k);

        // 输出新数组的前 k 个元素
        for (int i = 0; i < k; i++) {
            System.out.print(nums[i] + " ");
        }
    }

    // 原地删除 val 的实现
    public static int removeElement(int[] nums, int val) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != val) {
                nums[i] = nums[j];
                i++;
            }
        }
        return i;
    }
}
// 2 3 2 2 3    3
// l
// r
// 2 2 2
