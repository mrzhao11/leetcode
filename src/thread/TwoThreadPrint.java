package thread;

// 2个线程交替打印0-100
public class TwoThreadPrint {

    private static final Object LOCK = new Object(); // 锁对象
    private static volatile int count = 0; // 共享变量
    private static final int MAX = 100;// 打印的最大值

    public static void main(String[] args) {
        Thread thread = new Thread(new Seq(0)); // 线程0打印偶数
        Thread thread1 = new Thread(new Seq(1)); // 线程1打印奇数
        thread.start();
        thread1.start();
    }

    static class Seq implements Runnable {
        private final int index; // 线程标识,0或1

        public Seq(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            // Run方法只要执行结束了,线程就结束了
            while (count < MAX) {
                // 同步代码块,一个时刻只能有一个线程获取到锁
                synchronized (LOCK) {
                    // 获取到锁就进来判断，当前是否轮到该线程打印
                    while (count % 2 != index) {
                        // 不是当前线程打印,那么就让当前线程去wait,它会自动释放锁,所以其他线程可以进来
                        try {
                            LOCK.wait();
                            // 当线程被唤醒时，会尝试重新进入synchronized代码块
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    // 是当前线程打印, 但count>MAX
                    if (count > MAX) {
                        LOCK.notifyAll(); // 唤醒其他线程,让它们有机会结束
                        return;
                    }
                    System.out.println("Thread-" + index + ":" + count); // 打印count
                    count++;
                    LOCK.notifyAll(); // 打印完后,唤醒其他线程
                }
            }
        }
    }
}