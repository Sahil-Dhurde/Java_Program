import java.util.Scanner;

public class Practical2 {

    
    public static final double PI = 3.14159;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

       
        System.out.print("Enter the radius of the circle: ");
        double radius = scanner.nextDouble();

        
        double area = PI * radius * radius;
        double perimeter = 2 * PI * radius;

        
        System.out.println("Area of the circle: " + area);
        System.out.println("Perimeter (Circumference) of the circle: " + perimeter);

        scanner.close();
    }
}
