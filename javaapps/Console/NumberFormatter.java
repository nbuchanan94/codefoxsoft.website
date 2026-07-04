
// Nicholas Larkin Buchanan
// April 4, 2012
import java.text.DecimalFormat;

public class NumberFormatter {
    public static void main(String[] args) {
        DecimalFormat f = new DecimalFormat("#,###.####");
        double number = 123456789654.96874538426;
        System.out.println("Formatted Number: " + f.format(number));
    }
}
// Run to see an example of DecimalFormat usage.
