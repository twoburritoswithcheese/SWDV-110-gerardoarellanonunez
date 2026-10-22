package ClassAssignments;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;

public class ListManager {
    public static void main(String[] args) {
        List<String> tasks = new ArrayList<>();    
        Scanner sc = new Scanner(System.in);
        String menu = "\nadd\nremove\nlist\ndone\nquit\n |: ";
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("============ MENU ============");
            System.out.print("Enter option plus task: 'add candy':");
            System.out.print(menu);
            String input = sc.nextLine();

            String[] option = input.split(" ");

            switch (option[0]) {
                case "add":
                    // Add
                    tasks.add(option[1]);
                    break;
                case "remove":
                    // Remove
                    tasks.remove(option[1]);
                    break;
                case "list":
                    // List
                    System.out.println("+--------- Tasks ---------+");
                    Collections.sort(tasks);
                    int count = 1;
                    for (String t : tasks) {
                        System.out.println(count +". " + t);
                        count++;
                    }
                    break;
                case "done":
                    int index = Integer.parseInt(option[1]);
                    tasks.remove(index-1);
                    break;
                case "quit":
                    System.out.println("You have exited the application");
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
