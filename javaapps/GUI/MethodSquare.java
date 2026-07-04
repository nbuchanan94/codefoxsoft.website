
/*
 * Nicholas Larkin Buchanan
 * 2011-03-24
 *
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

public class MethodSquare extends JFrame {
    private JTextField inputField;
    private JLabel resultLabel;

    public MethodSquare() {
        super("MethodSquare - Nick Buchanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Theme: Math Function (Red)
        getContentPane().setBackground(new Color(255, 240, 240));

        JLabel prompt = new JLabel("Enter an integer:");
        prompt.setFont(new Font("Verdana", Font.BOLD, 12));
        add(prompt);

        inputField = new JTextField(10);
        add(inputField);

        JButton calcButton = new JButton("Square It!");
        calcButton.setBackground(new Color(220, 50, 50));
        calcButton.setForeground(Color.WHITE);
        add(calcButton);

        resultLabel = new JLabel("Result: -");
        resultLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        resultLabel.setForeground(new Color(150, 0, 0));
        add(resultLabel);

        calcButton.addActionListener(e -> calculate());
        inputField.addActionListener(e -> calculate());

        setVisible(true);
    }

    private void calculate() {
        try {
            int num = Integer.parseInt(inputField.getText());
            int sq = integer(num); // using original method name
            resultLabel.setText("Result: " + sq);
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid Integer");
        }
    }

    // Original static method
    public static int integer(int num) {
        return num * num;
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(MethodSquare::new);
        }
    }

    public static void runConsole(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter an integer: ");
        int num = kb.nextInt();
        System.out.println("The square is: " + integer(num));
    }
}
