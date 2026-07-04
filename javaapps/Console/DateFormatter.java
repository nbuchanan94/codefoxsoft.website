
//Nicholas Larkin Buchanan
//2011-02-20
//pd 8
import java.io.*;
import java.util.*;

public class DateFormatter {
	public static void main(String[] args) {
		// DateFormatter.java The program should then print the date in the format
		// Month, Day, Year such as February 25, 2002.

		System.out.println("System: Application Start");
		Scanner kb = new Scanner(System.in);
		System.out.print("enter a date in mm dd yy (include spaces): ");

		String m = kb.nextLine();
		System.out.println("DEBUG: User Input: " + m);

		StringTokenizer st = new StringTokenizer(m);

		if (st.countTokens() < 3) {
			System.out.println("Error: Please enter 3 numbers separated by spaces.");
			return;
		}

		String mm = st.nextToken();
		String dd = st.nextToken();
		String yy = st.nextToken();

		System.out.println("DEBUG: Parsed Month: " + mm + ", Day: " + dd + ", Year: " + yy);

		String monthName = "";

		if (mm.equals("1")) {
			monthName = "JANUARY";
		} else if (mm.equals("2")) {
			monthName = "FEBRUARY";
		} else if (mm.equals("3")) {
			monthName = "MARCH";
		} else if (mm.equals("4")) {
			monthName = "APRIL";
		} else if (mm.equals("5")) {
			monthName = "MAY";
		} else if (mm.equals("6")) {
			monthName = "JUNE";
		} else if (mm.equals("7")) {
			monthName = "JULY";
		} else if (mm.equals("8")) {
			monthName = "AUGUST";
		} else if (mm.equals("9")) {
			monthName = "SEPTEMBER";
		} else if (mm.equals("10")) {
			monthName = "OCTOBER";
		} else if (mm.equals("11")) {
			monthName = "NOVEMBER";
		} else if (mm.equals("12")) {
			monthName = "DECEMBER";
		} else {
			System.out.println("Invalid Month Number");
			return;
		}

		System.out.println("System: Outputting formatted date");
		System.out.println(monthName + ", " + dd + ", 20" + yy);

		System.out.println("System: Application End");
	}
}
// Usage: Run this file and input a date (e.g., '1 25 10'). Returns formatted
// date string.
