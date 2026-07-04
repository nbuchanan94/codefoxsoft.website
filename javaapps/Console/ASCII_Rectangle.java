
//Nicholas Larkin Buchanan
//2011-11-10
//pd 8
import java.util.*;
import java.io.*;

public class ASCII_Rectangle {
	public static void main(String[] args) {
		System.out.println("System: Application Start");
		Scanner kbReader = new Scanner(System.in);
		System.out.println("Enter length:");
		int h = kbReader.nextInt();
		System.out.println("Enter width:");
		int w = kbReader.nextInt();

		System.out.println(
				"DEBUG: Starting generation of " + h + " by " + w + " rectangle (Note: loops may swap dimensions)");

		for (int i = 1; i <= w; i++) {

			for (int j = 1; j < h; j++) {
				System.out.print("*");

			}
			System.out.println("*");
		}
		System.out.println("System: Application End");
	}
}

// Usage: Prints an ASCII rectangle based on input dimensions.
