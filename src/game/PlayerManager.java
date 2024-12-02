package src.game;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import src.player.Player;

/**
 * Manages the collection of players and judge rotation mechanics.
 * Implements IPlayerManager interface to provide player management functionality.
 */
public class PlayerManager implements IPlayerManager {
	private ArrayList<Player> players;
	private int currentJudgeIndex;

	/**
	 * Creates a new player manager with empty player list.
	 */
	public PlayerManager() {
		this.players = new ArrayList<Player>();
		this.currentJudgeIndex = 0;
	}

	/**
	 * Adds a new player to the game session.
	 * 
	 * @param player The player to add to the active players list
	 */
	@Override
	public void addPlayer(Player player) {
		players.add(player);
	}

	/**
	 * Randomly selects the first judge from all players.
	 * Uses ThreadLocalRandom for better concurrent performance.
	 */
	@Override
	public void initializeJudgeIndex() {
		currentJudgeIndex = ThreadLocalRandom.current().nextInt(players.size());
	}

	/**
	 * Gets the current judge player for this round.
	 * 
	 * @return The Player object who is currently the judge
	 */
	@Override
	public Player getJudge() {
		return players.get(currentJudgeIndex);
	}

	/**
	 * Moves the judge role to the next player using modulo arithmetic.
	 * Wraps around to the first player after the last player.
	 */
	@Override
	public void rotateJudge() {
		currentJudgeIndex = (currentJudgeIndex + 1) % players.size();
	}

	/**
	 * Gets all players currently in the game.
	 * Returns a defensive copy to prevent external modifications.
	 * 
	 * @return ArrayList of all active Player objects
	 */
	@Override
	public ArrayList<Player> getActivePlayers() {
		return new ArrayList<>(players);
	}
}