package greedy;

import java.util.Arrays;

public class Candy {
    // n 个孩子站成一排。给你一个整数数组 ratings 表示每个孩子的评分。
    // 你需要按照以下要求，给这些孩子分发糖果：
    // 每个孩子至少分配到 1 个糖果。
    // 相邻两个孩子评分更高的孩子会获得更多的糖果。
    // 请你给每个孩子分发糖果，计算并返回需要准备的 最少糖果数目 。
    public static int candy(int[] ratings) {
        int[] res = new int[ratings.length];
        Arrays.fill(res, 1);
        if (ratings.length == 1) return 1;
        // 先处理右边评分大于左边的情况
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1])
                res[i] = res[i - 1] + 1;
        }
        // 然后处理左边大于右边的情况
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1])
                res[i] = Math.max(res[i], res[i + 1] + 1);
        }
        int ans = 0;
        for (int x : res) {
            ans += x;
        }
        return ans;
    }

    // 如果连成圈
    public static int minCandiesCircle(int[] ratings) {
        int n = ratings.length;
        if (n == 0) return 0;
        if (n == 1) return 1;

        // %n代表环形，所以左邻是 (i-1+n)%n，右邻是 (i+1)%n
        // 1) 找局部最小 s,找到第一个满足 ratings[s] <= ratings[s-1] && ratings[s] <= ratings[s+1] 的点
        int s = 0;
        for (int i = 0; i < n; i++) {
            int left = (i - 1 + n) % n; // 左邻 +n 是为了防止负数取模
            int right = (i + 1) % n; // 右邻
            if (ratings[i] <= ratings[left] && ratings[i] <= ratings[right]) {
                s = i;
                break;
            }
        }

        int[] candies = new int[n];

        // 2) 前向（顺时针）一圈：满足左邻约束
        candies[s] = 1;
        for (int k = 1; k < n; k++) {
            int i = (s + k) % n;                 // 从s出发，顺时针走k步到达的点
            int prev = (i - 1 + n) % n;          // 左邻
            if (ratings[i] > ratings[prev]) {
                candies[i] = candies[prev] + 1;
            } else {
                candies[i] = 1;
            }
        }

        // 3) 后向（逆时针）一圈：修正右邻约束（包含首尾）
        for (int k = n - 1; k >= 1; k--) {
            int i = (s + k) % n;                 // 当前点
            int next = (i + 1) % n;              // 右邻（顺时针下一个）
            if (ratings[i] > ratings[next] && candies[i] <= candies[next]) {
                candies[i] = candies[next] + 1;
            }
        }

        // 4) 求和
        int sum = 0;
        for (int c : candies) sum += c;
        return sum;
    }

    public static void main(String[] args) {
        int[] ratings = {2,3,1};
        System.out.println(candy(ratings)); // 输出 4

        // 环形情况
        int[] ratingsCircle = {2,3,1};
        System.out.println(minCandiesCircle(ratingsCircle)); // 输出 6
    }
}
