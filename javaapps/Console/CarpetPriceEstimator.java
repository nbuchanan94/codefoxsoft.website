
// Nicholas Larkin Buchanan
// May 23, 2011
import java.util.Scanner;
import java.text.NumberFormat;

public class CarpetPriceEstimator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NumberFormat currency = NumberFormat.getCurrencyInstance();

        System.out.println("::: Karpet King - Area & Price Calculator :::");
        System.out.println("Calculating based on $29.89 per sq. yard.");

        System.out.print("\nEnter room width (feet): ");
        double width = scanner.nextDouble();

        System.out.print("Enter room length (feet): ");
        double length = scanner.nextDouble();

        // Calculate dimensions in yards
        double widthYards = width / 3.0;
        double lengthYards = length / 3.0;

        // Calculate areas
        double areaSqFeet = width * length;
        double areaSqYards = widthYards * lengthYards;

        // Calculate cost
        double cost = areaSqYards * 29.89;

        System.out.println("\n--- ESTIMATE REPORT ---");
        System.out.println("Dimensions:      " + width + "' x " + length + "'");
        System.out.println("Area (Sq. Feet): " + areaSqFeet);
        System.out.printf("Area (Sq. Yards): %.2f\n", areaSqYards);
        System.out.println("Total Cost:      " + currency.format(cost));
    }
}
// Run to estimate carpet costs. Input dimensions in feet.
