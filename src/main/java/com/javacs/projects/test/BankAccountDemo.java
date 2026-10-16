package com.javacs.projects.test;

public class BankAccountDemo {
    public static void main(String[] args) {

    }
}

class BankAccount {
    private long balance;

    BankAccount(long initialBalance) {
        this.balance = initialBalance;
    }

    void deposit(long amount) {
        balance += amount;
    }

    long getBalance() {
        return balance;
    }
}

class BankAccountTest {

    private static void expectThrows(String testName,
                                     Class<? extends Throwable> expected,
                                     Runnable action) {
        try {action.run();}
        catch (Throwable thrown) {
            if (expected.isInstance(thrown)) {
                System.out.println("PASS: " + testName);
            }
            else {
                System.out.println("FAIL: " + testName
                        + " threw " + thrown.getClass().getSimpleName());
            }
            return;
        }
        System.out.println("FAIL: " + testName + " did not throw");
    }

    static void deposit_negativeAmount_throws() {
        BankAccount account = new BankAccount(0);
        expectThrows("deposit_negativeAmount_throws",
                IllegalArgumentException.class,
                () -> account.deposit(-1));
    }
}
