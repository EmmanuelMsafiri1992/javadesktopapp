package front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login() {
        // Set frame properties
        setTitle("Employee Management & Training App");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen
        setResizable(false);

        // Set layout manager
        setLayout(new BorderLayout());

        // Add title label at top center
        JLabel titleLabel = new JLabel("Employee Management & Training App", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Create panel for login components
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));

        // Add login components
        loginPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        loginPanel.add(usernameField);

        loginPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        loginPanel.add(passwordField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (authenticateUser()) {
                    // Retrieve user type before calling openEmployeeDashboard
                    String userType = getUserType();
                    JOptionPane.showMessageDialog(Login.this, "Login successful!");
                    // Call openEmployeeDashboard with userType parameter
                    openEmployeeDashboard(userType);
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Invalid credentials. Try again.");
                }

                // Clear password field after checking
                passwordField.setText("");
            }
        });
        loginPanel.add(submitButton);

        // Add login panel to the center of the frame
        add(loginPanel, BorderLayout.CENTER);

        // Display the frame
        setVisible(true);
    }

    private String getUserType() {
        // Retrieve user information from the database
        String jdbcUrl = "jdbc:mysql://localhost:3306/employmanager";
        String dbUsername = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            String query = "SELECT user_type FROM Users WHERE name = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, usernameField.getText());
                preparedStatement.setString(2, new String(passwordField.getPassword()));

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("user_type");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void openDashboard(String userType) {
        SwingUtilities.invokeLater(() -> {
            if ("admin".equals(userType)) {
                // ... Existing code
            } else if ("trainer".equals(userType)) {
                // ... Existing code
            } else if ("supervisor".equals(userType)) {
                // ... Existing code
            } else if ("coordinator".equals(userType)) {
                // ... Existing code
            } else if ("employee".equals(userType)) {
                new EmployeeDashboard();
            } else {
                // Default case, open a generic user dashboard
                new EmployeeDashboard();
            }
        });
    }

    

    private boolean authenticateUser() {
        // TODO: Implement database authentication logic
        // Retrieve user information from the database
        String jdbcUrl = "jdbc:mysql://localhost:3306/employmanager";
        String dbUsername = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            String query = "SELECT user_type FROM Users WHERE name = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, usernameField.getText());
                preparedStatement.setString(2, new String(passwordField.getPassword()));

                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void openEmployeeDashboard(String userType) {
        SwingUtilities.invokeLater(() -> {
            if ("admin".equals(userType)) {
                // If user is admin, show welcome message
                JOptionPane.showMessageDialog(Login.this, "Welcome, Admin! Login successful!");
                new AdminDashboard();
                // Hide the login screen
                setVisible(false);
            } else {
                // Only show this message for non-admin users
                JOptionPane.showMessageDialog(Login.this, "Welcome to the Employee Dashboard!");
                new EmployeeDashboard();
                // Hide the login screen
                setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(() -> new Login());
    }
}
