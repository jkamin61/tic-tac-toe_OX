package org.example.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ComputerDifficultySelection extends JFrame {

    public ComputerDifficultySelection() {
        setTitle("Select Difficulty");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel label = new JLabel("Select Difficulty Level:");
        panel.add(label);

        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");

        panel.add(easyButton);
        panel.add(mediumButton);
        panel.add(hardButton);

        easyButton.addActionListener(this::startGameWithBot);
        mediumButton.addActionListener(this::startGameWithBot);
        hardButton.addActionListener(this::startGameWithBot);

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startGameWithBot(ActionEvent e) {
        setVisible(false);
        String difficulty = ((JButton) e.getSource()).getText();
        new MainView(true, difficulty);
    }
}
