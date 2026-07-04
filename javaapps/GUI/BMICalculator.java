// Nicholas Buchanan
// May 4, 2011

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BMICalculator extends JFrame {

	// Components
	private JTextField inchesField;
	private JTextField poundsField;
	private JLabel resultLabel;
	private JButton calculateButton;

	public BMICalculator() {
		super("BMI Calculator");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(4, 2, 10, 10)); // Grid Layout for cleaner look

		// Input: Height
		add(new JLabel(" Height (Inches):"));
		inchesField = new JTextField();
		add(inchesField);

		// Input: Weight
		add(new JLabel(" Weight (Pounds):"));
		poundsField = new JTextField();
		add(poundsField);

		// Button
		calculateButton = new JButton("Calculate BMI");
		calculateButton.addActionListener(e -> calculateBMI());
		add(calculateButton);

		// Placeholder for grid alignment
		add(new JLabel(""));

		// Output
		resultLabel = new JLabel("Index: --");
		resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resultLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		add(resultLabel);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void calculateBMI() {
		try {
			String inchesText = inchesField.getText();
			String poundsText = poundsField.getText();

			if (inchesText.isEmpty() || poundsText.isEmpty()) {
				resultLabel.setText("Enter all values!");
				return;
			}

			double inches = Double.parseDouble(inchesText);
			double pounds = Double.parseDouble(poundsText);

			// Original Logic Constants
			double meters = inches / 39.36;
			double kgrams = pounds / 2.2;

			double finalIndex = kgrams / Math.pow(meters, 2);

			// Cast to int as per original code behavior
			resultLabel.setText("Index: " + (int) finalIndex);

		} catch (NumberFormatException e) {
			resultLabel.setText("Invalid Numbers");
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new BMICalculator());
	}
}

/*
 * PROGRAM DESCRIPTION:
 *
 * BMICalculator.java
 *
 * This program calculates a user's Body Mass Index (BMI).
 * It takes input for height (in inches) and weight (in pounds), converts them
 * to metric units (meters and kilograms), performs the standard BMI formula,
 * and displays the resulting index as an integer.
 */