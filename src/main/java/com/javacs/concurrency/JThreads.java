package com.javacs.concurrency;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class JThreads {
    /*
     * parallelization  : Executing multiple threads in parallel.
     * concurrency      : Tasks are not performed simultaneously; the CPU
     *                    simply switches between threads very rapidly.
     * multitasking     : operating system manages multiple programs or processes.
     * multithreading   : There are several execution paths within a program.
     * [Race Condition]     -> If different threads modify a single piece of data, there is a possibility of error.
     * [synchronized]       -> Queueing system.
     *                      -> The guarantee is simply that the two threads do not enter simultaneously.
     * [critical section]   -> The part of the program that operates on shared data
     *                         and requires protection against concurrent access.
     *
     * Multithreading is typically employed for four purposes:
     *      [1] better CPU utilization
     *      [2] utilization of multiple cores
     *      [3] improved application responsiveness
     *      [4] fair resource allocation
     *
     * == Thread State ==
     *      [0] getState()      : STATE
     *      [1] NEW             => built, but not yet executed
     *      [2] RUNNABLE        => threads start != executing on cpu rn
     *      [3] BLOCKED         => locked by 'synchronized'; wating for 'monitor-lock'
     *      [4] WAITING         => wating for smthing to happen
     *      [5] TIMED_WAITING   => Thread.sleep(1000);
     *      [6] TERMINATED      => finished executing
     *
     * [NOTE]   : you CANNOT re-start a thread => [IllegalThreadStateException]
     *
     *               start()
     *                  │
     *                  ▼
     *                 NEW
     *                  │
     *                  ▼
     *               RUNNABLE
     *        ┌─────────┴─────────┐
     *        ▼         ▼         ▼
     *     BLOCKED   WAITING  TIMED_WAITING
     *        |         |         |
     *        └─────────┴─────────┘
     *                  │
     *                  ▼
     *               RUNNABLE
     *                  │
     *                  │ run() ends
     *                  ▼
     *              TERMINATED
     */

    public void runDownloadThread() {
        Thread downloadThread = new Thread(() -> {
            System.out.println("download_Thread");
        });
        downloadThread.start();
    }

    /*
     *              JVM
     *               │ <- thread.start()
     *        ┌──────┴──────┐
     *        │             │
     *     Thread A      Thread B
     *        │             │
     *     println()     println()
     *
     * Thread A --> ?
     * Thread B --> ?
     *
     * JVM
     *  │
     *  │ <- thread.run()
     *  │
     *  └── Thread A --> Thread B
     */

    /**
     * <b>The code that has been written is fragile.</b>
     * <p>Two separate threads — the main one and the one we created — are
     * attempting to modify shared data.</p>
     * <p>The outcome can be unpredictable and indistinguishable.</p>
     */
    public void raceConditionThread() {
        final int SIZE = 5;

        // A public array of integers
        int[] array = new int[SIZE];

        Thread thread = new Thread(() -> {
            for (int i = 0; i < SIZE; i++) {
                array[i] = i;
            }
        });

        thread.start();

        for (int i = 0; i < SIZE; i++) {
            array[i] = i + 1;
        }

        System.out.println(Arrays.toString(array));
    }

    public String getThreadName() {
        return Thread.currentThread().getName();
    }

    public void matryoshka() {

        AtomicInteger counter = new AtomicInteger();

        Thread superThread = new Thread(() -> {

            Thread supThread_1 = new Thread(
                    counter::getAndIncrement
            );
            Thread supThread_2 = new Thread(
                    counter::getAndIncrement
            );
            Thread supThread_3 = new Thread(
                    counter::getAndIncrement
            );

            supThread_1.start();
            supThread_2.start();
            supThread_3.start();
        });
        superThread.start();
        try {
            superThread.join();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /*
     *       RUNNABLE
     *          │ sleep()
     *          ▼
     *     TIMED_WAITING
     *          │ timeout
     *          ▼
     *       RUNNABLE
     */
    public void threadSleeper(long goodnight) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println(
                    Thread.currentThread().getName()
            );
        });
        thread.start();
        // ALWAYS STOPS THE CURRENT THREAD
        Thread.sleep(goodnight * 1000);
    }
    /*
     * main
     *  │
     *  ├── worker.start()
     *  │
     *  │        worker
     *  │          │
     *  │          ▼
     *  │       started
     *  │          │
     *  │       sleep 2s
     *  │          │
     *  │       finished
     *  │
     *  ├── worker.join()
     *  │       ↑
     *  │       │
     *  │     worker
     *  │
     *  ▼
     * Main finished
     */
    public void threadJoin() throws InterruptedException {
        Thread worker = new Thread(() -> {

            System.out.println("Worker started");

            try {
                Thread.sleep(2000);
            } // If an error occurs for the current thread
            catch (InterruptedException e) {
                // Stop the current thread (the thread is still alive and holds vital information)
                Thread.currentThread().interrupt();
            }

            System.out.println("Worker finished");
        });

        worker.start();

        worker.join();

        System.out.println("Main finished");
    }
    // volatile : It can help make the changes visible
    private volatile int progress;

    public void downloadFileThread() {
        Thread downloadFile = new Thread(() -> {
            System.out.println("Downloading...");
        });
        downloadFile.start();
        try {
            downloadFile.join();
            saveFileThread();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public void saveFileThread() {
        /*
         * If we are on the download thread and its task is complete (it is no longer active),
         * execute the file-saving thread.
         */
        Thread saveFile = new Thread(() -> {
            System.out.println("Where you wanna save you file?");
        });
        saveFile.start();
    }

    static class Deadlock {

        final Object porta = new Object();
        final Object cup = new Object();

        void coffeeShop() {
            Thread arsam = new Thread(() -> {
                synchronized (porta) {
                    System.out.println("Got the portafilter.");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                synchronized (cup) {
                    System.out.println("Coffee is ready.");
                }
            });

            Thread rick = new Thread(() -> {
                synchronized (cup) {
                    System.out.println("Got the cup.");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                synchronized (porta) {
                    System.out.println("Coffee is ready.");
                }
            });

            arsam.start();
            rick.start();
        }
    }

    // fixing the Deadlock class with ReentrantLock
    static class FixDeadLock {

        final Object porta = new Object();
        final Object cup = new Object();
        final ReentrantLock lock = new ReentrantLock(true);

        void coffeeShop() {

            Thread arsam = new Thread(() -> {
                try {
                    if (lock.tryLock(2, TimeUnit.SECONDS)) {
                        try {
                            synchronized (porta) {
                                System.out.println("Got portafilter");
                                Thread.sleep(100);
                            }
                            synchronized (cup) {
                                System.out.println("Coffee ready");
                            }
                        } finally {
                            lock.unlock();
                        }
                    } else {
                        System.out.println("I'll get it next time");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

            Thread rick = new Thread(() -> {
                try {
                    if (lock.tryLock(2, TimeUnit.SECONDS)) {
                        try {
                            synchronized (porta) {
                                System.out.println("Got portafilter");
                                Thread.sleep(100);
                            }
                            synchronized (cup) {
                                System.out.println("Coffee ready");
                            }
                        } finally {
                            lock.unlock();
                        }
                    } else {
                        System.out.println("I'll get it next time");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

            arsam.start();
            rick.start();
        }
    }

    /**
     * join()   -> It becomes active whenever the current thread finishes
     * wait()   -> It needs to be manually triggered to activate, subject to the aforementioned condition
     */

    static class Kitchen {

        private boolean isFoodReady = false;

        public synchronized void waiter() throws InterruptedException {
            System.out.println("waiting for food...");
            while (!isFoodReady()) {
                wait();
            }
            System.out.println("serving the food.");
        }

        public synchronized void chief() {

            try {
                Thread.sleep(5000);
                System.out.println("cooking food...");
            } catch (InterruptedException _) {
                Thread.currentThread().interrupt();
            }
            setFoodReady(true);
            System.out.println("food ready!");

            notify();
        }

        public boolean isFoodReady() {
            return isFoodReady;
        }

        public void setFoodReady(boolean foodStatus) {
            isFoodReady = foodStatus;
        }

        Kitchen kitchen = new Kitchen();

        public void startKitchen() {
            Thread waiterThread = new Thread(() -> {
                try {
                    kitchen.waiter();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            Thread chiefThread = new Thread(() -> {
                kitchen.chief();
            });

            waiterThread.start();
            chiefThread.start();
        }
    }

    public void executorServiceThread() {
        try (ExecutorService executor =
                     Executors.newFixedThreadPool(3)
        ) {
            // Future<?> video    = executor.submit(() -> downloadVideo());
            // Future<?> audio    = executor.submit(() -> downloadAudio());
            // Future<?> subtitle = executor.submit(() -> downloadSubtitle());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Each thread has a local cache of variables.
     *
     * Main Memory:  flag = true
     *      ↓
     * Thread 1:  cache: flag = true
     * Thread 2:  cache: flag = false (data has not yet been updated)
     *
     * [volatile] -> It reads directly from the main memory every time.
     *     ↓
     * Always read this variable from main memory, not the cache.
     */
    static class Worker {

        protected boolean wrongRunning = true;
        protected volatile boolean correctRunning = true;

        public void doingWork() {
            /*
             * A lambda can only use variables that are:
             *      [1] final
             *      [2] effectively final (unchanged)
             */
            Thread thread = new Thread(() -> {
                while (correctRunning)
                    System.out.println("Working...");
            });

            thread.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // Error : Variable used in lambda expression should be final or effectively final
            wrongRunning = false;

            correctRunning = false;
        }
    }

    static class Atomic {
        // still facing problem
        volatile int count = 0;

        public void atomsInThread() {
            // race condition
            int count = 0;
            count++;

            // fully thread-safe
            AtomicInteger atomicInteger = new AtomicInteger(0);
            atomicInteger.incrementAndGet();

            atomicInteger.get();                // Give the current value
            atomicInteger.set(10);              // Set the value.
            atomicInteger.incrementAndGet();    // First increment, then return → ++count
            atomicInteger.getAndIncrement();    // Give first, then +1 → count++
            atomicInteger.decrementAndGet();    // First deduct 1, then give.
            atomicInteger.addAndGet(5);   // Add x [5] and hand it over.

            /*
             * AtomicInteger    => int
             * AtomicLong       => long
             * AtomicBoolean    => boolean
             * AtomicReference  => any object
             */
        }
    }

    /*
     * problem with synchronized is that if we stuck in a junk of code,
     * there is no way for saving it, no cancel, no time out
     */
    public void reentrantLockThread() {
        ReentrantLock reentrantLock = new ReentrantLock(true);
        reentrantLock.lock(); // <- first lock (lock count = 1)
        try {
            // will wait a period of time before taking and action
            if (reentrantLock.tryLock(2, TimeUnit.SECONDS)) { // <- second lock (lock count = 2)
                try {
                    int absValue;
                    // check if the lock is open
                    if (reentrantLock.isLocked())
                        // It throws an exception if interrupted
                        reentrantLock.lockInterruptibly(); // <- third lock (lock count = 3)
                    absValue = Math.abs(8);
                } finally {
                    // whatever happens, open the lock
                    for (int i = 0; i <= 3; i++)
                        reentrantLock.unlock();
                }
                /*
                 * The locks stack up; to unlock the thread, you must issue
                 * the same number of release commands.
                 */
            }
            else System.out.println("[Locked after {2} second]");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private final ReentrantLock reentrantLock = new ReentrantLock();

    // simple lock
    public void simpleLockThread() {
        reentrantLock.lock();
        try {
            doWork();
        } finally {
            reentrantLock.unlock();
        }
    }

    // try, if lock -> leave it
    public void tryLockThread() {
        if (reentrantLock.tryLock()) {
            try {
                doWork();
            } finally {
                reentrantLock.unlock();
            }
        }
        else {
            System.out.println(
                    "It's locked"
            );
        }
    }

    // try, if locked, wait x time, if lock -> leave it
    public void tryLockTimeoutThread() throws InterruptedException {
        if (reentrantLock.tryLock(
                5, TimeUnit.SECONDS
        )) {
            try {
                doWork();
            } finally {
                reentrantLock.unlock();
            }
        }
        else {
            System.out.println(
                    "waited 5 second and It's still locked"
            );
        }
    }

    // leave it if thread got interrupted
    public void interruptLockThread() throws InterruptedException {
        reentrantLock.lockInterruptibly();
        try {
            doWork();
        } finally {
            reentrantLock.unlock();
        }
    }

    // little buddy is doing all the work
    public void doWork() {
        System.out.println("working...");
    }

    /*
     * ReentrantLock  : is like a door; it's either open or closed.
     * Semaphore      : is like a parking lot; it has a certain number of available spots.
     */
    public void parking() throws InterruptedException {
        final int CAPACITY = 5;

        // initializing parking space
        Semaphore semaphore = new Semaphore(CAPACITY);

        // getting a space
        semaphore.acquire(); // <- free space count : 4 (5 - 1)

        // freeing a space
        semaphore.release(); // <- free space count : 5
    }

    static class Server {

        boolean isDone = false;
        final int CAPACITY = 5;
        private final Semaphore semaphore = new Semaphore(CAPACITY);

        public void handleRequest(String user) {
            try {
                semaphore.acquire(); // <- minus one free space
                try {
                    System.out.println(user + " connected.");
                    Thread.sleep(2000);
                    isDone = true;
                    System.out.println(user + " disconnected.");
                }
                finally {
                    if (isDone) semaphore.release();
                    else handleRequest(user);
                }
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*
     * CountDownLatch   -> Each thread decrements the count by one.
     *                  -> When it reaches 0 → they are all released.
     *
     * latch.countDown();                   : minus the counter by one
     * latch.await();                       : wait till counter be zero
     * latch.await(5, TimeUnit.SECONDS);    : wait a maximum of 5 seconds
     * latch.getCount();                    : how many are left now?
     */
    public void countDownThread() {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        Thread thread_1 = new Thread(() -> {
            doWork();
            countDownLatch.countDown();
        });
        Thread thread_2 = new Thread(() -> {
            doWork();
            countDownLatch.countDown();
        });
        Thread thread_3 = new Thread(() -> {
            doWork();
            countDownLatch.countDown();
        });

        try {
            thread_1.start();
            thread_2.start();
            thread_3.start();
            countDownLatch.await();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getInt() {
        return 0;
    }

    public void completableFutureThread() {
        CompletableFuture<Integer> completableFuture =
                CompletableFuture.supplyAsync(this::getInt);
    }
}
