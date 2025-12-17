package battleship;

import java.util.ArrayList;

/**
 * Represents an empty coordinate in the game Battleship
 * Extends the Ship class and adjusts functions for an empty space
 */
public class EmptySea extends Ship{

    /**
     * Constructor calls the superclass constructor to create an empty ship of length 1
     */
    public EmptySea(){
        super(1);
    }

    /**
     * This method overrides shootAt(int row, int column) that is inherited
     * from Ship, and always returns false to indicate that nothing was hit.
     * Update hit array to indicate that a hit was attempted.
     * @param row row of ocean grid
     * @param column of ship location
     * @return false to indicate nothing was hit
     */
    @Override
    boolean shootAt(int row, int column){
        // update hit array to indicate a hit was attempted
        this.getHit()[0] = true;
        return false;
    }

    /**
     * This method overrides isSunk() that is inherited from Ship, and always
     * returns false to indicate that you didn’t sink anything
     * @return false to indicate that nothing was sunk
     */
    @Override
    boolean isSunk(){
        return false;
    }

    /**
     * Returns the single-character "-" String to use in the Ocean’s print method.
     * (Note, this is the character to be displayed if a shot has been fired, but nothing
     * has been hit.)
     */
    @Override
    public String toString(){
        return "-";
    }

    /**
     * This method overrides the abstract method in the ship class to return the correct ship type
     * @return the string "empty"
     */
    @Override
    public String getShipType(){
        return "empty";
    }

}

