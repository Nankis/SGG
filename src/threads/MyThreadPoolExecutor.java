package threads;

import java.util.concurrent.*;

/**
 * 手写线程池
 */
public class MyThreadPoolExecutor {
    public static void main(String[] args) {
        /**
         * CPU密集的意思是该任务需要大量的运算，而没有阻塞，CPU一直全速运行。
         * CPU密集任务只有在真正的多核CPU上才可能得到加速（通过多线程），而在单核CPU上（悲剧吧？（；’x）），
         * 无论你开几个模拟的多线程该任务都不可能得到加速，因为CPU总的运算能力就那些。
         *
         * CPU密集型任务配置尽可能少的线程数量：一般公式：CPU核数+1个线程的线程池
         *
         * 由于I0密集型任务线程并不是一直在执行任务，则应配置尽可能多的线程，如cPU核数*2
         */
        System.out.println(Runtime.getRuntime().availableProcessors()); //cpu核心数


        ExecutorService threadPool = new ThreadPoolExecutor(
                2,   //核心线程数
                5,//线程池最大数
                1L,//多余corePoolSize可存活时间
                TimeUnit.SECONDS,//keepaliveTime的单位
                new LinkedBlockingQueue<>(3), //workQueue阻塞队列的数据结构,大小是3
                Executors.defaultThreadFactory(),//创建线程的方式,使用默认既可
                new ThreadPoolExecutor.AbortPolicy());//拒绝策略  默认AbortPolicy 当超过(maximumPoolSize+workQueue)时会抛出异常


        for (int i = 1; i <= 8; i++) {
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 办理业务");
            });

        }
    }
}
