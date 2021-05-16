package com.AtGuiGu.Day14_Thread;

/**
 *   线程通信的应用：经典例题：生产者/消费者问题
 *
 *   生产者(Productor)将产品交给店员(Clerk)，而消费者(Customer)从店员处取走产品，
 *   店员一次只能持有固定数量的产品(比如:20），如果生产者试图生产更多的产品，店员
 *   会叫生产者停一下，如果店中有空位放产品了再通知生产者继续生产；如果店中没有产品
 *   了，店员会告诉消费者等一下，如果店中有产品了再通知消费者来取走产品。
 *
 *   分析：
 *   1. 是否是多线程问题？是，生产者线程，消费者线程
 *   2. 是否有共享数据？是，店员（或产品）
 *   3. 如何解决线程的安全问题？同步机制,有三种方法
 *   4. 是否涉及线程的通信？是
 * @author Chaoqun Cheng
 * @date 2021-04-2021/4/22-13:29
 */

public class TestConsumerProducer {
    public static void main(String[] args) {
        Clerk clerk = new Clerk(0);

        Producer p1 = new Producer(clerk);
        p1.setName("生产者1");

        Consumer c1 = new Consumer(clerk);
        c1.setName("消费者1");
        Consumer c2 = new Consumer(clerk);
        c2.setName("消费者2");

        p1.start();
        c1.start();
        c2.start();

    }
}


class Clerk{
    public int goods;

    public Clerk(int goods){
        this.goods = goods;
    }


    public synchronized void consume() {
        if(goods>0){
            System.out.println(Thread.currentThread().getName()+": 消费第 "+goods+" 个商品");
            goods--;
            notify();
        }else{
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public synchronized void produce() {
        if(goods<20){
            goods++;
            System.out.println(Thread.currentThread().getName()+": 生产第 "+goods+" 个商品");
            notify();
        }else{
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

class Producer extends Thread{
    private Clerk clerk;

    public Producer(Clerk clerk){
        this.clerk = clerk;

    }

    @Override
    public void run() {
        while(true){

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }

            clerk.produce();
        }
    }
}

class Consumer extends Thread{
    private Clerk clerk;

    public Consumer(Clerk clerk){
        this.clerk = clerk;

    }

    @Override
    public void run() {
        while(true){

            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }

            clerk.consume();
        }
    }
}