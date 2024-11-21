package src;

import java.io.*;
import java.util.*;

public class Player {
	private int playerID;
	private boolean isBot;
	private boolean online;
	private PlayerConnection connection;
	private List<Card> hand;
	private List<Card> greenApples;

	public Player(int playerID, List<Card> initialHand, boolean isBot) {
		this.playerID = playerID;
		this.isBot = isBot;
		this.online = false;
		this.hand = new ArrayList<>(initialHand);
		this.greenApples = new ArrayList<>();
	}

	public Player(int playerID, boolean isBot, PlayerConnection connection) {
		this.playerID = playerID;
		this.isBot = isBot;
		this.online = true;
		this.connection = connection;
		this.hand = new ArrayList<>();
		this.greenApples = new ArrayList<>();
	}

	public void play(ArrayList<PlayedApple> playedApples) {
		if (isBot) {
			// introduce synchronized to fix the race condition, causing the original bug
			// synchronized makes sure only one thread can modify the shared resource playedApples at a time
			synchronized (playedApples) {
				playedApples.add(new PlayedApple(playerID, hand.get(0)));
			}
			hand.remove(0);
		} else if (online) {
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
		} else {
			System.out.println("Choose a red apple to play");
			for (int i = 0; i < hand.size(); i++) {
				System.out.println("[" + i + "]   " + hand.get(i).getText());
			}
			System.out.println("");

			int choice = 0;
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String input = br.readLine();
				choice = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("That is not a valid option");
				play(playedApples);
			} catch (Exception e) {
				e.printStackTrace();
			}
			synchronized (playedApples) { // sync access to playedApples
				playedApples.add(new PlayedApple(playerID, hand.get(choice)));
			}
			hand.remove(choice);
			System.out.println("Waiting for other players\n");
		}
	}

	public PlayedApple judge(ArrayList<PlayedApple> playedApples) {
		if (isBot) {
			return playedApples.get(0);
		} else if (online) {
			int playedAppleIndex = 0;
			try {
				playedAppleIndex = Integer.parseInt(connection.getInput().readLine());
			} catch (Exception e) {
			}
			return playedApples.get(playedAppleIndex);
		} else {
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
	}

	public void addCard(Card redApple) {
		if (isBot || !online) {
			hand.add(redApple);
		} else {
			try {
				connection.writeMessage(redApple.toString());
			} catch (Exception e) {
			}
		}
	}

	public List<Card> getGreenApples() {
		return greenApples;
	}

	public ClientOutput getOutToClient() {
		return connection;
	}
}