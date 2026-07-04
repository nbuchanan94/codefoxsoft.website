
//Nicholas Larkin Buchanan
//2013-05-15
//pd 8
import java.util.*;

public class PhoneNumberConverter {
	public static void main(String[] args) {
		System.out.println("System: Application Start");
		Scanner kb = new Scanner(System.in);

		System.out.println("Enter alphanumeric phone number (e.g. 1-800-FLOWERS):");
		String input = kb.nextLine();

		System.out.print("Converted Number: ");
		for (int i = 0; i < input.length(); i++) {
			char original = input.charAt(i);
			if (Character.isLetter(original)) {
				System.out.print(getNumber(original));
			} else {
				// Return original char if not a letter (digits, dashes)
				System.out.print(original);
			}
		}
		System.out.println(); // Newline result
		System.out.println("System: Application End");
	}

	public static int getNumber(char letter) {
		letter = Character.toLowerCase(letter);
		int i = 0;

		if (letter >= 'a' && letter <= 'c')
			i = 2;
		else if (letter >= 'd' && letter <= 'f')
			i = 3;
		else if (letter >= 'g' && letter <= 'i')
			i = 4;
		else if (letter >= 'j' && letter <= 'l')
			i = 5;
		else if (letter >= 'm' && letter <= 'o')
			i = 6;
		else if (letter >= 'p' && letter <= 's')
			i = 7;
		else if (letter >= 't' && letter <= 'v')
			i = 8;
		else if (letter >= 'w' && letter <= 'z')
			i = 9;

		return i;
	}
}
// Usage: Run. Input "1-800-FLOWERS". Output "1-800-3569377".