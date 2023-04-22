package ru.nsu.ccfit.berkaev.logic;

import ru.nsu.ccfit.berkaev.timer.Timer;
import ru.nsu.ccfit.berkaev.ui.UI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

import static ru.nsu.ccfit.berkaev.logic.Cell.states.*;
import static ru.nsu.ccfit.berkaev.utils.UtilsBoard.countCoordinates;

public class Game implements MouseListener, ActionListener, WindowListener {

    private Board board;

    private ru.nsu.ccfit.berkaev.timer.Timer timer;
    private boolean playing;
    private UI ui;
    private boolean gameStarted = false;

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

    public boolean getGameStarted() {
        return gameStarted;
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

        String[] co = button.getName().split(",");

        int x = Integer.parseInt(co[0]);
        int y = Integer.parseInt(co[1]);

        if (SwingUtilities.isLeftMouseButton(e)) {
            button.setIcon(null);
            if (board.getMine(x, y)) {
                button.setIcon(ui.getIconRedMine());
                gameLost();
            } else {
                board.setState(x, y, OPEN.ordinal());
                button.setText(Integer.toString(board.getSurroundingMines(x, y)));
                ui.setTextColor(button);

                if (board.getSurroundingMines(x, y) == 0) {
                    button.setBackground(Color.lightGray);
                    button.setText("");
                    findZeroes(x, y);
                } else {
                    button.setBackground(Color.lightGray);
                }
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            if (board.getState(x, y) == FLAG.ordinal()) {
                board.setState(x, y, CLOSE.ordinal());
                button.setBackground(new Color(0, 110, 140));
                button.setIcon(ui.getIconTile());
                ui.plusMines();
            } else if (board.getState(x, y) == CLOSE.ordinal()) {
                board.setState(x, y, FLAG.ordinal());
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
            gameWon();
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
                        buttons[x][y].setText(Integer.toString(board.getSurroundingMines(x, y)));
                        ui.setTextColor(buttons[x][y]);
                    }
                }
            }
        }
    }

    public void gameWon() {
        endGame();

        JDialog dialog = new JDialog(ui, Dialog.ModalityType.DOCUMENT_MODAL);

        JLabel message = new JLabel("Congratulations, you won the game!", SwingConstants.CENTER);

        JPanel statistics = new JPanel();
        statistics.setLayout(new GridLayout(6, 1, 0, 10));

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 2, 10, 0));

        JButton exit = new JButton("Exit");
        JButton playAgain = new JButton("Play Again");


        exit.addActionListener((ActionEvent e) -> {
            dialog.dispose();
            windowClosing(null);
        });
        playAgain.addActionListener((ActionEvent e) -> {
            dialog.dispose();
            newGame();
        });


        buttons.add(exit);
        buttons.add(playAgain);

        JPanel c = new JPanel();
        c.setLayout(new BorderLayout(20, 20));
        c.add(message, BorderLayout.NORTH);
        c.add(statistics, BorderLayout.CENTER);
        c.add(buttons, BorderLayout.SOUTH);

        c.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                                     @Override
                                     public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                         dialog.dispose();
                                         newGame();
                                     }
                                 }
        );

        dialog.setTitle("Game Won");
        dialog.add(c);
        dialog.pack();
        dialog.setLocationRelativeTo(ui);
        dialog.setVisible(true);
    }
    public void gameLost() {
        endGame();


        JDialog dialog = new JDialog(ui, Dialog.ModalityType.DOCUMENT_MODAL);

        JLabel message = new JLabel("Sorry, you lost this game :(", SwingConstants.CENTER);


        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 3, 2, 0));

        JButton exit = new JButton("Exit");
        JButton restart = new JButton("Restart");
        JButton playAgain = new JButton("Play Again");

        exit.addActionListener((ActionEvent e) -> {
            dialog.dispose();
            windowClosing(null);
        });
        restart.addActionListener((ActionEvent e) -> {
            dialog.dispose();
            newGame();
        });
        playAgain.addActionListener((ActionEvent e) -> {
            dialog.dispose();
            newGame();
        });


        buttons.add(exit);
        buttons.add(restart);
        buttons.add(playAgain);

        JPanel c = new JPanel();
        c.setLayout(new BorderLayout(20, 20));
        c.add(message, BorderLayout.NORTH);
        c.add(buttons, BorderLayout.SOUTH);

        c.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                                     @Override
                                     public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                         dialog.dispose();
                                         newGame();
                                     }
                                 }
        );

        dialog.setTitle("Game Lost");
        dialog.add(c);
        dialog.pack();
        dialog.setLocationRelativeTo(ui);
        dialog.setVisible(true);
    }

    private void endGame() {
        playing = false;
        showAll();

    }
    private void showAll() {
        int state;
        JButton[][] buttons = ui.getButtons();

        for (int x = 0; x < board.getColumns(); x++) {
            for (int y = 0; y < board.getRows(); y++) {
                state = board.getState(x, y);

                if (state == (CLOSE.ordinal())) {
                    buttons[x][y].setIcon(null);
                    if (board.getMine(x, y)) {
                        buttons[x][y].setIcon(ui.getIconMine());
                        buttons[x][y].setBackground(Color.lightGray);
                    } else {
                        if (board.getSurroundingMines(x, y) == 0) {
                            buttons[x][y].setText("");
                            buttons[x][y].setBackground(Color.lightGray);
                        } else {
                            buttons[x][y].setBackground(Color.lightGray);
                            buttons[x][y].setText(Integer.toString(board.getSurroundingMines(x, y)));
                            ui.setTextColor(buttons[x][y]);
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
