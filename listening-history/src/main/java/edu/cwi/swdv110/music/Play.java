package edu.cwi.swdv110.music;

import java.time.LocalDate;

/** One time a user played a song. */
public record Play(String user, String song, String artist, String genre, LocalDate date, int seconds) {

    /**
     * Parses "maya,Paper Satellites,Static Bloom,Indie,2026-09-28,161".
     * Trim every field. Wrong number of fields -> IllegalArgumentException.
     * (An empty song title is allowed here – PlayCleaner removes it later.)
     */
    public static Play parse(String csvLine) {
        String[] fields = csvLine.split(",", -1);

        // check number of fields
        if (fields.length != 6) {
            throw new IllegalArgumentException("Must contain 6 fields");
        }

        String user = fields[0].trim();
        String song = fields[1].trim();
        String artist = fields[2].trim();
        String genre = fields[3].trim();
        LocalDate date = LocalDate.parse(fields[4].trim());
        int seconds = Integer.parseInt(fields[5].trim());

        Play play = new Play(user, song, artist, genre, date, seconds);

        return play;
    }
}