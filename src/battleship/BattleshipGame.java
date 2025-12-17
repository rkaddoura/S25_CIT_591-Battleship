package battleship;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class to set up the user interface and facilitate the play of the Battleship game.
 */
public class BattleshipGame {

    /**
     * This method facilities the game. It creates a new ocean, places ships, and applies user input to take a shot.
     * This repeats until all ships are sunk (game end).
     */
    private static void gameLoop() {

        // create a new blank ocean
        Ocean ocean = new Ocean();
        // randomly place ships
        ocean.placeAllShipsRandomly();

        // while the game is running
        while (ocean.isGameOver() == false) {

            //print ocean to the user
            ocean.print();

            System.out.println("Shot number " + (ocean.getShotsFired() + 1) + "!");

            // get user input for shot coordinates
            int[] shotCoords = getShotCoords();
            // set row and column variables based on shotCoords
            int row = shotCoords[0];
            int column = shotCoords[1];

            // shoot at provided coordinates and print the result
            if (ocean.shootAt(row, column) == true) {
                System.out.println("Hit!");
            } else {
                System.out.println("Miss!");
            }

            // get ship we just shot at
            Ship ship = ocean.getShipArray()[row][column];
            // if it is sunk then print what type was sunk
            if (ship.isSunk() == true) {
                System.out.println("You just sunk a " + ship.getShipType() + "!");
            }
        }
        // display final board
        ocean.printWithShips();
        // congratulate player and print how many turns it took (score)
        System.out.println("Nice job, you won in " + ocean.getShotsFired() + " shots!");
    }

    /**
     * Collects user input to determine what coordinates to shoot at.
     * @return int[] representing coordinates with row at index 0 and column at index 1
     */
    private static int[] getShotCoords() {

        // declare row and column variables
        int row;
        int column;

        // create a scanner to get user input
        Scanner scan = new Scanner(System.in);


        while (true) {
            // ask user for row input and store value to row
            System.out.print("Enter row: ");
            String userInput = scan.nextLine();
            try {
                // try casting to int
                row = Integer.parseInt(userInput);
                // if the row is valid (between 0 and 9) break loop
                if (row >= 0 && row <= 9) {
                    break;
                    // otherwise print try again and continue loop
                } else {
                    System.out.println("Invalid Entry: Try again!");
                }
            }catch (NumberFormatException e){
                System.out.println("Invalid Entry: Please enter an integer!");
            }
        }

        while (true) {
            // ask user for column input and store value to row
            System.out.print("Enter column: ");
            String userInput = scan.nextLine();
            try{
                // try casting to int
                column = Integer.parseInt(userInput);
                // if the row is valid (between 0 and 9) break loop
                if (column >= 0 && column <= 9) {
                    break;
                    // otherwise print try again and continue loop
                } else {
                    System.out.println("Invalid Entry! Try again");
                }
            }catch (NumberFormatException e){
                System.out.println("Invalid Entry: Please enter an integer!");
            }
        }

        // store values in shotCoords and return this int[]
        int[] shotCoords = {row, column};
        // row at index 0, column at index 1
        return shotCoords;
    }

    public static void main(String[] args) {

        // print a welcome message and play the game loop
        System.out.println("Welcome to Battleship!");
        gameLoop();

    }

}

