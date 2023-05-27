package ru.nsu.ccfit.berkaev.ui;

import ru.nsu.ccfit.berkaev.constants.Constants;
import ru.nsu.ccfit.berkaev.logic.Game;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static ru.nsu.ccfit.berkaev.constants.Constants.*;
import static ru.nsu.ccfit.berkaev.constants.Constants.EndGame.*;
import static ru.nsu.ccfit.berkaev.constants.Constants.Frame.*;
import static ru.nsu.ccfit.berkaev.constants.Constants.Panel.*;
import static ru.nsu.ccfit.berkaev.constants.Constants.Paths.*;
import static ru.nsu.ccfit.berkaev.constants.Constants.MenuItems.*;
import static ru.nsu.ccfit.berkaev.constants.Constants.Window.windowBorder;
import static ru.nsu.ccfit.berkaev.constants.Constants.Window.windowBorderLayout;
import static ru.nsu.ccfit.berkaev.constants.Constants.downPanel.downPanelBorderLayout;
import static ru.nsu.ccfit.berkaev.constants.Constants.hideAll.backgroundColor;
import static ru.nsu.ccfit.berkaev.constants.Constants.hideAll.text;
import static ru.nsu.ccfit.berkaev.constants.Constants.makeLabel.colorBackground;
import static ru.nsu.ccfit.berkaev.constants.Constants.makeLabel.labelText;

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
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem newGame;
    private JMenuItem statistics;
    private JMenuItem exit;

    public UI(int rows, int columns, int mines) {
        buttons = new JButton[columns][rows];
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;

        setSize(Constants.Frame.WIDTH, Constants.Frame.HEIGHT);
        setTitle(TITLE);
        setLocation(LOC_X, LOC_Y);


        menuBar = new JMenuBar();

        gameMenu = new JMenu(menuName);

        newGame = new JMenuItem(menuItemNewGame);
        statistics = new JMenuItem(menuItemStatistics);
        exit = new JMenuItem(menuItemExit);

        newGame.setName(menuItemNewGameName);
        statistics.setName(menuItemStatisticsName);
        exit.setName(menuItemExitName);

        gameMenu.add(newGame);
        gameMenu.add(statistics);
        gameMenu.add(exit);

        menuBar.add(gameMenu);


        minesLabel = makeLabel();
        timeLabel = makeLabel();

        setMines(mines);

        JPanel gameBoard;
        JPanel downPanel;

        gameBoard = createBoard();

        JPanel timePanel = makePanel(timerPath,timeLabel);
        JPanel minesPanel = makePanel(minePath,minesLabel);

        downPanel = makeDownPanel(timePanel,minesPanel);

        JPanel game = createWindow(gameBoard,downPanel);
        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource(backgroundPath))));

        add(background);
        background.setLayout(new BorderLayout(0, 0));
        background.add(game, BorderLayout.CENTER);
        background.add(menuBar,BorderLayout.NORTH);

        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(minePath)));
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

    public JPanel createBoard()
    {
        JPanel gameBoard = new JPanel();
        gameBoard.setLayout(new GridLayout(columns, rows, 0, 0));
        for (int x = 0; x < columns; ++x) {
            for (int y = 0; y < rows; ++y) {
                buttons[x][y] = new JButton();

                buttons[x][y].setName(x + "," + y);
                buttons[x][y].setFont(font);

                buttons[x][y].setBorder(border);

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
                buttons[x][y].setText(text);
                buttons[x][y].setBackground(backgroundColor);
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
        newGame.addActionListener(game);
        statistics.addActionListener(game);
        exit.addActionListener(game);
    }
    private JLabel makeLabel()
    {
        JLabel label = new JLabel(labelText, SwingConstants.CENTER);
        label.setFont(font);
        label.setBackground(colorBackground);
        label.setForeground(Color.white);
        label.setOpaque(true);
        return label;
    }
    private JPanel makeDownPanel(JPanel timePanel, JPanel minesPanel)
    {
        JPanel downPanel = new JPanel();
        downPanel.setLayout(downPanelBorderLayout);
        downPanel.add(timePanel, BorderLayout.WEST);
        downPanel.add(minesPanel, BorderLayout.EAST);
        downPanel.setOpaque(false);
        return downPanel;
    }
    private JPanel makePanel(String pathToIcon, JLabel label)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(BorderLayoutHgap, BorderLayoutVgap));
        JLabel icon = new JLabel(panelIconText, SwingConstants.CENTER);
        icon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(pathToIcon))));
        panel.add(icon, BorderLayout.WEST);
        panel.add(label, BorderLayout.CENTER);
        panel.setOpaque(false);
        return panel;
    }
    private JPanel createWindow(JPanel gameBoard, JPanel downPanel)
    {
        JPanel game = new JPanel();
        game.setLayout(windowBorderLayout);
        game.add(gameBoard, BorderLayout.CENTER);
        game.add(downPanel, BorderLayout.SOUTH);
        game.setBorder(windowBorder);
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
        exit.setText(exitText);
        playAgain.setText(playAgainText);
        JLabel message = new JLabel(isWin?winGameMesage:looseGameMesage, SwingConstants.CENTER);
        buttons.setLayout(buttonsGridLayout);
        buttons.add(exit);
        buttons.add(playAgain);
        c.setLayout(borderLayout);
        c.add(message, BorderLayout.NORTH);
        c.add(buttons, BorderLayout.SOUTH);

        c.setBorder(endGameBorder);

        dialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        dialog.setTitle(isWin?dialogWinText:dialogLooseText);
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
    public void setNumberMines(int numberMines)
    {
        this.mines = numberMines;
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
        redMine = setIcon(redminePath,width,height,offset);
        mine = setIcon(minePath,width,height,offset);
        flag = setIcon(flagPath,width,height,offset);
        tile = setIcon(closetPath,width,height,offset);
    }
    public Icon setIcon(String path,int width, int height,int offset)
    {
        ImageIcon i = new ImageIcon(Objects.requireNonNull(getClass().getResource(path)));
        return resizeIcon(i, width - offset, height - offset);
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


    public void setTextColor(JButton button, String number) {
        button.setForeground((Constants.Digits.getColor(number)));
    }
}
