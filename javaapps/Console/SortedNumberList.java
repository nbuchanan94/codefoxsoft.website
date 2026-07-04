
//Nicholas Larkin Buchanan
//2011-02-25
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SortedNumberList {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> numbers = new ArrayList<>();

        System.out.print("ENTER amount of numbers: ");
        int count = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < count; i++) {
            System.out.print("enter number #" + (i + 1) + ": ");
            numbers.add(scanner.nextLine());

             Collections.sort(numbers);
        }

        System.out.println("\n--- Sorted List ---");
        for (String num : numbers) {
            System.out.println(num);
        }
    }
}
// Usage: Accepts N numbers (as Strings) and prints them sorted.
