package front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrainerDashboard extends JFrame {
    private DefaultListModel<String> userListModel;
    private JList<String> userList;

    public TrainerDashboard() {
        // Set frame properties
        setTitle("Trainer Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout manager
        setLayout(new BorderLayout());

        // Add components to the trainer dashboard
        JLabel welcomeLabel = new JLabel("Welcome, Trainer!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        // Create a list model to store user names
        userListModel = new DefaultListModel<>();

        // Create a JList with the user list model
        userList = new JList<>(userListModel);
        userList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane userListScrollPane = new JScrollPane(userList);
        
        // Set the size of the JScrollPane
        userListScrollPane.setPreferredSize(new Dimension(600, 400));
        
        add(userListScrollPane, BorderLayout.CENTER);

        // Button to initiate training for selected users
        JButton trainButton = new JButton("Train Selected Users");
        trainButton.addActionListener(e -> trainSelectedUsers());
        add(trainButton, BorderLayout.SOUTH);

        // Add more buttons using a GridLayout for better alignment
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        addButton(buttonPanel, "Add Trainings", e -> addTrainings());
        addButton(buttonPanel, "Train Employees", e -> trainEmployees());
        addButton(buttonPanel, "Training Status", e -> showTrainingStatus());
        add(buttonPanel, BorderLayout.WEST);

        // Display the frame
        setVisible(true);
    }

    private void addButton(Container container, String buttonText, ActionListener actionListener) {
        JButton button = new JButton(buttonText);
        button.addActionListener(actionListener);
        container.add(button);
    }

    private void trainSelectedUsers() {
        // Get the selected user names from the JList
        int[] selectedIndices = userList.getSelectedIndices();

        if (selectedIndices.length > 0) {
            StringBuilder message = new StringBuilder("Training selected users:\n");

            for (int index : selectedIndices) {
                String userName = userListModel.getElementAt(index);
                message.append("- ").append(userName).append("\n");
            }

            // Replace this with your actual training logic
            JOptionPane.showMessageDialog(this, message.toString(), "Training Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No users selected for training.", "Training Result", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void addTrainings() {
        // Replace this with logic to add trainings
        JOptionPane.showMessageDialog(this, "Add Trainings button clicked.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void trainEmployees() {
        // Replace this with logic to train employees
        JOptionPane.showMessageDialog(this, "Train Employees button clicked.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showTrainingStatus() {
        // Replace this with logic to show training status
        JOptionPane.showMessageDialog(this, "Training Status button clicked.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    // Example method to add users to the list
    public void addUsers(String... users) {
        for (String user : users) {
            userListModel.addElement(user);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TrainerDashboard trainerDashboard = new TrainerDashboard();
            // Commenting out the example users to remove them from the display
            // trainerDashboard.addUsers("User1", "User2", "User3");
        });
    }
}
