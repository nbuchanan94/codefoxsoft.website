// Nicholas Buchanan
// September 2010

import java.io.*;
import java.util.*;

public class TriangleArea {
	public static void main(String[] args) {
		System.out.println("Enter vertices (x1 y1 x2 y2 x3 y3): ");

		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String line = input.readLine();
			if (line == null)
				return;

			StringTokenizer st = new StringTokenizer(line);

			// Parsing coordinates (Respective Points P1, P2, P3)
			int p1x = Integer.parseInt(st.nextToken());
			int p1y = Integer.parseInt(st.nextToken());

			int p2x = Integer.parseInt(st.nextToken());
			int p2y = Integer.parseInt(st.nextToken());

			int p3x = Integer.parseInt(st.nextToken());
			int p3y = Integer.parseInt(st.nextToken());

			// Determine Area using Shoelace Formula
			// (x1*y2 + x2*y3 + x3*y1) - (y1*x2 + y2*x3 + y3*x1)
			int term1 = (p1x * p2y) + (p2x * p3y) + (p3x * p1y);
			int term2 = (p1y * p2x) + (p2y * p3x) + (p3y * p1x);

			int area = Math.abs(term1 - term2) / 2;

			// Output Calculation
			// NOTE: The original code multiplied the result by 4/5 (0.8).
			// This suggests it was for a specific problem requiring this adjustment.
			System.out.println("Result: " + (area * 4 / 5));

		} catch (Exception e) {
			System.err.println("Error processing input: " + e.getMessage());
		}
	}
}

/*
 *
 * This program calculates the area of a triangle given three sets of
 * Cartesian coordinates using the Shoelace Formula.
 *
 */