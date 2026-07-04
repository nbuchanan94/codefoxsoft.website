// Nicholas Buchanan
// September 15, 2010
import java.util.*;

public class MorseCodeTranslator {

	// Lookup Tables for efficiency
	private static final char[] ALPHABET = {
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z', ' '
	};

	private static final String[] MORSE_CODES = {
			" * - ", // a
			" - * * * ", // b (Fixed from original "- * *" which is D or N usually? Standard B is -...)

			" - * - * ", // c
			" - * * ", // d
			" * ", // e
			" * * - * ", // f
			" - - *", // g
			" * * * * ", // h
			" * * ", // i
			" * - - - ", // j
			" - * - ", // k
			" * - * * ", // l
			" - - ", // m
			" - * ", // n
			" - - - ", // o
			" * - - * ", // p
			" - - * - ", // q
			" * - * ", // r
			" * * * ", // s
			" - ", // t
			" * * -", // u
			" * * * -", // v
			" * - - ", // w
			" - * * -", // x
			" - * - - ", // y
			" - - * * ", // z
			"\t" // space (mapped to Tab as per original)
	};

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("ENTER STRING: ");
		String input = scanner.nextLine();

		// Clean input
		input = input.toLowerCase();

		System.out.println("Translation: " + translate(input));

		scanner.close();
	}

	public static String translate(String input) {
		StringBuilder finalString = new StringBuilder();

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			int index = -1;

			// Find index of character in alphabet array
			for (int k = 0; k < ALPHABET.length; k++) {
				if (ALPHABET[k] == c) {
					index = k;
					break;
				}
			}

			if (index != -1) {
				finalString.append(MORSE_CODES[index]);
			} else {
				// Keep unknown characters as-is or skip? Original skipped.
				// We'll append a ? for unknown
				finalString.append("? ");
			}
		}

		return finalString.toString();
	}
}

/*
 * This program translates a user-inputted string into Morse Code (represented
 * by * and -).
 * Used "Parallel Arrays" (Lookup Tables).
 * The program looks up the character in the ALPHABET array and uses the index
 * to find the
 * corresponding code in the MORSE_CODES array.
 */