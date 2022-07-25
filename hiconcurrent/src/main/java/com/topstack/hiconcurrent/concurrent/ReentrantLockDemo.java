package com.topstack.hiconcurrent.concurrent;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Administrator
 */
public class ReentrantLockDemo {


    public static void reentrantLockTest() {
        //demo1();
        //demo2();
        demo3();
    }

    /**
     * 演示 多个线程去竞争锁的 用法
     */
    private static void demo1() {
        final ReentrantLockTask1 task = new ReentrantLockTask1();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                task.buyTicket();
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }

    static class ReentrantLockTask1 {
        ReentrantLock reentrantLock = new ReentrantLock();

        void buyTicket() {
            String name = Thread.currentThread().getName();
            try {
                reentrantLock.lock();
                System.out.println(name + ":准备好了");
                Thread.sleep(100);
                System.out.println(name + ":买好了");

                reentrantLock.lock();
                System.out.println(name + ":又准备好了");
                Thread.sleep(100);
                System.out.println(name + ":又买好了");

                reentrantLock.lock();
                System.out.println(name + ":准备好了");
                Thread.sleep(100);
                System.out.println(name + ":又买好了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    /**
     * 演示  多个线程 去打印纸张,每个线程 打印张(ReentrantLock 公平锁，非公平锁)
     * <p>
     * 公平锁：交易
     * 非公平锁：synchorinzed，场景比比皆是
     */
    public static void demo2() {
        final ReentrantLockTask2 task = new ReentrantLockTask2();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                task.print();
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }

    static class ReentrantLockTask2 {
        /**
         * 参数true:公平锁 false:非公平锁
         * 公平锁将全部打印第一次后才打印第二次
         * 非公平锁将可被抢占，大概率是两次打印结束后才会开启其他线程
         * 非公平锁性能较高
         */
        ReentrantLock lock = new ReentrantLock(true);

        void print() {
            String name = Thread.currentThread().getName();
            try {
                lock.lock();
                //打印两次
                System.out.println(name + "第一次打印");
                Thread.sleep(1000);
                lock.unlock();

                lock.lock();
                System.out.println(name + "第二次打印");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * 我们演示 生产者与消费者的场景，利用的是ReentrantLock condition 条件对象，能够指定唤醒某个线程去工作
     * <p>
     * <p>
     * 生产者是：一个boss  去生产砖，砖的序列号为偶数，那么工人2去搬，奇数号让工人去去搬
     * <p>
     * 消费者是两个工人，有砖搬就搬，没转搬就休息
     */
    public static void demo3() {
        final ReentrantLockTask3 lockTask = new ReentrantLockTask3();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    lockTask.work1();
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    lockTask.work2();
                }
            }
        }).start();


        for (int i = 0; i < 10; i++) {
            lockTask.boss();
        }
    }

    static class ReentrantLockTask3 {

        /**
         * 条件对象
         * 可由条件对象去指定操作单个线程
         */
        private Condition worker1Condition, worker2Condition;
        /**
         * 公平锁
         */
        ReentrantLock lock = new ReentrantLock(true);

        volatile int flag = 0;//砖的序列号

        public ReentrantLockTask3() {
            worker1Condition = lock.newCondition();
            worker2Condition = lock.newCondition();
        }

        //工人1搬砖
        void work1() {
            try {
                lock.lock();
                if (flag == 0 || flag % 2 == 0) {
                    System.out.println("worker1 无砖可搬,休息会");
                    worker1Condition.await();
                }
                System.out.println("worker1 搬到的砖是：" + flag);
                flag = 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }


        //工人2搬砖
        void work2() {
            try {
                lock.lock();
                if (flag == 0 || flag % 2 != 0) {
                    System.out.println("worker2 无砖可搬,休息会");
                    worker2Condition.await();
                }
                System.out.println("worker2 搬到的砖是：" + flag);
                flag = 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        void boss() {
            try {
                lock.lock();
                flag = new Random().nextInt(100);
                if (flag % 2 == 0) {
                    worker2Condition.signal();
                    System.out.println("生产出来了砖，唤醒工人2去搬：" + flag);
                } else {
                    worker1Condition.signal();
                    System.out.println("生产出来了砖，唤醒工人1去搬：" + flag);
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
