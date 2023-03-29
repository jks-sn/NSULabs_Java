package ru.nsu.ccfit.berkaev.ui.gui;

import ru.nsu.ccfit.berkaev.logic.Board;
import ru.nsu.ccfit.berkaev.logic.Game;
import ru.nsu.ccfit.berkaev.timer.Timer;
import ru.nsu.ccfit.berkaev.ui.GameViewInterface;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;


public class GameFrame extends JFrame implements GameViewInterface {
    // The buttons
    private JButton[][] buttons;

    // Number of Buttons in Grid
    private int rows;
    private int columns;

    // Labels
    private JLabel minesLabel;
    private int mines;


    // Frame settings
    private final String FRAME_TITLE = "Minesweeper";

    private int FRAME_WIDTH = 520;
    private int FRAME_HEIGHT = 550;
    private int FRAME_LOC_X = 430;
    private int FRAME_LOC_Y = 50;

    // Icons
    private Icon redMine;
    private Icon mine;
    private Icon flag;
    private Icon tile;


    // Menu Bar and Items

    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem newGame;
    private JMenuItem statistics;
    private JMenuItem exit;
    public GameFrame(int number_rows, int number_columns, int mines)
    {
        rows = number_rows;
        columns = number_columns;
        buttons = new JButton[rows][columns];

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(FRAME_TITLE);
        setLocation(FRAME_LOC_X, FRAME_LOC_Y);

        JPanel gameBoard;

        JPanel scorePanel;

        gameBoard = new JPanel();
        gameBoard.setLayout(new GridLayout(rows,columns,0,0));

        for( int x=0 ; x<rows ; x++ )
        {
            for( int y=0 ; y<columns ; y++ )
            {
                // Set button text.
                buttons[x][y] = new JButton("");

                // Set button name (x,y).
                buttons[x][y].setName(Integer.toString(x) + "," + Integer.toString(y));
                buttons[x][y].setFont(new Font("Serif", Font.BOLD, 24));

                buttons[x][y].setBorder(BorderFactory.createLineBorder(Color.black, 1, true));

                // Add this button to the gameboard.
                gameBoard.add(buttons[x][y]);
            }
        }

        JPanel timePassedPanel = new JPanel();
        timePassedPanel.setLayout(new BorderLayout(10,0));

        // Initialize the time passed label.


        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

        JLabel iT = new JLabel("",SwingConstants.CENTER);
        iT.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("src/main/res/timer.png"))));

        timePassedPanel.add(iT, BorderLayout.WEST);
        timePassedPanel.setOpaque(false);


        JPanel minesPanel = new JPanel();
        minesPanel.setLayout(new BorderLayout(10,0));


        // Initialize mines label.
        this.minesLabel = new JLabel ("  0  " , SwingConstants.CENTER);
        minesLabel.setFont(new Font("Serif", Font.BOLD, 20));
        minesLabel.setBorder(loweredetched);
        minesLabel.setBackground(new Color(110,110,255));
        minesLabel.setForeground(Color.white);

        minesLabel.setOpaque(true);

        JLabel mT = new JLabel("", SwingConstants.CENTER);
        mT.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/mine.png"))));

        minesPanel.add(minesLabel, BorderLayout.WEST);
        minesPanel.add(mT, BorderLayout.CENTER);
        minesPanel.setOpaque(false);

        menuBar = new JMenuBar();

        gameMenu = new JMenu("Game");

        newGame = new JMenuItem("   New Game");
        statistics = new JMenuItem("   Statistics");
        exit = new JMenuItem("   Exit");

        newGame.setName("New Game");
        statistics.setName("Statistics");
        exit.setName("Exit");

        gameMenu.add(newGame);
        gameMenu.add(statistics);
        gameMenu.add(exit);

        menuBar.add(gameMenu);

        JPanel p = new JPanel();
        p.setLayout(new BorderLayout(0,10));
        p.add(gameBoard, BorderLayout.CENTER);


        p.setBorder(BorderFactory.createEmptyBorder(60, 60, 14, 60));
        p.setOpaque(false);


        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/background_berserk.jpg"))));

        add(background);

        background.setLayout(new BorderLayout(0,0));

        background.add(menuBar,BorderLayout.NORTH);
        background.add(p, BorderLayout.CENTER);


        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/mine.png")));

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    public void init()
    {
        hideAll();
        enableAll();
    }
    private void handleButtonClick(JButton button) {
        // обработка клика на кнопку
    }
    public void hideAll()
    {
        for( int x=0 ; x<columns ; x++ )
        {
            for( int y=0 ; y<rows ; y++ )
            {
                buttons[x][y].setText("");
                buttons[x][y].setBackground(new Color(0,103,200));
                buttons[x][y].setIcon(tile);
            }
        }
    }
    public void enableAll()
    {
        for( int x=0 ; x<columns ; x++ )
        {
            for( int y=0 ; y<rows ; y++ )
            {
                buttons[x][y].setEnabled(true);
            }
        }
    }    public void disableAll()
    {
        for( int x=0 ; x<columns ; x++ )
        {
            for( int y=0 ; y<rows ; y++ )
            {
                buttons[x][y].setEnabled(false);
            }
        }
    }
    public void setButtonListeners(Game game)
    {
        addWindowListener(game);

        // Set listeners for all buttons in the grid in gameBoard
        for( int x=0 ; x<columns ; x++ )
        {
            for( int y=0 ; y<rows ; y++ )
            {
                    buttons[x][y].addMouseListener(new MouseAdapter()
                    {
                        @Override
                        public void mousePressed(MouseEvent e)
                        {
                            if(!e.getComponent().isEnabled())
                                return;
                            if(!game.getGameStarted())
                            {
                                Minesweeper.initField(finalI % Minesweeper.getFieldSize().width, finalI / Minesweeper.getFieldSize().height);
                                game.playGame();
                                pause.setVisible(true);
                                replay.setVisible(true);
                                turnOnTimer();
                            }

                            if(SwingUtilities.isLeftMouseButton(e))
                                Minesweeper.openCell(finalI % Minesweeper.getFieldSize().width, finalI / Minesweeper.getFieldSize().height);
                            else if(SwingUtilities.isRightMouseButton(e))
                                Minesweeper.markCell(finalI % Minesweeper.getFieldSize().width, finalI / Minesweeper.getFieldSize().height);
                        }
                    });
            }
        }
    @Override
    public void updateFlag(int x, int y, Board board) {

    }

    @Override
    public void updateBoard(Board board) {

    }

    @Override
    public void writeFirstCommandMessage() {

    }

    @Override
    public void updateTimer(Timer timer) {

    }

    @Override
    public void restartGame() {

    }

    @Override
    public void noticeWinGame() {

    }

    @Override
    public void noticeLoseGame() {

    }

    @Override
    public void updatePause() {

    }

    @Override
    public void writeMessageUnknownCommand() {

    }

    @Override
    public Object[] getCommand() {
        return new Object[0];
    }
}
