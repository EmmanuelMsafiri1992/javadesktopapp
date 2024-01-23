package helpers;

import java.sql.*;

public class TrainerModel {

    public String getDataForTable(String training) {
        String query = "SELECT * FROM " + training;

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            StringBuilder result = new StringBuilder();

            while (resultSet.next()) {
                result.append(resultSet.getInt("id"))
                        .append("\t")
                        .append(resultSet.getString("name"))
                        .append("\t")
                        .append(resultSet.getString("description"))
                        .append("\n");
            }

            return result.toString();

        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed to retrieve data for " + training;
        }
    }
        public void updateSite(String currentSiteName, String newSiteName, String newSiteDescription) {
        try (Connection connection = DatabaseHelper.getConnection()) {
            String sql = "UPDATE site SET site_name = ?, site_description = ? WHERE site_name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newSiteName);
                preparedStatement.setString(2, newSiteDescription);
                preparedStatement.setString(3, currentSiteName);

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Site updated successfully");
                } else {
                    System.out.println("Site update failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateArea(String currentAreaName, String newAreaName) {
        String sql = "UPDATE area SET name = ? WHERE name = ?";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, newAreaName);
            preparedStatement.setString(2, currentAreaName);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Area updated successfully");
            } else {
                System.out.println("Area update failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewArea(String areaName) {
        String sql = "INSERT INTO area (name) VALUES (?)";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, areaName);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Object[][] getDataForAreaManagement() {
        try (Connection connection = DatabaseHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM area")) {

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
}
