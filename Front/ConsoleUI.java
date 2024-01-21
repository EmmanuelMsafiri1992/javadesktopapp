package front;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import helpers.DatabaseHelper;
import authentication.UserAuthenticator;

public class ConsoleUI {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseHelper.getConnection();
            DatabaseHelper.createTables(connection);

            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Please enter your credentials to login:");

                System.out.print("Username: ");
                String username = scanner.next();

                System.out.print("Password: ");
                String password = scanner.next();

                if (UserAuthenticator.authenticateUser(connection, username, password)) {
                    System.out.println("Login successful!");
                    // Add logic for logged-in user actions if needed
                } else {
                    System.out.println("Invalid credentials. Login failed.");
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


