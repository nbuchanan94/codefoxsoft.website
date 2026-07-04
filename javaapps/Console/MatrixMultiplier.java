// Nicholas Buchanan
// April 12 2010
// MatrixMultiplier.java

import java.util.Scanner;

public class MatrixMultiplier {

  public static int[][] multiply(int[][] m1, int[][] m2) {
    int m1rows = m1.length;
    int m1cols = m1[0].length;
    int m2rows = m2.length;
    int m2cols = m2[0].length;

    if (m1cols != m2rows) {
      throw new IllegalArgumentException("Matrix dimensions do not match for multiplication: "
          + "Matrix 1 Cols (" + m1cols + ") != Matrix 2 Rows (" + m2rows + ")");
    }

    int[][] result = new int[m1rows][m2cols];

    // Multiply
    for (int i = 0; i < m1rows; i++) {
      for (int j = 0; j < m2cols; j++) {
        for (int k = 0; k < m1cols; k++) { // k < common dimension
          result[i][j] += m1[i][k] * m2[k][j];
        }
      }
    }

    return result;
  }

  /** Matrix print helper. */
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

  public static void main(String[] args) {
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

/*
 * PROGRAM DESCRIPTION:
 * 
 * MatrixMultiplier.java
 * 
 * This program multiplies two user-defined matrices (2D arrays) together.
 * It prompts the user for the dimensions and values of two matrices,
 * verifies that they are compatible for multiplication (Cols A == Rows B),
 * and calculates the resulting matrix.
 */
