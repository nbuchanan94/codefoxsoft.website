
// Nicholas Buchanan
// September 15, 2010
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class MorseCodeTranslator extends JFrame {

    private JTextArea inputArea;
    private JTextArea outputArea;
    // Lookup Tables
    private static final char[] ALPHABET = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', ' '
    };

    private static final String[] MORSE_CODES = {
            " * - ", " - * * * ", " - * - * ", " - * * ", " * ", " * * - * ", " - - *",
            " * * * * ", " * * ", " * - - - ", " - * - ", " * - * * ", " - - ", " - * ",
            " - - - ", " * - - * ", " - - * - ", " * - * ", " * * * ", " - ", " * * -",
            " * * * -", " * - - ", " - * * -", " - * - - ", " - - * * ", "\t"
    };

    public MorseCodeTranslator() {
        initGUI();
    }

    private void initGUI() {
        setTitle("MorseCodeTranslator - Nick Buchanan");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        // Telegraph Theme (Sepia)
        Color paper = new Color(244, 226, 198);
        Color ink = new Color(75, 54, 33); // Sepia Brown

        JPanel inP = new JPanel(new BorderLayout());
        inP.setBorder(BorderFactory.createTitledBorder("Input Text"));
        inputArea = new JTextArea();
        inputArea.setBackground(paper);
        inputArea.setForeground(ink);
        inputArea.setFont(new Font("Courier New", Font.PLAIN, 18));
        inP.add(new JScrollPane(inputArea), BorderLayout.CENTER);
        JButton transBtn = new JButton("TRANSLATE TO MORSE");
        transBtn.addActionListener(e -> translateGUI());
        inP.add(transBtn, BorderLayout.SOUTH);
        add(inP);

        JPanel outP = new JPanel(new BorderLayout());
        outP.setBorder(BorderFactory.createTitledBorder("Morse Code Output"));
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(paper);
        outputArea.setForeground(ink);
        outputArea.setFont(new Font("Monospaced", Font.BOLD, 22));
        outP.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(outP);

        setVisible(true);
    }

    private void translateGUI() {
        String in = inputArea.getText().toLowerCase();
        outputArea.setText(translate(in));
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new MorseCodeTranslator());
        }
    }

    public static String translate(String input) {
        StringBuilder finalString = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int index = -1;

            // Find index of character in alphabet array
            for (int k = 0; k < ALPHABET.length; k++) {
                if (ALPHABET[k] == c) {
                    index = k;
                    break;
                }
            }

            if (index != -1) {
                finalString.append(MORSE_CODES[index]);
            } else {
                finalString.append("? ");
            }
        }

        return finalString.toString();
    }

    public static void runConsole(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("ENTER STRING: ");
        String input = scanner.nextLine();

        // Clean input
        input = input.toLowerCase();

        System.out.println("Translation: " + translate(input));

        scanner.close();
    }
}

/*
 * This program translates a user-inputted string into Morse Code (represented
 * by * and -).
 * Used "Parallel Arrays" (Lookup Tables).
 */
