package Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHelper {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/employmanager";
    private static final String USERNAME = "root"; 
    private static final String PASSWORD = ""; 

    static {
        try {     
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public static void createTables(Connection connection) throws SQLException {
        createUsersTable(connection);
        createOtherTables(connection);
        // Add other table creation calls as needed
    }
    private static void createUsersTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS Users (" +
                "wwid INT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "password VARCHAR(255) NOT NULL," +
                "activated BOOLEAN NOT NULL," +
                "department_id INT," +
                "job_title VARCHAR(255)," +
                "shift_id INT," +
                "task_id INT," +
                "status VARCHAR(255)," +
                "start_date DATE," +
                "leaving_date DATE," +
                "badge_color VARCHAR(255)," +
                "reason_for_leaving VARCHAR(255)," +
                "area_id INT," +
                "user_type VARCHAR(255)," +
                "site_id INT," +
                "FOREIGN KEY (department_id) REFERENCES Department(department_id) ON DELETE CASCADE," +
                "FOREIGN KEY (shift_id) REFERENCES Shifts(shift_id) ON DELETE CASCADE," +
                "FOREIGN KEY (task_id) REFERENCES Task(task_id) ON DELETE CASCADE," +
                "FOREIGN KEY (area_id) REFERENCES Area(area_id) ON DELETE CASCADE," +
                "FOREIGN KEY (site_id) REFERENCES Site(site_id) ON DELETE CASCADE" +
                ")";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        }
    }
    
    }
    

    private static void createOtherTables(Connection connection) throws SQLException {
        // Implement table creation statements for other tables
        // ...
    }
}
