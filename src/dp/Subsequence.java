package dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Subsequence {
    // 最长递增子序列
    // 输入一个整数数组，返回其中最长严格递增子序列的长度
    // 子序列可以不连续
    public static int lengthOfLIS(int[] nums) {
        int n = nums.length;
        // dp[i]表示以 nums[i] 结尾的最长递增子序列的长度
        int[] dp = new int[n];
        Arrays.fill(dp, 1); // 每个元素自身可以构成长度为1的递增子序列
        int res = 1; // 最小的递增子序列长度为1
        for(int i = 1; i < n; i++){
            for(int j = 0; j < i;j++){
                // dp[i] 以nums[i]结尾那么前一个元素来自i之前且比nums[i]小的元素
                if(nums[j] < nums[i]) {
                    // 位置i的最长升序子序列等于j从0到i-1各个位置的最长升序子序列 + 1 的最大值
                    dp[i] = Math.max(dp[i],dp[j]+1);
                }
            }
            res = Math.max(res,dp[i]);
        }
        return res;
    }

    // 如果要求返回最长递增子序列本身，而不仅仅是长度
    // 可以在dp数组的基础上维护一个prev数组记录前驱下标，最后通过反向构造路径得到最长递增子序列
    public static List<Integer> lengthOfLISWithPath(int[] nums) {
        int n = nums.length;

        int[] dp = new int[n];
        int[] prev = new int[n];  // prev[i]记录以nums[i]结尾的最长递增子序列中，nums[i]的前一个元素的下标

        Arrays.fill(dp, 1);
        Arrays.fill(prev, -1); // 初始化prev为-1，表示没有前驱

        int maxLen = 1; // 最长递增子序列的长度至少为1
        int endIndex = 0; // 记录最长递增子序列的最后一个元素的下标

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;   // 更新prev[i]为j，表示nums[j]是nums[i]的前一个元素
                }
            }
            // 如果当前的最长递增子序列长度大于全局最大长度
            if (dp[i] > maxLen) {
                maxLen = dp[i]; // 更新全局最大长度
                endIndex = i; // 更新最长递增子序列最后一个元素的下标
            }
        }

        // 反向构造 LIS
        List<Integer> path = new ArrayList<>();
        while (endIndex != -1) {
            path.add(nums[endIndex]); // 将当前元素加入路径
            endIndex = prev[endIndex]; // 更新endIndex为前一个元素的下标，继续向前追溯
        }

        Collections.reverse(path);
        return path;
    }


    // 最长递增子序列可使用贪心+二分法优化至O(nlogn)
    // 贪心思想：尽可能让每个长度的递增子序列的末尾元素小一些，这样后续添加新元素时才有更大概率接在后面形成更长的递增子序列
    // 维护一个tails数组，tails[i]表示长度为i+1的递增子序列，其末尾元素的最小值
    // 遍历数组nums，对于每个元素x，使用二分法在tails数组中查找第一个大于等于x的元素位置idx
    public static int lengthOfLISOptimized(int[] nums) {
        int n = nums.length;
        int[] tails = new int[n]; // tails数组初始化
        // tails[0]表示长度为1的递增子序列的末尾元素最小值，tails[1]表示长度为2的递增子序列的末尾元素最小值，依此类推
        int size = 0; // 当前LIS的最大长度

        for (int x : nums) {
            int l = 0, r = size;
            // 在tails[0..size]中二分查找第一个大于等于x的元素位置
            // 在已有的递增子序列中寻找合适的位置替换
            while (l < r) {
                int mid = (l + r) >>> 1; // 无符号右移，等同于 (l + r) / 2
                if (tails[mid] < x) {
                    l = mid + 1;
                } else {
                    r = mid;
                }
            }
            // l 是第一个 >= x 的位置
            tails[l] = x;
            if (l == size) size++;
        }
        // tails不一定是最终的最长递增子序列，但size是其长度，替换只发生在同样长度的子序列中
        return size;
    }

    // 最长连续递增子序列
    public static int findLengthOfLCIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp,1);

        for(int i = 1;i<n;i++){
            if(nums[i-1] < nums[i]){
                dp[i] = dp[i-1] + 1;
            }
        }
        int ans = 0;
        for(int x : dp){
            ans = Math.max(x,ans);
        }
        return ans;
    }

    // 两个数组的最大长度重复子数组
    // 子数组要求连续
    public static int findLength(int[] nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;
        int res = 0;

        // i-1和j-1是为了方便处理边界情况
        // dp[i][j]表示以nums1[i-1]和nums2[j-1]结尾的最长「连续」公共子数组长度
        int[][] dp = new int[n1 + 1][n2 + 1];

        // 遍历时从 1 开始，这样就不需要单独处理边界情况
        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                // 只有当nums1[i-1] == nums2[j-1]时，才能构成重复子数组
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1; // 继续扩展之前的重复子数组
                    res = Math.max(res, dp[i][j]); // 更新结果
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        return res;
    }
    public static int findLength1d(int[] nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;
        int res = 0;

        // 使用一维数组优化空间
        int[] dp = new int[n2 + 1];

        for (int i = 1; i <= n1; i++) {
            // 需要从后向前遍历，避免覆盖之前的状态
            for (int j = n2; j >= 1; j--) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[j] = dp[j - 1] + 1;
                    res = Math.max(res, dp[j]);
                } else {
                    dp[j] = 0;
                }
            }
        }
        return res;
    }

    // 最长公共子序列
    // 输入两个字符串，返回它们的最长公共子序列的长度
    // 子序列不要求连续
    public static int longestCommonSubsequence(String text1, String text2) {
        int n1 = text1.length();
        int n2 = text2.length();

        // dp[i][j]表示以text1[i-1]和text2[j-1]结尾的最长公共子序列长度
        int[][] dp = new int[n1 + 1][n2 + 1];

        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                if (text1.charAt(i-1) == text2.charAt(j-1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }else{
                    // 此题与最长重复子数组不同之处在于，子序列不要求连续
                    // 所以当不相等时，取dp[i-1][j]和dp[i][j-1]的最大值
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[n1][n2];
    }
    // 两个数组间的最大连线数
    // 可以看作是最长公共子序列问题
    public static int maxUncrossedLines(int[] nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;

        int[][] dp = new int[n1 + 1][n2 + 1];

        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[n1][n2];
    }

    // 最大子数组和
    // 输入一个整数数组，找到一个具有最大和的连续子数组，返回其和
    public static int maxSubArray(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int res = nums[0];
        dp[0] = nums[0];
        for(int i = 1;i<n;i++){
            // 以nums[i]结尾的最大子数组和，要么是包含前面的子数组，要么重新开始
            dp[i] = Math.max(dp[i-1] + nums[i],nums[i]);
            res = Math.max(dp[i],res);
        }

        return res;
    }
    // 改进，如果本体要求返回最大子数组
    public static int[] maxSubArrayUpdate(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int res = nums[0];
        dp[0] = nums[0];

        int curStart = 0;     // 当前子数组起点
        int bestStart = 0;   // 最优子数组起点
        int bestEnd = 0;     // 最优子数组终点

        for(int i = 1;i<n;i++){
            // 是否重新开始
            if (dp[i - 1] + nums[i] >= nums[i]) {
                dp[i] = dp[i - 1] + nums[i];
            } else {
                dp[i] = nums[i];
                curStart = i;   //  从 i 重新开始
            }

            // 更新全局最优
            if (dp[i] > res) {
                res = dp[i];
                bestStart = curStart;
                bestEnd = i;
            }
        }

        return Arrays.copyOfRange(nums, bestStart, bestEnd + 1);
    }

    // 判断子序列
    // 给定字符串 s 和 t ，判断 s 是否为 t 的子序列
    public static boolean isSubsequence(String s, String t) {
        int n1 = s.length();
        int n2 = t.length();
        // dp[i][j]表示以i-1和j-1结尾的s和t的最长公共子序列长度
        int[][] dp = new int[n1+1][n2+1];
        for(int i = 1;i<=n1;i++){
            for(int j = 1;j<=n2;j++){
                // 如果相等，说明可以扩展公共子序列
                if(s.charAt(i-1)==t.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else{
                    // 不相等相当于t要删除元素，所以取dp[i][j-1]
                    dp[i][j] = dp[i][j-1];
                }
            }
        }
        if(dp[n1][n2] == n1) return true;
        return false;
    }

    // 不同的子序列
    // 给定一个字符串 s 和一个字符串 t ，计算在 s 的子序列中 t 出现的个数
    public static int numDistinct(String s, String t) {
        int n1 = s.length();
        int n2 = t.length();
        if (n1 < n2) return 0;
        // dp[i][j]表示以i-1为结尾的s子序列中出现以j-1为结尾的t的个数为dp[i][j]
        int[][] dp = new int[n1 + 1][n2 + 1];
        // 初始化第一列，t为空字符串时，s的任意子序列都包含空字符串
        for (int i = 0; i < n1; i++) {
            dp[i][0] = 1;
        }
        // 初始化第一行，s为空字符串时，无法包含非空t，为0，java默认初始化为0，无需显式赋值
        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    // 当s和t的当前字符相等时，可以选择匹配或者不匹配
                    // 匹配：dp[i-1][j-1]，使用s的当前字符匹配t的当前字符，此时i和j都向前移动一位
                    // 不匹配：dp[i-1][j]，不使用s的当前字符，继续在s的前i-1个字符中匹配t的前j个字符
                    dp[i][j] = dp[i - 1][j - 1] + dp[i-1][j];
                }else{
                    // 当s和t的当前字符不相等时，s的当前字符无法匹配t的当前字符
                    // 只能选择不匹配：dp[i-1][j]
                    dp[i][j] = dp[i-1][j];
                }
                // s可以删，t不可以删，因此不能取dp[i][j-1]
            }
        }
        return dp[n1][n2];
    }

    // 两个字符串的删除操作
    // 给定两个单词 word1 和 word2，找到使得 word1 和 word2 相同所需的最小步数
    // 每步可以删除任意一个字符串中的一个字符
    public static int minDistance(String word1, String word2) {
        int n1 = word1.length();
        int n2 = word2.length();
        // dp[i][j]表示以i-1和j-1结尾的word1和word2的最小删除步数
        int[][] dp = new int[n1+1][n2+1];
        for(int i = 0;i<=n1;i++){
            dp[i][0] = i; // word2为空字符串时，删除word1的所有字符
        }
        for(int j = 0;j<=n2;j++){
            dp[0][j] = j; // word1为空字符串时，删除word2的所有字符
        }
        for(int i = 1;i<=n1;i++){
            for(int j = 1;j<=n2;j++){
                if(word1.charAt(i-1)==word2.charAt(j-1)){
                    // 如果相等，则不需要删除，继承之前的状态
                    dp[i][j] = dp[i-1][j-1];
                }else{
                    // 不相等时，删除word1的当前字符或者删除word2的当前字符，取最小值加1
                    dp[i][j] = Math.min(dp[i-1][j]+1,dp[i][j-1]+1);
                }
            }
        }
        return dp[n1][n2];
    }
    // 同样该题目可以用最长公共子序列来解决，求出最长公共子序列长度lcs，然后用两个字符串长度之和减去2*lcs，即为最小删除步数
    public static int minDistanceLCS(String word1, String word2) {
        int n1 = word1.length();
        int n2 = word2.length();

        int[][] dp = new int[n1 + 1][n2 + 1];

        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        int lcs = dp[n1][n2];
        return n1 + n2 - 2 * lcs;
    }

    // 编辑距离
    // 给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。
    // 你可以对一个单词进行如下三种操作：
    // 插入一个字符，删除一个字符，替换一个字符
    public static int EditDistance(String word1, String word2) {
        int n1 = word1.length();
        int n2 = word2.length();
        // dp[i][j]表示以i-1和j-1结尾的word1到word2的最小编辑距离
        int[][] dp = new int[n1 + 1][n2 + 1];
        for (int i = 0; i <= n1; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n2; j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= n1; i++) {
            for (int j = 1; j <= n2; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    // 如果相等，则不需要编辑，继承之前的状态
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 不相等时，考虑插入、删除、替换三种操作，取最小值加1
                    // 插入：dp[i][j-1]，在word1中插入word2的当前字符，插入后word2的最后一个字符被匹配，而word1长度不变
                    // 删除：dp[i-1][j]，删除word1的当前字符，word1长度从i变为i-1
                    // 替换：dp[i-1][j-1]，将word1的当前字符替换为word2的当前字符，然后两个字符串长度都变为i-1和j-1
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp[n1][n2];
    }

    // 回文子串个数
    // 给你一个字符串 s ，请你统计并返回这个字符串中 回文子串 的数目。
    public static int countSubstrings(String s) {
        int n = s.length();
        // dp[i][j]表示子串s[i..j]是否为回文子串
        boolean[][] dp = new boolean[n][n];
        // dp初始化java默认false，无需显式赋值
        int res = 0;

        // i 必须从后往前遍历，保证 dp[i+1][j-1] 已经计算好
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {

                if (s.charAt(i) == s.charAt(j)) {
                    // 情况 1：单字符
                    // 情况 2：长度为 2 的子串
                    if (j - i <= 1) {
                        dp[i][j] = true;
                    }
                    // 情况 3：长度 >= 3，依赖内部区间
                    else {
                        // dp[i][j] 取决于 dp[i+1][j-1]
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
                // 统计回文子串个数
                if (dp[i][j]) res++;
            }
        }
        return res;
    }

    // 最长回文子序列
    // 给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
    // 子序列可以不连续，而子串要求连续
    public static int longestPalindromeSubseq(String s) {
        int n = s.length();
        // 区间i到j最长的回文子序列长度
        int[][] dp = new int[n][n];
        // 初始化单个字符的回文子序列长度为1
        for (int i = 0; i < n; i++)
            dp[i][i] = 1;

        // i从后往前遍历，j从i+1往后遍历,j从i+1开始是因为j必须大于i，而j=i时表示单个字符，已经初始化过了
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    // 两端字符相等，则回文子序列长度为内部区间加2
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    // 两端字符不等，则取去掉左端或右端字符后的区间的最大值
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }

    // 最长回文子串
    // 给你一个字符串 s，找到 s 中最长的回文子串。
    public static String longestPalindrome(String s) {
        int n = s.length();
        if (n < 2) return s;

        boolean[][] dp = new boolean[n][n];

        int start = 0;     // 最长回文子串的起点
        int maxLen = 1;    // 最长回文子串的长度（至少是 1）

        // i 从后往前，保证 dp[i+1][j-1] 已经算过
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {

                if (s.charAt(i) == s.charAt(j)) {
                    // 长度为 1 或 2，必然是回文
                    if (j - i <= 1) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                // 如果是回文，并且更长，更新答案
                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    start = i;
                }
            }
        }

        return s.substring(start, start + maxLen);
    }


    public static void main(String[] args) {
        int[] nums = {10,9,2,5,3,7,101,18};
        System.out.println("lengthOfLIS = " + lengthOfLIS(nums)); // 4
        System.out.println("lengthOfLISOptimized = " + lengthOfLISOptimized(nums)); // 4

        int[] nums2 = {1,3,5,4,7};
        System.out.println("findLengthOfLCIS = " + findLengthOfLCIS(nums2)); // 4

        int[] nums1 = {1,2,3,2,1};
        int[] nums3 = {3,2,1,4,7};
        System.out.println("findLength = " + findLength(nums1, nums3)); // 3
        System.out.println("findLength1d = " + findLength1d(nums1, nums3)); // 3

        String text1 = "abcde", text2 = "ace";
        System.out.println("longestCommonSubsequence = " + longestCommonSubsequence(text1, text2)); // 3

        int[] nums4 = {1,4,2}, nums5 = {1,2,4};
        System.out.println("maxUncrossedLines = " + maxUncrossedLines(nums4, nums5)); // 2

        int[] nums6 = {-2,1,-3,4,-1,2,1,-5,4};
        System.out.println("maxSubArray = " + maxSubArray(nums6)); // 6
        int[] maxSubArray = maxSubArrayUpdate(nums6);
        System.out.println("maxSubArrayUpdate = " + Arrays.toString(maxSubArray));

        String s = "abc", t = "ahbgdc";
        System.out.println("isSubsequence = " + isSubsequence(s, t)); // true

        String s1 = "rabbbit", t1 = "rabbit";
        System.out.println("numDistinct = " + numDistinct(s1, t1)); // 3

        String word1 = "sea", word2 = "eat";
        System.out.println("minDistance = " + minDistance(word1, word2)); // 2
        System.out.println("minDistanceLCS = " + minDistanceLCS(word1, word2)); // 2

        String w1 = "intention", w2 = "execution";
        System.out.println("EditDistance = " + EditDistance(w1, w2)); // 5

        String str = "aaa";
        System.out.println("countSubstrings = " + countSubstrings(str)); // 6

        String pstr = "bbabcbcab";
        System.out.println("longestPalindromeSubseq = " + longestPalindromeSubseq(pstr)); // 7

        String pstr2 = "babad";
        System.out.println("longestPalindrome = " + longestPalindrome(pstr2)); // "bab" or "aba"
    }
}
