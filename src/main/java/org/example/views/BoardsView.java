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

        // Initialize game state
        initializeGame();
    }

    private void initializeGame() {
        currentPlayer = random.nextBoolean() ? 'X' : 'O';

        currentBoardIndex = random.nextInt(9);
        highlightCurrentBoard();
    }

    private void highlightCurrentBoard() {
        for (int i = 0; i < panels.length; i++) {
            panels[i].setBorder(new EmptyBorder(5, 5, 5, 5));
        }
        panels[currentBoardIndex].setBorder(BorderFactory.createLineBorder(Color.RED, 2));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();
        int panelIndex = -1;
        int buttonIndex = -1;

        // Find which button was clicked
        outerLoop:
        for (int i = 0; i < panels.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j] == buttonClicked) {
                    panelIndex = i;
                    buttonIndex = j;
                    break outerLoop;
                }
            }
        }

        if (panelIndex != currentBoardIndex || buttonIndex == -1) {
            return; // Invalid move, only allow move on the current board
        }

        int row = buttonIndex / 3;
        int col = buttonIndex % 3;

        // Check if the clicked button is already marked
        if (boardState[panelIndex][buttonIndex] != ' ') {
            return; // Invalid move
        }

        // Mark the board state and update button text
        boardState[panelIndex][buttonIndex] = currentPlayer;
        buttonClicked.setText(String.valueOf(currentPlayer));

        // Log move to game history
//        gameHistoryView.addMove(currentPlayer, buttonIndex, panelIndex);

        // Check for a winner on the current board
        if (checkWinnerOnBoard(panelIndex)) {
            JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins on board " + (panelIndex + 1) + "!");
            updateScore(currentPlayer);
            resetBoard(panelIndex);
        } else if (isBoardFull(panelIndex)) {
            JOptionPane.showMessageDialog(this, "Board " + (panelIndex + 1) + " is full!");
            resetBoard(panelIndex);
        } else {
            // Switch player
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

            // Randomly select the next board
            currentBoardIndex = random.nextInt(9);
            highlightCurrentBoard();
        }
    }

    private boolean checkWinnerOnBoard(int boardIndex) {
        int startRow = boardIndex / 3 * 3;
        int startCol = boardIndex % 3 * 3;

        // Check rows
        for (int i = 0; i < 3; i++) {
            if (boardState[startRow + i][startCol] == currentPlayer &&
                    boardState[startRow + i][startCol + 1] == currentPlayer &&
                    boardState[startRow + i][startCol + 2] == currentPlayer) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (boardState[startRow][startCol + i] == currentPlayer &&
                    boardState[startRow + 1][startCol + i] == currentPlayer &&
                    boardState[startRow + 2][startCol + i] == currentPlayer) {
                return true;
            }
        }

        // Check diagonals
        if (boardState[startRow][startCol] == currentPlayer &&
                boardState[startRow + 1][startCol + 1] == currentPlayer &&
                boardState[startRow + 2][startCol + 2] == currentPlayer) {
            return true;
        }

        return boardState[startRow][startCol + 2] == currentPlayer &&
                boardState[startRow + 1][startCol + 1] == currentPlayer &&
                boardState[startRow + 2][startCol] == currentPlayer;
    }


    private boolean isBoardFull(int boardIndex) {
        // Check if a specific board is full
        for (int i = 0; i < 9; i++) {
            if (boardState[boardIndex][i] == ' ') {
                return false;
            }
        }
        return true;
    }

    private void resetBoard(int boardIndex) {
        // Reset a specific board
        for (int i = 0; i < 9; i++) {
            buttons[boardIndex][i].setText("");
            boardState[boardIndex][i] = ' ';
        }
        // Remove highlighting
        panels[boardIndex].setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    private void updateScore(char player) {
        // Update score for the player who won a board
        if (player == 'X') {
            xWins++;
        } else {
            oWins++;
        }
        gameHistoryView.updateScore(xWins, oWins);
        // Check if one player has won on 5 boards
        if (xWins >= 5 || oWins >= 5) {
            String winner = (xWins >= 5) ? "Player X" : "Player O";
            JOptionPane.showMessageDialog(this, winner + " wins the game!");
            resetGame();
        }
    }

    public void resetGame() {
        // Reset the entire game state
        xWins = 0;
        oWins = 0;
        currentPlayer = random.nextBoolean() ? 'X' : 'O';
        currentBoardIndex = random.nextInt(9);
        highlightCurrentBoard();
        for (int i = 0; i < boardState.length; i++) {
            for (int j = 0; j < boardState[i].length; j++) {
                boardState[i][j] = ' ';
            }
        }
        for (int i = 0; i < panels.length; i++) {
            resetBoard(i);
        }
    }
}
