
//Nicholas Larkin Buchanan
//2013-03-01
//pd 8
import java.util.*;

public class NamePrefixer {
	public static void main(String[] args) {
		System.out.println("System: Application Start");
		Scanner kbreader = new Scanner(System.in);
		System.out.println("Enter names separated by spaces (e.g. 'Amy Smith Fred Jones'):");
		String names = kbreader.nextLine();
		System.out.println("DEBUG: Input received: " + names);

		StringTokenizer st = new StringTokenizer(names, " ");
		String stt;

		while (st.hasMoreTokens()) {
			stt = st.nextToken();
			String lastName = "";
			if (st.hasMoreTokens()) {
				lastName = st.nextToken();
			} else {
				System.out.println("DEBUG: Warning - No last name for " + stt);
			}

			if ((stt.compareToIgnoreCase("amy") == 0) || (stt.compareToIgnoreCase("buffy") == 0)
					|| (stt.compareToIgnoreCase("cathy") == 0)) {
				System.out.println("Ms. " + stt + " " + lastName);
			} else if ((stt.compareToIgnoreCase("elroy") == 0) || (stt.compareToIgnoreCase("fred") == 0)
					|| (stt.compareToIgnoreCase("graham") == 0)) {
				System.out.println("Mr. " + stt + " " + lastName);

			} else {
				System.out.println(stt + " " + lastName);
			}
		}
		System.out.println("System: Application End");
	}
}
// Usage: Run this file. Enter Full Names. It adds Ms./Mr. to specific First
// Names.