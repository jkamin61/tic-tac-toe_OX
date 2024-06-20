package org.example.views;

import javax.swing.*;
import java.awt.*;

public class PlayerModeSelection extends JFrame {

    public PlayerModeSelection() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Select Player Mode");
        panel.add(label, BorderLayout.NORTH);

        JButton singlePlayerButton = new JButton("Single Player");
        JButton multiPlayerButton = new JButton("Multi Player");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(singlePlayerButton);
        buttonPanel.add(multiPlayerButton);
        panel.add(buttonPanel, BorderLayout.CENTER);

        singlePlayerButton.addActionListener(e -> {
            setVisible(false); // ukrycie bieżącej ramki
            new MainView();    // utworzenie nowego widoku
        });

        multiPlayerButton.addActionListener(e -> {
            // Logic to start multi player game
        });

        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
