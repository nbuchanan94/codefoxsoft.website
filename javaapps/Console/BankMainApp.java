
//Nicholas Larkin Buchanan
//2010-09-15
//pd 8
import java.io.*;
import java.util.*;

public class BankMainApp {
    public static void main(String[] args) {
        System.out.println("System: Application Start");
        Scanner kb = new Scanner(System.in);
        System.out.print("What is your name? ");
        String nm = kb.nextLine();
        double balence = 1000;
        System.out.println("Your current balence is " + balence);

        // Updated to use BankHelper
        BankHelper ba = new BankHelper(nm, balence);

        System.out.print("How much do you want to deposite? ");
        double dp = kb.nextDouble();
        System.out.println("System: calling ba.deposit()");
        ba.deposit(dp);

        System.out.println("System: calling ba.printnewbalence()");
        ba.printnewbalence();

        System.out.print("How much do you want do withdraw? ");
        double wd = kb.nextDouble();
        System.out.println("System: calling ba.withdraw()");
        ba.withdraw(wd);

        System.out.println("System: calling ba.printnewbalence()");
        ba.printnewbalence();

        System.out.println("System: Application End");
    }
}

// Usage: Run this file to start the banking application. Inputs are via
// console.
// Helpers: Uses BankHelper.java for account management logic.