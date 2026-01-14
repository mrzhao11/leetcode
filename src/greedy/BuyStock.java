package greedy;

public class BuyStock {
    // 122. 买卖股票的最佳时机 II
    // 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
    //在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。然而，你可以在 同一天 多次买卖该股票，但要确保你持有的股票不超过一股。
    //返回 你能获得的 最大 利润 。
    // 本题与 121. 买卖股票的最佳时机 类似，但你可以 多次 交易。
    // 如果只能进行一次交易，计算最大利润的方法是找到最低买入价和最高卖出价的差值。
    public static int maxProfit(int[] prices) {
        // 假如计算第一天买入，第三天卖出的利润
        // 3 - 1 ==> (2 - 1) + (3 - 2)
        // 可以把所有的上升区间的利润都加起来，得到最大利润
        int res = 0;
        if(prices.length == 1) return res;
        int[] sub = new int[prices.length-1];
        int idx = 0;
        for(int i = 1;i<prices.length;i++){
            sub[idx++] = prices[i] - prices[i-1];
        }
        for(int i = 0;i<sub.length;i++){
            if(sub[i] > 0) res += sub[i];
        }
        return res;
    }

   public static void main(String[] args) {
        int[] prices = {7,1,5,3,6,4};
        System.out.println(maxProfit(prices)); // 输出 7
    }
}
