package front;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Import statement for TrainerModel
import helpers.TrainerModel;
import helpers.SiteModel;
import helpers.TaskModel;
import helpers.ProcedureModel;
import helpers.ShiftModel;

public class TrainerDashboard extends JFrame {

    private final TrainerModel trainerModel;
    private final SiteModel siteModel;
    private final ShiftModel shiftModel;
    private final TaskModel taskModel;
    private final ProcedureModel procedureModel;
    

    public TrainerDashboard() {
        // Set frame properties
        setTitle("Trainer Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        trainerModel = new TrainerModel(); // Instantiate TrainerModel
        siteModel = new SiteModel();
        shiftModel = new ShiftModel();
        taskModel = new TaskModel();
        procedureModel = new ProcedureModel();
        // Set layout manager
        setLayout(new BorderLayout());

        // Add components to the trainer dashboard
        JLabel welcomeLabel = new JLabel("Welcome, Trainer!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        // Add buttons with specific actions
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        addButton(buttonPanel, "Shift Management", e -> showTable("Shifts"), gbc);
        addButton(buttonPanel, "Training Management", e -> showTable("Training"), gbc);
        addButton(buttonPanel, "Task Management", e -> showTable("Task"), gbc);
        addButton(buttonPanel, "Procedure Management", e -> showTable("Procedures"), gbc);
        addButton(buttonPanel, "Site Management", e -> showSiteManagement(), gbc);
        addButton(buttonPanel, "Area Management", e -> showAreaManagement(), gbc);

        // Add buttons to the center of the layout
        add(buttonPanel, BorderLayout.CENTER);

        // Display the frame
        setVisible(true);
    }

    private void addButton(JPanel panel, String label, ActionListener actionListener, GridBagConstraints gbc) {
        JButton button = new JButton(label);
        button.addActionListener(actionListener);

        // Set preferred size to make all buttons the same length
        button.setPreferredSize(new Dimension(200, 40));

        panel.add(button, gbc);
        gbc.gridy++; // Move to the next row for the next button
    }

    private void showTable(String tableName) {
        // Fetch data from the database based on the tableName
        String tableData = trainerModel.getDataForTable(tableName);

        // Display the data in a message dialog
        JOptionPane.showMessageDialog(this, "Showing " + tableName + " Table\n\n" + tableData);
    }

    private void showAreaManagement() {
        // Fetch data for the "Area" table
        String[] columnNames = {"ID", "Name"};
        Object[][] rowData = trainerModel.getDataForAreaManagement();

     // Create a table model
     DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Disable editing for all cells
    }
        };

        // Create a JTable with the table model
        JTable areaTable = new JTable(tableModel);

        // Create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(areaTable);

        // Create a JDialog for the popup
        JDialog areaDialog = new JDialog(this, "Area Management", true);
        areaDialog.setLayout(new BorderLayout());
        areaDialog.add(scrollPane, BorderLayout.CENTER);

        // Add "Add" and "Edit" buttons to the dialog
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");

        addButton.addActionListener(e -> handleAddButtonClick(areaDialog, areaTable));
        editButton.addActionListener(e -> handleEditButtonClick(areaDialog, areaTable));


        buttonPanel.add(addButton);
        buttonPanel.add(editButton);

        areaDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Set dialog properties
        areaDialog.setSize(400, 300);
        areaDialog.setLocationRelativeTo(this);
        areaDialog.setVisible(true);
    }

    // private void handleAddButtonClick(JDialog dialog, JTable areaTable) {
    //     // Implement logic for "Add" button
    //     String newAreaName = JOptionPane.showInputDialog(dialog, "Enter new Area Name:");

    //     // Validate and process the newAreaName as needed
    //     if (newAreaName != null && !newAreaName.isEmpty()) {
    //         // Check if the area already exists
    //         boolean areaExists = false;
    //         for (int row = 0; row < areaTable.getRowCount(); row++) {
    //             String existingName = (String) areaTable.getValueAt(row, 1); // Assuming "Area Name" is in the second column (index 1)
    //             if (newAreaName.equals(existingName)) {
    //                 areaExists = true;
    //                 break;
    //             }
    //         }

    //         if (!areaExists) {
    //             // Add the new area to the table
    //             Object[] newRow = {areaTable.getRowCount() + 1, newAreaName, "Actions"};
    //             ((DefaultTableModel) areaTable.getModel()).addRow(newRow);

    //             // Display a confirmation message
    //             JOptionPane.showMessageDialog(dialog, "New Area Added: " + newAreaName);
    //         } else {
    //             // Area already exists
    //             JOptionPane.showMessageDialog(dialog, "Area already exists.", "Error", JOptionPane.ERROR_MESSAGE);
    //         }
    //     } else {
    //         // User canceled or entered an invalid name
    //         JOptionPane.showMessageDialog(dialog, "Operation canceled or invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
    //     }
    // }
    private void refreshAreaTableData(JTable areaTable) {
        // Fetch updated data for the "Area" table from the database
        Object[][] rowData = trainerModel.getDataForAreaManagement();

        // Get the existing table model
        DefaultTableModel tableModel = (DefaultTableModel) areaTable.getModel();

        // Remove all existing rows from the table model
        tableModel.setRowCount(0);

        // Add the new rows to the table model
        for (Object[] row : rowData) {
            tableModel.addRow(row);
        }
    }

    private void handleAddButtonClick(JDialog dialog, JTable areaTable) {
        // Implement logic for "Add" button
        String newAreaName = JOptionPane.showInputDialog(dialog, "Enter new Area Name:");
    
        // Validate and process the newAreaName as needed
        if (newAreaName != null && !newAreaName.isEmpty()) {
            // Check if the area already exists
            boolean areaExists = false;
            for (int row = 0; row < areaTable.getRowCount(); row++) {
                String existingName = (String) areaTable.getValueAt(row, 1); // Assuming "Area Name" is in the second column (index 1)
                if (newAreaName.equals(existingName)) {
                    areaExists = true;
                    break;
                }
            }
    
            if (!areaExists) {
                // Call TrainerModel method to add new area to the database
                trainerModel.addNewArea(newAreaName);
    
                // Refresh the table data from the database
                refreshAreaTableData(areaTable);
    
                // Display a confirmation message
                JOptionPane.showMessageDialog(dialog, "New Area Added: " + newAreaName);
            } else {
                // Area already exists
                JOptionPane.showMessageDialog(dialog, "Area already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // User canceled or entered an invalid name
            JOptionPane.showMessageDialog(dialog, "Operation canceled or invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    // private void handleEditButtonClick(JDialog dialog, JTable areaTable) {
    //     // Get the selected row index
    //     int selectedRow = areaTable.getSelectedRow();
    
    //     // Check if a row is selected
    //     if (selectedRow != -1) {
    //         // Get the current area name from the selected row
    //         String currentAreaName = (String) areaTable.getValueAt(selectedRow, 1);  // Assuming "Name" is in the second column (index 1)
    
    //         // Prompt the user for a new area name
    //         String newAreaName = JOptionPane.showInputDialog(dialog, "Edit Area Name:", currentAreaName);
    
    //         // Validate and process the newAreaName as needed
    //         if (newAreaName != null && !newAreaName.isEmpty()) {
    //             // Check if the edited area name already exists
    //             boolean areaExists = false;
    //             for (int row = 0; row < areaTable.getRowCount(); row++) {
    //                 if (row != selectedRow) {
    //                     String existingName = (String) areaTable.getValueAt(row, 1); // Assuming "Area Name" is in the second column (index 1)
    //                     if (newAreaName.equals(existingName)) {
    //                         areaExists = true;
    //                         break;
    //                     }
    //                 }
    //             }
    
    //             if (!areaExists) {
    //                 // Update the area name in the table
    //                 areaTable.setValueAt(newAreaName, selectedRow, 1);  // Assuming "Name" is in the second column (index 1)
    
    //                 // Display a confirmation message
    //                 JOptionPane.showMessageDialog(dialog, "Area Updated: " + newAreaName);
    //             } else {
    //                 // Edited area name already exists
    //                 JOptionPane.showMessageDialog(dialog, "Area already exists.", "Error", JOptionPane.ERROR_MESSAGE);
    //             }
    //         } else {
    //             // User canceled or entered an invalid name
    //             JOptionPane.showMessageDialog(dialog, "Operation canceled or invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
    //         }
    //     } else {
    //         // No row selected, prompt the user to select a row
    //         JOptionPane.showMessageDialog(dialog, "Please select a row to edit.", "Error", JOptionPane.ERROR_MESSAGE);
    //     }
    // }
    private void handleEditButtonClick(JDialog dialog, JTable areaTable) {
        // Get the selected row index
        int selectedRow = areaTable.getSelectedRow();
    
        // Check if a row is selected
        if (selectedRow != -1) {
            // Get the current area name from the selected row
            String currentAreaName = (String) areaTable.getValueAt(selectedRow, 1);  // Assuming "Name" is in the second column (index 1)
    
            // Prompt the user for a new area name
            String newAreaName = JOptionPane.showInputDialog(dialog, "Edit Area Name:", currentAreaName);
    
            // Validate and process the newAreaName as needed
            if (newAreaName != null && !newAreaName.isEmpty()) {
                // Check if the edited area name already exists
                boolean areaExists = false;
                for (int row = 0; row < areaTable.getRowCount(); row++) {
                    if (row != selectedRow) {
                        String existingName = (String) areaTable.getValueAt(row, 1); // Assuming "Area Name" is in the second column (index 1)
                        if (newAreaName.equals(existingName)) {
                            areaExists = true;
                            break;
                        }
                    }
                }
    
                if (!areaExists) {
                    // Call TrainerModel method to update area in the database
                    trainerModel.updateArea(currentAreaName, newAreaName);
    
                    // Refresh the table data from the database
                    refreshAreaTableData(areaTable);
    
                    // Display a confirmation message
                    JOptionPane.showMessageDialog(dialog, "Area Updated: " + newAreaName);
                } else {
                    // Edited area name already exists
                    JOptionPane.showMessageDialog(dialog, "Area already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // User canceled or entered an invalid name
                JOptionPane.showMessageDialog(dialog, "Operation canceled or invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // No row selected, prompt the user to select a row
            JOptionPane.showMessageDialog(dialog, "Please select a row to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void showSiteManagement() {
        // Fetch data for the "Site" table
        String[] columnNames = {"ID", "Site Name"};
        Object[][] rowData = siteModel.getDataForSiteManagement();
    
        // Create a table model
        DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable editing for all cells
            }
        };
    
        // Create a JTable with the table model
        JTable siteTable = new JTable(tableModel);
    
        // Create a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(siteTable);
    
        // Create a JDialog for the popup
        JDialog siteDialog = new JDialog(this, "Site Management", true);
        siteDialog.setLayout(new BorderLayout());
        siteDialog.add(scrollPane, BorderLayout.CENTER);
    
        // Add "Add" and "Edit" buttons to the dialog
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
    
        addButton.addActionListener(e -> handleAddSiteButtonClick(siteDialog, siteTable));
        editButton.addActionListener(e -> handleEditSiteButtonClick(siteDialog, siteTable));
    
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
    
        siteDialog.add(buttonPanel, BorderLayout.SOUTH);
    
        // Set dialog properties
        siteDialog.setSize(400, 300);
        siteDialog.setLocationRelativeTo(this);
        siteDialog.setVisible(true);
    }

    private void handleAddSiteButtonClick(JDialog dialog, JTable siteTable) {
        // Implement logic for "Add" button
        String newSiteName = JOptionPane.showInputDialog(dialog, "Enter new Site Name:");
    
        // Validate and process the newSiteName and newSiteDescription as needed
        if (newSiteName != null && !newSiteName.isEmpty()) {
            // Call TrainerModel method to add new site to the database
            siteModel.addNewSite(newSiteName);
    
            // Refresh the table data from the database
            refreshSiteTableData(siteTable);
    
            // Display a confirmation message
            JOptionPane.showMessageDialog(dialog, "New Site Added: " + newSiteName);
        } else {
            // User canceled or entered invalid input
            JOptionPane.showMessageDialog(dialog, "Operation canceled or invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleEditSiteButtonClick(JDialog dialog, JTable siteTable) {
        // Get the selected row index
        int selectedRow = siteTable.getSelectedRow();
    
        // Check if a row is selected
        if (selectedRow != -1) {
            // Get the current site name and description from the selected row
            String currentSiteName = (String) siteTable.getValueAt(selectedRow, 1);  // Assuming "Site Name" is in the second column (index 1)
    
            // Prompt the user for a new site name and description
            String newSiteName = JOptionPane.showInputDialog(dialog, "Edit Site Name:", currentSiteName);
    
            // Validate and process the newSiteName as needed
            if (newSiteName != null && !newSiteName.isEmpty()) {
                // Call SiteModel method to update site in the database
                siteModel.updateSite(currentSiteName, newSiteName);
    
                // Refresh the table data from the database
                refreshSiteTableData(siteTable);
    
                // Display a confirmation message
                JOptionPane.showMessageDialog(dialog, "Site Updated: " + newSiteName);
            } else {
                // User canceled or entered invalid input
                JOptionPane.showMessageDialog(dialog, "Operation canceled or invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // No row selected, prompt the user to select a row
            JOptionPane.showMessageDialog(dialog, "Please select a row to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void refreshSiteTableData(JTable siteTable) {
        // Fetch updated data for the "Site" table from the database
        Object[][] rowData = siteModel.getDataForSiteManagement();
    
        // Get the existing table model
        DefaultTableModel tableModel = (DefaultTableModel) siteTable.getModel();
    
        // Remove all existing rows from the table model
        tableModel.setRowCount(0);
    
        // Add the new rows to the table model
        for (Object[] row : rowData) {
            tableModel.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TrainerDashboard();
        });
    }
}
