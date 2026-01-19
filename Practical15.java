// User-Defined Exception class
class NegativeNumberException extends Exception {
    public NegativeNumberException(String message) {
        super(message);
    }
}

public class Practical15 {

    public static void main(String[] args) 
    {
        try {
            
            int num1 = 10;
            int num2 = 0;  
            System.out.println("Division Result: " + (num1 / num2));  
        } 
        catch (ArithmeticException e)
         {
            System.out.println("Error: Cannot divide by zero.");
        }

        try {
            
            int number = -5;  
            if (number < 0)
             {
                throw new NegativeNumberException("Error: Negative number encountered.");
            }
            System.out.println("Number is positive: " + number);
        } 
        catch (NegativeNumberException e)
         {
            System.out.println(e.getMessage());
        }
    }
}

