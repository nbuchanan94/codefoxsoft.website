// Nicholas Buchanan
// December 23, 2010

import java.util.*;
import java.util.Arrays;
import javax.swing.*;
import java.awt.*;

public class StringedArraySorter extends JFrame {

    private JTextArea inputArea;
    private JTextArea outputArea;

    public StringedArraySorter() {
        initGUI();
    }

    private void initGUI() {
        setTitle("StringedArraySorter - Nick Buchanan");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Library Theme (Brown)
        getContentPane().setBackground(new Color(210, 180, 140)); // Tan

        JPanel p = new JPanel(new GridLayout(1, 2, 10, 10));
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel left = new JPanel(new BorderLayout());
        left.add(new JLabel("Input (One per line):"), BorderLayout.NORTH);
        inputArea = new JTextArea();
        left.add(new JScrollPane(inputArea), BorderLayout.CENTER);
        p.add(left);

        JPanel right = new JPanel(new BorderLayout());
        right.add(new JLabel("Sorted Output:"), BorderLayout.NORTH);
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(255, 250, 205)); // Lemon Chiffon
        right.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        p.add(right);

        add(p, BorderLayout.CENTER);

        JButton sortBtn = new JButton("SORT ALPHABETICALLY");
        sortBtn.setBackground(new Color(139, 69, 19)); // Saddle Brown
        sortBtn.setForeground(Color.WHITE);
        sortBtn.addActionListener(e -> sort());
        add(sortBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void sort() {
        String[] lines = inputArea.getText().split("\\n");
        // Remove empty lines
        java.util.List<String> list = new ArrayList<>();
        for (String s : lines) {
            if (!s.trim().isEmpty())
                list.add(s);
        }

        String[] arr = list.toArray(new String[0]);
        Arrays.sort(arr);

        StringBuilder sb = new StringBuilder();
        for (String s : arr)
            sb.append(s + "\n");
        outputArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new StringedArraySorter());
        }
    }

    public static void runConsole(String[] args) {
        int j = 0;
        // Initialize Scanner for user input
        Scanner kb = new Scanner(System.in);

        // Prompt user for the number of strings to enter
        System.out.println("how many times run the loop?");
        int innum = kb.nextInt();

        // Consume the leftover newline character from nextInt() to prevent skipping
        // usage
        kb.nextLine();

        // Create an array to hold the specific number of strings
        String[] name = new String[innum];

        // Prompt user to enter the strings
        System.out.println("ENTER STRING THEN PRESS ENTER! \nKEEP DOING THIS UNTIL LOOP ENDS");

        // Loop to capture user input for each string
        for (j = 0; j < innum; j++) {
            name[j] = kb.nextLine();
        }

        // Sort the array alphabetically
        Arrays.sort(name);

        // Loop to display the sorted strings
        System.out.println("\nSorted List:");
        for (String s : name) {
            System.out.println(s);
        }

        kb.close();
    }
}

/*
 * 1. Takes a user input for the size of the array.
 * 2. Collects strings from the user to fill the array.
 * 3. Uses java.util.Arrays.sort() to alphabetize the data.
 * 4. Prints the sorted list to the console.
 */
