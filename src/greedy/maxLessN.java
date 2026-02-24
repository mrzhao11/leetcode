package greedy;

import java.util.Arrays;

public class maxLessN {
    // 给定一组 digits 和一个整数 n，找出由 digits 中的数字组成的、严格小于 n 的最大整数。
    // 注意：digits 中的数字可以重复使用。
    // 从最高位开始贪心，如果这一位能变小，就后面全填最大；如果这一位没法选，就往前回退。
    public static int maxLessThanN(int[] digits, int n) {
        Arrays.sort(digits); // 确保 digits 有序，方便后续查找

        char[] target = String.valueOf(n).toCharArray(); // 将 n 转为字符数组，逐位处理
        int m = target.length;

        StringBuilder sb = new StringBuilder(); // 构建结果字符串

        // 遍历 target 的每一位，尝试选择一个 <= 当前位的 digit
        for (int i = 0; i < m; i++) {
            int cur = target[i] - '0'; // 当前位的目标数字

            int pick = findLE(digits, cur); // 找 <= 当前位的最大 digit

            // 当前位无法匹配 → 回退
            if (pick == -1) {
                return rollback(sb, digits, m);
            }
            // 找到了一个合适的 digit，选择它
            sb.append(pick); // 选择当前位的 digit

            // 当前位变小，那么已经保证后面填最大 digit 也不会超过 n，直接填最大 digit 返回结果
            if (pick < cur) {
                fillMax(sb, digits, m); // 后面填最大 digit
                return Integer.parseInt(sb.toString());
            }
        }

        // 完全匹配 n，回退找更小的数
        return rollback(sb, digits, m);
    }

    // 当前位没有找到 <= target 的 digit，回退前一位看看能不能选一个更小的 digit，选了之后后面全填最大 digit
    // 找 <= target 的最大 digit，从后往前找，找到第一个满足条件的 digit 就返回
    // 例如 digits = [1, 3, 5], target = 4，返回 3；target = 5，返回 5；target = 0，返回 -1
    private static int findLE(int[] digits, int target) {
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] <= target) return digits[i];
        }
        return -1;
    }

    // 回退，从末尾开始找 < 当前位的最大 digit，找到后后面全部填最大 digit
    private static int rollback(StringBuilder sb, int[] digits, int m) {
        int i = sb.length() - 1; // 从末尾开始回退

        while (i >= 0) {
            int cur = sb.charAt(i) - '0'; // 当前位的数字
            int smaller = findLess(digits, cur); // 找 < 当前位的最大 digit而不是 <= 当前位的 digit，因为如果是 <= 当前位的 digit，说明当前位没有变小，后面填最大 digit 可能会超过 n

            if (smaller != -1) { // 找到了一个更小的 digit，选择它
                sb.setCharAt(i, (char) (smaller + '0')); // 替换当前位为更小的 digit

                fillMax(sb, digits, m); // 后面填最大 digit，保证回退后得到的数是最大的
                return Integer.parseInt(sb.toString()); // 返回回退后的结果
            }
            i--;
        }

        // 回退失败，只能返回一个位数更小的最大数，例如 digits = [1, 3, 5], n = 100，回退失败只能返回 55
        StringBuilder res = new StringBuilder();
        int maxDigit = digits[digits.length - 1]; // digits 中的最大 digit
        for (int k = 0; k < m - 1; k++) { // 填充 m-1 位的最大 digit，得到一个位数更小的最大数
            res.append(maxDigit);
        }
        // 如果 m-1 位的最大数也无法满足条件，说明 digits 中的最大 digit 也比 n 的最高位小，那么只能返回 0
        return res.length() == 0 ? 0 : Integer.parseInt(res.toString());
    }

    // 找 < target 的最大 digit
    private static int findLess(int[] digits, int target) {
        for (int i = digits.length - 1; i >= 0; i--) {
            if (digits[i] < target) return digits[i];
        }
        return -1;
    }

    // 后面填最大 digit
    private static void fillMax(StringBuilder sb, int[] digits, int m) {
        int maxDigit = digits[digits.length - 1];
        while (sb.length() < m) {
            sb.append(maxDigit);
        }
    }

    public static void main(String[] args) {
        int[] digits = {1, 3, 5};
        int n = 100;
        System.out.println(maxLessThanN(digits, n)); // 输出 55
    }
}
