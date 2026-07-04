// Nicholas Buchanan
// Feb 2011

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PythagoreanTriples extends JFrame implements ActionListener {
	private final int winheight = 400;
	private final int winwidth = 400;
	public Button button1;
	public TextArea output;

	public PythagoreanTriples() {
		super("Pythagorean Triples Finder");
		setSize(winwidth, winheight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		button1 = new Button("Find Triples (1-100)"); // Updated label
		output = new TextArea();
		output.setEditable(false);
		output.setFont(new Font("Monospaced", Font.PLAIN, 12));

		add(button1, BorderLayout.NORTH);
		add(output, BorderLayout.CENTER);

		button1.addActionListener(this);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		output.setText(""); // Clear previous results
		output.append("================\n");
		output.append(" A\t B\t C\n");
		output.append("================\n");

		int count = 0;

		// Loop through reasonable range to find triples
		for (int a = 1; a <= 100; a++) {
			for (int b = a; b <= 100; b++) { // Start b from a to avoid duplicates (e.g., 3,4,5 vs 4,3,5)
				for (int c = b; c <= 150; c++) {

					// Pythagorean Theorem Check: a^2 + b^2 = c^2
					if ((a * a) + (b * b) == (c * c)) {
						output.append(" " + a + "\t " + b + "\t " + c + "\n");
						count++;
					}
				}
			}
		}
		output.append("----------------\n");
		output.append("Total Found: " + count);
	}

	public static void main(String[] args) {
		new PythagoreanTriples();
	}
}

/*
 * 1. The user clicks "Find Triples".
 * 2. The program iterates through loops for variables a, b, and c.
 * 3. It checks the condition (a*a + b*b == c*c).
 * 4. If the condition is met, the triple is displayed in the text area.
 */