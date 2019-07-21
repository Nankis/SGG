package jvm;

import java.util.concurrent.TimeUnit;

class Resource implements Runnable {
    private String lockA;
    private String lockB;
    public Resource(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }
    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有的:" + lockA + "\t 尝试获得:" + lockB);
            //为了效果明显 睡一会儿
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 自己持有的:" + lockB + "\t 尝试获得:" + lockA);
            }
        }
    }
}

public class DeadLock {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new Resource(lockA, lockB), "ThreadAAA").start();
        new Thread(new Resource(lockB, lockA), "ThreadBBB").start();
    }
}
