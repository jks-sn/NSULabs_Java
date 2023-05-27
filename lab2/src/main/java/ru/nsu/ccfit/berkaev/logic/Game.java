package ru.nsu.ccfit.berkaev.logic;

import ru.nsu.ccfit.berkaev.score.Score;
import ru.nsu.ccfit.berkaev.score.Score.Time;
import ru.nsu.ccfit.berkaev.timer.Timer;
import ru.nsu.ccfit.berkaev.ui.UI;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static ru.nsu.ccfit.berkaev.constants.Constants.MenuItems.*;
import static ru.nsu.ccfit.berkaev.constants.Constants.Nimbus;
import static ru.nsu.ccfit.berkaev.constants.Constants.Statictics.*;
import static ru.nsu.ccfit.berkaev.logic.Cell.States.*;
import static ru.nsu.ccfit.berkaev.utils.UtilsBoard.countCoordinates;

public class Game implements MouseListener, ActionListener, WindowListener {
    private Board board;

    private ru.nsu.ccfit.berkaev.timer.Timer timer;
    private boolean playing;
    private Score score;
    private UI ui;

    public Game(int rows, int cols, int mines) {
        score = new Score();
        score.populate();
        board = new Board(rows,cols,mines);
        timer = new Timer();
        UI.setLook(Nimbus);
        this.ui = new UI(board.getRows(), board.getColumns(), board.getNumberMines());
        this.ui.setButtonListeners(this);
        ui.setVisible(true);
        ui.setIcons();
        newGame(cols,rows,mines);
    }
    public void newGame(int cols, int rows, int mines) {
        this.playing = false;

        board = new Board(cols,rows,mines);
        ui.initGame();
        ui.setNumberMines(board.getNumberMines());
        ui.setMines(board.getNumberMines());
        timer.resetTimer(ui);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem menuItem = (JMenuItem) e.getSource();

        if (menuItem.getName().equals(menuItemNewGameName)) {
            timer.endTimer();
            this.playing = false;
            newGame(board.getRows(),board.getColumns(),board.getNumberMines());
            score.incGamesPlayed();
            score.save(timer.getTimePassed());
        }
        else if (menuItem.getName().equals(menuItemExitName))
        {
            windowClosing(null);
        }
        else
        {
            showScore();
        }

    }
    public void showScore()
    {

        JDialog dialog = new JDialog(ui, Dialog.ModalityType.DOCUMENT_MODAL);


        JPanel bestTimes = new JPanel();
        bestTimes.setLayout(new GridLayout(5,1));

        ArrayList<Time> bTimes = score.getBestTimes();

        for (Time bTime : bTimes) {
            JLabel t = new JLabel("  " + bTime.getTimeValue());
            bestTimes.add(t);
        }

        if (bTimes.isEmpty())
        {
            JLabel t = new JLabel("                               ");
            bestTimes.add(t);
        }

        TitledBorder b = BorderFactory.createTitledBorder(titleName);
        b.setTitleJustification(TitledBorder.LEFT);

        bestTimes.setBorder(b);

        JPanel statistics = new JPanel();

        statistics.setLayout(new GridLayout(6,1,0,10));

        JLabel gPlayed = new JLabel(labelPlayedName + score.getGamesPlayed());
        JLabel gWon = new JLabel(labelPlayedWon + score.getGamesWon());
        JLabel gPercentage = new JLabel(labelPercentageName + score.getWinPercentage() + "%");


        statistics.add(gPlayed);
        statistics.add(gWon);
        statistics.add(gPercentage);

        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        statistics.setBorder(loweredetched);


        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,2,10,0));

        JButton close = new JButton(buttonCloseName);
        JButton reset = new JButton(buttonResetName);


        close.addActionListener((ActionEvent e) -> {
            dialog.dispose();
        });
        reset.addActionListener((ActionEvent e) -> {
                    score.resetScore();
                    score.save(timer.getTimePassed());
                    dialog.dispose();
                    showScore();
        });

        buttons.add(close);
        buttons.add(reset);

        if (score.getGamesPlayed() == 0)
            reset.setEnabled(false);

        JPanel c = new JPanel();
        c.setLayout(new BorderLayout(20,20));
        c.add(bestTimes, BorderLayout.WEST);
        c.add(statistics, BorderLayout.CENTER);
        c.add(buttons, BorderLayout.SOUTH);

        c.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        dialog.setTitle(menuItemStatisticsName);
        dialog.add(c);
        dialog.pack();
        dialog.setLocationRelativeTo(ui);
        dialog.setVisible(true);
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
            newGame(board.getRows(),board.getColumns(),board.getNumberMines());
        });

        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                                     @Override
                                     public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                         dialog.dispose();
                                         newGame(board.getRows(),board.getColumns(),board.getNumberMines());
                                     }
                                 }
        );
        if(isWin) {
            score.save(timer.getTimePassed());
            score.incGamesWon();
        }
        score.incGamesPlayed();
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
