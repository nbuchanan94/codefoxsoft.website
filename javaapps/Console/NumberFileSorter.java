// Nicholas Buchanan
// 2011
import java.io.*;
import java.util.*;

public class NumberFileSorter {
	public static void main(String[] args) {
		ArrayList<Integer> numbers = new ArrayList<>();
		Scanner fileScanner = null;
		Scanner inputScanner = new Scanner(System.in);

		System.out.println("Attempting to read 'numbers.txt'...");

		try {
			// Try to read from the file
			File file = new File("numbers.txt");
			fileScanner = new Scanner(file);

			while (fileScanner.hasNext()) {
				if (fileScanner.hasNextInt()) {
					numbers.add(fileScanner.nextInt());
				} else {
					fileScanner.next(); // Skip non-integer tokens
				}
			}
			System.out.println("File loaded successfully.");

		} catch (FileNotFoundException e) {
			// File not found strategy: Prompt user
			System.out.println("File 'numbers.txt' was not found.");
			System.out.println("Please enter a list of numbers manually.");
			System.out.println("Example: 45 12 1 100 55");
			System.out.print("Input: ");

			// Read the entire line of input
			if (inputScanner.hasNextLine()) {
				String line = inputScanner.nextLine();
				Scanner lineScanner = new Scanner(line);
				while (lineScanner.hasNext()) {
					if (lineScanner.hasNextInt()) {
						numbers.add(lineScanner.nextInt());
					} else {
						lineScanner.next(); // Skip garbage
					}
				}
				lineScanner.close();
			}
		} finally {
			if (fileScanner != null)
				fileScanner.close();
			// We usually don't close System.in scanner in main if we might need it, but
			// here it's fine.
			inputScanner.close();
		}

		// Display Unsorted
		System.out.println("\nUnsorted List: " + numbers);

		// Sort
		Collections.sort(numbers);

		// Display Sorted
		System.out.println("SORTED NUMBERS: " + numbers);
	}
}

/*
 * This program attempts to read a list of integers from a file named
 * "numbers.txt".
 * If the file is found, it reads specific integer values into a list.
 * If the file is NOT found, it prompts the user to manually enter numbers via
 * the console.
 *
 * It then sorts the numbers numerically (smallest to largest) and displays the
 * result. This is a test to see if I could create from scratch a file reader.
 */