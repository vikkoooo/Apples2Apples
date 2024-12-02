package src.cards;

/**
 * Abstract base class representing a card in the Apples to Apples game.
 * Serves as the parent class for both Red Apple (noun) and Green Apple (adjective) cards.
 */
public abstract class Card {
	/** The text displayed on the card */
	private String text;

	/**
	 * Creates a new card with the specified text.
	 * 
	 * @param text The text to be displayed on the card
	 */
	public Card(String text) {
		this.text = text;
	}

	/**
	 * Returns the card's text representation.
	 * 
	 * @return The text content of the card
	 */
	@Override
	public String toString() {
		return text;
	}
}