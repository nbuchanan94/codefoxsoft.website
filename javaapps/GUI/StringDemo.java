
/*
 * Nicholas Larkin Buchanan
 * March 15, 2011
 *
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StringDemo extends JFrame {
    private JTextArea outputArea;

    public StringDemo() {
        super("StringDemo - Nick Buchanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BorderLayout());

        // Mirror/Silver Theme
        getContentPane().setBackground(Color.LIGHT_GRAY);

        JButton runButton = new JButton("Run Palindrome Demo");
        runButton.setFont(new Font("Arial", Font.BOLD, 16));
        runButton.setBackground(Color.WHITE);
        add(runButton, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(240, 240, 240));
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        runButton.addActionListener(e -> runDemo());

        setVisible(true);
    }

    private void runDemo() {
        outputArea.setText("");
        String palindrome = "Dot saw I was Tod";
        outputArea.append("Original String: " + palindrome + "\n");

        int len = palindrome.length();
        char[] tempCharArray = new char[len];
        char[] charArray = new char[len];

        // put original string in an array of chars
        for (int i = 0; i < len; i++) {
            tempCharArray[i] = palindrome.charAt(i);
        }

        // reverse array of chars
        for (int j = 0; j < len; j++) {
            charArray[j] = tempCharArray[len - 1 - j];
        }

        String reversePalindrome = new String(charArray);
        outputArea.append("Reversed String: " + reversePalindrome + "\n");
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(StringDemo::new);
        }
    }

    public static void runConsole(String[] args) {
        // Original logic
        String palindrome = "Dot saw I was Tod";
        System.out.println("Original String: " + palindrome);
        int len = palindrome.length();
        char[] tempCharArray = new char[len];
        char[] charArray = new char[len];

        for (int i = 0; i < len; i++) {
            tempCharArray[i] = palindrome.charAt(i);
        }

        for (int j = 0; j < len; j++) {
            charArray[j] = tempCharArray[len - 1 - j];
        }

        String reversePalindrome = new String(charArray);
        System.out.println("Reversed String: " + reversePalindrome);
    }
}
