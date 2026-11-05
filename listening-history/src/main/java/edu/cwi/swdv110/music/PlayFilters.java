package edu.cwi.swdv110.music;

import java.time.LocalDate;
import java.util.function.Predicate;

/** Factory methods that build reusable Predicate<Play> objects. Each body is ONE lambda. */
public final class PlayFilters {

    private PlayFilters() { }

    /** Matches plays by this user, ignoring case. */
    public static Predicate<Play> byUser(String user) {
        return p -> p.user().equalsIgnoreCase(user);
    }

    /** Matches plays in this genre, ignoring case. */
    public static Predicate<Play> byGenre(String genre) {
        return p -> p.genre().equalsIgnoreCase(genre);
    }

    /** Matches plays from `from` through `to`, inclusive on both ends. */
    public static Predicate<Play> between(LocalDate from, LocalDate to) {
        return p -> !p.date().isBefore(from) && !p.date().isAfter(to);
    }

    /** A "skip" is any play shorter than 30 seconds. */
    public static Predicate<Play> skipped() {
        return p -> p.seconds() < 30;
    }
}