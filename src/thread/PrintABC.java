package thread;

import java.util.concurrent.Semaphore;

public class PrintABC {

    private final Semaphore semA = new Semaphore(1); // 信号量A设置为1,从A开始打印
    private final Semaphore semB = new Semaphore(0);
    private final Semaphore semC = new Semaphore(0);
    private static int n = 3;   // 打印轮次

    public static void main(String[] args) {
        PrintABC printer = new PrintABC();
        new Thread(()->printer.print('A',printer.semA,printer.semB)).start(); // 线程A打印，获取semA，释放semB
        new Thread(()->printer.print('B',printer.semB,printer.semC)).start(); // 线程B打印，获取semB，释放semC
        new Thread(()->printer.print('C',printer.semC,printer.semA)).start(); // 线程C打印，获取semC，释放semA
    }

    // 打印方法，参数ch表示要打印的字符，current表示当前线程需要获取的信号量，next表示下一个线程需要释放的信号量
    public void print(char ch, Semaphore current, Semaphore next) {
        try {
            for (int i = 0; i < n; i++) {
                current.acquire();  // 获取当前信号量
                System.out.println(Thread.currentThread().getName() + ": " + ch); // 打印字符
                next.release();     // 释放下一个信号量
            }
        } catch (InterruptedException e) {
            e.printStackTrace(); // 处理线程中断异常
        }
    }
}
