package com.javacs.exceptions;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/*
 * Trace
 * │
 * ├── main()
 * │   │
 * │   ├── try (CASE 1)
 * │   │     ├── class LookUp { throwEx() }   ← local class ($1)
 * │   │     └── catch -> printTrace          ← frames: throwEx, main
 * │   │
 * │   ├── try (CASE 2)
 * │   │     ├── getThread()
 * │   │     │     ├── new Thread(lambda)     ← lambda$getThread$0
 * │   │     │     ├── setUncaughtExceptionHandler(...)
 * │   │     │     └── start()
 * │   │     ├── thread.join()                ← only WAITS
 * │   │     ├── throw RuntimeException       ← thrown in main
 * │   │     └── catch -> printTrace          ← frames: main
 * │   │
 * │   ├── throw new Exception("...")         ← thrown in main
 * │   └── catch (outer) -> printTrace        ← frames: main
 * │
 * ├── getThread() -> @NotNull Thread
 * │
 * └── printTrace(Throwable)
 *       ├── requireNonNull
 *       └── for each StackTraceElement -> print
 */
public final class Trace {
    public static void main(String[] args) {
        // Outer try/catch wraps everything so the final Exception thrown
        // at the end of the method is caught in one place.
        try {

            // CASE 1: Exception thrown from a local (inner) class
            // -----------------------------------------------------------
            // Flow:
            //   main()
            //     └─> LookUp.throwEx()
            //           └─> throw RuntimeException
            //     <── propagated up
            //   caught by inner catch #1
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

            // CASE 2: Exception thrown from a SEPARATE thread
            // -----------------------------------------------------------
            // KEY POINT:
            //   - An exception inside a thread does NOT propagate to
            //     the parent (main) thread's try/catch.
            //   - thread.join() only WAITS for the thread; it does NOT
            //     re-throw the thread's exception.
            //   - To handle it, we install an
            //     UncaughtExceptionHandler (done inside getThread()).
            //
            // Flow:
            //   main()
            //     ├─> getThread() -> starts Thread-0
            //     ├─> thread.join()  [ waits ]
            //     │       Thread-0:
            //     │         throw RuntimeException
            //     │         -> UncaughtExceptionHandler prints trace
            //     └─> throw RuntimeException("Runtime from outer thread")
            //           -> caught by inner catch #2
            try {
                Thread thread = getThread();
                thread.join(); // wait until Thread-0 finishes

                throw new RuntimeException("Runtime from outer thread");

            } catch (Exception e) {
                printTrace(e);
            }

            // CASE 3: Exception thrown directly from main()
            // -----------------------------------------------------------
            // This is thrown AFTER the previous two cases completed,
            // so it is the LAST exception this method produces.
            throw new Exception("Exception Message");
        }
        catch (Exception e) {
            // Final catch: handles the "Exception Message" from above.
            printTrace(e);
        }
    }

    // Creates, configures, and starts a worker thread.
    // -------------------------------------------------------------------
    // Why UncaughtExceptionHandler?
    //   Exceptions thrown inside a thread are NOT visible to the caller.
    //   Without this handler, the JVM's default handler would print
    //   "Exception in thread ..." to stderr. By installing our own,
    //   we control the output format (see printTrace).
    private static @NotNull Thread getThread() {
        Thread thread = new Thread(() -> {
            System.out.println("Thread");
            throw new RuntimeException("Runtime from inner thread");
        });

        // Handler runs on the dying thread, right before it terminates.
        // Parameters:
        //   thread1   -> the thread that crashed
        //   throwable -> the exception it threw
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

    // Prints the stack trace of any Throwable, one frame per line.
    //
    // Why Throwable (not Exception)?
    //   - getStackTrace() is defined on Throwable.
    //   - Errors (e.g. OutOfMemoryError) are also Throwable.
    //   - This makes the helper reusable for any crash type.
    //
    // Frame ordering (per JVM spec):
    //   index 0        -> method where the throwable was created
    //   last index     -> bottom of the call stack (e.g. main)
    //
    // Example output:
    //   com.javacs.exceptions.Trace$1LookUp.throwEx (line 11)
    //   com.javacs.exceptions.Trace.main (line 15)
    private static void printTrace(Throwable throwable) {

        // Fail fast on bad input instead of NPE-ing later on.
        Objects.requireNonNull(throwable, "throwable");

        for (StackTraceElement el : throwable.getStackTrace())
            System.out.println(el.getClassName() + "."
                    + el.getMethodName() + " (line "
                    + el.getLineNumber() + ")");
    }
}
