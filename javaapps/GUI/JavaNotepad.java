// Nicholas Buchanan
// August 8 2012

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class JavaNotepad extends JFrame implements ActionListener {
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JFileChooser fileChooser;
	private File currentFile;

	public JavaNotepad() {
		super("Java Notepad");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// -- Setup Components --
		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		scrollPane = new JScrollPane(textArea);
		add(scrollPane, BorderLayout.CENTER);

		fileChooser = new JFileChooser();

		// -- Setup Menu Bar --
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// 1. File Menu
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		addMenuItem(fileMenu, "New");
		addMenuItem(fileMenu, "Open");
		addMenuItem(fileMenu, "Save");
		addMenuItem(fileMenu, "Save As");
		fileMenu.addSeparator();
		addMenuItem(fileMenu, "Print");
		fileMenu.addSeparator();
		addMenuItem(fileMenu, "Exit");

		// 2. View Menu
		JMenu viewMenu = new JMenu("View");
		menuBar.add(viewMenu);

		JCheckBoxMenuItem wordWrapItem = new JCheckBoxMenuItem("Word Wrap");
		wordWrapItem.addActionListener(e -> {
			boolean wrap = wordWrapItem.isSelected();
			textArea.setLineWrap(wrap);
			textArea.setWrapStyleWord(wrap);
		});
		viewMenu.add(wordWrapItem);

		// 3. Help Menu
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		addMenuItem(helpMenu, "About");

		setVisible(true);
	}

	// Helper to add menu items
	private void addMenuItem(JMenu menu, String label) {
		JMenuItem item = new JMenuItem(label);
		item.addActionListener(this);
		item.setActionCommand(label); // Command is the label text
		menu.add(item);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		switch (command) {
			case "New":
				textArea.setText("");
				currentFile = null;
				setTitle("Java Notepad - Untitled");
				break;

			case "Open":
				openFile();
				break;

			case "Save":
				saveFile(false); // False = Save, not Save As (unless new)
				break;

			case "Save As":
				saveFile(true); // True = Force Save As
				break;

			case "Print":
				try {
					boolean complete = textArea.print();
					if (complete) {
						JOptionPane.showMessageDialog(this, "Printing Complete", "Result",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception printEx) {
					JOptionPane.showMessageDialog(this, "Printing Failed: " + printEx.getMessage());
				}
				break;

			case "Exit":
				System.exit(0);
				break;

			case "About":
				JOptionPane.showMessageDialog(this,
						"Credit to Nicholas Thorne Larkin Buchanan 2012",
						"About Java Notepad",
						JOptionPane.INFORMATION_MESSAGE);
				break;
		}
	}

	private void openFile() {
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try {
				// Using Scanner to read file content
				Scanner reader = new Scanner(selectedFile);
				StringBuilder content = new StringBuilder();
				while (reader.hasNextLine()) {
					content.append(reader.nextLine()).append("\n");
				}
				reader.close();

				textArea.setText(content.toString());
				currentFile = selectedFile;
				setTitle("Java Notepad - " + currentFile.getName());
			} catch (FileNotFoundException ex) {
				JOptionPane.showMessageDialog(this, "File not found error.");
			}
		}
	}

	private void saveFile(boolean forceSaveAs) {
		if (currentFile == null || forceSaveAs) {
			int result = fileChooser.showSaveDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				currentFile = fileChooser.getSelectedFile();
			} else {
				return; // Cancelled
			}
		}

		try {
			FileWriter writer = new FileWriter(currentFile);
			writer.write(textArea.getText());
			writer.close();
			setTitle("Java Notepad - " + currentFile.getName());
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "Error saving file.");
		}
	}

	public static void main(String[] args) {
		// Run on EDT
		SwingUtilities.invokeLater(() -> new JavaNotepad());
	}
}

/*
 * A simple text editor implementation using Java Swing.
 *
 */