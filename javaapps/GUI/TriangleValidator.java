// Nicholas Buchanan
// 2011

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TriangleValidator extends JFrame implements ActionListener {
	private final int WIN_HEIGHT = 400;
	private final int WIN_WIDTH = 400;

	private JTextField fieldA, fieldB, fieldC;
	private JButton checkButton;
	private JTextArea outputArea;

	public TriangleValidator() {
		super("Triangle Validator");
		setSize(WIN_WIDTH, WIN_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));

		// Input Panel
		JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
		inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		inputPanel.add(new JLabel("Side A:"));
		fieldA = new JTextField();
		inputPanel.add(fieldA);

		inputPanel.add(new JLabel("Side B:"));
		fieldB = new JTextField();
		inputPanel.add(fieldB);

		inputPanel.add(new JLabel("Side C:"));
		fieldC = new JTextField();
		inputPanel.add(fieldC);

		checkButton = new JButton("Verify Triangle");
		checkButton.addActionListener(this);
		inputPanel.add(new JLabel("")); // Spacer
		inputPanel.add(checkButton);

		add(inputPanel, BorderLayout.NORTH);

		// Output Area
		outputArea = new JTextArea();
		outputArea.setEditable(false);
		outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		outputArea.setBorder(BorderFactory.createTitledBorder("Results"));
		add(new JScrollPane(outputArea), BorderLayout.CENTER);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		outputArea.setText(""); // Clear previous results

		try {
			double a = Double.parseDouble(fieldA.getText());
			double b = Double.parseDouble(fieldB.getText());
			double c = Double.parseDouble(fieldC.getText());

			if (a <= 0 || b <= 0 || c <= 0) {
				outputArea.setText("Error: Side lengths must be positive.");
				return;
			}

			// Triangle Inequality Theorem
			if ((a + b > c) && (a + c > b) && (b + c > a)) {
				outputArea.append("Valid Triangle!\n\n");

				// Classify by Side
				if (a == b && b == c) {
					outputArea.append("Type: Equilateral\n");
				} else if (a == b || b == c || a == c) {
					outputArea.append("Type: Isosceles\n");
				} else {
					outputArea.append("Type: Scalene\n");
				}

				// Check for Right Triangle (Pythagorean Theorem)
				// Use small epsilon for float comparison logic if strict,
				// but for school projects direct check is usually expected.
				// Sorting helps identify hypotenuse.
				double[] sides = { a, b, c };
				java.util.Arrays.sort(sides); // sides[2] is hypotenuse
				if (Math.abs((sides[0] * sides[0] + sides[1] * sides[1]) - (sides[2] * sides[2])) < 0.0001) {
					outputArea.append("Angle: Right Triangle\n");
				} else {
					outputArea.append("Angle: Not a Right Triangle\n");
				}

			} else {
				outputArea.setText("Invalid Triangle.\nThe sum of any two sides must be\ngreater than the third.");
			}

		} catch (NumberFormatException ex) {
			outputArea.setText("Error: Please enter valid numbers.");
		}
	}

	public static void main(String[] args) {
		new TriangleValidator();
	}
}

/*
 * This program validates whether three entered side lengths can form a
 * geometric triangle based on the Triangle Inequality Theorem.
 *
 * IF valid, it further classifies the triangle as:
 * 1. Equilateral, Isosceles, or Scalene.
 * 2. Right-Angled (using the Pythagorean Theorem).
 *
 * It utilizes a Java Swing GUI for user interaction.
 */