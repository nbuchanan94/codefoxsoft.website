// Nicholas Buchanan
// December 2010

import java.util.Scanner;

public class SimpleLottery {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String choice = "y";

		System.out.println("--- Simple Lottery Game ---");

		do {
			// Generate two random numbers between 0 and 4
			int num1 = (int) (Math.random() * 5);
			int num2 = (int) (Math.random() * 5);

			System.out.println("\nRolling...");
			System.out.println("Number 1: " + num1);
			System.out.println("Number 2: " + num2);

			if (num1 == num2) {
				System.out.println("YOU WIN THE GAME!!!");
			} else {
				System.out.println("UNFORTUNATELY YOU LOSE!!! :P");
			}

			System.out.print("\nTry again? (y/n): ");
			choice = scanner.next();

		} while (choice.equalsIgnoreCase("y"));

		System.out.println("Thanks for playing!");
		scanner.close();
	}
}

/*
 * This program implements a simple game of chance. It generates two
 * random integers between 0 and 4. If the numbers match, the user
 * wins; otherwise, they lose.
 *
 * The program utilizes a do-while loop to allow the user to replay
 * the game as many times as they wish without restarting the application.
 */