
//Nicholas Larkin Buchanan
//2012-05-15
//pd 8
import java.util.*;
import java.lang.Math.*;

public class SumOfSquares {
	public static void main(String[] args) {
		System.out.println("System: Application Start");
		int output = 0;
		int j = 0;

		System.out.println("DEBUG: Calculating sum of squares for 1 to 10...");

		// Fixed: Removed extra semicolon after for loop
		for (int i = 1; i <= 10; i++) {
			j = i * i;
			output = output + j;
			System.out.println("DEBUG: Added " + i + "^2 (" + j + ") = " + output);
		}
		System.out.println("Result: " + output);
		System.out.println("System: Application End");
	}
}
// Usage: Run. Calculates the sum of squares for numbers 1 to 10.
