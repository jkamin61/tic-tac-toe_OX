package org.example.views;

import javax.swing.*;
import java.awt.*;

public class ComputerDifficultySelection extends JFrame {

    JRadioButton easyButton = new JRadioButton("Easy");
    JRadioButton mediumButton = new JRadioButton("Medium");
    JRadioButton hardButton = new JRadioButton("Hard");
    JButton confirmButton = new JButton("Confirm");
    ButtonGroup group = new ButtonGroup();

    public ComputerDifficultySelection() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Select Computer Difficulty");
        panel.add(label, BorderLayout.NORTH);

        group.add(easyButton);
        group.add(mediumButton);
        group.add(hardButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(easyButton);
        buttonPanel.add(mediumButton);
        buttonPanel.add(hardButton);
        buttonPanel.add(confirmButton);
        panel.add(buttonPanel, BorderLayout.CENTER);

        confirmButton.addActionListener(e -> {
            setVisible(false);
            new MainView();
        });

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
