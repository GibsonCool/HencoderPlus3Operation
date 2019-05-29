package com.example.doublex.a21_thread_synchronization;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {
//        thread();
//        runnable();
//        threadFactory();
//        executor();
//        callable();

//        runSynchronized1Demo();
        runSynchronized2Demo();
//        runSynchronized3Demo();
    }

    /**
     * 使用 Thread 类来定义工作
     */
    static void thread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                System.out.println("Thread started !");
            }
        };
        thread.start();
    }

    /**
     * 使用 Runnable 类来定义工作
     */
    static void runnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with Runnable started!");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * 使用 ThreadFactory 来创建线程工作
     */
    static void threadFactory() {
        ThreadFactory factory = new ThreadFactory() {
            //使用
            AtomicInteger count = new AtomicInteger();
//            int count = 0;

            @Override
            public Thread newThread(Runnable runnable) {
//                count++;
//                return new Thread(runnable, "Thread-" + count);
                return new Thread(runnable, "Thread-" + count.incrementAndGet());
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " started !");
            }
        };

        Thread thread = factory.newThread(runnable);
        thread.start();
        Thread thread1 = factory.newThread(runnable);
        thread1.start();
    }

    /**
     * 使用 Excutor 创建线程
     */
    static void executor() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with Ruunnable started!   by  executor " + Thread.currentThread().getName());
            }
        };

        Executor executor = Executors.newCachedThreadPool();
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);

    }

    /**
     * 使用 Callable 来定义工作，可以看成是一个有返回值的 Runnable
     * 一般搭配 Future 成对使用，future.get() 获取值
     */
    static void callable() {

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "Done!";
            }
        };

        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(callable);
        try {
            String result = future.get();
            System.out.println("result:" + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    static void runSynchronized1Demo() {
        new Synchronized1Demo().runTest();
    }

    static void runSynchronized2Demo() {
        new Synchronized2Demo().runTest();
    }

    static void runSynchronized3Demo() {
        new Synchronized3Demo().runTest();
    }

}

