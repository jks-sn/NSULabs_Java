package ru.nsu.ccfit.berkaev.ui;

import ru.nsu.ccfit.berkaev.logic.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

import static ru.nsu.ccfit.berkaev.constants.UI.*;

public class UI extends JFrame {
    private final JButton[][] buttons;
    private final int rows;
    private final int columns;
    private int mines;

    private final JLabel minesLabel;

    private final JLabel timeLabel;

    private Icon redMine;
    private Icon mine;
    private Icon flag;
    private Icon tile;

    public UI(int rows, int columns, int mines) {
        buttons = new JButton[columns][rows];
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(FRAME_TITLE);
        setLocation(FRAME_LOC_X, FRAME_LOC_Y);

        minesLabel = makeLabel();
        timeLabel = makeLabel();

        setMines(mines);

        JPanel gameBoard;
        JPanel downPanel;

        gameBoard = createBoard();

        JPanel timePanel = makePanel("/timer.png",timeLabel);
        JPanel minesPanel = makePanel("/mine.png",minesLabel);

        downPanel = makeDownPanel(timePanel,minesPanel);

        JPanel game = createWindow(gameBoard,downPanel);
        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/background.jpg"))));

        add(background);
        background.setLayout(new BorderLayout(0, 0));
        background.add(game, BorderLayout.CENTER);

        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/mine.png")));
    }

    public void initGame() {
        hideAll();
        enableAll();
    }

    public void enableAll() {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                buttons[x][y].setEnabled(true);
            }
        }
    }


    public void disableAll() {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                buttons[x][y].setEnabled(false);
            }
        }
    }
    public JPanel createBoard()
    {
        JPanel gameBoard = new JPanel();
        gameBoard.setLayout(new GridLayout(columns, rows, 0, 0));
        for (int x = 0; x < columns; ++x) {
            for (int y = 0; y < rows; ++y) {
                buttons[x][y] = new JButton();

                buttons[x][y].setName(x + "," + y);
                buttons[x][y].setFont(new Font("Serif", Font.BOLD, 24));

                buttons[x][y].setBorder(BorderFactory.createLineBorder(Color.black, 1, true));

                gameBoard.add(buttons[x][y]);
            }
        }
        return gameBoard;
    }
    public void setTime(int timePassed) {
        timeLabel.setText("  " + timePassed + "  ");
    }

    public void hideAll() {
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                buttons[x][y].setText("");
                buttons[x][y].setBackground(new Color(0, 103, 200));
                buttons[x][y].setIcon(tile);
            }
        }
    }


    public void setButtonListeners(Game game) {
        addWindowListener(game);
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                buttons[x][y].addMouseListener(game);
            }
        }
    }
    private JLabel makeLabel()
    {
        JLabel label = new JLabel("  0  ", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setBackground(new Color(110, 110, 255));
        label.setForeground(Color.white);
        label.setOpaque(true);
        return label;
    }
    private JPanel makeDownPanel(JPanel timePanel, JPanel minesPanel)
    {
        JPanel downPanel = new JPanel();
        downPanel.setLayout(new BorderLayout(0, 20));
        downPanel.add(timePanel, BorderLayout.WEST);
        downPanel.add(minesPanel, BorderLayout.EAST);
        downPanel.setOpaque(false);
        return downPanel;
    }
    private JPanel makePanel(String pathToIcon, JLabel label)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 0));
        JLabel icon = new JLabel("", SwingConstants.CENTER);
        icon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(pathToIcon))));
        panel.add(icon, BorderLayout.WEST);
        panel.add(label, BorderLayout.CENTER);
        panel.setOpaque(false);
        return panel;
    }
    private JPanel createWindow(JPanel gameBoard, JPanel downPanel)
    {
        JPanel game = new JPanel();
        game.setLayout(new BorderLayout(0, 10));
        game.add(gameBoard, BorderLayout.CENTER);
        game.add(downPanel, BorderLayout.SOUTH);
        game.setBorder(BorderFactory.createEmptyBorder(60, 60, 14, 60));
        game.setOpaque(false);
        return game;
    }
    public JButton[][] getButtons() {
        return buttons;
    }

    public void setMines(int mines) {
        minesLabel.setText("  " + mines + "  ");
    }
    public void endGame(boolean isWin, JDialog dialog, JButton exit, JButton playAgain, JPanel buttons, JPanel c)
    {
        exit.setText("Exit");
        playAgain.setText("PlayAgain");
        JLabel message = new JLabel(isWin?"You win the game":"You lose the game!", SwingConstants.CENTER);
        buttons.setLayout(new GridLayout(1, 2, 10, 0));
        buttons.add(exit);
        buttons.add(playAgain);
        c.setLayout(new BorderLayout(20, 20));
        c.add(message, BorderLayout.NORTH);
        c.add(buttons, BorderLayout.SOUTH);

        c.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        dialog.setTitle(isWin?"Game Won":"Game Lost");
        dialog.add(c);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    public static void setLook(String look) {
        try {

            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (look.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

        } catch (Exception ignored) {
        }
    }

    public void plusMines() {
        mines++;
        setMines(mines);
    }

    public void minesMines() {
        mines--;
        setMines(mines);
    }

    public int getMines() {
        return mines;
    }

    private static Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(resizedWidth, resizedHeight, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public void setIcons() {
        int offset = buttons[0][1].getInsets().left;
        int width = buttons[0][1].getWidth();
        int height = buttons[0][1].getHeight();

        ImageIcon d;

        d = new ImageIcon(Objects.requireNonNull(getClass().getResource("/redmine.png")));
        redMine = resizeIcon(d, width - offset, height - offset);

        d = new ImageIcon(Objects.requireNonNull(getClass().getResource("/mine.png")));
        mine = resizeIcon(d, width - offset, height - offset);

        d = new ImageIcon(Objects.requireNonNull(getClass().getResource("/flag.png")));
        flag = resizeIcon(d, width - offset, height - offset);

        d = new ImageIcon(Objects.requireNonNull(getClass().getResource("/closet.png")));
        tile = resizeIcon(d, width - offset, height - offset);

    }

    public Icon getIconMine() {
        return mine;
    }

    public Icon getIconRedMine() {
        return redMine;
    }

    public Icon getIconFlag() {
        return flag;
    }

    public Icon getIconTile() {
        return tile;
    }


    public void setTextColor(JButton b) {
    ru.nsu.ccfit.berkaev.constants.UI.setTextColor(b);
    }


}