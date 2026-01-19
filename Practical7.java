import java.util.Scanner;

class Student {
    int rollNumber;
    String name;
    String branch;
    int year;
    static int count = 0;

    // Constructor
    Student(int rollNumber, String name, String branch, int year) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.branch = branch;
        this.year = year;
        count++;
    }

    void display() {
        System.out.println("Roll No: " + rollNumber);
        System.out.println("Name: " + name);
        System.out.println("Branch: " + branch);
        System.out.println("Year: " + year);
    }
}

public class Practical7 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        Student[] students = new Student[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for student " + (i + 1));
            System.out.print("Roll Number: ");
            int roll = sc.nextInt();
            sc.nextLine();  // Consume newline
            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Branch: ");
            String branch = sc.nextLine();
            System.out.print("Year: ");
            int year = sc.nextInt();

            students[i] = new Student(roll, name, branch, year);
        }

        System.out.println("\n--- Student Details ---");
        for (Student s : students) {
            s.display();
            System.out.println();
        }

        System.out.println("Total Students Created: " + Student.count);
    }
}
