package RobotFunctions;

import Map.City;
import Map.Coordinate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by Alex on 6/14/17.
 * JSON decoder.
 */
public class Decoder
{
    /**
     * From JSON: The information we need to update the map
     */
    public static void updateTerrainField(Robot robot, String json)
    {
        ArrayList<City> cities=new ArrayList<>();
        JSONObject myObject = new JSONObject(json);
        final Iterator<String> keys = myObject.keys();
        while (keys.hasNext())
        {
            final String key = keys.next();
            if (!key.equals("robot") && !key.equals("time"))
            {
                JSONObject jason = (JSONObject) myObject.get(key);
                JSONArray orientation = jason.getJSONArray("orientation");
                double xCoord = orientation.getDouble(0);
                double yCoord = orientation.getDouble(1);
                Coordinate res = new Coordinate((int) xCoord, (int) yCoord);
                JSONArray corners = jason.getJSONArray("corners");
                JSONArray corner1 = corners.getJSONArray(0);
                JSONArray corner2 = corners.getJSONArray(1);
                JSONArray corner3 = corners.getJSONArray(2);
                JSONArray corner4 = corners.getJSONArray(3);
                Coordinate corner1Coordinate = new Coordinate((int) corner1.getDouble(0),
                        (int) corner1.getDouble(1));
                Coordinate corner2Coordinate = new Coordinate((int) corner2.getDouble(0),
                        (int) corner2.getDouble(1));
                Coordinate corner3Coordinate = new Coordinate((int) corner3.getDouble(0),
                        (int) corner3.getDouble(1));
                Coordinate corner4Coordinate = new Coordinate((int) corner4.getDouble(0),
                        (int) corner4.getDouble(1));

                JSONArray center = jason.getJSONArray("center");
                Coordinate centerCoord = new Coordinate((int) center.getDouble(0),
                        (int) center.getDouble(1));
                City newCity = new City(centerCoord, corner1Coordinate, corner2Coordinate,
                        corner3Coordinate, corner4Coordinate, res,
                        RobotUtils.distance(centerCoord, corner1Coordinate), "city");
                newCity.setLocation(RobotUtils.convertFromPixelToNode(centerCoord));
                //obstacle
                newCity.setId(Integer.parseInt(key));
                cities.add(newCity);
            }

        }
        robot.calculateTerrainMap(cities);
        updateRobot(robot,json);
    }

    /**
     * Updates robot's location and direction
     */
    public static void updateRobot(Robot robot, String json)
    {
        try
        {
            JSONObject jsonString = new JSONObject(json);
            JSONObject robotString = jsonString.getJSONObject("robot");
            JSONArray centerCoordinates = robotString.getJSONArray("center");
            double x = centerCoordinates.getDouble(0);
            double y = centerCoordinates.getDouble(1);
            Coordinate currentCenter = new Coordinate((int)x, (int) y);
            JSONArray orientationCoordinates = robotString.getJSONArray("orientation");
            double a = orientationCoordinates.getDouble(0);
            double b = orientationCoordinates.getDouble(1);
            JSONArray corners = robotString.getJSONArray("corners");
            // Compute front of robot
            JSONArray corner1 = corners.getJSONArray(0);
            JSONArray corner2 = corners.getJSONArray(1);
            JSONArray corner3 = corners.getJSONArray(2);
            JSONArray corner4 = corners.getJSONArray(3);

            Coordinate c1 = new Coordinate(corner1.getDouble(0), corner1.getDouble(1));
            Coordinate c2 = new Coordinate(corner2.getDouble(0), corner2.getDouble(1));
            Coordinate c3 = new Coordinate(corner3.getDouble(0), corner3.getDouble(1));
            Coordinate c4 = new Coordinate(corner4.getDouble(0), corner4.getDouble(1));
            // We didn't seem to use frontOfRobot?

            robot.setOrientation(new Coordinate(a, b));
            //robot.setCurrentLocation(new Coordinate((int) x, (int) y));
            robot.setCurrentLocation(currentCenter);
            //robot.setCurrentLocation(RobotUtils.convertFromPixelToNode(robot.getCurrentLocation()));
        }
        catch(Exception e)
        {
            System.out.println("Robot not found");
        }
    }
}
