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

        // 构造方法,传入线程标识
        public Seq(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            // Run方法只要执行结束了,线程就结束了
            while (count < MAX) {
                // 同步代码块,一个时刻只能有一个线程获取到锁
                synchronized (LOCK) {
                    // count % 2 == 0时,应该由线程0打印; count % 2 == 1时,应该由线程1打印
                    while (count % 2 != index) {
                        // 不是当前线程打印, 让当前线程等待
                        try {
                            LOCK.wait();
                            // 当线程被唤醒时，会尝试重新进入synchronized代码块
                        } catch (Exception e) {
                            e.printStackTrace();// 处理异常
                        }
                    }
                    // 只有当count % 2 == index时,才会执行下面的代码,打印count
                    // 当count > MAX时,说明已经打印完了,唤醒其他线程让它们有机会结束
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

// 两线程交替打印数组
class TwoThreadPrintArray {
    private static final Object LOCK = new Object();
    private static int index = 0; // 共享变量,表示当前打印到数组的哪个位置
    private static final int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // 要打印的数组

    public static void main(String[] args) {
        Thread thread0 = new Thread(new Seq(0)); // 线程0打印偶数索引
        Thread thread1 = new Thread(new Seq(1)); // 线程1打印奇数索引
        thread0.start();
        thread1.start();
    }

    static class Seq implements Runnable {
        private final int mod; // 线程标识,0或1

        public Seq(int mod) {
            this.mod = mod;
        }

        @Override
        public void run() {
            while (index < arr.length) {
                synchronized (LOCK) {
                    while (index < arr.length && index % 2 != mod) {
                        try {
                            LOCK.wait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (index >= arr.length) {
                        LOCK.notifyAll();
                        return;
                    }
                    System.out.println("Thread-" + mod + ":" + arr[index]); // 打印当前索引的数组元素
                    index++;
                    LOCK.notifyAll();
                }
            }
        }
    }
}