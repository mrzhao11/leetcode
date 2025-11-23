package greedy;

public class GasStation {
    // 在一条环路上有 N 个加油站，第 i 个加油站有汽油 gas[i] 升。
    // 你有一辆油箱容量无限的汽车，从第 i 个加油
    // 站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。
    // 你从其中的一个加油站出发，开始时油箱为空。
    // 如果你可以绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1。
    // 说明: 如果题目有解，该答案即为唯一答案。
    public static int canCompleteCircuit(int[] gas, int[] cost) {

        int curSum = 0; // 当前油量
        int totalSum = 0; // 总油量
        int start = 0; // 起始位置
        for(int i = 0; i< gas.length;i++){
            curSum += gas[i] - cost[i];
            totalSum += gas[i] - cost[i];
            // 当前油量不足，不能从start出发到达i+1位置
            if(curSum < 0) {
                start = i + 1; // 更新起始位置为i+1
                curSum = 0; // 重置当前油量
            }
        }
        if(totalSum < 0) return -1; // 总油量不足，无法完成环路
        return start;
    }

    public static void main(String[] args) {
        int[] gas = {1,2,3,4,5};
        int[] cost = {3,4,5,1,2};
        System.out.println(canCompleteCircuit(gas, cost)); // 输出 3
    }
}
