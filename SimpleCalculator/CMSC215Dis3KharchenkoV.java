import java.util.Scanner;

public class CMSC215Dis3KharchenkoV {

    public static int quotient(int number1, int number2) {
        if (number2 == 0)
            throw new ArithmeticException("Divisor cannot be zero");
        return number1 / number2;
    }

    public static int add(int number1, int number2) {
        return number1 + number2;
    }

    public static int subtract(int number1, int number2) {
        return number1 - number2;
    }

    public static int multiply(int number1, int number2) {
        return number1 * number2;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Prompt the user to enter two integers
        System.out.print("Enter two integers: ");
        int number1 = input.nextInt();
        int number2 = input.nextInt();

        // Prompt the user to enter an operator
        System.out.print("Enter an operator (+, -, *, /): ");
        char operator = input.next().charAt(0);

        try {
            int result = 0;
            switch (operator) {
                case '+':
                    result = add(number1, number2);
                    break;
                case '-':
                    result = subtract(number1, number2);
                    break;
                case '*':
                    result = multiply(number1, number2);
                    break;
                case '/':
                    result = quotient(number1, number2);
                    break;
                default:
                    System.out.println("Invalid operator!");
                    return;
            }
            System.out.println(number1 + " " + operator + " " + number2 + " is " + result);
        } catch (ArithmeticException ex) {
            System.out.println("Exception: an integer cannot be divided by zero");
        }

        System.out.println("Execution continues ...");
    }
}