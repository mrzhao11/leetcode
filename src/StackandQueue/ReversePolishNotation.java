package StackandQueue;
// 给你一个字符串数组 tokens ，表示一个根据 逆波兰表示法(后缀表达式) 表示的算术表达式。
//请你计算该表达式。返回一个表示表达式值的整数。
import java.util.*;

public class ReversePolishNotation {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 读取一整行并按空格拆分
        String line = sc.nextLine().trim();
        String[] tokens = line.split("\\s+"); // \s 表示任意空白字符 +表示出现一次或多次
        System.out.println(evalRPN(tokens));
        sc.close();
    }

    // 求逆波兰表达式的值
    public static int evalRPN(String[] tokens) {
        Deque<Integer> st = new ArrayDeque<>();

        for (String t : tokens) {
            if (t.equals("+")) {
                st.push(st.pop() + st.pop());
            } else if (t.equals("-")) {
                int a = st.pop(), b = st.pop();
                st.push(b - a);
            } else if (t.equals("*")) {
                st.push(st.pop() * st.pop());
            } else if (t.equals("/")) {
                int a = st.pop(), b = st.pop();
                st.push(b / a);
            } else {
                st.push(Integer.parseInt(t)); // 数字入栈
            }
        }
        return st.pop();
    }
}