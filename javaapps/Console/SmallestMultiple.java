
//Nicholas Larkin Buchanan
//2012-05-15
//pd 8
import java.util.*;

public class SmallestMultiple {
	public static void main(String[] args) {
		System.out.println("System: Application Start");
		System.out.println("DEBUG: Searching for smallest number divisible by 1-20...");

		// Removed unused array 'numbers'

		int name = 0;
		boolean searching = true;

		long startTime = System.currentTimeMillis();

		while (searching) {
			name++;

			// Progress Log every 50 million to keep user informed
			if (name % 50000000 == 0) {
				System.out.println("DEBUG: Checked " + name + " candidates...");
			}

			for (int i = 2; i <= 20; i++) {
				if (name % i == 0 && i == 20) {
					// Found it!
					System.out.println("Found it: " + name);
					System.out.println("Time taken: " + (System.currentTimeMillis() - startTime) + "ms");
					searching = false;
				} else if (name % i != 0) {
					// Not divisible by i, break and try next number
					break;
				}
			}
		}
		System.out.println("System: Application End");
	}
}
// Usage: Run. Calculates the smallest positive number that is evenly divisible
// by all of the numbers from 1 to 20.