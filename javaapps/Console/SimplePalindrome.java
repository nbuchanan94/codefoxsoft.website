// Nicholas Buchanan
// Friday, November 12, 2010

import java.util.*;

public class SimplePalindrome {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("--- Palindrome Checker ---");
		System.out.print("Enter a word or phrase to check: ");
		String originalInput = scanner.nextLine();

		// Clean the input: Remove spaces and convert to lowercase for accurate checking
		String cleanedInput = originalInput.replace(" ", "").toLowerCase();

		// Reverse the string
		StringBuilder reversedBuilder = new StringBuilder(cleanedInput);
		String reversedString = reversedBuilder.reverse().toString();

		System.out.println("\nOriginal (Cleaned): " + cleanedInput);
		System.out.println("Reversed (Cleaned): " + reversedString);

		// Check if Palindrome
		System.out.print("Is it a palindrome? ");
		if (cleanedInput.equals(reversedString)) {
			System.out.println("Yes!");
		} else {
			System.out.println("No.");
		}

		scanner.close();
	}
}

/*
 * This program checks whether a given string is a palindrome (reads the same
 * backwards as forwards).
 */
