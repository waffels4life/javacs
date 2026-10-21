package com.javacs.concurrency;

public class DSThreads {

    /*
     * Inheriting from `Thread` means our class can no longer inherit from another class.
     */
    static class ExtendsThread extends Thread {
        @Override public void run() {
            System.out.println("Extend thread: "
                    + Thread.currentThread().getName());
        }
    }

    /*
     * `Runnable` decouples a task from its execution mechanism, and this very separation
     * forms the foundation for all subsequent frameworks.
     */
    static class ImplementsRunnable implements Runnable {
        @Override public void run() {
            System.out.println("Implements Runnable: "
                    + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExtendsThread extendsThread = new ExtendsThread();
        Thread thread = new Thread(new ImplementsRunnable());

        extendsThread.start();
        thread.start();

        extendsThread.join();
        thread.join();

        Thread lambdaThread = new Thread(() ->
                System.out.println("Lambda thread: "
                        + Thread.currentThread().getName())
        );
        lambdaThread.start();
        lambdaThread.join();
    }
}

class LifecycleCoordination {
    static class Worker implements Runnable {
        @Override public void run() {
            try {
                for (int i = 0; i <= 5; i++) {
                    System.out.println("Working step " + i);
                    Thread.sleep(300);
                }
            }
            catch (InterruptedException e) {
                System.err.println("worker was interrupted during sleep.");
                Thread.currentThread().interrupt();
            }
            System.out.println("Worker finished.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(new Worker(), "worker-1");
        worker.start();

        System.out.println("isAlive after start: " + worker.isAlive());

        Thread.sleep(1000);
        worker.interrupt();
        worker.join(2000);


        System.out.println("isAlive after join: " + worker.isAlive());
        System.out.println("main finished");
    }
}

class FullLifecycleDemo {

    static class Worker implements Runnable {
        @Override public void run() {
            System.out.println("[" + Thread.currentThread().getName() + "] started");
            try {
                for (int i = 1; i <= 10; i++) {
                    System.out.println("[" + Thread.currentThread().getName() + "] step " + i);
                    Thread.sleep(500);
                }
                System.out.println("[" + Thread.currentThread().getName() + "] finished normally");
            } catch (InterruptedException e) {
                System.out.println("[" + Thread.currentThread().getName() + "] interrupted at step");
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(new Worker(), "worker");
        System.out.println("before start, isAlive = " + worker.isAlive());

        worker.start();
        System.out.println("after start,  isAlive = " + worker.isAlive());

        Thread.sleep(1200);
        System.out.println("main sends interrupt");
        worker.interrupt();

        worker.join();
        System.out.println("after join,   isAlive = " + worker.isAlive());
        System.out.println("main done");
    }
}