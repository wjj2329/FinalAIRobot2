package Map;

/**
 * Created by Alex on 6/14/17.
 * Represents a city that we are visiting.
 * Determined by an ArUco card.
 */
public class City
{
    /**
     * Location on the grid!
     */
    private Coordinate location;

    /**
     * Number, taken from ArUco cards.
     */
    private int id;

    /**
     * Whether it is a city or an obstacle. May not
     *  be necessary if we decide we don't want obstacles.
     */
    private String type;

    /**
     * Remember, we do indeed have a center, but
     *  the obstacle is actually a square!
     */
    private Coordinate center;

    /**
     * These represent the four corners of the obstacle.
     */
    private Coordinate corner1, corner2, corner3, corner4;

    /**
     * Not 100% sure if this should be a coordinate
     */
    private Coordinate orientation;

    /**
     * The radius of the city/ArUco cards
     */
    private double radius;

    /**
     * default public constructor
     *  with position parameters, since the cities won't move.
     */
    public City(Coordinate center, Coordinate corner1, Coordinate corner2, Coordinate corner3,
                Coordinate corner4, Coordinate orientation, double radius, String type)
    {
        this.center = center;
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.corner3 = corner3;
        this.corner4 = corner4;
        this.orientation = orientation;
        this.radius = radius;
        this.type = type;
    }

    public City(Coordinate gridLocation)
    {
        // Might need to change this
        this.location = gridLocation;
    }

    //<editor-fold desc="Getters and Setters!">
    public Coordinate getLocation()
    {
        return location;
    }

    public void setLocation(Coordinate location)
    {
        this.location = location;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "City{" +
                "location=" + location +
                ", id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

    /**
     * We use this equals for the .contains() method with an
     *  object of type City
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (id != city.id) return false;
        return location != null ? location.equals(city.location) : city.location == null;
    }

    @Override
    public int hashCode()
    {
        int result = location != null ? location.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }

//</editor-fold>
}