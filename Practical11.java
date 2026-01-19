import java.util.*;

public class Practical11 {

    public static void main(String[] args) {

        // 1. VECTOR
        System.out.println("=== Vector Demo ===");
        Vector<String> names = new Vector<>();

        // Add elements
        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");
        names.add("David");

        System.out.println("Names: " + names);

        // Remove an element
        names.remove("Charlie");

        // Search for an element
        boolean found = names.contains("Bob");
        System.out.println("Is Bob in the list? " + found);

        // Sort the Vector
        Collections.sort(names);
        System.out.println("Sorted Names: " + names);

        System.out.println();

        // 2. ARRAYLIST
        System.out.println("=== ArrayList Demo ===");
        ArrayList<Integer> numbers = new ArrayList<>();

        // Add elements
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        numbers.add(40);

        System.out.println("Numbers: " + numbers);

        // Remove an element
        numbers.remove(Integer.valueOf(20));

        // Search for an element
        boolean numberFound = numbers.contains(30);
        System.out.println("Is 30 in the list? " + numberFound);

        // Iterate using for-each loop
        System.out.print("All numbers: ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }

        System.out.println("\n");

        // 3. HASHMAP
        System.out.println("=== HashMap Demo ===");
        HashMap<String, Integer> studentMarks = new HashMap<>();

        // Add key-value pairs
        studentMarks.put("Alice", 85);
        studentMarks.put("Bob", 90);
        studentMarks.put("David", 78);

        // Display all key-value pairs
        for (String student : studentMarks.keySet()) {
            System.out.println("Name: " + student + ", Marks: " + studentMarks.get(student));
        }

        // Search for a key
        String searchKey = "Bob";
        if (studentMarks.containsKey(searchKey)) {
            System.out.println(searchKey + "'s Marks: " + studentMarks.get(searchKey));
        } else {
            System.out.println(searchKey + " not found in records.");
        }
    }
}
