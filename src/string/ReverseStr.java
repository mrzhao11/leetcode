package string;

import java.util.*;

public class ReverseStr {
    public static String reverseStr(String s, int k) {
        int p = 0;
        while (p < s.length()) {
            int end;
            if (p + k - 1 < s.length()) {
                end = p + k - 1;
            } else {
                end = s.length() - 1;
            }
            s = reverse(s, p, end);
            p = p + 2 * k;
        }
        return s;
    }

    public static String reverse(String s, int a, int b) {
        char[] arr = s.toCharArray();
        int l = a;
        int r = b;
        while (l < r) {
            char temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            l++;
            r--;
        }
        return new String(arr);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // 输入字符串
        String s = sc.next();
        // 输入 k
        int k = sc.nextInt();

        String result = reverseStr(s, k);
        System.out.println(result);
    }
}

