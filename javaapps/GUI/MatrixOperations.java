// Nicholas Buchanan
// 12/21/2010
// PD 4

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class MatrixOperations extends JFrame {

    // Simplification for GUI: Just do 2x2 Addition for demo,
    // as full arbitrary matrix ops in Swing is complex to layout dynamically in one
    // file
    // without external layout managers.
    private JTextField[][] gridA, gridB;
    private JTextArea resultArea;

    public MatrixOperations() {
        initGUI();
    }

    private void initGUI() {
        setTitle("MatrixOperations - Nick Buchanan");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Matrix Theme (Black & Green)
        Color bg = Color.BLACK;
        Color fg = new Color(0, 255, 0); // Bright Green

        getContentPane().setBackground(bg);

        JPanel center = new JPanel(new GridLayout(1, 3));
        center.setOpaque(false);
        gridA = createGrid(center, "Matrix A", bg, fg);
        gridB = createGrid(center, "Matrix B", bg, fg);

        JPanel resP = new JPanel(new BorderLayout());
        resP.setOpaque(false);
        resultArea = new JTextArea();
        resultArea.setBackground(bg);
        resultArea.setForeground(fg);
        resultArea.setCaretColor(fg);
        // Larger Font for Output
        resultArea.setFont(new Font("Monospaced", Font.BOLD, 32));
        resultArea.setBorder(BorderFactory.createLineBorder(fg));
        resP.add(new JScrollPane(resultArea));
        center.add(resP);

        add(center, BorderLayout.CENTER);

        JPanel bot = new JPanel();
        bot.setBackground(bg);

        JLabel modeL = new JLabel("Mode:");
        modeL.setForeground(fg);
        bot.add(modeL);

        String[] opts = { "Add Matrices", "Add Scalar (use A[0][0])", "Mult Scalar" };
        JComboBox<String> box = new JComboBox<>(opts);
        box.setBackground(bg);
        box.setForeground(fg);
        bot.add(box);

        JButton btn = new JButton("OPERATE");
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setBorder(BorderFactory.createLineBorder(fg));
        btn.addActionListener(e -> operate(box.getSelectedIndex()));
        bot.add(btn);

        add(bot, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JTextField[][] createGrid(JPanel p, String t, Color bg, Color fg) {
        JPanel g = new JPanel(new GridLayout(2, 2));
        g.setBackground(bg);
        g.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(fg), t,
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Monospaced", Font.BOLD, 12), fg));

        JTextField[][] tf = new JTextField[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                tf[i][j] = new JTextField("0");
                tf[i][j].setBackground(bg);
                tf[i][j].setForeground(fg);
                tf[i][j].setCaretColor(fg);
                tf[i][j].setHorizontalAlignment(JTextField.CENTER);
                // Larger font for inputs too matching visibility
                tf[i][j].setFont(new Font("Monospaced", Font.BOLD, 16));
                tf[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                g.add(tf[i][j]);
            }
        }
        p.add(g);
        return tf;
    }

    private void operate(int mode) {
        // Logic for 2x2 op
        try {
            int[][] a = new int[2][2];
            int[][] b = new int[2][2];
            for (int i = 0; i < 2; i++)
                for (int j = 0; j < 2; j++) {
                    a[i][j] = Integer.parseInt(gridA[i][j].getText());
                    b[i][j] = Integer.parseInt(gridB[i][j].getText());
                }

            StringBuilder sb = new StringBuilder();
            if (mode == 0) { // Add
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++)
                        // Use formatting for alignment
                        sb.append(String.format("%4d ", (a[i][j] + b[i][j])));
                    sb.append("\n");
                }
            } else if (mode == 1) { // Scalar Add (using B[0][0] as scalar)
                int s = b[0][0];
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 2; j++)
                        sb.append(String.format("%4d ", (a[i][j] + s)));
                    sb.append("\n");
                }
            }
            // ... etc
            resultArea.setText(sb.toString());
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new MatrixOperations());
        }
    }

    // CLI Methods
    private static int[][] inputMatrix(Scanner s, int rows, int cols) {
        int[][] m = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("[" + i + "][" + j + "]: ");
                m[i][j] = s.nextInt();
            }
        }
        return m;
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.printf("%4d", val);
            }
            System.out.println();
        }
    }

    public static void runConsole(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Matrix Operations ---");

        while (true) {
            System.out.println("\nSelect an Operation:");
            System.out.println("1. Add Two Matrices");
            System.out.println("2. Add Scalar to Matrix");
            System.out.println("3. Multiply Matrix by Scalar");
            System.out.println("4. Exit");
            System.out.print("> ");

            int choice = 0;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                scanner.next(); // Clear invalid input
            }

            if (choice == 4)
                break;

            if (choice < 1 || choice > 4) {
                System.out.println("Invalid choice.");
                continue;
            }

            System.out.print("Enter Rows: ");
            int rows = scanner.nextInt();
            System.out.print("Enter Columns: ");
            int cols = scanner.nextInt();

            int[][] result = new int[rows][cols];

            if (choice == 1) {
                System.out.println("\n--- Matrix A ---");
                int[][] a = inputMatrix(scanner, rows, cols);
                System.out.println("\n--- Matrix B ---");
                int[][] b = inputMatrix(scanner, rows, cols);

                System.out.println("\nResult (A + B):");
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        result[i][j] = a[i][j] + b[i][j];
                    }
                }
                printMatrix(result);

            } else if (choice == 2) {
                System.out.println("\n--- Matrix A ---");
                int[][] a = inputMatrix(scanner, rows, cols);
                System.out.print("Enter Scalar to Add: ");
                int scalar = scanner.nextInt();

                System.out.println("\nResult (A + Scalar):");
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        result[i][j] = a[i][j] + scalar;
                    }
                }
                printMatrix(result);
            }
        }
        scanner.close();
    }
}
// Usage: Matrix Arithmetic.
