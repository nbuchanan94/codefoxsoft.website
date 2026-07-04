
//Nicholas Larkin Buchanan
// 3/4/2011
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class SecurityTextScanner extends JFrame {

    private JTextPane textPane;
    private JLabel statusLabel;

    public SecurityTextScanner() {
        initGUI();
    }

    private void initGUI() {
        setTitle("SecurityTextScanner - Nick Buchanan");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Surveillance Theme
        getContentPane().setBackground(Color.BLACK);

        JButton scanBtn = new JButton("SCAN 'dictionary.txt'");
        scanBtn.setBackground(Color.RED);
        scanBtn.setForeground(Color.WHITE);
        scanBtn.addActionListener(e -> scanFile());
        add(scanBtn, BorderLayout.NORTH);

        textPane = new JTextPane();
        textPane.setBackground(Color.DARK_GRAY);
        textPane.setForeground(Color.LIGHT_GRAY); // Normal text
        textPane.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(textPane), BorderLayout.CENTER);

        statusLabel = new JLabel("Status: Idle");
        statusLabel.setForeground(Color.RED);
        add(statusLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void scanFile() {
        // keywords
        String[] keywords = { "governor", "bomb", "drill", "plan", "gun", "annapolis" };

        new Thread(() -> {
            try {
                File f = new File("dictionary.txt");
                Scanner sc = new Scanner(f);

                StyledDocument doc = textPane.getStyledDocument();
                // Clear
                SwingUtilities.invokeLater(() -> textPane.setText(""));

                Style normal = doc.addStyle("normal", null);
                StyleConstants.setForeground(normal, Color.LIGHT_GRAY);

                Style flagged = doc.addStyle("flagged", null);
                StyleConstants.setForeground(flagged, Color.RED);
                StyleConstants.setBold(flagged, true);

                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String lower = line.toLowerCase();
                    boolean flag = false;
                    for (String k : keywords)
                        if (lower.contains(k))
                            flag = true;

                    boolean finalFlag = flag;
                    String finalLine = line;
                    SwingUtilities.invokeLater(() -> {
                        try {
                            doc.insertString(doc.getLength(), finalLine + (finalFlag ? " [FLAGGED]" : "") + "\n",
                                    finalFlag ? flagged : normal);
                        } catch (Exception e) {
                        }
                    });
                }
                sc.close();
                SwingUtilities.invokeLater(() -> statusLabel.setText("Scan Complete."));

            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> statusLabel.setText("Error: File not found"));
            }
        }).start();
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new SecurityTextScanner());
        }
    }

    public static void runConsole(String[] args) {
        // defined keywords to search for
        String[] keywords = { "governor", "bomb", "drill", "plan", "gun", "annapolis" };

        System.out.println("--- Starting Security Scan ---");

        try {
            // Create Scanner to read from file
            // Note: Ensure 'cbp1data.txt' exists in the same directory
            Scanner infile = new Scanner(new File("dictionary.txt"));

            int lineNum = 0;

            // Loop through every line in the file
            while (infile.hasNextLine()) {
                String line = infile.nextLine();
                lineNum++;

                // Convert to lowercase for case-insensitive check
                String lowerLine = line.toLowerCase();

                boolean flagged = false;

                // Check against all keywords
                for (String keyword : keywords) {
                    if (lowerLine.contains(keyword)) {
                        flagged = true;
                        break; // No need to check other keywords if one is found
                    }
                }

                // If flagged, mark and print ONLY that line
                if (flagged) {
                    System.out.println("Line " + lineNum + ": " + line + " *flag");
                }
            }

            infile.close();
            System.out.println("--- Scan Complete ---");

        } catch (FileNotFoundException e) {
            System.out.println("Error: 'cbp1data.txt' file not found.");
            System.out.println("Please create this file with text to scan.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}

/*
 * 1. Keywords: A predefined list of words (e.g., "bomb", "gun") is established.
 * 2. File Input: The program opens 'cbp1data.txt'.
 */
