// Name: Kharchenko, Vadym

// Project Name: Fibonacci Series and Reverse

// Date: 22 July, 2024

import java.util.Scanner;

public class CMSC215Dis7KharchenkoV {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String continueInput;

        do {
            System.out.print("Enter an integer to reverse: ");
            int value = scanner.nextInt();
            System.out.print("The reversal of " + value + " is ");
            reverseDisplay(value);
            System.out.println();

            System.out.print("Would you like to reverse another number? (yes/no): ");
            continueInput = scanner.next();
        } while (continueInput.equalsIgnoreCase("yes"));

        System.out.println("Thank you for using the program!");
    }

    public static void reverseDisplay(int value) {
        if (value < 10) {
            System.out.print(value);
        } else {
            System.out.print(value % 10);
            reverseDisplay(value / 10);
        }
    }
}