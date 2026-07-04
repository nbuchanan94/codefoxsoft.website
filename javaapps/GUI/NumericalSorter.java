// Nicholas Buchanan
// 12/1/10
// 3rd period

import java.util.Scanner;
import java.util.Arrays;
import javax.swing.*;
import java.awt.*;

public class NumericalSorter extends JFrame {

    private JTextField countField;
    private JPanel inputPanel;
    private JTextArea resultArea;
    private java.util.List<JTextField> dynamicFields = new java.util.ArrayList<>();

    public NumericalSorter() {
        initGUI();
    }

    private void initGUI() {
        setTitle("NumericalSorter - Nick Buchanan");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Data Theme (Green/Black)
        getContentPane().setBackground(Color.BLACK);

        JPanel top = new JPanel();
        top.setOpaque(false);
        JLabel l = new JLabel("Count:");
        l.setForeground(Color.GREEN);
        top.add(l);
        countField = new JTextField(5);
        top.add(countField);
        JButton setBtn = new JButton("Set");
        setBtn.setBackground(Color.GREEN);
        setBtn.addActionListener(e -> setupInputs());
        top.add(setBtn);
        add(top, BorderLayout.NORTH);

        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(Color.BLACK);
        add(new JScrollPane(inputPanel), BorderLayout.CENTER);

        JPanel bot = new JPanel(new BorderLayout());
        JButton sortBtn = new JButton("SORT DATA");
        sortBtn.setBackground(Color.GREEN);
        sortBtn.addActionListener(e -> sort());
        bot.add(sortBtn, BorderLayout.NORTH);

        resultArea = new JTextArea(5, 20);
        resultArea.setBackground(Color.DARK_GRAY);
        resultArea.setForeground(Color.GREEN);
        bot.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(bot, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void setupInputs() {
        inputPanel.removeAll();
        dynamicFields.clear();
        try {
            int c = Integer.parseInt(countField.getText());
            for (int i = 0; i < c; i++) {
                JTextField tf = new JTextField();
                dynamicFields.add(tf);
                inputPanel.add(tf);
            }
            inputPanel.revalidate();
            inputPanel.repaint();
        } catch (Exception e) {
        }
    }

    private void sort() {
        try {
            int[] nums = new int[dynamicFields.size()];
            for (int i = 0; i < nums.length; i++) {
                nums[i] = Integer.parseInt(dynamicFields.get(i).getText());
            }
            Arrays.sort(nums);

            StringBuilder sb = new StringBuilder();
            for (int n : nums)
                sb.append(n + "\n");
            resultArea.setText(sb.toString());
        } catch (Exception e) {
            resultArea.setText("Error");
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new NumericalSorter());
        }
    }

    public static void runConsole(String[] args) {
        // Initialize Scanner for input
        Scanner brant = new Scanner(System.in);

        // Prompt user for the size of the array
        System.out.println("How many numbers to sort?");
        int count = 0;

        // Simple error handling for non-integer input
        try {
            count = brant.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid number entered. Exiting.");
            brant.close();
            return;
        }

        // Initialize array with user-defined size
        int[] num = new int[count];

        // Prompt user to enter the numbers
        System.out.println("Please enter " + count + " numbers:");

        // Loop to collect input numbers
        for (int i = 0; i < num.length; i++) {
            System.out.print("Number " + (i + 1) + ": ");
            num[i] = brant.nextInt();
        }

        // Sort the array in ascending order
        Arrays.sort(num);

        // Display the sorted results
        System.out.println("\n--- Numbers Sorted ---");
        for (int i = 0; i < num.length; i++) {
            System.out.println(num[i]);
        }

        // Close scanner (good practice)
        brant.close();
    }
}

/*
 * 1. Setup: The program asks the user how many numbers they want to sort.
 * 2. Input: It then prompts the user to enter that many integer values one by
 * one.
 * 3. Processing: The program uses Java's built-in Arrays.sort() method to sort
 * the numbers in ascending order.
 * 4. Output: Finally, it prints the sorted list of numbers to the console.
 */
