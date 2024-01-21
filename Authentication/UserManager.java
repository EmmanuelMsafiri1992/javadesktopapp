package authentication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserManager {
   public static void registerUser(Connection connection, String username, String password, String userType) throws SQLException {
        String query = "INSERT INTO Users (name, password, user_type) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, userType);
            preparedStatement.executeUpdate();
        }
    }
}
