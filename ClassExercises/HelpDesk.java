package ClassExercises;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import java.util.ArrayList;

public class HelpDesk {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        Deque<String> line = new ArrayDeque<>();
        line.offer("Ana");
        line.offer("Kai");
        line.offer("Zoe");
        
        boolean running = true;

        while (running) {
            System.out.println("========= MENU =========");
            System.out.print("new\nnext\npeek\ncount\nquit\n|: ");
            String input = sc.nextLine();
            String[] inputs = input.split(" ");

            switch (inputs[0]) {
                case "new":
                    line.offer(inputs[1]);
                    System.out.printf("%s has been added to the line", inputs[1] );
                    break;
                case "next":
                    line.poll();
                    break;
                case "peek":
                    System.out.println(line.peek());
                    break;
                case "count":
                    
                    break;
                case "quit":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
        sc.close();
    }
}
