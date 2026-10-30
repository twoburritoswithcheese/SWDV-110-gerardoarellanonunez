package edu.cwi.swdv110.calendar;


import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/** Static date helpers used by Semester and App. */
public final class DateTools {

// TODO Part 1: a formatter for "Mon, Jan 11" (pattern letters: EEE, MMM, d)
private static final DateTimeFormatter FORMAT_DATE = 
        DateTimeFormatter.ofPattern("EEE, MMM d", Locale.US);

// TODO Part 2: a formatter for "Mon, Jan 11 at 6:00 PM MST"
// (hint: literal text goes in single quotes: 'at')
private static final DateTimeFormatter ZONE_FORMAT = 
        DateTimeFormatter.ofPattern("EEE, MMM d 'at' h:mm a z", Locale.US);

// TODO Part 2: a List<DateTimeFormatter> of the three accepted input formats:
// 2027-03-15 3/15/2027 Mar 15, 2027
private static final List<DateTimeFormatter> FORMAT_LIST = List.of(
        DateTimeFormatter.ofPattern("uuuu-M-d", Locale.US).withResolverStyle(ResolverStyle.STRICT),
        DateTimeFormatter.ofPattern("M/d/uuu", Locale.US).withResolverStyle(ResolverStyle.STRICT),
        DateTimeFormatter.ofPattern("MMM d, uuuu", Locale.US).withResolverStyle(ResolverStyle.STRICT)
    );
    

// Always pass Locale.US to ofPattern so "Mon"/"PM" don't change on other computers.
// For the INPUT formatters use "uuuu" (not "yyyy") for the year and add
// .withResolverStyle(ResolverStyle.STRICT) – see the Part 2 notes for why.


private DateTools() { }


// ---------------- Part 1 ----------------


/** The nth given weekday of a month, e.g. (2027, JANUARY, 3, MONDAY) -> 2027-01-18 (MLK Day). */
public static LocalDate nthWeekday(int year, Month month, int n, DayOfWeek day) {
    return LocalDate.of(year, month, n).with(TemporalAdjusters.dayOfWeekInMonth(n, day));
}


/** The last given weekday of a month, e.g. (2027, MAY, MONDAY) -> 2027-05-31 (Memorial Day). */
public static LocalDate lastWeekday(int year, Month month, DayOfWeek day) {
    return LocalDate.of(year, month, 1).with(TemporalAdjusters.lastInMonth(day));
}


/** "Mon, Jan 11" */
public static String formatShort(LocalDate date) {
    return FORMAT_DATE.format(date);
}


/** True for Saturday and Sunday. */
public static boolean isWeekend(LocalDate date) {
    DayOfWeek weekend = date.getDayOfWeek();

    DayOfWeek saturday = DayOfWeek.SATURDAY;
    DayOfWeek sunday = DayOfWeek.SUNDAY;

    if (weekend == saturday || weekend == sunday) {
        return true;
    }

    return false;
}


// ---------------- Part 2 ----------------


/**
* Accepts "2027-03-15", "3/15/2027" or "Mar 15, 2027" (surrounding spaces allowed).
* Anything else -> IllegalArgumentException("Unrecognized date: " + text).
* Hint: try each formatter in turn and catch DateTimeParseException.
*/
public static LocalDate parseFlexible(String text) {
    int badFormat = 0;
    int match = 0;

    // check each format
    for (int i = 0; i < FORMAT_LIST.size(); i++) {
        try {
            LocalDate.parse(text, FORMAT_LIST.get(i));
            match = i;
            break;
        } catch (DateTimeParseException dtpe) {
            badFormat++;
            continue;
        }
    }

    // check if there is no match
    if (badFormat == 3) {
        throw new IllegalArgumentException("Unrecognized date: " + text);
    } else {
        return LocalDate.parse(text, FORMAT_LIST.get(match));
    }
}


/**
* Counts forward `days` school days from `start` (start itself is not counted).
* Weekends and dates in `holidays` are skipped.
* Fri 2027-01-08 + 1 school day -> Mon 2027-01-11
*/
public static LocalDate addSchoolDays(LocalDate start, int days, Set<LocalDate> holidays) {
    LocalDate newDate = start;
    int addDay = 0;

    while (addDay < days) {
        newDate = newDate.plusDays(1);

        // Skip weekends
        if (isWeekend(newDate)) {
            continue;
        }

        // Skip holidays
        if (holidays.contains(newDate)) {
            continue;
        }

        addDay++;
    }

    return newDate;

}


/**
* Late policy: 10% off for each 24 hours (or part of 24 hours) late, maximum 50%.
* on time or early -> 0
* 1 minute late -> 10
* exactly 24 hours late -> 10
* 24 hours + 1 minute -> 20
* a week late -> 50
* Hint: Duration.between(...).toMinutes()
*/
public static int latePenaltyPercent(LocalDateTime due, LocalDateTime submitted) {
    long minutesLate = Duration.between(due, submitted).toMinutes();
    
    // on time or early
    if (minutesLate <= 0) {
        return 0;
    } 

    long twentyFourHours = 1440;

    long days = minutesLate / twentyFourHours;

    // check if its more than 5 days late; max fee 50
    if (days >= 5) {
        return 50;
    }
    
    // Calculate fee for each 24 hour part
    long lateFee = days * 10;
    long lateMinutes = lateFee % 10;

    if (lateMinutes == 0) {
        return (int) lateFee;
    } else {
        return ((int)lateFee + 10);
    }
} 


/** Converts to another zone and formats: "Tue, Jan 12 at 10:00 AM JST" */
public static String inZone(ZonedDateTime when, ZoneId zone) {
    return when.withZoneSameInstant(zone).format(ZONE_FORMAT);
}
}