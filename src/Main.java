//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
//给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
//
//给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。

import java.util.ArrayList;
import java.util.List;

//示例 1：
//输入：digits = "23"
//输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
//示例 2：
//
//输入：digits = ""
//输出：[]
//示例 3：
//
//输入：digits = "2"
//输出：["a","b","c"]
//
//
//提示：
//
//0 <= digits.length <= 4
//digits[i] 是范围 ['2', '9'] 的一个数字。
public class Main {
    static List<String> res = new ArrayList<>();
    public static List<String> number(String digits){
        String[] numString = {"","","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};
        backtracking(digits,numString,0);
        return res;
    }

    static StringBuilder sb = new StringBuilder();
//    public static void backtracking(String digits, String[] numString, int idx){
//        if(idx == digits.length()){
//            res.add(sb.toString());
//            return;
//        }
//
//        digits - '0'
//        int index = digits.charAt(idx) - '0'; // 2
//        String str = numString[index];
//        for(int i = 0;i < str.length();i++){
//            sb.append(str.charAt(i));
//            backtracking(digits,numString,i + 1);
//            sb.deleteCharAt(sb.length()-1);
//        }
//    }

    public static void backtracking(String digits, String[] numString, int idx) {
        // 终止条件：当索引等于输入字符串长度时，收集结果
        if (idx == digits.length()) {
            res.add(sb.toString());
            return;
        }

        // 1. 获取当前数字对应的字符串 (比如输入 "23", idx=0 时获取 "abc")
        int index = digits.charAt(idx) - '0';
        String str = numString[index];

        // 2. 遍历当前数字对应字符串中的【每一个】字母
        for (int i = 0; i < str.length(); i++) {
            sb.append(str.charAt(i));          // 处理：添加当前字母
            backtracking(digits, numString, idx + 1); // 递归：处理下一个数字
            sb.deleteCharAt(sb.length() - 1);  // 回溯：撤销选择
        }
    }

    public static void main(String[] args){
        String test = "23";
        List<String> res = number(test);
        System.out.println(res);
    }
}