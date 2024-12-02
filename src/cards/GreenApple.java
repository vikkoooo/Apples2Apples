package src.cards;

/**
 * Represents a Green Apple (adjective) card in the Apples to Apples game.
 * Extends the base Card class.
 */
public class GreenApple extends Card {
	/**
	 * Creates a new Green Apple card with the specified text.
	 * Uses 'super' to call the parent Card class's constructor,
	 * passing the text parameter up to initialize the base card.
	 * 
	 * @param text The adjective text to be displayed on the card
	 */
	public GreenApple(String text) {
		super(text); // Calls Card(text) constructor from parent class
	}
}