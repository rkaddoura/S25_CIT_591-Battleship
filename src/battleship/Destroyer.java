package battleship;

/**
 * Represents a Destroyer in the game Battleship
 * Extends the Ship class and specifies the length and type of ship
 */
public class Destroyer extends Ship{

    /**
     * Variable that holds the type of ship as a string
     */
    private static final String SHIP_TYPE = "destroyer";

    /**
     * Variable to hold fixed ship length of a destroyer
     */
    private static final int SHIP_LENGTH = 2;

    /**
     * Constructor calls the superclass constructor to create a Destroyer with length 2
     */
    public Destroyer() {
        super(SHIP_LENGTH);
    }

    /**
     * This method overrides the abstract method in the ship class to return the correct ship type
     * @return the string "Destroyer"
     */
    @Override
    public String getShipType(){
        return Destroyer.SHIP_TYPE;
    }
}

