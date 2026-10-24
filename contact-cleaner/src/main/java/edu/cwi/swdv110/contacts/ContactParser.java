package edu.cwi.swdv110.contacts;

/** Static helper methods that clean up one piece of a messy contact line. */
public final class ContactParser {

private ContactParser() { }

/**
* Capitalizes the first letter of the word and every letter that follows a hyphen.
* All other letters become lowercase.
* "jOHN" -> "John"
* "garcia-LOPEZ" -> "Garcia-Lopez"
* "" -> ""
*/
public static String titleCase(String word) {
    if (word == null || word.isEmpty()) {
        return "";
    }
    // Grab the first ch in a string and change it to upper case
    StringBuilder text = new StringBuilder(word.toLowerCase());
    text.setCharAt(0, Character.toUpperCase(text.charAt(0)));

    // Check if name has a hyphen and change the letter after the hyphen
    for (int i = 1; i < text.length(); i++) {
        if (text.charAt(i - 1) == '-') {
            text.setCharAt(i, Character.toUpperCase(text.charAt(i)));
        }
    }

    return text.toString();
}

/**
* Cleans a raw name.
* - trims it and collapses runs of spaces to one space
* - "Last, First Middle" is reordered to "First Middle Last"
* - every word is title-cased
* Returns "" for a blank name.
* " smith, JOHN " -> "John Smith"
* "DE LA CRUZ, ana" -> "Ana De La Cruz"
* "maria garcia-lopez" -> "Maria Garcia-Lopez"
*/
public static String normalizeName(String raw) {

    String name = raw.trim().replaceAll("\\s+", " ");
  
    String first;
    String last;

    // Check if name is last name first followed by first name and separated by a ','
    if (name.contains(",")) {
        int index = name.indexOf(",");
        first = name.substring(index + 1).toLowerCase().trim();
        last = name.substring(0, index).toLowerCase().trim();

        name = first + " " + last;            
    } 

    
    String[] names = name.split(" ");
    StringBuilder titleCaseName = new StringBuilder();

    // change to upper case the first letter of name and add it to string.
    for (String n : names) {
        titleCaseName.append(titleCase(n) + " ");
    }

    return titleCaseName.toString().trim();
}

/**
* Returns true when the (trimmed) email:
* 1. has no spaces,
* 2. has exactly one '@' with at least one character before it, and
* 3. has a domain (the part after '@') that contains a '.' that is not its first
* character, does not end with '.', and never has two dots in a row.
*/
public static boolean isValidEmail(String email) {

    if (email == null || email.isEmpty()) {
        return false;
    }

    String validateEmail = email.trim();

    if (validateEmail.contains(" ")) {
        return false;
    }

    int at = validateEmail.indexOf("@");
    
    // check if '@' exist and there is no duplicate
    if (at <= 0 || at != validateEmail.lastIndexOf("@")) {
        return false;
    }

    // check if after '@' is empty 
    String afterAt = validateEmail.substring(at + 1);
    if (afterAt.isEmpty() || afterAt == null) {
        return false;
    }

    String domain = validateEmail.substring(at + 1);

    if (!domain.contains(".")) {
        return false;
    }

    if (domain.startsWith(".")
            || domain.endsWith(".")
            || domain.contains("..")) {
                return false;
    }

    return true;
}

/** Trims and lowercases an email: " JSmith@Example.COM " -> "jsmith@example.com" */
public static String normalizeEmail(String email) {
    return email.trim().toLowerCase();
}

/**
* Hides all but the first character before the '@':
* "jsmith@example.com" -> "j*****@example.com"
* If there are fewer than 2 characters before the '@' (or no '@'), return the email unchanged.
*/
public static String maskEmail(String email) {

    int at = email.indexOf("@");

    // check if email there are fewer than 2 ch before '@'
    if (at < 2 ) {
        System.out.println("email is short");
    }

    // mask email - repeat * using N of index and subtracting 1
    String maskedEmail = email.substring(0, 1)  
                        + "*".repeat(at -1) // repeat n - 1 
                        + email.substring(at);
    return maskedEmail;
}

/**
* Keeps only the digits. If there are 11 digits and the first is '1', drop that leading 1.
* Exactly 10 digits -> "(208) 555-0199". Anything else -> "INVALID".
*/
public static String formatPhone(String raw) {
    if (raw == null) {
        return "INVALID";
    }

    StringBuilder digits = new StringBuilder();

    // check if each ch in phone is a digit, if valid add to string
    for (char ch : raw.toCharArray()) {
        if (Character.isDigit(ch)) {
            digits.append(ch);
        }
    }

    if (digits.length() == 11 && digits.charAt(0) == '1') {
        digits.deleteCharAt(0);
    }

    if (digits.length() != 10) {
        return "INVALID";
    }

    // break the String phone number into parts
    String areaCode = digits.substring(0, 3);
    String firstSet = digits.substring(3, 6);
    String secondSet = digits.substring(6, 10);

    String phoneNumber = String.format("(%s) %s-%s", areaCode, firstSet, secondSet);

    return phoneNumber;
}

/**
* Username = first letter of the first word + the whole LAST word of a cleaned name,
* lowercase, letters only, at most 8 characters.
* "John Smith" -> "jsmith"
* "Maria Garcia-Lopez" -> "mgarcial"
* "Ana De La Cruz" -> "acruz"
* A one-word name uses just that word: "Bob" -> "bob"
*/
public static String makeUsername(String fullName) {
    if (fullName == null || fullName.isBlank()) {
        return "";
    }
    
    String[] name = fullName.trim().split("\\s+");
    String first = name[0];
    String last = name[name.length - 1];

    String combined;
    // combine first letter of name with lastname
    if (name.length == 1) {
        combined = first;
    } else {
        combined = first.substring(0, 1) + last;
    }

    // remove any symbols
    String username = combined.toLowerCase().replaceAll("[^a-z]", "");

    // trim username to length of 8 characters
    if (username.length() > 8 ) {
        return username.substring(0, 8);
    } 

    return username;
}

/**
* Parses one line of the file: "name | email | phone".
* Returns a Contact with a normalized name, normalized email and formatted phone.
* Throws IllegalArgumentException if the line does not have exactly 3 fields
* or the name is blank. (An invalid email or phone is NOT an exception –
* the report flags those.)
*/
public static Contact parseLine(String line) {
    if (line == null) {
        throw new IllegalArgumentException("Line cannot be empty");
    }

    String[] fields = line.split("\\|");

    // make sure there are 3 fields  in line
    if (fields.length != 3) {
        throw new IllegalArgumentException("Expected exactly 3 fields");
    }

    // grab each field from line
    String name = normalizeName(fields[0]).trim();
    String email = normalizeEmail(fields[1]).trim();
    String phone = formatPhone(fields[2]).trim();

    if (name.isBlank()) {
        throw new IllegalArgumentException("Name cannot be blank");
    }

    return new Contact(name, email, phone);
}
}