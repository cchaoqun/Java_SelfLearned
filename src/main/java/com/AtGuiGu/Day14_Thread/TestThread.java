package com.AtGuiGu.Day14_Thread;

/**
 *
 * 多线程的创建，方式一：继承于Thread类
 * 1. 创建一个继承于Thread类的子类
 * 2. 重写Thread类的run() --> 将此线程执行的操作声明在run()中
 * 3. 创建Thread类的子类的对象
 *      创建多个线程就创建该一个实现类的多个对象或者多个实现类的多个对象
 * 4. 通过此对象调用start()
 *      只能调用一次
 *      启动了线程并且调用了当前线程的run()
 *      直接通过线程对象调用run()方法并不会启动一个线程
 *
 *   测试Thread中的常用方法：
 *   1. start():启动当前线程；调用当前线程的run()
 *   2. run(): 通常需要重写Thread类中的此方法，将创建的线程要执行的操作声明在此方法中
 *   3. currentThread():静态方法，返回执行当前代码的线程
 *   4. getName():获取当前线程的名字
 *   5. setName():设置当前线程的名字
 *   6. yield():释放当前cpu的执行权
 *   7. join():在线程a中调用线程b的join(),此时线程a就进入阻塞状态，直到线程b完全执行完以后，线程a才
 *             结束阻塞状态。
 *   8. stop():已过时。当执行此方法时，强制结束当前线程。
 *   9. sleep(long millitime):让当前线程“睡眠”指定的millitime毫秒。在指定的millitime毫秒时间内，当前
 *                            线程是阻塞状态。
 *   10. isAlive():判断当前线程是否存活
 *
 *
 *   线程的优先级：
 *   1.
 *   MAX_PRIORITY：10
 *   MIN _PRIORITY：1
 *   NORM_PRIORITY：5  -->默认优先级
 *   2.如何获取和设置当前线程的优先级：
 *     getPriority():获取线程的优先级
 *     setPriority(int p):设置线程的优先级
 *
 *     说明：高优先级的线程要抢占低优先级线程cpu的执行权。但是只是从概率上讲，高优先级的线程高概率的情况下
 *     被执行。并不意味着只有当高优先级的线程执行完以后，低优先级的线程才执行。
 *
 *
 *
 * *创建多线程的方式二：实现Runnable接口
 *   1. 创建一个实现了Runnable接口的类
 *   2. 实现类去实现Runnable中的抽象方法：run()
 *   3. 创建实现类的对象
 *   4. 将此对象作为参数传递到Thread类的构造器中，创建Thread类的对象
 *   5. 通过Thread类的对象调用start()
 *
 *
 *   比较创建线程的两种方式。
 *   开发中：优先选择：实现Runnable接口的方式
 *   原因：1. 实现的方式没有类的单继承性的局限性
 *        2. 实现的方式更适合来处理多个线程有共享数据的情况。
 *
 *   联系：public class Thread implements Runnable
 *   相同点：两种方式都需要重写run(),将线程要执行的逻辑声明在run()中。
 *
 *
 * @author Chaoqun Cheng
 * @date 2021-04-2021/4/22-10:37
 */

//1. 创建一个继承于Thread类的子类
class MyThread extends Thread implements Runnable{
    //2. 重写Thread类的run()
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i % 2 == 0){
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }
    }
}


public class TestThread {
    public static void main(String[] args) {
        //3. 创建Thread类的子类的对象
        MyThread t1 = new MyThread();

        //获取当前线程的名称(main)
        Thread.currentThread().getName();

        //线程优先级 1-10
        Thread.currentThread().getPriority();
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        //4.通过此对象调用start():①启动当前线程 ② 调用当前线程的run()
        t1.start();
        //问题一：我们不能通过直接调用run()的方式启动线程。
//        t1.run();

        //问题二：再启动一个线程，遍历100以内的偶数。不可以还让已经start()的线程去执行。会报IllegalThreadStateException
//        t1.start();
        //我们需要重新创建一个线程的对象
        MyThread t2 = new MyThread();
        t2.start();

        //实现了runnable接口的类
        // 重写了的run方法,
        // 实例了对象
        // 传递到Thread()
        // 创建线程对象
        // 调用start
        MyThread2 t3 = new MyThread2();
        Thread t33 = new Thread(t3);
        t33.start();


        //如下操作仍然是在main线程中执行的。
        for (int i = 0; i < 100; i++) {
            if(i % 2 == 0){
                System.out.println(Thread.currentThread().getName() + ":" + i + "***********main()************");
            }
            if(i==20){
                //当前main线程阻塞等待t1执行完了再执行
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

class MyThread1 extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i%2==0){
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(this.getName()+": "+i);
            }
            if(i==20){
                //放弃当前时间片
                yield();
            }
        }
    }
    //构造器当前线程命名
    public MyThread1(String name){
        super(name);
    }
}

class MyThread2 implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if(i % 2 == 0){
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }
    }
}