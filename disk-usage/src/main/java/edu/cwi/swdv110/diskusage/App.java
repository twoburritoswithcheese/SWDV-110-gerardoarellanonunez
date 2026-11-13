package edu.cwi.swdv110.diskusage;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Folder to scan (Enter for this project): ");
        String input = sc.hasNextLine() ? sc.nextLine().trim() : "";
        File root = Path.of(input.isEmpty() ? "." : input).toAbsolutePath().normalize().toFile();
        if (!root.isDirectory()) {
            System.out.println("Not a folder: " + root);
            sc.close();
            return;
        }

        // Print the tree, 2 levels deep
        System.out.print(FileScanner.tree(root, 2));
        System.out.println();

        // Print "Files: N   Total: X   Deepest nesting: N levels"
        System.out.printf(
            "Files: %d  Total: %s  Deepest nesting: %d levels%n",
            FileScanner.countFiles(root),
            FileScanner.humanSize(FileScanner.totalSize(root)),
            FileScanner.maxDepth(root));

            System.out.println();

        // Print the 5 largest files (size right-aligned in 10 columns, then path)
        List<FileInfo> allFiles = FileScanner.listAll(root);
        List<FileInfo> biggest = Algorithms.largest(allFiles, 5);

        System.out.println("Largest files:");
        for (FileInfo file : biggest) {
            System.out.printf("%10s  %s%n",
            FileScanner.humanSize(file.size()), file.path());
        }

        System.out.println();
        
        // Count files per extension in a TreeMap<String, Integer>
        //               (files without a '.' count as "(none)") and print the map
        Map<String, Integer> byExtension = new TreeMap<>();

        for (FileInfo file : allFiles) {
            String name = file.path();
            int dot = name.lastIndexOf('.');

            String extension = dot >= 0 ? name.substring(dot+1).toLowerCase() : "(none)";

            byExtension.merge(extension, 1, Integer::sum);
        }

        System.out.println("Files by extension: " + byExtension);
        System.out.println();

        // loop – 'quit' ends, 'race' calls sortRace(), anything else is a path
        //               to look up with binarySearchByPath (sort the list BY_PATH first!)

        List<FileInfo> byPath = Algorithms.mergeSort(allFiles, Algorithms.BY_PATH);

        while (true) {
            System.out.print("Find path (or 'race' / 'quit'): ");

            if (!sc.hasNextLine()) {
                break;
            }

            String command = sc.nextLine().trim();

            if (command.equalsIgnoreCase("quit")) {
                break;
            }

            if (command.equalsIgnoreCase("race")) {
                sortRace();
                continue;
            }

            int index = Algorithms.binarySearchByPath(byPath, command);

            if (index >= 0) {
                FileInfo found = byPath.get(index);

                System.out.printf(
                        "Found at index %d: %s%n",
                        index,
                        FileScanner.humanSize(found.size())
                );
            } else {
                System.out.println("Not found: " + command);
            }

            System.out.println();
        }
        sc.close();

    }

    /** GIVEN: times both sorts on random data that doubles in size each round. */
    static void sortRace() {
        Random rnd = new Random(110);
        System.out.printf("%8s %14s %12s%n", "n", "selection ms", "merge ms");
        for (int n = 1_000; n <= 16_000; n *= 2) {
            List<FileInfo> data = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                data.add(new FileInfo("file" + rnd.nextInt(1_000_000), rnd.nextInt(10_000_000)));
            }
            long t0 = System.nanoTime();
            Algorithms.selectionSort(data, Algorithms.BY_SIZE_DESC);
            long t1 = System.nanoTime();
            Algorithms.mergeSort(data, Algorithms.BY_SIZE_DESC);
            long t2 = System.nanoTime();
            System.out.printf("%8d %14.1f %12.1f%n", n, (t1 - t0) / 1e6, (t2 - t1) / 1e6);
        }
    }
}