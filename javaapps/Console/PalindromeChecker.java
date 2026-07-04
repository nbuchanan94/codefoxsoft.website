// Nicholas Buchanan
// Friday, November 12, 2010

import java.util.Scanner;

public class PalindromeChecker {
	public static void main(String[] args) {
		Scanner kbRead = new Scanner(System.in);

		System.out.println("--- Palindrome Checker ---");
		System.out.print("Enter text to check: ");
		String originalInput = kbRead.nextLine();

		// Strip out spaces and punctuation, and convert to lowercase
		// This allows phrases like "Race car" to be detected as palindromes
		String cleanText = originalInput.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

		// Create a reversed version of the cleaned text
		StringBuilder reversedBuilder = new StringBuilder(cleanText);
		String reversedText = reversedBuilder.reverse().toString();

		System.out.println("\nOriginal: " + originalInput);
		System.out.println("Cleaned:  " + cleanText);
		System.out.println("Reversed: " + reversedText);

		// Compare the content of the strings
		if (cleanText.equals(reversedText)) {
			System.out.println("\nRESULT: YES, proper palindrome.");
		} else {
			System.out.println("\nRESULT: NO.");
		}

		kbRead.close();
	}
}

/*
 * 1. Accepts user input.
 * 2. Pre-processes the string by:
 * - Removing all non-alphanumeric characters (punctuation, spaces).
 * - Converting to lowercase.
 * 3. Reverses the processed string.
 * 4. Compares the forward and backward versions.
 * 5. If they match, the input is a palindrome.
 */
