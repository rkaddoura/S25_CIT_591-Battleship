package battleship;

/**
 * Represents a Battleship in the game Battleship
 * Extends the Ship class and specifies the length and type of ship
 */
public class Battleship extends Ship{

    /**
     * Variable that holds the type of ship as a string
     */
    private static final String SHIP_TYPE = "battleship";

    /**
     * Variable to hold fixed ship length of a battleship
     */
    private static final int SHIP_LENGTH = 4;

    /**
     * Constructor calls the superclass constructor to create a Battleship with length 4
     */
    public Battleship() {
        super(SHIP_LENGTH);
    }

    /**
     * This method overrides the abstract method in the ship class to return the correct ship type
     * @return the string "Battleship"
     */
    @Override
    public String getShipType(){
        return Battleship.SHIP_TYPE;
    }
}
