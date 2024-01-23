package helpers;

import java.sql.*;

public class SiteModel {

    public String getDataForTable(String site) {
        String query = "SELECT * FROM " + site;

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            StringBuilder result = new StringBuilder();

            while (resultSet.next()) {
                result.append(resultSet.getInt("id"))
                        .append("\t")
                        .append(resultSet.getString("name"))
                        .append("\t")
                        .append(resultSet.getString("site_description"))
                        .append("\n");
            }

            return result.toString();

        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed to retrieve data for " + site;
        }
    }
    // public void updateSite(String currentSiteName, String newSiteName, String newSiteDescription) {
    //     try (Connection connection = DatabaseHelper.getConnection()) {
    //         String sql = "UPDATE site SET site_name = ?, site_description = ? WHERE site_name = ?";
    //         try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
    //             preparedStatement.setString(1, newSiteName);
    //             preparedStatement.setString(2, newSiteDescription);
    //             preparedStatement.setString(3, currentSiteName);

    //             int rowsUpdated = preparedStatement.executeUpdate();

    //             if (rowsUpdated > 0) {
    //                 System.out.println("Site updated successfully");
    //             } else {
    //                 System.out.println("Site update failed");
    //             }
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }


    public Object[][] getDataForSiteManagement() {
        try (Connection connection = DatabaseHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM site")) {

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
    public void updateSite(String currentSiteName, String newSiteName) {
        try (Connection connection = DatabaseHelper.getConnection()) {
            String sql = "UPDATE site SET site_name = ? WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newSiteName);
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
    // Add method to update site data in the database
    // public boolean updateSite(String siteName, String siteDescription, int siteId) {
    //     String query = "UPDATE site SET site_name = ?, site_description = ? WHERE id = ?";

    //     try (Connection connection = DatabaseHelper.getConnection();
    //          PreparedStatement preparedStatement = connection.prepareStatement(query)) {

    //         preparedStatement.setString(1, siteName);
    //         preparedStatement.setString(2, siteDescription);
    //         preparedStatement.setInt(3, siteId);

    //         int rowsAffected = preparedStatement.executeUpdate();

    //         return rowsAffected > 0;

    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }

    // Add method to insert new site data into the database
    public boolean addNewSite(String siteName) {
        String query = "INSERT INTO site (name) VALUES (?)";

        try (Connection connection = DatabaseHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, siteName);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
