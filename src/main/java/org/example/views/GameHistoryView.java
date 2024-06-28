package org.example.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GameHistoryView extends JPanel {
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JLabel xScoreLabel;
    private final JLabel oScoreLabel;
    private int moveNumber;
    private int xWins;
    private int oWins;


    public GameHistoryView() {
        setLayout(new BorderLayout());

        xScoreLabel = new JLabel("Player X: 0 wins");
        oScoreLabel = new JLabel("Player O: 0 wins");

        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        scorePanel.add(xScoreLabel);
        scorePanel.add(oScoreLabel);

        add(scorePanel, BorderLayout.NORTH);

        String[] columnNames = {"Move number", "Player", "Board", "Field on board"};
        tableModel = new DefaultTableModel(columnNames, 0);

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        moveNumber = 0;
    }

    public void addMove(char player, int buttonIndex, int boardIndex) {
        String position = getPosition(buttonIndex);
        moveNumber++;
        Object[] rowData = {moveNumber, player, boardIndex + 1, position};
        tableModel.addRow(rowData);
    }

    public void updateScore(int xWins, int oWins) {
        this.xWins = xWins;
        this.oWins = oWins;
        String xScoreMessage = String.format("Player X: %d wins", xWins);
        String oScoreMessage = String.format("Player O: %d wins", oWins);
        xScoreLabel.setText(xScoreMessage);
        oScoreLabel.setText(oScoreMessage);
    }

    private String getPosition(int buttonIndex) {
        int row = buttonIndex / 3 + 1;
        int col = buttonIndex % 3 + 1;
        return "(" + row + "," + col + ")";
    }

    public void clearHistory() {
        tableModel.setRowCount(0);
        moveNumber = 0;
        xWins = 0;
        oWins = 0;

        // Clear score labels
        updateScore(0, 0); // Reset scores to 0
    }
}
