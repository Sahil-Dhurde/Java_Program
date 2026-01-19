
interface GPSVehicle
 {
    void navigate(String place);
}


abstract class Vehicle {
    abstract void startEngine();
    abstract void stopEngine();
    abstract void accelerate();
}

class Car extends Vehicle implements GPSVehicle {
    public void startEngine()
     {
        System.out.println("Car engine started.");
    }

    public void stopEngine() {
        System.out.println("Car engine stopped.");
    }

    public void accelerate()
     {
        System.out.println("Car is accelerating.");
    }

    public void navigate(String place)
     {
        System.out.println("Car is navigating to " + place);
    }
}

// Bike class
class Bike extends Vehicle {
    public void startEngine()
     {
        System.out.println("Bike engine started.");
    }

    public void stopEngine()
     {
        System.out.println("Bike engine stopped.");
    }

    public void accelerate()
     {
        System.out.println("Bike is accelerating.");
    }
}

// Main class
public class Practical12 {
    public static void main(String[] args)
     {
        Car car = new Car();
        car.startEngine();
        car.accelerate();
        car.navigate("Park");
        car.stopEngine();

        System.out.println();

        Bike bike = new Bike();
        bike.startEngine();
        bike.accelerate();
        bike.stopEngine();
    }
}

