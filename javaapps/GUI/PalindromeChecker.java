// Nicholas Buchanan
// Friday, November 12, 2010

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class PalindromeChecker extends JFrame {

    private JTextField inputField;
    private JLabel reflectionLabel;
    private JLabel resultLabel;

    public PalindromeChecker() {
        initGUI();
    }

    private void initGUI() {
        setTitle("PalindromeChecker - Nick Buchanan");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Mirror Theme (Silver/Glass)
        getContentPane().setBackground(new Color(192, 192, 192));

        JPanel p = new JPanel(new GridLayout(3, 1));
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        inputField = new JTextField("Race Car");
        inputField.setFont(new Font("Arial", Font.BOLD, 24));
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.addActionListener(e -> check());
        p.add(inputField);

        reflectionLabel = new JLabel("raC ecaR", SwingConstants.CENTER); // Placeholder
        reflectionLabel.setFont(new Font("Arial", Font.ITALIC, 24));
        reflectionLabel.setForeground(Color.DARK_GRAY);
        // Visual trick: Flip transform? Swing has no native text flip, so we keep text
        // reverse.
        p.add(reflectionLabel);

        resultLabel = new JLabel("?", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        p.add(resultLabel);

        add(p, BorderLayout.CENTER);

        JButton btn = new JButton("CHECK REFLECTION");
        btn.addActionListener(e -> check());
        add(btn, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void check() {
        String originalInput = inputField.getText();
        String cleanText = originalInput.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        StringBuilder reversedBuilder = new StringBuilder(cleanText);
        String reversedText = reversedBuilder.reverse().toString();

        // Visual Reflection (Raw reverse)
        reflectionLabel.setText(new StringBuilder(originalInput).reverse().toString());

        if (cleanText.equals(reversedText)) {
            resultLabel.setText("RESULT: YES, proper palindrome.");
            resultLabel.setForeground(new Color(0, 100, 0));
        } else {
            resultLabel.setText("RESULT: NO.");
            resultLabel.setForeground(Color.RED);
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new PalindromeChecker());
        }
    }

    public static void runConsole(String[] args) {
        Scanner kbRead = new Scanner(System.in);

        System.out.println("--- Palindrome Checker ---");
        System.out.print("Enter text to check: ");
        String originalInput = kbRead.nextLine();

        // Strip out spaces and punctuation, and convert to lowercase
        // This allows phrases like "Race car" to be detected as palindromes
        String cleanText = originalInput.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        // Create a reversed version of the cleaned text
        StringBuilder reversedBuilder = new StringBuilder(cleanText);
        String reversedText = reversedBuilder.reverse().toString();

        System.out.println("\nOriginal: " + originalInput);
        System.out.println("Cleaned:  " + cleanText);
        System.out.println("Reversed: " + reversedText);

        // Compare the content of the strings
        if (cleanText.equals(reversedText)) {
            System.out.println("\nRESULT: YES, proper palindrome.");
        } else {
            System.out.println("\nRESULT: NO.");
        }

        kbRead.close();
    }
}

/*
 * 1. Accepts user input.
 * 2. Pre-processes the string by:
 * - Removing all non-alphanumeric characters (punctuation, spaces).
 * - Converting to lowercase.
 * 3. Reverses the processed string.
 * 4. Compares the forward and backward versions.
 * 5. If they match, the input is a palindrome.
 */
