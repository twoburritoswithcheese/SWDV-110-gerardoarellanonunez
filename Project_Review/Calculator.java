package Project_Review;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Calculator {
    public static void main(String[] args) {
        String file = "transactions.txt";
        Scanner scanner = new Scanner(System.in);

        String prompt1 = "\nEnter a digit for 'x': ";
        String prompt2 = "\nEnter a digit for 'y': ";

        float x = getX(prompt1, scanner);
        float y = getY(prompt2, scanner);

        // Print values to the console.
        printResults(x,y);
        // Write transactions to a file.
        writeTransactions(x, y, file);

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


    public static void printResults(float x, float y) {
        ArrayList<Float> values = new ArrayList<>();

        values.add(x+y);
        values.add(x-y);
        values.add(x*y);
        values.add(x/y);

        for (float value : values) {
            System.out.printf("%.2f\n", value);
        }
    }


    public static void writeTransactions(float x, float y, String file) {
        ArrayList<String> transactions = new ArrayList<>();

        String stringAdd = String.format("%.2f + %.2f = " + (x + y), x, y);
        String stringSubtract = String.format("%.2f - %.2f = " + (x - y), x, y);
        String stringMultiply = String.format("%.2f * %.2f = " + (x * y), x, y);
        String stringDivide = String.format("%.2f / %.2f = " + (x / y), x, y);

        transactions.add(stringAdd);
        transactions.add(stringSubtract);
        transactions.add(stringMultiply);
        transactions.add(stringDivide);

        try (PrintWriter out = new PrintWriter(
                         new BufferedWriter(
                         new FileWriter(file)))) {
                            
            for (String transaction : transactions) {
                out.println(transaction);
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}