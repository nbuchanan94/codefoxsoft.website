
//Nicholas Larkin Buchanan
//2010-02-17
import java.util.Scanner;

public class RectangleDrawer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("height of rectangle (originally called width): ");
        int height = Integer.parseInt(scanner.nextLine());

        System.out.println("width of rectangle (originally called length): ");
        int width = Integer.parseInt(scanner.nextLine());

        System.out.println();

        int i = 0;

        // Fixed Nested Loop Logic (Original C# code failed to reset inner loop var)
        while (i < height) {
            i++;
            int j = 0;
            while (j < width) {
                j++;
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
// Usage: Draws a rectangle of * characters.
