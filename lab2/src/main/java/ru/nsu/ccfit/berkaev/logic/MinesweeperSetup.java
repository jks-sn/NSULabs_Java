package ru.nsu.ccfit.berkaev.logic;

import javax.swing.*;

public class MinesweeperSetup {
    private JTextField rowsField;
    private JTextField columnsField;
    private JTextField minesField;

    public MinesweeperSetup() {
        JFrame frame = new JFrame("Minesweeper Setup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        JLabel rowsLabel = new JLabel("Rows:");
        rowsField = new JTextField(10);
        JLabel columnsLabel = new JLabel("Columns:");
        columnsField = new JTextField(10);
        JLabel minesLabel = new JLabel("Mines:");
        minesField = new JTextField(10);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> startGame());

        panel.add(rowsLabel);
        panel.add(rowsField);
        panel.add(columnsLabel);
        panel.add(columnsField);
        panel.add(minesLabel);
        panel.add(minesField);
        panel.add(startButton);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private void startGame() {
        try {
            int rows = Integer.parseInt(rowsField.getText());
            int columns = Integer.parseInt(columnsField.getText());
            int mines = Integer.parseInt(minesField.getText());

            if (rows <= 0 || columns <= 0 || mines <= 0) {
                JOptionPane.showMessageDialog(null, "Rows, columns, and mines must be positive numbers.");
                return;
            }
            if (rows > 10 || columns > 10) {
                JOptionPane.showMessageDialog(null, "Field size cannot exceed 10x10.");
                return;
            }
            if (mines >= rows * columns) {
                JOptionPane.showMessageDialog(null, "Number of mines cannot be greater than the total number of cells.");
                return;
            }

            Game game = new Game(rows, columns, mines);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter numeric values.");
        }
    }
}
