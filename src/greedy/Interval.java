package greedy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Interval {
    // 452. 用最小数量的箭引爆气球
    // 给你一些气球，气球用区间表示，区间的开始和结束分别代表水平方向上气球的起始和结束坐标。
    // 由于水平方向上没有限制，气球的直径可以看作一个区间。
    // 一支箭可以沿着 x 轴从不同点完全穿过气球所在的区间来引爆气球。
    // 给你一个数组 points ，其中 points[i] = [xstart, xend] 表示水平直径在 xstart 和 xend 之间的气球。
    // 求引爆所有气球所需的最小箭数。 可以射出的箭的数量没有限制，且可以放置在 x 轴上的任意位置。
    public static int findMinArrowShots(int[][] points) {
        Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0])); // 按区间起点排序
        int count = 1;
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > points[i - 1][1]) { // 当前区间与上一个区间不重叠，需要增加箭的数量
                count++;
            } else {
                points[i][1] = Math.min(points[i][1], points[i - 1][1]); // 更新重叠区间右边边界的最小值
            }
        }
        return count;
    }

    // 435. 无重叠区间
    // 给定一个区间的集合 intervals ，其中 intervals[i] = [starti, endi] 。返回 需要移除区间的最小数量，使剩余区间互不重叠 。
    public static int earseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1])); // 按区间结束位置排序
        int remove = 0;  // 记录需要移除的区间数量
        int pre = intervals[0][1]; // 记录上一个区间的结束位置
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < pre) { // 当前区间与上一个区间重叠
                remove++;
                pre = Math.min(pre, intervals[i][1]); // 更新重叠区间右边边界的最小值
            } else {
                pre = intervals[i][1];
            }
        }
        return remove;
    }

    // 合并区间
    // 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
    // 请你合并所有重叠的区间，并返回一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。
    public static int[][] merge(int[][] intervals) {
        // List<int[]> 用于存储合并后的区间
        List<int[]> res = new ArrayList<>();
        if (intervals.length == 1) return intervals;
        // 按区间起点排序
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        int curLeft = intervals[0][0]; // 记录当前合并区间的左边界
        int curRight = intervals[0][1]; // 记录当前合并区间的右边界

        for (int i = 1; i < intervals.length; i++) {
            int left = intervals[i][0];
            int right = intervals[i][1];

            if (left <= curRight) {
                // 有重叠，右边界取最大值
                curRight = Math.max(curRight, right);
            } else {
                // 无重叠，将当前区间加入结果，并更新当前合并区间的边界
                res.add(new int[]{curLeft, curRight});
                curLeft = left;
                curRight = right;
            }
        }

        // 由于最后一个区间可能没有被添加到结果中，因此需要在循环结束后再添加一次
        res.add(new int[]{curLeft, curRight});

        return res.toArray(new int[res.size()][]);
    }

    // 划分字母区间
    // 字符串 s 由小写英文字母组成。我们要把这个字符串划分为尽可能多的片段，
    // 使得每个字母最多出现在一个片段中。返回一个表示每个字符串片段的长度的列表。
    public static List<Integer> partitionLabels(String s) {
        List<Integer> res = new ArrayList<>();
        int[] end = new int[26];
        for (int i = 0; i < s.length(); i++) {
            // a - 'a' = 0, b - 'a' = 1, ...
            end[s.charAt(i) - 'a'] = i; // 记录每个字母最后出现的位置
        }
        int left = 0, right = 0; // 记录当前片段的左右边界
        for (int i = 0; i < s.length(); i++) {
            right = Math.max(right, end[s.charAt(i) - 'a']); // 更新右边界
            if (i == right) { // 到达当前片段的右边界
                res.add(right - left + 1); // 计算片段长度并加入结果列表
                left = i + 1; // 更新左边界
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[][] points = {{10, 16}, {2, 8}, {1, 6}, {7, 12}};
        System.out.println(findMinArrowShots(points)); // 输出 2

        int[][] intervals = {{1, 2}, {2, 3}, {3, 4}, {1, 3}};
        System.out.println(earseOverlapIntervals(intervals)); // 输出 1

        int[][] mergeIntervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] merged = merge(mergeIntervals);
        for (int[] interval : merged) {
            System.out.println(Arrays.toString(interval)); // 输出 [1,6], [8,10], [15,18]
        }

        String s = "ababcbacadefegdehijhklij";
        System.out.println(partitionLabels(s)); // 输出 [9,7,8]
    }
}
