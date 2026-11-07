

import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;

public class LambdaCalculator {
    public static void main(String[] args) {

        @FunctionalInterface 
        interface MathOperation {
            int apply(int a, int b);
        }

        Map<String, MathOperation> calculator = new TreeMap<>();

        calculator.put("+", (a, b) -> a + b);
        calculator.put("-", (a, b) -> a - b);
        calculator.put("*", (a, b) -> a * b);
        calculator.put("/", (a, b) -> a / b);
        calculator.put("%", (a, b) -> a % b);

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter operation: ");
        String userInput = sc.nextLine();
   
        String[] operation = userInput.split(" ");

        if (calculator.containsKey(operation[1].trim())) {

            int a = Integer.parseInt(operation[0]);
            int b = Integer.parseInt(operation[2]);
            MathOperation c = calculator.get(operation[1]);

            System.out.println(c.apply(a,b));
        } else {
            System.out.println("Unkown operator");
        }

        sc.close();
    }
}