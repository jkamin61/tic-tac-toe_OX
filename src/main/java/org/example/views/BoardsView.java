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
    private boolean xTurn = true;
    private final char[][] boardState = new char[9][9];
    private final Random random = new Random();

    private int xWins;
    private int oWins;

    public BoardsView() {
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
        JButton button = (JButton) e.getSource();
        int boardIndex = -1;
        int cellIndex = -1;

        // Find the board and cell index
        for (int i = 0; i < panels.length; i++) {
            for (int j = 0; j < 9; j++) {
                if (buttons[i][j] == button) {
                    boardIndex = i;
                    cellIndex = j;
                    break;
                }
            }
        }

        if (boardIndex == -1 || cellIndex == -1) return;

        int row = cellIndex / 3;
        int col = cellIndex % 3;

        if (xTurn) {
            button.setText("X");
            boardState[boardIndex][cellIndex] = 'X';
        } else {
            button.setText("O");
            boardState[boardIndex][cellIndex] = 'O';
        }
        button.setEnabled(false);
        xTurn = !xTurn;

        searchForWinner(boardIndex, row, col);
    }

    public void searchForWinner(int boardIndex, int row, int col) {
        char player = boardState[boardIndex][row * 3 + col];

        // Check rows
        if (boardState[boardIndex][row * 3] == player &&
                boardState[boardIndex][row * 3 + 1] == player &&
                boardState[boardIndex][row * 3 + 2] == player) {
            announceWinner(player);
            return;
        }

        // Check columns
        if (boardState[boardIndex][col] == player &&
                boardState[boardIndex][col + 3] == player &&
                boardState[boardIndex][col + 6] == player) {
            announceWinner(player);
            return;
        }

        // Check diagonals
        if (boardState[boardIndex][0] == player &&
                boardState[boardIndex][4] == player &&
                boardState[boardIndex][8] == player) {
            announceWinner(player);
            return;
        }

        if (boardState[boardIndex][2] == player &&
                boardState[boardIndex][4] == player &&
                boardState[boardIndex][6] == player) {
            announceWinner(player);
        }
    }

    private void announceWinner(char player) {
        if (player == 'X') {
            xWins++;
        } else if (player == 'O') {
            oWins++;
        }
        JOptionPane.showMessageDialog(this, player + " wins!");
        resetBoard();
    }

    private void resetBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boardState[i][j] = ' ';
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        xTurn = true;
    }
}
