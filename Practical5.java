

public class Practical5 {
    
    String name;
    int age;
    String address;

    public Practical5(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

   
    public void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Address: " + address);
        System.out.println();
    }

    
    public static void main(String[] args) {
       
        Practical5 person1 = new Practical5("Sahil", 25, "Jamod");
        Practical5 person2 = new Practical5("SMD", 30, "Amravati");

        
        person1.displayInfo();
        person2.displayInfo();
    }
}

