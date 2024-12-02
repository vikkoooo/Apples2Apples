package src.game;

import java.util.ArrayList;
import src.player.Player;

/**
 * Implements game rules including win conditions and scoring thresholds.
 * Winning score scales inversely with player count to maintain game length.
 */
public class GameRules implements IGameRules {
	/**
	 * Checks if any player has reached the winning score based on player count.
	 * 
	 * @param players List of active players in the game
	 * @return true if a player has won, false otherwise
	 */
	@Override
	public boolean isGameOver(ArrayList<Player> players) {
		for (Player player : players) {
			if (player.getGreenApples().size() >= getWinningScore(players.size())) {
				System.out.println("Player ID " + player.getGreenApples().size() + " won the game!");
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines winning score threshold based on number of players.
	 * Fewer green apples needed to win with more players:
	 * - 4 or fewer players: 8 green apples
	 * - 5 players: 7 green apples
	 * - 6 players: 6 green apples
	 * - 7 players: 5 green apples
	 * - 8+ players: 4 green apples
	 *
	 * @param playerCount Number of players in the game
	 * @return Number of green apples needed to win
	 */
	@Override
	public int getWinningScore(int playerCount) {
		if (playerCount <= 4)
			return 8;
		if (playerCount == 5)
			return 7;
		if (playerCount == 6)
			return 6;
		if (playerCount == 7)
			return 5;
		return 4; // 8+ players
	}
}