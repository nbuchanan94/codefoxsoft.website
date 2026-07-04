
//Nicholas Larkin Buchanan
//2011-09-15
//pd 8
import java.util.*;

public class StringRotator {
	public static void main(String[] args) {
		System.out.println("System: Application Start");
		Scanner kbreader = new Scanner(System.in);

		System.out.println("Enter string ending with a shift number (e.g. 'Hello2'):");
		String input = kbreader.next();
		System.out.println("DEBUG: Input received: " + input);

		// Parse last char as rotation amount
		try {
			int changeby = Integer.parseInt(input.substring(input.length() - 1));
			System.out.println("DEBUG: Rotation amount: " + changeby);

			String rr = returner(input, changeby);
			System.out.println("Original: " + input);
			System.out.println("Rotated:  " + rr);
		} catch (NumberFormatException e) {
			System.out.println("Error: Last character must be a digit.");
		}
		System.out.println("System: Application End");
	}

	public static String returner(String toChange, int amount) {
		// Fix: Use correct logic to rotate right by 'amount'
		int len = toChange.length();
		// Prevent OutOfBounds if amount > length
		amount = amount % len;

		String backPart = toChange.substring(len - amount);
		String frontPart = toChange.substring(0, len - amount);

		return backPart + frontPart;
	}
}
// Usage: Run. Enter string with trailing digit 'N'. Rotates string right by N
// chars.