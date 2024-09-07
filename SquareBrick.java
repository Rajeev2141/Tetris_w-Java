/**
 *This is subclass SquareBrick
 * @author rajeevpatel
 * 15/03/2024
 */

public class SquareBrick extends TetrisBrick {
    
    public SquareBrick(int center_column)
    {
        this.colorNum = 5;
        initPosition(center_column);
    }
    
    public void initPosition(int center_column) 
    {
        this.position = new int[][] {
            {0, center_column - 1},
            {0, center_column},
            {1, center_column - 1},
            {1, center_column},
        };
    }

    public void rotate() {}

    public void unrotate() {}
}
