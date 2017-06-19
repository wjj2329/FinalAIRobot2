package RobotFunctions;

import Map.City;
import Map.Coordinate;
import Map.Node;

/**
 * Created by Alex on 6/14/17.
 * Utilities class for the robot.
 */
public class RobotUtils
{
    public static int sizeoftiles=60; // was 60
    public static int gridDimensionX = 1960/sizeoftiles;
    public static int gridDimensionY = 1020/sizeoftiles;
    public static int marginoferror=25; // was 30
    public static int innermarginofError=15; // was 20
    public static boolean startoff=true;

    public static double distance(Coordinate point1, Coordinate point2)
    {
        double first=point1.getX()-point2.getX();
        double second=point1.getY()-point2.getY();
        return Math.sqrt((first*first)+(second*second));
    }
    public enum TYPE
    {
        GOAL, REGULAR, START
    }
    /**
     * Sign: Returns the sign, either + or -, of a number.
     * If it is zero, we will still be multiplying it
     *  by negative infinity. So keep it as 1.
     */
    public static int sign(double param)
    {
        if (param < 0)
        {
            return -1;
        }
        return 1;
    }
    public static double robotCurrentAngle(Coordinate orientation)
    {
        double x = orientation.getX();
        double y = orientation.getY();
        return Math.acos(x) * sign(y);
    }
    public static Coordinate convertFromPixelToNode(Coordinate c)
    {
        return new Coordinate(Math.round(c.getX()/sizeoftiles), Math.round(c.getY()/sizeoftiles));
    }
    public static double calculateAngle(Node calc, Node next)
    {
        double xdiff=calc.getLocation().getX()-next.getLocation().getX();
        double ydiff=calc.getLocation().getY()-next.getLocation().getY();
        //System.out.println("xDiff is: " + xdiff);
        //System.out.println("yDiff is: " + ydiff);
        if(xdiff==1&&ydiff==0)
        {
            return 180;
        }
        if(xdiff==1&&ydiff==1)
        {
            return 225;
        }
        if(xdiff==0&&ydiff==1)
        {
            return 270;
        }
        if(xdiff==-1&&ydiff==1)
        {
            return 315;
        }
        if(xdiff==-1&&ydiff==0)
        {
            return 0;
        }
        if(xdiff==-1&&ydiff==-1)
        {
            return 45;
        }
        if(xdiff==0&&ydiff==-1)
        {
            return 90;
        }
        if(xdiff==1&&ydiff==-1)
        {
            return 135;
        }
        System.out.println("You done goofed, son.");
        return -1;
    }
}