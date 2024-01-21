package front;

import javax.swing.*;
import helpers.User;
import helpers.DatabaseHelper;
import java.awt.*;
import java.sql.SQLException;



class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private JTable table;  

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    // Add your custom logic for showing the edit user dialog
private void showEditUserDialog(User user) {
    // Replace this with your actual implementation to show the edit user dialog
    System.out.println("Edit User Dialog for user: " + user);
}

@Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(UIManager.getColor("Button.background"));
        }

        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;

        return button;
    }

@Override
    public Object getCellEditorValue() {
        
        if (isPushed) {
            // Handle button click based on label (Edit, Update, Delete)
            if ("Edit".equals(label)) {
                // Open the edit form (replace this with your actual edit form logic)
                openEditForm();
            } else if ("Update".equals(label)) {
                // Perform the update operation (replace this with your actual update logic)
                performUpdate();
            } else if ("Delete".equals(label)) {
                // Show a confirmation dialog for deletion
                showDeleteConfirmation();
            }
        }
        isPushed = false;
        return label;
    }
@Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
@Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }

    // Add your custom logic for opening the edit form
    private void openEditForm() {
 
        System.out.println("Open Edit Form");
    }

    // Add your custom logic for performing the update operation
    private boolean isEditing = false;  // Flag to track whether editing is in progress

    // Add your custom logic for performing the update operation
    private void performUpdate() {
        if (!isEditing) {
            // Get the selected row index from the JTable
            int selectedRowIndex = table.getSelectedRow();

            // Check if a row is selected
            if (selectedRowIndex == -1) {
                JOptionPane.showMessageDialog(
                        table,
                        "Please select a user to edit.",
                        "Edit User",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            // Get the user ID from the selected row (assuming the ID is in the first column)
            int userId = (int) table.getValueAt(selectedRowIndex, 0);

            try {
                // Fetch the user details from the database by ID
                User userToEdit = DatabaseHelper.getUserById(userId);

                // Check if the user is found
                if (userToEdit != null) {
                    // Show a dialog to edit user details
                    showEditUserDialog(userToEdit);
                    isEditing = true;  // Set editing flag to true
                } else {
                    JOptionPane.showMessageDialog(
                            table,
                            "User not found for ID: " + userId,
                            "Edit User",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (SQLException e) {

            }
        } else {
            // Handle the "Update" action here
            // This block will be executed when the "Update" button is clicked
            // ... (perform the update logic)
            isEditing = false;  // Reset editing flag
        }
    }


    // Show a confirmation dialog for deletion
    private void showDeleteConfirmation() {
        int result = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete?",
                "Delete Confirmation",
                JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            // Perform the delete operation (replace this with your actual delete logic)
            System.out.println("Perform Delete");
        }
    }
}
