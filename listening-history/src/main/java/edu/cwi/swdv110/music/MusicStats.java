package edu.cwi.swdv110.music;

import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/** Every method is a stream pipeline – no for or while loops in this class! */
public final class MusicStats {

    private MusicStats() { }

    /**
     * Newest date first; same date -> artist A–Z; same artist -> song A–Z.
     * TODO Part 3: build it with Comparator.comparing(...).reversed().thenComparing(...)
     */
    public static final Comparator<Play> NEWEST_FIRST = Comparator.comparing(Play::date)
            .reversed()
            .thenComparing(Play::artist)
            .thenComparing(Play::song);

    /** Total seconds of the plays matching `filter`, divided by 60 (whole minutes). */
    public static long totalMinutes(List<Play> plays, Predicate<Play> filter) {
        return plays.stream()
                    .filter(filter)
                    .mapToInt(Play::seconds)
                    .sum() / 60;
    }

    /** genre -> number of plays, sorted by genre. */
    public static Map<String, Long> playsPerGenre(List<Play> plays) {
        return plays.stream().collect(Collectors.groupingBy(
                        Play::genre,
                        TreeMap::new,
                        Collectors.counting()
                    ));
    }

    /** user -> total seconds, sorted by user. */
    public static Map<String, Integer> secondsPerUser(List<Play> plays) {   
        return plays.stream().collect(Collectors.groupingBy(
                        Play::user,
                        TreeMap::new,
                        Collectors.summingInt(Play::seconds)
                    ));
    }

    /**
     * The n artists with the most plays, most first. Ties: artist name A–Z.
     * Hint: group & count into a Map, then stream its entrySet() and sort with
     * Map.Entry.<String, Long>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey())
     */
    public static List<String> topArtists(List<Play> plays, int n) {

        return plays.stream()
                    .collect(Collectors.groupingBy(
                        Play::artist,
                        Collectors.counting()))
                        .entrySet()
                        .stream()
                        .sorted(
                            Map.Entry.<String, Long>comparingByValue()
                                .reversed()
                                .thenComparing(Map.Entry.comparingByKey()))
                        .limit(n)
                        .map(Map.Entry::getKey)
                        .toList();
    }

    /** The single longest play, or Optional.empty() for an empty list. */
    public static Optional<Play> longestPlay(List<Play> plays) {
        return plays.stream().max(Comparator.comparingInt(Play::seconds));
    }

    /** Distinct song titles by this artist (ignore case), sorted A–Z. */
    public static List<String> songsBy(List<Play> plays, String artist) {

        return plays.stream()
                    .filter(p -> p.artist().equalsIgnoreCase(artist))
                    .map(Play::song)
                    .distinct()
                    .sorted()
                    .toList();
    }

    /** The n most recent plays, ordered by NEWEST_FIRST. */
    public static List<Play> recent(List<Play> plays, int n) {
        return plays.stream()
                    .sorted(NEWEST_FIRST)
                    .limit(n)
                    .toList();
    }

    /** MONDAY..SUNDAY -> number of plays (only days that have plays). Uses Week 3's java.time! */
    public static Map<DayOfWeek, Long> playsByDay(List<Play> plays) {
        return plays.stream().collect(Collectors.groupingBy(
                        p -> p.date().getDayOfWeek(),
                        TreeMap::new,
                        Collectors.counting()));
    }

    /** Fraction (0.0–1.0) of this user's plays that were skips. 0.0 if the user has no plays. */
    public static double skipRate(List<Play> plays, String user) {
        long total = plays.stream()
                          .filter(PlayFilters.byUser(user))
                          .count();


        long skips = plays.stream()
                          .filter(PlayFilters.byUser(user))
                          .filter(PlayFilters.skipped())
                          .count();

        if (total == 0) {
            return 0.0;
        }

        return (double) skips / total;
    }

    /**
     * The genre this user played most. Ties go to the genre that comes first A–Z.
     * Optional.empty() if the user has no plays.
     */
    public static Optional<String> topGenre(List<Play> plays, String user) {
        return plays.stream()
            .filter(PlayFilters.byUser(user))
            .collect(Collectors.groupingBy(
                    Play::genre,
                    TreeMap::new,
                    Collectors.counting()))
            .entrySet()
            .stream()
            .max(Map.Entry.<String, Long>comparingByValue()
                    .thenComparing(Map.Entry.<String, Long>comparingByKey().reversed()))
            .map(Map.Entry::getKey);
    }
}