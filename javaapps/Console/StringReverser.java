// Nicholas Buchanan
// December 2010

import java.util.Scanner;

public class StringReverser {
	public static void main(String[] args) {
		Scanner kbRead = new Scanner(System.in);

		System.out.print("TYPE WHAT YOU WANT BACKWARDS: ");
		String userInput = kbRead.nextLine();

		// StringBuilder is more efficient than StringBuffer for non-threaded use
		StringBuilder reversedText = new StringBuilder(userInput);

		System.out.println(reversedText.reverse().toString());

		kbRead.close();
	}
}

/*
 * This program prompts the user to enter a string of text.
 * It then utilizes the `StringBuilder` class (an updated version of
 * StringBuffer) to efficienty reverse the order of characters in the
 * string and prints the result to the console.
 */