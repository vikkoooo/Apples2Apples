package src.player;

import java.util.ArrayList;
import src.cards.Card;
import src.cards.PlayedApple;

public interface IPlayerStrategy {
	void play(ArrayList<PlayedApple> playedApples, int playerID);

	void setHand(ArrayList<Card> hand);

	PlayedApple judge(ArrayList<PlayedApple> playedApples);

	void addCard(Card redApple);
}