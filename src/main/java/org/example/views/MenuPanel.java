package org.example.views;

import javax.swing.*;

public class MenuPanel extends JPanel {
    public MenuPanel() {
        JButton newGameButton = new JButton("New Game");
        JButton resetButton = new JButton("Reset");
        JButton exitButton = new JButton("Exit");

        add(newGameButton);
        add(resetButton);
        add(exitButton);

        // Add action listeners for buttons
        newGameButton.addActionListener(e -> {
            
        });

        resetButton.addActionListener(e -> {
            // Logic to reset the game
        });

        exitButton.addActionListener(e -> {
            System.exit(0);
        });
    }
}
