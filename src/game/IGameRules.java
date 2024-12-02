package src.game;

import src.player.Player;
import java.util.ArrayList;

/**
 * Interface defining game rules and win conditions.
 * Provides methods to check game end state and calculate winning scores.
 */
public interface IGameRules {
	/**
	 * Determines if the game has reached an end state by checking player scores.
	 * 
	 * @param players List of active players to check for win condition
	 * @return true if a player has won, false if game should continue
	 */
	boolean isGameOver(ArrayList<Player> players);

	/**
	 * Calculates how many green apples are needed to win based on player count.
	 * Score threshold typically decreases as player count increases.
	 * 
	 * @param playerCount Number of players in the game
	 * @return Number of green apples needed to win
	 */
	int getWinningScore(int playerCount);
}