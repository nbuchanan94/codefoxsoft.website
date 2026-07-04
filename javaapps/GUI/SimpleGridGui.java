
//Nicholas Larkin Buchanan
//2011-03-30
//pd 8
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleGridGui extends JFrame implements ActionListener {

	private final int winheight = 200;
	private final int winlength = 300;

	// Declaring components
	public Button button1;
	public Button button2;
	public Button button3;
	public Button getInput;

	public SimpleGridGui() {
		super("MyApp");
		setSize(winlength, winheight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2, 2)); // 2x2 Grid for 4 items

		// Fixing Initialization (was causing NPE)
		button1 = new Button("Button 1 (Disabled)");
		button2 = new Button("Button 2");
		button3 = new Button("Button 3");
		getInput = new Button("Get Input (Disabled)");

		// Setting properties
		getInput.setEnabled(false); // Was disable()
		button1.setEnabled(false); // Was disable()

		// Adding to layout
		add(button1);
		add(button2);
		add(button3);
		add(getInput);

		setVisible(true);
		System.out.println("System: GUI Initialized and Visible");
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("DEBUG: Action Performed: " + e.getActionCommand());
	}

	public static void main(String[] args) {
		System.out.println("System: Application Start");
		new SimpleGridGui();
	}
}
// Usage: Run this file. Displays a grid of buttons, some disabled to
// demonstrate setEnabled(false). Program has no functionality. Created for
// demonstrative purposes.