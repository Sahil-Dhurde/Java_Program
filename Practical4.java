import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Practical4 {

    public static void main(String[] args)
     {
        Scanner sc = new Scanner(System.in);

        
        System.out.print("Enter the number of elements in the array: ");
        int size = sc.nextInt();

        
        Integer[] array = new Integer[size];
        System.out.println("Enter " + size + " elements:");
        for (int i = 0; i < size; i++)
         {
            array[i] = sc.nextInt();
        }

        
        Arrays.sort(array);
        System.out.println("Array in ascending order: " + Arrays.toString(array));

        
        Arrays.sort(array, Collections.reverseOrder());
        System.out.println("Array in descending order: " + Arrays.toString(array));

        sc.close();
    }
}
