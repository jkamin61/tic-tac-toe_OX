package org.example.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

public class BoardsView extends JPanel implements ActionListener {
    private final JPanel[] panels = new JPanel[9];
    private final JButton[][] buttons = new JButton[9][9];
    private char currentPlayer;
    private final char[][] boardState = new char[9][9];
    private final Random random = new Random();
    private int currentBoardIndex;
    private final GameHistoryView gameHistoryView;

    private int xWins;
    private int oWins;

    public BoardsView(GameHistoryView gameHistoryView) {
        this.gameHistoryView = gameHistoryView;

        setLayout(new GridLayout(3, 3, 10, 10));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < panels.length; i++) {
            panels[i] = new JPanel();
            panels[i].setLayout(new GridLayout(3, 3));
            panels[i].setBorder(new EmptyBorder(5, 5, 5, 5));
            add(panels[i]);

            for (int j = 0; j < 9; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(50, 50));
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
                buttons[i][j].addActionListener(this);
                panels[i].add(buttons[i][j]);
            }
        }

        // Initialize boardState to empty
        for (char[] chars : boardState) {
            Arrays.fill(chars, ' ');
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
