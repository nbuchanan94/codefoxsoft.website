// Nicholas Buchanan
// April 12 2010


import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class MatrixMultiplier extends JFrame {

    private JTextField[][] gridA, gridB;
    private JTextArea resultArea;

    public MatrixMultiplier() {
        initGUI();
    }

    private void initGUI() {
        setTitle("MatrixMultiplier - Nick Buchanan");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Cyber Theme (Neon Purple)
        getContentPane().setBackground(Color.BLACK);

        JTabbedPane tabs = new JTabbedPane();


        JPanel mainP = new JPanel(new GridLayout(1, 3, 10, 10));
        mainP.setBackground(Color.BLACK);

        gridA = createGrid(mainP, "Matrix A (2x2)");
        gridB = createGrid(mainP, "Matrix B (2x2)");

        JPanel resP = new JPanel(new BorderLayout());
        resP.setOpaque(false);
        resP.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.MAGENTA), "Result"));
        resultArea = new JTextArea();
        resultArea.setFont(new Font("Monospaced", Font.BOLD, 24));
        resultArea.setForeground(Color.MAGENTA);
        resultArea.setBackground(Color.BLACK);
        resP.add(resultArea, BorderLayout.CENTER);
        JButton btn = new JButton("MULTIPLY");
        btn.setBackground(Color.MAGENTA);
        btn.addActionListener(e -> multiplyGUI());
        resP.add(btn, BorderLayout.SOUTH);

        mainP.add(resP);
        add(mainP, BorderLayout.CENTER);

        setVisible(true);
    }

    private JTextField[][] createGrid(JPanel parent, String title) {
        JPanel p = new JPanel(new GridLayout(2, 2, 5, 5));
        p.setBackground(Color.BLACK);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.CYAN), title));
        JTextField[][] f = new JTextField[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                f[i][j] = new JTextField("0");
                f[i][j].setHorizontalAlignment(JTextField.CENTER);
                p.add(f[i][j]);
            }
        }
        parent.add(p);
        return f;
    }

    private void multiplyGUI() {
        try {
            int[][] a = new int[2][2];
            int[][] b = new int[2][2];

            for (int i = 0; i < 2; i++)
                for (int j = 0; j < 2; j++)
                    a[i][j] = Integer.parseInt(gridA[i][j].getText());

            for (int i = 0; i < 2; i++)
                for (int j = 0; j < 2; j++)
                    b[i][j] = Integer.parseInt(gridB[i][j].getText());

            int[][] c = multiply(a, b);

            resultArea.setText(c[0][0] + "\t" + c[0][1] + "\n\n" + c[1][0] + "\t" + c[1][1]);

        } catch (Exception e) {
            resultArea.setText("Error");
        }
    }

    public static void main(String[] args) {
        if (java.awt.GraphicsEnvironment.isHeadless() || (args.length > 0 && args[0].equals("--cli"))) {
            runConsole(args);
        } else {
            SwingUtilities.invokeLater(() -> new MatrixMultiplier());
        }
    }

    public static int[][] multiply(int[][] m1, int[][] m2) {
        int m1rows = m1.length;
        int m1cols = m1[0].length;
        int m2rows = m2.length;
        int m2cols = m2[0].length;

        if (m1cols != m2rows) {
            throw new IllegalArgumentException("Matrix dimensions do not match");
        }

        int[][] result = new int[m1rows][m2cols];

        for (int i = 0; i < m1rows; i++) {
            for (int j = 0; j < m2cols; j++) {
                for (int k = 0; k < m1cols; k++) {
                    result[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return result;
    }

    public static void printMatrix(int[][] a, String name) {
        int rows = a.length;
        int cols = a[0].length;
        System.out.println(name + " [" + rows + "x" + cols + "] = {");
        for (int i = 0; i < rows; i++) {
            System.out.print("  {");
            for (int j = 0; j < cols; j++) {
                System.out.print(" " + a[i][j] + (j < cols - 1 ? "," : ""));
            }
            System.out.println(" }" + (i < rows - 1 ? "," : ""));
        }
        System.out.println("};");
        System.out.println();
    }

    public static void runConsole(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Matrix Multiplier ---");

        // Matrix A Input
        System.out.println("Matrix A:");
        System.out.print("Enter Rows: ");
        int rowsA = scanner.nextInt();
        System.out.print("Enter Columns: ");
        int colsA = scanner.nextInt();
        int[][] matrixA = new int[rowsA][colsA];

        System.out.println("Enter content for Matrix A:");
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsA; j++) {
                System.out.print("A[" + i + "][" + j + "]: ");
                matrixA[i][j] = scanner.nextInt();
            }
        }

        // Matrix B Input
        System.out.println("\nMatrix B:");
        System.out.print("Enter Rows: ");
        int rowsB = scanner.nextInt();
        System.out.print("Enter Columns: ");
        int colsB = scanner.nextInt();
        int[][] matrixB = new int[rowsB][colsB];

        System.out.println("Enter content for Matrix B:");
        for (int i = 0; i < rowsB; i++) {
            for (int j = 0; j < colsB; j++) {
                System.out.print("B[" + i + "][" + j + "]: ");
                matrixB[i][j] = scanner.nextInt();
            }
        }

        try {
            int[][] result = multiply(matrixA, matrixB);

            System.out.println("\n--- Results ---");
            printMatrix(matrixA, "Matrix A");
            printMatrix(matrixB, "Matrix B");
            printMatrix(result, "Result Matrix");

        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
// Usage: Multiplies two matrices.
