package jmm;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class myData {
    //    int number = 0;
    volatile int number = 0;  //加上了volatile 保证线程之间的可见性

    public void addTo60() {
        this.number = 60;
    }

    public void addSelf() {
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void addMyAtomic() {
        atomicInteger.getAndIncrement();  //+1
    }
}

public class volatileTest {
    /**
     * 1.验证volatile的可见性
     *      1.1 假设int number = 0; number 变量没添加volatile之前没有可见性
     * <p>
     * 2. 验证volatile 不保证原子性(整体要么全部成功,要么全部失败) ,需要用volatile修饰验证
     *     2.1 不保证原子性的原因
     *      number++编译后会变成4条指令,而线程之间的过快的存储操作会导致数据覆盖
     *    2.2 解决方案
     *      1.添加synchronized   (自增操作不推荐---杀鸡不能用牛刀)
     *      2.用JUC下的原子类
     *
     * @param args
     */
    public static void main(String[] args) {
        myData myData = new myData();

//    seeAble();

        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.addSelf();
                    myData.addMyAtomic();
                }
            }, String.valueOf(i)).start();
        }

        //等待上面20个线程全部执行完,再看计算结果是否与预期的20*1000相符
        while (Thread.activeCount() > 2) { //当后台的线程多于2个的时候,当前的线程作出退让(礼让线程)
            //之所以是2 是main和GC线程
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + "\t int type, finally number value:" + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t AtomicInteger type finally number value:" + myData.atomicInteger);

    }


    //可见性验证 volatile保证可见性---能及时通知其他线程,主内存的值已被修改
    private static void seeAble() {
        myData myData = new myData();  //资源类

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();  //延迟3s后,改值
            System.out.println(Thread.currentThread().getName() + "\t updated number 60");
        }, "AAA").start();

        //第二个线程是main线程
        while (myData.number == 0) {
            //如果没有可见性保证,那么main线程拿到的number会一直是0,所以在这里是死循环
        }

        //如果跳出循环,则会运行到这步
        System.out.println(Thread.currentThread().getName() + "\t mission is over.current number is " + myData.number);
    }

}

