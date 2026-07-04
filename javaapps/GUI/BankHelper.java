
/*
 * Nicholas Larkin Buchanan
 * 2010-09-15
 *
 */
import javax.swing.*;

public class BankHelper {
    public String name;
    public double balence;

    // Modified Constructor: Remove console print
    public BankHelper(String nm, double bl) {
        name = nm;
        balence = bl;
    }

    public void deposit(double dp) {
        balence = balence + dp;
    }

    public void withdraw(double wd) {
        balence = balence - wd;
    }

    // Returns string instead of printing
    public String getBalanceString() {
        return "The " + name + " account balance is " + balence;
    }


    public void printnewbalence() {
        System.out.println("The " + name + " account balence is " + balence);
    }
}
