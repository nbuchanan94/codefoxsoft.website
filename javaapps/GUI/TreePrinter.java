
// Nicholas Larkin Buchanan
// 2012
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class TreePrinter extends JFrame {

    private JTextField hField;
    private JTextArea treeArea;

    public TreePrinter() {
        initGUI();
    }

    private void initGUI() {
        setTitle("TreePrinter - Nick Buchanan");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Nature Theme
        getContentPane().setBackground(new Color(34, 139, 34)); // Forest Green

        JPanel top = new JPanel();
        top.setOpaque(false);
        JLabel l = new JLabel("Height (Odd 1-21):");
        l.setForeground(Color.WHITE);
        top.add(l);
        hField = new JTextField(5);
        top.add(hField);
        JButton btn = new JButton("GROW TREE");
        btn.setBackground(new Color(139, 69, 19)); // Saddle Brown
        btn.setForeground(Color.WHITE);
        btn.addActionListener(e -> grow());
        top.add(btn);
        add(top, BorderLayout.NORTH);

        treeArea = new JTextArea();
        treeArea.setFont(new Font("Monospaced", Font.BOLD, 16));
        treeArea.setBackground(new Color(139, 69, 19)); // Tree Truck Brown
        treeArea.setForeground(Color.GREEN); // Leaves
        treeArea.setEditable(false);
        add(new JScrollPane(treeArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void grow() {
        try {
            int n = Integer.parseInt(hField.getText());
            if (n % 2 == 0 || n < 1 || n > 21) {
                treeArea.setText("Error: Enter ODD number (1-21)");
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= n; i++) {
                // Print leading spaces
                for (int k = n; k > i; k--) {
                    sb.append(" ");
                }
                // Print stars
                for (int j = 1; j <= i; j++) {
                    sb.append("* ");
                }
                sb.append("\n");
            }
            treeArea.setText(sb.toString());

        } catch (Exception e) {
            treeArea.setText("Invalid Input");
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new TreePrinter());
        }
    }

    public static void runConsole(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Tree Printer!");
        System.out.println("This program draws a pyramid tree based on the size you enter.");
        System.out.print("Please enter an odd integer between 1 and 21 to set the tree height: ");

        if (scanner.hasNextInt()) {
            int n = scanner.nextInt();

            // Check if even or out of range (1 to 21 inclusive)
            if (n % 2 == 0 || n < 1 || n > 21) {
                System.out.println("Error: That number is not valid.");
                System.out.println(
                        "Please run the program again and enter an ODD number (like 3, 5, 7) within the range.");
            } else {
                // Draw the tree
                for (int i = 1; i <= n; i++) {
                    // Print leading spaces
                    for (int k = n; k > i; k--) {
                        System.out.print(" ");
                    }
                    // Print stars
                    for (int j = 1; j <= i; j++) {
                        System.out.print("* ");
                    }
                    System.out.println();
                }
                System.out.println("Tree printed successfully.");
            }
        } else {
            System.out.println("Error: Input was not an integer.");
        }
        scanner.close();
    }
}
// Run the program and input an odd integer (1-21) to generate a pyramid tree.
