package com.kalah;

import java.util.*;

public class Main {
    private static int turn = 1;                // Used to track how many turns have been taken
    private static int activePlayer = 1;        // Marks player 1 as the first, and active, player.
    private static int inactivePlayer = 2;      // Marks player 2 as inactive - they will be second to play.
    private static boolean gameOver = false;    // Used to indicate if the game is over.
    private static boolean p1Win = false;       // Used to identify player 1 as the winner.
    private static boolean p2Win = false;       // Used to identify player 2 as the winner.
    private static boolean bonusTurn = false;   // This will be used to track whether the player gets a bonus turn.
    // First part of array is P2 side, second part is P1 side. P2 is reversed when printed (index 6 is the store in each).
    private static int[][] board = new int[][]{
            {4, 4, 4, 4, 4, 4, 0}, //P2 board (will be printed 'backwards')
            {4, 4, 4, 4, 4, 4, 0}  //P1 board
    };

    public static void main(String[] args) {
        // Set up input scanner.
        Scanner input = new Scanner(System.in);
        // Print intro.
        System.out.println("\t\t -- 6 SEED KALAH --");
        System.out.println("\t\t   - Game Start -\n");
        // Declare int to hold the pit selected by the player.
        int selectedPit;
        do { // Do the following at least once, and while !gameOver.
            printBoard(board); // Print out the board.
            System.out.print("\t\t\t\t\t\tP" + activePlayer + ", choose pit: ");  //Prompt the user to pick and index to sow from.
            while (!input.hasNextInt()) { // Here we check to see if the input is an integer, and tell the user off if there isn't.
                System.out.println("Invalid input - enter a digit within the range 0-5!");
                input.next();
            }
            selectedPit = input.nextInt(); // Store the inputted index as the Selected Pit
            if (validSelection(selectedPit)) { // Check if the chosen pit is a valid option to play with
                // Make play
                makePlay(selectedPit);
                // Check for win-state
                if (checkWin()) {
                    System.out.println("We have a winner after "+turn+" turns!");
                    if (board[1][6] > board[0][6]) { // If P1's kalah contains more seeds than P2, P1 wins.
                        p1Win = true;
                        gameOver = true;
                    } else if (board[1][6] < board[0][6]) { // If P2's kalah contains more seeds than P1, P2 wins.
                        p2Win = true;
                        gameOver = true;
                    } else if (board[1][6] == board[0][6]) { // If P1 and P2 have equal number of seeds in respective kalahs, its a draw
                        System.out.println("\tIt's a draw!");
                        gameOver = true;
                    }
                }
                // Time to switch players
                changePlayers();
                turn++;
            } else {
                System.out.println("Invalid input - please select a pit index that is not empty!");
            }

        } while (!gameOver);
        input.close();

        if (p1Win) {
            System.out.println("\t\t\t!!P1 Wins!!");
        } else if (p2Win) {
            System.out.println("\t\t\t!!P2 Wins!!");
        } else {
            System.out.println("\t\t\t!! DRAW !!");
        }
        System.out.println("P1: " + board[1][6] + "\t" + " " + "\t" + " " + "\t" + " " + "\t" + " " + "\t\tP2: " + board[0][6]);
        System.out.println("\t\t   - Game End -");
        System.exit(0);
    }

    private static void changePlayers() {
        if (activePlayer == 1 && !bonusTurn) {
            activePlayer = 2;
            inactivePlayer = 1;
        } else if (activePlayer == 2 && !bonusTurn) {
            activePlayer = 1;
            inactivePlayer = 2;
        }
    }

    private static void printBoard(int[][] board) {
        System.out.println("+ Turn #" + turn + " +");
        System.out.println("  P" + inactivePlayer + "|");
        System.out.println("\t=============================");
        if (activePlayer == 1) {
            System.out.println("\t\t" + board[0][5] + "\t" + board[0][4] + "\t" + board[0][3] + "\t" + board[0][2] + "\t" + board[0][1] + "\t" + board[0][0]);
            System.out.println("\t" + board[0][6] + "\t" + " " + "\t" + " " + "\t" + " " + "\t" + " " + "\t\t\t" + board[1][6]);
            System.out.println("\t\t" + board[1][0] + "\t" + board[1][1] + "\t" + board[1][2] + "\t" + board[1][3] + "\t" + board[1][4] + "\t" + board[1][5]);
        } else if (activePlayer == 2) {
            System.out.println("\t\t" + board[1][5] + "\t" + board[1][4] + "\t" + board[1][3] + "\t" + board[1][2] + "\t" + board[1][1] + "\t" + board[1][0]);
            System.out.println("\t" + board[1][6] + "\t" + " " + "\t" + " " + "\t" + " " + "\t" + " " + "\t\t\t" + board[0][6]);
            System.out.println("\t\t" + board[0][0] + "\t" + board[0][1] + "\t" + board[0][2] + "\t" + board[0][3] + "\t" + board[0][4] + "\t" + board[0][5]);

        }
        System.out.println("\t=============================");
        System.out.println("Pits:  \t0\t1\t2\t3\t4\t5\t|P" + activePlayer);
    }

    private static boolean validSelection(int selectedPit) {
        int playerBoard = 1;
        if (selectedPit < 0 || selectedPit > 5) {
            return false;
        }
        if (activePlayer == 1) {
            playerBoard = 1;
        } else if (activePlayer == 2) {
            playerBoard = 0;
        }
        if/**/ (board[playerBoard][selectedPit] == 0) {
            return false;
        }
        return true;
    }

    private static void makePlay(int selectedPit) {
        bonusTurn = false; // Reset bonus turn flag, to prevent infinite bonus turns
        int playerBoard = 1;
        int oppositeBoard = 1;
        int modCursor = 0;
        int pitToDropInto = 0;
        boolean homeSide = true;
        if (activePlayer == 1) {
            playerBoard = 1;
            oppositeBoard = 0;
        } else if (activePlayer == 2) {
            playerBoard = 0;
            oppositeBoard = 1;
        }
        int cursor = selectedPit;
        int seedsToSow = board[playerBoard][cursor];
        // Take seeds from pit
        board[playerBoard][selectedPit] = 0;
        // For as long as we have seeds to sow (seedsToSow > 0) do...
        do {
            cursor++;

            // Before depositing a seed, we need to make sure we are on a pit, or on a player owned store.
            // The cursor increments for each seed there is, and so we need to wrap it around the two board arrays.
            modCursor = cursor % 14;
            //System.out.println("Player chose index " + selectedPit + " and cursor is set to " + cursor + ". This is modded to " + modCursor);

            if (modCursor <= 6) { // Check to see if the cursor is wrapped to be on the player-side
                //System.out.println("Cursor is on player side " + modCursor);
                pitToDropInto = modCursor;
                homeSide = true;
            } else if (modCursor != 13) { // Else, the cursor is on the opponent-side
                //System.out.println("Cursor is on enemy side " + modCursor);
                pitToDropInto = modCursor % 6;
                homeSide = false;
            } else { // Else, the cursor is on the opponent-side, and in their store
                //System.out.println("Cursor is at enemy store " + modCursor);
                // Since we are in their store, we need to reset cursor to 0.
                cursor = 0;
                // We are then back on the player side.
                homeSide = true;
            }
            if (homeSide) { // If we are on home side,
                board[playerBoard][pitToDropInto]++;
            } else {
                board[oppositeBoard][pitToDropInto]++;
            }
            seedsToSow--;
        } while (seedsToSow > 0);
        // Check if last seed landed in Kalah, earning a bonus turn.
        if (homeSide && cursor % 7 == 6) {
            System.out.println("\tP" + activePlayer + " scored a bonus turn.");
            bonusTurn = true;
            return;
        } else if (homeSide && cursor % 7 != 6) {
            bonusTurn = false;
        }
        // Check if we need to capture any seeds
        if (homeSide && cursor % 7 < 6) {
            int playerPit = cursor % 7;
            int oppositePit = 5 - playerPit;
            if (board[playerBoard][playerPit] == 1 && board[oppositeBoard][oppositePit] > 0) {
                // Last seed was added to an empty pit on home turf, with at least one seed in opposite pit
                // Add the seeds to kalah
                board[playerBoard][6] = board[playerBoard][6] + board[oppositeBoard][oppositePit] + board[playerBoard][playerPit];
                System.out.println("\tP" + activePlayer + " captured " + board[oppositeBoard][oppositePit] + " of P" + inactivePlayer + "'s seed(s) for " + (board[oppositeBoard][oppositePit] + board[playerBoard][playerPit]) + " points!");
                // Remove the seeds from pits
                board[oppositeBoard][oppositePit] = 0;
                board[playerBoard][playerPit] = 0;
            }
        }
    }

    private static boolean checkWin() {
        // When one player no longer has any seeds in any of their pits, the game ends.
        int p1SeedsRemaining = 0;
        int p2SeedsRemaining = 0;
        for (int i = 0; i < 6; i++) {
            // Check each pit for any seeds on both boards
            p1SeedsRemaining = p1SeedsRemaining + (board[1][i]);
            p2SeedsRemaining = p2SeedsRemaining + (board[0][i]);
        }
        // If any board is empty, we will have end game checks
        if (p1SeedsRemaining == 0) {
            for (int i = 0; i < 6; i++) {
                board[0][i] = 0;
            }
            board[0][6] = board[0][6] + p2SeedsRemaining;
            return true;
        } else if (p2SeedsRemaining == 0) {
            for (int i = 0; i < 6; i++) {
                board[1][i] = 0;
            }
            board[1][6] = board[1][6] + p1SeedsRemaining;
            return true;
        }
        return false;
    }
}