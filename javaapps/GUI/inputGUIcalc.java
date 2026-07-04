// Nick Buchanan
// 12/21/2010
// PD 4

import java.awt.*;
import java.awt.event.*;

// Main class for the Calculator GUI dealing with AWT components and Event Handling
public class inputGUIcalc extends Frame implements ActionListener {

	// Define window dimensions constants
	private final int winheight = 450; // declares the height of the window
	private final int winlength = 320; // declares the length of the window

	// Declare GUI Components: TextFields for input, Buttons for operations, Label
	// for output
	public TextField n1;
	public TextField n2;
	public Button add1;
	public Button sub;
	public Button dev;
	public Button mult;
	public Label output;

	// Constructor: Sets up the UI layout and components
	public inputGUIcalc() {
		// Set the window title
		super("inputGUIcalc");

		// Configure window size and layout manager
		setSize(winlength, winheight);
		setLayout(null); // Using null layout for absolute positioning

		// Initialize TextFields for user input (width of 4 columns)
		n1 = new TextField(4);
		n2 = new TextField(4);

		// Initialize and configure 'Add' button
		add1 = new Button("Add");
		add1.addActionListener(this); // Register event listener
		add1.setActionCommand("add"); // Set command name for event

		// Set background color of the window
		this.setBackground(new Color(160, 32, 240));

		// Initialize and configure 'Subtract' button
		sub = new Button("Subtract");
		sub.setActionCommand("sub");
		sub.addActionListener(this);

		// Initialize and configure 'Divide' button
		dev = new Button("Divide"); // Fixed typo from 'Devide'
		dev.addActionListener(this);
		dev.setActionCommand("dev");

		// Initialize and configure 'Multiply' button
		mult = new Button("Multiply");
		mult.addActionListener(this);
		mult.setActionCommand("mult");

		// Initialize output Label
		output = new Label("Your output");

		// Manually position components (x, y, width, height)
		n1.setBounds(70, 100, 80, 30);
		n2.setBounds(180, 100, 80, 30);
		add1.setBounds(70, 160, 80, 50);
		sub.setBounds(180, 160, 80, 50);
		mult.setBounds(70, 230, 80, 50);
		dev.setBounds(180, 230, 80, 50);
		output.setBounds(80, 320, 100, 80);

		// Set custom font for the output label
		output.setFont(new Font("Comic Sans MS", Font.BOLD, 15));

		// Add all components to the Frame window
		add(n1);
		add(n2);
		add(add1);
		add(sub);
		add(dev);
		add(mult);
		add(output);

		// Ensure background color is applied (redundant but safe)
		this.setBackground(new Color(160, 32, 240));

		// Make the window visible to the user
		setVisible(true);
	}

	// Event Handler: Called when a button is clicked
	public void actionPerformed(ActionEvent e) {
		double d1, d2;

		// Parse numbers from the text fields
		// Note: This will crash if non-numeric text is entered
		d1 = Double.parseDouble(n1.getText());
		d2 = Double.parseDouble(n2.getText());

		// Check which button was pressed and perform the corresponding math
		if (e.getSource() == add1) {
			output.setText(d1 + d2 + " "); // Addition
		} else if (e.getSource() == sub) {
			output.setText(d1 - d2 + ""); // Subtraction
		} else if (e.getSource() == dev) {
			output.setText(d1 / d2 + ""); // Division
		} else if (e.getSource() == mult) {
			output.setText(d1 * d2 + ""); // Multiplication
		}
	}

	// Main method: Entry point of the application
	public static void main(String[] args) {
		// Create an instance of the calculator window
		new inputGUIcalc();
	}
}

/*
 * 1. GUI Setup: It initializes a window (Frame) titled "inputGUIcalc" with
 * specific dimensions and a purple background.
 * 2. Inputs: Two text fields are provided for the user to input numerical
 * values.
 * 3. Controls: Four buttons are created for basic arithmetic operations: Add,
 * Subtract, Multiply, and Divide.
 * 4. Layout: The components are manually positioned using absolute coordinates
 * (setBounds).
 * 5. Event Handling: The program listens for button clicks. When a button is
 * pressed:
 * - It reads the text from the two input fields.
 * - Converts the text into double-precision floating-point numbers.
 * - Performs the mathematical operation corresponding to the clicked button.
 * - Updates the output label with the result.
 */