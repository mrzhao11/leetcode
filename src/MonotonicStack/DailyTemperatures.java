package MonotonicStack;

import java.util.ArrayDeque;
import java.util.Deque;
public class DailyTemperatures {
    public static int[] dailyTemperatures(int[] temperatures) {
        // 单调栈，存储下标，栈内元素对应的温度单调递减
        Deque<Integer> st = new ArrayDeque<>();
        int[] res = new int[temperatures.length];

        for (int i = 0; i < temperatures.length; i++) {
            // 遇到比栈顶元素大的温度，说明找到了更高温度的那一天
            while (!st.isEmpty() && temperatures[i] > temperatures[st.peek()]) {
                int prev = st.pop(); // 栈顶元素的下标
                res[prev] = i - prev; // 计算天数差
            }
            st.push(i);
        }
        return res;
    }

    public static void main(String[] args) {
        int[] temperatures = {73, 74, 75, 71, 69, 72, 76, 73};
        int[] res = dailyTemperatures(temperatures);
        for (int days : res) {
            System.out.print(days + " ");
        }
        // 输出: 1 1 4 2 1 1 0 0
    }
}
