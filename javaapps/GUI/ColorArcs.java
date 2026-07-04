// Nicholas Larkin Buchanan
// 2011

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class ColorArcs extends JPanel {

    int width, height;
    int N = 25; // the number of colors created
    Color[] spectrum; // an array of elements, each of type Color
    Color[] spectrum2; // another array

    public ColorArcs() {
        // Initialize logic from the old init() method
        width = 500; // Default size
        height = 500;
        setBackground(Color.black);

        // Allocate the arrays; make them "N" elements long
        spectrum = new Color[N];
        spectrum2 = new Color[N];

        System.out.println("Generating color spectrums...");

        // Generate the colors and store them in the arrays.
        for (int i = 1; i <= N; ++i) {
            // As i goes from 1 to N, this color goes from almost black to white.
            spectrum[i - 1] = new Color(i / (float) N, i / (float) N, i / (float) N);

            // As i goes from 1 to N, this color goes from almost pure green to pure red.
            spectrum2[i - 1] = new Color(i / (float) N, (N - i) / (float) N, 0);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Ensure background is cleared

        width = getWidth();
        height = getHeight();

        System.out.println("Painting arcs at time: " + new Date());

        int step = 90 / N;
        for (int i = 0; i < N; ++i) {
            g.setColor(spectrum[i]);
            g.fillArc(0, 0, 2 * width, 2 * height, 90 + i * step, step + 1);

            g.setColor(spectrum2[i]);
            g.fillArc(width / 3, height / 3, 4 * width / 3, 4 * height / 3, 90 + i * step, step + 1);
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting ColorArcs program...");
        JFrame frame = new JFrame("Drawing With Color");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        ColorArcs panel = new ColorArcs();
        frame.add(panel);

        frame.setVisible(true);
        System.out.println("Window visible.");
    }
}

/*
 * HOW TO USE PROGRAM:
 * 1. Run the application.
 * 2. It will display a window with geometric arc patterns.
 * 3. The arcs are drawn using two generated color spectrums:
 * - Grayscale (Black to White)
 * - Gradient (Green to Red)
 * 4. Resize the window to see the arcs scale dynamically.
 */
