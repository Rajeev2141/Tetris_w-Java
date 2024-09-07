/**
 *CSCI 282 lab 4 which is a tetris game
 * @author rajeevpatel
 * 15/03/2024
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class TetrisGame {

    private TetrisBrick fallingBrick;
    private int[][] background;
    private int rows;
    private int cols;
    private int numBrickTypes = 7;
    private int state = 1;
    private int score = 0;
    private Random randomGen;
    private JFileChooser selector;
    
    public TetrisGame(int rows, int cols)
    {
        this.rows = rows;
        this.cols = cols;
        randomGen = new Random();
        background = new int[rows][cols];

        selector = new JFileChooser("./saved", 
            FileSystemView.getFileSystemView());

        initBoard();
        spawnBrick();
    }

    public String toString()
    {
        String data = Integer.toString(rows) + "," + Integer.toString(cols);
        data += "\n" + Integer.toString(score) + "\n";

        for(int rowNum = 0; rowNum < background.length; rowNum++)
        {
            for(int colNum = 0; colNum < background[rowNum].length; colNum++)
            {
                data += background[rowNum][colNum];
                if(colNum != background[rowNum].length - 1)
                {
                    data += ",";
                }
            }
            data += "\n";
        }

        return data;
    }

    public void initBoard()
    {
        
        for(int segRow = 0; segRow < rows; segRow++)
        {
            for(int segCol = 0; segCol < cols; segCol++)
            {
                background[segRow][segCol] = 0;
            }
        }
    }

    public void newGame()
    {
        state = 1;
        score = 0;
        initBoard();
        spawnBrick();
    }

    public int fetchBoardPosition(int row, int col)
    {
        return background[row][col];
    }

    private void transferColor()
    {
        for(int segPos = 0; segPos < fallingBrick.position.length; segPos++)
        {
            background[fallingBrick.position[segPos][0]]
            [fallingBrick.position[segPos][1]] = fallingBrick.colorNum;
        }
        scoreGame();
    }
    
    private void spawnBrick()
    {
        Leaderboard scoreBoard = new Leaderboard();
        int center_col = cols/2;
        int randNum = randomGen.nextInt(numBrickTypes);

        if(background[0][center_col] != 0)
        {
            randNum = 9;
        }

        if(randNum == 0)
        {
            fallingBrick = new ElBrick(center_col);
        }
        else if(randNum == 1)
        {
            fallingBrick = new EssBrick(center_col);
        }
        else if(randNum == 2)
        {
            fallingBrick = new JayBrick(center_col);
        }
        else if(randNum == 3)
        {
            fallingBrick = new LongBrick(center_col);
        }
        else if(randNum == 4)
        {
            fallingBrick = new SquareBrick(center_col);
        }
        else if(randNum == 5)
        {
            fallingBrick = new StackBrick(center_col);
        }
        else if(randNum == 6)
        {
            fallingBrick = new ZeeBrick(center_col);
        }
        else
        {
            state = 0;
            scoreBoard.checkHighScore(score);
        }

        boolean spawnAgain = false;
        if(state == 1)
        {
            for(int[] segs : fallingBrick.position) {
                if(background[segs[0]][segs[1]] != 0) {
                    spawnAgain = true;
                    break;
                }
            }

            if(spawnAgain) {
                spawnBrick();
            }
        }
    }
    
    public void makeMove(char dir)
    {
        if(dir == 'T')
        {
            fallingBrick.rotate();
            if(!validateMove()) 
            {
                fallingBrick.unrotate();
            }
        }
        else if(dir == 'D')
        {
            fallingBrick.moveDown();
            if(!validateMove())
            {
                fallingBrick.moveUp();
                transferColor();
                spawnBrick();
            }
        }
        else if(dir == 'd')
        {
            boolean atBottom = false;
            while (!atBottom) {
                fallingBrick.moveDown();
                if(!validateMove())
                {
                    atBottom = true;
                    fallingBrick.moveUp();
                    transferColor();
                }
            }
            spawnBrick();
        }
        else if(dir == 'R')
        {
            fallingBrick.moveRight();
            if(!validateMove())
            {
                fallingBrick.moveLeft();
            }
        }
        else if(dir == 'L')
        {
            fallingBrick.moveLeft();
            if(!validateMove())
            {
                fallingBrick.moveRight();
            }
        }
    }

    private boolean rowHasSpace(int rowNum) 
    {
        boolean space = false;
        for(int val : background[rowNum]) 
        {
            if(val == 0)
             {
                space = true;
                break;
            }
        }

        return space;
    }

    private boolean rowHasColor(int rowNum)
    {
        boolean color = false;
        for(int val : background[rowNum])
        {
            if(val != 0)
            {
                color = true;
                break;
            }
        }

        return color;
    }

    private void copyRow(int rowNum)
    {
        for(int index = 0; index < background[rowNum].length; index++)
        {
            background[rowNum][index] = background[rowNum - 1][index]; 
        }
    }

    private void copyAllRows(int startRow)
    {

        while (startRow > 0)
        {
            if(rowHasColor(startRow))
            {
                copyRow(startRow);
            }
            startRow--;
        }
    }

    private void scoreGame()
    {
        boolean bricksFallen = false;
        int firstRow = fallingBrick.getMinimumRow();
        int lastRow = fallingBrick.getMaximumRow();
        int iter = 0;
        int numRowsDel = 0;

        while(!bricksFallen)
        {
            if(!rowHasSpace(firstRow + iter))
            {
                copyAllRows(firstRow + iter);
                numRowsDel++;

            }

            if(firstRow + iter >= lastRow)
            {
                bricksFallen = true;
            }
            iter++;
        }

        if(numRowsDel == 1)
        {
            score += 100;
        }
        else if(numRowsDel == 2)
        {
            score += 300;
        }
        else if(numRowsDel == 3)
        {
            score += 600;
        }
        else if(numRowsDel == 4)
        {
            score += 1200;
        }
    }
    
    private boolean validateMove()
    {
        boolean validMove = true;
        for(int seg = 0; seg < fallingBrick.position.length; seg++)
        {
            if(fallingBrick.position[seg][0] < 0)
            {
                validMove = false;
            }
            else if(fallingBrick.position[seg][0] >= rows)
            {
                validMove = false;
            }
            else if(fallingBrick.position[seg][1] < 0)
            {
                validMove = false;
            }
            else if(fallingBrick.position[seg][1] >= cols)
            {
                validMove = false;
            }
            else if(background[fallingBrick.position[seg][0]]
            [fallingBrick.position[seg][1]] != 0)
            {
                validMove = false;
            }
        }
        return validMove;
    }
    
    public void saveToFile()
    {
        selector.showSaveDialog(null);
        String path = selector.getSelectedFile().getAbsolutePath() + ".tet";

        try {

            FileWriter writer = new FileWriter(path, true);
            writer.append(toString());
            writer.append(fallingBrick.toString());
            writer.close();
        } catch(IOException ioe) {

        }
    }

    public void retrieveFromFile()
    {
        selector.showOpenDialog(null);
        File path = selector.getSelectedFile();

        try{
            Scanner reader = new Scanner(path);
            reader.useDelimiter(",|\n");

            while(reader.hasNext())
            {
                rows = Integer.parseInt(reader.next());
                cols = Integer.parseInt(reader.next());
                score = Integer.parseInt(reader.next());

                for(int rowNum = 0; rowNum < background.length; rowNum++)
                {
                    for(int colNum = 0; colNum < background[rowNum].length; colNum++)
                    {
                        background[rowNum][colNum] = Integer.parseInt(reader.next());
                    }
                }

                fallingBrick.colorNum = Integer.parseInt(reader.next());
                setBrickType(fallingBrick.colorNum);
                for(int segRow = 0; segRow < fallingBrick.position.length; segRow++)
                {
                    for(int segCol = 0; segCol < fallingBrick.position[segRow].length; segCol++)
                    {
                        fallingBrick.position[segRow][segCol] = Integer.parseInt(reader.next());
                    }
                }
            }
            reader.close();

        }catch(IOException ioe) {}
    }

    private void setBrickType(int colorNum)
    {
        int center_col = cols/2;

        if(colorNum == 1)
        {
            fallingBrick = new ElBrick(center_col);
        }
        else if(colorNum == 2)
        {
            fallingBrick = new EssBrick(center_col);
        }
        else if(colorNum == 3)
        {
            fallingBrick = new JayBrick(center_col);
        }
        else if(colorNum == 4)
        {
            fallingBrick = new LongBrick(center_col);
        }
        else if(colorNum == 5)
        {
            fallingBrick = new SquareBrick(center_col);
        }
        else if(colorNum == 6)
        {
            fallingBrick = new StackBrick(center_col);
        }
        else if(colorNum == 7)
        {
            fallingBrick = new ZeeBrick(center_col);
        }
    }

    public int getScore()
    {
        return score;
    }

    public int getState()
    {
        return state;
    }

    public int getNumSegs()
    {
        return fallingBrick.numSegments;
    }
    
    public int getFallingBrickColor()
    {
        return fallingBrick.colorNum;
    }
    
    public int getRows() {
        
        return rows;
    }

    public int getCols() {
        
        return cols;
    }

    public int getSegRow(int segmentNum)
    {
        return fallingBrick.position[segmentNum][0];
    }

    public int getSegCol(int segmentNum)
    {
        return fallingBrick.position[segmentNum][1];
    }
}