package helpers;

import java.sql.*;

public class ShiftModel {

    public String getDataForTable(String shifts) {
        String query = "SELECT * FROM " + shifts;

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            StringBuilder result = new StringBuilder();

            while (resultSet.next()) {
                result.append(resultSet.getInt("id"))
                        .append("\t")
                        .append(resultSet.getString("shift_name"))
                        .append("\t")
                        .append(resultSet.getString("shift_description"))
                        .append("\n");
            }

            return result.toString();

        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed to retrieve data for " + shifts;
        }
    }

    public Object[][] getDataForShiftManagement() {
        try (Connection connection = DatabaseHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM shift")) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            Object[][] data = new Object[100][columnCount];

            int row = 0;
            while (resultSet.next()) {
                for (int col = 0; col < columnCount; col++) {
                    data[row][col] = resultSet.getObject(col + 1);
                }
                row++;
            }

            Object[][] trimmedData = new Object[row][columnCount];
            System.arraycopy(data, 0, trimmedData, 0, row);

            return trimmedData;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Add method to update shift data in the database
    public boolean updateShift(String shiftName, String shiftDescription, int shiftId) {
        String query = "UPDATE shift SET shift_name = ?, shift_description = ? WHERE id = ?";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, shiftName);
            preparedStatement.setString(2, shiftDescription);
            preparedStatement.setInt(3, shiftId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add method to insert new shift data into the database
    public boolean addNewShift(String shiftName, String shiftDescription) {
        String query = "INSERT INTO shift (shift_name, shift_description) VALUES (?, ?)";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, shiftName);
            preparedStatement.setString(2, shiftDescription);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
