// Nicholas Buchanan
// 2/17/10

import java.util.Scanner;

public class RangePrinter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter the starting number:");
            int num1 = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter the ending number:");
            int num2 = Integer.parseInt(scanner.nextLine());

            System.out.println("\nPrinting numbers from " + num1 + " to " + num2 + ":");
            System.out.println(num1); // Print starting number

            while (num1 < num2) {
                num1++;
                System.out.println(num1);
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter integers.");
        } finally {
            scanner.close();
        }
    }
}

/*

 * 1. Prompts variable 'num1' (start) and 'num2' (end).
 * 2. It prints the starting number.
 * 3. It increments 'num1' until it reaches 'num2', printing each step.
 * 4. Effectively lists all integers in the specified range.
 */
