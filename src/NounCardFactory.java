package src;

public class NounCardFactory implements CardFactory {
	@Override
	public Card createCard(String text) {
		return new RedApple(text);
	}
}