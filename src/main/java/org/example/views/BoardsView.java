package org.example.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

public class BoardsView extends JPanel implements ActionListener {
    final JPanel[] panels = new JPanel[9];
    final JButton[][] buttons = new JButton[9][9];
    boolean xTurn = true;
    final char[][] boardState = new char[9][9];
    final Random random = new Random();
    int xWins;
    int oWins;
    GameHistoryView gameHistoryView;
    int previousBoardIndex = -1;
    boolean[] boardWon = new boolean[9];
    boolean boardFrozen = false;

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

        for (char[] chars : boardState) {
            Arrays.fill(chars, ' ');
        }

        chooseRandomBoardForNextMove();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        int boardIndex = -1;
        int cellIndex = -1;

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

        if (boardState[boardIndex][cellIndex] != ' ') {
            JOptionPane.showMessageDialog(this, "This cell is already occupied.");
            return;
        }

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
        gameHistoryView.addMove(xTurn ? 'O' : 'X', cellIndex, boardIndex);

        previousBoardIndex = boardIndex;
        chooseRandomBoardForNextMove();
    }

    public void searchForWinner(int boardIndex, int row, int col) {
        char player = boardState[boardIndex][row * 3 + col];

        if (boardState[boardIndex][row * 3] == player &&
                boardState[boardIndex][row * 3 + 1] == player &&
                boardState[boardIndex][row * 3 + 2] == player) {
            highlightBoard(boardIndex);
            disableBoardAfterWin(boardIndex);
            boardWon[boardIndex] = true;
            updateScore(player);
            return;
        }
        // Check columns
        if (boardState[boardIndex][col] == player &&
                boardState[boardIndex][col + 3] == player &&
                boardState[boardIndex][col + 6] == player) {
            highlightBoard(boardIndex);
            disableBoardAfterWin(boardIndex);
            boardWon[boardIndex] = true;
            updateScore(player);
            return;
        }
        // Check diagonals
        if (boardState[boardIndex][2] == player &&
                boardState[boardIndex][4] == player &&
                boardState[boardIndex][6] == player) {
            highlightBoard(boardIndex);
            disableBoardAfterWin(boardIndex);
            boardWon[boardIndex] = true;
            updateScore(player);
        }

        if (boardState[boardIndex][0] == player &&
                boardState[boardIndex][4] == player &&
                boardState[boardIndex][8] == player) {
            highlightBoard(boardIndex);
            disableBoardAfterWin(boardIndex);
            boardWon[boardIndex] = true;
            updateScore(player);
        }
    }

    public void resetBoard() {
        for (int i = 0; i < 9; i++) {
            panels[i].setBackground(null);
            gameHistoryView.clearHistory();
            boardWon[i] = false;
            for (int j = 0; j < 9; j++) {
                boardState[i][j] = ' ';
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        xTurn = true;
        chooseRandomBoardForNextMove();
    }

    private void highlightBoard(int boardIndex) {
        for (int i = 0; i < 9; i++) {
            if (i == boardIndex) {
                panels[i].setBackground(Color.RED);
            }
        }
    }

    private void disableBoardAfterWin(int boardIndex) {
        panels[boardIndex].setEnabled(false);
        for (int i = 0; i < 9; i++) {
            buttons[boardIndex][i].setEnabled(false);
        }
    }

    private void updateScore(char player) {
        if (player == 'X') {
            xWins++;
        } else {
            oWins++;
        }
        gameHistoryView.updateScore(xWins, oWins);
        if (xWins >= 5 || oWins >= 5) {
            String winner = (xWins >= 5) ? "Player X" : "Player O";
            JOptionPane.showMessageDialog(this, winner + " wins the game!");
            System.out.println("Freezing the board...");
            freezeBoard();
        }
    }

    private void freezeBoard() {
        boardFrozen = true;
        for (int i = 0; i < 9; i++) {
            panels[i].setEnabled(false);
            for (int j = 0; j < 9; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private boolean hasAvailableBoards() {
        for (boolean won : boardWon) {
            if (!won) {
                return true;
            }
        }
        return false;
    }

    private void chooseRandomBoardForNextMove() {
        if (boardFrozen) {
            return;
        }

        if (!hasAvailableBoards()) {
            JOptionPane.showMessageDialog(this, "No available boards left. The game is a draw!");
            freezeBoard();
            return;
        }

        int boardIndex;
        do {
            boardIndex = random.nextInt(9);
        } while (boardIndex == previousBoardIndex || boardWon[boardIndex]);

        for (int i = 0; i < 9; i++) {
            if (boardWon[i]) {
                panels[i].setBackground(Color.RED);
            } else {
                panels[i].setBackground(null);
            }
            for (int j = 0; j < 9; j++) {
                buttons[i][j].setEnabled(i == boardIndex && !boardWon[i]);
            }
        }

        panels[boardIndex].setBackground(Color.YELLOW);
    }
}
