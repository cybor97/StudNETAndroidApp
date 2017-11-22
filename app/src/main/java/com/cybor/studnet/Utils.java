package com.cybor.studnet;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;

import java.util.Calendar;

public class Utils {
    private static DateTime FIRST_WEEK = DateTime.now()
            .withMonthOfYear(9)
            .withDayOfMonth(1)
            .withHourOfDay(8)
            .withMinuteOfHour(30)
            .withDayOfWeek(1);

    public static DateTime getCurrentTime() {
        return DateTime
                .now(DateTimeZone.forTimeZone(Calendar.getInstance().getTimeZone()))
                .withYear(1970).withMonthOfYear(1).withDayOfMonth(1).plusHours(1);
    }

    public static boolean isEvenWeek(DateTime date) {
        return new Period(FIRST_WEEK, date.withDayOfWeek(1)).getWeeks() % 2 == 0;
    }
}