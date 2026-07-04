// Nicholas Buchanan
// PD 4
// Finally Finished :D Friday, October 29, 2010

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SevenEleven extends JFrame implements ActionListener {

	private final int winheight = 450;
	private final int winlength = 500;

	// Game State Variables
	public boolean roll1 = false;
	public boolean roll2 = false;
	public boolean roll3 = false;
	public boolean win = false;
	public boolean isdone = false;

	// GUI Components
	public JButton button1;
	public JButton button2;
	public TextArea output; // Keeping TextArea for scrolling simplicity without JScrollPane refactor
							// overhead
	public JLabel pic1;
	public JLabel pic2;

	public SevenEleven() {
		super("Seven Or Eleven in Heaven");
		setSize(winlength, winheight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout()); // Use BorderLayout for better placement

		// Top Panel for Buttons
		JPanel topPanel = new JPanel();
		button1 = new JButton("GENERATE!!!");
		button2 = new JButton("New Game");
		button1.setActionCommand("gen");
		button2.setActionCommand("new");
		button1.addActionListener(this);
		button2.addActionListener(this);
		topPanel.add(button1);
		topPanel.add(button2);
		add(topPanel, BorderLayout.NORTH);

		// Center for Output
		// Using AWT TextArea as in original for simplicity, but placed in Center
		output = new TextArea("", 10, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
		output.setEditable(false);
		add(output, BorderLayout.CENTER);

		// Bottom Panel for Dice Images
		JPanel bottomPanel = new JPanel();
		pic1 = new JLabel("");
		pic2 = new JLabel("");
		bottomPanel.add(pic1);
		bottomPanel.add(pic2);
		add(bottomPanel, BorderLayout.SOUTH);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if (command.equals("gen")) {
			if (!win && !isdone) {
				playTurn();
			} else if (win) {
				output.append("\nYou already won! Press 'New Game'.");
			} else {
				output.append("\nNo more rolls left. Hit 'New Game' :D");
			}
		} else if (command.equals("new")) {
			resetGame();
		}
	}

	private void playTurn() {
		Random r = new Random();
		int dice1 = r.nextInt(6) + 1;
		int dice2 = r.nextInt(6) + 1;

		// Update Images
		updateDiceImages(dice1, dice2);

		// Game Logic
		int sum = dice1 + dice2;
		boolean isLucky = (sum == 7 || sum == 11);

		if (!roll1) {
			roll1 = true;
			if (isLucky) {
				output.append("\nYOU WIN - " + dice1 + " " + dice2);
				win = true;
				isdone = true;
			} else {
				output.append("\nYOU HAVE 2 MORE TRYS TO MAKE 7 OR 11 - " + dice1 + " " + dice2);
			}
		} else if (!roll2) {
			roll2 = true;
			if (isLucky) {
				output.append("\nYOU WIN - " + dice1 + " " + dice2);
				win = true;
				isdone = true;
			} else {
				output.append("\nYOU HAVE ONE MORE TO MAKE 7 OR 11 - " + dice1 + " " + dice2);
			}
		} else if (!roll3) {
			roll3 = true;
			if (isLucky) {
				output.append("\nYOU WIN - " + dice1 + " " + dice2);
				win = true;
				isdone = true;
			} else {
				output.append("\nYou fail... sorry. - " + dice1 + " " + dice2);
				output.append("\nGAME OVER.");
				isdone = true;
			}
		}
	}

	private void updateDiceImages(int d1, int d2) {
		// Images are expected to be in the same folder or classpath
		// Using simple ImageIcon assuming working directory is correct
		pic1.setIcon(new ImageIcon("die" + d1 + ".png"));
		pic2.setIcon(new ImageIcon("die" + d2 + ".png"));
	}

	private void resetGame() {
		output.append("\n\n****************NEW GAME STARTED****************\n");
		win = false;
		roll1 = false;
		roll2 = false;
		roll3 = false;
		isdone = false;
		pic1.setIcon(null);
		pic2.setIcon(null);
	}

	public static void main(String[] args) {
		new SevenEleven();
	}
}

/*
 * PROGRAM DESCRIPTION:
 * SevenEleven.java
 *
 * This one is a project and deserves its own folder.
 * 
 * HOW IT WORKS:
 * 1. The goal is to get 7 or 11.
 * 2. It's a simple dice game 711heaven!
 */