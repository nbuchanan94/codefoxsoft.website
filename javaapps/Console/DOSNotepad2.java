
// Nicholas Larkin Buchanan
// April 14, 2012
import java.io.*;
import java.util.*;

public class DOSNotepad2 {
	private static ArrayList<String> buffer = new ArrayList<String>();
	private static Scanner scanner = new Scanner(System.in);
	private static String fileName = "DOSNotepad.txt";

	public static void main(String[] args) {
		System.out.println("::: WELCOME TO DOS NOTEPAD (VIM Style) :::");
		System.out.println("::: Made by Nick Buchanan :::");
		System.out.println("\nCommands: [i]nsert, [p]rint, [w]rite, [c]lear, [q]uit");

		boolean running = true;
		while (running) {
			System.out.println("-- COMMAND MODE --");
			System.out.println("Commands: [i]nsert, [p]rint, [w]rite, [c]lear, [q]uit");
			System.out.print(": ");
			String input = scanner.nextLine().trim();
			if (input.length() == 0)
				continue;

			char command = input.charAt(0);
			String arg = "";
			if (input.length() > 1) {
				arg = input.substring(1).trim();
			}

			switch (command) {
				case 'i': // Insert Mode
					System.out.println("-- INSERT MODE (Type '.' on a new line to exit) --");
					if (arg.length() > 0) {
						buffer.add(arg);
						System.out.println("[Added line: " + arg + "]");
					}
					while (true) {
						String line = scanner.nextLine();
						if (line.equals(".")) {
							System.out.println("-- COMMAND MODE --");
							break;
						}
						buffer.add(line);
					}
					break;

				case 'p': // Print Buffer
					if (buffer.isEmpty()) {
						System.out.println("[Buffer is empty]");
					} else {
						System.out.println("--- BUFFER START ---");
						for (int i = 0; i < buffer.size(); i++) {
							System.out.println((i + 1) + " | " + buffer.get(i));
						}
						System.out.println("--- BUFFER END ---");
					}
					break;

				case 'c': // Clear Buffer
					buffer.clear();
					System.out.println("[Buffer Cleared]");
					break;

				case 'w': // Write to File
					try {
						if (arg.length() > 0) {
							fileName = arg;
						} else {
							System.out.print("Filename [" + fileName + "]: ");
							String fn = scanner.nextLine().trim();
							if (fn.length() > 0)
								fileName = fn;
						}

						FileWriter fw = new FileWriter(fileName);
						PrintWriter pw = new PrintWriter(fw);
						for (String s : buffer) {
							pw.println(s);
						}
						pw.close();
						fw.close();
						System.out.println("[Wrote " + buffer.size() + " lines to " + fileName + "]");
					} catch (IOException e) {
						System.out.println("Error writing file: " + e.getMessage());
					}
					break;

				case 'q': // Quit
					running = false;
					System.out.println("Exiting DOSNotepad...");
					break;

				default:
					System.out.println("Unknown command: " + command);
					System.out.println("Usage: i [text], p, w [filename], c, q");
					break;
			}
		}
	}
}
// VIM-style Editor Instructions:
// 1. Run the program.
// 2. Type 'i' and press Enter to start typing text.
// 3. Type '.' on a line by itself to finish typing.
// 4. Type 'p' to view what you typed.
// 5. Type 'w' to save to a file.
// 6. Type 'q' to quit.