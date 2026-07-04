// Nicholas Buchanan
// 2/22/2011

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class StudentRecordApp extends JFrame implements ActionListener {
	// UI Components
	private JTextField firstName, middleInitial, lastName, grade, dob;
	private JTextArea displayArea;
	private JButton nextButton;

	// File Menu Items
	private JMenuItem openItem, saveItem, exitItem;

	public StudentRecordApp() {
		super("Student Record Keeper");
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));

		// --- Menu Bar ---
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");

		openItem = new JMenuItem("Open");
		openItem.addActionListener(this);
		fileMenu.add(openItem);

		saveItem = new JMenuItem("Save");
		saveItem.addActionListener(this);
		fileMenu.add(saveItem);

		fileMenu.addSeparator();

		exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(this);
		fileMenu.add(exitItem);

		menuBar.add(fileMenu);
		setJMenuBar(menuBar);

		// --- Input Panel (Center/Top) ---
		JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
		inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		inputPanel.add(new JLabel("First Name:"));
		firstName = new JTextField();
		inputPanel.add(firstName);

		inputPanel.add(new JLabel("Middle Initial:"));
		middleInitial = new JTextField();
		inputPanel.add(middleInitial);

		inputPanel.add(new JLabel("Last Name:"));
		lastName = new JTextField();
		inputPanel.add(lastName);

		inputPanel.add(new JLabel("Grade:"));
		grade = new JTextField();
		inputPanel.add(grade);

		inputPanel.add(new JLabel("DOB (mm/dd/yyyy):"));
		dob = new JTextField();
		inputPanel.add(dob);

		// --- Control Panel (South of Input) ---
		JPanel controlPanel = new JPanel();
		nextButton = new JButton("Add Entry");
		nextButton.addActionListener(this);
		controlPanel.add(nextButton);

		// Container for Input + Control
		JPanel topContainer = new JPanel(new BorderLayout());
		topContainer.add(inputPanel, BorderLayout.CENTER);
		topContainer.add(controlPanel, BorderLayout.SOUTH);

		add(topContainer, BorderLayout.NORTH);

		// --- Display Area (Center) ---
		displayArea = new JTextArea();
		displayArea.setEditable(false);
		displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JScrollPane scrollPane = new JScrollPane(displayArea);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Current Records"));
		add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == nextButton) {
			addEntry();
		} else if (source == saveItem) {
			saveData();
		} else if (source == openItem) {
			loadData();
		} else if (source == exitItem) {
			System.exit(0);
		}
	}

	private void addEntry() {
		String f = firstName.getText().trim();
		String m = middleInitial.getText().trim();
		String l = lastName.getText().trim();
		String g = grade.getText().trim();
		String d = dob.getText().trim();

		if (f.isEmpty() || l.isEmpty()) {
			JOptionPane.showMessageDialog(this, "First and Last Name are required.");
			return;
		}

		String record = f + "\t" + m + "\t" + l + "\t" + g + "\t" + d;
		displayArea.append(record + "\n");

		// Clear fields for next entry
		firstName.setText("");
		middleInitial.setText("");
		lastName.setText("");
		grade.setText("");
		dob.setText("");
		firstName.requestFocus();
	}

	private void saveData() {
		try {
			File f = new File("student_data.txt");
			FileWriter fw = new FileWriter(f);
			PrintWriter pw = new PrintWriter(fw);

			pw.print(displayArea.getText()); // Write exactly what is in the text area

			pw.close();
			fw.close();
			JOptionPane.showMessageDialog(this, "Saved to " + f.getAbsolutePath());
		} catch (IOException ioe) {
			JOptionPane.showMessageDialog(this, "Error saving file: " + ioe.getMessage());
		}
	}

	private void loadData() {
		try {
			File f = new File("student_data.txt");
			if (!f.exists()) {
				JOptionPane.showMessageDialog(this, "File not found: student_data.txt");
				return;
			}

			Scanner sc = new Scanner(f);
			displayArea.setText(""); // Clear current display

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				displayArea.append(line + "\n");
			}
			sc.close();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error loading file.");
		}
	}

	public static void main(String[] args) {
		new StudentRecordApp();
	}
}

/*
 * 1. Data Entry: Fields for Name, Grade, and DOB.
 * 2. Accumulation: 'Add Entry' button moves data to the main display list.
 * 3. Persistence:
 * - File > Save: Writes the current list to 'student_data.txt'.
 * - File > Open: Loads the list from 'student_data.txt'.
 *
 */