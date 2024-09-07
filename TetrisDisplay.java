/**
 *This is the display page of tetris
 * @author rajeevpatel
 * 15/03/2024
 */

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;

public class TetrisDisplay extends JPanel {

    private int start_x = 100;
    private int start_y = 100;
    private final int cell_size = 20;
    private boolean pause = false;
    private int delay = 150;
    private Timer timer;
    private Color[] colors = {Color.WHITE, Color.PINK, Color.RED, Color.BLUE, Color.GREEN, 
                                Color.GRAY, Color.ORANGE, Color.CYAN};
    private TetrisGame game;

    public TetrisDisplay(TetrisGame gam) {
        this.game = gam;

        this.timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                cycleMove();
            }
        });

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke)
            {
                translateKey(ke);
            }
        });

        timer.start();
        this.setFocusable(true);
    }
    
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        drawWell(g);
        drawBackground(g);
        drawScore(g);
        drawFallingBrick(g);

        if(game.getState() == 0)
        {
            drawGameOver(g);
        }
    }

    private void drawFallingBrick(Graphics g)
    {
        for(int numSeg = 0; numSeg < game.getNumSegs(); numSeg++)
        {
            int row = game.getSegCol(numSeg) * cell_size + this.start_x;
            int col = game.getSegRow(numSeg) * cell_size + this.start_y;
            g.setColor(colors[game.getFallingBrickColor()]);
            g.fillRect(row, col, cell_size, cell_size);
            g.setColor(Color.BLACK);
            g.drawRect(row, col, cell_size, cell_size);
        }
    }

    private void drawWell(Graphics g) 
    {
        int wellCenter_x = this.start_x;
        int wellCenter_y = this.start_y;
        int centerWidth = game.getCols()*cell_size;
        int centerHeight = game.getRows()*cell_size;
        
        int leftWall_x = wellCenter_x - cell_size;
        int leftWall_y = wellCenter_y;
        int leftWall_width = cell_size;
        int leftWall_height = centerHeight;
        g.setColor(Color.BLACK);
        g.fillRect(leftWall_x, leftWall_y, leftWall_width, leftWall_height);
        
        int bottomWall_x = leftWall_x;
        int bottomWall_y = leftWall_y + centerHeight;
        int bottomWall_width = (game.getCols()+2) * cell_size;
        int bottomWall_height = cell_size;
        g.fillRect(bottomWall_x, bottomWall_y, bottomWall_width, bottomWall_height);
        
        int rightWall_x = wellCenter_x + centerWidth;
        int rightWall_y = leftWall_y;
        int rightWall_width = cell_size;
        int rightWall_height = centerHeight;
        g.fillRect(rightWall_x, rightWall_y, rightWall_width, rightWall_height);
        
    }

    private void drawBackground(Graphics g)
    {

        for(int row = 0; row < game.getRows(); row++)
        {
            for(int col = 0; col < game.getCols(); col++)
            {
                int backgroundColorNum = game.fetchBoardPosition(row, col);
                int startRow = this.start_x + col * cell_size;
                int startCol = this.start_y + row * cell_size;

                if(backgroundColorNum == 0)
                {
                    g.setColor(Color.WHITE);
                    g.fillRect(startRow, startCol, cell_size, cell_size);
                }
                else
                {
                    g.setColor(colors[backgroundColorNum]);
                    g.fillRect(startRow, startCol, cell_size, cell_size);
                    g.setColor(Color.BLACK);
                    g.drawRect(startRow, startCol, cell_size, cell_size);
                }
            }
        }
    }

    private void drawScore(Graphics g)
    {
        int fontSize = 40;
        String score = "Score: " + Integer.toString(game.getScore());
        Font fontType = new Font("Arial", 1, fontSize);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, score.length() * 21, 60);
        g.setColor(Color.BLUE);
        g.setFont(fontType);
        g.drawString(score, 0, 40);
    }

    private void drawGameOver(Graphics g)
    {
        int fontSize = 60;
        String gameOver = "Game Over!";
        Font fontType = new Font("Arial", 1, fontSize);

        g.setColor(Color.WHITE);
        g.fillRect(start_x - 40, start_y + (game.getRows()/2 * cell_size), 
            (gameOver.length() + 7) * cell_size, 60);
        g.setColor(Color.BLUE);
        g.setFont(fontType);
        g.drawString(gameOver, start_x - 40, start_y + 250);
    }

    private void translateKey(KeyEvent ke)
    {
        if(ke.getKeyCode() == KeyEvent.VK_N)
        {
            game.newGame();
            timer.start();
        }
        else if(ke.getKeyCode() == KeyEvent.VK_SPACE)
        {
            pause = !pause;
            if(pause == true)
            {
                timer.stop();
            }
            else
            {
                timer.start();
            }
        }
        else if(ke.getKeyCode() == KeyEvent.VK_UP)
        {
            game.makeMove('T');
        }
        else if(ke.getKeyCode() == KeyEvent.VK_DOWN)
        {
            game.makeMove('d');
        }
        else if(ke.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            game.makeMove('R');
        }
        else if(ke.getKeyCode() == KeyEvent.VK_LEFT)
        {
            game.makeMove('L');
        }
        this.repaint();
    }

    private void cycleMove()
    {
        game.makeMove('D');
        this.repaint();
    }

    public void setTimer(boolean timerTiming)
    {
        if(timerTiming) {
            timer.start();
        }
        else if(!timerTiming)
        {
            timer.stop();
        }
    }
}
