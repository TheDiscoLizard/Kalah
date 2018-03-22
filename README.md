
# Kalah
### Introduction
A Java implementation of [Kalah(6,6)](https://en.wikipedia.org/wiki/Kalah), playable by two players on the local machine.
The code itself consists of the Main method which handles the game logic, and makes use of the following methods;
 - printBoard()
	 - For printing the player boards
 - validSelection()
	 - For validating the user's choice.
 - makePlay()
	 - For performing the sowing of seeds.
 - checkWin()
	 - To determine if the game has reached the end-state.
 - changePlayers()
	 - To switch players after a turn ends.
### Playing the Game

    				 -- 6 SEED KALAH --
    				   - Game Start -
    
    ______________________________________________________
    	Turn #1
    ------------------------------------------------------
      P2|
    	=============================
    		6	6	6	6	6	6
    	0	 	 	 	 			0
    		6	6	6	6	6	6
    	=============================
    Pits:  	0	1	2	3	4	5	|P1
    									P1, choose pit: $userInput

The above shows what the program prints when it is executed. The board is presented to the player, who inputs an integer to represent their choice of pit to begin sowing seeds from i.e. to select the first (furthest left pit) Player 1 inputs 0.

In the below example, Player 1 chose pit 5:

    ______________________________________________________
    	Turn #1
    ------------------------------------------------------
      P2|
    	=============================
    		6	6	6	6	6	6
    	0	 	 	 	 			0
    		6	6	6	6	6	6
    	=============================
    Pits:  	0	1	2	3	4	5	|P1
    									P1, choose pit: 5
    ______________________________________________________
    	Turn #2
    ------------------------------------------------------
      P1|
    	=============================
    		0	6	6	6	6	6
    	1	 	 	 	 			0
    		7	7	7	7	7	6
    	=============================
    Pits:  	0	1	2	3	4	5	|P2
    									P2, choose pit: $userInput

Player 2 now makes their choice. The board is flipped to print out from the perspective of player 2, to emulate P1 being sat opposite P2.
### Future Improvements
#### A Web-Based Implementation
Create a web-based implementation to allow two remote players to play.

 - Client
	 - Requests for a game to be set up.
	 - Sends the desired move to the web server, along with information around the playerID (for session maintenance and matchmaking). 
	 - Receives result of submitted move, be it the new state of the board or any move validation error messages.
 - Server
	 - Sets up games between two waiting players.
	 - Verifies moves from each client, ensuring it is their turn before performing the move and returning the new board state to each client.
	 - Checks for game end and win states.
	 - Logs player match results, with a leaderboad for winners ranked by their number-of-turns-to-victory (i.e. top players win in less moves).
