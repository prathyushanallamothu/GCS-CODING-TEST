import java.util.Arrays;
import java.util.Scanner;

public class MedianFinderr {
    public static void main(String[] args) {
        // Create a Scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the list of integers
        System.out.print("Enter the list of integers separated by spaces: ");
        String input = scanner.nextLine();

        // Parse the input string into an array of integers
        int[] numbers = Arrays.stream(input.split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();

        // Call the function to sort and find the median
        sortAndFindMedian(numbers);

        // Close the Scanner
        scanner.close();
    }

    public static void sortAndFindMedian(int[] numbers) {
        // Call the sort function to sort the array in ascending order
        sort(numbers);

        // Get the length of the sorted array
        int n = numbers.length;

        // Check if the length of the array is even or odd
        if (n % 2 == 0) {
            // If even, calculate the median as the average of the middle two elements
            double median = (double) (numbers[n / 2 - 1] + numbers[n / 2]) / 2;
            System.out.println("Median: " + median);
        } else {
            // If odd, the median is the middle element
            int median = numbers[n / 2];
            System.out.println("Median: " + median);
        }
    }


    public static void sort(int[] numbers) {
        // Get the length of the array
        int n = numbers.length;

        // Boolean flag to track if any swaps were made in the last pass
        boolean swapped;

        // Perform iterations until no more swaps are needed
        do {
            // Reset the swapped flag for this iteration
            swapped = false;

            // Iterate through the array
            for (int i = 0; i < n - 1; i++) {
                // Compare adjacent elements and swap if needed
                if (numbers[i] > numbers[i + 1]) {
                    // Swap numbers[i] and numbers[i + 1]
                    int temp = numbers[i];
                    numbers[i] = numbers[i + 1];
                    numbers[i + 1] = temp;

                    // Set the swapped flag to true
                    swapped = true;
                }
            }
        } while (swapped); // Continue until no more swaps are needed
    }
}

