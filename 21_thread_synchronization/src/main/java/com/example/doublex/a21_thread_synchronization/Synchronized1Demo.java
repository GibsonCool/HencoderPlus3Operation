package com.example.doublex.a21_thread_synchronization;

/**
 * synchronized 的本质:
 *      1、通过 synchronized 保证⽅方法内部或代码块内部资源(数据)的互斥访问。
 *         即同⼀一时间、由同⼀一个 Monitor 监视的代码，最多只能有⼀一个线程在访问
 *
 *      2、保证线程之间对监视资源的数据同步。即，任何线程在获取到 Monitor 后的第⼀一时间，
 *         会先将共享内存中的数据复制到⾃自⼰己的缓存中;任何线程在释放 Monitor 的第⼀一 时间，
 *         会先将缓存中的数据复制到共享内存中。
 */
public class Synchronized1Demo implements TestDemo {
    private int x = 0;
    private int y = 0;


    private  void count(int newValue) {
//    private synchronized void count(int newValue) {
        x = newValue;
        y = newValue;
        if (x != y) {
            System.out.println("x:  " + x + ",y: " + y);
        }
    }

    @Override
    public void runTest() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000_000; i++) {
                    count(i);
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000_000; i++) {
                    count(i);
                }
            }
        }.start();
    }

}
