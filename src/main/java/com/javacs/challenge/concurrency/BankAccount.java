package com.javacs.challenge.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private long balance;
    private final Lock lock = new ReentrantLock(true);

    public BankAccount(long initialBalance) {
        if (initialBalance < 0)
            throw new IllegalArgumentException(
                    "Initialize balance cannot be negative."
            );

        this.balance = initialBalance;
    }

    public void deposit(long amount) {
        lock.lock();
        try {
            if (amount <= 0)
                throw new IllegalArgumentException(
                        "Deposit amount cannot be negative or zero."
                );

            this.balance = Math.addExact(this.balance, amount);
        }
        finally {
            lock.unlock();
        }
    }

    public boolean withdraw(long amount) {
        lock.lock();
        try {
            if (amount <= 0)
                throw new IllegalArgumentException(
                        "Withdraw amount cannot be negative or zero."
                );

            if ((this.balance - amount) < 0) return false;
            else {
                this.balance -= amount;
                return true;
            }
        }
        finally {
            lock.unlock();
        }
    }

    public long getBalance() {
        lock.lock();
        try {
            return this.balance;
        }
        finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount(100_000);
        try (ExecutorService executorService = Executors.newFixedThreadPool(20)) {
            for (int i = 0; i < 10_000; i++)
                executorService.submit(() ->
                        bankAccount.deposit(10)
                );
            for (int i = 0; i < 10_000; i++)
                executorService.submit(() ->
                        bankAccount.withdraw(10)
                );
        }

        System.out.println(bankAccount.getBalance());
    }
}
