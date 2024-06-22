package org.example.views;

import javax.swing.*;

public class MenuPanel extends JPanel {
    BoardsView boardsView;

    public MenuPanel(BoardsView boardsView) {
        this.boardsView = boardsView;

        JButton newGameButton = new JButton("New Game");
        JButton exitButton = new JButton("Exit");

        add(newGameButton);
        add(exitButton);
        
        newGameButton.addActionListener(e -> {
            boardsView.resetBoard();
        });

        exitButton.addActionListener(e -> {
            System.exit(0);
        });
    }
}
