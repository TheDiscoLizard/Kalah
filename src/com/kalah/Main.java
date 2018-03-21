package com.kalah;

import java.util.*;

public class Main {
    private static int turn = 1;                // Used to indicate which turn the game is on.
    private static int activePlayer = 1;        // Marks player 1 as the first, and active, player.
    private static int inactivePlayer = 2;      // Marks player 2 as inactive - they will be second to play.
    private static boolean gameOver = false;    // Used to indicate if the game is over.
    private static boolean p1Win = false;       // Used to identify player 1 as the winner.
    private static boolean p2Win = false;       // Used to identify player 2 as the winner.
    private static boolean bonusTurn = false;   // This will be used to track whether the player gets a bonus turn.
    // First part of array is P2 side, second part is P1 side. P2 is reversed when printed (index 6 is the store in each).
    private static int[][] board = new int[][]{
            {6, 6, 6, 6, 6, 6, 0},              // P2 board (will be printed 'backwards', but accessed (i.e. board[0][6] = 0))
            {6, 6, 6, 6, 6, 6, 0}               // P1 board (i.e. board[1][5] = 4)
    };

    public static void main(String[] args) {
        // Set up input scanner.
        Scanner input = new Scanner(System.in);
        // Print intro.
        System.out.println("\n\t\t\t\t -- 6 SEED KALAH --\n\t\t\t\t   - Game Start -\n");
        // Declare int to hold the pit selected by the player. We can use this to refer back to the initial choice taken.
        int selectedPit;
        do {    // Do the following at least once, and while !gameOver.
            // Print out the board.
            printBoard(board);
            //Prompt the user to pick and index to sow from.
            System.out.print("\t\t\t\t\t\t\t\t\tP" + activePlayer + ", choose pit: ");
            // Here we check to see if the input is an integer, and tell the user off if there isn't.
            while (!input.hasNextInt()) {
                System.out.println("\t\t\t\t\t\t\t\t\tInvalid input - enter a digit within the range 0-5!");    //Prompt player for valid input.
                input.next();
            }
            // Store the inputted index as the Selected Pit.
            selectedPit = input.nextInt();
            if (validSelection(selectedPit)) { // Check if the chosen pit is a valid option to play with.
                // Make play.
                makePlay(selectedPit);
                // Check for win-state.
                if (checkWin()) {
                    System.out.println("We have a winner after " + turn + " turns!");
                    if (board[1][6] > board[0][6]) { // If P1's kalah (board[1][6]) contains more seeds than P2 (board[0][6]), P1 wins.
                        p1Win = true;
                        gameOver = true; // This breaks us out of the while !gameover loop.
                    } else if (board[1][6] < board[0][6]) { // If P2's kalah contains more seeds than P1, P2 wins.
                        p2Win = true;
                        gameOver = true;
                    } else if (board[1][6] == board[0][6]) { // If P1 and P2 have equal number of seeds in respective kalahs, its a draw
                        System.out.println("\tIt's a draw!");
                        gameOver = true;
                    }
                }
                // Time to switch players.
                changePlayers();
                // Increment the turn counter.
                turn++;

            } else {
                //Prompt player for valid input.
                System.out.println("\t\t\t\t\t\t\t\t\tInvalid input - please select a pit index that is not empty!");
            }
        } while (!gameOver);
        // Game is over, so close off the input scanner.
        input.close();
        // Print message to indicate winner.
        if (p1Win) {
            System.out.println("\t\t\t!!P1 Wins!!");
        } else if (p2Win) {
            System.out.println("\t\t\t!!P2 Wins!!");
        } else {
            System.out.println("\t\t\t!! DRAW !!");
        }
        // Print the final scores.
        System.out.println("P1: " + board[1][6] + "\t" + " " + "\t" + " " + "\t" + " " + "\t" + " " + "\t\tP2: " + board[0][6]);
        System.out.println("\t\t   - Game End -");
        // Exit the program
        System.exit(0);
    } // End Main()

    private static void changePlayers() {
        // This method changes the activePlayer. If the active player has a bonus turn, no change is made.
        if (activePlayer == 1 && !bonusTurn) {  // If P1 is the active player, and there is no bonus turn...
            activePlayer = 2; // Set active player to indicate P2 is up next.
            inactivePlayer = 1; // Set P1 as the inactive player.
        } else if (activePlayer == 2 && !bonusTurn) { // Vice versa
            activePlayer = 1;
            inactivePlayer = 2;
        }
    } // End changePlayers()

    private static void printBoard(int[][] board) {
        // Method is used to print the board.
        System.out.println("______________________________________________________");
        System.out.println("\tTurn #" + turn);
        System.out.println("------------------------------------------------------");
        System.out.println("  P" + inactivePlayer + "|");
        System.out.println("\t=============================");
        // This if statement is used to print the board from the perspective of the current player.
        if (activePlayer == 1) { // P1 is active and taking their turn, so the board is printed with P1's array at the bottom of the output, and their kalah to the right.
            System.out.println("\t\t" + board[0][5] + "\t" + board[0][4] + "\t" + board[0][3] + "\t" + board[0][2] + "\t" + board[0][1] + "\t" + board[0][0]);
            System.out.println("\t" + board[0][6] + "\t" + " " + "\t" + " " + "\t" + " " + "\t" + " " + "\t\t\t" + board[1][6]);
            System.out.println("\t\t" + board[1][0] + "\t" + board[1][1] + "\t" + board[1][2] + "\t" + board[1][3] + "\t" + board[1][4] + "\t" + board[1][5]);
        } else if (activePlayer == 2) { // P2 is active and taking their turn, so the board is printed with P2's array at the bottom of the output, and their kalah to the right.
            System.out.println("\t\t" + board[1][5] + "\t" + board[1][4] + "\t" + board[1][3] + "\t" + board[1][2] + "\t" + board[1][1] + "\t" + board[1][0]);
            System.out.println("\t" + board[1][6] + "\t" + " " + "\t" + " " + "\t" + " " + "\t" + " " + "\t\t\t" + board[0][6]);
            System.out.println("\t\t" + board[0][0] + "\t" + board[0][1] + "\t" + board[0][2] + "\t" + board[0][3] + "\t" + board[0][4] + "\t" + board[0][5]);
        }
        System.out.println("\t=============================");
        System.out.println("Pits:  \t0\t1\t2\t3\t4\t5\t|P" + activePlayer);
    } // End printBoard()

    private static boolean validSelection(int selectedPit) {
        // Method is used to indicate whether the selected pit constitutes a valid move.
        int playerBoard = 1;
        if (selectedPit < 0 || selectedPit > 5) {   // The player's pit choice must be within the range 0-5 (inclusive), otherwise it's invalid.
            return false;
        }
        if (activePlayer == 1) {
            playerBoard = 1;
        } else if (activePlayer == 2) {
            playerBoard = 0;
        }
        if (board[playerBoard][selectedPit] < 1) { // The player's pit choice must contain at least 1 seed, otherwise it's invalid.
            return false;
        }
        return true;
    }

    private static void makePlay(int selectedPit) {
        bonusTurn = false;      // Reset bonus turn flag, to prevent infinite bonus turns
        int playerBoard = 1;    // Used to indicate which array to access, as the player's side of the board.
        int oppositeBoard = 1;  // Used to indicate which array to access, as the opponent's side of the board.
        int modCursor;          // Used to wrap the cursor around, when the cursor goes above the arrays' range of indices.
        int pitToDropInto = 0;  // Used to indicate which pit the next seed will be dropped into.
        boolean onPlayerSide;   // Used to determine if the cursor is moving along the player's side of the board.
        if (activePlayer == 1) {
            playerBoard = 1;
            oppositeBoard = 0;
        } else if (activePlayer == 2) {
            playerBoard = 0;
            oppositeBoard = 1;
        }
        // Set the cursor's starting index.
        int cursor = selectedPit;
        // Collect the number of seeds to sow
        int seedsToSow = board[playerBoard][cursor];
        // Take seeds from pit
        board[playerBoard][selectedPit] = 0;
        // For as long as we have seeds to sow (seedsToSow > 0) do...
        do { // while we have at least one seed to sow (seedsToSow > 0)
            // Move the cursor along
            cursor++;
            // Before depositing a seed, we need to make sure we are on a pit, or on a player owned store.
            // The cursor increments for each seed there is, and so we need to wrap it around the two board arrays.
            modCursor = cursor % 14;    // Mod 14 is used to ensure the cursor is within the range of 2*7 possible array indices.
            if (modCursor <= 6) { // Check to see if the cursor is wrapped to be on the player-side.
                pitToDropInto = modCursor;
                onPlayerSide = true;
            } else if (modCursor != 13) { // Else, the cursor is on the opponent-side, at one of the pits
                pitToDropInto = modCursor % 7; // We need to apply mod 7 to the modCursor so that if it is above 6 it is 'wrapped' to a valid index.
                onPlayerSide = false;
            } else { // Else, the cursor is on the opponent-side, and in their store
                // Since we are in their store, we need to reset cursor to 0.
                cursor = 0;
                // We are then back on the player side, since we must not touch the opponent score pile
                onPlayerSide = true;
            }
            if (onPlayerSide) { // If we are on home side,
                board[playerBoard][pitToDropInto]++; // Increment the seed count into the appropriate pit on the player side.
            } else {
                board[oppositeBoard][pitToDropInto]++; // Increment the seed count into the appropriate pit on the opponent side.
            }
            // Decrement the remaining seedsToSow.
            seedsToSow--;
        } while (seedsToSow > 0);
        // Check if last seed landed in Kalah, earning a bonus turn.
        if (onPlayerSide && cursor % 7 == 6) {
            System.out.println("\tP" + activePlayer + " scored a bonus turn.");
            bonusTurn = true;
            // We can return out of this method now, since a bonus turn needs to be played.
        } else if (onPlayerSide && cursor % 7 < 6) {
            // We are on the player side, and the cursor is not on the Kalah. We need to see if the player captured any points.
            int playerPit = cursor % 7;
            int oppositePit = 5 - playerPit;
            if (board[playerBoard][playerPit] == 1 && board[oppositeBoard][oppositePit] > 0) { // If last seed was added to an empty pit on home turf, with at least one seed in opposite pit
                // Add the seeds to kalah
                board[playerBoard][6] = board[playerBoard][6] + board[oppositeBoard][oppositePit] + board[playerBoard][playerPit];
                System.out.println("\tP" + activePlayer + " captured " + board[oppositeBoard][oppositePit] + " of P" + inactivePlayer + "'s seed(s) for " + (board[oppositeBoard][oppositePit] + board[playerBoard][playerPit]) + " points!");
                // Remove the seeds from pits
                board[oppositeBoard][oppositePit] = 0;
                board[playerBoard][playerPit] = 0;
            } // End If (board[playerBoard][playerPit] == 1 && board[oppositeBoard][oppositePit] > 0
        } // End Else If (onPlayerSide && cursor % 7 < 6)
    } // End makePlay()

    private static boolean checkWin() {
        // When one player no longer has any seeds in any of their pits, the game ends.
        // This method returns a true or false value.
        int p1SeedsRemaining = 0;
        int p2SeedsRemaining = 0;
        for (int i = 0; i < 6; i++) {
            // Cycle through pits on each board-side, counting up remaining seeds.
            p1SeedsRemaining = p1SeedsRemaining + (board[1][i]);
            p2SeedsRemaining = p2SeedsRemaining + (board[0][i]);
        }
        // If any board is empty, we will have end game checks. We check to see if the p*SeedsRemaining values == 0.
        if (p1SeedsRemaining == 0) {
            // P1 has no seeds to play, so we iterate through P2s pits and set the seeds to 0
            for (int i = 0; i < 6; i++) {
                board[0][i] = 0;
            }
            // We then add P2s remaining seeds to their kalah.
            board[0][6] = board[0][6] + p2SeedsRemaining;
            return true;
        } else if (p2SeedsRemaining == 0) {
            // P2 has no seeds to play, so we iterate through P1s pits and set the seeds to 0
            for (int i = 0; i < 6; i++) {
                board[1][i] = 0;
            }
            // We then add P1s remaining seeds to their kalah.
            board[1][6] = board[1][6] + p1SeedsRemaining;
            return true;
        }
        return false;
    } // End checkWin()
} // End Main Class
