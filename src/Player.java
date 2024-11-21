package src;

import java.io.*;
import java.util.*;

public class Player {
	private int playerID;
	private boolean isBot;
	private boolean online;
	private PlayerConnection connection;
	private ArrayList<String> hand;
	private ArrayList<String> greenApples = new ArrayList<>();

	public Player(int playerID, ArrayList<String> hand, boolean isBot) {
		this.playerID = playerID;
		this.hand = hand;
		this.isBot = isBot;
		this.online = false;
	}

	public Player(int playerID, boolean isBot, PlayerConnection connection) {
		this.playerID = playerID;
		this.isBot = isBot;
		this.online = true;
		this.connection = connection;
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
					playedApples.add(new PlayedApple(playerID, aPlayedApple));
				}
			} catch (Exception e) {
			}
		} else {
			System.out.println("Choose a red apple to play");
			for (int i = 0; i < hand.size(); i++) {
				System.out.println("[" + i + "]   " + hand.get(i));
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

	public void addCard(String redApple) {
		if (isBot || !online) {
			hand.add(redApple);
		} else {
			try {
				connection.writeMessage(redApple);
			} catch (Exception e) {
			}
		}
	}

	public ArrayList<String> getGreenApples() {
		return greenApples;
	}

	public ClientOutput getOutToClient() {
		return connection;
	}
}