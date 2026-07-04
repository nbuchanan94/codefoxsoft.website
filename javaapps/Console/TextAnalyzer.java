// Nicholas Buchanan
// 2011 (Refactored)
// TextAnalyzer.java

import java.util.Scanner;

public class TextAnalyzer {
	public static void main(String[] args) {
		Scanner kbread = new Scanner(System.in);
		System.out.println("--- Text Analyzer ---");
		System.out.print("ENTER STRING: ");
		String input = kbread.nextLine();

		// Normalize for vowel counting
		String lowerInput = input.toLowerCase();

		// 1. Vowel Counts
		int a = countChar(lowerInput, 'a');
		int e = countChar(lowerInput, 'e');
		int i = countChar(lowerInput, 'i');
		int o = countChar(lowerInput, 'o');
		int u = countChar(lowerInput, 'u');

		System.out.println("\n--- Vowel Statistics ---");
		System.out.println("A's: " + a);
		System.out.println("E's: " + e);
		System.out.println("I's: " + i);
		System.out.println("O's: " + o);
		System.out.println("U's: " + u);

		// 2. Word Count
		String[] words = input.trim().split("\\s+");
		int numWords = (input.trim().isEmpty()) ? 0 : words.length;

		// 3. Letter Count (excluding whitespace)
		int numLetters = 0;
		for (char c : input.toCharArray()) {
			if (Character.isLetter(c)) {
				numLetters++;
			}
		}

		System.out.println("\n--- General Statistics ---");
		System.out.println("Total Words:   " + numWords);
		System.out.println("Total Letters: " + numLetters);

		// 4. Punctuation
		int comma = countChar(input, ',');
		int semi = countChar(input, ';');
		int colon = countChar(input, ':');
		int period = countChar(input, '.');
		int totalPunct = comma + semi + colon + period;

		System.out.println("\n--- Punctuation Statistics ---");
		System.out.println("Commas:      " + comma);
		System.out.println("Semicolons:  " + semi);
		System.out.println("Colons:      " + colon);
		System.out.println("Periods:     " + period);
		System.out.println("Total Found: " + totalPunct);

		kbread.close();
	}

	/**
	 * Helper method to count occurrences of a specific character
	 */
	public static int countChar(String str, char target) {
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == target) {
				count++;
			}
		}
		return count;
	}
}

/*
 * This program analyzes a user-provided string and generates a statistical
 * report. It calculates:
 * 1. Frequency of each vowel (case-insensitive).
 * 2. Total number of words.
 * 3. Total number of letters (ignoring spaces/punctuation).
 * 4. Frequency of specific punctuation marks (comma, semicolon, colon, period).
 *
 * It demonstrates string manipulation, character iteration, and helper methods.
 */