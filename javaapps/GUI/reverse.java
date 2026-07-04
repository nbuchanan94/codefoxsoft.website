
/*
 * Nicholas Larkin Buchanan
 * October 9, 2012
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

public class reverse extends JFrame {
    private JTextField inputField;
    private JLabel resultLabel;

    public reverse() {
        super("reverse - Nick Buchanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        setLayout(new BorderLayout(10, 10));

        // Reflection/Glass Theme (Light Blue/White)
        getContentPane().setBackground(new Color(240, 248, 255));

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.add(new JLabel("Enter Text:"));
        inputField = new JTextField(20);
        JButton reverseButton = new JButton("Reverse");
        topPanel.add(inputField);
        topPanel.add(reverseButton);
        add(topPanel, BorderLayout.NORTH);

        resultLabel = new JLabel("Result will appear here", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Serif", Font.ITALIC, 20));
        add(resultLabel, BorderLayout.CENTER);

        reverseButton.addActionListener(e -> doReverse());
        inputField.addActionListener(e -> doReverse());

        setVisible(true);
    }

    private void doReverse() {
        String text = inputField.getText();
        StringBuffer sb = new StringBuffer(text);
        resultLabel.setText(sb.reverse().toString());
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(reverse::new);
        }
    }

    public static void runConsole(String[] args) {
        Scanner kbRead = new Scanner(System.in);
        System.out.print("TYPE WHAT YOU WANT BACKWARDS: ");
        String string = kbRead.nextLine();
        StringBuffer reversedText = new StringBuffer(string);
        System.out.println("Reversed Result: " + reversedText.reverse());
    }
}
