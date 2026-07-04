
/*
 * Nicholas Larkin Buchanan
 * January 20, 2011
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

public class nameLength extends JFrame {
    private JTextField nameField;
    private JLabel lengthLabel;
    private JLabel reversedLabel;

    public nameLength() {
        super("nameLength - Nick Buchanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,400);
        setLayout(new GridLayout(4, 1, 10, 10));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Bio/Green Theme
        getContentPane().setBackground(new Color(230, 255, 230));

        JPanel startPanel = new JPanel(new FlowLayout());
        startPanel.setOpaque(false);
        startPanel.add(new JLabel("Name:"));
        nameField = new JTextField(15);
        startPanel.add(nameField);
        add(startPanel);

        JButton analyzeButton = new JButton("Analyze");
        analyzeButton.setBackground(new Color(0, 128, 0));
        analyzeButton.setForeground(Color.WHITE);
        add(analyzeButton);

        lengthLabel = new JLabel("Length: -");
        add(lengthLabel);

        reversedLabel = new JLabel("Reversed: -");
        add(reversedLabel);

        analyzeButton.addActionListener(e -> analyze());
        nameField.addActionListener(e -> analyze());

        setVisible(true);
    }

    private void analyze() {
        String name = nameField.getText();
        lengthLabel.setText("Length: " + name.length());

        StringBuffer sb = new StringBuffer(name);
        reversedLabel.setText("Reversed: " + sb.reverse());
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(nameLength::new);
        }
    }

    public static void runConsole(String[] args) {
        Scanner kbReader = new Scanner(System.in);
        System.out.println("What is your name? ");
        String yourName = kbReader.next();

        System.out.println("Name: " + yourName);
        System.out.println("Length: " + yourName.length());

        StringBuffer sb = new StringBuffer(yourName);
        System.out.println("Reversed: " + sb.reverse());
    }
}
