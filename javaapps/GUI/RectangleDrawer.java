
//Nicholas Larkin Buchanan
//2010-02-17
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class RectangleDrawer extends JFrame {

    private JTextField wField, hField;
    private DrawPanel canvas;

    public RectangleDrawer() {
        initGUI();
    }

    private void initGUI() {
        setTitle("RectangleDrawer - Nick Buchanan");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Drafting Theme
        getContentPane().setBackground(Color.WHITE);

        JPanel p = new JPanel();
        p.add(new JLabel("Width:"));
        wField = new JTextField("10", 3);
        p.add(wField);
        p.add(new JLabel("Height:"));
        hField = new JTextField("5", 3);
        p.add(hField);
        JButton btn = new JButton("DRAW");
        btn.setBackground(Color.BLUE);
        btn.setForeground(Color.WHITE);
        btn.addActionListener(e -> draw());
        p.add(btn);
        add(p, BorderLayout.NORTH);

        canvas = new DrawPanel();
        add(new JScrollPane(canvas), BorderLayout.CENTER); // ScrPane in case huge

        setVisible(true);
    }

    private void draw() {
        try {
            int w = Integer.parseInt(wField.getText());
            int h = Integer.parseInt(hField.getText());
            canvas.setDims(w, h);
            canvas.repaint();
        } catch (Exception e) {
        }
    }

    class DrawPanel extends JPanel {
        int w = 0;
        int h = 0;

        public void setDims(int w, int h) {
            this.w = w;
            this.h = h;
            setPreferredSize(new Dimension(w * 20 + 50, h * 20 + 50));
            revalidate();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw grid paper effect
            g.setColor(new Color(230, 240, 255));
            for (int i = 0; i < getWidth(); i += 20)
                g.drawLine(i, 0, i, getHeight());
            for (int i = 0; i < getHeight(); i += 20)
                g.drawLine(0, i, getWidth(), i);

            // Draw Boxes
            g.setColor(Color.BLUE);
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    g.fillRect(j * 20 + 10, i * 20 + 10, 18, 18);
                }
            }
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new RectangleDrawer());
        }
    }

    public static void runConsole(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("height of rectangle (originally called width): ");
        int height = Integer.parseInt(scanner.nextLine());

        System.out.println("width of rectangle (originally called length): ");
        int width = Integer.parseInt(scanner.nextLine());

        System.out.println();

        int i = 0;

        // Fixed Nested Loop Logic (Original C# code failed to reset inner loop var)
        while (i < height) {
            i++;
            int j = 0;
            while (j < width) {
                j++;
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
// Usage: Draws a rectangle of * characters.
