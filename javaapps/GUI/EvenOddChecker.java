// Nicholas Buchanan
// September 2010

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EvenOddChecker extends JFrame {

    private JTextField inputField;
    private JLabel resultLabel;

    public EvenOddChecker() {
        initGUI();
    }

    private void initGUI() {
        setTitle("EvenOddChecker - Nick Buchanan");
        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // B&W Theme
        getContentPane().setBackground(Color.BLACK);

        JPanel centerP = new JPanel(new GridLayout(2, 1));
        centerP.setBackground(Color.BLACK);

        inputField = new JTextField();
        inputField.setFont(new Font("Courier New", Font.BOLD, 24));
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.addActionListener(e -> check()); // Enter key
        centerP.add(inputField);

        JButton btn = new JButton("CHECK");
        btn.setBackground(Color.WHITE);
        btn.setForeground(Color.BLACK);
        btn.addActionListener(e -> check());
        centerP.add(btn);

        add(centerP, BorderLayout.CENTER);

        resultLabel = new JLabel("?", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 32));
        resultLabel.setForeground(Color.WHITE);
        add(resultLabel, BorderLayout.SOUTH);

        JLabel title = new JLabel("Even OR Odd", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        setVisible(true);
    }

    private void check() {
        try {
            int n = Integer.parseInt(inputField.getText());
            if (n % 2 == 0) {
                resultLabel.setText("EVEN");
                resultLabel.setForeground(Color.GREEN);
            } else {
                resultLabel.setText("ODD");
                resultLabel.setForeground(Color.RED);
            }
        } catch (Exception e) {
            resultLabel.setText("NaN");
            resultLabel.setForeground(Color.GRAY);
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new EvenOddChecker());
        }
    }

    public static void runConsole(String[] args) {
        System.out.println("--- Even or Odd Checker ---");
        System.out.print("Enter a number: ");

        Scanner scanner = new Scanner(System.in);

        try {
            int n = scanner.nextInt();

            if (n % 2 == 0) {
                System.out.println("The number " + n + " is Even.");
            } else {
                System.out.println("The number " + n + " is Odd.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid integer.");
        } finally {
            scanner.close();
        }
    }
}

/*
 * This program accepts an integer input from the user and determines
 * whether it is even or odd using the modulus operator (%).
 *
 * It serves as a fundamental example of conditional logic (if-else)
 * in Java programming.
 */
