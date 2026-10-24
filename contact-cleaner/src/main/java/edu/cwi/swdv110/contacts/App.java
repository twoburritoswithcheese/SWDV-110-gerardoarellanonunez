package edu.cwi.swdv110.contacts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of("contacts.txt"));
        List<Contact> contacts = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            
            
            // - skip blank lines and lines that start with '#'
            if (line.isBlank() || line.trim().startsWith("#")) {
                continue;
            }
            
            // - parseLine(...) each remaining line and add it to contacts
            // - catch IllegalArgumentException and print "Skipped line N: <message>"
            try {
                contacts.add(ContactParser.parseLine(line));
            } catch (IllegalArgumentException e) {
                System.out.printf("Skipped line %d: %s\n", i+1, e.getMessage());
            }    
        }
        
        // Print report
        System.out.println(ContactReport.buildReport(contacts));

        Scanner sc = new Scanner(System.in);

        // run app unless user 'quit'
        while (true) {
            System.out.print("Search (or 'quit'): ");
            String input = sc.nextLine().toLowerCase();

            if (input.equals("quit")) {
                break;
            }

            int matches = 0;

            // search for user input
            for (Contact contact : contacts) {
                if (contact.name().toLowerCase().contains(input)) {
                    System.out.printf("%s | %s\n", contact.name(), ContactParser.maskEmail(contact.email()));
                    matches++;
                }
            }
            // display the N times user input was found 
            System.out.printf("%d match(es)\n", matches);
        }
        sc.close();
    }
}