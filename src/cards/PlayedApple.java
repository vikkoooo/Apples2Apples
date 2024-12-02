package src.cards;

/**
 * Represents a Red Apple card that has been played by a player during a round.
 * Links a played card to the player who played it through their ID.
 */
public class PlayedApple {
	/** The ID of the player who played this card */
	public int playerID;

	/** The Red Apple card that was played */
	public Card redApple;

	/**
	 * Creates a new played card entry.
	 * 
	 * @param playerID The ID of the player who played the card
	 * @param redApple The Red Apple card that was played
	 */
	public PlayedApple(int playerID, Card redApple) {
		this.playerID = playerID;
		this.redApple = redApple;
	}
}