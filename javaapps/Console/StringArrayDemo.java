
//Nicholas Larkin Buchanan
//2011-02-05
//pd 8
import java.io.*;
import java.util.*;

public class StringArrayDemo {
	public static void main(String[] args) {
		System.out.println("System: Application Start");
		Scanner kb = new Scanner(System.in);

		System.out.print("How many times run the loop? ");
		int innum = kb.nextInt();

		// Fix: Consume leftover newline from nextInt()
		kb.nextLine();

		System.out.println("DEBUG: Array size set to " + innum);
		String[] name = new String[innum];

		System.out.println("ENTER STRING THEN PRESS ENTER! (Repeat " + innum + " times)");

		// Fixed loop: 0 to < innum (Standard Java Array iteration)
		for (int i = 0; i < innum; i++) {
			System.out.print("ENTER A STRING (" + (i + 1) + "/" + innum + "): ");
			name[i] = kb.nextLine();
		}

		System.out.println("--- Results ---");
		for (int j = 0; j < innum; j++) {
			System.out.println("Index " + j + ": " + name[j]);
		}
		System.out.println("System: Application End");
	}
}
//demonstrates how to store multiple inputs as an string array to save more of user input at once.