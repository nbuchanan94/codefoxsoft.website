// Nicholas Buchanan
// Friday, November 12, 2010

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class SimplePalindrome extends JFrame {

    private JTextField inputField;
    private JLabel resultLabel;

    public SimplePalindrome() {
        initGUI();
    }

    private void initGUI() {
        setTitle("SimplePalindrome - Nick Buchanan");
        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Simple Blue Mirror Theme
        getContentPane().setBackground(new Color(173, 216, 230));

        JPanel c = new JPanel(new GridLayout(3, 1));
        c.setOpaque(false);
        c.add(new JLabel("Check Word:"));
        inputField = new JTextField();
        inputField.addActionListener(e -> check());
        c.add(inputField);
        JButton btn = new JButton("Check");
        btn.addActionListener(e -> check());
        c.add(btn);
        add(c, BorderLayout.CENTER);

        resultLabel = new JLabel("Waiting...", SwingConstants.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void check() {
        String originalInput = inputField.getText();
        String cleanedInput = originalInput.replace(" ", "").toLowerCase();
        StringBuilder reversedBuilder = new StringBuilder(cleanedInput);
        String reversedString = reversedBuilder.reverse().toString();

        if (cleanedInput.equals(reversedString)) {
            resultLabel.setText("Yes!");
            getContentPane().setBackground(Color.GREEN);
        } else {
            resultLabel.setText("No.");
            getContentPane().setBackground(Color.RED);
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new SimplePalindrome());
        }
    }

    public static void runConsole(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Palindrome Checker ---");
        System.out.print("Enter a word or phrase to check: ");
        String originalInput = scanner.nextLine();

        // Clean the input: Remove spaces and convert to lowercase for accurate checking
        String cleanedInput = originalInput.replace(" ", "").toLowerCase();

        // Reverse the string
        StringBuilder reversedBuilder = new StringBuilder(cleanedInput);
        String reversedString = reversedBuilder.reverse().toString();

        System.out.println("\nOriginal (Cleaned): " + cleanedInput);
        System.out.println("Reversed (Cleaned): " + reversedString);

        // Check if Palindrome
        System.out.print("Is it a palindrome? ");
        if (cleanedInput.equals(reversedString)) {
            System.out.println("Yes!");
        } else {
            System.out.println("No.");
        }

        scanner.close();
    }
}

/*
 * This program checks whether a given string is a palindrome (reads the same
 * backwards as forwards).
 */
