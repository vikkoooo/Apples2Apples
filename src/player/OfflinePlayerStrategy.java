package src.player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import src.cards.Card;
import src.cards.PlayedApple;

public class OfflinePlayerStrategy implements IPlayerStrategy {
	private ArrayList<Card> hand;

	@Override
	public void play(ArrayList<PlayedApple> playedApples, int playerID) {
		System.out.println("Choose a red apple to play");
		for (int i = 0; i < hand.size(); i++) {
			System.out.println("[" + i + "]   " + hand.get(i).toString());
		}
		System.out.println("");

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input = br.readLine();
			int choice = Integer.parseInt(input);

			synchronized (playedApples) {
				playedApples.add(new PlayedApple(playerID, hand.get(choice)));
			}
			hand.remove(choice);
			System.out.println("Waiting for other players\n");
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
		System.out.println("Choose which red apple wins\n");
		int choice = 0;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input = br.readLine();
			choice = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			System.out.println("That is not a valid option");
			judge(playedApples);
		} catch (Exception e) {
		}
		return playedApples.get(choice);
	}

	@Override
	public void addCard(Card redApple) {
		if (hand == null) {
			hand = new ArrayList<>();
		}
		hand.add(redApple);
	}
}