package Map;

import RobotFunctions.RobotUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static RobotFunctions.RobotUtils.TYPE.REGULAR;

/**
 * Created by williamjones on 5/15/17.
 * Class designed to represent a node in our traversal graph.
 */
public class Node
{
    //private double degree = -1;
    private Coordinate location = new Coordinate(-1, -1);
    private Map<Integer, Double> degreeMap;

    // these would be in pixels, not node coordinates
    private Coordinate topLeft;
    private Coordinate topRight;
    private Coordinate bottomLeft;
    private Coordinate bottomRight;

    private boolean partOfPath = false;
    /**
     * All the previous nodes up to this point in our current path
     */
    private ArrayList<Node> pathVisited;

    private RobotUtils.TYPE type;

    public Node()
    {
        type = REGULAR;
        pathVisited = new ArrayList<>();
        degreeMap = new HashMap<>();
    }

    public void cleanseMyMap(int posInPath)
    {
        Map<Integer, Double> theCopy = new HashMap<>(degreeMap);
        for (int current : degreeMap.keySet())
        {
            if (current <= posInPath)
            {
                //degreeMap.remove()
                theCopy.remove(current);
            }
        }
        degreeMap = theCopy;
    }

    public void printmylist()
    {

        for(Node n:pathVisited)
        {
            System.out.println(n.toString());
        }
    }

    public void printDegreeMap()
    {
        System.out.println("printDegreeMap");
        for (Integer key : degreeMap.keySet())
        {
            System.out.println("key=" + key + ", value=" + degreeMap.get(key));
        }
    }
    //<editor-fold desc="Equals/HashCode - Do NOT delete">

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (partOfPath != node.partOfPath) return false;
        if (location != null ? !location.equals(node.location) : node.location != null) return false;
        if (degreeMap != null ? !degreeMap.equals(node.degreeMap) : node.degreeMap != null) return false;
        if (topLeft != null ? !topLeft.equals(node.topLeft) : node.topLeft != null) return false;
        if (topRight != null ? !topRight.equals(node.topRight) : node.topRight != null) return false;
        if (bottomLeft != null ? !bottomLeft.equals(node.bottomLeft) : node.bottomLeft != null) return false;
        if (bottomRight != null ? !bottomRight.equals(node.bottomRight) : node.bottomRight != null) return false;
        if (pathVisited != null ? !pathVisited.equals(node.pathVisited) : node.pathVisited != null) return false;
        return type == node.type;
    }

    @Override
    public int hashCode()
    {
        int result = location != null ? location.hashCode() : 0;
        result = 31 * result + (degreeMap != null ? degreeMap.hashCode() : 0);
        result = 31 * result + (topLeft != null ? topLeft.hashCode() : 0);
        result = 31 * result + (topRight != null ? topRight.hashCode() : 0);
        result = 31 * result + (bottomLeft != null ? bottomLeft.hashCode() : 0);
        result = 31 * result + (bottomRight != null ? bottomRight.hashCode() : 0);
        result = 31 * result + (partOfPath ? 1 : 0);
        result = 31 * result + (pathVisited != null ? pathVisited.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "Node{" +
                "location=" + location +
                ", degreeMap=" + degreeMap +
                ", topLeft=" + topLeft +
                ", topRight=" + topRight +
                ", bottomLeft=" + bottomLeft +
                ", bottomRight=" + bottomRight +
                ", partOfPath=" + partOfPath +
                ", pathVisited=" + pathVisited +
                ", type=" + type +
                '}';
    }


    //</editor-fold>

    //<editor-fold desc="Getters/Setters! :D">

    /**
     * Gets the MINIMUM DEGREE.
     */
    public double getDegree()
    {
        //return degree;
        int min = Integer.MAX_VALUE;
        for (int currentNumber : degreeMap.keySet())
        {
            if (currentNumber < min)
            {
                min = currentNumber;
            }
        }
        System.out.println("Minimum degree is " + min);
        System.out.println("How big is the degree map? " + degreeMap.size());
        System.out.println("What's in the degree map? ");
        // return -1
        printDegreeMap();
        double answer = Integer.MAX_VALUE;
        if (min != Integer.MAX_VALUE)
        {
            answer = degreeMap.get(min);
        }
        return answer;
    }

    public void setDegree(int posInPath, double degree)
    {
        //this.degree = degree;
        degreeMap.put(posInPath, degree);
    }

    public Coordinate getLocation()
    {
        return location;
    }

    public void setLocation(Coordinate location)
    {
        this.location = location;
    }

    public Coordinate getTopLeft()
    {
        return topLeft;
    }

    public void setTopLeft(Coordinate topLeft)
    {
        this.topLeft = topLeft;
    }

    public Coordinate getTopRight()
    {
        return topRight;
    }

    public void setTopRight(Coordinate topRight)
    {
        this.topRight = topRight;
    }

    public Coordinate getBottomLeft()
    {
        return bottomLeft;
    }

    public void setBottomLeft(Coordinate bottomLeft)
    {
        this.bottomLeft = bottomLeft;
    }

    public Coordinate getBottomRight()
    {
        return bottomRight;
    }

    public void setBottomRight(Coordinate bottomRight)
    {
        this.bottomRight = bottomRight;
    }

    public ArrayList<Node> getPathVisited()
    {
        if (pathVisited == null)
        {
            pathVisited = new ArrayList<>();
        }
        return pathVisited;
    }

    public void setPathVisited(ArrayList<Node> pathVisited)
    {
        this.pathVisited = pathVisited;
    }

    public RobotUtils.TYPE getType()
    {
        return type;
    }

    public void setType(RobotUtils.TYPE type)
    {
        this.type = type;
    }

    public boolean isPartOfPath()
    {
        return partOfPath;
    }

    public void setPartOfPath(boolean partOfPath)
    {
        this.partOfPath = partOfPath;
    }

    public Map<Integer, Double> getDegreeMap()
    {
        return degreeMap;
    }

    public void setDegreeMap(Map<Integer, Double> degreeMap)
    {
        this.degreeMap = degreeMap;
    }

    //</editor-fold>
}

