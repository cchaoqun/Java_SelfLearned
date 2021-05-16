package com.AtGuiGu.Day14_Thread;

import java.util.concurrent.*;

/**
 * 创建线程的方式三：实现Callable接口。 --- JDK 5.0新增
 *
 *
 *  如何理解实现Callable接口的方式创建多线程比实现Runnable接口创建多线程方式强大？
 *  1. call()可以有返回值的。
 *  2. call()可以抛出异常，被外面的操作捕获，获取异常的信息
 *  3. Callable是支持泛型的
 *
 *
 *   * 创建线程的方式四：使用线程池
 *  *
 *  * 好处：
 *  * 1.提高响应速度（减少了创建新线程的时间）
 *  * 2.降低资源消耗（重复利用线程池中线程，不需要每次都创建）
 *  * 3.便于线程管理
 *  *      corePoolSize：核心池的大小
 *  *      maximumPoolSize：最大线程数
 *  *      keepAliveTime：线程没有任务时最多保持多长时间后会终止
 *  *
 *  *
 *  * 面试题：创建多线程有几种方式？四种！
 * @author Chaoqun Cheng
 * @date 2021-04-2021/4/22-13:40
 */

public class TestCreateThread {
    public static void main(String[] args) {
        //1. 提供指定线程数量的线程池
        ExecutorService service = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor service1 = (ThreadPoolExecutor) service;
        //设置线程池的属性
//        System.out.println(service.getClass());
//        service1.setCorePoolSize(15);
//        service1.setKeepAliveTime();


        //2.执行指定的线程的操作。需要提供实现Runnable接口或Callable接口实现类的对象
        service.execute(new NumberThread());//适合适用于Runnable
        service.execute(new NumberThread1());//适合适用于Runnable

//        service.submit(Callable callable);//适合使用于Callable
        //3.关闭连接池
        service.shutdown();
    }
}


//1.创建一个实现Callable的实现类
class NumThread implements Callable {

    //2.实现call方法，将此线程需要执行的操作声明在call()中
    @Override
    public Object call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            if(i % 2 == 0){
                System.out.println(i);
                sum += i;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        //3.创建Callable接口实现类的对象
        NumThread numThread = new NumThread();
        //4.将此Callable接口实现类的对象作为传递到FutureTask构造器中，创建FutureTask的对象
        FutureTask futureTask = new FutureTask(numThread);
        //5.将FutureTask的对象作为参数传递到Thread类的构造器中，创建Thread对象，并调用start()
        new Thread(futureTask).start();

        try {
            //6.获取Callable中call方法的返回值
            //get()返回值即为FutureTask构造器参数Callable实现类重写的call()的返回值。
            Object sum = futureTask.get();
            System.out.println("总和为：" + sum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}


//线程池
class NumberThread implements Runnable{

    @Override
    public void run() {
        for(int i = 0;i <= 100;i++){
            if(i % 2 == 0){
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
        }
    }
}

class NumberThread1 implements Runnable{

    @Override
    public void run() {
        for(int i = 0;i <= 100;i++){
            if(i % 2 != 0){
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
        }
    }
}



