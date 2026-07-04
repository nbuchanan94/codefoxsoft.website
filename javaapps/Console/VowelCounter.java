// Nicholas Buchanan
// 12/1/10
// 3rd period

import java.util.*;

public class VowelCounter {
	public static void main(String[] args) {
		Scanner will = new Scanner(System.in);
		System.out.println("What is your phrase?");

		String rick = will.nextLine();
		// 1. convert the string to lower case
		rick = rick.toLowerCase();

		System.out.println("Analyzing: " + rick);

		int a = 0;
		int e = 0;
		int i = 0;
		int o = 0;
		int u = 0;

		// Single loop to iterate through the string once
		for (int s = 0; s < rick.length(); s++) {
			char character = rick.charAt(s);

			if (character == 'a') {
				a++;
			} else if (character == 'e') {
				e++;
			} else if (character == 'i') {
				i++;
			} else if (character == 'o') {
				o++;
			} else if (character == 'u') {
				u++;
			}
		}

		System.out.println("This is your vowel count:");
		System.out.println("a: " + a + "\te: " + e + "\ti: " + i + "\to: " + o + "\tu: " + u);

		will.close();
	}
}

/*
 * 1. Input: The program asks the user to enter a phrase.
 * 2. Processing:
 * - It converts the entire phrase to lowercase to ensure case-insensitive
 * counting.
 * - It loops through the string character by character.
 * - If a vowel ('a', 'e', 'i', 'o', 'u') is found, the corresponding counter is
 * incremented.
 * 3. Output: The final count for each vowel is displayed to the user.
 */