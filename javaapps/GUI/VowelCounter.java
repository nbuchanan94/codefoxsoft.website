// Nicholas Buchanan
// 12/1/10
// 3rd period

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class VowelCounter extends JFrame {

    private JTextField inputField;
    private JLabel aL, eL, iL, oL, uL;

    public VowelCounter() {
        initGUI();
    }

    private void initGUI() {
        setTitle("VowelCounter - Nick Buchanan");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Colorful Alphabet Theme
        getContentPane().setBackground(Color.WHITE);

        JPanel top = new JPanel(new BorderLayout(5, 5));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add some padding
        top.add(new JLabel("Enter Phrase:"), BorderLayout.WEST);
        inputField = new JTextField(); // No fixed columns, let Center expand
        top.add(inputField, BorderLayout.CENTER);
        JButton btn = new JButton("Count Vowels");
        btn.addActionListener(e -> count());
        top.add(btn, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        JPanel mid = new JPanel(new GridLayout(1, 5, 5, 5));
        aL = createIL("A", Color.RED);
        eL = createIL("E", Color.ORANGE);
        iL = createIL("I", Color.YELLOW);
        oL = createIL("O", Color.GREEN);
        uL = createIL("U", Color.BLUE);

        mid.add(aL);
        mid.add(eL);
        mid.add(iL);
        mid.add(oL);
        mid.add(uL);
        add(mid, BorderLayout.CENTER);

        setVisible(true);
    }

    private JLabel createIL(String t, Color c) {
        JLabel l = new JLabel("<html><center>" + t + "<br>0</center></html>", SwingConstants.CENTER);
        l.setOpaque(true);
        l.setBackground(c);
        l.setFont(new Font("Arial", Font.BOLD, 40));
        return l;
    }

    private void updateIL(JLabel l, String charStr, int count) {
        l.setText("<html><center>" + charStr + "<br>" + count + "</center></html>");
    }

    private void count() {
        String rick = inputField.getText();
        rick = rick.toLowerCase();

        int a = 0;
        int e = 0;
        int i = 0;
        int o = 0;
        int u = 0;

        for (int s = 0; s < rick.length(); s++) {
            char character = rick.charAt(s);
            if (character == 'a')
                a++;
            else if (character == 'e')
                e++;
            else if (character == 'i')
                i++;
            else if (character == 'o')
                o++;
            else if (character == 'u')
                u++;
        }

        updateIL(aL, "A", a);
        updateIL(eL, "E", e);
        updateIL(iL, "I", i);
        updateIL(oL, "O", o);
        updateIL(uL, "U", u);
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new VowelCounter());
        }
    }

    public static void runConsole(String[] args) {
        Scanner will = new Scanner(System.in);
        System.out.println("What is your phrase?");

        String rick = will.nextLine();
        // 1. convert the string to lower case
        rick = rick.toLowerCase();

        System.out.println("Analyzing: " + rick);

        int a = 0;
        int e = 0;
        int i = 0;
        int o = 0;
        int u = 0;

        // Single loop to iterate through the string once
        for (int s = 0; s < rick.length(); s++) {
            char character = rick.charAt(s);

            if (character == 'a') {
                a++;
            } else if (character == 'e') {
                e++;
            } else if (character == 'i') {
                i++;
            } else if (character == 'o') {
                o++;
            } else if (character == 'u') {
                u++;
            }
        }

        System.out.println("This is your vowel count:");
        System.out.println("a: " + a + "\te: " + e + "\ti: " + i + "\to: " + o + "\tu: " + u);

        will.close();
    }
}

/*
 * 1. Input: The program asks the user to enter a phrase.
 * 2. Processing:
 * - It converts the entire phrase to lowercase to ensure case-insensitive
 * counting.
 * - It loops through the string character by character.
 * - If a vowel ('a', 'e', 'i', 'o', 'u') is found, the corresponding counter is
 * incremented.
 * 3. Output: The final count for each vowel is displayed to the user.
 */
