package edu.cwi.swdv110.contacts;

import java.util.List;

/** Builds the printable report for a list of cleaned contacts. */
public final class ContactReport {

/** Use this format for the header and every row so the columns line up. */
private static final String ROW = "%-22s %-30s %-15s %-9s%n";

private ContactReport() { }

/**
* Returns the full report as ONE string (see the sample output in Part 5):
* - a header row: NAME EMAIL PHONE USERNAME
* - a line of 78 dashes
* - one row per contact; put "!! " in front of an invalid email
* - another line of dashes
* - "Total contacts: N | Invalid emails: N | Invalid phones: N"
*/
public static String buildReport(List<Contact> contacts) {
    StringBuilder report = new StringBuilder();

    // Print header with line across
    report.append(String.format(ROW, "NAME","EMAIL","PHONE","USERNAME"));
    report.append("-".repeat(78) + "\n");

    int invalidEmail = 0;
    int invalidPhone = 0;
   
    for (Contact contact : contacts) {
        String email;

        // check that email is valid otherwise add '!!' in front of e-mail
        if(!ContactParser.isValidEmail(contact.email())) {
            invalidEmail++;
            email = "!!" + " " + contact.email();    
        } else {
            email = contact.email();
        }

        // check if phone number is 'INVALID'
        if ("INVALID".equals(contact.phone())) {
            invalidPhone++;
        }

        String username = ContactParser.makeUsername(contact.name());

        // create new line with correct format
        report.append(String.format(
                ROW,
                contact.name(),
                email,
                contact.phone(),
                username));
    }
    
    report.append("-".repeat(78) + "\n");
    report.append(String.format("Total contacts: %d | Invalid emails: %d | Invalid phones: %d",
        contacts.size(), invalidEmail, invalidPhone
    ));

    return report.toString();
}
}