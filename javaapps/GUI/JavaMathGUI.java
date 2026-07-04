
//Nicholas Larkin Buchanan
//2012-02-15
//pd 8
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.FlowLayout;
import java.net.*;
import java.lang.Math;

public class JavaMathGUI extends JFrame implements ActionListener {

	private final int winheight = 300;// declares the height of the window;
	private final int winlength = 500;// declares the length of the window;

	public Button button1;
	public TextField input;
	public Label label1;
	public TextArea output;

	public JavaMathGUI() {
		super("Math Class");
		setSize(winlength, winheight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		button1 = new Button("Show Calculations");
		input = new TextField(5);
		output = new TextArea("");
		label1 = new Label("Enter the Number to use to calculate:");
		add(label1);
		add(input);
		add(output);

		add(button1);

		button1.addActionListener(this);

		setVisible(true);
		System.out.println("System: GUI Initialized and Visible");

	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("DEBUG: Button clicked");
		try {
			double inNum = Double.parseDouble(input.getText());
			System.out.println("DEBUG: Input parsed: " + inNum);

			output.setText(""); // Clear previous
			output.append("Number: " + inNum + "\n");
			output.append("----------------\n");

			output.append("Abs: " + Math.abs(inNum) + "\n");
			output.append("Ceil: " + Math.ceil(inNum) + "\n");
			output.append("Floor: " + Math.floor(inNum) + "\n");
			output.append("Round: " + Math.round(inNum) + "\n");
			output.append("Sqrt: " + Math.sqrt(inNum) + "\n");
			output.append("Pow (x^3): " + Math.pow(inNum, 3) + "\n"); // Demonstating power

			// Random demo relative to input?
			output.append("Max(x, 100): " + Math.max(inNum, 100) + "\n");
			output.append("Min(x, 0): " + Math.min(inNum, 0) + "\n");

			System.out.println("DEBUG: Calculations displayed");

		} catch (NumberFormatException ex) {
			output.setText("Error: Invalid Number");
			System.out.println("DEBUG: Invalid Input");
		}

	}

	public static void main(String[] args) {
		System.out.println("System: Application Start");
		new JavaMathGUI();
	}
}
// Usage: Run this file to start the GUI. Enter a number to see various Math
// class operations.
