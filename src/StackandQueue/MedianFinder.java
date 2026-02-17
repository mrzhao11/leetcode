package StackandQueue;

import java.util.PriorityQueue;

// 数据流中的中位数
// 题目描述：中位数是有序列表中间的数。如果列表长度是偶数，则中位数是中间两个数的平均值。设计一个支持以下两种操作的数据结构：
// void addNum(int num) - 从数据流中添加一个整数到数据结构中
// double findMedian() - 返回目前所有元素的中位数
// 本题采用双堆（最大堆和最小堆）的方法来实现。
// 最大堆用于存储较小的一半元素，最小堆用于存储较大的一半元素。这样可以保证两个堆的大小差不超过1，从而能够快速计算中位数。
public class MedianFinder {
    PriorityQueue<Integer> min; // 最小堆，存储较大的一半元素
    PriorityQueue<Integer> max; // 最大堆，存储较小的一半元素

    public MedianFinder() {
        min = new PriorityQueue<>(); // 默认是最小堆
        max = new PriorityQueue<>((a, b) -> b - a); // 自定义比较器实现最大堆
    }

    public void addNum(int num) {
        max.offer(num); // 先将新元素添加到最大堆

        min.offer(max.poll()); // 将最大堆的堆顶元素移动到最小堆

        // 保持大顶堆的元素数量不小于小顶堆的元素数量
        if (max.size() < min.size()) {
            max.offer(min.poll()); // 如果最小堆元素多了，将最小堆的堆顶元素移动回最大堆
        }
    }

    public double findMedian() {
        if (max.size() > min.size()) {
            return max.peek(); // 最大堆元素多，返回最大堆的堆顶
        } else {
            return (max.peek() + min.peek()) / 2.0; // 两个堆大小相等，返回两个堆顶的平均值
        }
    }

}

class Main {
    public static void main(String[] args) {
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(1);
        medianFinder.addNum(2);
        System.out.println(medianFinder.findMedian()); // 输出 1.5
        medianFinder.addNum(3);
        System.out.println(medianFinder.findMedian()); // 输出 2.0
    }
}
