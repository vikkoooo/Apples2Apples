package src.player;

import java.util.ArrayList;
import src.cards.Card;
import src.cards.PlayedApple;
import src.cards.RedApple;
import src.network.PlayerConnection;

/**
 * Implements network player behavior for remote human players.
 * Handles network communication and thread synchronization.
 */
public class OnlinePlayerStrategy implements IPlayerStrategy {
	@SuppressWarnings("unused")
	private ArrayList<Card> hand; // Unused but required by interface, cards are managed client-side
	private PlayerConnection connection;

	/**
	 * Creates strategy with network connection to remote player.
	 * 
	 * @param connection Socket wrapper for player communication
	 */
	public OnlinePlayerStrategy(PlayerConnection connection) {
		this.connection = connection;
	}

	/**
	 * Receives played card from network client and adds to shared list.
	 * Uses synchronization to prevent race conditions with concurrent
	 * modifications to playedApples list from multiple players.
	 * 
	 * @param playedApples Thread-shared collection of played cards
	 * @param playerID Remote player's unique identifier
	 */
	@Override
	public void play(ArrayList<PlayedApple> playedApples, int playerID) {
		try {
			String aPlayedApple = connection.getInput().readLine();
			synchronized (playedApples) { // Prevent concurrent modification
				// Convert the string back to a Card object
				Card playedCard = new RedApple(aPlayedApple);
				playedApples.add(new PlayedApple(playerID, playedCard));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets hand reference (unused but required by interface).
	 */
	@Override
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	/**
	 * Receives judge's choice over network when player is judge.
	 * 
	 * @param playedApples Collection of cards to judge
	 * @return The chosen PlayedApple based on index received
	 */
	@Override
	public PlayedApple judge(ArrayList<PlayedApple> playedApples) {
		int playedAppleIndex = 0;
		try {
			String choice = connection.getInput().readLine();
			playedAppleIndex = Integer.parseInt(choice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return playedApples.get(playedAppleIndex);
	}

	/**
	 * Sends a new card to the remote player over network.
	 * 
	 * @param redApple Card to add to player's hand
	 */
	@Override
	public void addCard(Card redApple) {
		try {
			connection.writeMessage(redApple.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}