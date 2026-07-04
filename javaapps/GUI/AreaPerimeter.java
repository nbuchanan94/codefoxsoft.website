
//Nicholas Larkin Buchanan
//2010-02-17
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class AreaPerimeter extends JFrame {

    private JTextField lenField, widField;
    private JLabel areaLabel, perimLabel;
    private RectanglePanel shapePanel;

    public AreaPerimeter() {
        initGUI();
    }

    private void initGUI() {
        setTitle("AreaPerimeter - Nick Buchanan");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Orange Geometry Theme
        getContentPane().setBackground(new Color(255, 165, 0));

        JPanel topP = new JPanel(new FlowLayout());
        topP.setOpaque(false);
        topP.add(new JLabel("Length:"));
        lenField = new JTextField("10", 5);
        topP.add(lenField);
        topP.add(new JLabel("Width:"));
        widField = new JTextField("5", 5);
        topP.add(widField);

        JButton calcBtn = new JButton("Calculate");
        calcBtn.addActionListener(e -> calculate());
        topP.add(calcBtn);
        add(topP, BorderLayout.NORTH);

        shapePanel = new RectanglePanel();
        add(shapePanel, BorderLayout.CENTER);

        JPanel botP = new JPanel(new GridLayout(2, 1));
        botP.setOpaque(false);
        areaLabel = new JLabel("Area: ?", SwingConstants.CENTER);
        areaLabel.setFont(new Font("Arial", Font.BOLD, 16));
        perimLabel = new JLabel("Perimeter: ?", SwingConstants.CENTER);
        perimLabel.setFont(new Font("Arial", Font.BOLD, 16));
        botP.add(areaLabel);
        botP.add(perimLabel);
        add(botP, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void calculate() {
        try {
            int l = Integer.parseInt(lenField.getText());
            int w = Integer.parseInt(widField.getText());
            int area = l * w;
            int perim = (l * 2) + (w * 2);

            areaLabel.setText("Area of rectangle: " + area);
            perimLabel.setText("Perimeter of Rectangle: " + perim);

            shapePanel.setDims(l, w);
            shapePanel.repaint();
        } catch (Exception e) {
        }
    }

    // Custom Panel for Visuals
    class RectanglePanel extends JPanel {
        int l = 0, w = 0;

        public void setDims(int l, int w) {
            this.l = l;
            this.w = w;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (l <= 0 || w <= 0)
                return;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int maxW = getWidth() - 100;
            int maxH = getHeight() - 100;

            // Scale logic to fit window
            double scale = Math.min((double) maxW / l, (double) maxH / w);
            if (scale > 20)
                scale = 20; // Cap zoom
            int drawL = (int) (l * scale);
            int drawW = (int) (w * scale);

            int x = (getWidth() - drawL) / 2;
            int y = (getHeight() - drawW) / 2;

            g2.setColor(Color.BLUE);
            g2.drawRect(x, y, drawL, drawW);
            g2.setColor(new Color(0, 0, 255, 50));
            g2.fillRect(x, y, drawL, drawW);

            g2.setColor(Color.BLACK);
            g2.drawString("L=" + l, x + drawL / 2 - 10, y - 5);
            g2.drawString("W=" + w, x + drawL + 5, y + drawW / 2);
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new AreaPerimeter());
        }
    }

    public static void runConsole(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter length: ");
        int length = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter width: ");
        int width = Integer.parseInt(scanner.nextLine());

        int area = length * width;
        int perimeter = (length * 2) + (width * 2);

        System.out.println("\nArea of rectangle: " + area);
        System.out.println("Perimeter of Rectangle: " + perimeter);
    }
}
// Usage: Calculates Area and Perimeter of a rectangle given length and width.
