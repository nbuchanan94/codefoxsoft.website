
//Nicholas Larkin Buchanan
//2012-05-15
//pd 8
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class SmallestMultiple extends JFrame {

    private JTextArea logArea;

    public SmallestMultiple() {
        initGUI();
    }

    private void initGUI() {
        setTitle("SmallestMultiple - Nick Buchanan");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Time/Clock Theme
        getContentPane().setBackground(Color.WHITE);

        JButton runBtn = new JButton("Run Search (1-20)");
        runBtn.setFont(new Font("Times New Roman", Font.BOLD, 22));
        runBtn.addActionListener(e -> runSearch());
        add(runBtn, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void runSearch() {
        new Thread(() -> {
            logArea.setText("Starting search...\n");
            int name = 0;
            boolean searching = true;
            long startTime = System.currentTimeMillis();

            while (searching) {
                name++;
                if (name % 50000000 == 0) {
                    logArea.append("Checked " + name + " candidates...\n");
                    logArea.setCaretPosition(logArea.getDocument().getLength());
                }

                for (int i = 2; i <= 20; i++) {
                    if (name % i == 0 && i == 20) {
                        logArea.append("Found it: " + name + "\n");
                        logArea.append("Time taken: " + (System.currentTimeMillis() - startTime) + "ms\n");
                        searching = false;
                    } else if (name % i != 0) {
                        break;
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new SmallestMultiple());
        }
    }

    public static void runConsole(String[] args) {
        System.out.println("System: Application Start");
        System.out.println("DEBUG: Searching for smallest number divisible by 1-20...");

        int name = 0;
        boolean searching = true;

        long startTime = System.currentTimeMillis();

        while (searching) {
            name++;

            // Progress Log every 50 million to keep user informed
            if (name % 50000000 == 0) {
                System.out.println("DEBUG: Checked " + name + " candidates...");
            }

            for (int i = 2; i <= 20; i++) {
                if (name % i == 0 && i == 20) {
                    // Found it!
                    System.out.println("Found it: " + name);
                    System.out.println("Time taken: " + (System.currentTimeMillis() - startTime) + "ms");
                    searching = false;
                } else if (name % i != 0) {
                    // Not divisible by i, break and try next number
                    break;
                }
            }
        }
        System.out.println("System: Application End");
    }
}
// Usage: Run. Calculates the smallest positive number that is evenly divisible
// by all of the numbers from 1 to 20.
