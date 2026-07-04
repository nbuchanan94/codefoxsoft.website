// Nicholas Buchanan
// Wednesday, October 5, 2011

import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.util.Random;

public class KeyGenerator extends JFrame {

	private JTextField keyField;
	private JButton generateButton;
	private JButton exitButton;

	public KeyGenerator() {
		super("Fibonacci Hex Key Generator");
		setSize(600, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		getContentPane().setBackground(new Color(50, 50, 50)); // Dark theme

		// Output Field
		keyField = new JTextField(40);
		keyField.setEditable(false);
		keyField.setFont(new Font("Monospaced", Font.BOLD, 14));
		keyField.setHorizontalAlignment(JTextField.CENTER);
		add(keyField);

		// Generate Button
		generateButton = new JButton("Generate Key");
		generateButton.addActionListener(e -> generateKey());
		add(generateButton);

		// Exit Button
		exitButton = new JButton("Exit");
		exitButton.addActionListener(e -> System.exit(0));
		add(exitButton);

		setLocationRelativeTo(null); // Center on screen
		setVisible(true);
	}

	private void generateKey() {
		// Key Generation Logic:
		// 1. Pick a large random index for Fibonacci sequence
		// 2. Calculate the Fibonacci number -> Hex
		// 3. Format into 4 groups of 4 characters (XXXX-XXXX-XXXX-XXXX)

		Random rand = new Random();
		int n = rand.nextInt(401) + 100; // Random index 100 to 500

		BigInteger fib = calculateFibonacci(n);
		String hex = fib.toString(16).toUpperCase();

		// Ensure we have enough characters (pad if impossibly small)
		while (hex.length() < 16) {
			hex = hex + "0";
		}

		// Take a slice (first 16 meaningful digits)
		String rawKey = hex.substring(0, 16);

		// Format with dashes
		StringBuilder formattedKey = new StringBuilder();
		for (int i = 0; i < rawKey.length(); i++) {
			if (i > 0 && i % 4 == 0) {
				formattedKey.append("-");
			}
			formattedKey.append(rawKey.charAt(i));
		}

		keyField.setText(formattedKey.toString());
	}

	private BigInteger calculateFibonacci(int n) {
		if (n <= 1)
			return BigInteger.valueOf(n);

		BigInteger a = BigInteger.ZERO;
		BigInteger b = BigInteger.ONE;

		for (int i = 2; i <= n; i++) {
			BigInteger temp = a.add(b);
			a = b;
			b = temp;
		}
		return b;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new KeyGenerator());
	}
}

/*
 *
 * It selects a random high-index position in the Fibonacci sequence (roughly
 * between
 * indices 100 and 500), calculates the massive resulting integer, and converts
 * it
 * into a Hexadecimal representation to form a unique alphanumeric code.
 */