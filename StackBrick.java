/**
 * This is subclass StackBrick
 * @author rajeevpatel
 * 7/04/2024
 */

public class StackBrick extends TetrisBrick {
    
    public StackBrick(int center_column)
    {
        this.colorNum = 6;
        initPosition(center_column);
    }
    
    public void initPosition(int center_column) 
    {
        this.position = new int[][] {
            {0, center_column},
            {1, center_column - 1},
            {1, center_column},
            {1, center_column + 1},
        };
    }
    
    public void rotate()
    {

        int[] zeroSeg = new int[] {position[0][0], position[0][1]};

        if(zeroSeg[0] == position[2][0] - 1 &&
            zeroSeg[1] == position[2][1])
        {
            position = new int[][]
            {
                {position[2][0], position[2][1] + 1},
                {position[2][0] - 1, position[2][1]},
                {position[2][0], position[2][1]},
                {position[2][0] + 1, position[2][1]},
            };
        }
        else if(zeroSeg[0] == position[2][0] &&
            zeroSeg[1] == position[2][1] + 1)
        {
            position = new int[][]
            {
                {position[2][0] + 1, position[2][1]},
                {position[2][0], position[2][1] - 1},
                {position[2][0], position[2][1]},
                {position[2][0], position[2][1] + 1},
            };
        }
        else if(zeroSeg[0] == position[2][0] + 1 &&
            zeroSeg[1] == position[2][1])
        {
            position = new int[][]
            {
                {position[2][0], position[2][1] - 1},
                {position[2][0] - 1, position[2][1]},
                {position[2][0], position[2][1]},
                {position[2][0] + 1, position[2][1]},
            };
        }
        else if(zeroSeg[0] == position[2][0] &&
            zeroSeg[1] == position[2][1] - 1)
        {
            position = new int[][]
            {
                {position[2][0] - 1, position[2][1]},
                {position[2][0], position[2][1] - 1},
                {position[2][0], position[2][1]},
                {position[2][0], position[2][1] + 1},
            };
        }
    }

    public void unrotate()
    {
        rotate();
        rotate();
        rotate();
    }
}
