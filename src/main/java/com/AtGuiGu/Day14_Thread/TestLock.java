package com.AtGuiGu.Day14_Thread;

/**
 *
 *  例子：创建三个窗口卖票，总票数为100张.使用实现Runnable接口的方式
 *
 *  1.问题：卖票过程中，出现了重票、错票 -->出现了线程的安全问题
 *  2.问题出现的原因：当某个线程操作车票的过程中，尚未操作完成时，其他线程参与进来，也操作车票。
 *  3.如何解决：当一个线程a在操作ticket的时候，其他线程不能参与进来。直到线程a操作完ticket时，其他
 *             线程才可以开始操作ticket。这种情况即使线程a出现了阻塞，也不能被改变。
 *
 *
 *  4.在Java中，我们通过同步机制，来解决线程的安全问题。
 *
 *   方式一：同步代码块
 *
 *    synchronized(同步监视器){
 *       //需要被同步的代码
 *
 *    }
 *   说明：1.操作共享数据的代码，即为需要被同步的代码。  -->不能包含代码多了，也不能包含代码少了。
 *        2.共享数据：多个线程共同操作的变量。比如：ticket就是共享数据。
 *        3.同步监视器，俗称：锁。任何一个类的对象，都可以充当锁。
 *           要求：多个线程必须要共用同一把锁。
 *
 *        补充：在实现Runnable接口创建多线程的方式中，我们可以考虑使用this充当同步监视器。
 *   方式二：同步方法。
 *      如果操作共享数据的代码完整的声明在一个方法中，我们不妨将此方法声明同步的。
 *
 *
 *   5.同步的方式，解决了线程的安全问题。---好处
 *     操作同步代码时，只能有一个线程参与，其他线程等待。相当于是一个单线程的过程，效率低。 ---局限性
 *
 *   使用同步方法解决实现Runnable接口的线程安全问题
 *  关于同步方法的总结：
 *  1. 同步方法仍然涉及到同步监视器，只是不需要我们显式的声明。
 *  2. 非静态的同步方法，同步监视器是：this
 *     静态的同步方法，同步监视器是：当前类本身
 *
 * @author Chaoqun Cheng
 * @date 2021-04-2021/4/22-11:42
 */

public class TestLock {
    public static void main(String[] args) {
        Window1 w = new Window1();

        Thread t1 = new Thread(w);
        Thread t2 = new Thread(w);
        Thread t3 = new Thread(w);

        t1.setName("窗口1");
        t2.setName("窗口2");
        t3.setName("窗口3");

        t1.start();
        t2.start();
        t3.start();
    }
}

//Runnable
class Window1 implements Runnable{
    private int ticket = 100;
    //    Object obj = new Object();
//    Dog dog = new Dog();
    @Override
    public  void run() {
        //这里每一个线程的run方法都创建了一个Object对象 不共用一个锁还是会出现线程安全问题
//        Object obj = new Object();
        while(true){
            synchronized (this){//此时的this:唯一的Window1的对象   //方式二：synchronized (dog) {
                if (ticket > 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ":卖票，票号为：" + ticket);
                    ticket--;
                } else {
                    break;
                }
            }
        }
    }
}

//Extends Thread
class Window2 extends Thread{
    Class clazz = Window2.class;
    private static int ticket = 100;
    private static Object obj = new Object();
    @Override
    public void run() {
        while(true){
            //正确的
//            synchronized (obj){
            synchronized (Window2.class){//Class clazz = Window2.class,Window2.class只会加载一次
                //错误的方式：this代表着t1,t2,t3三个对象
//              synchronized (this){
                if(ticket > 0){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(getName() + "：卖票，票号为：" + ticket);
                    ticket--;
                }else{
                    break;
                }
            }
        }
    }
}

//将同步的代码块包装成一个方法并用synchronized修饰 表示整个方法同步
//实际上方法内的代码隐式的synchronized{}包括起来
//并且这里的锁是this即当前类的对象,
// 而实现了Runnable接口,当前对象只有一个, 创建不同线程传递到Thread的对象唯一,所以this相同,所以锁也相同
class Window3 implements Runnable {
    private int ticket = 100;
    @Override
    public void run() {
        while (true) {
            show();
        }
    }

    private synchronized void show(){//同步监视器：this
        //synchronized (this){
        if (ticket > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ":卖票，票号为：" + ticket);
            ticket--;
        }
        //}
    }
}

//Extends Thread
//同样对需要同步的代码块封装到一个方法中,
//如果只用synchronized关键字修饰方法, 锁为this,指向的是当前对象
//但是创建线程的时候,每一个线程都创建了一个对象,每个对象都不同,所以不共用锁, 所以存在线程安全问题
//这里需要把当前方法声明为static,因为这样这个方法随着类的加载而加载,且所有类的对象共有一个,
//这里类加载时还没有对象,所以这里synchronized的锁使用的是当前类 相当于Window4.class
//而类只加载一次,对于所有的对象只有一个,所以所有的对象共用一个锁
class Window4 extends Thread {
    private static int ticket = 100;
    @Override
    public void run() {
        while (true) {
            show();
        }
    }
    private static synchronized void show(){//同步监视器：Window4.class
        //private synchronized void show(){ //同步监视器：t1,t2,t3。此种解决方式是错误的
        if (ticket > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "：卖票，票号为：" + ticket);
            ticket--;
        }
    }
}

class Dog{

}