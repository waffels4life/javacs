package com.javacs.exceptions;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Trace {
    public static void main(String[] args) {
        try {
            try {
                class LookUp {
                    void throwEx() {
                        System.out.println("Inner class");
                        throw new RuntimeException("Runtime from inner class");
                    }
                }
                new LookUp().throwEx();
            } catch (Exception e) {
                printTrace(e);
            }

            try {
                Thread thread = getThread();
                thread.join();

                throw new RuntimeException("Runtime from outer thread");

            } catch (Exception e) {
                printTrace(e);
            }

            throw new Exception("Exception Message");
        }
        catch (Exception e) {
            printTrace(e);
        }
    }

    private static @NotNull Thread getThread() {
        Thread thread = new Thread(() -> {
            System.out.println("Thread");
            throw new RuntimeException("Runtime from inner thread");
        });

        thread.setUncaughtExceptionHandler((thread1, throwable) -> {
            System.out.println("Uncaught in "
                    + thread1.getName()
                    + ":"
            );
            printTrace(throwable);
        });

        thread.start();
        return thread;
    }

    private static void printTrace(Throwable throwable) {

        Objects.requireNonNull(throwable, "throwable");

        for (StackTraceElement el : throwable.getStackTrace())
            System.out.println(el.getClassName() + "."
                    + el.getMethodName() + " (line "
                    + el.getLineNumber() + ")");

    }
}
