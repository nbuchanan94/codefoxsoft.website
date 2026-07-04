// Nicholas Buchanan
// October 10, 2010

import javax.swing.*;
import java.awt.*;

public class BallMover extends JFrame {

	public BallMover() {
		super("Moving Ball");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Use a JPanel for double-buffered drawing (smooth animation)
		BallPanel panel = new BallPanel();
		add(panel);

		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new BallMover());
	}

	// Inner class to handle custom drawing
	private class BallPanel extends JPanel {
		private int x = 0;
		private int y = 200;
		private int dx = 5; // Speed
		private final int BALL_SIZE = 100;

		public BallPanel() {
			setBackground(Color.BLUE);

			// Animation Timer
			Timer timer = new Timer(20, e -> {
				updatePosition();
				repaint();
			});
			timer.start();
		}

		private void updatePosition() {
			x += dx;

			// Bounce logic
			if (x + BALL_SIZE >= getWidth()) {
				x = getWidth() - BALL_SIZE;
				dx = -dx;
			} else if (x <= 0) {
				x = 0;
				dx = -dx;
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g); // Clears background

			g.setColor(Color.YELLOW);
			g.fillOval(x, y, BALL_SIZE, BALL_SIZE);
		}
	}
}

/*
 * PROGRAM DESCRIPTION:
 *
 * BallMover.java
 *
 * This program displays a simple animation of a yellow ball bouncing
 * back and forth horizontally across a blue window.
 */