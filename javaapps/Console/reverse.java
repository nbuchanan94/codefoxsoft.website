
// Nicholas Larkin Buchanan
// 2012
import javax.swing.JOptionPane;
import java.io.*;
import java.util.*;
import java.lang.StringBuffer;

public class reverse {
	public static void main(String[] args) {
		Scanner kbRead = new Scanner(System.in);
		System.out.print("TYPE WHAT YOU WANT BACKWARDS: ");
		String string = kbRead.nextLine();
		StringBuffer reversedText = new StringBuffer(string);
		System.out.println("Reversed Result: " + reversedText.reverse());
	}
}
// Run the program and enter text to see it reversed.