package front;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CoordinatorDashboard extends JFrame {
    public CoordinatorDashboard() {
        // Set frame properties
        setTitle("Coordinator Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Set layout manager
        setLayout(new BorderLayout());

        // Add components to the coordinator dashboard
        JLabel welcomeLabel = new JLabel("Welcome, Coordinator!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        // Reuse buttons with specific actions
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        addButton(buttonPanel, "Shift Management", e -> showShiftTable(), gbc);
        addButton(buttonPanel, "Training Management", e -> showTrainingsTable(), gbc);
        addButton(buttonPanel, "Task Management", e -> showTaskTable(), gbc);
        addButton(buttonPanel, "Procedure Management", e -> showProcedureTable(), gbc);
        addButton(buttonPanel, "Site Management", e -> showSiteTable(), gbc);
        addButton(buttonPanel, "Area Management", e -> showAreaTable(), gbc);

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

    private void showShiftTable() {
        // Implement the action for "Shift Management" button
        JOptionPane.showMessageDialog(this, "Showing Shift Table");
    }

    private void showTrainingsTable() {
        // Implement the action for "Training Management" button
        JOptionPane.showMessageDialog(this, "Showing Training Table");
    }

    private void showTaskTable() {
        // Implement the action for "Task Management" button
        JOptionPane.showMessageDialog(this, "Showing Task Table");
    }

    private void showProcedureTable() {
        // Implement the action for "Procedure Management" button
        JOptionPane.showMessageDialog(this, "Showing Procedure Table");
    }

    private void showSiteTable() {
        // Implement the action for "Site Management" button
        JOptionPane.showMessageDialog(this, "Showing Site Table");
    }

    private void showAreaTable() {
        // Implement the action for "Area Management" button
        JOptionPane.showMessageDialog(this, "Showing Area Table");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CoordinatorDashboard();
        });
    }
}
