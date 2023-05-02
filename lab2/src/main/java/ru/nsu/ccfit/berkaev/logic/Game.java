package ru.nsu.ccfit.berkaev.logic;

import ru.nsu.ccfit.berkaev.timer.Timer;
import ru.nsu.ccfit.berkaev.ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static ru.nsu.ccfit.berkaev.logic.Cell.states.*;
import static ru.nsu.ccfit.berkaev.utils.UtilsBoard.countCoordinates;

public class Game implements MouseListener, ActionListener, WindowListener {

    private Board board;

    private ru.nsu.ccfit.berkaev.timer.Timer timer;
    private boolean playing;
    private UI ui;

    public Game() {
        board = new Board();
        timer = new Timer();
        UI.setLook("Nimbus");
        this.ui = new UI(board.getRows(), board.getColumns(), board.getNumberMines());
        this.ui.setButtonListeners(this);
        ui.setVisible(true);
        ui.setIcons();
        newGame();
    }

    public void newGame() {
        this.playing = false;

        board = new Board();
        ui.initGame();
        ui.setMines(board.getNumberMines());
        timer.resetTimer(ui);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (!playing) {
            timer.startTimer(ui);
            playing = true;
        }

        JButton button = (JButton) e.getSource();

        String[] coordinates = button.getName().split(",");

        int row = Integer.parseInt(coordinates[0]);
        int column= Integer.parseInt(coordinates[1]);

        if (SwingUtilities.isLeftMouseButton(e)) {
            button.setIcon(null);
            if (board.getMine(row, column)) {
                button.setIcon(ui.getIconRedMine());
                endGame(false);
            } else {
                board.setState(row, column, OPEN.ordinal());
                String numberSurroundingMines = Integer.toString(board.getSurroundingMines(row, column));
                button.setText(numberSurroundingMines);
                ui.setTextColor(button,numberSurroundingMines);
                button.setBackground(Color.lightGray);
                if (board.getSurroundingMines(row, column) == 0) {
                    button.setText("");
                    findZeroes(row, column);
                }
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            if (board.getState(row, column) == FLAG.ordinal()) {
                board.setState(row, column, CLOSE.ordinal());
                button.setBackground(Color.lightGray);
                button.setIcon(ui.getIconTile());
                ui.plusMines();
            } else if (board.getState(row, column) == CLOSE.ordinal()) {
                board.setState(row, column, FLAG.ordinal());
                button.setBackground(Color.blue);
                button.setIcon(ui.getIconFlag());
                ui.minesMines();
            }
        }
        checkGame();
    }

    public boolean isFinished() {
        boolean isFinished = true;
        for (int x = 0; x < board.getColumns(); x++) {
            for (int y = 0; y < board.getRows(); y++) {

                if ((!board.getMine(x, y)) && (board.getState(x, y) == CLOSE.ordinal())) {
                    isFinished = false;
                    break;
                }
            }
        }
        return isFinished;
    }

    private void checkGame() {
        if (isFinished()) {
            endGame(true);
        }
    }
    public void findZeroes(int xInput, int yInput) {
        JButton[][] buttons = ui.getButtons();
        for (int x = countCoordinates(xInput - 1, board.getColumns()); x <= countCoordinates(xInput + 1, board.getColumns()); x++) {
            for (int y = countCoordinates(yInput - 1, board.getRows()); y <= countCoordinates(yInput + 1, board.getRows()); y++) {
                if (board.getState(x, y) == CLOSE.ordinal()) {
                    buttons[x][y].setIcon(null);
                    buttons[x][y].setBackground(Color.lightGray);
                    board.setState(x, y, OPEN.ordinal());
                    if (board.getSurroundingMines(x, y) == 0) {
                        findZeroes(x, y);
                    } else {
                        String numberSurroundingMines = Integer.toString(board.getSurroundingMines(x, y));
                        buttons[x][y].setText(numberSurroundingMines);
                        ui.setTextColor(buttons[x][y],numberSurroundingMines);
                    }
                }
            }
        }
    }

    private void endGame(boolean isWin) {
        timer.endTimer();
        playing = false;
        showAll();
        JDialog dialog = new JDialog(ui, Dialog.ModalityType.DOCUMENT_MODAL);
        JPanel buttons = new JPanel();
        JButton exit = new JButton();
        JButton playAgain = new JButton();
        JPanel panel = new JPanel();
        exit.addActionListener((ActionEvent e) -> {
            dialog.dispose();
            windowClosing(null);
        });
        playAgain.addActionListener((ActionEvent e) -> {
            dialog.dispose();
            newGame();
        });

        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                                     @Override
                                     public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                         dialog.dispose();
                                         newGame();
                                     }
                                 }
        );
        ui.endGame(isWin,dialog,exit,playAgain,buttons,panel);
    }
    private void showAll() {
        JButton[][] buttons = ui.getButtons();
        for (int x = 0; x < board.getColumns(); x++) {
            for (int y = 0; y < board.getRows(); y++) {
                int state = board.getState(x, y);
                if (state == (CLOSE.ordinal())) {
                    buttons[x][y].setIcon(null);
                    buttons[x][y].setBackground(Color.lightGray);
                    if (board.getMine(x, y)) {
                        buttons[x][y].setIcon(ui.getIconMine());
                    } else {

                        if (board.getSurroundingMines(x, y) != 0) {
                            String numberSurroundingMines = Integer.toString(board.getSurroundingMines(x, y));
                            buttons[x][y].setText(numberSurroundingMines);
                            ui.setTextColor(buttons[x][y],numberSurroundingMines);
                        }
                    }
                } else if (state == FLAG.ordinal()) {
                    if (!board.getMine(x, y)) {
                        buttons[x][y].setBackground(Color.orange);
                    } else
                        buttons[x][y].setBackground(Color.green);
                }

            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e)
    {
            System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
