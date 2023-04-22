package ru.nsu.ccfit.berkaev.ui;

import ru.nsu.ccfit.berkaev.logic.Cell;
import ru.nsu.ccfit.berkaev.logic.Game;
import ru.nsu.ccfit.berkaev.timer.Timer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.io.File;
import java.util.Objects;

import static ru.nsu.ccfit.berkaev.logic.Cell.states.CLOSE;
import static ru.nsu.ccfit.berkaev.logic.Cell.states.FLAG;

public class UI extends JFrame {
    private JButton[][] buttons;
    private int rows;
    private int columns;
    private int mines;

    private JLabel minesLabel;

    private JLabel timeLabel;

    private final String FRAME_TITLE = "Minesweeper";

    private final int FRAME_WIDTH = 520;
    private final int FRAME_HEIGHT = 550;
    private final int FRAME_LOC_X = 430;
    private final int FRAME_LOC_Y = 50;

    private Icon redMine;
    private Icon mine;
    private Icon flag;
    private Icon tile;

    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem newGame;
    private JMenuItem exit;

    public UI(int rows, int columns, int mines) {
        buttons = new JButton[columns][rows];
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(FRAME_TITLE);
        setLocation(FRAME_LOC_X, FRAME_LOC_Y);

        JPanel gameBoard;
        JPanel downPanel;

        gameBoard = new JPanel();
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

        JPanel timePanel = new JPanel();
        timePanel.setLayout(new BorderLayout(10,0));
        this.timeLabel = new JLabel ("  0  " , SwingConstants.CENTER);
        timeLabel.setFont(new Font("Serif", Font.BOLD, 20));

        timeLabel.setBackground(new Color(110,110,255));
        timeLabel.setForeground(Color.white);
        timeLabel.setOpaque(true);

        JLabel iconTimer = new JLabel("", SwingConstants.CENTER);
        iconTimer.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/timer.png"))));

        timePanel.add(iconTimer, BorderLayout.WEST);
        timePanel.add(timeLabel, BorderLayout.CENTER);
        timePanel.setOpaque(false);


        JPanel minesPanel = new JPanel();
        minesPanel.setLayout(new BorderLayout(10, 0));

        this.minesLabel = new JLabel("  0  ", SwingConstants.CENTER);
        minesLabel.setFont(new Font("Serif", Font.BOLD, 20));
        minesLabel.setBackground(new Color(110, 110, 255));
        minesLabel.setForeground(Color.white);
        minesLabel.setOpaque(true);
        setMines(mines);

        JLabel iconMine = new JLabel("", SwingConstants.CENTER);
        iconMine.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/mine.png"))));

        minesPanel.add(minesLabel, BorderLayout.WEST);
        minesPanel.add(iconMine, BorderLayout.CENTER);
        minesPanel.setOpaque(false);

        downPanel = new JPanel();
        downPanel.setLayout(new BorderLayout(0,20));

        downPanel.add(timePanel, BorderLayout.WEST);
        downPanel.add(minesPanel, BorderLayout.EAST);
        downPanel.setOpaque(false);


        menuBar = new JMenuBar();
        gameMenu = new JMenu("Menu");
        newGame = new JMenuItem("   New Game");
        exit = new JMenuItem("   Exit");
        newGame.setName("New Game");
        exit.setName("Exit");

        gameMenu.add(newGame);
        gameMenu.add(exit);
        menuBar.add(gameMenu);

        JPanel p = new JPanel();
        p.setLayout(new BorderLayout(0, 10));
        p.add(gameBoard, BorderLayout.CENTER);
        p.add(downPanel, BorderLayout.SOUTH);

        p.setBorder(BorderFactory.createEmptyBorder(60, 60, 14, 60));
        p.setOpaque(false);

        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/background_berserk.png"))));

        add(background);

        background.setLayout(new BorderLayout(0, 0));

        background.add(menuBar, BorderLayout.NORTH);
        background.add(p, BorderLayout.CENTER);


        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/mine.png")));

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

    public void setTime(int timePassed)
    {
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

        newGame.addActionListener(game);
        exit.addActionListener(game);

        newGame.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        exit.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
    }


    public JButton[][] getButtons() {
        return buttons;
    }

    public void setMines(int mines) {
        minesLabel.setText("  " + mines + "  ");
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
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    public void setIcons() {
        int bOffset = buttons[0][1].getInsets().left;
        int bWidth = buttons[0][1].getWidth();
        int bHeight = buttons[0][1].getHeight();

        ImageIcon d;

        d = new ImageIcon(Objects.requireNonNull(getClass().getResource("/redmine.png")));
        redMine = resizeIcon(d, bWidth - bOffset, bHeight - bOffset);

        d = new ImageIcon(Objects.requireNonNull(getClass().getResource("/mine.png")));
        mine = resizeIcon(d, bWidth - bOffset, bHeight - bOffset);

        d = new ImageIcon(Objects.requireNonNull(getClass().getResource("/flag.png")));
        flag = resizeIcon(d, bWidth - bOffset, bHeight - bOffset);

        d = new ImageIcon(Objects.requireNonNull(getClass().getResource("/closet.png")));
        tile = resizeIcon(d, bWidth - bOffset, bHeight - bOffset);

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
        if (b.getText().equals("1"))
            b.setForeground(Color.blue);
        else if (b.getText().equals("2"))
            b.setForeground(new Color(76, 153, 0));
        else if (b.getText().equals("3"))
            b.setForeground(Color.red);
        else if (b.getText().equals("4"))
            b.setForeground(new Color(153, 0, 0));
        else if (b.getText().equals("5"))
            b.setForeground(new Color(153, 0, 153));
        else if (b.getText().equals("6"))
            b.setForeground(new Color(96, 96, 96));
        else if (b.getText().equals("7"))
            b.setForeground(new Color(0, 0, 102));
        else if (b.getText().equals("8"))
            b.setForeground(new Color(153, 0, 76));
    }



}
