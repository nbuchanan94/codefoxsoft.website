// Nicholas Buchanan
// November 12 2011

import java.util.*;

public class CharacterCounter {
	public static void main(String[] args) {
		Scanner kbReader = new Scanner(System.in);
		System.out.println("Ever wonder how much a certain letter comes up in a sentance? Now is your chance to find out.");
		System.out.print("Enter phrase: ");
		String input = kbReader.nextLine();

		System.out.print("Enter character to count: ");
		String searchInput = kbReader.next();

		// Ensure we handle case where user enters more than 1 char (just take first)
		char searchChar = searchInput.charAt(0);

		System.out.println("Your character returns " + occur(input, searchChar));

		kbReader.close();
	}

	public static int occur(String in, char search) {
		int count = 0;
		for (int i = 0; i < in.length(); i++) {
			if (in.charAt(i) == search) {
				count++;
			}
		}
		return count;
	}
}

/*
 * This program demonstrates basic string manipulation.
 * It prompts the user for a phrase and a search character,
 * then iterates through the string to count the number of times
 * that specific character appears.
 */