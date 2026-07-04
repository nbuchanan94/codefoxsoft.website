
/*
 * Nicholas Larkin Buchanan
 * 2011-02-05
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

public class StringArrayDemo extends JFrame {
    private JTextField countField;
    private JTextArea outputArea;
    private String[] nameArray;
    private int currentIndex = 0;
    private int totalCount = 0;

    // Components for entry phase
    private JPanel entryPanel;
    private JLabel entryLabel;
    private JTextField entryField;
    private JButton nextButton;
    private JPanel setSizePanel;

    public StringArrayDemo() {
        super("StringArrayDemo - Nick Buchanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout(10, 10));

        // Grid/Data Entry Theme
        getContentPane().setBackground(new Color(240, 240, 240));

        // Panel 1: Set Size
        setSizePanel = new JPanel();
        setSizePanel.add(new JLabel("How many strings?"));
        countField = new JTextField(5);
        JButton setButton = new JButton("Start");
        setSizePanel.add(countField);
        setSizePanel.add(setButton);
        add(setSizePanel, BorderLayout.NORTH);

        // Panel 2: Entry (Hidden initially)
        entryPanel = new JPanel(new BorderLayout());
        entryPanel.setVisible(false);
        entryLabel = new JLabel("Enter String 1:", SwingConstants.CENTER);
        entryField = new JTextField();
        nextButton = new JButton("Next");
        entryPanel.add(entryLabel, BorderLayout.NORTH);
        entryPanel.add(entryField, BorderLayout.CENTER);
        entryPanel.add(nextButton, BorderLayout.SOUTH);

        // We add entryPanel to NORTH too, replacing setSizePanel later?
        // Better: Use a CardLayout or just swap visibility.
        // For simplicity, add to CENTER top.
        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.add(entryPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        centerContainer.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(centerContainer, BorderLayout.CENTER);

        setButton.addActionListener(e -> startEntry());
        nextButton.addActionListener(e -> nextEntry());
        entryField.addActionListener(e -> nextEntry());

        setVisible(true);
    }

    private void startEntry() {
        try {
            totalCount = Integer.parseInt(countField.getText());
            if (totalCount <= 0)
                return;
            nameArray = new String[totalCount];
            currentIndex = 0;

            setSizePanel.setVisible(false);
            entryPanel.setVisible(true);
            updateEntryLabel();
            entryField.requestFocus();

            outputArea.setText("Array initialized with size " + totalCount + "\n");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Number");
        }
    }

    private void updateEntryLabel() {
        entryLabel.setText("Enter String " + (currentIndex + 1) + "/" + totalCount + ":");
    }

    private void nextEntry() {
        String input = entryField.getText();
        nameArray[currentIndex] = input;
        currentIndex++;
        entryField.setText("");

        if (currentIndex >= totalCount) {
            finish();
        } else {
            updateEntryLabel();
        }
    }

    private void finish() {
        entryPanel.setVisible(false);
        outputArea.append("\n--- Results ---\n");
        for (int j = 0; j < totalCount; j++) {
            outputArea.append("Index " + j + ": " + nameArray[j] + "\n");
        }

        // Reset option?
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            setSizePanel.setVisible(true);
            getContentPane().remove(resetButton); // Remove self
            outputArea.setText("");
            revalidate();
            repaint();
        });
        add(resetButton, BorderLayout.SOUTH);
        revalidate();
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(StringArrayDemo::new);
        }
    }

    public static void runConsole(String[] args) {
        System.out.println("System: Application Start");
        Scanner kb = new Scanner(System.in);

        System.out.print("How many times run the loop? ");
        int innum = kb.nextInt();
        kb.nextLine(); // consume newline

        System.out.println("DEBUG: Array size set to " + innum);
        String[] name = new String[innum];

        System.out.println("ENTER STRING THEN PRESS ENTER! (Repeat " + innum + " times)");

        for (int i = 0; i < innum; i++) {
            System.out.print("ENTER A STRING (" + (i + 1) + "/" + innum + "): ");
            name[i] = kb.nextLine();
        }

        System.out.println("--- Results ---");
        for (int j = 0; j < innum; j++) {
            System.out.println("Index " + j + ": " + name[j]);
        }
        System.out.println("System: Application End");
    }
}
