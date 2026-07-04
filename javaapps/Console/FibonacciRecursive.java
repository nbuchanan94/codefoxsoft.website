
//Nicholas Larkin Buchanan
//2013-02-12
//pd 8
import java.io.*;
import java.util.*;

public class FibonacciRecursive {
	public static void main(String[] args) {
		System.out.println("System: Application Start");
		Scanner kbreader = new Scanner(System.in);
		System.out.print("Enter number of terms to calculate: ");
		int inNumber = kbreader.nextInt();
		System.out.println("DEBUG: Input received: " + inNumber);

		for (int i = 0; i <= inNumber; i++) {
			System.out.print("Term " + i + ": ");
			System.out.println(integer(i));

		}
		System.out.println("System: Application End");
	}

	public static int integer(int i) {
		if (i == 0) {
			return i;
		} else if (i == 1) {
			return i;

		} else {
			return integer(i - 1) + integer(i - 2);

		}
	}
}
// Usage: Run this file. Enter N. Prints the first N numbers of the Fibonacci
// sequence using recursion.