package bankaccount;


import java.util.Scanner;

public class BankAccountApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("===== BANK ACCOUNT APP =====");

        // Get customer information
        System.out.print("Enter your name: ");
        String name = input.nextLine();

        System.out.print("Enter opening deposit: $");
        double openingDeposit = input.nextDouble();

        while (openingDeposit < 0) {
            System.out.print("Deposit cannot be negative. Try again: $");
            openingDeposit = input.nextDouble();
        }

        // Create savings account
        SavingsAccount account =
                new SavingsAccount(name, openingDeposit, 0.02);

        System.out.println("\nWelcome, " + name + "!");
        System.out.printf("Opening balance: $%,.2f%n", openingDeposit);

        int choice;

        // Menu loop
        do {
            System.out.println("\n===== BANK MENU =====");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Add Interest");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");

            choice = input.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Enter deposit amount: $");
                    double depositAmount = input.nextDouble();
                    account.deposit(depositAmount);
                    break;

                case 2:
                    System.out.print("Enter withdrawal amount: $");
                    double withdrawAmount = input.nextDouble();

                    try {
                        account.withdraw(withdrawAmount);
                    } catch (InsufficientFundsException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    account.displayBalance();
                    break;

                case 4:
                    account.addInterest();
                    break;

                case 5:
                    System.out.println("Thank you for banking with us!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 5);

        input.close();
    }
}