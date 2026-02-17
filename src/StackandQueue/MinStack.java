package StackandQueue;

import java.util.ArrayDeque;
import java.util.Deque;

// 设计一个支持 push，pop，top 操作，并能在常数时间内检索到最小元素的栈。
// push(x) —— 将元素 x 推入栈中。
// pop() —— 删除栈顶的元素。
// top() —— 获取栈顶元素。
// getMin() —— 检索栈中的最小元素。
public class MinStack {
    private Deque<Integer> st; // 用于存储栈元素
    private Deque<Integer> min; // 辅助栈，用于存储当前最小元素
    // min和st同步，每个位置的min都是st对应位置的最小值

    public MinStack() {
        st = new ArrayDeque<>();
        min = new ArrayDeque<>();
    }

    public void push(int val) {
        st.push(val);
        // 只有当新元素小于等于当前最小元素时，才将其压入辅助栈
        if(min.isEmpty() || val <= min.peek()){
            min.push(val);
        }
    }

    public void pop() {
        int top = st.pop(); // 弹出主栈的元素
        if(top == min.peek()){ // 如果弹出的元素是当前最小元素，则辅助栈也要弹出
            min.pop();
        }
    }

    public int top() {
        return st.peek();
    }

    public int getMin() {
        return min.peek();
    }
}
// 差值栈,通过存储当前元素与最小元素的差值来实现空间优化
// diff = 当前元素 - 最小元素
// 当 diff < 0 时，代表当前元素小于最小元素，此时更新最小元素为当前元素，并将 diff 压入栈中
// 当 diff >= 0 时，代表当前元素大于等于最小元素，此时直接将 diff 压入栈中
class MinStackdiff {
    private Deque<Long> st; // 用于存储差值
    private long min; // 当前最小元素

    public MinStackdiff() {
        st = new ArrayDeque<>();
    }

    public void push(int val) {
        if(st.isEmpty()){
            st.push(0L); // 差值为0
            min = val; // 更新最小元素
        } else {
            long diff = val - min; // 计算差值
            st.push(diff);
            if(diff < 0){ // 新元素更小，更新最小元素
                min = val;
            }
        }
    }

    public void pop() {
        long diff = st.pop();
        if(diff < 0){ // 弹出的元素是当前最小元素，恢复之前的最小元素
            min = min - diff;
        }
    }

    public int top() {
        long diff = st.peek();
        if(diff >= 0){ // 如果差值非负，当前元素大于等于最小元素
            return (int)(min + diff); // 当前元素等于最小元素加上差值
        } else {
            return (int)min; // 当前元素就是最小元素
        }
    }

    public int getMin() {
        return (int)min;
    }
}
