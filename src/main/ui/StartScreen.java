package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JFrame {

    private JPanel startScreenPanel;
    private JLabel welcomeLabel;
    private ImageIcon logoIcon;
    private JLabel logoLabel;
    private JButton startButton;

    // EFFECTS: Sets up the start screen

    public StartScreen() {

        setTitle("UBCCook - Start Screen");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel startScreenPanel = new JPanel();
        startScreenPanel.setLayout(new BoxLayout(startScreenPanel, BoxLayout.Y_AXIS));
        JLabel logoLabel = new JLabel(setUpImage());
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        JButton startButton = new JButton("Let's Get Cooking!");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel welcomeLabel = new JLabel("Welcome to UBCCook!");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        startScreenPanel.add(Box.createVerticalStrut(20));
        startScreenPanel.add(welcomeLabel);
        startScreenPanel.add(Box.createVerticalStrut(10));
        startScreenPanel.add(logoLabel);
        startScreenPanel.add(Box.createVerticalStrut(10));
        startScreenPanel.add(startButton);
        startButton.addActionListener(e -> startUp());


        add(startScreenPanel);

        setLocationRelativeTo(null);
    }

    // EFFECTS: Closes the start screen and sets up the main app
    public void startUp() {
        dispose(); //closes the start screen
        new CookApp().setVisible(true); //Sets up the main app
    }

    // EFFECTS: Sets up the Image (just to save on lines of code) by resizing the image
    public ImageIcon setUpImage() {
        ImageIcon logoIcon = new ImageIcon("ubcook.png");
        Image originalImage = logoIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(resizedImage);
        return logoIcon;
    }

}


