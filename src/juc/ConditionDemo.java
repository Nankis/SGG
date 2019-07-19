package juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareResource {
    private int number = 1; //线程 A:1 B:2 C:3
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            //1.判断
            while (number != 1) {
                c1.await();
            }
            //2.干活
            for (int i = 1; i <=5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t " + i);
            }
            //3.通知
            number = 2; //标志位先换掉
            c2.signal(); //相比于synchronized灵活的地方

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try {
            //1.判断
            while (number != 2) {
                c2.await();
            }
            //2.干活
            for (int i = 1; i <=10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t " + i);
            }
            //3.通知
            number = 3; //标志位先换掉
            c3.signal(); //相比于synchronized灵活的地方

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15() {
        lock.lock();
        try {
            //1.判断
            while (number != 3) {
                c3.await();
            }
            //2.干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t " + i);
            }
            //3.通知
            number = 1; //标志位先换掉
            c1.signal(); //相比于synchronized灵活的地方

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}

public class ConditionDemo {
    /**
     * 题目：一个初始值为零的变量，两个线程对其交替操作，广个加一个1，来5轮
     * //线程操纵资源类,判断,干活,唤醒,通知,严防多线程状态下的虚假唤醒
     * //题月：synchronized和lock有什么区别？用新的Lock有什么好处？你举例说说
     * ----------------------------------------------重点
     * //题日：多线程之间按顺序调用，实现A->B->C三个线程启动，要求如下：
     * //AA打印5次，BB打10次，CC打15次
     * //紧接着
     * //AA打印5次，BB打10，CC打印15
     * //来10轮
     */

    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareResource.print5();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareResource.print10();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                shareResource.print15();
            }
        }, "C").start();

    }


}
