class Vehicle {
    String brand, model;
    int year;

    Vehicle(String b, String m, int y) {
        brand = b;
        model = m;
        year = y;
    }

    void show() {
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
    }
}

class Car extends Vehicle {
    int doors;

    Car(String b, String m, int y, int d) {
        super(b, m, y);
        doors = d;
    }

    void show() {
        super.show();
        System.out.println("Type: Car");
        System.out.println("Doors: " + doors);
    }
}

class Bike extends Vehicle {
    boolean carrier;

    Bike(String b, String m, int y, boolean c) {
        super(b, m, y);
        carrier = c;
    }

    void show() {
        super.show();
        System.out.println("Type: Bike");
        System.out.println("Carrier: " + (carrier ? "Yes" : "No"));
    }
}

public class Practical8 {
    public static void main(String[] args) {
        Car c = new Car("Toyota", "Corolla", 2021, 4);
        Bike b = new Bike("Yamaha", "FZ", 2022, true);

        System.out.println("--- Car ---");
        c.show();

        System.out.println("\n--- Bike ---");
        b.show();
    }
}

