import java.util.Scanner;

public class Practical10 {

    
    public static String reverseString(String input) {
        char[] chars = input.toCharArray();
        String reversed = "";
        for (int i = chars.length - 1; i >= 0; i--) {
            reversed += chars[i];
        }
        return reversed;
    }

   
    public static boolean isPalindrome(String input) {
        String cleanInput = input.replaceAll("\\s+", "").toLowerCase();
        int left = 0;
        int right = cleanInput.length() - 1;

        while (left < right) {
            if (cleanInput.charAt(left) != cleanInput.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    
    public static void countVowelsAndConsonants(String input) {
        int vowels = 0, consonants = 0;
        input = input.toLowerCase();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                switch (ch) {
                    case 'a': case 'e': case 'i': case 'o': case 'u':
                        vowels++;
                        break;
                    default:
                        consonants++;
                }
            }
        }

        System.out.println("Vowels: " + vowels);
        System.out.println("Consonants: " + consonants);
    }

    
    public static String removeDuplicates(String input) {
        boolean[] seen = new boolean[256];
        String result = "";

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (!seen[ch]) {
                result += ch;
                seen[ch] = true;
            }
        }

        return result;
    }

   
    public static char mostFrequentChar(String input) {
        int[] count = new int[256];
        int max = 0;
        char maxChar = ' ';

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch != ' ') {
                count[ch]++;
                if (count[ch] > max) {
                    max = count[ch];
                    maxChar = ch;
                }
            }
        }

        return maxChar;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        System.out.println("\n1. Reversed String: " + reverseString(input));
        System.out.println("2. Is Palindrome? " + (isPalindrome(input) ? "Yes" : "No"));
        System.out.println("3. Vowel and Consonant Count:");
        countVowelsAndConsonants(input);
        System.out.println("4. String after removing duplicates: " + removeDuplicates(input));
        System.out.println("5. Most frequent character: " + mostFrequentChar(input));

        scanner.close();
    }
}

