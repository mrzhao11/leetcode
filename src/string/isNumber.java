package string;

public class isNumber {
    // 65. 有效数字
    // 验证给定的字符串是否可以解释为十进制数字。
    // 例如:
    // "0" => true
    // " 0.1 " => true
    // "abc" => false
    // "1 a" => false
    // "2e10" => true

    // 解析数字的状态机
    // 共存在以下几种情况：
    // 1. 数字部分：可以包含整数和小数，例如 "123", "0.1", ".5", "3."
    // 2. 指数部分：以 'e' 或 'E' 开头，后面必须跟一个整数，例如 "2e10", "3.5E-2"
    // 3. 符号部分：可以在数字部分的开头或指数部分的开头出现 '+' 或 '-'，例如 "+3.5", "2e-10"
    public static boolean isNumber(String s) {
        if (s == null) return false;
        s = s.trim();
        if (s.length() == 0) return false;

        boolean seenNum = false;        // 是否出现过数字
        boolean seenDot = false;        // 是否出现过小数点
        boolean seenE = false;          // 是否出现过 e/E
        boolean seenNumAfterE = true;   // e 后是否出现数字（默认 true，防止没 e 时误判）

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                seenNum = true;
                seenNumAfterE = true;
            }
            else if (c == '.') {
                // 小数点不能出现两次，也不能在 e 后面
                if (seenDot || seenE) return false;
                seenDot = true;
            }
            else if (c == 'e' || c == 'E') {
                // e 前必须有数字，且只能出现一次
                if (!seenNum || seenE) return false;
                seenE = true;
                seenNumAfterE = false;  // 重新要求 e 后必须有数字
            }
            else if (c == '+' || c == '-') {
                // 只能在开头或 e 后面
                if (i != 0 && s.charAt(i - 1) != 'e' && s.charAt(i - 1) != 'E') {
                    return false;
                }
            }
            else { // 其他字符不合法
                return false;
            }
        }

        return seenNum && seenNumAfterE;
    }

    public static void main(String[] args) {
        String[] testCases = {"0", " 0.1 ", "abc", "1 a", "2e10", ".1", "3.", "+.8", "-.9", "6e-1", "99e2.5"};
        for (String test : testCases) {
            System.out.println("\"" + test + "\" => " + isNumber(test));
        }
    }
}


