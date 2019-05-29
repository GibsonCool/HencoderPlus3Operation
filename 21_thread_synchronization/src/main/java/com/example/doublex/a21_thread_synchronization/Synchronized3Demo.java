package com.example.doublex.a21_thread_synchronization;

/**
 *
 */
public class Synchronized3Demo implements TestDemo {

    private int x = 0;
    private int y = 0;
    private String name;
    private final Object monitor = new Object();

    private void count(int newValue) {
        synchronized (this) {
            x = newValue;
            y = newValue;
        }
    }

    /**
     * synchronized (this)  和 在方法中加上 synchronized 修饰的结果一样
     * 方法上加 synchronized 默认的 monitor (所谓的锁)就是 this。
     * 相比起来 synchronized (this) 更灵活可懂范围调整
     */
    private synchronized void minus(int delta) {
        x -= delta;
        y -= delta;

    }

    /**
     * 为了能在访问 name 资源的时候不因为 minus 和 count 所持有相同的 monitor-->this
     * 而产生排队等monitor。可以给自己创建单独的monitor
     */
    private void setName(String newName) {
        synchronized (monitor) {
            name = newName;
        }
    }

    @Override
    public void runTest() {

    }
}
