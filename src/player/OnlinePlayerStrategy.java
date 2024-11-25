package src.player;

import java.util.ArrayList;
import src.cards.Card;
import src.cards.PlayedApple;
import src.cards.RedApple;
import src.network.PlayerConnection;

public class OnlinePlayerStrategy implements IPlayerStrategy {
	@SuppressWarnings("unused") // suppress warning since we actually need hand, but used different for online players
	private ArrayList<Card> hand;
	private PlayerConnection connection;

	public OnlinePlayerStrategy(PlayerConnection connection) {
		this.connection = connection;
	}

	@Override
	public void play(ArrayList<PlayedApple> playedApples, int playerID) {
		try {
			String aPlayedApple = connection.getInput().readLine();
			synchronized (playedApples) { // sync access to playedApples
				// Convert the string back to a Card object
				Card playedCard = new RedApple(aPlayedApple);
				playedApples.add(new PlayedApple(playerID, playedCard));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	@Override
	public PlayedApple judge(ArrayList<PlayedApple> playedApples) {

		int playedAppleIndex = 0;
		try {
			String choice = connection.getInput().readLine();
			playedAppleIndex = Integer.parseInt(choice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return playedApples.get(playedAppleIndex);
	}

	@Override
	public void addCard(Card redApple) {
		try {
			connection.writeMessage(redApple.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}