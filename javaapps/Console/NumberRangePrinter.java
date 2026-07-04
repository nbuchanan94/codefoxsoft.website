
//Nicholas Larkin Buchanan
//2010-02-17
import java.util.Scanner;

public class NumberRangePrinter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("enter 2 numbers");
        int num1 = Integer.parseInt(scanner.nextLine());
        int num2 = Integer.parseInt(scanner.nextLine());

        System.out.println("Start: " + num1);
        System.out.println("your numbers:");

        while (num1 < num2) {
            num1++;
            System.out.println(num1);
        }
    }
}
// Usage: Prints numbers from Start+1 to End.
