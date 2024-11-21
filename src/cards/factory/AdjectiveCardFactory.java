package src.cards.factory;

import src.cards.Card;
import src.cards.GreenApple;

public class AdjectiveCardFactory implements ICardFactory {
	@Override
	public Card createCard(String text) {
		return new GreenApple(text);
	}
}