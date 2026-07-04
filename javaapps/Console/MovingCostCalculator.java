
//Nicholas Larkin Buchanan
//2011-01-13
//pd 5
import java.util.*;

public class MovingCostCalculator {
	public static void main(String[] args) {
		System.out.println("System: Application Start");
		Scanner kbreader = new Scanner(System.in);

		System.out.print("Enter distance traveled (km): ");
		double traveled = kbreader.nextDouble();
		System.out.println(" Input received: " + traveled);

		double total = 200; // Base fee?
		double km = .25; // Rate per km?

		if (traveled < 50) {
			System.out.println("Distance < 50. Applying local rate.");
			System.out.println("Cost: " + (((traveled * .05) + traveled) * km + (traveled + total)));
		} else if (traveled > 400) {
			System.out.println("Distance > 400. Long distance.");
			// Placeholder logic for empty block
			System.out.println("Cost: " + (total + (traveled * km * 1.5)));
			System.out.println("(Includes long-distance surcharge)");
		} else {
			System.out.println("Standard distance (50-400).");
			// Placeholder logic for empty block
			System.out.println("Cost: " + (total + (traveled * km)));
		}
		System.out.println("System: Application End");
	}
}
// Usage: Run this file. Enter distance in km. Calculates moving cost.