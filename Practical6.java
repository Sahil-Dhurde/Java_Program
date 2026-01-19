
import java.util.Scanner;

public class Practical6 {

  
    public double area(double radius) {
        return Math.PI * radius * radius;
    }

   
    public double area(double length, double breadth) {
        return length * breadth;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Practical6 calc = new Practical6();

        System.out.print("Enter radius of circle: ");
        double radius = sc.nextDouble();
        System.out.println("Area of Circle: " + calc.area(radius));

        System.out.print("Enter length and breadth of rectangle: ");
        double length = sc.nextDouble();
        double breadth = sc.nextDouble();
        System.out.println("Area of Rectangle: " + calc.area(length, breadth));
    }
}
