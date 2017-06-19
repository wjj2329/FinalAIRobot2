package testFunctions;

import Map.City;
import Map.Coordinate;
import Map.Node;
import RobotFunctions.MachineVision;
import RobotFunctions.RobotUtils;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Alex on 6/16/17.
 * Testing our standard simulated annealing approach to the TSP, in
 *  order to ascertain if it outputs a path.
 */
public class TestBaseTSP extends TestCase
{
    private ArrayList<City> cities;
    private MachineVision machineVision;

    @Before
    public void setUp() throws Exception
    {
        cities = new ArrayList<>();
        machineVision = new MachineVision();
    }

    @After
    public void tearDown() throws Exception
    {
        cities = null;
        machineVision = null;
    }

    /**
     * Testing basic TSP algorithm
     */
    public void testTSP() throws Exception
    {
        generateRandomCities();
        ArrayList<City> resultantPath = machineVision.computeTSP(cities);
        assertNotNull(resultantPath);
        System.out.println("Original Path: " + cities.toString());
        System.out.println("Resultant Path: " + resultantPath.toString());
        assertEquals(20, resultantPath.size());
        printPath(resultantPath);
    }

    public void testGreedy() throws Exception
    {
        generateRandomCities();
        ArrayList<City> greedyResult = machineVision.greedySolution(cities);
        assertEquals(20, greedyResult.size());
    }

    /**
     * Generates cities in random locations.
     */
    private void generateRandomCities()
    {
        Random random = new Random();
        for (int i = 0; i < 20; i++)
        {
            City city = new City(new Coordinate(random.nextInt(RobotUtils.gridDimensionX),
                    random.nextInt(RobotUtils.gridDimensionY)));
            while (cities.contains(city))
            {
                city = new City(new Coordinate(random.nextInt(RobotUtils.gridDimensionX),
                        random.nextInt(RobotUtils.gridDimensionY)));
            }
            cities.add(city);
        }
    }

    private void printPath(ArrayList<City> resultantPath)
    {
        for (int i = 0; i < resultantPath.size(); i++)
        {
            if (i < resultantPath.size() - 1)
            {
                System.out.print(resultantPath.get(i).toString() + ", ");
            }
            else
            {
                System.out.println(resultantPath.get(i).toString());
            }
        }
    }
}
