
// Nicholas Larkin Buchanan
// February 28, 2011
import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PatternGenerator extends JFrame {

    private JTextArea outputArea;

    public PatternGenerator() {
        initGUI();
    }

    private void initGUI() {
        setTitle("PatternGenerator - Nick Buchanan");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Abstract Art Theme (Purple/Lime)
        getContentPane().setBackground(new Color(128, 0, 128)); // Purple

        JButton genBtn = new JButton("GENERATE ART");
        genBtn.setBackground(new Color(50, 205, 50)); // Lime Green
        genBtn.setForeground(Color.BLACK);
        genBtn.setFont(new Font("Monospaced", Font.BOLD, 20));
        genBtn.addActionListener(e -> generate());
        add(genBtn, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setFont(new Font("Monospaced", Font.BOLD, 16));
        outputArea.setBackground(Color.BLACK);
        outputArea.setForeground(new Color(50, 205, 50)); // Lime
        outputArea.setEditable(false);
        // User requested NO scrolling
        add(outputArea, BorderLayout.CENTER);

        // Add Resize Listener
        outputArea.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                generate();
            }
        });

        setVisible(true);
    }

    private void generate() {
        if (outputArea.getWidth() == 0 || outputArea.getHeight() == 0)
            return;

        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        String[] chars = { "_", "|", "-" }; // Requested "maze" chars

        // Dynamic Calculation based on Font Metrics
        FontMetrics fm = outputArea.getFontMetrics(outputArea.getFont());
        int charW = fm.charWidth('_'); // Monospaced
        int charH = fm.getHeight();

        if (charW <= 0 || charH <= 0)
            return; // Prevention

        int cols = outputArea.getWidth() / charW;
        int rows = outputArea.getHeight() / charH;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                sb.append(chars[rand.nextInt(chars.length)]);
            }
            sb.append("\n");
        }
        outputArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new PatternGenerator());
        }
    }

    public static void runConsole(String[] args) {
        System.out.println("Generating Random Maze Pattern...");
        Random rand = new Random();
        String[] chars = { "_", "|", "-" };

        int rows = 20;
        int cols = 40; // Wider for console usually

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                System.out.print(chars[rand.nextInt(chars.length)]);
            }
            System.out.println();
        }
    }
}
// Random Patern Generator
