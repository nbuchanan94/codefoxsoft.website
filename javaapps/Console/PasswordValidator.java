// Nicholas Buchanan
// December 3, 2011

import java.util.Scanner;
import java.util.regex.Pattern;

public class PasswordValidator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Password Validator ---");
        System.out.print("Enter password to test: ");
        String password = scanner.next(); // Read single token password (no spaces)

        boolean passedStage1 = false;
        boolean passedStage2 = true;
        boolean passedStage3 = false;

        // Stage 1: Check for Digits
        // Using Regex to check if contain at least one digit
        if (Pattern.compile("[0-9]").matcher(password).find()) {
            System.out.println("Stage 1 (Digits): PASSED.");
            passedStage1 = true;
        } else {
            System.out.println("Stage 1 (Digits): FAILED. Password must contain at least one number.");
        }

        // Stage 2: Check for Repeated Characters (e.g., "aa", "bb")
        // Iterating through string to find adjacent identical characters
        for (int i = 0; i < password.length() - 1; i++) {
            if (password.charAt(i) == password.charAt(i + 1)) {
                System.out.println("Stage 2 (Repeats): FAILED. Repeated character found: '" + password.charAt(i) + "'");
                passedStage2 = false;
                break;
            }
        }

        if (passedStage2) {
            System.out.println("Stage 2 (Repeats): PASSED. No repeated adjacent characters.");
        }

        // Stage 3: Check for Special Characters
        if (Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) {
            System.out.println("Stage 3 (Special Chars): PASSED.");
            passedStage3 = true;
        } else {
            System.out.println(
                    "Stage 3 (Special Chars): FAILED. Password must contain at least one special character (@, #, $, etc).");
        }

        // Final Result
        if (passedStage1 && passedStage2 && passedStage3) {
            System.out.println("\nVERDICT: Good Password.");
        } else {
            System.out.println("\nVERDICT: Weak Password.");
        }

        scanner.close();
    }
}

/*
 * 1. Checks if the password contains at least one numeric digit (0-9).
 * 2. Checks if the password contains any immediately repeating characters (e.g.
 * "aa").
 * 3. Checks if the password contains any special characters (non-alphanumeric).
 * 4. Outputs the result of each stage and a final verdict.
 */
