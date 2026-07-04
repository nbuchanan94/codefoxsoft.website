
// Nicholas Larkin Buchanan
// February 28, 2011
import java.util.*;
import java.io.*;

public class PatternGenerator {
    public static void main(String[] args) {
        System.out.println("Generating Nonsense Patterns...");
        String vowels = "aeiou";
        String alfabet = "bcdfghjklmnpqrstvwxyz";

        int j = 0;
        int i = 0;

        while (j < 5) {
            while (i < 5) {
                System.out.println(alfabet.substring(j));
                i++;
            }
            j++;
        }
    }
}
// Run to view the generated pattern output.
