package src;

public class NounCardFactory implements ICardFactory {
	@Override
	public Card createCard(String text) {
		return new RedApple(text);
	}
}