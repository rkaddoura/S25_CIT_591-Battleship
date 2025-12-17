package battleship;

/**
 * Represents a Cruiser in the game Battleship
 * Extends the Ship class and specifies the length and type of ship
 */
public class Cruiser extends Ship{

    /**
     * Variable that holds the type of ship as a string
     */
    private static final String SHIP_TYPE = "cruiser";

    /**
     * Variable to hold fixed ship length of a cruiser
     */
    private static final int SHIP_LENGTH = 3;

    /**
     * Constructor calls the superclass constructor to create a Cruiser with length 3
     */
    public Cruiser() {
        super(SHIP_LENGTH);
    }

    /**
     * This method overrides the abstract method in the ship class to return the correct ship type
     * @return the string "Cruiser"
     */
    @Override
    public String getShipType(){
        return Cruiser.SHIP_TYPE;
    }
}
