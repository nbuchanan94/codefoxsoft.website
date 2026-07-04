// Nicholas Buchanan
// Monday, December 06, 2010

import java.util.*;

public class WordSorter {
	public static void main(String[] args) {
		ArrayList<String> words = new ArrayList<String>();
		Scanner kbReader = new Scanner(System.in);

		// Instructions for the user
		System.out.println("--- Word Sorter ---");
		System.out.println("Type words one by one to add them to the list.");
		System.out.println("Type 'done' when you are finished to see them sorted.");
		System.out.print(">> ");

		while (true) {
			String input = kbReader.next();

			// Check for sentinel value "done" (case-insensitive)
			if (input.equalsIgnoreCase("done")) {
				break;
			}

			words.add(input);
			System.out.print(">> ");
		}

		// Sort the list alphabetically
		Collections.sort(words);

		System.out.println("\nSorted List: " + words);

		kbReader.close();
	}
}

/*
 * This program collects a list of words entered by the user.
 * It continues to accept input until the user types the word "done".
 * Once finished, it sorts the words alphabetically and displays the list.
 */