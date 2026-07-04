
//Nicholas Larkin Buchanan
//2010-02-17
 import java.util.Scanner;

public class AreaPerimeter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter length: ");
        int length = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter width: ");
        int width = Integer.parseInt(scanner.nextLine());

        int area = length * width;
        int perimeter = (length * 2) + (width * 2);

        System.out.println("\nArea of rectangle: " + area);
        System.out.println("Perimeter of Rectangle: " + perimeter);
    }
}
// Usage: Calculates Area and Perimeter of a rectangle given length and width.
