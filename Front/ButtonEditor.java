package front;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import helpers.TrainerModel;

public class ButtonEditor extends DefaultCellEditor implements TableCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;

    private TrainerModel trainerModel;
    private JTable table;  // Reference to the table
    private int editingRow;  // Variable to store the editing row

    public ButtonEditor(JCheckBox checkBox, TrainerModel trainerModel, JTable table) {
        super(checkBox);
        this.trainerModel = trainerModel;
        this.table = table;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }
    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        // Additional initialization if needed
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
        editingRow = row;  // Store the editing row

        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            if ("Edit".equals(label)) {
                performEdit();  // No need to pass row here
            }
        }
        isPushed = false;
        return label;
    }

    private void performEdit() {
        String currentAreaName = (String) table.getValueAt(editingRow, 1);  // Assuming "Area Name" is in the second column (index 1)
        String newAreaName = JOptionPane.showInputDialog("Enter new Area Name:", currentAreaName);

        if (newAreaName != null) {
            // Update the model with the new area name
            table.getModel().setValueAt(newAreaName, editingRow, 1);  // Assuming "Area Name" is in the second column (index 1)
        }
    }
}
