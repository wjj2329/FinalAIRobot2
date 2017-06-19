package RobotFunctions;

import MainStuff.Main2;
import Map.City;
import Map.Coordinate;
import Map.Node;
import Map.TerrainMap;
import TelnetFunctions.Telnet;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Alex on 6/14/17.
 * Class encapsulates our lovely, MSP430-powered robot!
 * We will have two physical robots, so make sure that they
 *  are structured to work in tandem together.
 * 🤖 <- Beepy loves the MSP430. :D
 */
public class Robot
{

    private ArrayList<Node> computedPathRobot1;
    private ArrayList<Node> computedPathRobot2;
    private MachineVision vision;
    private TerrainMap map;
    private Coordinate currentLocation;
    private Coordinate orientation;
    private ArrayList<City> myCities;
    /**
     * Default public constructor
     */
    public Robot()
    {
        myCities = new ArrayList<>();
        map = new TerrainMap();
        computedPathRobot1 = new ArrayList<>();
        vision = new MachineVision();
    }

    public void calculateTerrainMap(ArrayList<City> cities)
    {
        for (City currentCity : cities)
        {
            System.out.println(currentCity.toString());
            // The double for loop
            for (int p = 0; p < map.getMyMap().length; p++)
            {
                for (int q = 0; q < map.getMyMap()[p].length; q++)
                {
                    Node current = map.getMyMap()[p][q];
                    //current.setLocation(new Coordinate(p, q));
                    // Set type to obstacle here if we end up adding obstacles
                }
            }
            myCities.add(currentCity); // or just use addAll
        }
    }
    public void calculatePath()
    {
        computedPathRobot1 = vision.computeBasicPath(myCities, map, this); // use the correct one.
    }


    /**
     * Rotation of the robot
     * @param t necessary Telnet client
     */
    public void rotateMe(Telnet t) throws IOException, InterruptedException
    {
        // Figure out the angles
        double currentAngle = RobotUtils.robotCurrentAngle(this.orientation);
        currentAngle = Math.toDegrees(currentAngle);
        // Normalize
        if (currentAngle < 0)
        {
            currentAngle += 360;
        }
        Coordinate c = RobotUtils.convertFromPixelToNode(currentLocation);
        // We will need a different ending condition, OR to set the last arUco card in the path to GOAL
        if (map.getMyMap()[(int)c.getX()][(int)c.getY()].getType()== RobotUtils.TYPE.GOAL)
        {
            t.sendSpeed(0, 0);
            System.exit(0);
        }
        double angleINeedToBecome = map.getMyMap()[(int)c.getX()][(int)c.getY()].getDegree();

        if(angleINeedToBecome == -1 || angleINeedToBecome == Integer.MAX_VALUE)
        {
            //t.sendSpeed(0, 0);
            //Thread.sleep(3000);
            System.out.println("May have left path");
            return;
        }

        double angle1 = Math.abs(angleINeedToBecome - currentAngle);
        double angle2 = 360.0 - angle1;
        double angleICareAbout;
        if (angle1 < angle2)
        {
            angleICareAbout = angle1;
        }
        else
        {
            angleICareAbout = angle2;
        }
        if (angleICareAbout > RobotUtils.marginoferror)
        {
            System.out.println("Robot is at " + angleICareAbout + ", and needs to become " + angleINeedToBecome);
            while (angleICareAbout > RobotUtils.innermarginofError)
            {
                t.sendSpeed(2, -2);
                String responseFromServer = t.sendWhere();
                if (responseFromServer.equals("None") || responseFromServer.equals("") ||
                        responseFromServer.equals("\n"))
                {
                    continue;
                }
               // Decoder.updateRobot(this, responseFromServer);
                Main2.mymutex.lock();
                currentAngle = Math.toDegrees(RobotUtils.robotCurrentAngle(orientation));
                Main2.mymutex.unlock();
                if (currentAngle < 0)
                {
                    currentAngle += 360;
                }
                angle1 = Math.abs(angleINeedToBecome - currentAngle);
                angle2 = 360.0 - angle1;
                if(angle1 < angle2)
                {
                    angleICareAbout = angle1;
                }
                else
                {
                    angleICareAbout = angle2;
                }
            }
            t.sendSpeed(-2, 2);
            Thread.sleep(1200);
            t.sendSpeed(0, 0);
        }
    }

    public boolean shouldIPause()
    {
        int result = -1;
        // Robot needs to be in node coordinates.
        for (int i = 0; i < myCities.size(); i++)
        {
            if (myCities.get(i).getLocation().equals(RobotUtils.convertFromPixelToNode(currentLocation)))
            {
                //computedPathRobot1.cleanseMyMap(i);
                result = i;
                for (int j = 0; j < computedPathRobot1.size(); j++)
                {
                    if (computedPathRobot1.get(j).getLocation().equals(myCities.get(i).getLocation()))
                    {
                        // I think that this is right
                        if (computedPathRobot1.get(j).getDegreeMap().size() > 1)
                        {
                            System.out.println("This is the map beforehand: ");
                            computedPathRobot1.get(j).printDegreeMap();
                            computedPathRobot1.get(j).cleanseMyMap(j);
                            System.out.println("This is the map afterwards: ");
                            computedPathRobot1.get(j).printDegreeMap();
                        }
                    }
                }
                myCities.remove(result);
                return true;
            }
        }
        return false;
    }

    public boolean end()
    {
        if (RobotUtils.convertFromPixelToNode(currentLocation)
                .equals(computedPathRobot1.get(computedPathRobot1.size() - 1).getLocation()))
        {
            return true;
        }
        return false;
    }

    //<editor-fold desc="Getters and Setters!">


    public MachineVision getVision()
    {
        return vision;
    }

    public void setVision(MachineVision vision)
    {
        this.vision = vision;
    }

    public TerrainMap getMap()
    {
        return map;
    }

    public void setMap(TerrainMap map)
    {
        this.map = map;
    }

    public Coordinate getCurrentLocation()
    {
        return currentLocation;
    }

    public void setCurrentLocation(Coordinate currentLocation)
    {
        this.currentLocation = currentLocation;

    }

    public Coordinate getOrientation()
    {
        return orientation;
    }

    public void setOrientation(Coordinate orientation)
    {

     this.orientation = orientation;
    }

    public ArrayList<City> getMyCities() {
        return myCities;
    }

    public void setMyCities(ArrayList<City> myCities) {
        this.myCities = myCities;
    }

    //</editor-fold>
}
