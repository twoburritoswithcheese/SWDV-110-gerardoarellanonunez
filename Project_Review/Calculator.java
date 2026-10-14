import java.util.Scanner;
import java.util.ArrayList;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String prompt1 = "\nEnter a digit for 'x': ";
        String prompt2 = "\nEnter a digit for 'y': ";

        float x = getX(prompt1, scanner);
        float y = getY(prompt2, scanner);

        String add = String.format("%.1f + %.1f = " + (x + y), x, y);
        String subtract = String.format("%.1f - %.1f = " + (x - y), x, y);
        String multiply = String.format("%.1f * %.1f = " + (x * y), x, y);
        String divide = String.format("%.1f / %.1f = " + (x / y), x, y);

        ArrayList<String> transactions = new ArrayList<>();

        transactions.add(add);
        transactions.add(subtract);
        transactions.add(multiply);
        transactions.add(divide);

        System.out.println("-------------------------------");
        for ( String transaction : transactions) {
            System.out.println(transaction);
        } 
        System.out.println("-------------------------------");
        scanner.close();

    }

    public static float getX(String prompt1, Scanner scanner) {
        float x;
        while (true) {
            try {
                System.out.print(prompt1);
                x = Float.parseFloat(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Not a number");
                continue;
            }
            return x;
        }
    }


    public static float getY(String prompt2, Scanner scanner) {
        float y;
        while (true) {
            try {
                System.out.print(prompt2);
                y = Float.parseFloat(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Not a number");
                continue;
            }
            return y;
        }
    }
}