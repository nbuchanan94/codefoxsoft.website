
/*
 * Nicholas Buchanan
 * January 2, 2010
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlayerNameSubstrings extends JFrame {
    private JTextField inputField;
    private JTextField firstNameField;
    private JTextField lastNameField;

    public PlayerNameSubstrings() {
        super("PlayerNameSubstrings - Nick Buchanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 250);
        setLayout(new GridLayout(4, 1, 10, 10));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Identity/Blue Theme
        getContentPane().setBackground(new Color(230, 240, 255));

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setOpaque(false);
        inputPanel.add(new JLabel("Full Name:"));
        inputField = new JTextField("Clinton Portis", 15);
        JButton sliceButton = new JButton("Slice Name");
        sliceButton.setBackground(new Color(65, 105, 225));
        sliceButton.setForeground(Color.WHITE);

        inputPanel.add(inputField);
        inputPanel.add(sliceButton);
        add(inputPanel);

        JPanel firstPanel = new JPanel(new FlowLayout());
        firstPanel.setOpaque(false);
        firstPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField(15);
        firstNameField.setEditable(false);
        firstPanel.add(firstNameField);
        add(firstPanel);

        JPanel lastPanel = new JPanel(new FlowLayout());
        lastPanel.setOpaque(false);
        lastPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField(15);
        lastNameField.setEditable(false);
        lastPanel.add(lastNameField);
        add(lastPanel);

        JLabel infoLabel = new JLabel("Demonstrating substring()", SwingConstants.CENTER);
        infoLabel.setForeground(Color.GRAY);
        add(infoLabel);

        sliceButton.addActionListener(e -> processName());
        inputField.addActionListener(e -> processName());

        setVisible(true);
    }

    private void processName() {
        String fullName = inputField.getText().trim();
        int spaceIndex = fullName.indexOf(" ");

        if (spaceIndex != -1) {
            String firstName = fullName.substring(0, spaceIndex);
            String lastName = fullName.substring(spaceIndex + 1);
            firstNameField.setText(firstName);
            lastNameField.setText(lastName);
        } else {
            firstNameField.setText("Error");
            lastNameField.setText("No space found");
        }
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(PlayerNameSubstrings::new);
        }
    }

    public static void runConsole(String[] args) {
        // Original Console Logic
        String player1 = "Clinton Portis";
        String player2 = "Jason Campbell";
        String player3 = "Chris Cooley";

        System.out.println("--- NFL Player Name Extractor ---");
        System.out.println("Demonstrating Java's substring() method\n");

        processAndPrintCLI(player1);
        processAndPrintCLI(player2);
        processAndPrintCLI(player3);
    }

    private static void processAndPrintCLI(String fullName) {
        int spaceIndex = fullName.indexOf(" ");

        if (spaceIndex != -1) {
            String firstName = fullName.substring(0, spaceIndex);
            String lastName = fullName.substring(spaceIndex + 1);

            System.out.println("Full Name:  " + fullName);
            System.out.println("First Name: " + firstName);
            System.out.println("Last Name:  " + lastName);
            System.out.println("-----------------------------");
        } else {
            System.out.println("Could not parse: " + fullName);
        }
    }
}
