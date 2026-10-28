package ClassAssignments;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class BirthdayExplorer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LocalDate currentDate = LocalDate.now();
    
        System.out.println("Welcome to Birthday Explorer.");

        LocalDate dateOfBirth = getDateOfBirth(sc);

        // Get data from birthday
        DayOfWeek dayOfWeek = dateOfBirth.getDayOfWeek();
        int dayOfYear = dateOfBirth.getDayOfYear();
        boolean isLeap = dateOfBirth.isLeapYear();
        LocalDate thisYearBirthday = dateOfBirth.withYear(2026);

        // Get day of week for next 5 years
        ArrayList<DayOfWeek> daysOfWeeks = new ArrayList<>();
        int year = 2027;
        for ( int i = 0; i < 5; i++) {
            LocalDate placeHolder = dateOfBirth.withYear(year);
            daysOfWeeks.add(placeHolder.getDayOfWeek());
            year++;
        }

        // Print out results
        System.out.println("\nDay of the week you were born: " + dayOfWeek);
        System.out.println("Day of the year: " + dayOfYear);

        if (isLeap) {
            System.out.println("Your birthday was on a leap year.");
        } else {
            System.out.println("Your birthday was not on a leap year.");
        }

        if (thisYearBirthday.isAfter(currentDate)) {
            System.out.println("Your birthday has not passed.");
        } else {
            System.out.println("Your birthday has already passed.");
        }

        // Print days birthday falls on for the next 5 years
        System.out.println("\nBirthday days for the next 5 years");
        int years = 2027;

        for (DayOfWeek date : daysOfWeeks) {
            System.out.println( years + ": " +date);
            years++;
        }

        // Close scanner
        sc.close();
    }

    private static LocalDate getDateOfBirth(Scanner sc) {
        LocalDate currentDate = LocalDate.now();
        LocalDate dob;

         while (true) {
            System.out.print("\nEnter your date of birth (YYYY-MM-DD): ");
            String input = sc.nextLine();

            try {
                dob = LocalDate.parse(input);

            } catch (DateTimeParseException e) {
                System.out.println("\n ** Invalid format.");
                continue;
            } 

            if (dob.isAfter(currentDate)) {
                System.out.println("\n ** Enter a date before current date.");
                continue;
            } 

            return dob;
        } 
    }
}