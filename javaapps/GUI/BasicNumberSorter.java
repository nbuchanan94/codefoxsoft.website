// Nicholas Buchanan
// September 2009

import java.util.Scanner;
import java.util.Arrays;
import javax.swing.*;
import java.awt.*;

public class BasicNumberSorter extends JFrame {

    private JTextField[] inputs = new JTextField[5];
    private JTextArea resultArea;

    public BasicNumberSorter() {
        initGUI();
    }

    private void initGUI() {
        setTitle("BasicNumberSorter - Nick Buchanan");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Minimalist Theme
        getContentPane().setBackground(Color.WHITE);

        JPanel center = new JPanel(new GridLayout(6, 1, 5, 5));
        center.setOpaque(false);
        center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 5; i++) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            row.setOpaque(false);
            row.add(new JLabel("Num " + (i + 1) + ":"));
            inputs[i] = new JTextField(10);
            row.add(inputs[i]);
            center.add(row);
        }

        JButton btn = new JButton("SORT");
        btn.addActionListener(e -> sort());
        center.add(btn);

        add(center, BorderLayout.CENTER);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createTitledBorder("Sorted Result"));
        add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        setVisible(true);
    }

    private void sort() {
        try {
            int[] nums = new int[5];
            for (int i = 0; i < 5; i++) {
                nums[i] = Integer.parseInt(inputs[i].getText());
            }
            Arrays.sort(nums);
            resultArea.setText(Arrays.toString(nums));
        } catch (Exception e) {
            resultArea.setText("Invalid Input");
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new BasicNumberSorter());
        }
    }

    public static void runConsole(String[] args) {
        Scanner kbreader = new Scanner(System.in);
        int[] sortnum = new int[5];

        // Loop to get 5 numbers
        for (int i = 0; i < 5; i++) {
            System.out.print("Enter number " + (i + 1) + ": ");
            sortnum[i] = kbreader.nextInt();
        }

        Arrays.sort(sortnum);
        System.out.println("Sorted Numbers are as follows: " + Arrays.toString(sortnum));

        kbreader.close();
    }
}

/*
 * PROGRAM DESCRIPTION:
 * BasicNumberSorter.java
 *
 * This program takes a fixed set of 5 integer inputs from the user,
 * sorts them in ascending order using Java's built-in Arrays class,
 * and displays the sorted list.
 */
