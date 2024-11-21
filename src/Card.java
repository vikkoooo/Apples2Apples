// Card.java
package src;

public abstract class Card {
	private String text;

	public Card(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return text;
	}
}