// Nicholas Buchanan
// November 6 2011

import java.util.*;

public class LetterSpan {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("--- Letter Span Calculator ---");
		System.out
				.println("This program calculates the distance (number of steps) between two letters in the alphabet.");
		System.out.println("Example: The distance between 'A' and 'C' is 2.");
		System.out.println("------------------------------\n");

		System.out.print("Enter first letter: ");
		String input1 = scanner.next().toUpperCase();
		char char1 = input1.charAt(0);

		System.out.print("Enter second letter: ");
		String input2 = scanner.next().toUpperCase();
		char char2 = input2.charAt(0);

		// Calculate absolute difference
		int span = Math.abs(char1 - char2);

		System.out.println("\nCalculated Span: " + span);

		// Optional: List the letters in between if span > 1
		if (span > 1) {
			System.out.print("Letters in between: ");
			char start = (char) (Math.min(char1, char2) + 1);
			char end = (char) (Math.max(char1, char2));
			for (char c = start; c < end; c++) {
				System.out.print(c + " ");
			}
			System.out.println();
		}

		scanner.close();
	}
}

/*
 * This program asks the user for two letters and calculates the "span" or
 * numerical usage distance between them in the alphabet.
 * It also lists the specific letters that fall between the two inputs.
 */