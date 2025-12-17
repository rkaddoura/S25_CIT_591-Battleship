package battleship;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OceanTest {

    Ocean ocean;

    static int NUM_BATTLESHIPS = 1;
    static int NUM_CRUISERS = 2;
    static int NUM_DESTROYERS = 3;
    static int NUM_SUBMARINES = 4;
    static int OCEAN_SIZE = 10;

    @BeforeEach
    void setUp() throws Exception {
        ocean = new Ocean();
    }

    @Test
    void testEmptyOcean() {

        //tests that all locations in the ocean are "empty"

        Ship[][] ships = ocean.getShipArray();

        for (int i = 0; i < ships.length; i++) {
            for (int j = 0; j < ships[i].length; j++) {
                Ship ship = ships[i][j];

                assertEquals("empty", ship.getShipType());
            }
        }

        assertEquals(0, ships[0][0].getBowRow());
        assertEquals(0, ships[0][0].getBowColumn());

        assertEquals(5, ships[5][5].getBowRow());
        assertEquals(5, ships[5][5].getBowColumn());

        assertEquals(9, ships[9][0].getBowRow());
        assertEquals(0, ships[9][0].getBowColumn());
    }

    @Test
    void testPlaceAllShipsRandomly() {

        //tests that the correct number of each ship type is placed in the ocean

        ocean.placeAllShipsRandomly();

        Ship[][] ships = ocean.getShipArray();
        ArrayList<Ship> shipsFound = new ArrayList<Ship>();

        int numBattlehips = 0;
        int numCruisers = 0;
        int numDestroyers = 0;
        int numSubmarines = 0;
        int numEmptySeas = 0;

        for (int i = 0; i < ships.length; i++) {
            for (int j = 0; j < ships[i].length; j++) {
                Ship ship = ships[i][j];
                if (!shipsFound.contains(ship)) {
                    shipsFound.add(ship);
                }
            }
        }

        for (Ship ship : shipsFound) {
            if ("battleship".equals(ship.getShipType())) {
                numBattlehips++;
            } else if ("cruiser".equals(ship.getShipType())) {
                numCruisers++;
            } else if ("destroyer".equals(ship.getShipType())) {
                numDestroyers++;
            } else if ("submarine".equals(ship.getShipType())) {
                numSubmarines++;
            } else if ("empty".equals(ship.getShipType())) {
                numEmptySeas++;
            }
        }

        assertEquals(NUM_BATTLESHIPS, numBattlehips);
        assertEquals(NUM_CRUISERS, numCruisers);
        assertEquals(NUM_DESTROYERS, numDestroyers);
        assertEquals(NUM_SUBMARINES, numSubmarines);

        //calculate total number of available spaces and occupied spaces
        int totalSpaces = OCEAN_SIZE * OCEAN_SIZE;
        int occupiedSpaces = (NUM_BATTLESHIPS * 4)
                + (NUM_CRUISERS * 3)
                + (NUM_DESTROYERS * 2)
                + (NUM_SUBMARINES * 1);

        //test number of empty seas, each with length of 1
        assertEquals(totalSpaces - occupiedSpaces, numEmptySeas);
    }

    @Test
    void testIsOccupied() {

        // before adding anything, all spots in the ocean should be empty
        for (int row = 0; row < 10; row ++){
            for (int column = 0; column < 10; column++){
                assertFalse(ocean.isOccupied(row, column));
            }
        }

        // add a ship with valid dimensions
        Destroyer destroyer = new Destroyer();
        int row = 1;
        int column = 5;
        boolean horizontal = false;
        destroyer.placeShipAt(row, column, horizontal, ocean);
        // check that entire ship was placed correctly
        assertTrue(ocean.isOccupied(1, 5));
        assertTrue(ocean.isOccupied(0, 5));

        // add a submarine with valid dimensions to a corner
        Ship submarine = new Submarine();
        row = 0;
        column = 0;
        horizontal = false;
        submarine.placeShipAt(row, column, horizontal, ocean);
        assertTrue(ocean.isOccupied(0,0));

        // attempting to add a ship illegally, area should not be occupied
        Ship battleship = new Battleship();
        row = 2;
        column = 0;
        horizontal = true;
        if (battleship.okToPlaceShipAt(row, column, horizontal, ocean)){
            battleship.placeShipAt(row, column, horizontal, ocean);
        }
        assertFalse(ocean.isOccupied(2, 0));

    }

    @Test
    void testShootAt() {

        // test shooting at empty ocean
        assertFalse(ocean.shootAt(0, 1));
        assertEquals(1, ocean.getShotsFired());
        assertEquals(0, ocean.getHitCount());

        // add a ship and test if shooting is accurate
        Destroyer destroyer = new Destroyer();
        int row = 1;
        int column = 5;
        boolean horizontal = false;
        destroyer.placeShipAt(row, column, horizontal, ocean);

        assertTrue(ocean.shootAt(1, 5));
        assertFalse(destroyer.isSunk());

        // try reshooting the same position (while ship is not sunk)
        assertTrue(ocean.shootAt(1, 5));
        assertEquals(3, ocean.getShotsFired());
        assertEquals(2, ocean.getHitCount());

        // sink the ship
        assertTrue(ocean.shootAt(0, 5));
        assertTrue(destroyer.isSunk());
        assertEquals(4, ocean.getShotsFired());
        assertEquals(3, ocean.getHitCount());

        // try shooting a sunk ship (shots fired should increment, hit count should not)
        assertFalse(ocean.shootAt(0, 5));
        assertEquals(5, ocean.getShotsFired());
        assertEquals(3, ocean.getHitCount());

    }

    @Test
    void testGetShotsFired() {

        // no shots fired on an empty ocean
        assertEquals(0, ocean.getShotsFired());

        //should be all false - no ships added yet
        assertFalse(ocean.shootAt(0, 1));
        assertFalse(ocean.shootAt(1, 0));
        assertFalse(ocean.shootAt(3, 3));
        assertFalse(ocean.shootAt(9, 9));
        assertEquals(4, ocean.getShotsFired());

        // create destroyer
        Destroyer destroyer = new Destroyer();
        int row = 1;
        int column = 5;
        boolean horizontal = false;
        destroyer.placeShipAt(row, column, horizontal, ocean);

        // create submarine
        Ship submarine = new Submarine();
        row = 0;
        column = 0;
        horizontal = false;
        submarine.placeShipAt(row, column, horizontal, ocean);

        // shoot and sink a destroyer, shots should increment
        assertTrue(ocean.shootAt(1, 5));
        assertFalse(destroyer.isSunk());
        assertTrue(ocean.shootAt(0, 5));
        assertTrue(destroyer.isSunk());
        assertEquals(6, ocean.getShotsFired());

        // shoot at a sunk ship, shots should increment
        assertFalse(ocean.shootAt(1,5));
        assertEquals(7, ocean.getShotsFired());

        // shoot at the same spot repeatedly, even after sinking
        assertTrue(ocean.shootAt(0,0));
        assertEquals(8, ocean.getShotsFired());
        assertTrue(submarine.isSunk());
        assertFalse(ocean.shootAt(0,0));
        assertFalse(ocean.shootAt(0,0));
        assertFalse(ocean.shootAt(0,0));
        assertEquals(11, ocean.getShotsFired());

    }

    @Test
    void testGetHitCount() {

        // no hits to start with
        assertEquals(0, ocean.getHitCount());

        // hits do not increment on an empty ocean
        assertFalse(ocean.shootAt(0, 1));
        assertFalse(ocean.shootAt(1, 0));
        assertFalse(ocean.shootAt(3, 3));
        assertFalse(ocean.shootAt(9, 9));
        assertEquals(0, ocean.getHitCount());

        Destroyer destroyer = new Destroyer();
        int row = 1;
        int column = 5;
        boolean horizontal = false;
        destroyer.placeShipAt(row, column, horizontal, ocean);

        // shoot and hit the destroyer (don't sink)
        assertTrue(ocean.shootAt(1, 5));
        assertFalse(destroyer.isSunk());
        assertEquals(1, ocean.getHitCount());

        // hit same spot before ship is sunk
        assertTrue(ocean.shootAt(1, 5));
        assertTrue(ocean.shootAt(1, 5));
        assertTrue(ocean.shootAt(1, 5));
        assertFalse(destroyer.isSunk());
        assertEquals(4, ocean.getHitCount());

        // sink ship, hits increment
        assertTrue(ocean.shootAt(0, 5));
        assertTrue(destroyer.isSunk());
        assertEquals(5, ocean.getHitCount());

        // additional shots should not increment hit count
        assertFalse(ocean.shootAt(1, 5));
        assertFalse(ocean.shootAt(0, 5));
        assertEquals(5, ocean.getHitCount());


    }

    @Test
    void testGetShipsSunk() {

        // no ships sunk on empty sea
        assertEquals(0, ocean.getShipsSunk());

        // no ships sunk after shooting at empty sea
        assertFalse(ocean.shootAt(0, 1));
        assertFalse(ocean.shootAt(1, 0));
        assertFalse(ocean.shootAt(3, 3));
        assertFalse(ocean.shootAt(9, 9));
        assertEquals(0, ocean.getShipsSunk());

        // add a destroyer
        Destroyer destroyer = new Destroyer();
        int row = 1;
        int column = 5;
        boolean horizontal = false;
        destroyer.placeShipAt(row, column, horizontal, ocean);

        // count doesn't increment if not sunk
        assertTrue(ocean.shootAt(1, 5));
        assertFalse(destroyer.isSunk());
        assertEquals(1, ocean.getHitCount());
        assertEquals(0, ocean.getShipsSunk());

        // sink destroyer
        assertTrue(ocean.shootAt(0, 5));
        assertTrue(destroyer.isSunk());
        assertEquals(2, ocean.getHitCount());
        assertEquals(1, ocean.getShipsSunk());

        // add a submarine
        Ship submarine = new Submarine();
        row = 0;
        column = 0;
        horizontal = false;
        submarine.placeShipAt(row, column, horizontal, ocean);
        assertTrue(ocean.isOccupied(0,0));

        // sink the submarine
        assertFalse(submarine.isSunk());
        assertTrue(ocean.shootAt(0,0));
        assertTrue(submarine.isSunk());
        assertEquals(3, ocean.getHitCount());
        assertEquals(2, ocean.getShipsSunk());

        // hit a sunk ship multiple times shouldn't increment count
        assertTrue(submarine.isSunk());
        assertFalse(ocean.shootAt(0,0));
        assertFalse(ocean.shootAt(0,0));
        assertEquals(3, ocean.getHitCount());
        assertEquals(2, ocean.getShipsSunk());

    }

    @Test
    void testGetShipArray() {

        // check dimesnions
        Ship[][] shipArray = ocean.getShipArray();
        assertEquals(OCEAN_SIZE, shipArray.length);
        assertEquals(OCEAN_SIZE, shipArray[0].length);

        assertEquals("empty", shipArray[0][0].getShipType());

        // check that entire array is empty
        for (int r = 0; r < 10; r++){
            for (int c = 0; c < 10; c++){
                assertEquals("empty", shipArray[r][c].getShipType());
            }
        }

        // create a destroyer
        Destroyer destroyer = new Destroyer();
        int row = 1;
        int column = 5;
        boolean horizontal = false;
        destroyer.placeShipAt(row, column, horizontal, ocean);

        // check that location returns properly
        assertEquals("destroyer", shipArray[1][5].getShipType());
        assertEquals("destroyer", shipArray[0][5].getShipType());

        // create a submarine
        Ship submarine = new Submarine();
        row = 0;
        column = 0;
        horizontal = false;
        submarine.placeShipAt(row, column, horizontal, ocean);
        assertEquals("submarine", shipArray[0][0].getShipType());


        // create new ocean and place randomly
        Ocean testOcean = new Ocean();
        testOcean.placeAllShipsRandomly();

        Ship[][] ships = testOcean.getShipArray();

        int battleshipSquareCount = 0;
        int cruiserSquareCount = 0;
        int destroyerSquareCount = 0;
        int submarineSquareCount = 0;
        int emptySeaSquareCount = 0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                String shipType = ships[i][j].getShipType();
                if ("battleship".equals(shipType)) {
                    battleshipSquareCount++;
                } else if ("cruiser".equals(shipType)) {
                    cruiserSquareCount++;
                } else if ("destroyer".equals(shipType)) {
                    destroyerSquareCount++;
                } else if ("submarine".equals(shipType)) {
                    submarineSquareCount++;
                } else if ("empty".equals(shipType)) {
                    emptySeaSquareCount++;
                }
            }
        }

        // adjust count to number of ships
        int numBattlehips = battleshipSquareCount / 4;
        int numCruisers = cruiserSquareCount / 3;
        int numDestroyers = destroyerSquareCount / 2;
        int numSubmarines = submarineSquareCount / 1;
        int numEmptySeas = emptySeaSquareCount / 1;

        assertEquals(1, numBattlehips);
        assertEquals(2, numCruisers);
        assertEquals(3, numDestroyers);
        assertEquals(4, numSubmarines);
        assertEquals(80, numEmptySeas);
    }

    @Test
    void testIsGameOver(){

        // should be false at start
        assertEquals(0, ocean.getShipsSunk());
        assertFalse(ocean.isGameOver());

        // add 10 ships
        ocean.placeAllShipsRandomly();

        // sink some of the ships
        for (int r = 0; r < 5; r++){
            for (int c = 0; c < 10; c++){
                ocean.shootAt(r, c);

            }
        }

        assertFalse(ocean.isGameOver());

        // sink remaining ships
        for (int r = 5; r < 10; r++){
            for (int c = 0; c < 10; c++){
                ocean.shootAt(r, c);

            }
        }

        assertTrue(ocean.isGameOver());

    }

}
