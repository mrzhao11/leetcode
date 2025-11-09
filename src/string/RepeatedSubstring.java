package string;
import java.util.*;

// 判断某个字符串是否由重复字符串组成
public class RepeatedSubstring {
//    // solution 1
//    //若 s 能由某个子串重复构成，则把 s 与自身拼接得到 t = s + s
//    // 去掉首尾各一个字符后，中间一定还会出现一次 s
//    public boolean repeatedSubstringPattern(String s) {
//        String t = s + s;
//        // 去掉首尾各1个字符，防止匹配到原始 s 的两个端点
//        return t.substring(1, t.length() - 1).contains(s);
//    }
    // solution 2
    // KMP
    public static void getNext(int[] next,String s){
        int j = 0;
        next[0] = 0;
        for(int i = 1;i<s.length();i++){
            while(j>0 && s.charAt(i)!=s.charAt(j)){
                j = next[j-1];
            }
            if(s.charAt(i)==s.charAt(j)){
                j++;
            }
            next[i] = j;
        }
    }
    // 如果这个字符串s是由重复子串组成，那么最长相等前后缀不包含的子串是字符串s的最小重复子串。
    // p p p p
    public static boolean repeatedSubstringPattern(String s) {
        int n = s.length();
        int[] next = new int[s.length()];
        getNext(next,s);
        int l = next[n-1]; // 整个串s的最长相等前后缀长度，即边界
        int period = n - l; // 周期
        // 若 L > 0 ，说明 s 有周期 p = n − L。
        // 若 n 能被 p 整除，说明可平铺若干个完整周期而无残余。
        return l > 0 && n % period == 0;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String ans = sc.nextLine();
        System.out.println(repeatedSubstringPattern(ans));
    }
}
