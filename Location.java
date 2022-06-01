import java.util.Objects;

/**
 * Location of a marble in a board.
 */
public class Location {
    int x;
    int y;
    boolean flag = false;

    public Location(int x, int y){
        this.x = x;
        this.y = y;

    }

    @Override
    public String toString() {
        return "(" + x +
                ", " + y +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return x == location.x && y == location.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * get manhattan distance between 2 coordinates.
     * @param other
     * @return
     */
    public int ManhattanDistance(Location other, int cost){
        //System.out.println("Calculating: |" + this.x + " - " + other.x + " | + |" + this.y +" - " + other.y + " |");
        return cost*(Math.abs(this.x - other.x) + Math.abs(this.y - other.y));
    }
}
