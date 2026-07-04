// nick buchanan
// 1-2-10
//pd 8

import java.util.*;
import java.text.*;

public class BabySitterPayCalc {
	public static void main(String[] args) {
		// Display Program Header
		System.out.println("=====================");
		System.out.println("+++BabySitter.java+++");
		System.out.println("=====================");

		// Display the base pay rate to the user
		System.out.println("Payrate: $2.50 per kid per hour");
		System.out.println();

		// Initialize formatting for currency
		DecimalFormat money = new DecimalFormat("$#,###.00");
		// Initialize scanner for user input
		Scanner kbReader = new Scanner(System.in);

		// --- FIRST BABYSITTER INPUT ---
		System.out.print("Sitters Name: ");
		String name = kbReader.next();

		System.out.print("Hours worked: ");
		double hoursWorked = kbReader.nextDouble();

		System.out.print("Kids Watched: ");
		int kidsWatched = kbReader.nextInt();

		// Rate per kid per hour
		double amountPay = 2.5;
		// Calculate total pay: Rate * Kids * Hours
		double amountPayed = amountPay * kidsWatched * hoursWorked;

		// --- SECOND BABYSITTER INPUT ---
		System.out.println(); // Add spacing
		System.out.print("Sitters Name: ");
		String name1 = kbReader.next();

		System.out.print("Hours worked: ");
		double hoursWorked1 = kbReader.nextDouble();

		System.out.print("Kids Watched: ");
		int kidsWatched1 = kbReader.nextInt();

		double amountPay1 = 2.5;
		// Calculate total pay for second sitter
		double amountPayed1 = amountPay1 * kidsWatched1 * hoursWorked1;

		// --- OUTPUT TABLE ---
		System.out.println("\nSitter\t\tHours\t\tKids\t\tTotal Made");
		// Print details for first sitter
		System.out.println(name + "\t\t" + hoursWorked + "\t\t" + kidsWatched + "\t\t" + money.format(amountPayed));
		// Print details for second sitter
		System.out.println(name1 + "\t\t" + hoursWorked1 + "\t\t" + kidsWatched1 + "\t\t" + money.format(amountPayed1));

		// Close scanner
		kbReader.close();
	}
}

/*
 * PROGRAM DESCRIPTION:
 * This program calculates the total pay for two babysitters based on their
 * hours worked and the number of kids watched.
 * 
 * 1. It informs the user of the fixed pay rate ($2.50 per kid per hour).
 * 2. It prompts the user to input the Name, Hours Worked, and Number of Kids
 * Watched for the first babysitter.
 * 3. It calculates the pay for the first babysitter using the formula: Pay =
 * $2.50 * Kids * Hours.
 * 4. It repeats this process for a second babysitter.
 * 5. Finally, it displays a formatted table showing the Name, Hours, Kids, and
 * Total Pay for both babysitters.
 */