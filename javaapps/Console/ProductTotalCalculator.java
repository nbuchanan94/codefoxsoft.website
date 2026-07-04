
//Nicholas Larkin Buchanan
//2010-02-17
import java.util.Scanner;

public class ProductTotalCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many products? ");
        int times = Integer.parseInt(scanner.nextLine());
        double price = 0;

        for (int i = 1; i <= times; i++) {
            System.out.print("What is the price of product " + i + "? ");
            price += Double.parseDouble(scanner.nextLine());
        }

        double tax = price * 0.06;
        double total = price + tax;

        System.out.printf("Your total before tax: $%.2f\n", price);
        System.out.printf("Your tax is: $%.2f\n", tax);
        System.out.printf("Your total is: $%.2f\n", total);
    }
}
// Usage: Sums product prices and applies 6% tax.
