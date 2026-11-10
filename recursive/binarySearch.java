package recursive;


import java.util.Scanner;

public class BinarySearch {
    // counter
    static int binaryChecks = 0;
    static int linearChecks = 0;

    public static void main() {
        Scanner sc = new Scanner(System.in);

        // create new array
        int[] numbers = new int[1000];

        // fill array with numbers
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }

        // collect user input
        System.out.print("Enter a number: ");
        int target = Integer.parseInt(sc.nextLine());

        // binary search
        int binaryIndex = binarySearch(numbers, target, 0, numbers.length - 1);
        if (binaryIndex == -1) {
            System.out.println("Number not found with binay search");
        }

        // linear search
        int linearIndex = linearSearch(numbers, target);
        if (linearIndex == -1) {
            System.out.println("Number not found with linear search");
        }

        String lineBreak = "=".repeat(8);

        System.out.println(lineBreak + "Binary Search" + lineBreak);
        System.out.println(target + " is located at index: " + binaryIndex);
        System.out.println("With binary search it took " + binaryChecks + " checks to find");
        System.out.println();
        System.out.println(lineBreak + "Linear Search" + lineBreak);
        System.out.println(target + " is located at index: " + linearIndex);
        System.out.println("With linear search it took " + linearChecks + " checks to find");

        sc.close();
    }


    public static int binarySearch(int[] a, int target, int low, int high) {
        // Base case one - Target not found
        if (low > high) {
            return -1;
        }

        int mid = (low + high) / 2;

        binaryChecks++;
        
        // Base case two - Target found
        if (a[mid] == target) {
            return mid;
        }

        // search left
        if (target < a[mid]) {
            return binarySearch(a, target, low, mid - 1);
        } 
        
        // search right
        return binarySearch(a, target, mid + 1, high);
    }


    public static int linearSearch(int[] a, int target) {
    
        for (int i = 0; i < a.length; i++) {
            linearChecks++;
            if (a[i] == target) {
                return i;
            }
        }
        return -1;
    }

}