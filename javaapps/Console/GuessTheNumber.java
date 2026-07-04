
// Nicholas Larkin Buchanan
// March 15, 2011
import java.util.Scanner;
import java.util.Random;

public class GuessTheNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean keepPlaying = true;

        System.out.println("::: GUESS THE NUMBER CHALLENGE :::");
        System.out.println("Can you beat the computer?");

        while (keepPlaying) {
            int secret = random.nextInt(10) + 1; // 1 to 10
            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println("\n-----------------------------------");
            System.out.println("I have picked a number between 1 and 10.");
            System.out.println("Start guessing!");

            while (!guessedCorrectly) {
                System.out.print("Your guess: ");
                if (!scanner.hasNextInt()) {
                    String trash = scanner.next(); // Consume bad input
                    System.out.println("Hey! '" + trash + "' is not a number. Try again.");
                    continue;
                }

                int guess = scanner.nextInt();
                attempts++;

                if (guess == secret) {
                    System.out.println("***********************************");
                    System.out.println("   WINNER! WINNER! WINNER!");
                    System.out.println("   You got it in " + attempts + " try(s)!");
                    System.out.println("***********************************");
                    guessedCorrectly = true;
                } else if (guess > secret) {
                    System.out.println(" -> Too high! Try something smaller.");
                } else {
                    System.out.println(" -> Too low! Try something bigger.");
                }
            }

            System.out.print("\nDo you want to play again? (y/n): ");
            String response = scanner.next();
            if (!response.equalsIgnoreCase("y")) {
                keepPlaying = false;
                System.out.println("Thanks for playing! Goodbye!");
            }
        }
    }
}
// Run the program to play a guessing game against the computer.
// Follow the on-screen prompts to guess the secret number (1-10).
