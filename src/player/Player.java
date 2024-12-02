package src.player;

import java.util.*;
import src.cards.Card;
import src.cards.PlayedApple;
import src.network.IClientOutput;
import src.network.PlayerConnection;

/**
 * Represents a player in the game with their cards and game interaction strategy.
 * Uses Strategy pattern to handle different player types (bot, online, offline).
 */
public class Player {
	private int playerID;
	private PlayerConnection connection;
	private ArrayList<Card> hand;
	private ArrayList<Card> greenApples;
	private IPlayerStrategy strategy;

	/**
	 * Constructor for offline and bot players.
	 * Initializes player with starting hand and appropriate strategy.
	 * 
	 * @param playerID Unique identifier for the player
	 * @param initialHand Starting set of cards
	 * @param isBot true for AI player, false for local human player
	 */
	public Player(int playerID, ArrayList<Card> initialHand, boolean isBot) {
		this.playerID = playerID;
		this.hand = new ArrayList<>(initialHand);
		this.greenApples = new ArrayList<>();

		// Select appropriate strategy based on player type
		if (isBot) {
			this.strategy = new BotPlayerStrategy();
		} else {
			this.strategy = new OfflinePlayerStrategy();
		}
		this.strategy.setHand(new ArrayList<>(this.hand));
	}

	/**
	 * Constructor for online players.
	 * Initializes player with network connection and appropriate strategy.
	 * 
	 * @param playerID Unique identifier for the player
	 * @param isBot true for AI player, false for networked human player
	 * @param connection Network connection wrapper
	 */
	public Player(int playerID, boolean isBot, PlayerConnection connection) {
		this.playerID = playerID;
		this.connection = connection;
		this.hand = new ArrayList<>();
		this.greenApples = new ArrayList<>();

		if (isBot) {
			this.strategy = new BotPlayerStrategy();
		} else {
			this.strategy = new OnlinePlayerStrategy(connection);
		}
		this.strategy.setHand(this.hand);
	}

	/**
	 * Delegates card playing to the strategy implementation.
	 * 
	 * @param playedApples Collection of cards played this round
	 */
	public void play(ArrayList<PlayedApple> playedApples) {
		strategy.play(playedApples, playerID);
	}

	/**
	 * Delegates judging to the strategy implementation.
	 * 
	 * @param playedApples Collection of cards to judge
	 * @return The chosen winning card
	 */
	public PlayedApple judge(ArrayList<PlayedApple> playedApples) {
		return strategy.judge(playedApples);
	}

	/**
	 * Adds a card to player's hand and updates strategy.
	 * 
	 * @param card Card to add
	 */
	public void addCard(Card card) {
		hand.add(card); // Keep local hand in sync
		strategy.addCard(card);
	}

	/**
	 * Gets won green apple cards for win condition checking.
	 * 
	 * @return Collection of won green apple cards
	 */
	public ArrayList<Card> getGreenApples() {
		return greenApples;
	}

	/**
	 * Gets network connection interface for online players.
	 * 
	 * @return Network connection wrapper or null if offline
	 */
	public IClientOutput getOutToClient() {
		return connection;
	}

	/**
	* Gets the unique identifier for this player.
	* 
	* @return Player's ID number
	*/
	public int getPlayerID() {
		return playerID;
	}

	/**
	* Gets the current cards in player's hand.
	* 
	* @return Collection of red apple cards in hand
	*/
	public ArrayList<Card> getHand() {
		return hand;
	}

	/**
	* Gets the strategy implementation for this player.
	* Could be bot, online, or offline strategy.
	* 
	* @return Player's behavior strategy
	*/
	public IPlayerStrategy getStrategy() {
		return strategy;
	}
}