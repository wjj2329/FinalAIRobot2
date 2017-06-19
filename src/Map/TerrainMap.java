package Map;

import RobotFunctions.RobotUtils;

/**
 * Created by Alex on 6/14/17.
 * Class designed to represent our grid.
 */
public class TerrainMap
{
    /**
     * Squares which need to be converted
     *  FROM pixel coordinates.
     */
    private Node[][] myMap;

    /**
     * Guarding against those pesky null pointer exceptions.
     */
    private void initMap()
    {
        for (int i = 0; i < myMap.length; i++)
        {
            for (int j = 0; j < myMap[i].length; j++)
            {
                myMap[i][j] = new Node();
                myMap[i][j].setLocation(new Coordinate(i, j));
            }
        }
    }

    public TerrainMap()
    {
        // 1920 x 1080 pixels; there is no buffer
        myMap = new Node[RobotUtils.gridDimensionX][RobotUtils.gridDimensionY];
        initMap();
    }

    //<editor-fold desc="Getters and Setters">
    public Node[][] getMyMap()
    {
        return myMap;
    }

    public void setMyMap(Node[][] myMap)
    {
        this.myMap = myMap;
    }
    //</editor-fold>
}
