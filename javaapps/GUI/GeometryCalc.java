// Psychowolf94 AKA NICK BUCHANAN <(^_^)>
// 2010
//

import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

public class GeometryCalc extends Frame implements ActionListener {
	// UI Components
	public Button calculate = new Button("Calculate");
	public TextField radiusInput = new TextField(10); // Renamed from 'radius' for clarity

	public Label radiusLabel = new Label("Enter Radius:");
	public Label circumference = new Label("Circumference: ---");
	public Label circleArea = new Label("Circle Area: ---");
	public Label sphereSA = new Label("Sphere Surface Area: ---");
	public Label sphereVol = new Label("Sphere Volume: ---");

	public double inp = 0;

	// Constructor setup
	public GeometryCalc() {
		super("Geometry Calculator");
		setSize(400, 300);
		setLayout(new GridLayout(7, 1)); // Vertical stack layout

		// Add window close listener
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});

		// 1. Input Panel
		Panel inputPanel = new Panel(new FlowLayout());
		inputPanel.add(radiusLabel);
		inputPanel.add(radiusInput);
		add(inputPanel);

		// 2. Calculate Button
		Panel buttonPanel = new Panel(new FlowLayout());
		calculate.addActionListener(this);
		buttonPanel.add(calculate);
		add(buttonPanel);

		// 3. Results Labels
		add(circumference);
		add(circleArea);
		add(sphereSA);
		add(sphereVol);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		try {
			String text = radiusInput.getText();
			if (text.isEmpty())
				return;

			// Use double for radius to allow decimal inputs
			inp = Double.parseDouble(text);

			// --- CALCULATIONS ---

			// 1. Circumference = 2 * PI * r
			double circVals = 2 * Math.PI * inp;

			// 2. Circle Area = PI * r^2
			double areaVals = Math.PI * Math.pow(inp, 2);

			// 3. Sphere Surface Area = 4 * PI * r^2
			// Formula corrected from previous version
			double sphereSAVals = 4 * Math.PI * Math.pow(inp, 2);

			// 4. Sphere Volume = (4/3) * PI * r^3
			// FIXED: Use 4.0/3.0 to force floating point division
			double sphereVolVals = (4.0 / 3.0) * Math.PI * Math.pow(inp, 3);

			// --- UPDATE LABELS ---
			// Update labels with formatted strings (2 decimal places)
			circumference.setText(String.format("Circumference: %.2f", circVals));
			circleArea.setText(String.format("Circle Area: %.2f", areaVals));
			sphereSA.setText(String.format("Sphere Surface Area: %.2f", sphereSAVals));
			sphereVol.setText(String.format("Sphere Volume: %.2f", sphereVolVals));

		} catch (NumberFormatException ex) {
			circumference.setText("Error: Invalid Number");
		}
	}

	public static void main(String[] args) {
		new GeometryCalc();
	}
}

/*
 * 1. User Interface: Displays a window with an input field for Radius and a
 * "Calculate" button.
 * 2. Input: The user enters a numeric radius value (integer or decimal).
 * 3. Processing: When "Calculate" is clicked, the program converts the input to
 * a double and computes:
 * - Circle Circumference (2 * PI * r)
 * - Circle Area (PI * r^2)
 * - Sphere Surface Area (4 * PI * r^2)
 * - Sphere Volume (4/3 * PI * r^3)
 * 4. Output: The results are displayed in separate labels below the button,
 * formatted to 2 decimal places.
 */