package helpers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import helpers.User;
import java.sql.SQLException;

public class DatabaseHelper {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/employmanager";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private DatabaseHelper(){
        
    }
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

    public static void createTables(Connection connection) {
        try {
            createDepartmentTable(connection);
            createShiftsTable(connection);
            createTaskTable(connection);
            createAreaTable(connection);
            createSiteTable(connection);
            createTrainingTable(connection);
            createProceduresTable(connection);
            createUsersTable(connection);
            createTaskProceduresTable(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
    public static void createDepartmentTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS Department (" +
                "department_id INT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL)";
        executeUpdate(connection, query);
    }

    public static void createShiftsTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS Shifts (" +
                "shift_id INT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "start_time VARCHAR(255) NOT NULL, " +
                "end_time VARCHAR(255) NOT NULL)";
        executeUpdate(connection, query);
    }

    public static void createTaskTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS Task (" +
                "task_id INT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "status VARCHAR(50) NOT NULL DEFAULT 'Pending')";
        executeUpdate(connection, query);
    }

    public static void createAreaTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS Area (" +
                "area_id INT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL)";
        executeUpdate(connection, query);
    }

    public static void createSiteTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS Site (" +
                "site_id INT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL)";
        executeUpdate(connection, query);
    }

    public static void createTrainingTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS Training (" +
                "training_id INT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL)";
        executeUpdate(connection, query);
    }

    public static void createProceduresTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS Procedures (" +
                "procedure_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(255) NOT NULL, " +
                "active VARCHAR(255) NOT NULL, " +
                "key_number INT NOT NULL, " +
                "release_date DATE NOT NULL, " +
                "comment_on_status VARCHAR(255) NOT NULL, " +
                "department_id INT, " +
                "reads_and_understand VARCHAR(255) NOT NULL, " +
                "training_id INT, " +
                "FOREIGN KEY (department_id) REFERENCES Department(department_id) ON DELETE CASCADE, " +
                "FOREIGN KEY (training_id) REFERENCES Training(training_id) ON DELETE CASCADE" +
                ")";
        executeUpdate(connection, query);
    }

    public static void createUsersTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS Users (" +
        "wwid INT PRIMARY KEY AUTO_INCREMENT, " +
        "name VARCHAR(255) NOT NULL, " +
        "password VARCHAR(255) NOT NULL, " +
        "activated BOOLEAN DEFAULT 0, " +
        "department_id INT, " +
        "job_title VARCHAR(255), " +
        "shift_id INT, " +
        "task_id INT, " +      
        "status VARCHAR(255), " +
        "start_date DATE, " +
        "leaving_date DATE, " +
        "badge_color VARCHAR(255), " +
        "reason_for_leaving VARCHAR(255), " +
        "area_id INT, " +
        "training_id INT, " +   
        "user_type VARCHAR(255), " +
        "site_id INT, " +
        "FOREIGN KEY (department_id) REFERENCES Department(department_id) ON DELETE CASCADE, " +
        "FOREIGN KEY (shift_id) REFERENCES Shifts(shift_id) ON DELETE CASCADE, " +
        "FOREIGN KEY (task_id) REFERENCES Task(task_id) ON DELETE CASCADE, " +
        "FOREIGN KEY (area_id) REFERENCES Area(area_id) ON DELETE CASCADE, " +
        "FOREIGN KEY (site_id) REFERENCES Site(site_id) ON DELETE CASCADE, " +
        "FOREIGN KEY (training_id) REFERENCES Training(training_id) ON DELETE CASCADE" +
        ")";
executeUpdate(connection, query);
// Insert default admin user
insertDefaultAdminUser(connection);
    }
  private static void insertDefaultAdminUser(Connection connection) throws SQLException {
        String adminUsername = "admin@gmail.com";
        String adminPassword = "admin";

        String query = "INSERT INTO Users (wwid, name, password, activated, user_type) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, 1); // Use a unique ID for the admin user
            preparedStatement.setString(2, adminUsername);
            preparedStatement.setString(3, adminPassword);
            preparedStatement.setBoolean(4, true); // Activated
            preparedStatement.setString(5, "admin");

            preparedStatement.executeUpdate();
        }
    }

    public static void createTaskProceduresTable(Connection connection) throws SQLException {
        String query = "CREATE TABLE IF NOT EXISTS TaskProcedures (" +
                "task_id INT, " +
                "procedure_id INT, " +
                "PRIMARY KEY (task_id, procedure_id), " +
                "FOREIGN KEY (task_id) REFERENCES Task(task_id) ON DELETE CASCADE, " +
                "FOREIGN KEY (procedure_id) REFERENCES Procedures(procedure_id) ON DELETE CASCADE" +
                ")";
        executeUpdate(connection, query);
    }

    private static void executeUpdate(Connection connection, String query) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed SQL query: " + query);
            e.printStackTrace();
            throw e;
        }
    }
    public static void insertUser(Connection connection, String name, String password, String userType) throws SQLException {
        String query = "INSERT INTO users (name, password, user_type) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, userType);

            preparedStatement.executeUpdate();
        }
    }
    public static void updateUser(Connection connection, String userId, String name, String password, String userType) throws SQLException {
        // Implementation to update user in the database
        // Use the provided parameters to construct your SQL update statement
        // Execute the update statement using the provided connection
    }
    
    public static boolean checkUserExists(Connection connection, String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM Users WHERE name = ?";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
    
        return false;
    }
    
public static User getUserById(int userId) throws SQLException {
    try (Connection connection = getConnection()) {
        String query = "SELECT * FROM Users WHERE wwid = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve user details from the result set
                    String name = resultSet.getString("name");
                    // Retrieve other user details as needed

                    // Create a User object and set the details
                    User user = new User();
                    user.setWwid(userId);
                    user.setName(name);
                    // Set other user details as needed

                    return user;
                }
            }
        }
    }
    return null; // Return null if user not found
}
    
}
