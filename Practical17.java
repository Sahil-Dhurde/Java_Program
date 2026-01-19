import java.util.Scanner;

public class Practical17 {

    public static void main(String[] args) {
        
        try
         {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter numerator: ");
            int numerator = scanner.nextInt();
            System.out.print("Enter denominator: ");
            int denominator = scanner.nextInt();
            
            int result = numerator / denominator;
            System.out.println("Result: " + result);
        } 
        catch (ArithmeticException e)
         {
            System.out.println("Error: Cannot divide by zero.");
        }
        
        
        try
         {
            int[] numbers = {10, 20, 30, 40, 50};
            System.out.print("Enter index to access element: ");
            Scanner scanner = new Scanner(System.in);
            int index = scanner.nextInt();
            
            System.out.println("Element at index " + index + ": " + numbers[index]);
        }
         catch (ArrayIndexOutOfBoundsException e) 
         {
            System.out.println("Error: Invalid index. Please try again with a valid index.");
        }
        
        // Demonstrating NullPointerException
        try
         {
            String str = null;
            System.out.println("Length of string: " + str.length());
        }
         catch (NullPointerException e)
          {
            System.out.println("Error: Null value encountered. Cannot access methods on a null reference.");
        }
    }
}
