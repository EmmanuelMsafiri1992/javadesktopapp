package helpers;

import java.sql.*;

public class TaskModel {

    public String getDataForTable(String task) {
        String query = "SELECT * FROM " + task;

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            StringBuilder result = new StringBuilder();

            while (resultSet.next()) {
                result.append(resultSet.getInt("id"))
                        .append("\t")
                        .append(resultSet.getString("task_name"))
                        .append("\t")
                        .append(resultSet.getString("task_description"))
                        .append("\n");
            }

            return result.toString();

        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed to retrieve data for " + task;
        }
    }

    public Object[][] getDataForTaskManagement() {
        try (Connection connection = DatabaseHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM task")) {

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

    // Add method to update task data in the database
    public boolean updateTask(String taskName, String taskDescription, int taskId) {
        String query = "UPDATE task SET task_name = ?, task_description = ? WHERE id = ?";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, taskName);
            preparedStatement.setString(2, taskDescription);
            preparedStatement.setInt(3, taskId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add method to insert new task data into the database
    public boolean addNewTask(String taskName, String taskDescription) {
        String query = "INSERT INTO task (task_name, task_description) VALUES (?, ?)";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, taskName);
            preparedStatement.setString(2, taskDescription);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
