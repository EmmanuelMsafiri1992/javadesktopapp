package helpers;

import java.sql.*;

public class ProcedureModel {

    public String getDataForTable(String procedures) {
        String query = "SELECT * FROM " + procedures;

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            StringBuilder result = new StringBuilder();

            while (resultSet.next()) {
                result.append(resultSet.getInt("id"))
                        .append("\t")
                        .append(resultSet.getString("procedure_name"))
                        .append("\t")
                        .append(resultSet.getString("procedure_description"))
                        .append("\n");
            }

            return result.toString();

        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed to retrieve data for " + procedures;
        }
    }

    public Object[][] getDataForProcedureManagement() {
        try (Connection connection = DatabaseHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM procedure")) {

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

    // Add method to update procedure data in the database
    public boolean updateProcedure(String procedureName, String procedureDescription, int procedureId) {
        String query = "UPDATE procedure SET procedure_name = ?, procedure_description = ? WHERE id = ?";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, procedureName);
            preparedStatement.setString(2, procedureDescription);
            preparedStatement.setInt(3, procedureId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add method to insert new procedure data into the database
    public boolean addNewProcedure(String procedureName, String procedureDescription) {
        String query = "INSERT INTO procedure (procedure_name, procedure_description) VALUES (?, ?)";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, procedureName);
            preparedStatement.setString(2, procedureDescription);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
