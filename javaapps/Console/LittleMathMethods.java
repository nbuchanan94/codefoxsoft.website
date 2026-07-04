
// Nicholas Larkin Buchanan
// June 12, 2011
import java.util.Scanner;

public class LittleMathMethods {
    public static void main(String[] args) {
        Scanner kbInput = new Scanner(System.in);
        System.out.println("::: Little Math Methods :::");
        System.out.println("Pick 2 numbers");

        System.out.print("Number 1: ");
        double n1 = kbInput.nextDouble();

        System.out.print("Number 2: ");
        double n2 = kbInput.nextDouble();

        System.out.println("\n--- Results ---");
        add(n1, n2);
        sub(n1, n2);
        multiply(n1, n2);
        divide(n1, n2);
    }

    public static void add(double a, double b) {
        System.out.println(a + " + " + b + " = " + (a + b));
    }

    public static void sub(double a, double b) {
        System.out.println(a + " - " + b + " = " + (a - b));
    }

    public static void multiply(double a, double b) {
        System.out.println(a + " * " + b + " = " + (a * b));
    }

    public static void divide(double a, double b) {
        if (b != 0) {
            System.out.println(a + " / " + b + " = " + (a / b));
        } else {
            System.out.println(a + " / " + b + " = Undefined (Cannot divide by zero)");
        }
    }
}
// Run the program and enter two numbers to see the results of addition,
// subtraction, multiplication, and division.