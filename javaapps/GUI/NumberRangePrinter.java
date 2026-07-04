
/*
 * Nicholas Larkin Buchanan
 * 2010-02-17
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

public class NumberRangePrinter extends JFrame {
    private JTextField startField;
    private JTextField endField;
    private JTextArea outputArea;

    public NumberRangePrinter() {
        super("NumberRangePrinter - Nick Buchanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 400);
        setLayout(new BorderLayout(10, 10));

        // Sequence/Blue Theme
        getContentPane().setBackground(new Color(135, 206, 250)); // Light Sky Blue

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.setOpaque(false);
        inputPanel.add(new JLabel("Start Number:"));
        startField = new JTextField();
        inputPanel.add(startField);

        inputPanel.add(new JLabel("End Number:"));
        endField = new JTextField();
        inputPanel.add(endField);

        JButton printButton = new JButton("Print Range");
        inputPanel.add(new JLabel("")); // spacer
        inputPanel.add(printButton);

        add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        printButton.addActionListener(e -> printRange());

        setVisible(true);
    }

    private void printRange() {
        outputArea.setText("");
        try {
            int num1 = Integer.parseInt(startField.getText());
            int num2 = Integer.parseInt(endField.getText());

            outputArea.append("Start: " + num1 + "\n");
            outputArea.append("Your numbers:\n");

            // Original logic: while num1 < num2, num1++, print.
            // This effectively prints (start+1) to end.
            int current = num1;
            while (current < num2) {
                current++;
                outputArea.append(current + "\n");
            }
        } catch (NumberFormatException e) {
            outputArea.setText("Invalid input.");
        }
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(NumberRangePrinter::new);
        }
    }

    public static void runConsole(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter 2 numbers");
        int num1 = Integer.parseInt(scanner.nextLine());
        int num2 = Integer.parseInt(scanner.nextLine());

        System.out.println("Start: " + num1);
        System.out.println("your numbers:");

        while (num1 < num2) {
            num1++;
            System.out.println(num1);
        }
    }
}
