package com.javacs.time;

import org.jetbrains.annotations.NotNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class AgeInfo {

    private final LocalDate now;
    private final LocalDate custom;
    private final Period period;

    public AgeInfo(int year, Month month, int day) {
        now = LocalDate.now();
        custom = LocalDate.of(year, month, day);

        if (custom.isAfter(now))
            throw new DateTimeException(
                    "How interesting, you were born in the future..."
            );

        period = Period.between(custom, now);
    }

    private String printInfo() {
        return "Year: " +
                period.getYears() +
                "\n" +
                "Months: " +
                period.getMonths() +
                "\n" +
                "Days: " +
                period.getDays();
    }

    public static void main(String[] args) {
        System.out.println("Enter date in [Year -> Month -> day] format.");

        int year = 0;
        int month = 0;
        int day = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            year = scanner.nextInt();
            month = scanner.nextInt();
            day = scanner.nextInt();
        }
        catch (InputMismatchException e) {
            System.err.println("[Error] Only numbers are valid.");
            return;
        }
        catch (NoSuchElementException e) {
            System.err.println("[Error] Please do not skip an input.");
            return;
        }

        Sapling sapling = null;
        try {
            sapling = new Sapling(year, Month.of(month), day);
        }
        catch (DateTimeException e) {
            System.err.println("[Error] " + e.getMessage());
            return;
        }

        System.out.println(sapling.printInfo());
    }
}
