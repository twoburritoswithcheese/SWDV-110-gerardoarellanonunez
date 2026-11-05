package edu.cwi.swdv110.music;

import java.util.List;
import java.util.function.UnaryOperator;

/** Fixes messy rows IN PLACE using the List methods that take lambdas. */
public final class PlayCleaner {

    private PlayCleaner() { }

    /** "  hip-HOP " -> "Hip-Hop",  "ELECTRONIC" -> "Electronic"  (capitalize after '-' and ' ') */
    public static String titleCase(String text) {
        // check if string is empty
        if (text == null || text == "") {
            return "";
        }

        StringBuilder textString = new StringBuilder(text.toLowerCase().trim());

        // Uppercase first letter of string
        textString.setCharAt(0, Character.toUpperCase(textString.charAt(0)));

        // check if string contains hyphen or a space in between chars
        if (text.contains(String.valueOf('-')) || text.contains(String.valueOf(' '))) {
            for (int i = 1; i < textString.length(); i++) {
                if (textString.charAt(i-1) == '-' || textString.charAt(i-1) == ' ') {
                    textString.setCharAt( 
                        i, Character.toUpperCase(textString.charAt(i)));
                }
            }
        } 
        
        return textString.toString();
    }

    /**
     * 1. removeIf: delete plays with seconds <= 0 or a blank song title.
     * 2. replaceAll: records are immutable, so replace each Play with a NEW Play whose
     *    user is lowercase and whose genre is title-cased. Write the lambda as a
     *    UnaryOperator<Play> variable first, then pass it to replaceAll.
     * Returns how many plays were removed.
     */
    public static int clean(List<Play> plays) {
        // check size before removing anything
        int playsSize = plays.size();

        // remove plays with 0 seconds or blank song
        plays.removeIf(p -> p.seconds() <= 0 || p.song().isBlank());

        UnaryOperator<Play> op = p -> new Play(p.user().toLowerCase(),
                p.song(),
                p.artist(),
                titleCase(p.genre()),
                p.date(),
                p.seconds());
        
        plays.replaceAll(op);

        return playsSize - plays.size();
    }
}