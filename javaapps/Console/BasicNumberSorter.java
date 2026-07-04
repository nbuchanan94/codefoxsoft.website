// Nicholas Buchanan
// September 2009

import java.util.Scanner;
import java.util.Arrays;

public class BasicNumberSorter {
	public static void main(String[] args) {
		Scanner kbreader = new Scanner(System.in);
		int[] sortnum = new int[5];

		// Loop to get 5 numbers
		for (int i = 0; i < 5; i++) {
			System.out.print("Enter number " + (i + 1) + ": ");
			sortnum[i] = kbreader.nextInt();
		}

		Arrays.sort(sortnum);
		System.out.println("Sorted Numbers are as follows: " + Arrays.toString(sortnum));

		kbreader.close();
	}
}

/*
 * PROGRAM DESCRIPTION:
 * BasicNumberSorter.java
 *
 * This program takes a fixed set of 5 integer inputs from the user,
 * sorts them in ascending order using Java's built-in Arrays class,
 * and displays the sorted list.
 */