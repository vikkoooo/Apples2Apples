package src.cards.factory;

import src.cards.Card;

public interface ICardFactory {
	Card createCard(String text);
}