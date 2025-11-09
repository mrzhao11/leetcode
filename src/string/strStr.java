package string;

// 给你两个字符串 haystack 和 needle
// 请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0 开始）
// 如果 needle 不是 haystack 的一部分，则返回  -1 。
// 经典KMP算法

import java.util.*;

public class strStr {
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
    public static int strSTR(String haystack, String needle){
        if(needle.length()==0) return -1;
        int[] next = new int[needle.length()];
        getNext(next,needle);
        int j = 0;
        for(int i = 0;i<haystack.length();i++){
            while(j>0&&haystack.charAt(i)!=needle.charAt(j)){
                j = next[j-1];
            }
            if(haystack.charAt(i)==needle.charAt(j)){
                j++;
            }
            if(j == needle.length()){
                return i - needle.length()+1;
            }
        }
        return -1;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String h = sc.nextLine();
        String n = sc.nextLine();
        System.out.println(strSTR(h,n));
    }
}
