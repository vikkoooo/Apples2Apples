// GreenAppleCardFactory.java
package src;

public class GreenAppleCardFactory implements CardFactory {
	@Override
	public Card createCard(String text) {
		return new GreenApple(text);
	}
}