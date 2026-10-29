package ClassAssignments;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class LibraryDueDates {
    public static void main(String[] args) {
        final int DUE_DAYS = 21;

        // books are due in 21 days
        LocalDate checkoutDate = LocalDate.now().plusDays(DUE_DAYS);

        // check if due date falls on weekend, move it to Monday
        String isWeekend = checkoutDate.getDayOfWeek().toString();

        if (isWeekend.equals("SATURDAY")) {
            checkoutDate.plusDays(2);
        } else if (isWeekend.equals("SUNDAY")) {
            checkoutDate.plusDays(1);
        }

        // print the due date and how many days unitl it is due
        System.out.printf("The book is due in %d days: %s",
                DUE_DAYS, checkoutDate.toString());

    
        // ask for a return date; if late, charge $0.25 per day

        // print the fee formatted as currency

        // Stretch: store due dates for several books in a TreeMap<LocalDate, String>
    }
}