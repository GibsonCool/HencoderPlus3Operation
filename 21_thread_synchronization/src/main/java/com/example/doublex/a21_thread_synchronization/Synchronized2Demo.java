package com.example.doublex.a21_thread_synchronization;

public class Synchronized2Demo implements TestDemo {
    private int x = 0;

    private void count() {
//    private synchronized void count() {
        x++;
    }

    @Override
    public void runTest() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    count();
                }
                System.out.println("final x from thread1 : " + x);
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    count();
                }
                System.out.println("final x from thread2 : " + x);
            }
        }.start();
    }
}
