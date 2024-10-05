// Name: Kharchenko, Vadym

// Project Name: Polygon Convex - Finding Area

// Date: 13 July, 2024

import java.util.Scanner;

public class CMSC215Dis5KharchenkoV {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of points in the convex polygon: ");
        int n = scanner.nextInt();

        double[] x = new double[n];
        double[] y = new double[n];

        System.out.println("Enter the coordinates of the points in clockwise order:");
        for (int i = 0; i < n; i++) {
            System.out.print("Point " + (i + 1) + " (format: x y): ");
            x[i] = scanner.nextDouble();
            y[i] = scanner.nextDouble();
        }

        double area = calculateArea(x, y, n);

        System.out.printf("The total area of the convex polygon is: %.2f\n", area);
    }

    public static double calculateArea(double[] x, double[] y, int n) {
        double area = 0.0;

        // Calculate the area using the Shoelace formula
        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;
            area += x[i] * y[j] - y[i] * x[j];
        }

        area = Math.abs(area) / 2.0;
        return area;
    }
}
