// Nicholas Buchanan
// 2010

import java.util.Scanner;

public class PlanetaryWeightConverter {
	public static void main(String[] args) {
		Scanner kbReader = new Scanner(System.in);

		// Conversion Factors
		double voltarFactor = 0.091;
		double kryptonFactor = 0.72;
		double fertosFactor = 0.865;
		double servontosFactor = 4.612;

		System.out.println("Weight Conversion Throughout the Universe!");
		System.out.print("Your weight? ");

		if (!kbReader.hasNextInt()) {
			System.out.println("Invalid input. Please enter an integer weight.");
			kbReader.close();
			return;
		}

		int weight = kbReader.nextInt();

		System.out.println("\t 1) Voltar ");
		System.out.println("\t 2) Krypton");
		System.out.println("\t 3) Fertos");
		System.out.println("\t 4) Servontos");
		System.out.println("\t 5) Exit");
		System.out.print("Selection: ");

		int choice = kbReader.nextInt();

		switch (choice) {
			case 1:
				System.out.println("Your weight on Voltar is: " + (weight * voltarFactor));
				break;
			case 2:
				System.out.println("Your weight on Krypton is: " + (weight * kryptonFactor));
				break;
			case 3:
				System.out.println("Your weight on Fertos is: " + (weight * fertosFactor));
				break;
			case 4:
				System.out.println("Your weight on Servontos is: " + (weight * servontosFactor));
				break;
			case 5:
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("That is not a choice!");
		}

		kbReader.close();
	}
}

/*
 * This program calculates a user's weight on various fictional planets
 * (Voltar, Krypton, Fertos, Servontos) using specific conversion factors.
 * It demonstrates basic menu selection (`switch` statements) and arithmetic
 * operations.
 */