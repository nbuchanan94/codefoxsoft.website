// Nicholas Buchanan
// September 15, 2010

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MorseCodeGUI extends JFrame implements ActionListener {

	private JButton toMorseButton;
	private JTextArea outputArea;
	private JTextField inputField;

	// Lookup Tables (Same as MorseCodeTranslator)
	private static final char[] ALPHABET = {
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
			'u', 'v', 'w', 'x', 'y', 'z', ' '
	};

	private static final String[] MORSE_CODES = {
			" * - ", // a
			" - * * * ", // b
			" - * - * ", // c
			" - * * ", // d
			" * ", // e
			" * * - * ", // f
			" - - *", // g
			" * * * * ", // h
			" * * ", // i
			" * - - - ", // j
			" - * - ", // k
			" * - * * ", // l
			" - - ", // m
			" - * ", // n
			" - - - ", // o
			" * - - * ", // p
			" - - * - ", // q
			" * - * ", // r
			" * * * ", // s
			" - ", // t
			" * * -", // u
			" * * * -", // v
			" * - - ", // w
			" - * * -", // x
			" - * - - ", // y
			" - - * * ", // z
			"   " // space (3 spaces for word separation)
	};

	public MorseCodeGUI() {
		super("Morse Code Converter");
		setSize(530, 260); // Original dimensions preserved
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());

		// Input
		inputField = new JTextField("Type text here to convert...", 40);
		add(inputField);

		// Button
		toMorseButton = new JButton("Convert to Morse");
		toMorseButton.addActionListener(this);
		add(toMorseButton);

		// Output
		outputArea = new JTextArea(10, 45);
		outputArea.setEditable(false);
		outputArea.setBorder(BorderFactory.createEtchedBorder());

		// Add scroll pane for better UX
		JScrollPane scroll = new JScrollPane(outputArea);
		add(scroll);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String input = inputField.getText().toLowerCase();
		String translated = translateToMorse(input);
		outputArea.setText(translated);
	}

	public static String translateToMorse(String input) {
		StringBuilder finalString = new StringBuilder();

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			int index = -1;

			// Find index
			for (int k = 0; k < ALPHABET.length; k++) {
				if (ALPHABET[k] == c) {
					index = k;
					break;
				}
			}

			if (index != -1) {
				finalString.append(MORSE_CODES[index]);
			} else {
				finalString.append("? ");
			}
		}
		return finalString.toString();
	}

	public static void main(String[] args) {
		// Ensure GUI runs on Event Dispatch Thread
		SwingUtilities.invokeLater(() -> new MorseCodeGUI());
	}
}

/*
 * This is a Graphical User Interface (GUI) version of the Morse Code
 * Translator.
 * It provides a window where users can type a sentence and click a button to
 * see the text converted into Morse Code sequences.
 *
 */