// Nicholas Buchanan
// September 15, 2010
// 3rd period

import java.util.*;
import java.text.DecimalFormat;

public class CircleArea {

    public static void main(String[] args) {
        Scanner kbReader = new Scanner(System.in);
        System.out.print("Enter the radius of the circle: ");
        // Changed nextInt to nextDouble to prevent crash on decimal inputs,
        // while preserving the fundamental logic structure.
        double rad = kbReader.nextDouble();
        DecimalFormat df = new DecimalFormat("###.##");
        System.out.println(df.format(area(rad)));
        kbReader.close();
    }

    public static double area(double radius) {
        // Original logic preserved
        radius = Math.PI * Math.pow(radius, 2);
        return radius;
    }
}

/*
 * 1. The user is prompted to enter a radius.
 * 2. The program reads the input.
 * 3. It calls a method 'area' which calculates Area = PI * r^2.
 * 4. It prints the result formatted to two decimal places.
 */