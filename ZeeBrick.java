/**
 *This is subclass ZeeBrick
 * @author rajeevpatel
 * 7/04/2024
 */

public class ZeeBrick extends TetrisBrick{
    
    public ZeeBrick(int center_column)
    {
        this.colorNum = 7;
        initPosition(center_column);
    }
    
    public void initPosition(int center_column) 
    {
        this.position = new int[][] {
            {0, center_column - 1},
            {0, center_column},
            {1, center_column},
            {1, center_column + 1},
        };
    }

    public void rotate()
    {
        int[] zeroSeg = new int[] {position[0][0], position[0][1]};

        if(zeroSeg[0] == position[2][0] - 1 &&
            zeroSeg[1] == position[2][1] - 1)
        {
            position = new int[][]
            {
                {position[1][0] - 1, position[2][1] + 1},
                {position[1][0], position[1][1]},
                {position[2][0], position[2][1]},
                {position[1][0], position[2][1] + 1}
            };
        }
        else if(zeroSeg[0] == position[2][0] - 2 &&
            zeroSeg[1] == position[2][1] + 1)
        {
            position = new int[][]
            {
                {position[2][0] - 1, position[2][1] - 1},
                {position[2][0] - 1, position[2][1]},
                {position[2][0], position[2][1]},
                {position[2][0], position[2][1] + 1}
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