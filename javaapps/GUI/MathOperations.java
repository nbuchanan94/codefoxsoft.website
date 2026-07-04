// Nicholas Buchanan
// 2011
// MathOperations.java

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class MathOperations extends JFrame {

    private JTextField n1Field, n2Field;
    private JTextArea outArea;

    public MathOperations() {
        initGUI();
    }

    private void initGUI() {
        setTitle("MathOperations - Nick Buchanan");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Scientific Theme (Dark Blue)
        getContentPane().setBackground(new Color(25, 25, 112)); // Midnight Blue

        JPanel top = new JPanel(new GridLayout(3, 2));
        top.setOpaque(false);

        JLabel l1 = new JLabel("Number:");
        l1.setForeground(Color.WHITE);
        top.add(l1);
        n1Field = new JTextField();
        top.add(n1Field);

        // Single input layout adjustment
        // Removed n2Field

        JButton btn = new JButton("PERFORM OPERATIONS");
        btn.addActionListener(e -> runOps());
        top.add(btn);

        add(top, BorderLayout.NORTH);

        outArea = new JTextArea();
        outArea.setEditable(false);
        outArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(outArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void runOps() {
        try {
            double num1 = Double.parseDouble(n1Field.getText());

            StringBuilder sb = new StringBuilder();
            sb.append("--- Results ---\n");
            sb.append("Absolute Value:    " + Math.abs(num1) + "\n");
            sb.append("Square (n^2):      " + Math.pow(num1, 2) + "\n");
            sb.append("Cube (n^3):        " + Math.pow(num1, 3) + "\n");
            sb.append("Square Root:       " + Math.sqrt(num1) + "\n");
            sb.append("Ceiling:           " + Math.ceil(num1) + "\n");
            sb.append("Floor:             " + Math.floor(num1) + "\n");
            sb.append("Round:             " + Math.round(num1) + "\n");
            double randomVal = Math.random() * 10;
            sb.append("Random Value (0-10): " + (int) randomVal + "\n");

            outArea.setText(sb.toString());
        } catch (Exception e) {
            outArea.setText("Invalid Input");
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new MathOperations());
        }
    }

    public static void runConsole(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Math Library Demo ---");
        System.out.println("Enter a number:");

        try {
            double num1 = scanner.nextDouble();

            System.out.println("\n--- Results ---");
            System.out.println("Absolute Value:    " + Math.abs(num1));
            System.out.println("Square (n^2):      " + Math.pow(num1, 2));
            System.out.println("Cube (n^3):        " + Math.pow(num1, 3));
            System.out.println("Square Root:       " + Math.sqrt(num1));
            System.out.println("Ceiling:           " + Math.ceil(num1));
            System.out.println("Floor:             " + Math.floor(num1));
            System.out.println("Round:             " + Math.round(num1));

            double randomVal = Math.random() * 10;
            System.out.println("Random Value (0-10): " + (int) randomVal);

        } catch (Exception e) {
            System.out.println("Invalid input. Please enter valid numbers.");
        } finally {
            scanner.close();
        }
    }
}

/*
 * This program demonstrates the functionality of various methods within
 * Java's standard `Math` class. It accepts two numerical inputs from the
 * user and performs calculations such as:
 * - Absolute Value
 * - Exponentiation (Power)
 * - Square Root
 * - Ceiling and Floor rounding
 * - Minimum and Maximum comparison
 * - Rounding
 * - Random number generation
 */
