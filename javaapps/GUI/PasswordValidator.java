// Nicholas Buchanan
// December 3, 2011

import java.util.Scanner;
import java.util.regex.Pattern;
import javax.swing.*;
import java.awt.*;

public class PasswordValidator extends JFrame {

    private JTextField passField;
    private JLabel s1Light, s2Light, s3Light;
    private JLabel verdict;

    public PasswordValidator() {
        initGUI();
    }

    private void initGUI() {
        setTitle("PasswordValidator - Nick Buchanan");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Security Vault Theme
        getContentPane().setBackground(Color.DARK_GRAY);

        JPanel p = new JPanel(new GridLayout(5, 1, 10, 10));
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        passField = new JTextField();
        passField.setBackground(Color.BLACK);
        passField.setForeground(Color.GREEN);
        passField.setCaretColor(Color.GREEN);
        p.add(passField);

        JButton btn = new JButton("VALIDATE");
        btn.addActionListener(e -> validatePass());
        p.add(btn);

        s1Light = createLight("Stage 1: Digits");
        p.add(s1Light);
        s2Light = createLight("Stage 2: Uppercase");
        p.add(s2Light);
        s3Light = createLight("Stage 3: Special Chars");
        p.add(s3Light);

        add(p, BorderLayout.CENTER);

        verdict = new JLabel("ENTER PASSWORD", SwingConstants.CENTER);
        verdict.setForeground(Color.WHITE);
        verdict.setFont(new Font("Monospaced", Font.BOLD, 20));
        add(verdict, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JLabel createLight(String txt) {
        JLabel l = new JLabel(txt, SwingConstants.CENTER);
        l.setOpaque(true);
        l.setBackground(Color.GRAY);
        l.setForeground(Color.WHITE);
        l.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return l;
    }

    private void validatePass() {
        String password = passField.getText();
        boolean passedStage1 = false;
        boolean passedStage2 = false;
        boolean passedStage3 = false;

        // Stage 1
        if (Pattern.compile("[0-9]").matcher(password).find()) {
            passedStage1 = true;
            s1Light.setBackground(Color.GREEN);
        } else {
            s1Light.setBackground(Color.RED);
        }

        // Stage 2: Uppercase Check
        if (Pattern.compile("[A-Z]").matcher(password).find()) {
            passedStage2 = true;
            s2Light.setBackground(Color.GREEN);
        } else {
            s2Light.setBackground(Color.RED);
        }

        // Stage 3
        if (Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) {
            passedStage3 = true;
            s3Light.setBackground(Color.GREEN);
        } else {
            s3Light.setBackground(Color.RED);
        }

        int passedCount = 0;
        if (passedStage1)
            passedCount++;
        if (passedStage2)
            passedCount++;
        if (passedStage3)
            passedCount++;

        if (passedCount == 3) {
            verdict.setText("VERDICT: Strong Password!");
            verdict.setForeground(Color.GREEN);
        } else if (passedCount == 2) {
            verdict.setText("VERDICT: Medium Password.");
            verdict.setForeground(Color.YELLOW);
        } else {
            verdict.setText("VERDICT: Weak Password.");
            verdict.setForeground(Color.RED);
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new PasswordValidator());
        }
    }

    public static void runConsole(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Password Validator ---");
        System.out.print("Enter password to test: ");
        String password = scanner.next(); // Read single token password (no spaces)

        boolean passedStage1 = false;
        boolean passedStage2 = false;
        boolean passedStage3 = false;

        // Stage 1: Check for Digits
        // Using Regex to check if contain at least one digit
        if (Pattern.compile("[0-9]").matcher(password).find()) {
            System.out.println("Stage 1 (Digits): PASSED.");
            passedStage1 = true;
        } else {
            System.out.println("Stage 1 (Digits): FAILED. Password must contain at least one number.");
        }

        // Stage 2: Check for Uppercase
        if (Pattern.compile("[A-Z]").matcher(password).find()) {
            System.out.println("Stage 2 (Uppercase): PASSED.");
            passedStage2 = true;
        } else {
            System.out.println("Stage 2 (Uppercase): FAILED. Password must contain at least one uppercase letter.");
        }

        // Stage 3: Check for Special Characters
        if (Pattern.compile("[^a-zA-Z0-9]").matcher(password).find()) {
            System.out.println("Stage 3 (Special Chars): PASSED.");
            passedStage3 = true;
        } else {
            System.out.println(
                    "Stage 3 (Special Chars): FAILED. Password must contain at least one special character (@, #, $, etc).");
        }

        // Final Result
        int passedCount = 0;
        if (passedStage1)
            passedCount++;
        if (passedStage2)
            passedCount++;
        if (passedStage3)
            passedCount++;

        if (passedCount == 3) {
            System.out.println("\nVERDICT: Strong Password!");
        } else if (passedCount == 2) {
            System.out.println("\nVERDICT: Medium Password.");
        } else {
            System.out.println("\nVERDICT: Weak Password.");
        }

        scanner.close();
    }
}

/*
 * 1. Checks if the password contains at least one numeric digit (0-9).
 * 2. Checks if the password contains any immediately repeating characters (e.g.
 * "aa").
 * 3. Checks if the password contains any special characters (non-alphanumeric).
 * 4. Outputs the result of each stage and a final verdict.
 */
