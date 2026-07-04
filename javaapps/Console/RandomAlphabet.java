
//Nicholas Larkin Buchanan
//2012-09-01
//pd 8
import java.util.*;
import java.io.*;
import java.util.Random;

public class RandomAlphabet {
	public static void main(String[] args) {
		System.out.println("System: Application Start");
		System.out.println("System: Generating random alphabet permutation...");

		String[] alphabet = new String[26];
		Random r = new Random();
		boolean[] randoms = new boolean[26];
		for (int i = 65; i <= 90; i++) {
			alphabet[i - 65] = (char) (i) + "";
		}
		for (int i = 0; i < 26; i++) {
			int rn = r.nextInt(26);
			if (!randoms[rn]) {
				System.out.println(alphabet[rn]);
				randoms[rn] = true;
			} else {
				i--;

			}
		}
		System.out.println("System: Application End");

	}
}

// Usage: Run this file to generate a random permutation of the alphabet (A-Z).