package src.player;

import java.util.ArrayList;
import src.cards.Card;
import src.cards.PlayedApple;

/**
 * Interface defining the decision-making behavior for players.
 * Used by both human players and AI bots to interact with the game.
 */
public interface IPlayerStrategy {
	/**
	 * Plays a card from the player's hand during their turn.
	 * 
	 * @param playedApples Collection of cards played this round
	 * @param playerID ID of the player making the move
	 */
	void play(ArrayList<PlayedApple> playedApples, int playerID);

	/**
	 * Sets or updates the player's hand of cards.
	 * 
	 * @param hand New collection of cards for the player
	 */
	void setHand(ArrayList<Card> hand);

	/**
	 * Selects a winning card when player is judge.
	 * 
	 * @param playedApples Collection of cards to judge
	 * @return The chosen winning PlayedApple
	 */
	PlayedApple judge(ArrayList<PlayedApple> playedApples);

	/**
	 * Adds a new card to the player's hand.
	 * 
	 * @param redApple Card to add to hand
	 */
	void addCard(Card redApple);
}