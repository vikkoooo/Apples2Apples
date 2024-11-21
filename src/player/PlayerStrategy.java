package src.player;

import src.cards.Card;

public interface PlayerStrategy {
	void playCard(Card card);

	void receiveCard(Card card);

	void sendMessage(String message);
}