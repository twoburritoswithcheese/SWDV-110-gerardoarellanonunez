package ClassAssignments;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class LibraryDueDates {
    public static void main(String[] args) {
        final int DUE_DAYS = 21;

        // books are due in 21 days
        LocalDate checkoutDate = LocalDate.now();
        LocalDate dueDate = checkoutDate.plusDays(DUE_DAYS);

        // check if due date falls on weekend, move it to Monday
        String isWeekend = dueDate.getDayOfWeek().toString();

        if (isWeekend.equals("SATURDAY")) {
            dueDate.plusDays(2);
        } else if (isWeekend.equals("SUNDAY")) {
            dueDate.plusDays(1);
        }

        // format due date 
        DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
        String formattedDueDate = dtf.format(dueDate);

        long daysUntilDue = checkoutDate.until(dueDate, ChronoUnit.DAYS);

        // print the due date and how many days unitl it is due
        System.out.println("\nBook has been checked out.");
        System.out.printf("Book Due Date: %s\nDays until it is due: %d\n",
        formattedDueDate, daysUntilDue);
        
        // collect return date and verify correct date entered; if late, charge $0.25 per day
        Scanner sc = new Scanner(System.in);
        LocalDate returnDate = null;

        while (true) {
            System.out.println("================================");
            System.out.print("Enter return date (YYYY-MM-DD): ");
            String input = sc.nextLine();
            
            try {
                returnDate = LocalDate.parse(input);
            } catch (DateTimeParseException dtpe) {
                System.out.println("\nInvalid date format.");
                continue;
            }
            break;
        }

        float lateFee = 0.0f;
        float fee = 0.25f;
        
        if (returnDate.isBefore(dueDate) && returnDate.isAfter(checkoutDate)) {
            System.out.printf("Book succesfully returned.\nLate Fee: $%.2f\n", lateFee);
        } else if (returnDate.isAfter(dueDate)) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
            lateFee = fee * daysLate;

            System.out.printf("!! Book is late.\nLate Fee: $%.2f\n", lateFee);
        }

        sc.close();
    }
}