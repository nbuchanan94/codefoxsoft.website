
//Nicholas Larkin Buchanan
//2012-05-18
//pd 8
import java.io.*;
import java.util.*;

public class MethodSquare {
	public static void main(String[] args) {
		System.out.println("System: Application Start");
		Scanner kbreader = new Scanner(System.in);
		System.out.print("Enter number to square: ");

		int inNumber = kbreader.nextInt();
		System.out.println("DEBUG: Input received: " + inNumber);

		System.out.println("System: Calling method 'integer'...");
		System.out.println("Result: " + integer(inNumber));
		System.out.println("System: Application End");

	}

	public static int integer(int i) {
		System.out.println("DEBUG: Method 'integer' entered with arg: " + i);
		i = i * i;
		System.out.println("DEBUG: Calculation complete, returning: " + i);
		return i;

	}
}

// Usage: Run this file. Input an integer. It prints the square of that integer.
// Helpers: None.