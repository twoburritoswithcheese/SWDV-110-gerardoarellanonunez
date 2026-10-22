package ClassExercises;

import java.util.TreeMap;
import java.util.Map;

public class WordFrequencyCounter {
    public static void main(String[] args) {
        String text = "The Cat, and The Hat, and The Bat.";
        System.out.println(text);
        String textLower = text.toLowerCase().replaceAll("[^a-z ]", "");

        Map<String, Integer> counts = new TreeMap<>();

        for (String word : textLower.split(" ")) {
            int current = counts.getOrDefault(word, 0);
            counts.put(word, current + 1);
        }

        for (Map.Entry<String, Integer> e : counts.entrySet()) {
            System.out.println(e.getKey() + ":" + e.getValue());
        }
    }
}