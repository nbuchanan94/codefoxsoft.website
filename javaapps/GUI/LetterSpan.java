// Nicholas Buchanan
// November 6 2011

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class LetterSpan extends JFrame {

    private JTextField l1, l2;
    private JLabel resultSpan;
    private JLabel resultVars;

    public LetterSpan() {
        initGUI();
    }

    private void initGUI() {
        setTitle("LetterSpan - Nick Buchanan");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Typography Theme (Paper Yellow)
        Color paperColor = new Color(255, 253, 208); // Cream/Paper styling
        getContentPane().setBackground(paperColor);

        JPanel inputP = new JPanel();
        inputP.setBackground(paperColor); // Match background
        inputP.add(new JLabel("Letter 1:"));
        l1 = new JTextField(2);
        inputP.add(l1);
        inputP.add(new JLabel("Letter 2:"));
        l2 = new JTextField(2);
        inputP.add(l2);

        JButton btn = new JButton("SCAN");
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.addActionListener(e -> calc());
        inputP.add(btn);

        add(inputP, BorderLayout.NORTH);

        JPanel resP = new JPanel(new GridLayout(2, 1));
        resP.setOpaque(false);

        // "SPAN" in Red
        resultSpan = new JLabel("<html><font color='red'>SPAN</font>: </html>", SwingConstants.CENTER);
        resultSpan.setFont(new Font("Serif", Font.BOLD, 40));
        resP.add(resultSpan);

        resultVars = new JLabel("", SwingConstants.CENTER);
        resultVars.setFont(new Font("Monospaced", Font.PLAIN, 18));
        resP.add(resultVars);

        add(resP, BorderLayout.CENTER);

        setVisible(true);
    }

    private void calc() {
        try {
            String s1 = l1.getText().toUpperCase();
            String s2 = l2.getText().toUpperCase();
            if (s1.isEmpty() || s2.isEmpty())
                return;

            char char1 = s1.charAt(0);
            char char2 = s2.charAt(0);

            int span = Math.abs(char1 - char2);
            resultSpan.setText("<html><font color='red'>SPAN</font>: " + span + "</html>");

            if (span > 1) {
                // Determine letters between
                char start = (char) (Math.min(char1, char2) + 1);
                char end = (char) (Math.max(char1, char2));

                StringBuilder sb = new StringBuilder();
                int count = 0;
                for (char c = start; c < end; c++) {
                    sb.append(c + " ");
                    count++;
                }

                String listStr = sb.toString().trim();

                // "max 9 letters after the word 'between'"
                if (count > 9) {
                    // Wrap to new line, centered
                    resultVars
                            .setText("<html><div style='text-align: center;'>Between:<br>" + listStr + "</div></html>");
                } else {
                    resultVars.setText("Between: " + listStr);
                }
            } else {
                resultVars.setText("");
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new LetterSpan());
        }
    }

    public static void runConsole(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Letter Span Calculator ---");
        System.out
                .println("This program calculates the distance (number of steps) between two letters in the alphabet.");
        System.out.println("Example: The distance between 'A' and 'C' is 2.");
        System.out.println("------------------------------\n");

        System.out.print("Enter first letter: ");
        String input1 = scanner.next().toUpperCase();
        char char1 = input1.charAt(0);

        System.out.print("Enter second letter: ");
        String input2 = scanner.next().toUpperCase();
        char char2 = input2.charAt(0);

        // Calculate absolute difference
        int span = Math.abs(char1 - char2);

        System.out.println("\nCalculated Span: " + span);

        // Optional: List the letters in between if span > 1
        if (span > 1) {
            System.out.print("Letters in between: ");
            char start = (char) (Math.min(char1, char2) + 1);
            char end = (char) (Math.max(char1, char2));
            for (char c = start; c < end; c++) {
                System.out.print(c + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}

/*
 * This program asks the user for two letters and calculates the "span" or
 * numerical usage distance between them in the alphabet.
 * It also lists the specific letters that fall between the two inputs.
 */
