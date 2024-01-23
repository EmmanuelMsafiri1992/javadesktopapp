package front;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import helpers.DatabaseHelper;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TrainingWindow extends JFrame {
    private JTable userTable;
    private DefaultTableModel tableModel;

    public TrainingWindow() {
        // Set frame properties
        setTitle("Train Employees");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize the table model
        tableModel = new DefaultTableModel();
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);

        // Add columns to the user table
        tableModel.addColumn("Name");
        tableModel.addColumn("Department");
        tableModel.addColumn("Job Title");
        tableModel.addColumn("Shift");
        tableModel.addColumn("Task");
        tableModel.addColumn("Start Date");
        tableModel.addColumn("Finish Date");
        tableModel.addColumn("Badge Color");
        tableModel.addColumn("Training Name");
        tableModel.addColumn("Site Name");

        // Set the row height
        userTable.setRowHeight(30);

        // Add components to the training window
        add(scrollPane, BorderLayout.CENTER);

        // Fetch and display user data
        fetchUserData();

        // Display the frame
        setVisible(true);
    }

    // Method to fetch and display user data
    private void fetchUserData() {
        try {
            // Replace the following with your actual data retrieval logic
            // For demonstration, I'm using a list of Object arrays
            List<Object[]> userDataList = fetchDataFromDatabase();

            // Add data to the table model
            for (Object[] userData : userDataList) {
                tableModel.addRow(userData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions or log them as needed
        }
    }

    private List<Object[]> fetchDataFromDatabase() {
        List<Object[]> userDataList = new ArrayList<>();

        try (Connection connection = DatabaseHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM employees")) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String department = resultSet.getString("department");
                String jobTitle = resultSet.getString("job_title");
                String shift = resultSet.getString("shift");
                String task = resultSet.getString("task");
                String startDate = resultSet.getString("start_date");
                String finishDate = resultSet.getString("finish_date");
                String badgeColor = resultSet.getString("badge_color");
                String trainingName = resultSet.getString("training_name");
                String siteName = resultSet.getString("site_name");

                Object[] userData = {name, department, jobTitle, shift, task, startDate, finishDate, badgeColor, trainingName, siteName};
                userDataList.add(userData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exceptions
        }

        return userDataList;
    }

    public static void main(String[] args) {
        // Create an instance of the TrainingWindow
        SwingUtilities.invokeLater(() -> new TrainingWindow());
    }
}
