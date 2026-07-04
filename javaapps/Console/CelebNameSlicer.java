// Nicholas Larkin Buchanan
// October 12, 2010
public class CelebNameSlicer {
    public static void main(String args[]) {
        System.out.println("Processing Celebrity Names (Removing first 2 and last 3 chars)...");

        String s1 = "Richard Williams";
        String s2 = "John Wayne";
        String s3 = "Gregory Peck";

        System.out.print(s1 + " >>> ");
        String smallpart = s1.substring(2, s1.length() - 3);
        System.out.println(smallpart);

        System.out.print(s2 + " >>> ");
        String smallpart2 = s2.substring(2, s2.length() - 3);
        System.out.println(smallpart2);

        System.out.print(s3 + " >>> ");
        String smallpart3 = s3.substring(2, s3.length() - 3);
        System.out.println(smallpart3);
    }
}
// Run to see substring operations on celebrity names.
