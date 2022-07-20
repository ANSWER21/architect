package com.topstack.hiconcurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Administrator
 * 线程安全之原子类
 * 一个用原子类修饰，一个用volatile修饰，在多线程的情况自增，然后输出最后的值
 */
public class AtomicDemo {

    public static void atomicTest() throws InterruptedException {
        final AtomicTask atomicTask = new AtomicTask();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    atomicTask.incrementVolatile();
                    atomicTask.incrementAtomic();
                }
            }
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        /**
         * I/System.out: 原子类结果：20000
         * I/System.out: volatile修饰的结果：19412
         * 说明volatile的++操作并非原子操作
         */
        System.out.println("原子类结果：" + atomicTask.atomicInteger.get());
        System.out.println("volatile修饰的结果：" + atomicTask.volatileCount);
    }

    static class AtomicTask {
        /**
         * 分别以原子类和volatile关键字修饰
         * 并对其做加1操作
         */
        AtomicInteger atomicInteger = new AtomicInteger();
        volatile int volatileCount = 0;

        void incrementAtomic() {
            atomicInteger.getAndIncrement();
        }

        void incrementVolatile() {
            //volatileCount++;
            volatileCount = volatileCount + 1;
        }
    }
}
