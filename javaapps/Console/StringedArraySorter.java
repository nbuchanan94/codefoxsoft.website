// Nicholas Buchanan
// December 23, 2010

import java.util.*;
import java.util.Arrays;

public class StringedArraySorter {
	public static void main(String[] args) {
		int j = 0;
		// Initialize Scanner for user input
		Scanner kb = new Scanner(System.in);

		// Prompt user for the number of strings to enter
		System.out.println("how many times run the loop?");
		int innum = kb.nextInt();

		// Consume the leftover newline character from nextInt() to prevent skipping
		// usage
		kb.nextLine();

		// Create an array to hold the specific number of strings
		String[] name = new String[innum];

		// Prompt user to enter the strings
		System.out.println("ENTER STRING THEN PRESS ENTER! \nKEEP DOING THIS UNTIL LOOP ENDS");

		// Loop to capture user input for each string
		for (j = 0; j < innum; j++) {
			name[j] = kb.nextLine();
		}

		// Sort the array alphabetically
		Arrays.sort(name);

		// Loop to display the sorted strings
		System.out.println("\nSorted List:");
		for (String s : name) {
			System.out.println(s);
		}

		kb.close();
	}
}

/*
 * 1. Takes a user input for the size of the array.
 * 2. Collects strings from the user to fill the array.
 * 3. Uses java.util.Arrays.sort() to alphabetize the data.
 * 4. Prints the sorted list to the console.
 */
