// Nicholas Buchanan
// Version 1.0

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NumberSystemConverter extends JFrame implements ActionListener {
	// GUI Components
	private Label output;
	private TextField input;
	private Button calculate;
	private Checkbox dec;
	private Checkbox bin;
	private Checkbox hex;
	private Checkbox oct;
	private Checkbox reverse;
	private Container options;
	private Container inputarea;

	public NumberSystemConverter() {
		super("Number System Converter");
		// Increased height slightly to accommodate vertical stacking
		setSize(400, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Use a vertical GridLayout (5 rows, 1 column) to stack sections
		setLayout(new GridLayout(5, 1));

		// 1. INPUT AREA
		inputarea = new Container();
		inputarea.setLayout(new FlowLayout(FlowLayout.CENTER));
		inputarea.add(new Label("Enter Value:"));
		input = new TextField(20);
		inputarea.add(input);
		add(inputarea);

		// 2. OPTIONS AREA
		options = new Container();
		options.setLayout(new FlowLayout(FlowLayout.CENTER));
		dec = new Checkbox("Decimal");
		bin = new Checkbox("Binary");
		hex = new Checkbox("Hex");
		oct = new Checkbox("Octal");
		options.add(dec);
		options.add(bin);
		options.add(hex);
		options.add(oct);
		add(options);

		// 3. CALCULATE BUTTON AREA
		Container calcPanel = new Container();
		calcPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		calculate = new Button("CALCULATE");
		// Make button slightly larger/prominent
		calculate.setPreferredSize(new Dimension(100, 30));
		calcPanel.add(calculate);
		add(calcPanel);

		// 4. REVERSE OPTION AREA
		Container reversePanel = new Container(); // Local variable now, or just use anonymous if preferred, but
													// existing code used field. Wait, I removed the field. So this is
													// fine as local var.
		reversePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		reverse = new Checkbox("Reverse order");
		reversePanel.add(reverse);
		add(reversePanel);

		// 5. ANSWER AREA
		output = new Label("ANSWER HERE", Label.CENTER);
		output.setFont(new Font("Dialog", Font.BOLD, 16));
		add(output);

		// Register listener
		calculate.addActionListener(this);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String inputStr = input.getText().trim();
		if (inputStr.isEmpty()) {
			output.setText("Please enter a value.");
			return;
		}

		try {
			// Determine Source and Target Bases
			int sourceBase = -1;
			int targetBase = -1;
			// Logic type logic removed as variable was unused

			// Check if "Reverse order" is selected
			boolean isReverse = reverse.getState();

			// LOGIC:
			// We need to identify which TWO checkboxes are selected.
			// Priority Order (Visual Left-to-Right): Decimal -> Binary -> Hex -> Octal

			// Collect validation states
			boolean d = dec.getState();
			boolean b = bin.getState();
			boolean h = hex.getState();
			boolean o = oct.getState();

			// Count selected
			int count = 0;
			if (d)
				count++;
			if (b)
				count++;
			if (h)
				count++;
			if (o)
				count++;

			if (count < 2) {
				output.setText("Select exactly 2 types.");
				return;
			} else if (count > 2) {
				output.setText("Select ONLY 2 types.");
				return;
			}

			// Define Bases
			// Decimal=10, Binary=2, Hex=16, Octal=8

			if (d && b) { // Decimal & Binary
				// Standard: Dec -> Bin | Reverse: Bin -> Dec
				if (!isReverse) {
					sourceBase = 10;
					targetBase = 2;
				} else {
					sourceBase = 2;
					targetBase = 10;
				}
			} else if (d && h) { // Decimal & Hex
				// Standard: Dec -> Hex | Reverse: Hex -> Dec
				if (!isReverse) {
					sourceBase = 10;
					targetBase = 16;
				} else {
					sourceBase = 16;
					targetBase = 10;
				}
			} else if (d && o) { // Decimal & Octal
				// Standard: Dec -> Oct | Reverse: Oct -> Dec
				if (!isReverse) {
					sourceBase = 10;
					targetBase = 8;
				} else {
					sourceBase = 8;
					targetBase = 10;
				}
			} else if (b && h) { // Binary & Hex
				// Standard: Bin -> Hex | Reverse: Hex -> Bin
				if (!isReverse) {
					sourceBase = 2;
					targetBase = 16;
				} else {
					sourceBase = 16;
					targetBase = 2;
				}
			} else if (b && o) { // Binary & Octal
				// Standard: Bin -> Oct | Reverse: Oct -> Bin
				if (!isReverse) {
					sourceBase = 2;
					targetBase = 8;
				} else {
					sourceBase = 8;
					targetBase = 2;
				}
			} else if (h && o) { // Hex & Octal
				// Standard: Hex -> Oct | Reverse: Oct -> Hex
				// Hex is visually "left" of Octal in standard layout usually,
				// but code adds: Dec, Bin, Hex, Oct. So Hex is left of Oct.
				if (!isReverse) {
					sourceBase = 16;
					targetBase = 8;
				} else {
					sourceBase = 8;
					targetBase = 16;
				}
			}

			// Perform Conversion
			// 1. Parse Input to Integer (Decimal)
			// Note: parsing Hex requires ignoring case
			long decimalValue = Long.parseLong(inputStr, sourceBase);

			// 2. Convert Decimal to Target String
			String result = "";
			if (targetBase == 10) {
				result = Long.toString(decimalValue);
			} else if (targetBase == 2) {
				result = Long.toBinaryString(decimalValue);
			} else if (targetBase == 8) {
				result = Long.toOctalString(decimalValue);
			} else if (targetBase == 16) {
				result = Long.toHexString(decimalValue).toUpperCase();
			}

			output.setText("Result: " + result);

		} catch (NumberFormatException ex) {
			output.setText("Invalid Input for Base.");
		} catch (Exception ex) {
			output.setText("Error.");
		}
	}

	public static void main(String[] args) {
		new NumberSystemConverter();
	}
}

/*
 * 1. User Inputs a value in the text field.
 * 2. User selects EXACTLY TWO checkboxes corresponding to the source and
 * destination systems.
 * 3. Standard Mode (Reverse unchecked):
 * - The conversion flows from the "Left-most" option to the "Right-most" option
 * based on the visual/code order: Decimal -> Binary -> Hex -> Octal.
 * - Example: If Decimal and Binary are checked, it converts Decimal TO Binary.
 *
 * 4. Reverse Mode (Reverse checked):
 * - The conversion flows in the opposite direction (Right -> Left).
 * - Example: If Decimal and Binary are checked + Reverse, it converts Binary TO
 * Decimal.
 *
 * 5. Implementation uses Java's built-in parsing (Long.parseLong) and
 * formatting (Long.toXString) methods.
 */