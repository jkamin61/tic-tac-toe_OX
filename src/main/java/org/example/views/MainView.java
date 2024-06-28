package org.example.views;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private final JFrame frame;
    private final BoardsView boardsView;
    private final GameHistoryView gameHistoryView;
    private final MenuPanel menuPanel;
    private final boolean singlePlayerMode;
    private final String difficulty;

    public MainView(boolean singlePlayerMode, String difficulty) {
        this.singlePlayerMode = singlePlayerMode;
        this.difficulty = difficulty;

        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 600);

        gameHistoryView = new GameHistoryView();
        boardsView = new BoardsView(gameHistoryView, singlePlayerMode, difficulty);
        menuPanel = new MenuPanel(boardsView);

        frame.add(gameHistoryView, BorderLayout.EAST);
        frame.add(menuPanel, BorderLayout.NORTH);
        frame.add(boardsView, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
