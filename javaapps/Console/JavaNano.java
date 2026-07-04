// Nicholas Buchanan
// December 2011
// JavaNano.java

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class JavaNano {
	private static final String FILENAME = "notepad_data.txt";
	private static ArrayList<String> buffer = new ArrayList<>();

	public static void main(String[] args) {
		loadFile();
		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome to JavaNano via Console!");
		printInterface();

		while (true) {
			System.out.print("> ");
			String input = scanner.nextLine();

			if (input.equalsIgnoreCase(":x") || input.equalsIgnoreCase(":exit")) {
				System.out.print("Save changes? (y/n): ");
				String save = scanner.nextLine();
				if (save.equalsIgnoreCase("y")) {
					saveFile();
				}
				break;
			} else if (input.equalsIgnoreCase(":w") || input.equalsIgnoreCase(":save")) {
				saveFile();
				printInterface(); // Redraw
			} else if (input.equalsIgnoreCase(":p") || input.equalsIgnoreCase(":print")) {
				printInterface();
			} else {
				buffer.add(input);
				// Optional: Auto-save or just keep in memory. Nano keeps in memory.
			}
		}

		System.out.println("Exited JavaNano.");
		scanner.close();
	}

	private static void loadFile() {
		try {
			File file = new File(FILENAME);
			if (file.exists()) {
				Scanner fileScanner = new Scanner(file);
				while (fileScanner.hasNextLine()) {
					buffer.add(fileScanner.nextLine());
				}
				fileScanner.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error loading file.");
		}
	}

	private static void saveFile() {
		try {
			FileWriter fw = new FileWriter(FILENAME);
			PrintWriter output = new PrintWriter(fw);

			for (String line : buffer) {
				output.println(line);
			}

			output.close();
			fw.close();
			System.out.println(" [ Wrote " + buffer.size() + " lines ]");
		} catch (IOException e) {
			System.out.println("Error saving file.");
		}
	}

	private static void printInterface() {
		// "Clear" screen
		for (int i = 0; i < 3; i++)
			System.out.println();

		System.out.println("  FAUXCNANO             File: " + FILENAME);
		System.out.println("--------------------------------------------------------------------------------");

		if (buffer.isEmpty()) {
			System.out.println("  [ New File ]");
		} else {
			for (int i = 0; i < buffer.size(); i++) {
				System.out.println((i + 1) + "  " + buffer.get(i));
			}
		}

		System.out.println("--------------------------------------------------------------------------------");
		System.out.println(":G Get Help   :O WriteOut   :R Read File   :Y Prev Page   :K Cut Text    :C Cur Pos");
		System.out.println(":X Exit       :J Justify    :W Where Is    :V Next Page   :U UnCut Text  :T To Spell");
		System.out.println("\n(Simulation Note: Type ':w' to Save, ':x' to Exit, ':p' to Reprint View)");
	}
}

/*
 * 1. Persistent Storage: Automatically loads and saves to 'notepad_data.txt'.
 * 2. Line Buffer: Stores text in memory (ArrayList) during editing.
 * 3. Command System:
 * - Type any text to add a new line.
 * - Type ':w' to write (save) the file.
 * - Type ':x' to search exit the program.
 * - Type ':p' to reprint the current document view.
 */