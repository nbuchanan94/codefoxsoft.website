//Nicholas Larkin Buchanan
//2010-03-01

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class GPACalculator extends JFrame {

    private JTextField[] grades = new JTextField[7];
    private JLabel resultLabel;

    public GPACalculator() {
        initGUI();
    }

    private void initGUI() {
        setTitle("GPACalculator - Nick Buchanan");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // School Theme (Yellow/White)
        getContentPane().setBackground(new Color(255, 255, 240)); // Ivory

        JPanel p = new JPanel(new GridLayout(8, 2, 5, 5)); // 7 inputs + header
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        p.add(new JLabel("Class"));
        p.add(new JLabel("Grade (A/B/C/D/F)"));

        for (int i = 0; i < 7; i++) {
            p.add(new JLabel("Period " + (i + 1) + ":"));
            grades[i] = new JTextField(2);
            grades[i].setHorizontalAlignment(JTextField.CENTER);
            p.add(grades[i]);
        }
        add(p, BorderLayout.CENTER);

        JPanel bot = new JPanel(new BorderLayout());
        bot.setOpaque(false);

        JButton btn = new JButton("CALCULATE GPA");
        btn.setBackground(new Color(255, 215, 0)); // Gold
        btn.addActionListener(e -> calc());
        bot.add(btn, BorderLayout.NORTH);

        resultLabel = new JLabel("GPA: ---", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Serif", Font.BOLD, 32));
        resultLabel.setForeground(Color.BLACK);
        bot.add(resultLabel, BorderLayout.SOUTH);

        add(bot, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void calc() {
        double gpa = 0;
        for (int i = 0; i < 7; i++) {
            String g = grades[i].getText().trim().toLowerCase();
            if (g.equals("a"))
                gpa += 4;
            else if (g.equals("b"))
                gpa += 3;
            else if (g.equals("c"))
                gpa += 2;
            else if (g.equals("d"))
                gpa += 1;
        }
        gpa = gpa / 7.0;
        resultLabel.setText(String.format("GPA: %.3f", gpa));
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new GPACalculator());
        }
    }

    public static void runConsole(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double gpa = 0;

        for (int i = 1; i <= 7; i++) {
            System.out.print("enter grade " + i + ": ");
            String grade = scanner.nextLine().toLowerCase();

            if (grade.equals("a")) {
                gpa += 4;
            } else if (grade.equals("b")) {
                gpa += 3;
            } else if (grade.equals("c")) {
                gpa += 2;
            } else if (grade.equals("d")) {
                gpa += 1;
            } else {
                gpa += 0;
            }
            System.out.println("Current Sum: " + gpa);
        }

        gpa = gpa / 7.0;
        System.out.printf("Your GPA is %.3f.\n", gpa);
    }
}
// Usage: Calculates GPA from 7 letter grades.
