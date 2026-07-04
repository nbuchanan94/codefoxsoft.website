
//Nicholas Larkin Buchanan
//2012-05-15
//pd 8
import java.util.*;
import java.lang.Math.*;
import javax.swing.*;
import java.awt.*;

public class SumOfSquares extends JFrame {

    private JTextArea logArea;

    public SumOfSquares() {
        initGUI();
    }

    private void initGUI() {
        setTitle("SumOfSquares - Nick Buchanan");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(178, 34, 34)); // Firebrick

        JButton btn = new JButton("Run Sum of Squares (1-10)");
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.addActionListener(e -> run());
        add(btn, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void run() {
        logArea.setText("Starting calculation...\n");
        int output = 0;
        int j = 0;

        // Fixed: Removed extra semicolon after for loop
        for (int i = 1; i <= 10; i++) {
            j = i * i;
            output = output + j;
            logArea.append("Added " + i + "^2 (" + j + ") = " + output + "\n");
        }
        logArea.append("Result: " + output + "\n");
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new SumOfSquares());
        }
    }

    public static void runConsole(String[] args) {
        System.out.println("System: Application Start");
        int output = 0;
        int j = 0;

        System.out.println("DEBUG: Calculating sum of squares for 1 to 10...");

        // Fixed: Removed extra semicolon after for loop
        for (int i = 1; i <= 10; i++) {
            j = i * i;
            output = output + j;
            System.out.println("DEBUG: Added " + i + "^2 (" + j + ") = " + output);
        }
        System.out.println("Result: " + output);
        System.out.println("System: Application End");
    }
}
// Usage: Run. Calculates the sum of squares for numbers 1 to 10.
