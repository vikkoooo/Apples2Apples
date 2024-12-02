package src.player;

import java.util.ArrayList;
import src.cards.Card;
import src.cards.PlayedApple;

/**
 * Implements AI bot behavior for automated players.
 * Includes thread-safe implementation for concurrent play.
 */
public class BotPlayerStrategy implements IPlayerStrategy {
	private ArrayList<Card> hand;

	/**
	 * Plays a card from the bot's hand.
	 * Uses synchronization to prevent race conditions when multiple bots
	 * try to modify the shared playedApples list simultaneously.
	 * Previously caused bugs when bots attempted concurrent access.
	 * 
	 * @param playedApples Thread-shared collection of played cards
	 * @param playerID Bot's unique identifier
	 */
	@Override
	public synchronized void play(ArrayList<PlayedApple> playedApples, int playerID) {
		synchronized (playedApples) { // Prevent concurrent modification
			playedApples.add(new PlayedApple(playerID, hand.get(0)));
		}
		hand.remove(0);
	}

	/**
	 * Sets the bot's initial or replacement hand.
	 * 
	 * @param hand New collection of cards
	 */
	@Override
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	/**
	 * Simple judging strategy - always picks first card.
	 * 
	 * @param playedApples Collection of cards to judge
	 * @return First played card
	 */
	@Override
	public PlayedApple judge(ArrayList<PlayedApple> playedApples) {
		return playedApples.get(0);
	}

	/**
	 * Adds a new card to bot's hand.
	 * Initializes hand if null.
	 * 
	 * @param redApple Card to add
	 */
	@Override
	public void addCard(Card redApple) {
		if (hand == null) {
			hand = new ArrayList<>();
		}
		hand.add(redApple);
	}
}