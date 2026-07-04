// Nicholas Buchanan
// November 12 2011

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class CharacterCounter extends JFrame {

    private JTextField phraseField, charField;
    private JLabel countLabel;

    public CharacterCounter() {
        initGUI();
    }

    private void initGUI() {
        setTitle("CharacterCounter - Nick Buchanan");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Typewriter Theme (Beige)
        getContentPane().setBackground(new Color(245, 245, 220));

        JLabel title = new JLabel("CHARACTER COUNTER", SwingConstants.CENTER);
        title.setFont(new Font("Courier New", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(4, 1, 10, 10));
        center.setOpaque(false);
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        center.add(new JLabel("Type Phrase:"));
        phraseField = new JTextField();
        phraseField.setFont(new Font("Courier New", Font.PLAIN, 14));
        center.add(phraseField);

        JPanel sub = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sub.setOpaque(false);
        sub.add(new JLabel("Search Char:"));
        charField = new JTextField(2);
        charField.setFont(new Font("Courier New", Font.BOLD, 18));
        sub.add(charField);
        JButton btn = new JButton("Count");
        btn.addActionListener(e -> count());
        sub.add(btn);
        center.add(sub);

        add(center, BorderLayout.CENTER);

        countLabel = new JLabel("0", SwingConstants.CENTER);
        countLabel.setFont(new Font("Courier New", Font.BOLD, 80));
        countLabel.setForeground(new Color(139, 69, 19)); // Saddle Brown
        add(countLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void count() {
        String ph = phraseField.getText();
        String cStr = charField.getText();
        if (cStr.length() > 0) {
            char c = cStr.charAt(0);
            int n = occur(ph, c);
            countLabel.setText("" + n);
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new CharacterCounter());
        }
    }

    public static int occur(String in, char search) {
        int count = 0;
        for (int i = 0; i < in.length(); i++) {
            if (in.charAt(i) == search) {
                count++;
            }
        }
        return count;
    }

    public static void runConsole(String[] args) {
        Scanner kbReader = new Scanner(System.in);
        System.out.println(
                "Ever wonder how much a certain letter comes up in a sentance? Now is your chance to find out.");
        System.out.print("Enter phrase: ");
        String input = kbReader.nextLine();

        System.out.print("Enter character to count: ");
        String searchInput = kbReader.next();

        // Ensure we handle case where user enters more than 1 char (just take first)
        char searchChar = searchInput.charAt(0);

        System.out.println("Your character returns " + occur(input, searchChar));

        kbReader.close();
    }
}

/*
 * This program demonstrates basic string manipulation.
 * It prompts the user for a phrase and a search character,
 * then iterates through the string to count the number of times
 * that specific character appears.
 */
