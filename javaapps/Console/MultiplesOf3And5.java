
//Nicholas Larkin Buchanan
//2012-05-15
//pd 8
import java.util.*;

public class MultiplesOf3And5 {
	public static void main(String[] args) {
		System.out.println("System: Application Start");
		Scanner kbreader = new Scanner(System.in);
		final int multx = 3;
		final int multy = 5;
		int output = 0;

		System.out.print("Enter limit (e.g. 10 or 1000): ");
		int upTo = kbreader.nextInt();
		System.out.println("DEBUG: calculating multiples of " + multx + " and " + multy + " below " + upTo);

		for (int i = 1; i < upTo; i++) {
			if (i % multx == 0) {
				output = output + i;
			} else if (i % multy == 0) {
				output = output + i;
			}
		}
		System.out.println("Sum of multiples: " + output);
		System.out.println("System: Application End");
	}
}
// Usage: Run. Calculates sum of multiples of 3 and 5 below input limit.