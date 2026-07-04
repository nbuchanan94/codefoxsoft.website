// Original: cbp1.java
// Refactored for efficiency and scalability

import java.util.*;
import java.io.*;

public class SecurityTextScanner {
	public static void main(String[] args) {
		// defined keywords to search for
		String[] keywords = { "governor", "bomb", "drill", "plan", "gun", "annapolis" };

		System.out.println("--- Starting Security Scan ---");

		try {
			// Create Scanner to read from file
			// Note: Ensure 'cbp1data.txt' exists in the same directory
			Scanner infile = new Scanner(new File("cbp1data.txt"));

			int lineNum = 0;

			// Loop through every line in the file
			while (infile.hasNextLine()) {
				String line = infile.nextLine();
				lineNum++;

				// Convert to lowercase for case-insensitive check
				String lowerLine = line.toLowerCase();

				boolean flagged = false;

				// Check against all keywords
				for (String keyword : keywords) {
					if (lowerLine.contains(keyword)) {
						flagged = true;
						break; // No need to check other keywords if one is found
					}
				}

				// If flagged, mark and print ONLY that line
				if (flagged) {
					System.out.println("Line " + lineNum + ": " + line + " *flag");
				}
			}

			infile.close();
			System.out.println("--- Scan Complete ---");

		} catch (FileNotFoundException e) {
			System.out.println("Error: 'cbp1data.txt' file not found.");
			System.out.println("Please create this file with text to scan.");
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
		}
	}
}

/*
 * 1. Keywords: A predefined list of words (e.g., "bomb", "gun") is established.
 * 2. File Input: The program opens 'cbp1data.txt'.
 * 3. Scanning: It reads the file line by line.
 * 4. Detection: Each line is checked against the keyword list.
 * 5. Output: If a keyword is found, the line is printed to the console with a
 * "*flag" marker.
 */
