// Nicholas Buchanan
// 2011
// MathOperations.java

import java.util.Scanner;

public class MathOperations {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("--- Math Library Demo ---");
		System.out.println("Enter 2 numbers with space between them:");

		try {
			double num1 = scanner.nextDouble();
			double num2 = scanner.nextDouble();

			System.out.println("\n--- Results ---");
			System.out.println("Absolute Value (num1): " + Math.abs(num1));
			System.out.println("Power (num1^num2):     " + Math.pow(num1, num2));
			System.out.println("Square Root (num1):    " + Math.sqrt(num1));
			System.out.println("Ceiling (num1):        " + Math.ceil(num1));
			System.out.println("Floor (num1):          " + Math.floor(num1));
			System.out.println("Minimum:               " + Math.min(num1, num2));
			System.out.println("Maximum:               " + Math.max(num1, num2));
			System.out.println("Round (num1):          " + Math.round(num1));

			double randomVal = Math.random() * 10;
			System.out.println("Random Value (0-10):   " + (int) randomVal);

		} catch (Exception e) {
			System.out.println("Invalid input. Please enter valid numbers.");
		} finally {
			scanner.close();
		}
	}
}

/*
 * This program demonstrates the functionality of various methods within
 * Java's standard `Math` class. It accepts two numerical inputs from the
 * user and performs calculations such as:
 * - Absolute Value
 * - Exponentiation (Power)
 * - Square Root
 * - Ceiling and Floor rounding
 * - Minimum and Maximum comparison
 * - Rounding
 * - Random number generation
 */