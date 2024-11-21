// RedAppleCardFactory.java
package src;

public class RedAppleCardFactory implements CardFactory {
	@Override
	public Card createCard(String text) {
		return new RedApple(text);
	}
}