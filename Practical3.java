import java.util.Random;
import java.util.Scanner;

public class Practical3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int numberToGuess = random.nextInt(100) + 1; // Generates number between 1 and 100
        int numberOfAttempts = 5;

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("Guess the number between 1 and 100. You have " + numberOfAttempts + " attempts.");

        for (int attempt = 1; attempt <= numberOfAttempts; attempt++) {
            System.out.print("Attempt " + attempt + ": Enter your guess: ");
            int guess = scanner.nextInt();

            if (guess == numberToGuess) {
                System.out.println("Congratulations! You guessed the correct number!");
                break;
            } else if (guess < numberToGuess) {
                System.out.println("Too low!");
            } else {
                System.out.println("Too high!");
            }

            if (attempt == numberOfAttempts) {
                System.out.println("Sorry! You've used all your attempts.");
                System.out.println("The correct number was: " + numberToGuess);
            }
        }

        scanner.close();
    }
}

