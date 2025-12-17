package battleship;

import java.util.Random;

/**
 * Ocean class maintains the Battleship game by storing the positions of the ships and facilitating shots.
 */
public class Ocean {

    /**
     * A 10 x 10 grid that stores the locations of the ships in the ocean
     */
    private Ship[][] ships = new Ship[10][10];

    /**
     * The total number of shots fired by the user
     */
    private int shotsFired;

    /**
     * The number of times a shot hit a ship. If the user shoots the same part of a ship
     * more than once, every hit is counted, even though additional “hits” don’t do the
     * user any good.
     */
    private int hitCount;

    /**
     * The number of ships sunk (10 ships in all)
     */
    private int shipsSunk;

    /**
     * Constructor creates an empty ocean and initializes game variables
     */
    public Ocean(){

        // initialize variables
        this.shotsFired = 0;
        this.hitCount = 0;
        this.shipsSunk = 0;

        // create an "empty" ocean filled with EmptySea objects
        for (int row = 0; row < 10; row++){
            for (int column = 0; column < 10; column++){
                // create new empty ship
                EmptySea emptySea = new EmptySea();
                // set the row of the empty ship
                emptySea.setBowRow(row);
                //set the column of the empty ship
                emptySea.setBowColumn(column);
                //assign the empty ship's spot in array
                this.ships[row][column] = emptySea;
            }
        }

    }

    /**
     * Method to place all ten ships randomly on the (initially empty) ocean. Place larger ships
     * before smaller ones, or you may end up with no legal place to put
     * a large ship.
     */
    void placeAllShipsRandomly(){

        // create a fleet of ships
        // contains 1 battleship, 2 cruisers, 3 destroyers, and 4 submarines
        Ship[] fleet = {new Battleship(), new Cruiser(), new Cruiser(),
                        new Destroyer(), new Destroyer(), new Destroyer(),
                        new Submarine(), new Submarine(), new Submarine(), new Submarine()};

        // create instance of random class
        Random random = new Random();

        for (Ship ship: fleet){

            // create a flag to check if the ship has been places
            boolean ship_placed = false;

            // while the ship hasn't been placed, look for a legal spot
            while (!ship_placed){

                // generate a random row, column, and orientation until a legal position is found
                int row = random.nextInt(10);
                int column = random.nextInt(10);
                boolean horizontal = random.nextBoolean();

                // if position is legal, place the ship, and change the flag
                if (ship.okToPlaceShipAt(row, column, horizontal, this)){
                    ship.placeShipAt(row, column, horizontal, this);
                    ship_placed = true;
                }
            }
        }
    }

    /**
     * Method to check if a given location contains a ship
     * @param row ship may be in
     * @param column ship may be in
     * @return true if the given location contains a ship, false if it does not
     */
    boolean isOccupied(int row, int column){
        return !this.ships[row][column].getShipType().equals("empty");
    }

    /**
     * Returns true if the given location contains a ”real” ship, still afloat, (not an
     * EmptySea), false if it does not. In addition, this method updates the number of
     * shots that have been fired, and the number of hits.
     *
     * Note: If a location contains a “real” ship, shootAt should return true every time
     * the user shoots at that same location. Once a ship has been ”sunk", additional
     * shots at its location should return false.
     *
     * @param row ship may be in
     * @param column ship may be in
     * @return true if a real ship is still afloat, false otherwise
     */
    boolean shootAt(int row, int column){

        // increment count of shots fired
        this.shotsFired += 1;

        // locate ship of interest
        Ship ship = this.ships[row][column];

        boolean shot = ship.shootAt(row, column);

        if (shot){
            // if given location contains a ship and shot was successful, increment hit count
            this.hitCount += 1;

            // check if ship sunk and increment count
            if (ship.isSunk()){
                this.shipsSunk += 1;
            }

            return true;
        }

        // if given location is empty or ship is sunk, return false
        return false;

    }

    /**
     * Method to get the number of shots fired (in the game)
     * @return the number of shots fired (in the game)
     */
    int getShotsFired(){
        return this.shotsFired;
    }

    /**
     * Method to get the number of hits recorded (in the game). All hits are counted, not just
     * the first time a given square is hit.
     * @return the number of hits recorded (in the game)
     */
    int getHitCount(){
        return this.hitCount;
    }

    /**
     * Method to get the number of ships sunk
     * @return the number of ships sunk (in the game)
     */
    int getShipsSunk(){
        return this.shipsSunk;
    }

    /**
     * Method to check if game is over
     * @return true if all ships have been sunk, otherwise false
     */
    boolean isGameOver() {
        return this.shipsSunk == 10;
    }

    /**
     * Returns the 10x10 array of Ships. The methods in the Ship class that take an
     * Ocean parameter need to be able to look at the contents of this array; the
     * placeShipAt() method even needs to modify it. While it is undesirable to
     * allow methods in one class to directly access instance variables in another class,
     * sometimes there is just no good alternative.
     *
     * @return the array of ships in the ocean
     */
    Ship[][] getShipArray(){
        return this.ships;
    }

    /**
     * Method to check if a ship was hit in a particular position for printing purposes.
     * @param row row in ocean to check
     * @param column column in ocean to check
     * @param ship ship object to check
     * @return true if ship was hit in given position, false otherwise
     */
    private boolean wasHitAt(int row, int column, Ship ship){

        // get hit array
        boolean [] shipHits = ship.getHit();

        //convert column and row to position in ship
        int shipPos;

        // if horizontal, need to map column value
        if (ship.isHorizontal()){
            shipPos = ship.getBowColumn() - column;
        }else {
            shipPos = ship.getBowRow() - row;
        }

        if (shipPos >= 0 && shipPos <= 9){
            return shipHits[shipPos];
        }

        return false;
    }


    /**
     * Prints the Ocean. To aid the user, row numbers should be displayed along the
     * left edge of the array, and column numbers should be displayed along the top.
     * Numbers should be 0 to 9, not 1 to 10.
     * -> The top left corner square should be 0, 0.
     * -> ‘x’: Use ‘x’ to indicate a location that you have fired upon and hit a (real) ship.
     * (reference the description of toString in the Ship class)
     * -> "-": Use "-" to indicate a location that you have fired upon and found nothing
     * there. (reference the description of toString in the EmptySea class)
     * -> ‘s’: Use ‘s’ to indicate a location containing a sunken ship. (reference the
     * description of toString in the Ship class)
     * -> ".": Use "." (a period) to indicate a location that you have never fired upon
     *
     * This is the only method in the Ocean class that does any input/output, and it is
     * never called from within the Ocean class, only from the BattleshipGame
     * class.
     *
     */
    void print(){

        System.out.println();

        //print column markers
        for (int col = 0; col < 10; col++){
            if (col == 0){
                System.out.print("   " + col + " ");
            }else {
                System.out.print(" " + col + " ");
            }
        }

        // print rows
        for (int row = 0; row < 10; row++){
            System.out.println("");

            // print row number
            System.out.print(row +" ");

            for (int column = 0; column < 10; column++){

                Ship ship = this.ships[row][column];

                // check and print ship status at each position
                if (wasHitAt(row, column, ship)){
                    System.out.print(" " + ship + " ");
                }else{
                    System.out.print(" . ");
                }
            }
        }
        System.out.println();
    }


    /**
     * USED FOR DEBUGGING PURPOSES ONLY.
     * - Like the print() method, this method prints the Ocean with row numbers
     * displayed along the left edge of the array, and column numbers displayed along
     * the top. Numbers should be 0 to 9, not 1 to 10. The top left corner square
     * should be 0, 0.
     *
     * - Unlike the print() method, this method shows the location of the ships.
     * This method can be used during development and debugging, to see where
     * ships are actually being placed in the Ocean. (The TAs may also use this
     * method when running your program and grading.) It can be called from the
     * BattleshipGame class, after creating the Ocean and placing ships in it.
     *
     * - Be sure to comment out any call to this method before actually playing the
     * game and before submitting your Java project.
     *
     * - Use 'b' to indicate Battleship
     * - Use 'c' to indicate Cruiser
     * - Use 'd' to indicate Destroyer
     * - Use 's' to indicate Submarine
     * - Use ' ' (single space) to indicate EmptySea
     */
    void printWithShips(){
        // print spacer
        System.out.println();

        //print column markers
        for (int col = 0; col < 10; col++){
            if (col == 0){
                System.out.print("  " + col + " ");
            }else {
                System.out.print(" " + col + " ");
            }
        }

        // print rows
        for (int row = 0; row < 10; row++){
            System.out.println("");

            // print row number
            System.out.print(row + "");

            for (int column = 0; column < 10; column++){

                Ship ship = this.ships[row][column];

                // check and print ship status at each position
                if (ship.getShipType().equals("battleship")){
                    System.out.print(" b ");
                }else if (ship.getShipType().equals("cruiser")){
                    System.out.print(" c ");
                }else if (ship.getShipType().equals("destroyer")){
                    System.out.print(" d ");
                }else if (ship.getShipType().equals("submarine")) {
                    System.out.print(" s ");
                }else{
                    System.out.print("   ");
                }
            }
        }
        System.out.println();
    }
}


