
/*
 * Nicholas Larkin Buchanan
 * 2011-03-31
 *
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

public class StringRotator extends JFrame {
    private JTextField inputField;
    private JLabel resultLabel;

    public StringRotator() {
        super("StringRotator - Nick Buchanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BorderLayout(10, 10));

        // Spin/Cyan Theme
        getContentPane().setBackground(Color.CYAN);

        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        centerPanel.setBackground(Color.CYAN);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel prompt = new JLabel("Enter string (end with digit):");
        inputField = new JTextField();
        JButton rotateButton = new JButton("Rotate");

        centerPanel.add(prompt);
        centerPanel.add(inputField);
        centerPanel.add(rotateButton);
        add(centerPanel, BorderLayout.CENTER);

        resultLabel = new JLabel("Result will appear here", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultLabel.setOpaque(true);
        resultLabel.setBackground(Color.WHITE);
        resultLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        resultLabel.setPreferredSize(new Dimension(400, 50));
        add(resultLabel, BorderLayout.SOUTH);

        rotateButton.addActionListener(e -> rotate());
        inputField.addActionListener(e -> rotate());

        setVisible(true);
    }

    private void rotate() {
        String input = inputField.getText().trim();
        if (input.length() < 2) {
            resultLabel.setText("Error: String too short");
            return;
        }

        try {
            // Get last character
            char lastChar = input.charAt(input.length() - 1);
            int rotateAmount = Integer.parseInt(String.valueOf(lastChar));

            // The string to rotate is everything BUT the last digit
            String toRotate = input.substring(0, input.length() - 1);

            if (rotateAmount > toRotate.length()) {
                rotateAmount = rotateAmount % toRotate.length();
            }

            // Logic: Rotate RIGHT by amount
            // "Hello2" -> "loHel" (Rotate "Hello" right by 2) -> "loHel"
            // Wait, original logic check:
            // If input is "Hello2", n=2.
            // Right rotation: last n chars go to front.
            // "Hello" (len 5). n=2. "lo" + "Hel".

            String part1 = toRotate.substring(toRotate.length() - rotateAmount);
            String part2 = toRotate.substring(0, toRotate.length() - rotateAmount);
            String result = part1 + part2;

            resultLabel.setText(result);

        } catch (NumberFormatException ex) {
            resultLabel.setText("Error: Last char must be a digit");
        } catch (Exception ex) {
            resultLabel.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(StringRotator::new);
        }
    }

    public static void runConsole(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter string with trailing digit: ");
        String input = kb.nextLine().trim();

        try {
            int rotateAmount = Integer.parseInt(input.substring(input.length() - 1));
            String toRotate = input.substring(0, input.length() - 1);
            if (rotateAmount > toRotate.length()) {
                rotateAmount = rotateAmount % toRotate.length();
            }
            String part1 = toRotate.substring(toRotate.length() - rotateAmount);
            String part2 = toRotate.substring(0, toRotate.length() - rotateAmount);
            System.out.println(part1 + part2);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
