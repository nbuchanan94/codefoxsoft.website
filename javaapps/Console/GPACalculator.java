
//Nicholas Larkin Buchanan
//2010-03-01

import java.util.Scanner;

public class GPACalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double gpa = 0;

        for (int i = 1; i <= 7; i++) {
            System.out.print("enter grade " + i + ": ");
            String grade = scanner.nextLine().toLowerCase();

            if (grade.equals("a")) {
                gpa += 4;
            } else if (grade.equals("b")) {
                gpa += 3;
            } else if (grade.equals("c")) {
                gpa += 2;
            } else if (grade.equals("d")) {
                gpa += 1;
            } else {
                gpa += 0;
            }
            System.out.println("Current Sum: " + gpa);
        }

        gpa = gpa / 7.0;
        System.out.printf("Your GPA is %.3f.\n", gpa);
    }
}
// Usage: Calculates GPA from 7 letter grades.
