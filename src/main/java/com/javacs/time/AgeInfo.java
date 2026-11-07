package com.javacs.time;

import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class AgeInfo {

    private final LocalDate birthDate;
    private final LocalDate currentDate;
    private final Period age;

    public AgeInfo(int year, Month month, int day, Clock clock) {
        this.birthDate = LocalDate.of(year, month, day);
        this.currentDate = LocalDate.now(clock);

        if (birthDate.isAfter(currentDate))
            throw new DateTimeException(
                    "Birth date cannot be in the future."
            );

        this.age = Period.between(birthDate, currentDate);
    }

    public int getYears() {
        return age.getYears();
    }

    public int getMonths() {
        return age.getMonths();
    }

    public int getDays() {
        return age.getDays();
    }

    public Period getAge() {
        return age;
    }

    public void printAge() {
        System.out.println(
                "Years: " + getYears() +
                        "\nMonths: " + getMonths() +
                        "\nDays: " + getDays()
        );
    }

    public static void main(String[] args) {

        System.out.println("Enter your birth date [Year Month Day]:");

        int year;
        int month;
        int day;

        try (Scanner scanner = new Scanner(System.in)) {
            year = scanner.nextInt();
            month = scanner.nextInt();
            day = scanner.nextInt();

        }
        catch (InputMismatchException e) {
            System.err.println("[Error] Only numeric values are accepted.");
            return;
        }
        catch (NoSuchElementException e) {
            System.err.println("[Error] Insufficient input.");
            return;
        }

        try {
            AgeInfo ageInfo = new AgeInfo(
                    year,
                    Month.of(month),
                    day,
                    Clock.systemDefaultZone()
            );
            ageInfo.printAge();
        }
        catch (DateTimeException e) {
            System.err.println("[Error] " + e.getMessage());
        }
    }
}