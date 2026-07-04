// Nicholas Buchanan
// 12/1/10
// 3rd period

import java.util.Scanner;
import java.util.Arrays;

public class NumericalSorter {
	public static void main(String[] args) {
		// Initialize Scanner for input
		Scanner brant = new Scanner(System.in);

		// Prompt user for the size of the array
		System.out.println("How many numbers to sort?");
		int count = 0;

		// Simple error handling for non-integer input
		try {
			count = brant.nextInt();
		} catch (Exception e) {
			System.out.println("Invalid number entered. Exiting.");
			brant.close();
			return;
		}

		// Initialize array with user-defined size
		int[] num = new int[count];

		// Prompt user to enter the numbers
		System.out.println("Please enter " + count + " numbers:");

		// Loop to collect input numbers
		for (int i = 0; i < num.length; i++) {
			System.out.print("Number " + (i + 1) + ": ");
			num[i] = brant.nextInt();
		}

		// Sort the array in ascending order
		Arrays.sort(num);

		// Display the sorted results
		System.out.println("\n--- Numbers Sorted ---");
		for (int i = 0; i < num.length; i++) {
			System.out.println(num[i]);
		}

		// Close scanner (good practice)
		brant.close();
	}
}

/* 1. Setup: The program asks the user how many numbers they want to sort.
 * 2. Input: It then prompts the user to enter that many integer values one by
 * one.
 * 3. Processing: The program uses Java's built-in Arrays.sort() method to sort
 * the numbers in ascending order.
 * 4. Output: Finally, it prints the sorted list of numbers to the console.
 */
