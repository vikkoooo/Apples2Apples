package src;

public class AdjectiveCardFactory implements ICardFactory {
	@Override
	public Card createCard(String text) {
		return new GreenApple(text);
	}
}