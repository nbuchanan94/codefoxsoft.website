
// Nicholas Larkin Buchanan
// June 12, 2011
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class LittleMathMethods extends JFrame {

    private JTextField n1Field, n2Field;
    private JTextArea resultArea;

    public LittleMathMethods() {
        initGUI();
    }

    private void initGUI() {
        setTitle("LittleMathMethods - Nick Buchanan");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Primary Colors
        getContentPane().setBackground(new Color(255, 255, 224)); // Light Yellow

        JPanel inP = new JPanel(new GridLayout(3, 2, 5, 5));
        inP.setBackground(new Color(173, 216, 230)); // Light Blue
        inP.add(new JLabel("Number 1:"));
        n1Field = new JTextField();
        inP.add(n1Field);
        inP.add(new JLabel("Number 2:"));
        n2Field = new JTextField();
        inP.add(n2Field);

        JButton runBtn = new JButton("RUN ALL");
        runBtn.setBackground(new Color(255, 99, 71)); // Red
        runBtn.setForeground(Color.WHITE);
        runBtn.addActionListener(e -> runAll());
        inP.add(runBtn);

        add(inP, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Monospaced", Font.BOLD, 14));
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void runAll() {
        try {
            double n1 = Double.parseDouble(n1Field.getText());
            double n2 = Double.parseDouble(n2Field.getText());

            StringBuilder sb = new StringBuilder();
            sb.append("--- Results ---\n");

            // Re-implement logic for GUI to capture string
            sb.append(n1 + " + " + n2 + " = " + (n1 + n2) + "\n");
            sb.append(n1 + " - " + n2 + " = " + (n1 - n2) + "\n");
            sb.append(n1 + " * " + n2 + " = " + (n1 * n2) + "\n");
            if (n2 != 0)
                sb.append(n1 + " / " + n2 + " = " + (n1 / n2) + "\n");
            else
                sb.append(n1 + " / " + n2 + " = Undefined\n");

            resultArea.setText(sb.toString());
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new LittleMathMethods());
        }
    }

    public static void runConsole(String[] args) {
        Scanner kbInput = new Scanner(System.in);
        System.out.println("::: Little Math Methods :::");
        System.out.println("Pick 2 numbers");

        System.out.print("Number 1: ");
        double n1 = kbInput.nextDouble();

        System.out.print("Number 2: ");
        double n2 = kbInput.nextDouble();

        System.out.println("\n--- Results ---");
        add(n1, n2);
        sub(n1, n2);
        multiply(n1, n2);
        divide(n1, n2);
    }

    public static void add(double a, double b) {
        System.out.println(a + " + " + b + " = " + (a + b));
    }

    public static void sub(double a, double b) {
        System.out.println(a + " - " + b + " = " + (a - b));
    }

    public static void multiply(double a, double b) {
        System.out.println(a + " * " + b + " = " + (a * b));
    }

    public static void divide(double a, double b) {
        if (b != 0) {
            System.out.println(a + " / " + b + " = " + (a / b));
        } else {
            System.out.println(a + " / " + b + " = Undefined (Cannot divide by zero)");
        }
    }
}
// Run the program and enter two numbers to see the results of addition,
// subtraction, multiplication, and division.
