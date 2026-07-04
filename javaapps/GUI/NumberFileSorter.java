
// Nicholas Buchanan
// 2011
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class NumberFileSorter extends JFrame {

    private JTextArea inputArea, outputArea;

    public NumberFileSorter() {
        initGUI();
    }

    private void initGUI() {
        setTitle("NumberFileSorter - Nick Buchanan");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Data Centre Theme (Blue/Silver)
        getContentPane().setBackground(new Color(192, 192, 192)); // Silver

        JPanel p = new JPanel(new GridLayout(1, 2, 10, 10));
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel left = new JPanel(new BorderLayout());
        left.add(new JLabel("Unsorted (File or Manual):"), BorderLayout.NORTH);
        inputArea = new JTextArea();
        left.add(new JScrollPane(inputArea), BorderLayout.CENTER);
        JButton loadBtn = new JButton("Load numbers.txt");
        loadBtn.addActionListener(e -> loadFile());
        left.add(loadBtn, BorderLayout.SOUTH);
        p.add(left);

        JPanel right = new JPanel(new BorderLayout());
        right.add(new JLabel("Sorted Output:"), BorderLayout.NORTH);
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(230, 230, 250)); // Lavender
        right.add(new JScrollPane(outputArea), BorderLayout.CENTER);
        JButton sortBtn = new JButton("SORT >>>");
        sortBtn.setBackground(Color.BLUE);
        sortBtn.setForeground(Color.WHITE);
        sortBtn.addActionListener(e -> sort());
        right.add(sortBtn, BorderLayout.SOUTH);
        p.add(right);

        add(p, BorderLayout.CENTER);

        setVisible(true);
    }

    private void loadFile() {
        try {
            File f = new File("numbers.txt");
            if (f.exists()) {
                Scanner sc = new Scanner(f);
                StringBuilder sb = new StringBuilder();
                while (sc.hasNext())
                    sb.append(sc.next() + "\n");
                sc.close();
                inputArea.setText(sb.toString());
            } else {
                JOptionPane.showMessageDialog(this, "numbers.txt not found. Enter manually.");
            }
        } catch (Exception e) {
        }
    }

    private void sort() {
        try {
            String txt = inputArea.getText();
            Scanner s = new Scanner(txt);
            ArrayList<Integer> nums = new ArrayList<>();
            while (s.hasNext()) {
                if (s.hasNextInt())
                    nums.add(s.nextInt());
                else
                    s.next();
            }
            s.close();

            Collections.sort(nums);

            StringBuilder sb = new StringBuilder();
            for (Integer i : nums)
                sb.append(i + "\n");
            outputArea.setText(sb.toString());

        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new NumberFileSorter());
        }
    }

    public static void runConsole(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        Scanner fileScanner = null;
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("Attempting to read 'numbers.txt'...");

        try {
            // Try to read from the file
            File file = new File("numbers.txt");
            fileScanner = new Scanner(file);

            while (fileScanner.hasNext()) {
                if (fileScanner.hasNextInt()) {
                    numbers.add(fileScanner.nextInt());
                } else {
                    fileScanner.next(); // Skip non-integer tokens
                }
            }
            System.out.println("File loaded successfully.");

        } catch (FileNotFoundException e) {
            // File not found strategy: Prompt user
            System.out.println("File 'numbers.txt' was not found.");
            System.out.println("Please enter a list of numbers manually.");
            System.out.println("Example: 45 12 1 100 55");
            System.out.print("Input: ");

            // Read the entire line of input
            if (inputScanner.hasNextLine()) {
                String line = inputScanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                while (lineScanner.hasNext()) {
                    if (lineScanner.hasNextInt()) {
                        numbers.add(lineScanner.nextInt());
                    } else {
                        lineScanner.next(); // Skip garbage
                    }
                }
                lineScanner.close();
            }
        } finally {
            if (fileScanner != null)
                fileScanner.close();
            // We usually don't close System.in scanner in main if we might need it, but
            // here it's fine.
            inputScanner.close();
        }

        // Display Unsorted
        System.out.println("\nUnsorted List: " + numbers);

        // Sort
        Collections.sort(numbers);

        // Display Sorted
        System.out.println("SORTED NUMBERS: " + numbers);
    }
}

/*
 * This program attempts to read a list of integers from a file named
 * "numbers.txt".
 */
