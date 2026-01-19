public class Practical16 {
    public static void main(String[] args) {
    
        try 
        {
            String str = null;
            System.out.println(str.length()); // Trying to call a method on a null object
        } 
        catch (NullPointerException e)
         {
            System.out.println("Caught NullPointerException: " + e.getMessage());
        }

        
        try
         {
            int[] numbers = new int[3];
            numbers[5] = 10; // Trying to access an invalid index
        }
         catch (ArrayIndexOutOfBoundsException e)
          {
            System.out.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
        }

        
        try
        {
            String invalidNumber = "abc";
            int num = Integer.parseInt(invalidNumber); // Trying to parse a non-numeric string
        } 
        catch (NumberFormatException e)
         {
            System.out.println("Caught NumberFormatException: " + e.getMessage());
        }

        
        try
         {
            Object obj = new Integer(10);
            String str = (String) obj; // Trying to cast an Integer to a String
        }
        catch (ClassCastException e)
         {
            System.out.println("Caught ClassCastException: " + e.getMessage());
        }
    }
}

