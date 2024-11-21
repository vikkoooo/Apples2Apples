package src.cards;

public class RedApple extends Card {
	public RedApple(String text) {
		super(text);
	}

	@Override
	public String toString() {
		return getText();
	}
}