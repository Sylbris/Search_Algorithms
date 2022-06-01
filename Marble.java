import java.util.Objects;

/**
 * This class represents a gaming marble inside the board.
 */
public class Marble {

    char color;
    int cost;

    /**
     * Setting the color of the marble and its cost, according to the rules :
     * 1. Red and yellow marbles cost 1 to move.
     * 2. Blue has a moving cost of 2.
     * 3. Green is the most expensive , 10.
     * 4. '_' will indicate an empty space, or a null marble that will cost 0.
     * @param color
     */
    public Marble(char color){
        this.color = color;
        switch (color) {
            case 'R':
            case 'Y':
                cost = 1;
                break;
            case 'B':
                cost = 2;
                break;
            case 'G':
                cost = 10;
                break;
            case '_':
                cost = 0;
                break;
        }
    }

    /**
     * Deep copy constructor.
     * @param other
     */
    public Marble(Marble other){
        this.color = other.color;
        this.cost = other.cost;
    }

    @Override
    public String toString() {
        return Character.toString(color);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marble marble = (Marble) o;
        return color == marble.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
