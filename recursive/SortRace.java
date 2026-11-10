package recursive;


import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;

public class SortRace {
    public static void main(){

        // fill an int[] wiht 50k random numbers
        int[] numbers = new int[50_000];
        Random random = new Random();
        for (int i=0; i < numbers.length; i++) {
            numbers[i] = random.nextInt();
        }

        // Copy array three times
        int[] selectionSortCopy = Arrays.copyOf(numbers, numbers.length);
        int[] mergeSortCopy = Arrays.copyOf(numbers, numbers.length);
        int[] arraysSortCopy = Arrays.copyOf(numbers, numbers.length);

        // time selectionSort
        Instant startSelcection = Instant.now();
        selectionSort(selectionSortCopy);
        Instant endSelcetion = Instant.now();
        Duration durationSelection = Duration.between(startSelcection, endSelcetion);

        // time mergeSort
        Instant startMerge = Instant.now();
        mergeSort(mergeSortCopy);
        Instant endMerge = Instant.now();
        Duration durationMerge = Duration.between(startMerge, endMerge);

        // time Arrays.sort
        Instant startArrays = Instant.now();
        Arrays.sort(arraysSortCopy);
        Instant endArrays = Instant.now();
        Duration durationArrays = Duration.between(startArrays, endArrays);

        // verify each result is sorted
        System.out.println("\nSelection Sort is sorted?: " + isSorted(selectionSortCopy));
        System.out.println("Duration: " + durationSelection);
        
        System.out.println("\nMerge Sort is sorted?: " + isSorted(mergeSortCopy));
        System.out.println("Duration: " + durationMerge);

        System.out.println("\nArrays.sort is sorted?: " + isSorted(arraysSortCopy));
        System.out.println("Duration: " + durationArrays);
    }


    // Helper method to verify if sorted
    public static boolean isSorted(int[] a) {
        for (int i = 0; i < a.length -1; i++) {
            if (a[i] > a[i+1]) {
                return false;
            }
        }
        return true;
    }

    // Selection Sort
    public static void selectionSort(int[] numbers) {
        for(int i = 0; i < numbers.length-1; i++) {
            int minIndex = i;
            for(int j = i + 1; j < numbers.length; j++) {
                if (numbers[j] < numbers[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = numbers[i];
            numbers[i] = numbers[minIndex];
            numbers[minIndex] = temp;
        }
    }
    // Merge Sort
    public static void mergeSort(int[] a) {
        if (a.length < 2) return; // base case
        int mid = a.length / 2;
        int[] left = Arrays.copyOfRange(a, 0, mid);
        int[] right = Arrays.copyOfRange(a, mid, a.length);
        mergeSort(left); // recursive cases
        mergeSort(right);
        merge(a, left, right);
    }

    private static void merge(int[] a, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
        a[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
        }
        while (i < left.length) a[k++] = left[i++];
        while (j < right.length) a[k++] = right[j++];
    }
}