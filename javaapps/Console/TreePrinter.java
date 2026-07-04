
// Nicholas Larkin Buchanan
// 2012
import java.util.Scanner;

public class TreePrinter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Tree Printer!");
        System.out.println("This program draws a pyramid tree based on the size you enter.");
        System.out.print("Please enter an odd integer between 1 and 21 to set the tree height: ");

        if (scanner.hasNextInt()) {
            int n = scanner.nextInt();

            // Check if even or out of range (1 to 21 inclusive)
            if (n % 2 == 0 || n < 1 || n > 21) {
                System.out.println("Error: That number is not valid.");
                System.out.println(
                        "Please run the program again and enter an ODD number (like 3, 5, 7) within the range.");
            } else {
                // Draw the tree
                for (int i = 1; i <= n; i++) {
                    // Print leading spaces
                    for (int k = n; k > i; k--) {
                        System.out.print(" ");
                    }
                    // Print stars
                    for (int j = 1; j <= i; j++) {
                        System.out.print("* ");
                    }
                    System.out.println();
                }
                System.out.println("Tree printed successfully.");
            }
        } else {
            System.out.println("Error: Input was not an integer.");
        }
        scanner.close();
    }
}
// Run the program and input an odd integer (1-21) to generate a pyramid tree.
