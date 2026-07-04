
// Nicholas Larkin Buchanan
// June 15, 2011
import java.util.Scanner;

public class SimpleIO {
    private static Scanner scanner = new Scanner(System.in);

    // Prints a message to the console
    public static void print(Object message) {
        System.out.println(message);
    }

    // Prints a prompt and returns the user's input as a String
    public static String input(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        print("SimpleIO Test Running...");

        String name = input("What is your name?");
        print("Hello, " + name + "!");

        String age = input("How old are you?");
        print("You are " + age + " years old.");
    }
}
// Run this file to test the Simple Input/Output utility methods.
