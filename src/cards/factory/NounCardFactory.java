package src.cards.factory;

import src.cards.Card;
import src.cards.RedApple;

public class NounCardFactory implements ICardFactory {
	@Override
	public Card createCard(String text) {
		return new RedApple(text);
	}
}