// Nicholas Buchanan
// September 15, 2010
// 3rd period

import java.util.*;
import java.text.DecimalFormat;
import javax.swing.*;
import java.awt.*;

public class CircleArea extends JFrame {

    private JTextField radField;
    private JLabel resultLabel;
    private CirclePanel shapePanel;

    public CircleArea() {
        initGUI();
    }

    private void initGUI() {
        setTitle("CircleArea - Nick Buchanan");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Purple Theme
        getContentPane().setBackground(new Color(147, 112, 219));

        JPanel topP = new JPanel(new FlowLayout());
        topP.setOpaque(false);
        topP.add(new JLabel("Radius:"));
        radField = new JTextField("5", 5);
        topP.add(radField);
        JButton calcBtn = new JButton("Calculate Area");
        calcBtn.addActionListener(e -> calculate());
        topP.add(calcBtn);
        add(topP, BorderLayout.NORTH);

        shapePanel = new CirclePanel();
        add(shapePanel, BorderLayout.CENTER);

        resultLabel = new JLabel("Area: ?", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultLabel.setForeground(Color.WHITE);
        add(resultLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void calculate() {
        try {
            double r = Double.parseDouble(radField.getText());
            double a = area(r);
            DecimalFormat df = new DecimalFormat("###.##");
            resultLabel.setText("Area: " + df.format(a));

            shapePanel.setRadius(r);
            shapePanel.repaint();
        } catch (Exception e) {
        }
    }

    class CirclePanel extends JPanel {
        double r = 0;

        public void setRadius(double r) {
            this.r = r;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (r <= 0)
                return;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int maxD = Math.min(getWidth(), getHeight()) - 60;
            // No strict scale relative to screen, just fit it comfortably to show
            // "Circle-ness"
            // We can't really draw a 10000px circle or a 0.1px circle accurately in pixel
            // mapping 1:1 for users
            // So we'll draw a standardized representation but label it

            int drawD = 200; // Fixed visual size for concept, or we could scale relative to max?
            // User requested "Example... with values placed around".
            // Let's use a nice dynamic size that breathes.

            int x = (getWidth() - drawD) / 2;
            int y = (getHeight() - drawD) / 2;
            int cx = x + drawD / 2;
            int cy = y + drawD / 2;

            g2.setColor(Color.RED);
            g2.drawOval(x, y, drawD, drawD);
            g2.setColor(new Color(255, 0, 0, 50));
            g2.fillOval(x, y, drawD, drawD);

            // Draw Radius Line
            g2.setColor(Color.WHITE);
            g2.drawLine(cx, cy, cx + drawD / 2, cy); // Line to right edge
            g2.drawString("r = " + r, cx + 20, cy - 5);
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new CircleArea());
        }
    }

    public static double area(double radius) {
        // Original logic preserved
        radius = Math.PI * Math.pow(radius, 2);
        return radius;
    }

    public static void runConsole(String[] args) {
        Scanner kbReader = new Scanner(System.in);
        System.out.print("Enter the radius of the circle: ");
        // Changed nextInt to nextDouble to prevent crash on decimal inputs,
        // while preserving the fundamental logic structure.
        double rad = kbReader.nextDouble();
        DecimalFormat df = new DecimalFormat("###.##");
        System.out.println(df.format(area(rad)));
        kbReader.close();
    }
}

/*
 * 1. The user is prompted to enter a radius.
 * 2. The program reads the input.
 * 3. It calls a method 'area' which calculates Area = PI * r^2.
 * 4. It prints the result formatted to two decimal places.
 */
