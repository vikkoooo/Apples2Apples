package src;

public class PlayedApple {
	public int playerID;
	public Card redApple;

	public PlayedApple(int playerID, Card redApple) {
		this.playerID = playerID;
		this.redApple = redApple;
	}

	// For backward compatibility and display purposes
	public String getRedAppleText() {
		return redApple.getText();
	}
}