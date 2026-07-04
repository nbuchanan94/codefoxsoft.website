
//Nicholas Larkin Buchanan
//2012-10-10
//pd 8
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class DigitFrequencyCounter extends JFrame {

    private JTextField inputField;
    private HistogramPanel histo;
    private int[] counts = new int[10];

    public DigitFrequencyCounter() {
        initGUI();
    }

    private void initGUI() {
        setTitle("DigitFrequencyCounter - Nick Buchanan");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Matrix Theme
        getContentPane().setBackground(Color.BLACK);

        JPanel top = new JPanel(new FlowLayout());
        top.setOpaque(false);
        JLabel l = new JLabel("Enter Digit String:");
        l.setForeground(Color.GREEN);
        l.setFont(new Font("Monospaced", Font.BOLD, 16));
        top.add(l);
        inputField = new JTextField(20);
        inputField.setBackground(Color.BLACK);
        inputField.setForeground(Color.GREEN);
        inputField.setCaretColor(Color.GREEN);
        top.add(inputField);
        JButton btn = new JButton("ANALYZE");
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.GREEN);
        btn.addActionListener(e -> analyze());
        top.add(btn);
        add(top, BorderLayout.NORTH);

        histo = new HistogramPanel();
        add(histo, BorderLayout.CENTER);

        setVisible(true);
    }

    private void analyze() {
        String input = inputField.getText();
        Arrays.fill(counts, 0);

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (Character.isDigit(ch)) {
                int digit = ch - '0';
                counts[digit]++;
            }
        }
        histo.repaint();
    }

    class HistogramPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, getWidth(), getHeight());

            // Draw Bars
            int barW = getWidth() / 12;
            int maxH = getHeight() - 50;
            int maxVal = 1;
            for (int c : counts)
                maxVal = Math.max(maxVal, c);

            for (int i = 0; i < 10; i++) {
                int h = (int) ((double) counts[i] / maxVal * (maxH - 20));
                int x = (i + 1) * barW;
                int y = getHeight() - 30 - h;

                g2.setColor(Color.GREEN);
                g2.fillRect(x, y, barW - 5, h);
                g2.setColor(Color.WHITE);
                g2.drawString("" + i, x + barW / 2 - 5, getHeight() - 10);
                if (counts[i] > 0)
                    g2.drawString("" + counts[i], x + barW / 2 - 5, y - 5);
            }
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new DigitFrequencyCounter());
        }
    }

    public static void runConsole(String[] a) {
        System.out.println("System: Application Start");
        Scanner kb = new Scanner(System.in);

        System.out.println("Enter a string of digits (e.g., 11295): ");
        String input = kb.nextLine();
        System.out.println("DEBUG: Input received: " + input);

        // Array to hold counts for digits 0-9
        int[] counts = new int[10];

        // Process each character
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (Character.isDigit(ch)) {
                // distinct char '0' makes '0'->0, '1'->1 etc.
                int digit = ch - '0';
                counts[digit]++;
            } else {
                System.out.println("DEBUG: Ignored non-digit: " + ch);
            }
        }

        System.out.println("--- Frequencies ---");
        for (int i = 0; i < 10; i++) {
            if (counts[i] > 0) {
                System.out.println("Digit " + i + ": " + counts[i] + " times");
            }
        }
        System.out.println("System: Application End");
    }
}
// Usage: Run. Enter digits. Counts how many times each digit appears.
