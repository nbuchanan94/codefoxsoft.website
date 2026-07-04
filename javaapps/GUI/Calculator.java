// Nicholas Buchanan
// 11/5/2011
//
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.text.DecimalFormat;
import java.util.*;

public class Calculator extends Frame implements ActionListener {
	// GUI Components
	private Button keys[]; // Array of keys
	private Panel keypad; // Panel to hold keys
	private TextField lcd; // Display screen

	// Logic Variables
	private double op1; // Stores the first operand
	private boolean first; // Flag for first input
	private boolean clearText; // Flag to clear text on next input (after operator press)
	private int lastOp; // Stores the pending operation code
	private DecimalFormat calcPattern; // Output formatter

	// Operation Constants
	private static final int OP_NONE = 0;
	private static final int OP_ADD = 1;
	private static final int OP_SUB = 2;
	private static final int OP_MUL = 3;
	private static final int OP_DIV = 4;

	public Calculator() {
		super("Calculator");
		setSize(300, 400); // Window dimensions

		// --- MENU BAR SETUP ---
		MenuBar mnubar = new MenuBar();
		setMenuBar(mnubar);

		Menu mnuFile = new Menu("File", true);
		mnubar.add(mnuFile);
		MenuItem mnuFileExit = new MenuItem("Exit");
		mnuFile.add(mnuFileExit);

		Menu mnuEdit = new Menu("Edit", true);
		mnubar.add(mnuEdit);
		MenuItem mnuEditclear = new MenuItem("Clear");
		mnuEdit.add(mnuEditclear);
		mnuEdit.insertSeparator(1);
		MenuItem mnuEditCopy = new MenuItem("Copy");
		mnuEdit.add(mnuEditCopy);
		MenuItem mnuEditPaste = new MenuItem("Paste");
		mnuEdit.add(mnuEditPaste);

		Menu mnuAbout = new Menu("About", true);
		mnubar.add(mnuAbout);
		MenuItem mnuAboutCalc = new MenuItem("About Calculator");
		mnuAbout.add(mnuAboutCalc);

		// Add Listeners for Menu Items
		mnuFileExit.addActionListener(this);
		mnuEditclear.addActionListener(this);
		mnuEditCopy.addActionListener(this);
		mnuEditPaste.addActionListener(this);
		mnuAboutCalc.addActionListener(this);

		// Set Action Commands for Menu
		mnuFileExit.setActionCommand("exit");
		mnuEditclear.setActionCommand("clear");
		mnuEditCopy.setActionCommand("copy");
		mnuEditPaste.setActionCommand("paste");
		mnuAboutCalc.setActionCommand("about");

		// --- GUI COMPONENT SETUP ---
		lcd = new TextField(20);
		lcd.setEditable(false); // Display only
		lcd.setFont(new Font("Dialog", Font.BOLD, 18));

		keypad = new Panel();
		keys = new Button[16]; // 16 keys total

		// Logic initialization
		first = true;
		op1 = 0.0;
		clearText = true;
		lastOp = OP_NONE;
		calcPattern = new DecimalFormat("########.########");

		// initialize keys array
		for (int i = 0; i <= 15; i++) {
			keys[i] = new Button();
			keys[i].addActionListener(this);
			keys[i].setFont(new Font("Dialog", Font.BOLD, 14));
		}

		// Assign special labels
		keys[10].setLabel("/");
		keys[11].setLabel("*");
		keys[12].setLabel("-");
		keys[13].setLabel("+");
		keys[14].setLabel("=");
		keys[15].setLabel(".");

		setLayout(new BorderLayout(10, 10)); // Main layout

		// Numeric Keypad Layout (4x4 Grid)
		keypad.setLayout(new GridLayout(4, 4, 10, 10));

		// Row 1: 7, 8, 9, /
		for (int i = 7; i <= 9; i++) {
			keys[i].setLabel(String.valueOf(i));
			keypad.add(keys[i]);
		}
		keypad.add(keys[10]); // /

		// Row 2: 4, 5, 6, *
		for (int i = 4; i <= 6; i++) {
			keys[i].setLabel(String.valueOf(i));
			keypad.add(keys[i]);
		}
		keypad.add(keys[11]); // *

		// Row 3: 1, 2, 3, -
		for (int i = 1; i <= 3; i++) {
			keys[i].setLabel(String.valueOf(i));
			keypad.add(keys[i]);
		}
		keypad.add(keys[12]); // -

		// Row 4: 0, ., =, +
		keys[0].setLabel("0");
		keypad.add(keys[0]);
		keypad.add(keys[15]); // .
		keypad.add(keys[14]); // =
		keypad.add(keys[13]); // +

		add(lcd, BorderLayout.NORTH);
		add(keypad, BorderLayout.CENTER);

		setVisible(true);

		// Handle Window Closing
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		String arg = e.getActionCommand();
		// --- MENU COMMANDS ---
		if (arg.equals("exit")) {
			System.exit(0);
		} else if (arg.equals("clear")) {
			clearText = true;
			first = true;
			op1 = 0.0;
			lastOp = OP_NONE;
			lcd.setText("");
		} else if (arg.equals("copy")) {
			Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
			StringSelection contents = new StringSelection(lcd.getText());
			cb.setContents(contents, null);
		} else if (arg.equals("paste")) {
			Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable content = cb.getContents(this);
			try {
				String ss = (String) content.getTransferData(DataFlavor.stringFlavor);
				lcd.setText(calcPattern.format(Double.parseDouble(ss)));
			} catch (Exception exc) {
				lcd.setText("");
			}
		} else if (arg.equals("about")) {
			// Placeholder for about box
			lcd.setText("Nicholas Larkin Buchanan");
		}
		// --- CALCULATOR BUTTON COMMANDS ---
		else {
			// Check if input is a digit or decimal
			if ((arg.charAt(0) >= '0' && arg.charAt(0) <= '9') || arg.equals(".")) {
				if (clearText) {
					lcd.setText(arg);
					clearText = false;
				} else {
					lcd.setText(lcd.getText() + arg);
				}
			}
			// Check if input is an operator
			else if (arg.equals("/") || arg.equals("*") || arg.equals("-") || arg.equals("+")) {
				// If we aren't first, calculate pending result first
				if (!first) {
					calculateResult(); // Calculate pending op before new one
				} else {
					// Parse current screen value
					try {
						op1 = Double.parseDouble(lcd.getText());
					} catch (Exception ex) {
						op1 = 0.0;
					}
				}

				// Store operator
				if (arg.equals("/"))
					lastOp = OP_DIV;
				else if (arg.equals("*"))
					lastOp = OP_MUL;
				else if (arg.equals("-"))
					lastOp = OP_SUB;
				else if (arg.equals("+"))
					lastOp = OP_ADD;

				clearText = true;
				first = false;
			}
			// Check if input is Equals
			else if (arg.equals("=")) {
				calculateResult();
				first = true; // Reset sequence since calculation complete
				clearText = true;
				lastOp = OP_NONE;
			}
		}
	}

	// method to perform the math based on lastOp
	private void calculateResult() {
		double op2 = 0.0;
		try {
			op2 = Double.parseDouble(lcd.getText());
		} catch (Exception ex) {
			return;
		}

		double result = op1;

		switch (lastOp) {
			case OP_ADD:
				result = op1 + op2;
				break;
			case OP_SUB:
				result = op1 - op2;
				break;
			case OP_MUL:
				result = op1 * op2;
				break;
			case OP_DIV:
				if (op2 != 0) {
					result = op1 / op2;
				} else {
					lcd.setText("Error");
					return;
				}
				break;
		}

		// Update display and op1 for chained calculations
		lcd.setText(calcPattern.format(result));
		op1 = result;
	}

	public static void main(String[] args) {
		new Calculator();
	}
}

/* - The program listens for button clicks via the ActionListener interface.
 * - When a number is pressed, it is appended to the display (lcd).
 * - When an operator is pressed, the current number is stored in 'op1', the
 * operator type is saved 'lastOp',
 * and the display is flagged to clear upon the next number input.
 * - When '=' is pressed, the pending operation is performed between 'op1' and
 * the current screen number,
 * and the result is displayed.
 */