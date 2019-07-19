package juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicbarrierDemo {
    /**
     * 当指定的线程都执行完成后,才会触发想要执行的函数
     * 类比集齐7颗龙珠才能召唤神龙.
     *
     * @param args
     */

    public static void main(String[] args) {
        //cyclicBarrier(int parties,Runnable barrierAction)
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("********召唤神龙!");
        });

        for (int i = 1; i <= 7; i++) {
            final int tempInt = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 收集到第: " + tempInt + "号龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }

    }

}
