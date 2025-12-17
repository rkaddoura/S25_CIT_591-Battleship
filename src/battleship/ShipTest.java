package battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {

    Ocean ocean;
    Ship ship;

    @BeforeEach
    void setUp() throws Exception {
        ocean = new Ocean();
    }

    @Test
    void testGetLength() {
    	
    	// test battleship length
        ship = new Battleship();
        assertEquals(4, ship.getLength());
        
        // test cruiser length
        ship = new Cruiser();
        assertEquals(3, ship.getLength());
        
        // test destroyer length
        ship = new Destroyer();
        assertEquals(2, ship.getLength());
        
        // test submarine length
        ship = new Submarine();
        assertEquals(1, ship.getLength());
    }

    @Test
    void testGetBowRow() {
    	
    	// test horizontal placement
        Ship battleship = new Battleship();
        int row = 0;
        int column = 4;
        boolean horizontal = true;
        battleship.placeShipAt(row, column, horizontal, ocean);
        assertEquals(row, battleship.getBowRow());

        // test vertical placement
        Ship cruiser = new Cruiser();
        row = 5;
        column = 6;
        horizontal = false;
        cruiser.placeShipAt(row, column, horizontal, ocean);
        assertEquals(row, cruiser.getBowRow());
        
        // test submarine placement both vertical and horizontal (should not matter)
        Ship submarineVertical = new Submarine();
        Ship submarineHorizontal = new Submarine();
        row = 0;
        column = 0;
        submarineVertical.placeShipAt(row, column, false, ocean);
        submarineHorizontal.placeShipAt(row, column, true, ocean);
        assertEquals(row, submarineVertical.getBowRow()); // testing vertical placement
        assertEquals(row, submarineHorizontal.getBowRow()); // testing horizontal placement
        assertEquals(submarineVertical.getBowRow(), submarineHorizontal.getBowRow()); // test both
    }

    @Test
    void testGetBowColumn() {
    	
    	// test horizontal placement
        Ship battleship = new Battleship();
        int row = 0;
        int column = 4;
        boolean horizontal = true;
        battleship.placeShipAt(row, column, horizontal, ocean);
        battleship.setBowColumn(column);
        assertEquals(column, battleship.getBowColumn());

        // test vertical placement
        Ship destroyer = new Destroyer();
        row = 5;
        column = 5;
        horizontal = false;
        destroyer.placeShipAt(row, column, horizontal, ocean);
        assertEquals(column, destroyer.getBowColumn());
        
        // test two ships in the same column
        Ship battleshipVertical = new Battleship();
        row = 9;
        column = 9;
        horizontal = false;
        battleshipVertical.placeShipAt(row, column, horizontal, ocean);
        Ship battleshipHorizontal = new Battleship();
        row = 0;
        column = 9;
        horizontal = true;
        battleshipHorizontal.placeShipAt(row, column, horizontal, ocean);
        assertEquals(battleshipVertical.getBowColumn(), battleshipHorizontal.getBowColumn());
        assertEquals(9, battleshipVertical.getBowColumn());
        assertEquals(9, battleshipHorizontal.getBowColumn());
    }

    @Test
    void testGetHit() {
    	// test ship with no hits
        ship = new Battleship();
        boolean[] hits = new boolean[4];
        assertArrayEquals(hits, ship.getHit());
        assertFalse(ship.getHit()[0]);
        assertFalse(ship.getHit()[1]);
        
        // test sunk ship
        ship = new Cruiser();
        ship.placeShipAt(0, 2, true, ocean);
        ship.shootAt(0, 2);
        ship.shootAt(0, 1);
        ship.shootAt(0, 0);
        boolean[] hitsSunk = {true, true, true};
        assertArrayEquals(hitsSunk, ship.getHit());
        
        // test before and after shooting at
        ship = new Submarine();
        ship.placeShipAt(9, 9, false, ocean);
        assertFalse(ship.getHit()[0]);
        ship.shootAt(9, 9);
        assertTrue(ship.getHit()[0]);
    }
    
    @Test
    void testGetShipType() {
    	
    	// test battleship type
        ship = new Battleship();
        assertEquals("battleship", ship.getShipType());
        
        // test cruiser type
        ship = new Cruiser();
        assertEquals("cruiser", ship.getShipType());
        
        // test destroyer type
        ship = new Destroyer();
        assertEquals("destroyer", ship.getShipType());
        
        // test submarine type
        ship = new Submarine();
        assertEquals("submarine", ship.getShipType());
    }

    @Test
    void testIsHorizontal() {
    	
    	// test a horizontal ship
        Ship battleship = new Battleship();
        int row = 0;
        int column = 4;
        boolean horizontal = true;
        battleship.placeShipAt(row, column, horizontal, ocean);
        assertTrue(battleship.isHorizontal());

        // test a vertical ship
        Ship cruiser = new Cruiser();
        row = 9;
        column = 9;
        horizontal = false;
        cruiser.placeShipAt(row, column, horizontal, ocean);
        assertFalse(cruiser.isHorizontal());
        
        // test two different ships
        Ship battleshipHorizontalOne = new Battleship();
        Ship battleshipHorizontalTwo = new Battleship();
        Ship battleshipVertical = new Battleship();
        battleshipHorizontalOne.placeShipAt(9, 9, true, ocean);
        battleshipHorizontalTwo.placeShipAt(8, 9, true, ocean);
        battleshipVertical.placeShipAt(7, 3, false, ocean);
        assertEquals(battleshipHorizontalOne.isHorizontal(), battleshipHorizontalTwo.isHorizontal());
        assertNotEquals(battleshipHorizontalOne.isHorizontal(), battleshipVertical.isHorizontal());
    }

    @Test
    void testSetBowRow() {
    	
    	// test set bow row
        Ship battleship = new Battleship();
        int row = 0;
        int column = 4;
        boolean horizontal = true;
        battleship.setBowRow(row);
        assertEquals(row, battleship.getBowRow());

        // test setting bow row on an already placed ship
        Ship cruiser = new Cruiser();
        row = 9;
        column = 9;
        horizontal = true;
        cruiser.placeShipAt(row, column, horizontal, ocean);
        assertEquals(row, cruiser.getBowRow());
        cruiser.setBowRow(0);
        assertEquals(0, cruiser.getBowRow());
        
        // test set bow row for a submarine
        Ship submarine = new Submarine();
        row = 5;
        column = 5;
        horizontal = true;
        submarine.setBowRow(row);
        assertEquals(row, submarine.getBowRow());
        assertTrue(submarine.getBowRow() == 5);
    }

    @Test
    void testSetBowColumn() {
        Ship battleship = new Battleship();
        int row = 0;
        int column = 4;
        boolean horizontal = true;
        battleship.setBowColumn(column);
        assertEquals(column, battleship.getBowColumn());

        // test setting bow column on an already placed ship
        Ship cruiser = new Cruiser();
        row = 9;
        column = 9;
        horizontal = true;
        cruiser.placeShipAt(row, column, horizontal, ocean);
        assertEquals(row, cruiser.getBowColumn());
        cruiser.setBowColumn(0);
        assertEquals(0, cruiser.getBowColumn());
        
        // test set bow row for a submarine
        Ship submarine = new Submarine();
        row = 5;
        column = 5;
        horizontal = true;
        submarine.setBowColumn(row);
        assertEquals(row, submarine.getBowColumn());
        assertTrue(submarine.getBowColumn() == 5);
    }

    @Test
    void testSetHorizontal() {
    	
    	// test setting horizontal
        Ship battleship = new Battleship();
        int row = 0;
        int column = 4;
        boolean horizontal = true;
        battleship.setHorizontal(horizontal);
        assertTrue(battleship.isHorizontal());

        // test setting vertical
        Ship cruiser = new Cruiser();
        cruiser.setHorizontal(false);
        assertFalse(cruiser.isHorizontal());
        
        // test setting horizontal on an already placed ship
        Ship destroyer = new Destroyer();
        row = 9;
        column = 9;
        horizontal = true;
        destroyer.placeShipAt(row, column, horizontal, ocean);
        assertTrue(destroyer.isHorizontal());
        destroyer.setHorizontal(false);
        assertFalse(destroyer.isHorizontal());
        
    }

    @Test
    void testOkToPlaceShipAt() {

        //test when other ships are not in the ocean
        Ship battleship = new Battleship();
        int row = 0;
        int column = 4;
        boolean horizontal = true;
        boolean ok = battleship.okToPlaceShipAt(row, column, horizontal, ocean);
        assertTrue(ok, "OK to place ship here.");

        // test trying to put a ship with bow out of bounds doesn't work
        Ship destroyer = new Destroyer();
        row = -100;
        column = -100;
        horizontal = true;
        assertFalse(destroyer.okToPlaceShipAt(row, column, horizontal, ocean));
        Ship cruiser= new Cruiser();
        row = 100;
        column = 100;
        horizontal = true;
        assertFalse(cruiser.okToPlaceShipAt(row, column, horizontal, ocean));
        
        // test putting ship with bow in bounds but rest goes out of bounds doesn't work
        Ship battleshipOOB = new Battleship();
        assertFalse(battleshipOOB.okToPlaceShipAt(0,0, true, ocean));
        assertFalse(battleshipOOB.okToPlaceShipAt(0,0, false, ocean));
    }

    @Test
    void testOkToPlaceShipAtAgainstOtherShipsOneBattleship() {

        //test when other ships are in the ocean

        //place first ship
        Battleship battleship1 = new Battleship();
        int row = 0;
        int column = 4;
        boolean horizontal = true;
        boolean ok1 = battleship1.okToPlaceShipAt(row, column, horizontal, ocean);
        assertTrue(ok1, "OK to place ship here.");
        battleship1.placeShipAt(row, column, horizontal, ocean);

        //test second ship
        Battleship battleship2 = new Battleship();
        row = 1;
        column = 4;
        horizontal = true;
        boolean ok2 = battleship2.okToPlaceShipAt(row, column, horizontal, ocean);
        assertFalse(ok2, "Not OK to place ship vertically adjacent below.");
        
        // test putting ship away from other ships works properly
        Submarine sub = new Submarine();
        assertTrue(sub.okToPlaceShipAt(9, 9, true, ocean));
        
        // test putting ship next to other ships doesn't work
        // put sub at 9,9
        sub.placeShipAt(9, 9, true, ocean); 
        Submarine sub2 = new Submarine();
        // test above adjacent
        assertFalse(sub2.okToPlaceShipAt(8, 9, true, ocean));
        // test side adjacent
        assertFalse(sub2.okToPlaceShipAt(9, 8, true, ocean));
        // test diagonal adjacent
        assertFalse(sub2.okToPlaceShipAt(8, 8, true, ocean));
        
        // test putting ship on top of other ship doesn't work
        assertFalse(sub2.okToPlaceShipAt(9, 9, true, ocean));
        
    }

    @Test
    void testPlaceShipAt() {
    	
    	// test placing horizontal
        Ship battleship = new Battleship();
        int row = 0;
        int column = 4;
        boolean horizontal = true;
        battleship.placeShipAt(row, column, horizontal, ocean);
        assertEquals(row, battleship.getBowRow());
        assertEquals(column, battleship.getBowColumn());
        assertTrue(battleship.isHorizontal());

        assertEquals("empty", ocean.getShipArray()[0][0].getShipType());
        assertEquals(battleship, ocean.getShipArray()[0][1]);


        // test placing vertical
        Ship battleshipVertical = new Battleship();
        row = 3;
        column = 9;
        horizontal = false;
        battleshipVertical.placeShipAt(row, column, horizontal, ocean);
        assertEquals(row, battleshipVertical.getBowRow());
        assertEquals(column, battleshipVertical.getBowColumn());
        assertEquals("battleship", ocean.getShipArray()[3][9].getShipType());
        assertEquals("battleship", ocean.getShipArray()[2][9].getShipType());
        assertEquals("battleship", ocean.getShipArray()[1][9].getShipType());
        assertEquals("battleship", ocean.getShipArray()[0][9].getShipType());
        
        // test before and after placing ship
        assertEquals("empty", ocean.getShipArray()[9][9].getShipType());
        Ship sub = new Submarine();
        sub.placeShipAt(9, 9, false, ocean);
        assertEquals("submarine", ocean.getShipArray()[9][9].getShipType());
        
    }

    @Test
    void testShootAt() {
    	
    	// test missing
        Ship battleship = new Battleship();
        int row = 0;
        int column = 9;
        boolean horizontal = true;
        battleship.placeShipAt(row, column, horizontal, ocean);

        assertFalse(battleship.shootAt(1, 9));
        boolean[] hitArray0 = {false, false, false, false};
        assertArrayEquals(hitArray0, battleship.getHit());

        // test hitting
        assertTrue(battleship.shootAt(0, 9));
        boolean[] hitArray1 = {true, false, false, false};
        assertArrayEquals(hitArray1, battleship.getHit());
        battleship.shootAt(0,8);
        battleship.shootAt(0,7);
        battleship.shootAt(0,6);
        boolean[] sunkArray = {true, true, true, true};
        assertArrayEquals(sunkArray, battleship.getHit());
        
        // test shooting at a sunk ship is considered a miss
        Ship sub = new Submarine();
        sub.placeShipAt(0, 0, true, ocean);
        // first shot is a hit
        assertTrue(sub.shootAt(0, 0));
        // second shot is a miss because ship is sunk
        assertFalse(sub.shootAt(0, 0));
    }

    @Test
    void testIsSunk() {
    	
    	// test not sunk
        Ship submarine = new Submarine();
        int row = 3;
        int column = 3;
        boolean horizontal = true;
        submarine.placeShipAt(row, column, horizontal, ocean);

        assertFalse(submarine.isSunk());
        assertFalse(submarine.shootAt(5, 2));
        assertFalse(submarine.isSunk());
        
        // test sunk
        assertFalse(submarine.isSunk());
        submarine.shootAt(3, 3);
        assertTrue(submarine.isSunk());
        
        // test not sunk even though hit twice
        Ship destroyer = new Destroyer();
        destroyer.placeShipAt(5, 5, horizontal, ocean);
        destroyer.shootAt(5, 5);
        destroyer.shootAt(5, 5);
        assertFalse(destroyer.isSunk());

        // test sinking a battleship
        Ship battleship = new Battleship();
        battleship.placeShipAt(9, 9, false, ocean);
        assertFalse(battleship.isSunk());
        battleship.shootAt(9, 9);
        battleship.shootAt(8, 9);
        battleship.shootAt(7, 9);
        // 3 hits still not sunk
        assertFalse(battleship.isSunk());
        battleship.shootAt(6, 9);
        // 4th shot sunk it
        assertTrue(battleship.isSunk());
    }

    @Test
    void testToString() {
    	
    	// test unsunk ship
        Ship battleship = new Battleship();
        assertEquals("x", battleship.toString());

        int row = 9;
        int column = 1;
        boolean horizontal = false;
        battleship.placeShipAt(row, column, horizontal, ocean);
        battleship.shootAt(9, 1);
        assertEquals("x", battleship.toString());

        // test sunk ship
        Ship submarine = new Submarine();
        submarine.placeShipAt(9, 9, true, ocean);
        assertEquals("x", submarine.toString());
        submarine.shootAt(9, 9);
        assertEquals("s", submarine.toString());
        
        // test empty sea before and after shot
        assertEquals(".", ocean.getShipArray()[5][5].toString());
        ocean.shootAt(5, 5);
        assertEquals("-", ocean.getShipArray()[5][5].toString());
    }

}
