// Nicholas Buchanan
// September 2010

import java.util.Scanner;

public class EvenOddChecker {
  public static void main(String[] args) {
    System.out.println("--- Even or Odd Checker ---");
    System.out.print("Enter a number: ");

    Scanner scanner = new Scanner(System.in);

    try {
      int n = scanner.nextInt();

      if (n % 2 == 0) {
        System.out.println("The number " + n + " is Even.");
      } else {
        System.out.println("The number " + n + " is Odd.");
      }
    } catch (Exception e) {
      System.out.println("Invalid input. Please enter a valid integer.");
    } finally {
      scanner.close();
    }
  }
}

/*
 * This program accepts an integer input from the user and determines
 * whether it is even or odd using the modulus operator (%).
 *
 * It serves as a fundamental example of conditional logic (if-else)
 * in Java programming.
 */