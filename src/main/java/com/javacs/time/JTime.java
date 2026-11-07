package com.javacs.time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

@SuppressWarnings("all")
public final class JTime {
    /*
     * java.time package works with time and date (no shit)
     */
    private LocalDate localDate;
    private LocalTime localTime;
    public JTime(int year, Month month, int day) {
        this.localDate = LocalDate.of(year, month, day);
    }
    public JTime(int hour, int minute) {
        this.localTime = LocalTime.of(hour, minute);
    }

    private int getMonth() {
        return localDate.getMonth().getValue();
    }
}
