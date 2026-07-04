
/*
 * Nicholas Buchanan
 * September 21 2010
 *
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

public class SimpleLoop extends JFrame {
    private JTextField inputField;
    private JTextArea outputArea;

    public SimpleLoop() {
        super("SimpleLoop - Nick Buchanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout(10, 10));

        // Pink/Repeater Theme
        getContentPane().setBackground(new Color(255, 192, 203));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(255, 192, 203));
        topPanel.add(new JLabel("Loop Count:"));
        inputField = new JTextField(5);
        JButton runButton = new JButton("Run Loop");
        topPanel.add(inputField);
        topPanel.add(runButton);

        add(topPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        runButton.addActionListener(e -> runLoop());
        inputField.addActionListener(e -> runLoop());

        setVisible(true);
    }

    private void runLoop() {
        outputArea.setText("");
        try {
            int count = Integer.parseInt(inputField.getText());
            for (int j = 0; j < count; j++) {
                outputArea.append("Design & Logic is Awesome!\n");
            }
        } catch (NumberFormatException e) {
            outputArea.setText("Please enter a valid integer.");
        }
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(SimpleLoop::new);
        }
    }

    public static void runConsole(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.println("how many times run the loop?");
        int innum = kb.nextInt();

        for (int j = 0; j < innum; j++) {
            System.out.println("Design & Logic is Awesome!");
        }
    }
}
