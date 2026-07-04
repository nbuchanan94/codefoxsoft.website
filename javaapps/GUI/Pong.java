// Nicholas Buchanan
// October 12 2011

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Pong extends JPanel implements KeyListener, Runnable {
	// --- Global Variables ---
	private Thread thread;
	private boolean running = true;

	// Game Entities
	private int ballx = 50;
	private int bally = 50;
	private int paddlex = 250; // Paddle X position
	private int paddley = 600; // Fixed Paddle Y position

	private int paddleWidth = 60;
	private int paddleHeight = 15;

	// Movement Logic
	private int x_direction = 6;
	private int y_direction = 8;
	private Random rnd = new Random();

	// Score / Stats
	private int bouncedPaddle = 0;

	public Pong() {
		// JPanel Setup
		this.setPreferredSize(new Dimension(700, 700));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(this);
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	@Override
	public void run() {
		// Game Loop
		while (running) {
			updateGameLogic();
			repaint();

			try {
				Thread.sleep(30); // ~30 FPS
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void updateGameLogic() {
		// --- Wall Collisions ---

		// Horizontal Walls
		if (ballx < 0 || ballx > getWidth() - 15) {
			x_direction *= -1;
			playSound("sidehit.wav");
		}

		// Ceiling Collision
		if (bally < 0) {
			y_direction *= -1;
			playSound("sidehit.wav");
		}

		// Floor Collision (Game Over check usually, but just bouncing for now as per
		// original)
		if (bally > getHeight() - 15) {
			y_direction *= -1;
			// playSound("gameover.wav"); // Optional
		}

		// --- Paddle Collision ---
		// Basic rectangular collision check
		if (bally + 10 >= paddley && bally <= paddley + paddleHeight &&
				ballx + 10 >= paddlex && ballx <= paddlex + paddleWidth) {

			// Reverse Y
			y_direction *= -1;

			// Add some randomness to X, but ensure it doesn't stop (0)
			int randomFactor = rnd.nextInt(3); // 0, 1, 2
			if (randomFactor == 0)
				randomFactor = 1; // prevent 0 speed if multiplying

			// In original logic: x_direction *= rnd.nextInt(3)*-1;
			// We want to keep it moving.
			// Let's just slightly alter speed or direction for fun,
			// but for stability let's keep simple bounce Logic like the original intended:

			x_direction = (x_direction > 0 ? 1 : -1) * (rnd.nextInt(3) + 4); // Speed 4, 5, or 6
			playSound("sidehit.wav");
			bouncedPaddle++;
		}

		// Apply Movement
		ballx += x_direction;
		bally += y_direction;
	}

	private void playSound(String filename) {
		// Placeholder for sound logic.
		// Original used Applet.newAudioClip which is deprecated/unavailable in standard
		// Swing easily without libs.
		// Keeping empty to prevent crashes.
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Clear screen

		// Draw Ball
		g.setColor(Color.GREEN);
		g.fillOval(ballx, bally, 15, 15);

		// Draw Paddle
		g.fillRect(paddlex, paddley, paddleWidth, paddleHeight);

		// Draw Score Info
		g.setColor(Color.WHITE);
		g.drawString("Hits: " + bouncedPaddle, 10, 20);
	}

	// --- Key Instructions ---
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		int speed = 15;

		if (key == KeyEvent.VK_A) {
			if (paddlex > 0)
				paddlex -= speed;
		} else if (key == KeyEvent.VK_S) {
			if (paddlex < getWidth() - paddleWidth)
				paddlex += speed;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	// --- Main Entry Point ---
	public static void main(String[] args) {
		JFrame frame = new JFrame("Pong 2011");
		Pong game = new Pong();

		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game.start();
	}
}

/*
 * It demonstrates a basic implementation of the classic arcade game
 * using Java Swing mechanics.
 */
