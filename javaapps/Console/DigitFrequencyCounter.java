
//Nicholas Larkin Buchanan
//2012-10-10
//pd 8
import java.util.*;

public class DigitFrequencyCounter {
	public static void main(String[] a) {
		System.out.println("System: Application Start");
		Scanner kb = new Scanner(System.in);

		System.out.println("Enter a string of digits (e.g., 11295): ");
		String input = kb.nextLine();
		System.out.println("DEBUG: Input received: " + input);

		// Array to hold counts for digits 0-9
		int[] counts = new int[10];

		// Process each character
		for (int i = 0; i < input.length(); i++) {
			char ch = input.charAt(i);
			if (Character.isDigit(ch)) {
				// distinct char '0' makes '0'->0, '1'->1 etc.
				int digit = ch - '0';
				counts[digit]++;
			} else {
				System.out.println("DEBUG: Ignored non-digit: " + ch);
			}
		}

		System.out.println("--- Frequencies ---");
		for (int i = 0; i < 10; i++) {
			if (counts[i] > 0) {
				System.out.println("Digit " + i + ": " + counts[i] + " times");
			}
		}
		System.out.println("System: Application End");
	}
}
// Usage: Run. Enter digits. Counts how many times each digit appears.