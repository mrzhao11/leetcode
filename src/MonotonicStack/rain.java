package MonotonicStack;

import java.util.ArrayDeque;
import java.util.Deque;

public class rain {
    // 接雨水
    // 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
    // 暴力解法
    public static int trap(int[] height) {
        int sum = 0;
        for (int i = 0; i < height.length; i++) {
            // 边界不存水
            if (i == 0 || i == height.length - 1) continue;

            int rHeight = height[i]; // 右侧最高
            int lHeight = height[i]; // 左侧最高
            for (int r = i + 1; r < height.length; r++) {
                rHeight = Math.max(rHeight, height[r]);
            }
            for (int l = i - 1; l >= 0; l--) {
                lHeight = Math.max(lHeight, height[l]);
            }
            // 当前位置能存水的高度等于左右两侧最高的较小值减去当前位置高度
            int h = Math.min(lHeight, rHeight) - height[i];
            if (h > 0) {
                sum += h;
            }
        }
        return sum;
    }

    // dp优化
    public static int trap2(int[] height) {
        if (height.length <= 2) return 0;
        int[] maxLeft = new int[height.length]; // 记录每个位置左侧最高
        int[] maxRight = new int[height.length]; // 记录每个位置右侧最高

        maxLeft[0] = height[0]; // 初始化左侧最高
        for (int i = 1; i < height.length; i++) {
            maxLeft[i] = Math.max(maxLeft[i - 1], height[i - 1]); // 当前位置左侧最高等于前一个位置左侧最高和前一个位置高度的较大值
        }
        maxRight[height.length - 1] = height[height.length - 1];// 初始化右侧最高
        for (int i = height.length - 2; i >= 0; i--) {
            maxRight[i] = Math.max(maxRight[i + 1], height[i + 1]); // 当前位置右侧最高等于后一个位置右侧最高和后一个位置高度的较大值
        }
        int sum = 0;
        for (int i = 0; i < height.length; i++) {
            int h = Math.min(maxLeft[i], maxRight[i]) - height[i];
            if (h > 0) {
                sum += h;
            }
        }
        return sum;
    }

    // 双指针解法
    public static int trapTwoPointers(int[] height) {
        if (height == null || height.length <= 2) return 0;

        int l = 0, r = height.length - 1;
        int leftMax = 0, rightMax = 0; // 左指针及其左侧最高，右指针及其右侧最高
        // leftMax 和 rightMax 不是全局的，而是“指针扫过区域内”的最高值
        int sum = 0;

        // 进入任意一次循环时，都有以下成立：
        // 不变式 I：区间 [0, l-1] 的水量已经被正确、最终地计算完成
        // 不变式 II：区间 [r+1, n-1] 的水量已经被正确、最终地计算完成
        // 不变式 III：区间 [l, r] 的水量尚未确定，需要依赖未来信息
        while (l < r) {
            if (height[l] < height[r]) {
                // 左边更矮，右边一定更高，因此不需要知道右侧最高值，根据左边即可计算
                leftMax = Math.max(leftMax, height[l]); // 更新左侧最高
                sum += leftMax - height[l]; // 当前位置能存水的高度等于左侧最高减去当前位置高度
                l++;
            } else {
                // 右边更矮，右边的水量可以确定
                rightMax = Math.max(rightMax, height[r]);
                sum += rightMax - height[r];
                r--;
            }
        }

        return sum;
    }


    // 单调栈解法
    public static int trap3(int[] height) {
        if (height == null || height.length < 3) return 0;

        Deque<Integer> st = new ArrayDeque<>(); // 单调栈，存储柱子下标，栈内元素对应的高度单调递减
        int sum = 0;

        st.push(0); // 第一个柱子（下标）入栈
        for (int i = 1; i < height.length; i++) {

            if (height[i] < height[st.peek()]) {
                // 当前柱子比栈顶低，入栈
                st.push(i);
            } else if (height[i] == height[st.peek()]) {
                // 当前柱子和栈顶一样高，更新栈顶为当前柱子
                st.pop();
                st.push(i);
            } else {
                // 只要当前柱子比栈顶高，就可能形成凹槽，栈头第二个元素是左墙，栈头是凹槽底部，当前柱子是右墙
                while (!st.isEmpty() && height[i] > height[st.peek()]) {
                    int mid = st.pop(); // 凹槽底部

                    // 弹出后如果栈空，说明左边没有墙，不能接水
                    if (st.isEmpty()) break;

                    int left = st.peek(); // 左墙下标

                    // 凹槽高度 = min(左墙, 右墙) - 凹槽底
                    int h = Math.min(height[left], height[i]) - height[mid];

                    // 凹槽宽度 = 右墙 - 左墙 - 1
                    int w = i - left - 1;

                    sum += h * w;
                }
            }
            // 当前柱子入栈
            st.push(i);
        }
        return sum;
    }

    // 柱状图中最大的矩形
    // 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
    // 求在该柱状图中，能够勾勒出来的矩形的最大面积。
    // 本题求能勾勒出的矩形的最大面积，那么高度一定是它覆盖所有柱子中的最低高度，宽度则是它覆盖的柱子数量
    // 当确定某根柱子 i 是最矮的柱子时，，此时高度固定，向左延伸和向右延伸，直到遇到比它更矮的柱子为止，这就是最大宽度
    public static int largestRectangleArea(int[] heights) {
        // 单调栈，存储下标，栈内元素对应的高度单调递增，即栈顶是最高的柱子
        Deque<Integer> st = new ArrayDeque<>();
        // 在 heights 数组的两端各添加一个高度为 0 的柱子，方便处理边界情况
        // 假如一个数组是单调递增的，那么栈里会一直有元素，最后一个元素出栈时，左边没有更小的元素了，因此需要在左边添加一个哨兵
        // 同理，假如一个数组是单调递减的，那么栈里会一直有元素，最后一个元素出栈时，右边没有更小的元素了，因此需要在右边添加一个哨兵
        int[] newHeights = new int[heights.length + 2];
        newHeights[0] = 0;
        newHeights[newHeights.length - 1] = 0;
        for (int index = 0; index < heights.length; index++) {
            newHeights[index + 1] = heights[index];
        }
        heights = newHeights;

        int square = heights[0];
        st.push(0);
        for (int i = 1; i < heights.length; i++) {
            if (heights[i] > heights[st.peek()]) { // 当前柱子比栈顶高，入栈
                st.push(i);
            } else if (heights[i] == heights[st.peek()]) { // 当前柱子和栈顶一样高，更新栈顶为当前柱子
                st.pop();
                st.push(i);
            } else {
                while (!st.isEmpty() && heights[i] < heights[st.peek()]) {
                    int mid = st.pop(); // 作为最矮柱子的下标
                    if (st.isEmpty()) break;
                    int left = st.peek(); // 左侧第一个比它矮的柱子下标
                    int h = heights[mid]; // 矩形高度等于最矮柱子高度
                    // 为什么右侧第一个比它矮的柱子下标就是 i 呢？
                    // 因为当前柱子 heights[i] 比 heights[mid] 矮，而 heights[mid] 是栈顶弹出时的最高柱子
                    // 因此 heights[i] 一定是右侧第一个比 heights[mid] 矮的柱子
                    int w = i - left - 1; // 矩形宽度等于右侧第一个比它矮的柱子下标减去左侧第一个比它矮的柱子下标再减一

                    square = Math.max(square, h * w);
                }
            }
            st.push(i);
        }
        return square;
    }

    public static void main(String[] args) {
        int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println(trap(height)); // 输出 6
        System.out.println(trap2(height)); // 输出 6
        System.out.println(trap3(height)); // 输出 6
        System.out.println(trapTwoPointers(height));

        int[] heights = {2, 1, 5, 6, 2, 3};
        System.out.println(largestRectangleArea(heights)); // 输出 10
    }
}