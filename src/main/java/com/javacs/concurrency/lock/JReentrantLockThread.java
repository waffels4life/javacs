package com.javacs.concurrency.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JReentrantLockThread {

    public int number = 0;
    private static final Lock lock = new ReentrantLock();
    public void increase() {
        lock.lock();
        try {
            number++;
        }
        finally {
            lock.unlock();
        }
    }

    public void decrease() {
        lock.lock();
        try {
            number--;
        }
        finally {
            lock.unlock();
        }
    }

    public int getNumber() {
        lock.lock();
        try {
            return this.number;
        }
        finally {
            lock.unlock();
        }
    }
}
