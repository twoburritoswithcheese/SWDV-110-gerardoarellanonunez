package NameEmailParser;

import java.util.Scanner;

public class NameEmailParser {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter name: ");
        String fullName = sc.nextLine();

        int firstSpace = fullName.indexOf(" ");
        int secondSpace = fullName.indexOf(" ", firstSpace + 1);

        String firstName = fullName.substring(0, firstSpace);
        String middleName = fullName.substring(firstSpace, secondSpace);
        String lastName = fullName.substring(secondSpace);

        String firstInitial = firstName.substring(0, 1);
        String secondInitial = middleName.substring(0, 2);
        String thirdInitial = lastName.substring(0, 2);

        // Print first and last name
        System.out.println(firstName + " " + lastName);
        System.out.printf("%s.%s.%s.\n", firstInitial, secondInitial, thirdInitial);

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        int at = email.indexOf("@");
        
        sc.close();
    }
}