package hashtable;


import java.util.*;

public class GroupAnagrams {
    // 字母异位词分组
    // 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
    public static List<List<String>> groupAnagrams(String[] strs) {
        // Map 存储 分组结果，key 为排序后的字符串，value 为对应的原字符串列表
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            // 1. 排序字符串，作为 key
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);

            // 2. 如果 key 不存在，先创建一个空列表
            map.putIfAbsent(key, new ArrayList<>());

            // 3. 把原字符串加入对应分组
            List<String> list = map.get(key);
            list.add(str);

        }

        // 4. 收集结果
        List<List<String>> res = new ArrayList<>();
        for (List<String> group : map.values()) {
            res.add(group);
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String[] strs = new String[n];
        for (int i = 0; i < n; i++) {
            strs[i] = sc.next();
        }
        List<List<String>> res = groupAnagrams(strs);
        System.out.println(res);
        // 测试用例：
        // 输入：
        // 6
        // eat
        // tea
        // tan
        // ate
        // nat
        // bat
        // 输出：
        // [[eat, tea, ate], [tan, nat], [bat]]
    }
}
