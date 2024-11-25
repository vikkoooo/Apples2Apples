package src.cards;

public abstract class Card {
	private String text;

	public Card(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}