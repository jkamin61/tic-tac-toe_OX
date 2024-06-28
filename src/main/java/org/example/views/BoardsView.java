package org.example.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
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
    boolean[] boardPlayed = new boolean[9];
    boolean boardFrozen = false;
    int currentBoardIndex;

    final ImageIcon xIcon;
    final ImageIcon oIcon;

    public BoardsView(GameHistoryView gameHistoryView) {
        this.gameHistoryView = gameHistoryView;

        xIcon = scaleIcon(new ImageIcon("src/main/resources/X.png"), 50, 50);
        oIcon = scaleIcon(new ImageIcon("src/main/resources/O.png"), 50, 50);

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

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        int[] numpadMapping = {KeyEvent.VK_NUMPAD7, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD9,
                KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD6,
                KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3};

        for (int i = 0; i < 9; i++) {
            final int index = i;
            inputMap.put(KeyStroke.getKeyStroke(numpadMapping[i], 0), "numpad" + (i + 1));
            actionMap.put("numpad" + (i + 1), new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (buttons[currentBoardIndex][index].isEnabled()) {
                        buttons[currentBoardIndex][index].doClick();
                    }
                }
            });
        }


        setFocusable(true);
        requestFocusInWindow();
    }

    private ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
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
            button.setIcon(xIcon);
            button.setDisabledIcon(xIcon);
            boardState[boardIndex][cellIndex] = 'X';
        } else {
            button.setIcon(oIcon);
            button.setDisabledIcon(oIcon);
            boardState[boardIndex][cellIndex] = 'O';
        }
        button.setEnabled(false);
        xTurn = !xTurn;

        searchForWinner(boardIndex, row, col);
        gameHistoryView.addMove(xTurn ? 'O' : 'X', cellIndex, boardIndex);

        previousBoardIndex = boardIndex;

        if (checkForBoardsPlayed() < 8) {
            chooseRandomBoardForNextMove();
        } else {
            enableButtons();
        }
    }

    private void enableButtons() {
        for (int i = 0; i < 9; i++) {
            if (!boardPlayed[i]) {
                panels[i].setBackground(Color.YELLOW);
                for (int j = 0; j < 9; j++) {
                    buttons[i][j].setEnabled(true);
                }
            }
        }
    }

    private int checkForBoardsPlayed() {
        int count = 0;
        for (boolean won : boardPlayed) {
            if (won) {
                count++;
            }
        }
        return count;
    }

    public void searchForWinner(int boardIndex, int row, int col) {
        char player = boardState[boardIndex][row * 3 + col];

        if (checkForTie(boardIndex)) {
            highlightTiedBoard(boardIndex);
            disableBoardAfterWin(boardIndex);
            boardPlayed[boardIndex] = true;
            announceWinner();
            return;
        }

        if (boardState[boardIndex][row * 3] == player &&
                boardState[boardIndex][row * 3 + 1] == player &&
                boardState[boardIndex][row * 3 + 2] == player) {
            highlightWonBoard(boardIndex);
            disableBoardAfterWin(boardIndex);
            boardPlayed[boardIndex] = true;
            updateScore(player);
            return;
        }
        // Check columns
        if (boardState[boardIndex][col] == player &&
                boardState[boardIndex][col + 3] == player &&
                boardState[boardIndex][col + 6] == player) {
            highlightWonBoard(boardIndex);
            disableBoardAfterWin(boardIndex);
            boardPlayed[boardIndex] = true;
            updateScore(player);
            return;
        }
        // Check diagonals
        if (boardState[boardIndex][2] == player &&
                boardState[boardIndex][4] == player &&
                boardState[boardIndex][6] == player) {
            highlightWonBoard(boardIndex);
            disableBoardAfterWin(boardIndex);
            boardPlayed[boardIndex] = true;
            updateScore(player);
        }

        if (boardState[boardIndex][0] == player &&
                boardState[boardIndex][4] == player &&
                boardState[boardIndex][8] == player) {
            highlightWonBoard(boardIndex);
            disableBoardAfterWin(boardIndex);
            boardPlayed[boardIndex] = true;
            updateScore(player);
        }
    }

    private boolean checkForTie(int boardIndex) {
        for (int i = 0; i < 9; i++) {
            if (boardState[boardIndex][i] == ' ') {
                return false;
            }
        }
        return true;
    }

    public void resetBoard() {
        boardFrozen = false;
        xWins = 0;
        oWins = 0;
        for (int i = 0; i < 9; i++) {
            panels[i].setBackground(null);
            gameHistoryView.clearHistory();
            boardPlayed[i] = false;
            for (int j = 0; j < 9; j++) {
                boardState[i][j] = ' ';
                buttons[i][j].setText("");
                buttons[i][j].setIcon(null);
                buttons[i][j].setEnabled(true);
            }
        }
        xTurn = true;
        chooseRandomBoardForNextMove();
    }

    private void highlightWonBoard(int boardIndex) {
        for (int i = 0; i < 9; i++) {
            if (i == boardIndex) {
                panels[i].setBackground(Color.RED);
            }
        }
    }

    private void highlightTiedBoard(int boardIndex) {
        for (int i = 0; i < 9; i++) {
            if (i == boardIndex) {
                panels[i].setBackground(Color.BLUE);
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
        } else if (player == 'O') {
            oWins++;
        }
        gameHistoryView.updateScore(xWins, oWins);
        announceWinner();
    }

    private void announceWinner() {
        if (checkForBoardsPlayed() == 9) {
            if (xWins > oWins) {
                JOptionPane.showMessageDialog(this, "Player X wins the game!");
            } else if (oWins > xWins) {
                JOptionPane.showMessageDialog(this, "Player O wins the game!");
            } else {
                JOptionPane.showMessageDialog(this, "It's a tie!");
            }
            freezeBoard();
        } else if (xWins >= 5 || oWins >= 5) {
            String winner = (xWins >= 5) ? "Player X" : "Player O";
            JOptionPane.showMessageDialog(this, winner + " wins the game!");
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
        for (boolean won : boardPlayed) {
            if (!won) {
                return true;
            }
        }
        return false;
    }

    private void chooseRandomBoardForNextMove() {
        if (!hasAvailableBoards()) {
            freezeBoard();
            return;
        }
        if (boardFrozen) {
            return;
        }
        int boardIndex;
        do {
            boardIndex = random.nextInt(9);
        } while (boardIndex == previousBoardIndex || boardPlayed[boardIndex]);

        currentBoardIndex = boardIndex;

        for (int i = 0; i < 9; i++) {
            if (boardPlayed[i]) {
                panels[i].setBackground(Color.RED);
            } else {
                panels[i].setBackground(null);
            }
            for (int j = 0; j < 9; j++) {
                buttons[i][j].setEnabled(i == boardIndex && !boardPlayed[i]);
            }
        }

        panels[boardIndex].setBackground(Color.YELLOW);
    }
}
