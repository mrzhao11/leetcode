package thread;

public class ThreeThreadPrint {

    private static final Object LOCK = new Object();
    private static int count = 0;
    private static final int MAX = 100;

    public static void main(String[] args) {
        new Thread(new Seq(0), "T0").start();
        new Thread(new Seq(1), "T1").start();
        new Thread(new Seq(2), "T2").start();
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
                    while (count <= MAX && count % 3 != index) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    if (count > MAX) {
                        LOCK.notifyAll();
                        break;
                    }

                    System.out.println(Thread.currentThread().getName() + " -> " + count);
                    count++;
                    LOCK.notifyAll();
                }
            }
        }
    }
}
