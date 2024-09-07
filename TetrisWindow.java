/**
 * Drawn the outline well of TetrisGame 
 * author: Rajeev Patel
 * modified on 15-3-2024
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TetrisWindow extends JFrame implements ActionListener{

    private TetrisGame game;
    private TetrisDisplay display;
    private final int win_Width = 600;
    private final int win_Height = 600;
    private final int game_rows = 20;
    private final int game_cols = 12;

    public TetrisWindow() {
        setTitle("My Tetris Game   Rajeev Patel");
        setSize(win_Width, win_Height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new TetrisGame(game_rows, game_cols);
        display = new TetrisDisplay(game);
        
        initMenu();

        this.add(display);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) 
    {
        Leaderboard scores = new Leaderboard();
        JMenuItem button = (JMenuItem) ae.getSource();

        if(button.getText().equals("New Game"))
        {
            game.newGame();
            display.setTimer(true);
            display.repaint();
        }
        else if(button.getText().equals("Save"))
        {
            display.setTimer(false);
            game.saveToFile();
        }
        else if(button.getText().equals("Retrieve"))
        {
            display.setTimer(false);
            game.retrieveFromFile();
            display.repaint();
            display.setTimer(true);
        }
        else if(button.getText().equals("Quit"))
        {
            System.exit(0);
        }
        else if(button.getText().equals("Show LeaderBoard"))
        {
            display.setTimer(false);
            scores.showLeaderboard();
        }
        else if(button.getText().equals("Clear Leaderboard"))
        {
            display.setTimer(false);
            scores.clear();
        }
    }

    private void initMenu() 
    {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu settingsMenu = new JMenu("Options");
        JMenu leaderBoardMenu = new JMenu("Leaderboard");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(this);

        JMenuItem saveGame = new JMenuItem("Save");
        saveGame.addActionListener(this);

        JMenuItem retrieveGame = new JMenuItem("Retrieve");
        retrieveGame.addActionListener(this);

        JMenuItem quitGame = new JMenuItem("Quit");
        quitGame.addActionListener(this);

        JMenuItem scoreBoard = new JMenuItem("Show LeaderBoard");
        scoreBoard.addActionListener(this);

        JMenuItem clearBoard = new JMenuItem("Clear Leaderboard");
        clearBoard.addActionListener(this);

        settingsMenu.add(newGame);
        settingsMenu.addSeparator();
        settingsMenu.add(saveGame);
        settingsMenu.addSeparator();
        settingsMenu.add(retrieveGame);
        settingsMenu.addSeparator();
        settingsMenu.add(quitGame);

        leaderBoardMenu.add(scoreBoard);
        leaderBoardMenu.addSeparator();
        leaderBoardMenu.add(clearBoard);

        menuBar.add(settingsMenu);
        menuBar.add(leaderBoardMenu);
    }

     public static void main(String[] args) {

        TetrisWindow win = new TetrisWindow();
    }
}
