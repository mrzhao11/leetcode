package string;

import java.util.*;

public class FindAllAnagramsinString {
    // 找到字符串中所有字母异位词
    // 给定两个字符串 s 和 p，返回 s 中所有 p 的 异位词 的起始索引。不考虑答案输出的顺序。
    // 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        if (s.length() < p.length())
            return res;
        // Map存放 p 中每个字符及其出现的次数
        Map<Character, Integer> map = new HashMap<>();
        for (char c : p.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int l = 0, r = 0;
        int need = p.length(); // 还需要匹配的字符数

        // 维护一个滑动窗口 [l,r]，始终保持窗口长度小于等于p.length()，map中记录的是：p还缺少的字符数
        // 初始map = p 的字符频率
        // 窗口右移时，如果遇到 p 中的字符，说明匹配到了一个需要的字符，need 减 1，map 中该字符的频率减 1
        // 当窗口长度等于 p.length() 时，判断 need 是否为 0，如果是，则记录起始索引 l
        // 然后窗口左移，若移出的字符在 p 中，说明失去匹配了一个需要的字符，need 加 1，map 中该字符的频率加 1
        while (r < s.length()) {
            char c = s.charAt(r);
            // 当前字符在 p 中，消耗一个需要匹配的字符，need 减 1
            if (map.containsKey(c)) {
                // 大于0判断加入前是否是需要的字符
                if (map.get(c) > 0)
                    need--;
                map.put(c, map.get(c) - 1);
            }
            r++;

            // 当窗口长度等于 p.length() 时，检查是否找到一个异位词
            if (r - l == p.length()) {
                if (need == 0) res.add(l); // 找到一个异位词，记录起始索引
                char leftChar = s.charAt(l);
                if (map.containsKey(leftChar)) {
                    // 大于等于0判断移出后是否是需要的字符
                    if (map.get(leftChar) >= 0)
                        need++;
                    map.put(leftChar, map.get(leftChar) + 1);
                }
                l++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 输入字符串 s
        String s = sc.nextLine();
        // 输入字符串 p
        String p = sc.nextLine();

        List<Integer> result = findAnagrams(s, p);
        System.out.println(result);
    }
}
