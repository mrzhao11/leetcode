package thread;

// 三个线程交替打印0-100
public class ThreeThreadPrint {

    private static final Object LOCK = new Object(); // 锁对象,所有线程共享
    private static volatile int count = 0; // 共享变量,所有线程共享,需要保证可见性和原子性
    private static final int MAX = 100; // 打印的最大值

    public static void main(String[] args) {
        Thread thread0 = new Thread(new Seq(0));
        Thread thread1 = new Thread(new Seq(1));
        Thread thread2 = new Thread(new Seq(2));
        thread0.start();
        thread1.start();
        thread2.start();
    }

    static class Seq implements Runnable {
        private final int index;

        public Seq(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (LOCK) {
                    // 只有当count % 3 == index时,才会执行下面的代码,打印count
                    while (count <= MAX && count % 3 != index) {
                        try {
                            LOCK.wait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // 当count > MAX时,说明已经打印完了,唤醒其他线程让它们有机会结束
                    if (count > MAX) {
                        LOCK.notifyAll();
                        break;
                    }

                    System.out.println("Thread-" + index + ":" + count);
                    count++;
                    LOCK.notifyAll();
                }
            }
        }
    }
}
