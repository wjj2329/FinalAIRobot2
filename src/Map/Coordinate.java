package Map;

/**
 * Created by williamjones on 5/15/17.
 * Represents a coordinate position on the terrain map.
 */
public class Coordinate
{
    private double x;
    private double y;

    /**
     * Public constructor
     * @param x
     * @param y
     */
    public Coordinate(double x, double y)
    {
        this.x=x;
        this.y=y;
    }

    @Override
    public String toString()
    {
        return "Coordinate {" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    //<editor-fold desc="Getters/Setters">
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    //</editor-fold>

    //<editor-fold desc="Equals/HashCode">
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (Double.compare(that.x, x) != 0) return false;
        return Double.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
    //</editor-fold>
}
