package edu.cwi.swdv110.diskusage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/** Recursive folder walking. Files.walk() and other built-in walkers are off-limits. */
public final class FileScanner {

    private static final String[] UNITS = {"B", "KB", "MB", "GB", "TB"};

    private FileScanner() { }

    /**
     * GIVEN: the children of a folder sorted by name, or an empty array if the folder
     * can't be read (listFiles() returns null for protected folders – this prevents a crash).
     */
    static File[] sortedChildren(File dir) {
        File[] kids = dir.listFiles();
        if (kids == null) {
            return new File[0];
        }
        Arrays.sort(kids, Comparator.comparing(File::getName));
        return kids;
    }

    // ---------------- Part 1 ----------------

    /**
     * Base case:      a file's size is its length().
     * Recursive case: a folder's size is the sum of the totalSize of each child.
     */
    public static long totalSize(File f) {
        // Base case
        if (f.isFile()) {
            return f.length();
        }

        // Recursive case
        long total = 0;
        
        for (File child : sortedChildren(f)) {
            total += totalSize(child);
        }
        
        return total;
    }

    /** Number of FILES (not folders) in f and everything under it. A single file counts as 1. */
    public static int countFiles(File f) {
        // Base Case
        if (f.isFile()) {
            return 1;
        }

        // Recursive case
        int count = 0;
        for (File child : sortedChildren(f)) {
            count += countFiles(child);
        }

        return count;
    }

    /**
     * How many levels deep the nesting goes.
     *   a file, or an empty folder           -> 0
     *   a folder that only contains files     -> 1
     *   otherwise                             -> 1 + the deepest child
     */
    public static int maxDepth(File f) {
        // Base case
        if (f.isFile()) { return 0;}

        // check if folder is empty
        File[] children = sortedChildren(f);
        if (children.length == 0) { return 0;}

        // Recursive case
        int max = 0;

        for (File child : sortedChildren(f)) {
            int childDepth = maxDepth(child);

            if (childDepth > max) {
                max = childDepth;
            }
        }

        return max + 1;
    }

    // ---------------- Part 2 ----------------

    /**
     * Every file under root, in name order at each level, with paths relative to root:
     *   "a.txt", "docs/img/cat.png", "docs/notes.txt"
     * Do the work in the private recursive helper below; this method just starts it.
     */
    public static List<FileInfo> listAll(File root) {
        List<FileInfo> result = new ArrayList<>();
        
        collect(root, "", result);

        return result;
    }

    /** Recursive helper: adds the files in dir to out. prefix is the path so far, e.g. "docs/img/". */
    private static void collect(File dir, String prefix, List<FileInfo> out) {
        
        // Examine each child - Base Case
        for (File child : sortedChildren(dir)) {

            if (child.isFile()) {
                out.add(new FileInfo(prefix + child.getName(), child.length()));

            } else if (child.isDirectory()) {
                // Recursive case
                collect(child, prefix + child.getName() + "/", out);
            }
        }
    }

    /** Files whose name ends with "." + ext (ignore case), in listAll order. */
    public static List<FileInfo> findByExtension(File root, String ext) {

        // Get every file
        List<FileInfo> allFiles = listAll(root);

        // Create a list for matching files.
        List<FileInfo> matches = new ArrayList<>();

        // Build the extension.
        String ending = "." + ext.toLowerCase();

        // Examine every file.
        for (FileInfo file : allFiles) {

            if (file.path().toLowerCase().endsWith(ending)) {

                matches.add(file);
            }
        }

        return matches;
    }

    /**
     * Human-readable size, 1024 bytes per KB:
     *   0 -> "0 B"   999 -> "999 B"   1536 -> "1.5 KB"   1048576 -> "1.0 MB"
     * Write it RECURSIVELY with the helper below: if the value is under 1024 (or you've
     * run out of units) format it; otherwise call yourself with value / 1024 and the next unit.
     * Use String.format(Locale.US, ...) so the decimal point is '.' on every computer.
     */
    public static String humanSize(long bytes) {
        return humanSize(bytes, 0);
    }

    private static String humanSize(double value, int unit) {
        // Base case: The value is small enough,
        // or reached final unit.
        if (value < 1024 || unit == UNITS.length - 1) {

            if (unit == 0) {
                return String.format("%.0f %s", value, UNITS[unit]);
            }

            return String.format("%.1f %s", value, UNITS[unit]);
        }

        // Recursive case
        return humanSize(value / 1024, unit + 1);
    }

    // ---------------- Part 3 ----------------

    /**
     * An indented tree, two spaces per level, folders end with '/', each line shows the size:
     *   myfolder/ (2.4 KB)
     *     a.txt (100 B)
     *     docs/ (2.2 KB)
     *       notes.txt (300 B)
     * Folders deeper than maxDepth are listed but their contents are not
     * (their size still includes everything inside).
     */
    public static String tree(File root, int maxDepth) {
        StringBuilder sb = new StringBuilder();
        
        tree(root, 0, maxDepth, sb);

        return sb.toString();
    }

    private static void tree(File f, int depth, int maxDepth, StringBuilder sb) {
        // Base case: file has no children
        String indent = "  ".repeat(depth);

        String name = f.getName();

        if (f.isDirectory()) {
            name += "/";
        }

        sb.append(indent)
        .append(name)
        .append(" (")
        .append(humanSize(totalSize(f)))
        .append(")")
        .append(System.lineSeparator());

        // Recursive case
        if (f.isDirectory() && depth < maxDepth) {
            for (File child : sortedChildren(f)) {
                tree(child, depth + 1, maxDepth, sb);
            }
        }
    }
}