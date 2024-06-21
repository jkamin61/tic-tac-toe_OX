package org.example.views;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private final JFrame frame;
    private final BoardsView boardsView;
    private final GameHistoryView gameHistoryView;
    private final MenuPanel menuPanel;

    public MainView() {
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 600);

        menuPanel = new MenuPanel();
        frame.add(menuPanel, BorderLayout.NORTH);
        gameHistoryView = new GameHistoryView();
        frame.add(gameHistoryView, BorderLayout.EAST);
        boardsView = new BoardsView();
        frame.add(boardsView, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
