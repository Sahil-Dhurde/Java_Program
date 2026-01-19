
abstract class Product
 {
    String name;
    double price;

    Product(String name, double price)
     {
        this.name = name;
        this.price = price;
    }

    abstract String getDescription();
    abstract double getPrice();
}


interface Discountable {
    double getDiscount();
}

// Electronics class
class Electronics extends Product implements Discountable {

    Electronics(String name, double price) {
        super(name, price);
    }

    public String getDescription() {
        return "Electronics Item: " + name;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return 0.15 * price;  // 15% discount
    }
}


class Books extends Product {

    Books(String name, double price) {
        super(name, price);
    }

    public String getDescription() {
        return "Book: " + name;
    }

    public double getPrice() {
        return price;
    }
}


class Clothing extends Product implements Discountable {

    Clothing(String name, double price) {
        super(name, price);
    }

    public String getDescription() {
        return "Clothing Item: " + name;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return 0.10 * price; // 10% discount
    }
}

public class Practical14 {
    public static void main(String[] args) {
        Product e = new Electronics("Laptop", 50000);
        Product b = new Books("Java Programming", 1200);
        Product c = new Clothing("Jacket", 3000);

        System.out.println(e.getDescription() + ", Price: ₹" + e.getPrice());
        System.out.println("Discount: ₹" + ((Discountable)e).getDiscount());   //This is called type casting.
       // c is a reference of type Product (because Product c = new Clothing(...)).

        System.out.println(b.getDescription() + ", Price: ₹" + b.getPrice());

        System.out.println(c.getDescription() + ", Price: ₹" + c.getPrice());
        System.out.println("Discount: ₹" + ((Discountable)c).getDiscount());  //Type Casting
    }
}

