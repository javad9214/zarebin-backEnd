package com.zarebin.utility;

import com.github.mfathi91.time.PersianDate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {

    // Shamsi → Gregorian
    public static LocalDate shamsiToGregorian(int year, int month, int day) {
        PersianDate persianDate = PersianDate.of(year, month, day);
        return persianDate.toGregorian();
    }

    // Gregorian → Shamsi
    public static PersianDate gregorianToShamsi(LocalDate gregorianDate) {
        if (gregorianDate == null) return null;
        return PersianDate.fromGregorian(gregorianDate);
    }

    // Persian LocalDate (year/month/day in Jalali numbers)
    // → real Gregorian LocalDate (for DB storage)
    public static LocalDate persianLocalDateToGregorian(LocalDate persianLocalDate) {

        if (persianLocalDate == null) {
            return null;
        }

        PersianDate persianDate = PersianDate.of(
                persianLocalDate.getYear(),
                persianLocalDate.getMonthValue(),
                persianLocalDate.getDayOfMonth()
        );

        return persianDate.toGregorian();
    }

    public static LocalDate gregorianToShamsiLocalDate(LocalDate gregorianDate) {
        if (gregorianDate == null) return null;

        PersianDate p = gregorianToShamsi(gregorianDate);

        return LocalDate.of(
                p.getYear(),
                p.getMonth().getValue(),
                p.getDayOfMonth()
        );
    }


    public static LocalDate persianLocalDateToGregorian(Date persianDate) {
        if (persianDate == null) {
            return null;
        }

        LocalDate persianLocalDate = persianDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        PersianDate persian = PersianDate.of(
                persianLocalDate.getYear(),
                persianLocalDate.getMonthValue(),
                persianLocalDate.getDayOfMonth()
        );

        return persian.toGregorian();
    }

    public static LocalDate gregorianToShamsiLocalDate(Date gregorianDate) {
        if (gregorianDate == null) return null;

        LocalDate gregorianLocalDate = gregorianDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        PersianDate p = gregorianToShamsi(gregorianLocalDate);

        return LocalDate.of(
                p.getYear(),
                p.getMonth().getValue(),
                p.getDayOfMonth()
        );
    }


}

