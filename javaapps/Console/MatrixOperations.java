// Nicholas Buchanan
// 12/21/2010
// PD 4

import java.util.Scanner;

public class MatrixOperations {
    public static void main(String[] args) {
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

            // Common setup: Get dimensions
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

            } else if (choice == 3) {
                System.out.println("\n--- Matrix A ---");
                int[][] a = inputMatrix(scanner, rows, cols);
                System.out.print("Enter Scalar Multiplier: ");
                int scalar = scanner.nextInt();

                System.out.println("\nResult (A * Scalar):");
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        result[i][j] = a[i][j] * scalar;
                    }
                }
                printMatrix(result);
            }
        }

        System.out.println("Exiting Matrix Operations.");
        scanner.close();
    }

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
}

/*

 * This program provides reasonable matrix arithmetic utilities via a console
 * menu.
 * It allows the user to:
 * 1. Add two matrices together (element-wise).
 * 2. Add a scalar value to every element of a matrix.
 * 3. Multiply every element of a matrix by a scalar value.
 *
 * It uses 2D arrays to represent the matrices and nested loops to
 * perform the calculations.
 */