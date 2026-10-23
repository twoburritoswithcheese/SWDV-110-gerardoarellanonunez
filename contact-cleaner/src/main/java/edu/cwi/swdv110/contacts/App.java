package edu.cwi.swdv110.contacts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws IOException {
        // Reading files is covered later in Murach – this one line gives you every line of the file.
        List<String> lines = Files.readAllLines(Path.of("contacts.txt"));
        List<Contact> contacts = new ArrayList<>();

        // TODO Part 5a: loop over lines
        // - skip blank lines and lines that start with '#'
        // - parseLine(...) each remaining line and add it to contacts
        // - catch IllegalArgumentException and print "Skipped line N: <message>"
        // (N is the line number in the file, starting at 1)

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            if (line.isBlank() || line.trim().startsWith("#")) {
                continue;
            }

            try {
                contacts.add(ContactParser.parseLine(line));
            } catch (IllegalArgumentException e) {
                System.out.printf("Skipped line %d: %s\n", i+1, e.getMessage());
            }    
        }
        // TODO Part 5b: print ContactReport.buildReport(contacts)
        System.out.println(ContactReport.buildReport(contacts));


        // TODO Part 5c: search loop
        // - prompt "Search (or 'quit'): "
        // - 'quit' (any capitalization) ends the program
        // - otherwise print every contact whose name contains the term, ignoring case,
        // showing the name and the MASKED email, then "N match(es)"
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Search (or 'quit'): ");
            String input = sc.nextLine().toLowerCase();

            if (input.equals("quit")) {
                break;
            }

            int matches = 0;

            for (Contact contact : contacts) {
                if (contact.name().toLowerCase().contains(input)) {
                    System.out.printf("%s | %s\n", contact.name(), ContactParser.maskEmail(contact.email()));
                    matches++;
                }
            }
            System.out.printf("%d match(es)\n", matches);
        }
        sc.close();
    }
}