package front;
import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;


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

        // Add more components to the coordinator dashboard as needed.

        // Display the frame
        setVisible(true);
    }
}
