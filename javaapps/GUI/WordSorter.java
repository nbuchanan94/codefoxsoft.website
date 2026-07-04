// Nicholas Buchanan
// Monday, December 06, 2010

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class WordSorter extends JFrame {

    private JTextField inputField;
    private DefaultListModel<String> model;
    private JList<String> wordList;

    public WordSorter() {
        initGUI();
    }

    private void initGUI() {
        setTitle("WordSorter - Nick Buchanan");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Dictionary Theme (Blue/White)
        getContentPane().setBackground(new Color(70, 130, 180)); // Steel Blue

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(new JLabel("<html><h1 style='color:white'>DICTIONARY SORTER</h1></html>", SwingConstants.CENTER),
                BorderLayout.NORTH);

        JPanel inputP = new JPanel();
        inputField = new JTextField(20);
        inputField.addActionListener(e -> addWord());
        inputP.add(inputField);
        JButton addBtn = new JButton("Add Word");
        addBtn.addActionListener(e -> addWord());
        inputP.add(addBtn);
        top.add(inputP, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        model = new DefaultListModel<>();
        wordList = new JList<>(model);
        wordList.setFont(new Font("Serif", Font.PLAIN, 18));
        add(new JScrollPane(wordList), BorderLayout.CENTER);

        JButton doneBtn = new JButton("DONE (SORT)");
        doneBtn.setFont(new Font("Arial", Font.BOLD, 20));
        doneBtn.addActionListener(e -> sort());
        add(doneBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addWord() {
        String w = inputField.getText();
        if (!w.isEmpty() && !w.equalsIgnoreCase("done")) {
            model.addElement(w);
            inputField.setText("");
        }
    }

    private void sort() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < model.size(); i++)
            list.add(model.get(i));
        Collections.sort(list);
        model.clear();
        for (String s : list)
            model.addElement(s);
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new WordSorter());
        }
    }

    public static void runConsole(String[] args) {
        ArrayList<String> words = new ArrayList<String>();
        Scanner kbReader = new Scanner(System.in);

        // Instructions for the user
        System.out.println("--- Word Sorter ---");
        System.out.println("Type words one by one to add them to the list.");
        System.out.println("Type 'done' when you are finished to see them sorted.");
        System.out.print(">> ");

        while (true) {
            String input = kbReader.next();

            // Check for sentinel value "done" (case-insensitive)
            if (input.equalsIgnoreCase("done")) {
                break;
            }

            words.add(input);
            System.out.print(">> ");
        }

        // Sort the list alphabetically
        Collections.sort(words);

        System.out.println("\nSorted List: " + words);

        kbReader.close();
    }
}

/*
 * This program collects a list of words entered by the user.
 * It continues to accept input until the user types the word "done".
 * Once finished, it sorts the words alphabetically and displays the list.
 */
