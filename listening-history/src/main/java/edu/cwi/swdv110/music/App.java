package edu.cwi.swdv110.music;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) throws IOException {
        //   Part 4a: ONE stream pipeline: read all lines of plays.csv, skip the header,
        //   drop blank lines, map with Play::parse, and collect into an ArrayList
        //   (Collectors.toCollection(ArrayList::new) – clean() needs a list it can modify).
        List<Play> plays = Files.lines(Path.of("plays.csv"))
                .skip(1)
                .filter(line -> !line.isBlank())
                .map(Play::parse)
                .collect(Collectors.toCollection(ArrayList::new));

        //  Part 4b: clean the list and print "Loaded N plays (M bad rows removed)"
        int removed = PlayCleaner.clean(plays);

        System.out.printf("Loaded %d plays (%d bad rows removed)%n", plays.size(), removed);
        //   Part 4c: create two PlayFormatter lambdas:
        //   compact  -> "Afterglow – Lumen"
        //   detailed -> "2026-10-11  maya    Afterglow          Lumen              3:56"

        PlayFormatter compact = p -> p.song() + " - " + p.artist();
        PlayFormatter detailed = p -> String.format("%-12s %-7s %-19s %-19s %d:%02d",
            p.date(), p.user(), p.song(), p.artist(), p.seconds() / 60, p.seconds() % 60);

        //   Part 4d: print the reports shown in the instructions, using Map.forEach((k, v) -> ...)
        //   and Optional.ifPresent(...)
        System.out.println("\nMinutes per user:");

        plays.stream()
             .map(Play::user)
             .distinct()
             .sorted()
             .forEach(user -> System.out.printf(" %-9s %d min%n", user,
              MusicStats.totalMinutes(plays, PlayFilters.byUser(user))));

        System.out.println("\nPlays per genre:");

        MusicStats.playsPerGenre(plays)
                .forEach((genre, count) ->
                        System.out.printf("  %-12s %s%n", genre, "#".repeat(count.intValue())));

        System.out.println("\nPlays by day:");

        MusicStats.playsByDay(plays)
                .forEach((day, count) -> System.out.printf("  %-10s %d%n", day, count));

        System.out.println("\nTop 3 artists: " + MusicStats.topArtists(plays, 3));

        MusicStats.longestPlay(plays)
                .ifPresent(p -> System.out.println("Longest play:  " + compact.format(p)));

        System.out.println("\nMost recent:");

        MusicStats.recent(plays, 5)
                  .forEach(p -> System.out.println("  " + detailed.format(p)));
        // TODO Part 4e: loop – ask for a user name (or 'quit') and print their summary

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.print("\nUser to summarize (or 'quit'): ");

            String user = sc.nextLine().trim().toLowerCase();

            if (user.equals("quit")) {
                break;
            }

            List<Play> userPlays = plays.stream()
                    .filter(PlayFilters.byUser(user))
                    .toList();

            if (userPlays.isEmpty()) {
                System.out.println("No listening history found for " + user);
                continue;
            }

            long totalPlays = userPlays.size();

            long minutes = MusicStats.totalMinutes(plays, PlayFilters.byUser(user));

            String genre = MusicStats.topGenre(plays, user).orElse("None");

            int skipPercent = (int) Math.round(MusicStats.skipRate(plays, user) * 100);

            System.out.printf(
                    "%s: %d plays, %d min, top genre %s, skip rate %d%%%n",
                    user,
                    totalPlays,
                    minutes,
                    genre,
                    skipPercent);

            // Total minutes for the user's top genre.
            long genreMinutes = MusicStats.totalMinutes(plays, PlayFilters.byUser(user)
                                          .and(PlayFilters.byGenre(genre)));

            System.out.println("  " + genre + " minutes: " + genreMinutes);

            // Full listens (not skips), distinct and sorted.
            String fullListens = plays.stream()
                    .filter(PlayFilters.byUser(user))
                    .filter(PlayFilters.skipped().negate())
                    .map(compact::format)
                    .distinct()
                    .sorted()
                    .collect(Collectors.joining("; "));

            System.out.println("  Full listens:  " + fullListens);
        }

        sc.close();
    }
}