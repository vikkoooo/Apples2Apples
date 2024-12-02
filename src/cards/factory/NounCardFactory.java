package src.cards.factory;

import src.cards.Card;
import src.cards.RedApple;

/**
 * Factory class for creating Noun cards (Red Apples) in the game.
 * Implements the ICardFactory interface to provide a consistent card creation interface.
 */
public class NounCardFactory implements ICardFactory {
	/**
	 * Creates a new Red Apple (noun) card with the specified text.
	 * 
	 * @param text The noun text to be displayed on the card
	 * @return A new RedApple card instance with the given text
	 */
	@Override
	public Card createCard(String text) {
		return new RedApple(text);
	}
}