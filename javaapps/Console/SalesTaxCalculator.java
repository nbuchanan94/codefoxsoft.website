// Nicholas Buchanan
// 2/17/10

import java.util.Scanner;
import java.text.NumberFormat;

public class SalesTaxCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

        System.out.print("How many products? ");
        int time2s = 0;
        try {
            times = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        double price = 0;
        for (int i = 1; i <= times; i++) {
            System.out.print("What is the price of your product? ");
            try {
                price += Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number ignored.");
                i--; // retry
            }
        }

        double taxAmount = price * 0.06;
        double total = price + taxAmount;

        System.out.println("Your total before tax " + currencyFormatter.format(price));
        System.out.println("Your tax is " + currencyFormatter.format(taxAmount));
        System.out.println("Your total is: " + currencyFormatter.format(total));

        scanner.close();
    }
}

/*
 * 1. Asks user for number of items.
 * 2. Uses a loop to accumulate the price of each item entered by the user.
 * 3. Calculates a 6% sales tax on the subtotal.
 * 4. Outputs the subtotal, tax amount, and final total using currency
 * formatting.
 */
