package Front;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import Helpers.DatabaseHelper;

import Authentication.UserAuthenticator;
import Authentication.UserManager;

public class ConsoleUI {
  public static void main(String[] args) {
        try {
            Connection connection = DatabaseHelper.getConnection();
            DatabaseHelper.createTables(connection);

            Scanner scanner = new Scanner(System.in);

            System.out.println("1. Login");
            System.out.println("2. Register");

            System.out.print("Choose option: ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                // User Login
                System.out.print("Enter username: ");
                String username = scanner.next();
                System.out.print("Enter password: ");
                String password = scanner.next();

                if (UserAuthenticator.authenticateUser(connection, username, password)) {
                    System.out.println("Login successful!");
                } else {
                    System.out.println("Invalid credentials. Login failed.");
                }
            } else if (choice == 2) {
                // User Registration
                System.out.print("Enter username: ");
                String username = scanner.next();
                System.out.print("Enter password: ");
                String password = scanner.next();
                System.out.print("Enter user type: ");
                String userType = scanner.next();

                UserManager.registerUser(connection, username, password, userType);
                System.out.println("Registration successful!");
            } else {
                System.out.println("Invalid choice.");
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
