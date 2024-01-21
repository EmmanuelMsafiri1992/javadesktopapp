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
        addButton(buttonPanel, "Training Management", e -> {
            // Handle Training Management button click
        }, gbc);

        addButton(buttonPanel, "Task Management", e -> {
            // Handle Task Management button click
        }, gbc);

        addButton(buttonPanel, "Procedure Management", e -> {
            // Handle Procedure Management button click
        }, gbc);

        addButton(buttonPanel, "Site Management", e -> {
            // Handle Site Management button click
        }, gbc);

        addButton(buttonPanel, "Area Management", e -> {
            // Handle Area Management button click
        }, gbc);

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

    private void createUser() {
        // Implement create user functionality
        // ...
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

// ...

// ...
private void showUserTable() {
    JPanel userManagementPanel = new JPanel(new BorderLayout());

    tableModel = new DefaultTableModel();
    userTable = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(userTable);

    tableModel.addColumn("Name");
    tableModel.addColumn("User Type");
    tableModel.addColumn("Actions");

    // Set the custom renderer for the "Actions" column
    userTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());

    try {
        fetchPageData(currentPage);
    } catch (SQLException e) {
        handleSQLException(e, "Error fetching users.");
    }

    // Set the row height to your desired value (e.g., 30)
    userTable.setRowHeight(30);
    userManagementPanel.add(scrollPane, BorderLayout.CENTER);

// Add buttons for Add, Edit, and Delete
JButton addButton = createButton("Add", e -> createUser());
// JButton editButton = createButton("Edit", e -> showEditUserDialog());
// JButton deleteButton = createButton("Delete", e -> deleteUser());

// Access the user name from the selected row in the table
int selectedRow = userTable.getSelectedRow();
if (selectedRow != -1) {
    String userName = (String) userTable.getValueAt(selectedRow, 0); // Assuming the user name is in the first column
    // editButton.addActionListener(e -> showUserActionsDialog(userName, userType));
    // deleteButton.addActionListener(e -> deleteUser(userName));
}
    JPanel actionsPanel = new JPanel();
    actionsPanel.add(addButton);
    // actionsPanel.add(editButton);
    // actionsPanel.add(deleteButton);

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
    // Implement logic to show an edit user dialog
    // ...

    // For now, let's display a simple message dialog as a placeholder
    JOptionPane.showMessageDialog(
            this,
            "Editing user: " + name + ", UserType: " + userType,
            "Edit User",
            JOptionPane.INFORMATION_MESSAGE
    );
}

    private void showUserActionsDialog(String name, String userType) {
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
    
    // ...
    private void fetchPageData(int page) throws SQLException {
        tableModel.setRowCount(0);
    
        try (Connection connection = DatabaseHelper.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users LIMIT " +
                     ((page - 1) * rowsPerPage) + "," + rowsPerPage)) {
    
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String userType = resultSet.getString("user_type");
    
                JButton editButton = createButton("Edit", e -> showEditUserDialog(name, userType));
                JButton updateButton = createButton("Update", e -> updateUser(name));
                JButton deleteButton = createButton("Delete", e -> deleteUser(name));
                
                // editButton.addActionListener(e -> showUserActionsDialog(name, userType));
                editButton.addActionListener(e -> showEditUserDialog(name, userType));

                
    
                // Create a panel to hold the buttons
                JPanel actionsPanel = new JPanel();
                actionsPanel.add(editButton);
                actionsPanel.add(updateButton);
                actionsPanel.add(deleteButton);
    
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