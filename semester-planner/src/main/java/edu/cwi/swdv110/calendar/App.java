package edu.cwi.swdv110.calendar;


import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class App {


    static final ZoneId CAMPUS = ZoneId.of("America/Boise");


    public static void main(String[] args) {
        // Example semester – these dates are for practice, not the official CWI calendar.
        Semester sem = new Semester("SWDV-110 – Spring 2027",
        LocalDate.of(2027, 1, 11), LocalDate.of(2027, 5, 7),
        Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
        LocalTime.of(18, 0), LocalTime.of(20, 30));
        sem.addHoliday(DateTools.nthWeekday(2027, Month.JANUARY, 3, DayOfWeek.MONDAY)); // MLK Day
        sem.addHoliday(DateTools.nthWeekday(2027, Month.FEBRUARY, 3, DayOfWeek.MONDAY)); // Presidents' Day
        sem.addBreak(LocalDate.of(2027, 3, 22), LocalDate.of(2027, 3, 26)); // Spring break


        // print the name, a line of '=' the same length, then every meeting:
        // "Week N" (only on the first meeting of each week) and the formatted date
        System.out.println(sem.getName());
        System.out.println("=".repeat(sem.getName().length()));

        int previousWeek = 0;
        List<LocalDate> meetings = sem.meetingDates();

        for (LocalDate meeting : meetings) {
            int week = sem.weekNumber(meeting);

            if (week != previousWeek) {
                System.out.println("Week " + week + " " + DateTools.formatShort(meeting));
                previousWeek = week;
            } else {
                System.out.println(DateTools.formatShort(meeting));
            }
        }


        // print days off, number of meetings, total class time as "75h 0m"
        // (toHours, toMinutesPart) and the semester length as "3 months, 26 days"
        Duration total = sem.totalClassTime();
        Period length = sem.length();

        System.out.println();
        System.out.println("Days off: " + sem.getDaysOff().size());
        System.out.println("Meetings: " + meetings.size());
        System.out.println("Class time: " + total.toHours()
                + "h " + total.toMinutesPart() + "m");
        System.out.println("Length: " + length.getMonths()
                + " months, " + length.getDays() + " days");

        // build a ZonedDateTime for the FIRST meeting at the class start time
        // in CAMPUS, and print it for America/Boise, America/Los_Angeles,
        // America/New_York and Asia/Tokyo using DateTools.inZone

        System.out.println();
        System.out.println("First class, for online students:");

        LocalDate firstMeeting = meetings.get(0);

        ZonedDateTime firstClass = ZonedDateTime.of(firstMeeting, sem.getClassStart(),
                CAMPUS);

        List<ZoneId> zones = List.of(
                ZoneId.of("America/Boise"),
                ZoneId.of("America/Los_Angeles"),
                ZoneId.of("America/New_York"),
                ZoneId.of("Asia/Tokyo"));

        for (ZoneId zone : zones) {
            System.out.println(zone + " "
                    + DateTools.inZone(firstClass, zone));
        }


        // loop – read a date (any of the 3 formats) or 'quit'. Print:
        // the next class and its week number, days until the semester ends, and the
        // due date of a lab assigned at that class (5 school days, skipping days off).
        // If there are no more classes say so. Catch IllegalArgumentException for bad input.
        //Scanner sc = new Scanner(System.in);

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println();
                System.out.print("Enter a date (or 'quit'): ");

                if (!sc.hasNextLine()) {
                    break;
                }

                String input = sc.nextLine().trim();

                if (input.equalsIgnoreCase("quit")) {
                    break;
                }

                try {
                    LocalDate requested = DateTools.parseFlexible(input);

                    LocalDate next = sem.nextMeeting(requested);

                    if (next == null) {
                        System.out.println("No more classes - enjoy your summer!");
                        continue;
                    }

                    System.out.println("Next class: " + DateTools.formatShort(next)
                            + " (week " + sem.weekNumber(next) + ")");

                    System.out.println("Days until the semester ends: " + sem.daysLeft(requested));

                    LocalDate due = DateTools.addSchoolDays(next, 5, sem.getDaysOff());

                    System.out.println("A lab assigned that day is due " + DateTools.formatShort(due));

                } catch (IllegalArgumentException ex) {
                    System.out.println("Unrecognized date: " + input + " - try 2027-03-15, 3/15/2027 or Mar 15, 2027");
                }
            }
        }
    }
}