// Nicholas Buchanan
// September 2010

import java.util.Scanner;

public class SimpleLoop {
	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		int j = 0;

		System.out.println("how many times run the loop?");
		int innum = kb.nextInt();

		for (j = 0; j < innum; j++) {
			System.out.println("Design & Logic is Awesome!");
		}

		kb.close();
	}
}

/*
 * This program demonstrates a basic iteration control structure
 * (the 'for' loop). It prompts the user for a count and then
 * repeats a specific text message that many times to the console.
 */