package com.javacs.concurrency.lock;

public class SynchronizedThreadLock {

    public int number = 0;

    public synchronized void increase() {
        this.number++;
    }

    public synchronized int getNumber() {
        return this.number;
    }

    public static void main(String[] args) {
        SynchronizedThreadLock synchronizedThreadLock = new SynchronizedThreadLock();

        Thread thread_1 = new Thread(() -> {
            for (int i = 0; i < 1000; i ++)
                synchronizedThreadLock.increase();
        });


        Thread thread_2 = new Thread(() -> {
            for (int i = 0; i < 1000; i ++)
                synchronizedThreadLock.increase();
        });

        thread_1.start();
        thread_2.start();

        try {
            thread_1.join();
            thread_2.join();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
