package battleship;

/**
 * Represents a Submarine in the game Battleship
 * Extends the Ship class and specifies the length and type of ship
 */
public class Submarine extends Ship{

    /**
     * Variable that holds the type of ship as a string
     */
    private static final String SHIP_TYPE = "submarine";

    /**
     * Variable to hold fixed ship length of a submarine
     */
    private static final int SHIP_LENGTH = 1;

    /**
     * Constructor calls the superclass constructor to create a Submarine with length 1
     */
    public Submarine() {
        super(SHIP_LENGTH);
    }

    /**
     * This method overrides the abstract method in the ship class to return the correct ship type
     * @return the string "Submarine"
     */
    @Override
    public String getShipType(){
        return Submarine.SHIP_TYPE;
    }
}

