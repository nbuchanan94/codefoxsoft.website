// Nicholas Buchanan
// March 20 2011

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class RandomColorSquares extends JFrame {

	public RandomColorSquares() {
		super("Flashing Random Color Squares");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add the drawing panel
		add(new SquarePanel());

		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new RandomColorSquares());
	}

	// Inner panel for drawing
	private class SquarePanel extends JPanel {
		private final Random random = new Random();
		private final Timer timer;

		public SquarePanel() {
			setBackground(Color.BLACK);

			// Timer to trigger repaint every 50ms (20 FPS)
			timer = new Timer(50, e -> repaint());
			timer.start();
		}

		@Override
		protected void paintComponent(Graphics g) {
			// super.paintComponent(g); // If we want to clear the screen every frame,
			// uncomment this.
			// If we want squares to accumulate (filling the screen), leave it commented or
			// just draw over.
			// "Flash" usually implies clearing, but let's clear for "flashing" effect.
			super.paintComponent(g);

			// Draw a bunch of random squares each frame
			for (int i = 0; i < 50; i++) {
				int r = random.nextInt(256);
				int gr = random.nextInt(256);
				int b = random.nextInt(256);
				g.setColor(new Color(r, gr, b));

				int w = getWidth();
				int h = getHeight();

				int size = random.nextInt(90) + 10; // Size 10 to 100
				int x = random.nextInt(w - size);
				int y = random.nextInt(h - size);

				g.fillRect(x, y, size, size);
			}
		}
	}
}

/*
 * This program creates a visual effect by rapidly drawing random squares
 * of various colors and sizes on the screen.
 * It uses a Swing Timer to refresh the display, creating a "flashing"
 * animation.
 */