package org.example.views;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class GameHistoryView extends JPanel {
    private final JTextArea textArea;
    private int xWins;
    private int oWins;

    public GameHistoryView() {
        setLayout(new BorderLayout());

        textArea = new JTextArea(20, 20);
        textArea.setBorder(BorderFactory.createTitledBorder("Game History"));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    }

    public void addMove(char player, int buttonIndex, int boardIndex) {
        String position = getPosition(buttonIndex);
        String message = String.format("Player %s placed %s on %s on board %d.\n", player, player, position, (boardIndex + 1));
        textArea.append(message);
    }

    public void updateScore(int xWins, int oWins) {
        this.xWins = xWins;
        this.oWins = oWins;
        String scoreMessage = String.format("Score - Player X: %d, Player O: %d\n\n", xWins, oWins);
        textArea.append(scoreMessage);
    }

    private String getPosition(int buttonIndex) {
        int row = buttonIndex / 3 + 1;
        int col = buttonIndex % 3 + 1;
        return "(" + row + "," + col + ")";
    }

    public void clearHistory() {
        textArea.setText("");
    }
}
