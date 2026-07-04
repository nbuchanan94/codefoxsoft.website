
// Nicholas Larkin Buchanan
// January 20, 2011
import java.io.*;
import java.util.*;

public class nameLength {
    public static void main(String[] args) {
        Scanner kbReader = new Scanner(System.in);
        System.out.println("What is your name? ");
        String yourName = kbReader.next();

        System.out.println("Name: " + yourName);
        System.out.println("Length: " + yourName.length());

        StringBuffer sb = new StringBuffer(yourName);
        System.out.println("Reversed: " + sb.reverse());
    }
}
// Run to enter your name and see its length and reversed form.
