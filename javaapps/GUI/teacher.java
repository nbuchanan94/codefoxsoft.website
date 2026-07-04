
// Nicholas Larkin Buchanan
// May 2, 2011
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.ArrayList;

public class teacher extends JFrame {

    // GUI Components
    private JTextField nameField;
    private JTextField gradeField;
    private JTextArea gradeBookArea;
    private ArrayList<String> students = new ArrayList<>();

    public teacher() {
        initGUI();
    }

    private void initGUI() {
        setTitle("teacher - Nick Buchanan");
        setSize(700,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Red Theme for School
        getContentPane().setBackground(new Color(178, 34, 34)); // Firebrick Red

        JLabel title = new JLabel("Teacher Gradebook", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBackground(new Color(255, 99, 71)); // Tomato
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Grade (0-100):"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        JButton addBtn = new JButton("Add Grade");
        addBtn.addActionListener(e -> addGrade());
        inputPanel.add(addBtn);

        JButton clearBtn = new JButton("Clear All");
        clearBtn.addActionListener(e -> {
            students.clear();
            gradeBookArea.setText("");
        });
        inputPanel.add(clearBtn);

        add(inputPanel, BorderLayout.CENTER);

        gradeBookArea = new JTextArea();
        gradeBookArea.setEditable(false);
        gradeBookArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        gradeBookArea.setText("--- Gradebook ---\n");
        add(new JScrollPane(gradeBookArea), BorderLayout.SOUTH);
        gradeBookArea.setPreferredSize(new Dimension(480, 200));

        pack();
        setVisible(true);
    }

    private void addGrade() {
        String name = nameField.getText().trim();
        String gradeStr = gradeField.getText().trim();

        if (!name.isEmpty() && !gradeStr.isEmpty()) {
            try {
                int grade = Integer.parseInt(gradeStr);
                String message;
                if (grade >= 90)
                    message = "Great job You got an A!!!";
                else if (grade >= 80)
                    message = "Good you have a B!!!!!";
                else if (grade >= 70)
                    message = "Thats nice you have a C!";
                else if (grade >= 60)
                    message = "your almost there! you have a D";
                else
                    message = "FAILURE!!! YOU HAVE BELOW A D!!! THATS AN F!!!";

                String entry = String.format("Student: %-15s | Grade: %3d | %s", name, grade, message);
                students.add(entry);
                gradeBookArea.append(entry + "\n");
                nameField.setText("");
                gradeField.setText("");
            } catch (Exception e) {
            }
        }
    }

    // Hybrid Main
    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new teacher());
        }
    }

    // Original Main Logic Encapsulated
    public static void runConsole(String[] args) {
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
