import java.util.Random;
import java.util.Scanner;
import java.util.StringJoiner;

public class Main {
    static final int MAX_CODE_LENGTH = 10;
    static final int MAX_SYMBOLS = 36;

    public static String getLowerCaseAlphabet() {
        StringBuilder alphabet = new StringBuilder();
        for (char c = 'a'; c <= 'z'; c++) {
            alphabet.append(c);
        }
        return alphabet.toString();
    }

    public static String getNumericCharacters() {
        return "0123456789";
    }

    public static String getBulls(int bulls) {
        return bulls > 1 ? "bulls" : "bull";
    }

    public static String getCows(int cows) {
        return cows > 1 ? "cows" : "cow";
    }

    private static StringBuilder generateSecretCode(int length, int symbols) {
        StringBuilder code = new StringBuilder();
        String sequence = (Main.getNumericCharacters() + Main.getLowerCaseAlphabet()).substring(0, symbols);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char randomChar = sequence.charAt(random.nextInt(sequence.length() ));
            if ((i == 0 && randomChar != '0') || (i != 0 && !code.toString().contains(String.valueOf(randomChar)))) {
                code.append(randomChar);
            } else {
                i--;
            }
        }
        return code;
    }

    private static String getSequenceToDisplay(int symbols) {
        String numbers = Main.getNumericCharacters();
        String alphabet = Main.getLowerCaseAlphabet();
        char firstNumber = numbers.charAt(0);
        char lastNumber = numbers.charAt(symbols > 10 ? numbers.length() - 1 : symbols - 1);
        char firstLetter = alphabet.charAt(0);
        char lastLetter = alphabet.charAt(symbols > 10 ? symbols - 11 : 0);

        StringBuilder displaySequence = new StringBuilder();
        StringBuilder numberSequence = new StringBuilder().append(firstNumber + "-" + lastNumber);
        StringBuilder letterSequence = new StringBuilder().append(firstLetter + "-" + lastLetter);
        displaySequence.append("(").append(numberSequence).append(symbols > 10 ? (", " + letterSequence) : "").append(").");

        return displaySequence.toString();
    }

    private static void playGame(String secretCode, int symbols, Scanner scanner) {
        System.out.println("The secret is prepared: " + "*".repeat(secretCode.length()) + " " + getSequenceToDisplay(symbols));
        System.out.println("Okay, let's start a game!");

        int count = 1;
        boolean success = false;
        while (!success) {
            System.out.println("Turn " + count + ":");
            String inputStr = scanner.next();
            success = checkCowsAndBulls(secretCode, inputStr);
            count++;
        }
    }

    private static boolean checkCowsAndBulls(String secretCode, String inputStr) {
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < inputStr.length(); i++) {
            char currentChar = inputStr.charAt(i);
            if (currentChar == secretCode.charAt(i)) {
                bulls++;
            }
            if (secretCode.contains(String.valueOf(currentChar))) {
                cows++;
            }
        }
        printGrade(bulls, cows);
        if (bulls == secretCode.length()) {
            System.out.println("Congratulations! You guessed the secret code.");
            return true;
        }
        return false;
    }

    private static void printGrade(int bulls, int cows) {
        if (bulls > 0 && cows > 0) {
            System.out.printf("Grade: %d %s and %d %s%n", bulls, Main.getBulls(bulls), cows, Main.getCows(cows));
        } else if (bulls > 0) {
            System.out.printf("Grade: %d %s.%n", bulls, Main.getBulls(bulls));
        } else if (cows > 0) {
            System.out.printf("Grade: %d %s.%n", cows, Main.getCows(cows));
        } else {
            System.out.println("Grade: None.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        String input = scanner.nextLine();
        int length = 0;
        if (input.matches("\\d+")) {
            length = Integer.parseInt(input);
        } else {
            System.out.println("error, length must be a number");
            System.exit(1);
        }
        if (length > MAX_CODE_LENGTH) {
            System.out.println("error, please enter a value less than or equal to " + MAX_CODE_LENGTH + ".");
        }
        System.out.println("Input the number of possible symbols in the code:");
        int symbols = scanner.nextInt();
        if(symbols > 0 && symbols <= MAX_SYMBOLS && length > 0 && length <= MAX_CODE_LENGTH && symbols >= length && length > 0) {
            StringBuilder code = generateSecretCode(length, symbols);
            playGame(code.toString(), symbols, scanner);
        } else {
            System.out.println("error, please enter a value less than or equal to " + MAX_SYMBOLS + ".");
        }


        scanner.close();
    }

}
