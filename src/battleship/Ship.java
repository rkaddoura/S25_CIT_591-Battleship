package battleship;

import java.util.ArrayList;

/**
 * This abstract class describes the characteristics common to all ships
 */
public abstract class Ship {

    /**
     * The row that contains the bow (front part of the ship)
     */
    private int bowRow;

    /**
     * The column that contains the bow (front part of the ship)
     */
    private int bowColumn;

    /**
     * The length of the ship
     */
    private int length;

    /**
     * A boolean to represent whether the ship is going to be placed horizontally or vertically
     */
    private boolean horizontal;

    /**
     * An array of booleans that indicate whether that part of the ship has been hit or not
     */
    private boolean[] hit;

    // default constructor
    /**
     * The constructor sets the length property of the particular ship and initializes the hit array based on that length
     */
    public Ship (int length){
        this.length = length;
        this.hit = new boolean[length];
    }

    /**
     * Method to get the ship's length
     * @return the ship length
     */
    public int getLength(){
        return this.length;
    }

    /**
     * Method to get the row of the bow
     * @return the row corresponding to the position of the bow
     */
    public int getBowRow(){
        return this.bowRow;
    }

    /**
     * Method to get the column of the bow
     * @return the column corresponding to the position of the bow
     */
    public int getBowColumn(){
        return this.bowColumn;
    }

    /**
     * Method to get the array where the ship has been hit
     * @return the hit array
     */
    public boolean[] getHit(){
        return this.hit;
    }

    /**
     * Method to check is ship is horizontal or not
     * @return true if ship is horizontal, false otherwise
     */
    public boolean isHorizontal(){
        return this.horizontal;
    }

    /**
     * Method to set the row of the bow
     * @param row row of ocean grid
     */
    public void setBowRow(int row){
        this.bowRow = row;
    }

    /**
     * Method to set the column of the bow
     * @param column column of ocean grid
     */
    public void setBowColumn(int column){
        this.bowColumn = column;
    }

    /**
     * Method to set the instance variable horizontal
     * @param horizontal true if ship is horizontal, false otherwise
     */
    public void setHorizontal(boolean horizontal){
        this.horizontal = horizontal;
    }

    /**
     * Returns the type of ship as a String. Every specific type of Ship (e.g.
     * Battleship, Cruiser, etc.) has to override and implement this method and
     * return the corresponding ship type.
     */
    public abstract String getShipType();


    /**
     * This method checks is attempted placement is out of bounds and returns valid ship coordinates of an attempted ship placement.
     * If ship goes out of bounds, it will return null.
     *
     * @param row row of the starting coordinate
     * @param column column of the starting coordinate
     * @param horizontal orientation of the ship, true if horizontal, false if vertical
     * @return an arraylist of coordinates of the ship if valid, null otherwise
     */
    private ArrayList<int[]> calculateShipCoords(int row, int column, boolean horizontal) {

        // ArrayList to hold coordinates of each part of ship
        ArrayList<int[]> shipCoords = new ArrayList<>();

        // iterate over length of ship and add coordinates to array if valid
        for (int i = 0; i < this.length; i++){

            // horizontal ships are added East to West
            if (horizontal){

                // if ship goes out of bounds, return null
                if (column - i < 0){
                    return null;
                }

                // if valid, add next coordinate
                int[] currentCoords = {row, column - i};
                shipCoords.add(currentCoords);

            }else{  // vertical ships are place south to north

                // if ship goes out of bounds, return null
                if (row - i < 0){
                    return null;
                }

                // if valid, add next coordinate
                int[] currentCoords = {row - i, column};
                shipCoords.add(currentCoords);
            }
        }

        // return the ArrayList of coordinates for the ship
        return shipCoords;
    }


    /**
     * Based on the given row, column, and orientation, returns true if it is okay to put a
     * ship of this length with its bow in this location; false otherwise. The ship must not
     * overlap another ship, or touch another ship (vertically, horizontally, or diagonally),
     * and it must not ”stick out” beyond the array. Does not actually change either the
     * ship or the Ocean- it just says if it is legal to do so.
     *
     * @param row row of the starting coordinate
     * @param column column of the starting coordinate
     * @param horizontal orientation of the ship, true if horizontal, false if vertical
     * @param ocean ocean object that contains location of ships
     * @return true if the ship can be placed in the specified position, false otherwise
     */
    boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean){

        // ArrayList to hold coordinates of each part of ship
        ArrayList<int[]> shipCoords = calculateShipCoords(row, column, horizontal);

        // if null was returned or row or column is out of bounds, attempted placement is out of bounds and returns false
        if (shipCoords == null || row < 0 || row > 9 || column < 0 || column > 9){
            return false;
        }

        // iterate through ship coordinates and make sure none are illegal placements
        for (int[] coord : shipCoords) {

            // set row and columns
            int currentRow = coord[0];
            int currentColumn = coord[1];

            // create array of coordinates + adjacent coordinates to check (8-connectivity)
            int[][] adjacentCoords = {
            		{currentRow, currentColumn},
                    {currentRow,currentColumn +1},
                    {currentRow,currentColumn -1},
                    {currentRow +1,currentColumn},
                    {currentRow +1,currentColumn +1},
                    {currentRow +1,currentColumn -1},
                    {currentRow -1,currentColumn},
                    {currentRow -1,currentColumn +1},
                    {currentRow -1,currentColumn -1},};

            // iterate through adjacent coordinates
            for (int[] coords : adjacentCoords) {

                if ((0 <= coords[0] && coords[0] <= 9) && (0 <= coords[1] && coords[1] <= 9)){
                    // set row and columns
                    int adjecentRow = coords[0];
                    int adjecentColumn = coords[1];
                    // check if the position is occupied
                    if (ocean.isOccupied(adjecentRow, adjecentColumn)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * “Puts” the ship in the ocean. This involves giving values to the bowRow,
     * bowColumn, and horizontal instance variables in the ship, and it also involves
     * putting a reference to the ship in each of 1 or more locations (up to 4) in the ships
     * array in the Ocean object. (Note: This will be as many as four identical
     * references; you can’t refer to a ”part” of a ship, only to the whole ship.)
     *
     * For placement consistency (although it doesn’t really affect how you play the
     * game), let’s agree that horizontal ships face East (bow at right end) and vertical
     * ships face South (bow at bottom end).
     * - This means, if you place a horizontal battleship at location (9, 8) in the
     * ocean, the bow is at location (9, 8) and the rest of the ship occupies
     * locations: (9, 7), (9, 6), (9, 5).
     * - If you place a vertical cruiser at location (4, 0) in the ocean, the bow is at
     * location (4, 0) and the rest of the ship occupies locations: (3, 0), (2, 0).
     *
     * @param row row of the starting row (position of bow)
     * @param column column of the starting column (position of bow)
     * @param horizontal orientation of the ship, true if horizontal, false if vertical
     * @param ocean ocean object that contains location of ships
     */
    void placeShipAt (int row, int column, boolean horizontal, Ocean ocean){

        // set ships bowRow, bowColumn, and horizontal instance variables
        this.setBowRow(row);
        this.setBowColumn(column);
        this.setHorizontal(horizontal);

        // get coordinates of the full ship
        ArrayList<int[]> shipCoords = calculateShipCoords(row, column, horizontal);

        // get shipArray from ocean
        Ship[][] shipArray = ocean.getShipArray();

        // iterate through coordinates of our ship
        for (int[] coord : shipCoords) {
            // set current row and column
            int currentRow = coord[0];
            int currentColumn = coord[1];

            // put a reference to "this" ship in proper coordinates
            shipArray[currentRow][currentColumn] = this;
        }

    }

    /**
     * If a part of the ship occupies the given row and column, and the ship hasn’t been
     * sunk, mark that part of the ship as “hit” (in the hit array, index 0 indicates the
     * bow) and return true; otherwise return false.
     *
     * @param row row of the shot
     * @param column column of the shot
     * @return true if the shot hits a part of the ship that is afloat, false otherwise
     */
    boolean shootAt (int row, int column){

        // get ship coordinates
        ArrayList<int[]> shipCoords = calculateShipCoords(this.getBowRow(), this.getBowColumn(), this.isHorizontal());

        // array to hold shot coordinates
        int[] shotCoords = {row, column};

        // iterate for length of ship coordinates
        for (int i = 0; i < shipCoords.size(); i++) {

            // store coordinates at current index
            int[] currentCoords = shipCoords.get(i);

            // check if the shot coordinates match the ship coordinates at the current index and the ship is not sunk
            if (shotCoords[0] == currentCoords[0] && shotCoords[1] == currentCoords[1] && !this.isSunk()) {
                // set proper index in hit to true and return true
                this.hit[i] = true;
                return true;
            }
        }

        // if not a hit or ship is sunk return false
        return false;
    }

    /**
     * This method check if the ship has sunk
     * @return true if every part of the ship has been hit, false otherwise
     */
    boolean isSunk(){

        // flag starts off as true to indicate that ship is sunk
        boolean sunk = true;
        // if any location in hit Array is false set sunk flag to false and escape loop
        for (boolean location : hit) {
            if (!location) {
                sunk = false;
                break;
            }
        }

        // if the sunk flag is still true it means every part of ship was hit so return true
        // otherwise return false as the ship is not sunk
        return sunk;
    }

    /**
     * Returns a single-character String to use in the Ocean’s print method. This method
     * should return ”s” if the ship has been sunk and ”x” if it has not been sunk. This
     * method can be used to print out locations in the ocean that have been shot at; it
     * should not be used to print locations that have not been shot at. Since toString
     * behaves exactly the same for all ship types, it is placed here in the Ship class.
     *
     * @return a single character String representing if ship is sunk or not
     */
    @Override
    public String toString(){

        // if the ship is sunk return "s", otherwise "x"
        if (this.isSunk()) {
            return "s";
        } else {
            return "x";
        }
    }

}
