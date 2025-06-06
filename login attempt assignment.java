import java.util.Scanner;

public class LoginFeature {
    private static final String CORRECT_USERNAME = "raekwon";
    private static final String CORRECT_PASSWORD = "barksdale";
    private static final int MAX_ATTEMPTS = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Login System ===");
        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            attempts++;

            // Prompt for username
            System.out.print("Enter username: ");
            String username = scanner.nextLine().trim();

            // Prompt for password with asterisk display
            String password = promptPassword(scanner);

            // Validate credentials
            if (username.equals(CORRECT_USERNAME) && password.equals(CORRECT_PASSWORD)) {
                System.out.println("Login successful. Welcome, " + username + ".");
                break;
            } else {
                printErrorMessage(username, password, attempts);
            }
        }

        scanner.close();
    }

    private static String promptPassword(Scanner scanner) {
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        // Print asterisks
        System.out.print("Password entered: ");
        for (int i = 0; i < password.length(); i++) {
            System.out.print("*");
        }
        System.out.println();

        // Validate password
        if (password.isEmpty()) {
            System.out.println("Password cannot be empty. Please try again.");
            return promptPassword(scanner); // Recursive retry
        }

        return password;
    }

    private static void printErrorMessage(String username, String password, int attempts) {
        if (!username.equals(CORRECT_USERNAME) && !password.equals(CORRECT_PASSWORD)) {
            System.out.println("Both username and password are incorrect. Access Denied.");
        } else if (!username.equals(CORRECT_USERNAME)) {
            System.out.println("Username is incorrect.");
        } else {
            System.out.println("Password is incorrect.");
        }

        int remainingAttempts = MAX_ATTEMPTS - attempts;
        if (remainingAttempts > 0) {
            System.out.println("Attempts remaining: " + remainingAttempts);
        } else {
            System.out.println("Maximum attempts reached. Access denied.");
        }
    }
}
