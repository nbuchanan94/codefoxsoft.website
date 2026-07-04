
// Nicholas Larkin Buchanan
// October 21, 2010
import java.io.*;
import java.util.*;
//import java.util.StringTokenizer;

public class Strtok {
      public static void main(String[] args) {
            Scanner kb = new Scanner(System.in);
            System.out.print("Enter a phrase to split apart: ");
            String inp = kb.nextLine();
            StringTokenizer st = new StringTokenizer(inp);
            System.out.println("Breaking phrase into tokens...\n");
            System.out.println("tokens: \n");

            while (st.hasMoreTokens()) {
                  System.out.println(st.nextToken());
            }
      }
}
// Run the program and enter a phrase to see it split into individual
// keys/words.