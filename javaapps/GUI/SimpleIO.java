
/*
 * Nicholas Larkin Buchanan
 *
 * 12/28/2011
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

public class SimpleIO extends JFrame {
    private JTextField nameField;
    private JTextField ageField;
    private JTextArea outputArea;

    public SimpleIO() {
        super("SimpleIO - Nick Buchanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout(10, 10));

        // Terminal Theme
        getContentPane().setBackground(Color.BLACK);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBackground(Color.BLACK);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        nameField = new JTextField();

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setForeground(Color.WHITE);
        ageField = new JTextField();

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(ageLabel);
        inputPanel.add(ageField);

        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.setBackground(Color.BLACK);
        topContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topContainer.add(inputPanel, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(Color.WHITE);
        submitButton.setForeground(Color.BLACK);
        submitButton.addActionListener(e -> processInput());
        topContainer.add(submitButton, BorderLayout.SOUTH);

        add(topContainer, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(Color.BLACK);
        outputArea.setForeground(Color.WHITE);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void processInput() {
        String name = nameField.getText();
        String age = ageField.getText();

        outputArea.setText("");
        outputArea.append("Hello " + name + "!\n");
        outputArea.append("You are " + age + " years old.\n");
    }

    // Original Static Helper Methods preserved for compatibility
    public static void print(String message) {
        System.out.println(message);
    }

    public static String input(String prompt) {
        System.out.print(prompt);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(SimpleIO::new);
        }
    }

    // Original Main functionality
    public static void runConsole(String[] args) {
        String name = input("Enter your name: ");
        print("Hello, " + name + "!");

        String age = input("Enter your age: ");
        print("You are " + age + " years old.");
    }
}
