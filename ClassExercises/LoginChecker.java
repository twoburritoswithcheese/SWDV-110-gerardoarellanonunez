package LoginChecker;

import java.util.ArrayList;
import java.util.Scanner;

public class LoginChecker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> attempts = new ArrayList<>();

        String usernameOne = "admin";
        String passwordOne = "Secret123";

        while (true) {
            System.out.print("Username: ");
            String usernameTwo = sc.nextLine();
            System.out.print("Password: ");
            String passwordTwo = sc.nextLine();

            if (usernameTwo.equalsIgnoreCase(usernameOne) && passwordTwo.equals(passwordOne)) {
                System.out.println("Welcome");
                break;
            } else {
                attempts.add(usernameTwo);
                System.out.println("Access Denied");
                continue;
            }
        }
        for (String attempt : attempts) {
            System.out.println(attempt);
        }
        sc.close();
    }
}
