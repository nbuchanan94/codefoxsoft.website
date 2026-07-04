
// Nicholas Larkin Buchanan
// May 2, 2011
import java.io.*;
import java.util.*;

public class teacher {
	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		System.out.println("how many students?");
		int numStudents = kb.nextInt();
		String[] names = new String[numStudents];
		int[] grades = new int[numStudents];
		boolean studentsGraded = false;

		while (true) {
			System.out.println("\nTEACHER GRADE PROGRAM");
			System.out.println(" 1) punch in grades");
			System.out.println(" 2) View Grades");
			System.out.println(" 3) exit");
			System.out.println();
			System.out.print("What is your choice? (1, 2, or 3?) ");

			int choice = kb.nextInt();

			switch (choice) {
				case 1:
					System.out.println();
					for (int i = 0; i < numStudents; i++) {
						System.out.println("Student " + (i + 1) + ":");
						System.out.print("Enter student name: ");
						names[i] = kb.next();

						System.out.print("Please enter grade: ");
						grades[i] = kb.nextInt();
						int grade = grades[i];

						if (grade >= 90) {
							System.out.println(names[i] + ", Great job You got an A!!!");
						} else if ((grade >= 80) && (grade <= 90)) {
							System.out.println(names[i] + ", Good you have a B!!!!!");
						} else if ((grade >= 70) && (grade <= 79)) {
							System.out.println(names[i] + ", Thats nice you have a C!");
						} else if ((grade >= 60) && (grade <= 69)) {
							System.out.println(names[i] + ", your almost there! you have a D");
						} else {
							System.out.println(names[i] + ", FAILURE!!! YOU HAVE BELOW A D!!! THATS AN F!!!");
						}
					}
					studentsGraded = true;
					break;
				case 2:
					if (studentsGraded) {
						System.out.println("\n--- Gradebook ---");
						for (int i = 0; i < numStudents; i++) {
							System.out.println("Student: " + names[i] + " | Grade: " + grades[i]);
						}
					} else {
						System.out.println("\nNo grades entered yet. Please select Option 1 first.");
					}
					break;
				case 3:
					System.out.println("GOODBYE");
					return; // Exit program
				default:
					System.out.println("Invalid choice.");
					break;
			}
		}
	}
}
// Run the program to grade students based on their scores.