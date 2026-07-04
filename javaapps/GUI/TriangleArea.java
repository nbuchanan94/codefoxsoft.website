// Nicholas Buchanan
// September 2010

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class TriangleArea extends JFrame {

    private JTextField x1, y1, x2, y2, x3, y3;
    private JLabel resLabel;
    private TrianglePanel canvas;

    public TriangleArea() {
        initGUI();
    }

    private void initGUI() {
        setTitle("TriangleArea - Nick Buchanan");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Blueprint Theme
        getContentPane().setBackground(new Color(0, 50, 150)); // Blueprint Blue

        JPanel controls = new JPanel(new GridLayout(4, 2));
        controls.setOpaque(false);
        controls.add(new JLabel("P1 (x, y):"));
        JPanel p1 = new JPanel(new GridLayout(1, 2));
        x1 = new JTextField("0");
        y1 = new JTextField("0");
        p1.add(x1);
        p1.add(y1);
        controls.add(p1);

        controls.add(new JLabel("P2 (x, y):"));
        JPanel p2 = new JPanel(new GridLayout(1, 2));
        x2 = new JTextField("10");
        y2 = new JTextField("0");
        p2.add(x2);
        p2.add(y2);
        controls.add(p2);

        controls.add(new JLabel("P3 (x, y):"));
        JPanel p3 = new JPanel(new GridLayout(1, 2));
        x3 = new JTextField("0");
        y3 = new JTextField("10");
        p3.add(x3);
        p3.add(y3);
        controls.add(p3);

        JButton btn = new JButton("CALCULATE & DRAW");
        btn.addActionListener(e -> calc());
        controls.add(btn);

        resLabel = new JLabel("Area: ?", SwingConstants.CENTER);
        resLabel.setForeground(Color.WHITE);
        controls.add(resLabel);

        add(controls, BorderLayout.NORTH);

        canvas = new TrianglePanel();
        add(canvas, BorderLayout.CENTER);

        setVisible(true);
    }

    private void calc() {
        try {
            int px1 = Integer.parseInt(x1.getText());
            int py1 = Integer.parseInt(y1.getText());
            int px2 = Integer.parseInt(x2.getText());
            int py2 = Integer.parseInt(y2.getText());
            int px3 = Integer.parseInt(x3.getText());
            int py3 = Integer.parseInt(y3.getText());

            // Determine Area using Shoelace Formula
            int term1 = (px1 * py2) + (px2 * py3) + (px3 * py1);
            int term2 = (py1 * px2) + (py2 * px3) + (py3 * px1);
            int area = Math.abs(term1 - term2) / 2;

            resLabel.setText("Area: " + (area * 4 / 5)); // Original Logic

            canvas.setPoints(px1, py1, px2, py2, px3, py3);
            canvas.repaint();

        } catch (Exception e) {
            resLabel.setText("Invalid Input");
        }
    }

    class TrianglePanel extends JPanel {
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];

        public void setPoints(int x1, int y1, int x2, int y2, int x3, int y3) {
            // Scale up for visibility?
            // Let's multiply by 10 for drawing
            int scale = 10;
            xPoints[0] = x1 * scale + 50;
            yPoints[0] = y1 * scale + 50;
            xPoints[1] = x2 * scale + 50;
            yPoints[1] = y2 * scale + 50;
            xPoints[2] = x3 * scale + 50;
            yPoints[2] = y3 * scale + 50;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.WHITE);
            // Grid
            for (int i = 0; i < getWidth(); i += 20)
                g.drawLine(i, 0, i, getHeight());
            for (int i = 0; i < getHeight(); i += 20)
                g.drawLine(0, i, getWidth(), i);

            g.setColor(Color.YELLOW);
            g.drawPolygon(xPoints, yPoints, 3);
            g.fillPolygon(xPoints, yPoints, 3);
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new TriangleArea());
        }
    }

    public static void runConsole(String[] args) {
        System.out.println("Enter vertices (x1 y1 x2 y2 x3 y3): ");

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String line = input.readLine();
            if (line == null)
                return;

            StringTokenizer st = new StringTokenizer(line);

            // Parsing coordinates (Respective Points P1, P2, P3)
            int p1x = Integer.parseInt(st.nextToken());
            int p1y = Integer.parseInt(st.nextToken());

            int p2x = Integer.parseInt(st.nextToken());
            int p2y = Integer.parseInt(st.nextToken());

            int p3x = Integer.parseInt(st.nextToken());
            int p3y = Integer.parseInt(st.nextToken());

            // Determine Area using Shoelace Formula
            // (x1*y2 + x2*y3 + x3*y1) - (y1*x2 + y2*x3 + y3*x1)
            int term1 = (p1x * p2y) + (p2x * p3y) + (p3x * p1y);
            int term2 = (p1y * p2x) + (p2y * p3x) + (p3y * p1x);

            int area = Math.abs(term1 - term2) / 2;

            // Output Calculation
            // NOTE: The original code multiplied the result by 4/5 (0.8).
            // This suggests it was for a specific problem requiring this adjustment.
            System.out.println("Result: " + (area * 4 / 5));

        } catch (Exception e) {
            System.err.println("Error processing input: " + e.getMessage());
        }
    }
}

/*
 * This program calculates the area of a triangle given three sets of
 * Cartesian coordinates using the Shoelace Formula.
 */
