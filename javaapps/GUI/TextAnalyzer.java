// Nicholas Buchanan
// 2011 (Refactored)
// TextAnalyzer.java

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class TextAnalyzer extends JFrame {

    private JTextArea inputArea;
    private JLabel wordStat, letterStat, vowelStat, punctStat;

    public TextAnalyzer() {
        initGUI();
    }

    private void initGUI() {
        setTitle("TextAnalyzer - Nick Buchanan");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Dashboard Theme
        getContentPane().setBackground(new Color(47, 79, 79)); // Dark Slate Gray

        // Top Input
        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createTitledBorder("Input Text"));
        inputArea = new JTextArea(5, 50);
        inputArea.setText("Enter text here...");
        top.add(new JScrollPane(inputArea), BorderLayout.CENTER);
        JButton btn = new JButton("ANALYZE DASHBOARD");
        btn.addActionListener(e -> analyze());
        top.add(btn, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        // Dashboard Panels
        JPanel dash = new JPanel(new GridLayout(2, 2, 10, 10));
        dash.setBackground(new Color(47, 79, 79));

        wordStat = createCard("Word Count");
        dash.add(wordStat);

        letterStat = createCard("Letter Count");
        dash.add(letterStat);

        vowelStat = createCard("Vowel Stats");
        dash.add(vowelStat);

        punctStat = createCard("Punctuation");
        dash.add(punctStat);

        add(dash, BorderLayout.CENTER);

        setVisible(true);
    }

    private JLabel createCard(String title) {
        JLabel l = new JLabel("<html><h2>" + title + "</h2><br>Waiting...</html>", SwingConstants.CENTER);
        l.setOpaque(true);
        l.setBackground(Color.WHITE);
        l.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return l;
    }

    private void analyze() {
        String input = inputArea.getText();
        String lowerInput = input.toLowerCase();

        // 1. Vowels
        int a = countChar(lowerInput, 'a');
        int e = countChar(lowerInput, 'e');
        int i = countChar(lowerInput, 'i');
        int o = countChar(lowerInput, 'o');
        int u = countChar(lowerInput, 'u');
        vowelStat.setText("<html><h2>Vowel Stats</h2>A: " + a + "<br>E: " + e + "<br>I: " + i + "<br>O: " + o
                + "<br>U: " + u + "</html>");

        // 2. Words
        String[] words = input.trim().split("\\s+");
        int numWords = (input.trim().isEmpty()) ? 0 : words.length;
        wordStat.setText("<html><h2>Word Count</h2><h1>" + numWords + "</h1></html>");

        // 3. Letters
        int numLetters = 0;
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c))
                numLetters++;
        }
        letterStat.setText("<html><h2>Letter Count</h2><h1>" + numLetters + "</h1></html>");

        // 4. Punct
        int comma = countChar(input, ',');
        int semi = countChar(input, ';');
        int colon = countChar(input, ':');
        int period = countChar(input, '.');
        int totalPunct = comma + semi + colon + period;
        punctStat.setText(
                "<html><h2>Punctuation</h2>Total: " + totalPunct + "<br>.: " + period + ", : " + comma + "</html>");
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new TextAnalyzer());
        }
    }

    public static int countChar(String str, char target) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == target) {
                count++;
            }
        }
        return count;
    }

    public static void runConsole(String[] args) {
        Scanner kbread = new Scanner(System.in);
        System.out.println("--- Text Analyzer ---");
        System.out.print("ENTER STRING: ");
        String input = kbread.nextLine();

        // Normalize for vowel counting
        String lowerInput = input.toLowerCase();

        // 1. Vowel Counts
        int a = countChar(lowerInput, 'a');
        int e = countChar(lowerInput, 'e');
        int i = countChar(lowerInput, 'i');
        int o = countChar(lowerInput, 'o');
        int u = countChar(lowerInput, 'u');

        System.out.println("\n--- Vowel Statistics ---");
        System.out.println("A's: " + a);
        System.out.println("E's: " + e);
        System.out.println("I's: " + i);
        System.out.println("O's: " + o);
        System.out.println("U's: " + u);

        // 2. Word Count
        String[] words = input.trim().split("\\s+");
        int numWords = (input.trim().isEmpty()) ? 0 : words.length;

        // 3. Letter Count (excluding whitespace)
        int numLetters = 0;
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                numLetters++;
            }
        }

        System.out.println("\n--- General Statistics ---");
        System.out.println("Total Words:   " + numWords);
        System.out.println("Total Letters: " + numLetters);

        // 4. Punctuation
        int comma = countChar(input, ',');
        int semi = countChar(input, ';');
        int colon = countChar(input, ':');
        int period = countChar(input, '.');
        int totalPunct = comma + semi + colon + period;

        System.out.println("\n--- Punctuation Statistics ---");
        System.out.println("Commas:      " + comma);
        System.out.println("Semicolons:  " + semi);
        System.out.println("Colons:      " + colon);
        System.out.println("Periods:     " + period);
        System.out.println("Total Found: " + totalPunct);

        kbread.close();
    }
}

/*
 * This program analyzes a user-provided string and generates a statistical
 * report. It calculates:
 * 1. Frequency of each vowel (case-insensitive).
 * 2. Total number of words.
 * 3. Total number of letters (ignoring spaces/punctuation).
 * 4. Frequency of specific punctuation marks (comma, semicolon, colon, period).
 *
 * It demonstrates string manipulation, character iteration, and helper methods.
 */
