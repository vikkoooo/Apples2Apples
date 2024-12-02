package src.game;

import java.util.ArrayList;
import src.player.Player;

/**
 * Interface for managing players and judge rotation in the game.
 * Handles adding players, tracking the current judge, and maintaining player state.
 */
public interface IPlayerManager {
	/**
	 * Adds a new player to the game session.
	 * 
	 * @param player The player to add to the active players list
	 */
	void addPlayer(Player player);

	/**
	 * Sets up the initial judge position for the start of the game.
	 * Should be called before the first round begins.
	 */
	void initializeJudgeIndex();

	/**
	 * Gets the current judge player for this round.
	 * 
	 * @return The Player object who is currently the judge
	 */
	Player getJudge();

	/**
	 * Moves the judge role to the next player in sequence.
	 * Should be called at the end of each round.
	 */
	void rotateJudge();

	/**
	 * Gets all players currently in the game.
	 * 
	 * @return ArrayList of all active Player objects
	 */
	ArrayList<Player> getActivePlayers();
}
