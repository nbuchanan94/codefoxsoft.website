
// Nicholas Larkin Buchanan
// September 12, 2009
import java.util.*;
import java.io.*;

public class ConsoleCalc {
    public static void main(String[] args) {

        System.out.println("Make your arithmetic selection from the choices below: \n");
        System.out.println("     1.  Addition");
        System.out.println("     2.  Subtraction");
        System.out.println("     3.  Multiplication");
        System.out.println("     4.  Division \n");
        System.out.print("          Your choice?  ");
        Scanner kbReader = new Scanner(System.in);
        int choice = kbReader.nextInt();
        System.out.print("\n Enter first operand.");
        double op1 = kbReader.nextDouble();
        System.out.print("\n Enter second operand.");
        double op2 = kbReader.nextDouble();
        System.out.println();
        switch (choice) {
            case 1: // addition
                System.out.println(op1 + "  plus  " + op2 + "  =  " + (op1 + op2));
                break;
            case 2: // subtraction
                System.out.println(op1 + "  minus  " + op2 + "  =  " + (op1 - op2));
                break;
            case 3: // multiplication
                System.out.println(op1 + "  times  " + op2 + "  =  " + (op1 * op2));
                break;
            case 4: // division
                System.out.println(op1 + "  divided by  " + op2 + "  =  " + (op1 / op2));
                break;
            default:
                System.out.println("Hey dummy, enter only a 1, 2, 3, or 4!");
        }

    }
}
// Run to perform basic arithmetic operations.
