
/**
 * This is superclass TetrisBrick created for the movement of Tetris
 * @author rajeevpatel
 * 15/03/2024
 */

import java.awt.Color;

public abstract class TetrisBrick {
    
    protected int numSegments = 4;
    protected int[][] position = new int[numSegments][2];
    protected Color color;
    protected int colorNum;
    
    public TetrisBrick(){}

    public String toString() 
    {
        String brick = Integer.toString(colorNum) + "\n";
        for(int row = 0; row < position.length; row++)
        {
            for(int col = 0; col < position[row].length; col++)
            {
                brick += position[row][col];
                if(col != position[row].length - 1)
                {
                    brick += ",";
                }
            }
            if(row != position.length - 1)
            {
                brick += "\n";
            }
        }
        return brick;
    }
    
    public void moveDown()
    {
        for(int seg = 0; seg < position.length; seg++) 
        {
            position[seg][0]++;
        }

    }
    
    public void moveUp()
    {
     
        for(int seg = 0; seg < position.length; seg++)
        {
            position[seg][0]--;
        }
    }

    public void moveRight()
    {

        for(int seg = 0; seg < position.length; seg++)
        {
            position[seg][1]++;
        }
    }

    public void moveLeft()
    {

        for(int seg = 0; seg < position.length; seg++)
        {
            position[seg][1]--;
        }
    }

    public int getMinimumRow()
    {
        int lowRow = position[0][0];
        for(int[] segs : position) {
            if(segs[0] < lowRow) {
                lowRow = segs[0];
            }
        }

        return lowRow;
    }

    public int getMaximumRow() 
    {
        int highRow = position[0][0];
        for(int[] segs : position) {
            if(segs[0] > highRow) {
                highRow = segs[0];
            }
        }

        return highRow;
    }
    
    public int getColorNumber()
    {
        return colorNum;
    }
    
    public abstract void initPosition(int center_column);

    public abstract void rotate();

    public abstract void unrotate();
    
}
