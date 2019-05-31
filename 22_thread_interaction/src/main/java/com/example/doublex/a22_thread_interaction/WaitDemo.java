package com.example.doublex.a22_thread_interaction;

/**
 * wait()、notify() 和  notifyAll()
 *
 *  join():
 *      比如我在 thread1 中调用的了 thread2.join() 。那么就会让 thread1 暂时排队等待 thread2 调用完成了自己在执行
 *
 *  yield():
 *      Thread.yield 暂时把当前抢占到的cpu执行权让出给'同优先级'的线程。但是不影响下次cpu执行权的抢夺
 */
class WaitDemo implements TestDemo {

    private String sharedStr;



    private synchronized void printStr() {
        /**
         * printStr() 和 initString 的monitor（锁）都是同一个，当前对象。
         * 所以如果 printStr() 在 initString() 之前执行,并且 printStr() 内部一直在循环检测
         * 一直没有释放锁的话，initString() 也就拿不到锁无法执行一直等待 printStr()。两者之前互相牵扯
         * 导致形成了类似"死循环"的结果
         */

        //可能 sharedStr 还没有初始化，进行判断
        while (sharedStr == null) {
            // 提示调用次方法的线程先抢占到了 monitor(锁--》this--》当前对象) 并且值还没初始化
            // 调用"当前对象"的 wait() 方法。暂时阻塞当前调用线程并且释放monitor的占用，并去排队等待被唤醒 notify()/notifyAll()
            // 其他使用同 monitor 的线程就可以获取并执行
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("String:" + sharedStr);
    }

    private synchronized void initString() {
        sharedStr = "DoubleX";
        //当调用次方法的线程初始化值后，调用 notifyAll() 去唤醒该 monitor 上的所有被 wait() 后
        //在等待排队的其他线程，让他们可以去抢夺 monitor 并且 "接着" 执行其线程代码
        notifyAll();
    }

    @Override
    public void runTest() {
         new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printStr();
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                initString();
            }
        }.start();

    }
}
