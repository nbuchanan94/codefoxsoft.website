
//Nicholas Larkin Buchanan
//2013-02-12
//pd 8
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FibonacciRecursive extends JFrame {

    private JTextField termField;
    private JTextArea outArea;

    public FibonacciRecursive() {
        initGUI();
    }

    private void initGUI() {
        setTitle("FibonacciRecursive - Nick Buchanan");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Golden Theme
        getContentPane().setBackground(new Color(218, 165, 32));

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.add(new JLabel("Number of Terms:"));
        termField = new JTextField(5);
        top.add(termField);
        JButton runBtn = new JButton("Generate Sequence");
        runBtn.addActionListener(e -> runSeq());
        top.add(runBtn);
        add(top, BorderLayout.NORTH);

        outArea = new JTextArea();
        outArea.setEditable(false);
        outArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(outArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void runSeq() {
        try {
            int n = Integer.parseInt(termField.getText());
            StringBuilder sb = new StringBuilder();
            sb.append("Calculing " + n + " terms recursively...\n\n");

            // Running on separate thread to not freeze GUI if N is large?
            // Recursive fib is slow. Let's keep it simple as per request but maybe warn
            // user.

            for (int i = 0; i <= n; i++) {
                sb.append("Term " + i + ": " + integer(i) + "\n");
            }
            outArea.setText(sb.toString());
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new FibonacciRecursive());
        }
    }

    public static void runConsole(String[] args) {
        System.out.println("System: Application Start");
        Scanner kbreader = new Scanner(System.in);
        System.out.print("Enter number of terms to calculate: ");
        int inNumber = kbreader.nextInt();
        System.out.println("DEBUG: Input received: " + inNumber);

        for (int i = 0; i <= inNumber; i++) {
            System.out.print("Term " + i + ": ");
            System.out.println(integer(i));

        }
        System.out.println("System: Application End");
    }

    public static int integer(int i) {
        if (i == 0) {
            return i;
        } else if (i == 1) {
            return i;
        } else {
            return integer(i - 1) + integer(i - 2);

        }
    }
}
// Usage: Run this file. Enter N. Prints the first N numbers of the Fibonacci
// sequence using recursion.
