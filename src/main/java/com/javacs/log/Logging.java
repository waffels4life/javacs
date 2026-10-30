package com.javacs.log;

import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Logging {
    /*
     * Logger.getLogger: creates a single logger instance for the class we want to observer.
     *
     * All log messages are handled and displayed by Java’s centralized logging system automatically.
     *
     * [*] In Java, there is a central logging system (typically referring to `java.util.logging`—or JUL for short—or,
     * more broadly, a logging framework like Log4j or SLF4J that aggregates all logs in one place). This means:
     *
     * [1] You do not need to manually collect or print logs yourself; the logging system handles this for you.
     * [2] All logs pass through a single central point—meaning that instead of each class independently calling
     * `System.out.println`, they all utilize a shared infrastructure.
     * [3] Management is automated—log levels, formatting, output destinations (such as the console or a file),
     * and filtering are all handled automatically by this central system.
     */
    private static final Logger LOGGER = Logger.getLogger(Logging.class.getName());

    private static void printLogMessage() {
        // LOGGER.info(): logs an INFO message to indicate normal program execution.
        LOGGER.info("Application started");

        // LOGGER.severe(): logs a SEVERE message for a serious error.
        LOGGER.severe("This is a severe error message");
    }

    public static @Nullable String loggerName = null;

    private static void nullLogger() {
        try {
            Logger logger = Logger.getLogger(loggerName);
            LOGGER.warning("getLogger(null) did not throw NPE");
        }
        catch (NullPointerException e) {
            LOGGER.info("getLogger(null) threw NPE as expected");
        }
    }

    private static void costumeLogLevel() {
        LOGGER.log(Level.WARNING, "Warning log");
    }

    public static void main(String[] args) {
        costumeLogLevel();
    }
}
