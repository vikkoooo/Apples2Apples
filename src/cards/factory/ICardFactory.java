package src.cards.factory;

import src.cards.Card;

/**
 * Interface defining the card factory contract.
 * Provides a standardized way to create different types of cards in the game.
 */
public interface ICardFactory {
	/**
	 * Creates a new card with the specified text.
	 * 
	 * @param text The text to be displayed on the card
	 * @return A new Card instance of the appropriate type
	 */
	Card createCard(String text);
}