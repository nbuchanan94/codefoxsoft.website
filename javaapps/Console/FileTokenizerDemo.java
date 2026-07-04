
//Nicholas Larkin Buchanan
//2013-01-05
//pd 8
import java.io.*;
import java.util.*;

public class FileTokenizerDemo {
	public static void main(String[] args) {
		System.out.println("System: Application Start");
		Scanner kb = new Scanner(System.in);
		System.out.println("Enter name of file to tokenize (e.g., test_io.txt): ");
		String fileName = kb.nextLine();
		System.out.println("DEBUG: Input filename: " + fileName);

		File f = new File(fileName);
		if (!f.exists()) {
			System.out.println("Error: File not found.");
			return;
		}

		try {
			Scanner fileReader = new Scanner(f);
			System.out.println("DEBUG: File opened. Reading lines...");

			while (fileReader.hasNextLine()) {
				String line = fileReader.nextLine();
				System.out.println("DEBUG: Creating StringTokenizer for line: \"" + line + "\"");

				StringTokenizer st = new StringTokenizer(line);
				while (st.hasMoreTokens()) {
					System.out.println("Token: " + st.nextToken());
				}
			}
			fileReader.close();
			System.out.println("DEBUG: File processing complete.");

		} catch (FileNotFoundException e) {
			System.err.println("Error: " + e.getMessage());
		}

		System.out.println("System: Application End");
	}

}

// Usage: Run this file. Enter a filename. It prints every word (token) in that
// file.