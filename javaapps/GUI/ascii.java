
/*
 * Nicholas Larkin Buchanan
 * 2011-02-15
 * pd 8
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ascii extends JFrame {
    private JTextArea outputArea;
    private JButton generateButton;

    public ascii() {
        super("ascii - Nick Buchanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLayout(new BorderLayout(10, 10));

        // Use a Matrix theme (Green on Black)
        getContentPane().setBackground(Color.BLACK);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.BLACK);
        generateButton = new JButton("Generate ASCII Table");
        generateButton.setBackground(Color.GREEN);
        generateButton.setForeground(Color.BLACK);
        generateButton.setFocusPainted(false);
        generateButton.setFont(new Font("Monospaced", Font.BOLD, 14));
        topPanel.add(generateButton);

        add(topPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        outputArea.setBackground(Color.BLACK);
        outputArea.setForeground(Color.GREEN);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        add(scrollPane, BorderLayout.CENTER);

        generateButton.addActionListener(e -> generateTable());

        // Auto-run for convenience
        generateTable();

        setVisible(true);
    }

    private void generateTable() {
        outputArea.setText("DECIMAL   ASCII\n");
        outputArea.append("----------------\n");

        // Original Logic: 1 to 128
        for (int i = 32; i < 128; i++) {
            // Handle non-printable characters visually if possible, but keeping it simple
            // as per original
            char c = (char) i;
            String displayChar = String.valueOf(c);
            if (i < 32)
                displayChar = "[Control]"; // visualize control chars
            if (i == 127)
                displayChar = "[DEL]";

            outputArea.append(String.format("%-10d %s\n", i, displayChar));
        }
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(ascii::new);
        }
    }

    public static void runConsole(String[] args) {
        // Original Console Logic
        int i;

        for (i = 1; i < 128; i++) {
            System.out.println(i + " " + (char) i);
        }
    }
}
