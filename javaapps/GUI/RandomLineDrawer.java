// Nicholas Buchanan
// 2011

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class RandomLineDrawer extends JFrame {
	private CanvasPanel canvas;
	private Timer timer;

	public RandomLineDrawer() {
		super("Random Line Drawer");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		canvas = new CanvasPanel();
		add(canvas);

		setVisible(true);

		// Timer to add lines (faster speed for better effect)
		timer = new Timer(10, e -> canvas.drawRandomLine());
		timer.start();
	}

	// Inner class to handle custom drawing
	private class CanvasPanel extends JPanel {
		private BufferedImage image;
		private Graphics2D g2d;
		private Random r = new Random();

		public CanvasPanel() {
			setBackground(Color.WHITE); // Initial background
		}

		public void drawRandomLine() {
			int w = getWidth();
			int h = getHeight();

			if (w <= 0 || h <= 0)
				return;

			// Initialize image if needed or on resize
			if (image == null || image.getWidth() != w || image.getHeight() != h) {
				BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
				Graphics2D newG = newImage.createGraphics();

				// If we had an old image, copy it over (preserve drawing)
				if (image != null) {
					newG.drawImage(image, 0, 0, null);
				} else {
					newG.setColor(Color.WHITE); // Default background for new image
					newG.fillRect(0, 0, w, h);
				}

				image = newImage;
				g2d = image.createGraphics();
			}

			// Draw a new random line
			g2d.setColor(Color.RED);
			g2d.drawLine(r.nextInt(w), r.nextInt(h), r.nextInt(w), r.nextInt(h));

			// Request repaint to show the updated image
			repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (image != null) {
				g.drawImage(image, 0, 0, null);
			}
			g.setColor(Color.BLACK);
			g.drawString("Accumulating random lines...", 10, 20);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new RandomLineDrawer());
	}
}

/*
 * 1. Creates a JFrame with a custom JPanel.
 * 2. Uses a BufferedImage as a "back buffer" to store drawn pixels.
 * 3. A Timer fires every 10ms to draw a new random red line onto the buffer.
 * 4. The panel paints the buffer to the screen, allowing lines to accumulate.
 */