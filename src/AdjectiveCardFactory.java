package src;

public class AdjectiveCardFactory implements CardFactory {
	@Override
	public Card createCard(String text) {
		return new GreenApple(text);
	}
}