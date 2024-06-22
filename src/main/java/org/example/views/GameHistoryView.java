package org.example.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GameHistoryView extends JPanel {
    private final JTable table;
    private final DefaultTableModel tableModel;
    private int moveNumber;
    private int xWins;
    private int oWins;

    public GameHistoryView() {
        setLayout(new BorderLayout());

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
        Object[] rowData = {xScoreMessage, oScoreMessage, "", ""};
        tableModel.addRow(rowData);
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
    }
}
