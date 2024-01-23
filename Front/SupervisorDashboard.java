package front;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SupervisorDashboard extends JFrame {
    private final TrainingManager trainingManager;

    public SupervisorDashboard() {
        // Set frame properties
        setTitle("Supervisor Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout manager
        setLayout(new BorderLayout());

        // Add components to the supervisor dashboard
        JLabel welcomeLabel = new JLabel("Welcome, Supervisor!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.CENTER);

        // Display the frame
        setVisible(true);

        // Initialize TrainingManager
        trainingManager = new TrainingManager(this);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0)); // Add left margin

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addButton(buttonPanel, "Shift Management", e -> showShiftTable(), gbc);
        addButton(buttonPanel, "Training Management", e -> showTrainingsTable(), gbc); 
        addButton(buttonPanel, "Task Management", e -> showTaskTable(), gbc); 

        addButton(buttonPanel, "Procedure Management", e -> showProcedureTable(), gbc); 

        addButton(buttonPanel, "Site Management", e -> showSiteTable(), gbc); 

        addButton(buttonPanel, "Area Management", e -> showAreaTable(), gbc);

        return buttonPanel;
    }

    private void addButton(JPanel panel, String buttonText, ActionListener listener, GridBagConstraints gbc) {
        JButton button = new JButton(buttonText);
        button.addActionListener(listener);
        panel.add(button, gbc);
        gbc.gridy++;
    }

    private void showShiftTable() {
        // Implement logic to show Shift Table
        System.out.println("Showing Shift Table");
    }

    private void showTrainingsTable() {
        // Implement logic to show Trainings Table
        System.out.println("Showing Trainings Table");
    }

    private void showTaskTable() {
        // Implement logic to show Task Table
        System.out.println("Showing Task Table");
    }

    private void showProcedureTable() {
        // Implement logic to show Procedure Table
        System.out.println("Showing Procedure Table");
    }

    private void showSiteTable() {
        // Implement logic to show Site Table
        System.out.println("Showing Site Table");
    }

    private void showAreaTable() {
        // Implement logic to show Area Table
        System.out.println("Showing Area Table");
    }

    public static void main(String[] args) {
        // Create an instance of the SupervisorDashboard
        SwingUtilities.invokeLater(() -> new SupervisorDashboard());
    }
}

class TrainingManager {
    private final SupervisorDashboard supervisorDashboard;

    public TrainingManager(SupervisorDashboard supervisorDashboard) {
        this.supervisorDashboard = supervisorDashboard;
    }

    public void showTrainingWindow() {
        SwingUtilities.invokeLater(() -> new TrainingWindow());
    }
}

class TrainingWindow extends JFrame {
    private JTable userTable;
    private DefaultTableModel tableModel;

    public TrainingWindow() {
        // ... (same code as before)
    }

    // ... (same code as before)

    // Method to fetch and display user data
    private void fetchUserData() {
        // ... (same code as before)
    }
}
