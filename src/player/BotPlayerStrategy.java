package src.player;

import java.util.ArrayList;
import src.cards.Card;
import src.cards.PlayedApple;

public class BotPlayerStrategy implements IPlayerStrategy {
	private ArrayList<Card> hand;

	@Override
	public void play(ArrayList<PlayedApple> playedApples, int playerID) {
		// introduce synchronized to fix the race condition, causing the original bug
		// synchronized makes sure only one thread can modify the shared resource playedApples at a time
		synchronized (playedApples) {
			playedApples.add(new PlayedApple(playerID, hand.get(0)));
		}
		hand.remove(0);
	}

	@Override
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	@Override
	public PlayedApple judge(ArrayList<PlayedApple> playedApples) {
		return playedApples.get(0);
	}

	@Override
	public void addCard(Card redApple) {
		if (hand == null) {
			hand = new ArrayList<>();
		}
		hand.add(redApple);
	}
}