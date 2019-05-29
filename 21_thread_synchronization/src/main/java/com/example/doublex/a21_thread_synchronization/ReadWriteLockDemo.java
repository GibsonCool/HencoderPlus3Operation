package com.example.doublex.a21_thread_synchronization;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁是为了解决多个线程在读、读的时候不会因为monitor需要排队影响效率
 * 只有在 读写   写读   写写  的情况下才会上锁监控同一时刻只有一个线程访问资源。
 */
public class ReadWriteLockDemo implements TestDemo {

    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    private int x = 0;

    private void count() {
        writeLock.lock();
        try {
            x++;
        } finally {
            writeLock.unlock();
        }
    }


    private void print(int time) {
        readLock.lock();
        try {
            for (int i = 0; i < time; i++) {
                System.out.println("x=" + x);
            }
            System.out.println();
        }finally {
            readLock.unlock();
        }
    }

    @Override
    public void runTest() {

    }
}
