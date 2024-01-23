package front;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import helpers.DatabaseHelper;



public class AdminDashboard extends JFrame {
    private DefaultTableModel tableModel;
    private JTable userTable;
    private int currentPage = 1;
    private final int rowsPerPage = 15;
    private final int buttonWidth = 150; // Set a fixed width for buttons

    public AdminDashboard() {
        initializeUI();
            // Other initialization code...
            tableModel = new DefaultTableModel();
            userTable = new JTable(tableModel);
    }

    private void initializeUI() {
        setTitle("Admin Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createButtonPanel(), BorderLayout.WEST);
    
        // Commented out the showShiftTable method call
        showShiftTable();
    
        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        JLabel welcomeLabel = new JLabel("Welcome, Admin!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(welcomeLabel);
        return headerPanel;
    }
    private void showEditTrainingDialog(String trainingName) {
        // Implement the logic to show the edit training dialog
        // For example, you can create a new JFrame or dialog for editing the training details
        JOptionPane.showMessageDialog(
                this,
                "Editing training: " + trainingName,
                "Edit Training",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void deleteTraining(String trainingName) {
        // Implement the logic to delete the training
        // For example, you can prompt the user for confirmation before deleting
        JOptionPane.showMessageDialog(
                this,
                "Deleting training: " + trainingName,
                "Delete Training",
                JOptionPane.INFORMATION_MESSAGE
        );
    
        // After deletion, you may want to refresh the training table
        // You can call fetchTrainingData() or perform any other necessary actions
    }
    
    private void fetchTrainingData() throws SQLException {
        // Clear the existing data in the table model
        tableModel.setRowCount(0);
    
        try (Connection connection = DatabaseHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Training")) {
    
            while (resultSet.next()) {
                String trainingName = resultSet.getString("name");
                // You may have additional columns in your Training table
    
                JButton editButton = createButton("Edit", e -> showEditTrainingDialog(trainingName));
                JButton deleteButton = createButton("Delete", e -> deleteTraining(trainingName));

    
                // Create a panel to hold the buttons
                JPanel actionsPanel = new JPanel();
                actionsPanel.add(editButton);
                actionsPanel.add(deleteButton);
    
                // Add a new row with the training data and buttons
                tableModel.addRow(new Object[]{trainingName, actionsPanel});

            }
        }
    }
    
    private void showTrainingsTable() {
        JPanel trainingManagementPanel = new JPanel(new BorderLayout());
    
        // Create a new table model for training data
        DefaultTableModel trainingTableModel = new DefaultTableModel();
        JTable trainingTable = new JTable(trainingTableModel);
        JScrollPane trainingScrollPane = new JScrollPane(trainingTable);
    
        // Add columns to the training table
        trainingTableModel.addColumn("Training Name");
        trainingTableModel.addColumn("Date");
        trainingTableModel.addColumn("Location");
        trainingTableModel.addColumn("Actions");
    
        // Set the custom renderer for the "Actions" column
        trainingTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
    
        try {
            // Fetch and display existing training data
            fetchTrainingData(); // You need to implement fetchTrainingData() method
        } catch (SQLException e) {
            handleSQLException(e, "Error fetching training data.");
        }
    
        // Set the row height to your desired value (e.g., 30)
        trainingTable.setRowHeight(30);
        trainingManagementPanel.add(trainingScrollPane, BorderLayout.CENTER);
    
        // Add buttons for Edit and Delete
        // JButton addButton = createButton("Add", e -> createTraining());
        // JPanel addPanel = new JPanel();
        // addPanel.add(addButton);
    
        JPanel actionsPanel = new JPanel();
        // actionsPanel.add(addPanel);
    
        trainingManagementPanel.add(actionsPanel, BorderLayout.NORTH);
    
        // Set the editor for the "Actions" column
        trainingTable.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));
    
        JFrame tableFrame = new JFrame("Training Management");
        tableFrame.setSize(750, 400);
        tableFrame.setLocationRelativeTo(null);
        tableFrame.add(trainingManagementPanel);
        // Make the JFrame non-resizable
        tableFrame.setResizable(false);
        tableFrame.setVisible(true);
    }
    private void showTaskTable() {
        JPanel taskManagementPanel = new JPanel(new BorderLayout());
    
        // Create a new table model for task data
        DefaultTableModel taskTableModel = new DefaultTableModel();
        JTable taskTable = new JTable(taskTableModel);
        JScrollPane taskScrollPane = new JScrollPane(taskTable);
    
        // Add columns to the task table
        taskTableModel.addColumn("Task Name");
        taskTableModel.addColumn("Due Date");
        taskTableModel.addColumn("Assigned To");
        taskTableModel.addColumn("Actions");
    
        // Set the custom renderer for the "Actions" column
        taskTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
    
        try {
            // Fetch and display existing task data
            fetchTaskData(); // You need to implement fetchTaskData() method
        } catch (SQLException e) {
            handleSQLException(e, "Error fetching task data.");
        }
    
        // Set the row height to your desired value (e.g., 30)
        taskTable.setRowHeight(30);
        taskManagementPanel.add(taskScrollPane, BorderLayout.CENTER);
    
        // Add buttons for Edit and Delete
        JButton addTaskButton = createButton("Add Task", e -> createTask());
        JPanel addTaskPanel = new JPanel();
        addTaskPanel.add(addTaskButton);
    
        JPanel actionsPanel = new JPanel();
        actionsPanel.add(addTaskPanel);
    
        taskManagementPanel.add(actionsPanel, BorderLayout.NORTH);
    
        // Set the editor for the "Actions" column
        taskTable.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));
    
        JFrame taskTableFrame = new JFrame("Task Management");
        taskTableFrame.setSize(750, 400);
        taskTableFrame.setLocationRelativeTo(null);
        taskTableFrame.add(taskManagementPanel);
        // Make the JFrame non-resizable
        taskTableFrame.setResizable(false);
        taskTableFrame.setVisible(true);
    }

    private void showProcedureTable() {
        JPanel procedureManagementPanel = new JPanel(new BorderLayout());
    
        // Create a new table model for procedure data
        DefaultTableModel procedureTableModel = new DefaultTableModel();
        JTable procedureTable = new JTable(procedureTableModel);
        JScrollPane procedureScrollPane = new JScrollPane(procedureTable);
    
        // Add columns to the procedure table
        procedureTableModel.addColumn("Procedure Name");
        procedureTableModel.addColumn("Category");
        procedureTableModel.addColumn("Actions");
    
        // Set the custom renderer for the "Actions" column
        procedureTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
    
        try {
            // Fetch and display existing procedure data
            fetchProcedureData(); // You need to implement fetchProcedureData() method
        } catch (SQLException e) {
            handleSQLException(e, "Error fetching procedure data.");
        }
    
        // Set the row height to your desired value (e.g., 30)
        procedureTable.setRowHeight(30);
        procedureManagementPanel.add(procedureScrollPane, BorderLayout.CENTER);
    
        // Add buttons for Edit and Delete
        JButton addProcedureButton = createButton("Add Procedure", e -> createProcedure());
        JPanel addProcedurePanel = new JPanel();
        addProcedurePanel.add(addProcedureButton);
    
        JPanel actionsPanel = new JPanel();
        actionsPanel.add(addProcedurePanel);
    
        procedureManagementPanel.add(actionsPanel, BorderLayout.NORTH);
    
        // Set the editor for the "Actions" column
        procedureTable.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));
    
        JFrame procedureTableFrame = new JFrame("Procedure Management");
        procedureTableFrame.setSize(750, 400);
        procedureTableFrame.setLocationRelativeTo(null);
        procedureTableFrame.add(procedureManagementPanel);
        // Make the JFrame non-resizable
        procedureTableFrame.setResizable(false);
        procedureTableFrame.setVisible(true);
    }
    
    private void fetchProcedureData() throws SQLException {
        // Clear the existing data in the table model
        tableModel.setRowCount(0);
    
        try (Connection connection = DatabaseHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM procedures")) {
    
            while (resultSet.next()) {
                int procedureId = resultSet.getInt("id");
                String procedureName = resultSet.getString("name");
                String category = resultSet.getString("category");
    
                JButton editButton = createButton("Edit", e -> showEditProcedureDialog(procedureName));
                JButton deleteButton = createButton("Delete", e -> deleteProcedure(procedureName));
    
                // Create a panel to hold the buttons
                JPanel actionsPanel = new JPanel();
                actionsPanel.add(editButton);
                actionsPanel.add(deleteButton);
    
                // Add a new row with the procedure data and buttons
                tableModel.addRow(new Object[]{procedureName, category, actionsPanel});
            }
        }
    }
    
    private void showEditProcedureDialog(String procedureName) {
        // Implement the logic to show the edit procedure dialog
        // For example, you can create a new JFrame or dialog for editing the procedure details
        JOptionPane.showMessageDialog(
                this,
                "Editing procedure: " + procedureName,
                "Edit Procedure",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void deleteProcedure(String procedureName) {
        // Implement the logic to delete the procedure
        // For example, you can prompt the user for confirmation before deleting
        JOptionPane.showMessageDialog(
                this,
                "Deleting procedure: " + procedureName,
                "Delete Procedure",
                JOptionPane.INFORMATION_MESSAGE
        );
    
        // After deletion, you may want to refresh the procedure table
        // You can call fetchProcedureData() or perform any other necessary actions
    }
    
    private void createProcedure() {
        // Implement the logic to create a new procedure
        // For example, you can show a dialog for entering procedure details and insert into the database
        JOptionPane.showMessageDialog(
                this,
                "Creating a new procedure...",
                "Create Procedure",
                JOptionPane.INFORMATION_MESSAGE
        );
    
        // After creating a new procedure, you may want to refresh the procedure table
        // You can call fetchProcedureData() or perform any other necessary actions
    }
    
    private void showSiteTable() {
        JPanel siteManagementPanel = new JPanel(new BorderLayout());
    
        // Create a new table model for site data
        DefaultTableModel siteTableModel = new DefaultTableModel();
        JTable siteTable = new JTable(siteTableModel);
        JScrollPane siteScrollPane = new JScrollPane(siteTable);
    
        // Add columns to the site table
        siteTableModel.addColumn("Site Name");
        siteTableModel.addColumn("Location");
        siteTableModel.addColumn("Actions");
    
        // Set the custom renderer for the "Actions" column
        siteTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
    
        try {
            // Fetch and display existing site data
            fetchSiteData(); // You need to implement fetchSiteData() method
        } catch (SQLException e) {
            handleSQLException(e, "Error fetching site data.");
        }
    
        // Set the row height to your desired value (e.g., 30)
        siteTable.setRowHeight(30);
        siteManagementPanel.add(siteScrollPane, BorderLayout.CENTER);
    
        // Add buttons for Edit and Delete
        JButton addSiteButton = createButton("Add Site", e -> createSite());
        JPanel addSitePanel = new JPanel();
        addSitePanel.add(addSiteButton);
    
        JPanel actionsPanel = new JPanel();
        actionsPanel.add(addSitePanel);
    
        siteManagementPanel.add(actionsPanel, BorderLayout.NORTH);
    
        // Set the editor for the "Actions" column
        siteTable.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));
    
        JFrame siteTableFrame = new JFrame("Site Management");
        siteTableFrame.setSize(750, 400);
        siteTableFrame.setLocationRelativeTo(null);
        siteTableFrame.add(siteManagementPanel);
        // Make the JFrame non-resizable
        siteTableFrame.setResizable(false);
        siteTableFrame.setVisible(true);
    }
    
    private void fetchSiteData() throws SQLException {
        // Clear the existing data in the table model
        tableModel.setRowCount(0);
    
        try (Connection connection = DatabaseHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM site")) {
    
            while (resultSet.next()) {
                int siteId = resultSet.getInt("id");
                String siteName = resultSet.getString("name");
                String location = resultSet.getString("location");
    
                JButton editButton = createButton("Edit", e -> showEditSiteDialog(siteName));
                JButton deleteButton = createButton("Delete", e -> deleteSite(siteName));
    
                // Create a panel to hold the buttons
                JPanel actionsPanel = new JPanel();
                actionsPanel.add(editButton);
                actionsPanel.add(deleteButton);
    
                // Add a new row with the site data and buttons
                tableModel.addRow(new Object[]{siteName, location, actionsPanel});
            }
        }
    }
    
    private void showEditSiteDialog(String siteName) {
        // Implement the logic to show the edit site dialog
        // For example, you can create a new JFrame or dialog for editing the site details
        JOptionPane.showMessageDialog(
                this,
                "Editing site: " + siteName,
                "Edit Site",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void deleteSite(String siteName) {
        // Implement the logic to delete the site
        // For example, you can prompt the user for confirmation before deleting
        JOptionPane.showMessageDialog(
                this,
                "Deleting site: " + siteName,
                "Delete Site",
                JOptionPane.INFORMATION_MESSAGE
        );
    
        // After deletion, you may want to refresh the site table
        // You can call fetchSiteData() or perform any other necessary actions
    }
    
    private void createSite() {
        // Implement the logic to create a new site
        // For example, you can show a dialog for entering site details and insert into the database
        JOptionPane.showMessageDialog(
                this,
                "Creating a new site...",
                "Create Site",
                JOptionPane.INFORMATION_MESSAGE
        );
    
        // After creating a new site, you may want to refresh the site table
        // You can call fetchSiteData() or perform any other necessary actions
    }
    private void showAreaTable() {
        JPanel areaManagementPanel = new JPanel(new BorderLayout());
    
        // Create a new table model for area data
        DefaultTableModel areaTableModel = new DefaultTableModel();
        JTable areaTable = new JTable(areaTableModel);
        JScrollPane areaScrollPane = new JScrollPane(areaTable);
    
        // Add columns to the area table
        areaTableModel.addColumn("Area Name");
        areaTableModel.addColumn("Actions");
    
        // Set the custom renderer for the "Actions" column
        areaTable.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer());
    
        try {
            // Fetch and display existing area data
            fetchAreaData(); // You need to implement fetchAreaData() method
        } catch (SQLException e) {
            handleSQLException(e, "Error fetching area data.");
        }
    
        // Set the row height to your desired value (e.g., 30)
        areaTable.setRowHeight(30);
        areaManagementPanel.add(areaScrollPane, BorderLayout.CENTER);
    
        // Add buttons for Edit and Delete
        JButton addAreaButton = createButton("Add Area", e -> createArea());
        JPanel addAreaPanel = new JPanel();
        addAreaPanel.add(addAreaButton);
    
        JPanel actionsPanel = new JPanel();
        actionsPanel.add(addAreaPanel);
    
        areaManagementPanel.add(actionsPanel, BorderLayout.NORTH);
    
        // Set the editor for the "Actions" column
        areaTable.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));
    
        JFrame areaTableFrame = new JFrame("Area Management");
        areaTableFrame.setSize(750, 400);
        areaTableFrame.setLocationRelativeTo(null);
        areaTableFrame.add(areaManagementPanel);
        // Make the JFrame non-resizable
        areaTableFrame.setResizable(false);
        areaTableFrame.setVisible(true);
    }
    
    
    private void fetchAreaData() throws SQLException {
        // Clear the existing data in the table model
        tableModel.setRowCount(0);
    
        try (Connection connection = DatabaseHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM area")) {
    
            while (resultSet.next()) {
                // int areaId = resultSet.getInt("area_id");
                String areaName = resultSet.getString("name");
    
    
                JButton editButton = createButton("Edit", e -> showEditAreaDialog(areaName));
                JButton deleteButton = createButton("Delete", e -> deleteArea(areaName));
    
                // Create a panel to hold the buttons
                JPanel actionsPanel = new JPanel();
                actionsPanel.add(editButton);
                actionsPanel.add(deleteButton);
    
                // Add a new row with the area data and buttons
                tableModel.addRow(new Object[]{areaName, actionsPanel});
            }
        }
    }
    
    private void showEditAreaDialog(String areaName) {
        // Implement the logic to show the edit area dialog
        // For example, you can create a new JFrame or dialog for editing the area details
        JOptionPane.showMessageDialog(
                this,
                "Editing area: " + areaName,
                "Edit Area",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void deleteArea(String areaName) {
        // Implement the logic to delete the area
        // For example, you can prompt the user for confirmation before deleting
        JOptionPane.showMessageDialog(
                this,
                "Deleting area: " + areaName,
                "Delete Area",
                JOptionPane.INFORMATION_MESSAGE
        );
    
        // After deletion, you may want to refresh the area table
        // You can call fetchAreaData() or perform any other necessary actions
    }
    private void createArea() {
        // Create a panel to hold the input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JTextField areaNameField = new JTextField(20);
    
        inputPanel.add(new JLabel("Area Name:"));
        inputPanel.add(areaNameField);
    
        int result = JOptionPane.showConfirmDialog(
                this,
                inputPanel,
                "Create Area",
                JOptionPane.OK_CANCEL_OPTION
        );
    
        if (result == JOptionPane.OK_OPTION) {
            // User clicked OK, proceed with creating the area
    
            String areaName = areaNameField.getText().trim();
    
            // Validate input (you can add more validation as needed)
    
            if (!areaName.isEmpty()) {
                try (Connection connection = DatabaseHelper.getConnection()) {
                    if (connection != null) {
                        // Insert the new area into the database
                        DatabaseHelper.insertArea(connection, areaName);
    
                        // Refresh the area table after adding a new area
                        fetchAreaData();
                    }
                } catch (SQLException ex) {
                    handleSQLException(ex, "Error creating area.");
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Area Name is a required field.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    
    
    private void fetchTaskData() throws SQLException {
        // Clear the existing data in the table model
        tableModel.setRowCount(0);
    
        try (Connection connection = DatabaseHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM task")) {
    
            while (resultSet.next()) {
                int taskId = resultSet.getInt("id");
                String taskName = resultSet.getString("name");
    
                JButton editButton = createButton("Edit", e -> showEditTaskDialog(taskName));
                JButton deleteButton = createButton("Delete", e -> deleteTask(taskName));
    
                // Create a panel to hold the buttons
                JPanel actionsPanel = new JPanel();
                actionsPanel.add(editButton);
                actionsPanel.add(deleteButton);
    
                // Add a new row with the task data and buttons
                tableModel.addRow(new Object[]{taskName, actionsPanel});
            }
        }
    }
    
    
    private void showEditTaskDialog(String taskName) {
        // Implement the logic to show the edit task dialog
        // For example, you can create a new JFrame or dialog for editing the task details
        JOptionPane.showMessageDialog(
                this,
                "Editing task: " + taskName,
                "Edit Task",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void deleteTask(String taskName) {
        // Implement the logic to delete the task
        // For example, you can prompt the user for confirmation before deleting
        JOptionPane.showMessageDialog(
                this,
                "Deleting task: " + taskName,
                "Delete Task",
                JOptionPane.INFORMATION_MESSAGE
        );
    
        // After deletion, you may want to refresh the task table
        // You can call fetchTaskData() or perform any other necessary actions
    }
    
    private void createTask() {
        // Implement the logic to create a new task
        // For example, you can show a dialog for entering task details and insert into the database
        JOptionPane.showMessageDialog(
                this,
                "Creating a new task...",
                "Create Task",
                JOptionPane.INFORMATION_MESSAGE
        );
    
        // After creating a new task, you may want to refresh the task table
        // You can call fetchTaskData() or perform any other necessary actions
    }
    

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0)); // Add left margin

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addButton(buttonPanel, "User Management", e -> showUserTable(), gbc);
        addButton(buttonPanel, "Shift Management", e -> showShiftTable(), gbc);
        addButton(buttonPanel, "Training Management", e -> showTrainingsTable(), gbc); 
        addButton(buttonPanel, "Task Management", e -> showTaskTable(), gbc); 

        addButton(buttonPanel, "Procedure Management", e -> showProcedureTable(), gbc); 

        addButton(buttonPanel, "Site Management", e -> showSiteTable(), gbc); 

        addButton(buttonPanel, "Area Management", e -> showAreaTable(), gbc);

        return buttonPanel;
    }
 
    private void addButton(Container container, String buttonText, ActionListener actionListener, GridBagConstraints gbc) {
        JButton button = new JButton(buttonText);
        button.addActionListener(actionListener);
        button.setPreferredSize(new Dimension(buttonWidth, button.getPreferredSize().height));
        container.add(button, gbc);
        gbc.gridy++;
    }
    private void fetchShiftPageData(int page) {
        // Clear the existing data in the table model
        // tableModel.setRowCount(0);

        // try {
        //     Connection connection = DatabaseHelper.getConnection();
        //     if (connection != null) {
        //         System.out.println("Connection is not null"); // Add this line for debugging
    
        //         // Rest of the code...
        //     } else {
        //         System.out.println("Connection is null"); // Add this line for debugging
        //     }
        // } catch (SQLException e) {
        //     handleSQLException(e, "Error fetching shifts.");
        // }
        // try (Connection connection = DatabaseHelper.getConnection();
        //      Statement statement = connection.createStatement();
        //      ResultSet resultSet = statement.executeQuery("SELECT * FROM shifts LIMIT " +
        //              ((page - 1) * rowsPerPage) + "," + rowsPerPage)) {
    
        //     while (resultSet.next()) {
        //         String shiftName = resultSet.getString("shift_name");
        //         String startTime = resultSet.getString("start_time");
        //         String endTime = resultSet.getString("end_time");
    

    
        //         // Create a panel to hold the buttons
        //         JPanel actionsPanel = new JPanel();

    
        //         // Add a new row with the shift data and buttons
        //         tableModel.addRow(new Object[]{shiftName, startTime, endTime, actionsPanel});
        //     }
        // } catch (SQLException e) {
        //     handleSQLException(e, "Error fetching shifts.");
        // }


        // // Set the editor for the "Actions" column
        // userTable.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));
    
        // Clear the existing data in the table model
        // if (tableModel == null) {
        //     System.out.println("TableModel is null");
        //     return; // exit the method if tableModel is null
        // }
        
        // tableModel.setRowCount(0);
    
        // try {
        //     Connection connection = DatabaseHelper.getConnection();
        //     if (connection != null) {
        //         System.out.println("Connection is not null");
    
        //         // Print some information about the page
        //         System.out.println("Fetching data for page: " + page);
    
        //         // Rest of the code...
        //     } else {
        //         System.out.println("Connection is null");
        //     }
        // } catch (SQLException e) {
        //     handleSQLException(e, "Error fetching shifts.");
        // }

        try {
    Connection connection = DatabaseHelper.getConnection();
    if (connection != null) {
        System.out.println("Connection is not null"); // Add this line for debugging

        String query = "SELECT * FROM Shifts LIMIT ?, ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int offset = (page - 1) * rowsPerPage;
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, rowsPerPage);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Clear the existing rows
                tableModel.setRowCount(0);

                while (resultSet.next()) {
                    String shiftName = resultSet.getString("name");
                    String startTime = resultSet.getString("start_time");
                    String endTime = resultSet.getString("end_time");

                    // Create a panel to hold the buttons
                    JPanel actionsPanel = new JPanel();

                    // Add a new row with the shift data and buttons
                    tableModel.addRow(new Object[]{shiftName, startTime, endTime, actionsPanel});
                }
            }
        } catch (SQLException e) {
            handleSQLException(e, "Error fetching shifts.");
        }
    } else {
        System.out.println("Connection is null"); // Add this line for debugging
    }
} catch (SQLException e) {
    handleSQLException(e, "Error fetching shifts.");
}

    }
    
    

    private void createShift() {
        JPanel shiftManagementPanel = new JPanel(new BorderLayout());


        fetchShiftPageData(currentPage);
    
        // Create buttons for Shift Management
        JButton createButton = createButton("Create Shift", e -> createShift());
        // JButton editButton = createButton("Edit Shift", e -> showEditShiftDialog());
        JButton deleteButton = createButton("Delete Shift", e -> deleteShift());
    
        // Create a panel to hold the buttons
        JPanel actionsPanel = new JPanel();
        actionsPanel.add(createButton);
        // actionsPanel.add(editButton);
        actionsPanel.add(deleteButton);
    
        // Create a table model and JTable for Shift Management
        DefaultTableModel shiftTableModel = new DefaultTableModel();
        JTable shiftTable = new JTable(shiftTableModel);
        JScrollPane shiftScrollPane = new JScrollPane(shiftTable);
    
        // Add columns to the table model for Shift Management
        shiftTableModel.addColumn("Shift Name");
        shiftTableModel.addColumn("Start Time");
        shiftTableModel.addColumn("End Time");
        shiftTableModel.addColumn("Actions");
    
        // Set the custom renderer for the "Actions" column
        shiftTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
    
        shiftManagementPanel.add(actionsPanel, BorderLayout.NORTH);
        shiftManagementPanel.add(shiftScrollPane, BorderLayout.CENTER);
    

        ButtonEditor shiftButtonEditor = new ButtonEditor(new JCheckBox());

shiftTable.getColumn("Actions").setCellEditor(shiftButtonEditor);


        shiftTable.getColumn("Actions").setCellEditor(shiftButtonEditor);
    
        JFrame shiftTableFrame = new JFrame("Shift Management");
        shiftTableFrame.setSize(600, 400);
        shiftTableFrame.setLocationRelativeTo(null);
        shiftTableFrame.add(shiftManagementPanel);
        shiftTableFrame.setVisible(true);
    }

    private void showShiftTable() {
        JPanel shiftManagementPanel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel();
        JTable shiftTable = new JTable(tableModel);  // Create a new JTable for shifts
        JScrollPane scrollPane = new JScrollPane(shiftTable);
    
        tableModel.addColumn("Shift Name");
        tableModel.addColumn("Start Time");
        tableModel.addColumn("End Time");
        tableModel.addColumn("Actions");
    
        // Set the custom renderer for the "Actions" column
        shiftTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
    
        fetchShiftPageData(currentPage);
    
        shiftManagementPanel.add(scrollPane, BorderLayout.CENTER);
        shiftTable.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));
    
        JFrame tableFrame = new JFrame("Shift Management"); // Use the literal string for the title
        tableFrame.setSize(600, 400);
        tableFrame.setLocationRelativeTo(null);
        tableFrame.add(shiftManagementPanel);
        tableFrame.setVisible(true);
    }
    

    private void updateShift() {
        // Implement update shift functionality
        // ...
    }

    private void deleteShift() {
        // Implement delete shift functionality
        fetchShiftPageData(currentPage);
    }

    // private void createUser() {
    //     // Implement create user functionality
    //     // ...
    // }

    private void createUser() {
        JPanel createUserPanel = new JPanel(new BorderLayout());
    
        // Create text fields for user input
        JTextField nameField = new JTextField(20);
        JTextField userTypeField = new JTextField(20);
    
        // Create a button to submit the new user
        JButton submitButton = createButton("Submit", e -> {
            // Retrieve user input from text fields
            String name = nameField.getText();
            String userType = userTypeField.getText();
    
            // Validate user input (you can add more validation as needed)
    
            // Insert the new user into the database
            try {
                Connection connection = DatabaseHelper.getConnection();
                if (connection != null) {
                    DatabaseHelper.insertUser(connection, name, "password", userType);
                    // Refresh the user table after adding a new user
                    fetchPageData(currentPage);
                }
            } catch (SQLException ex) {
                handleSQLException(ex, "Error creating user.");
            }
        });
    
        // Create a panel to hold the input fields and submit button
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("User Type:"));
        inputPanel.add(userTypeField);
        inputPanel.add(new JLabel("")); // Empty label for spacing
        inputPanel.add(submitButton);
    
        createUserPanel.add(inputPanel, BorderLayout.CENTER);
    
        JFrame createUserFrame = new JFrame("Add New User");
        createUserFrame.setSize(400, 200);
        createUserFrame.setLocationRelativeTo(null);
        createUserFrame.add(createUserPanel);
        createUserFrame.setVisible(true);
    }
    
    private void updateUser(String name) {
        JOptionPane.showMessageDialog(
                this,
                "Updating user: " + name,
                "Update User",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void deleteUser(String name) {
        // Implement delete user functionality
        // ...
    
        JOptionPane.showMessageDialog(
                this,
                "Deleting user: " + name,
                "Delete User",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    private void showUserTable() {
        JPanel userManagementPanel = new JPanel(new BorderLayout());
    
        tableModel = new DefaultTableModel();
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
    
        tableModel.addColumn("Name");
        tableModel.addColumn("User Type");
        tableModel.addColumn("Actions");
    
          // Add buttons for Add User
    JButton addUserButton = createButton("Add User", e -> createUser());
  
        // Set the custom renderer for the "Actions" column
        userTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
    
        try {
            fetchPageData(currentPage); // Fetch and display existing users
        } catch (SQLException e) {
            handleSQLException(e, "Error fetching users.");
        }
    
        // Set the row height to your desired value (e.g., 30)
        userTable.setRowHeight(30);
        userManagementPanel.add(scrollPane, BorderLayout.CENTER);
    
        JPanel actionsPanel = new JPanel();
        // actionsPanel.add(addPanel);
    
        userManagementPanel.add(actionsPanel, BorderLayout.NORTH);
    
        // Set the editor for the "Actions" column
        userTable.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));
    
        JFrame tableFrame = new JFrame("User Management");
        tableFrame.setSize(750, 400);
        tableFrame.setLocationRelativeTo(null);
        tableFrame.add(userManagementPanel);
        // Make the JFrame non-resizable
        tableFrame.setResizable(false);
        tableFrame.setVisible(true);
    }

    private void showEditUserDialog(String name, String userType) {
        JFrame dialogFrame = new JFrame("User Actions");
        dialogFrame.setSize(300, 150);
        dialogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialogFrame.setLocationRelativeTo(null);
    
        JPanel dialogPanel = new JPanel(new FlowLayout());
    
        JButton editButton = new JButton("Edit");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
    
        // Add action listeners to the buttons
        editButton.addActionListener(e -> showEditUserDialog(name, userType));
        updateButton.addActionListener(e -> updateUser(name));
        deleteButton.addActionListener(e -> deleteUser(name));  // Pass 'name' to deleteUser method
    
        dialogPanel.add(editButton);
        dialogPanel.add(updateButton);
        dialogPanel.add(deleteButton);
    
        dialogFrame.add(dialogPanel);
        dialogFrame.setVisible(true);
    }
    

    private void showEditUserDialog(String name, String jobTitle, String userType) {
        JFrame dialogFrame = new JFrame("User Actions");
        dialogFrame.setSize(300, 150);
        dialogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialogFrame.setLocationRelativeTo(null);
    
        JPanel dialogPanel = new JPanel(new FlowLayout());
    
        JButton editButton = new JButton("Edit");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
    
        // Add action listeners to the buttons
        editButton.addActionListener(e -> showEditUserDialog(name, jobTitle, userType));
        updateButton.addActionListener(e -> updateUser(name));
        deleteButton.addActionListener(e -> deleteUser(name));  // Pass 'name' to deleteUser method
    
        dialogPanel.add(editButton);
        dialogPanel.add(updateButton);
        dialogPanel.add(deleteButton);
    
        dialogFrame.add(dialogPanel);
        dialogFrame.setVisible(true);
    }
    


    private void fetchPageData(int page) throws SQLException {
        tableModel.setRowCount(0);
    
        try (Connection connection = DatabaseHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users LIMIT " +
                     ((page - 1) * rowsPerPage) + "," + rowsPerPage)) {
    
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String job_title = resultSet.getString("job_title");
                String userType = resultSet.getString("user_type");
    
                JButton editButton = createButton("Edit", e -> showEditUserDialog(name, userType));

                JButton deleteButton = createButton("Delete", e -> deleteUser(name));
    
                // Create a panel to hold the buttons
                JPanel actionsPanel = new JPanel();
                                // Add the "Add" button to the panel
                                JButton addButton = createButton("Add", e -> createUser());
                                actionsPanel.add(addButton);
                actionsPanel.add(editButton);
                actionsPanel.add(deleteButton);
    


                // JButton addButton = createButton("Add", e -> createUser());
    
                // Add a new row with the user data and buttons
                tableModel.addRow(new Object[]{name, userType, actionsPanel});
            }
        }
    
        // Set the editor for the "Actions" column
        userTable.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));
    }
 
    
    private JButton createButton(String buttonText, ActionListener actionListener) {
        JButton button = new JButton(buttonText);
        button.addActionListener(actionListener);
        return button;
    }

    private void handleSQLException(SQLException ex, String additionalMessage) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(
                this,
                "Error: " + additionalMessage + "\nDetails: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    class ButtonRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JPanel) {
                JPanel panel = (JPanel) value;
                panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                panel.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
                return panel;
            } else {
                return new JLabel(value.toString());
            }
        }

    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard());
    }
}