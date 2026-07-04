
//Nicholas Larkin Buchanan
//2012-12-05
//pd 8
import java.io.*;

/**
 * Read an int from Standard Input
 */
public class ReadStdinInt {
    public static void main(String[] ap) {
        System.out.println("System: Application Start");
        String line = null;
        int val = 0;
        try {
            BufferedReader is = new BufferedReader(
                    new InputStreamReader(System.in));
            System.out.print("Enter a number: ");
            line = is.readLine();
            val = Integer.parseInt(line);
            System.out.println("DEBUG: Input parsed successfully: " + val);

        } catch (NumberFormatException ex) {
            System.err.println("Not a valid number: " + line);
        } catch (IOException e) {
            System.err.println("Unexpected IO ERROR: " + e);
        }
        System.out.println("I read this number: " + val);
        System.out.println("System: Application End");
    }
}
// Usage: Run this file. Enter an integer when prompted.