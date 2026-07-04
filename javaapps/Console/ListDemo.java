
//Nicholas Larkin Buchanan
//2011-02-20
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> myList = new ArrayList<>();

        System.out.println("Enter up to 10 strings (type 'exit' to stop):");

        for (int i = 0; i <= 10; i++) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                break;
            } else {
                System.out.println("ENTER NEXT STRING");
            }
            myList.add(input);
        }

        System.out.println("\n--- Results ---");
        for (String s : myList) {
            System.out.println(s);
        }
    }
}
// Usage: Accepts strings into a list until 'exit' or 10 items. Prints results.
