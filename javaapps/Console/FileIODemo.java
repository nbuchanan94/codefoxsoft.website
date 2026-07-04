
//Nicholas Larkin Buchanan
//2012-11-20
//pd 8
import java.io.*;
import java.util.*;

public class FileIODemo {
  // by nick buchanan
  // this demonstraits how to write to a file and read from a file for input and
  // output :D
  public static void main(String[] args) {
    System.out.println("System: Application Start");

    System.out.println("Enter name of file: ");

    try {

      BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));// sets keyboardInput as the
                                                                                          // class that gets input from
                                                                                          // keyboard
      String FileName = keyboardInput.readLine();
      System.out.println("DEBUG: Filename input: " + FileName);

      File f;
      f = new File(FileName);
      System.out.println("DEBUG: Checking existence of file...");

      if (!f.exists()) {
        System.out.println("DEBUG: File not found. Creating...");
        f.createNewFile();
        System.out.println("New file \"" + FileName + "\" has been created to the current directory");

        BufferedWriter write1 = new BufferedWriter(new FileWriter(f));

        System.out.print("Enter Name:");
        String name = keyboardInput.readLine();
        write1.write("NAME: " + name);

        System.out.print("Date of Birth?");

        String DOB = keyboardInput.readLine();
        write1.write("\n" + "DOB: " + DOB);
        write1.close();
        System.out.println("DEBUG: Data written and file closed.");

      } else {
        System.out.println("File exists. Continuing with that file...");
        System.out.println("DEBUG: Reading content...");
        BufferedReader is = new BufferedReader(new FileReader(f));
        System.out.println(is.readLine());
        System.out.println(is.readLine());

      }

    } catch (IOException e) {
      System.err.println("Unexpected IO ERROR: " + e);
    }

    System.out.println("System: Application End");

  }
}
// Usage: Run this file. Enter a filename. Writes Name/DOB if new, reads it if
// exists.