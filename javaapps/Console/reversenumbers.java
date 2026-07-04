
// Nicholas Larkin Buchanan
// 2012
import java.io.*;
import java.util.*;
import java.lang.*;
import java.util.StringTokenizer;
import java.lang.Integer;

public class reversenumbers {
	public static void main(String[] args) {
		System.out.println("Enter your 2 numbers!");
		Scanner kb = new Scanner(System.in); // creates a new instance of Scanner class called kb//
		int number1 = kb.nextInt(); // stores input from keyboard into a memory location called string//
		int number2 = kb.nextInt();
		StringBuffer reverse1 = new StringBuffer(number1 + "");
		reverse1.reverse();
		StringBuffer reverse2 = new StringBuffer(number2 + "");
		reverse2.reverse();
		int num1 = Integer.parseInt(reverse1.toString());
		int num2 = Integer.parseInt(reverse2.toString());
		int sum = num1 + num2;
		String callit = sum + "";
		while (callit.substring(callit.length() - 1).equals("0")) {

			sum /= 10;
			callit = sum + "";
		}

		StringBuffer sumreverse = new StringBuffer(sum + "");
		System.out.println("Reversed Sum Result: " + sumreverse.reverse());

	}
}
// Run the program and enter two numbers to see the reversed sum calculation.