// Nicholas Buchanan
//October 6 2011


import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class RandomPixels extends JFrame {

	// Original variables from Applet
	int x_pos = 10;
	int y_pos = 10;
	int radius = 5;
	int color = 5;
	public Random rand = new Random();

	public RandomPixels() {
		super("Random Pixels");
		// Original size logic: Screen Size
		setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

 		setBackground(Color.black);

		setVisible(true);

		Timer timer = new Timer(10, e -> {
			// Original logic in 'run' loop just updated x_pos/y_pos (unused in paint?) and
			// called repaint
			x_pos = rand.nextInt(getWidth());
			y_pos = rand.nextInt(getHeight());
			repaint();
		});
		timer.start();
	}

	@Override
	public void paint(Graphics g) {

		// To be safe and minimal:

		// Original Paint Logic
		int red = (int) (Math.random() * 256);
		int green = (int) (Math.random() * 256);
		int blue = (int) (Math.random() * 256);

		// g.setColor(new Color(red,green,blue)); // Commented out in original
		g.setFont(new Font("Lucida Console", Font.PLAIN, 30));
		g.setColor(Color.WHITE);
		// I'll ensure it's visible.
		g.drawString("PURE EPICNESS", 30, 30);

		g.setColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));

		int w = getWidth();
		int h = getHeight();

 		// instead of 1000
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);
		g.fillOval(rand.nextInt(w), rand.nextInt(h), 5, 5);

		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
		g.drawLine(rand.nextInt(w), rand.nextInt(h), rand.nextInt(w), rand.nextInt(h));
	}

	public static void main(String[] args) {
		new RandomPixels();
	}
}

/*
 * 1. Creates a full-screen window.
 * 2. Rapidly repaints the screen (Timer ~10ms).
 * 3. On each frame, drawing dozens of random ovals and lines covering the
 * entire screen.
 */