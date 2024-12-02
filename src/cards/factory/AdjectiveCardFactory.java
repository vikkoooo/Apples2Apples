package src.cards.factory;

import src.cards.Card;
import src.cards.GreenApple;

/**
 * Factory class for creating Adjective cards (Green Apples) in the game.
 * Implements the ICardFactory interface to provide a consistent card creation interface.
 */
public class AdjectiveCardFactory implements ICardFactory {
	/**
	 * Creates a new Green Apple (adjective) card with the specified text.
	 * 
	 * @param text The descriptive text for the adjective card
	 * @return A new GreenApple card instance with the given text
	 */
	@Override
	public Card createCard(String text) {
		return new GreenApple(text);
	}
}