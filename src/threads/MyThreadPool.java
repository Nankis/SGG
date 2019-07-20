package threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThreadPool {
    /**
     * 实现多线程的4种方式
     * 1.继承Thread类  ---> 单继承 拓展性差            逻辑写在run()方法里
     * 2.实现Runnable接口  ---> 没有返回值,也不抛异常  逻辑写在run()方法里
     * 3.实现Callable接口  ---> 有返回值,会抛异常      逻辑写在call()方法里
     * 4.通过线程池获取
     */

    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newFixedThreadPool(5); //一池固定线程
    }
}
