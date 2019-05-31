package com.example.doublex.a22_thread_interaction;

/**
 *  线程的交互
 *
 *      一个线程启动另一个线程： ① new Thread().start()   ② Executor.execute() 等等
 *
 *      一个线程终结另一个线程：
 *
 *              ① Thread.stop()
 *              ② Thread.interrupt() 和 isInterrupted() 搭配按需求终止
 */
class ThreadInteractionDemo implements TestDemo {


    @Override
    public void runTest() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    /**
                     * Thread.interrupted() 和 isInterrupted() 都可获取当前线程是否需要中断
                     * 不同的是 Thread.interrupted() 会重置 interrupted 的值
                     */
                    if(Thread.interrupted()){
                        //当标记次线程需要打断时，退出循环线程结束
                        return;
                    }
                    System.out.println("number:" + i);
                }
            }
        };
        thread.start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 现在基本不使用这种方式，暴力停止线程
         */
        //thread.stop();

        /**
         * 通常在需要的时候调用此方法，将线程标记为需要打断、终止
         * 然后在线程内部更具自己业务需求去获取标记状态，自行判断是否退出线程
         */
        thread.interrupt();
    }
}
