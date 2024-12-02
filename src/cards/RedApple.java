package src.cards;

/**
 * Represents a Red Apple (noun) card in the Apples to Apples game.
 * Extends the base Card class.
 */
public class RedApple extends Card {
	/**
	 * Creates a new Red Apple card with the specified text.
	 * Uses 'super' to call the parent Card class's constructor,
	 * passing the text parameter up to initialize the base card.
	 * 
	 * @param text The noun text to be displayed on the card
	 */
	public RedApple(String text) {
		super(text); // Calls Card(text) constructor from parent class
	}
}