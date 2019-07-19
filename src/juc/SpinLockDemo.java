package juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLockDemo {
    /**
     * 要求手动实现一个自旋锁
     */
    //原子引用线程
    AtomicReference<Thread> atomicReference = new AtomicReference<>();


    public void myLock() {
        Thread thread = Thread.currentThread(); //当前进来的线程
        System.out.println(thread.getName() + " \t come in...");
        do {
            //模仿CAS的实现
        } while (!atomicReference.compareAndSet(null, thread));
    }
    public void myUnLock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(thread.getName()+ " \t invoke myUnLock()");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        new Thread(()->{
            spinLockDemo.myLock();
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnLock();
        },"AA").start();

        new Thread(()->{
            spinLockDemo.myLock();
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnLock();
        },"BB").start();


    }

}
