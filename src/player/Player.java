package src.player;

import java.util.*;
import src.cards.Card;
import src.cards.PlayedApple;
import src.network.IClientOutput;
import src.network.PlayerConnection;

public class Player {
	private int playerID;
	//private boolean isBot;
	//private boolean online;
	private PlayerConnection connection;
	private ArrayList<Card> hand;
	private ArrayList<Card> greenApples;
	private IPlayerStrategy strategy;

	// Old constructor for offline and bots
	/* 
	public Player(int playerID, List<Card> initialHand, boolean isBot) {
		this.playerID = playerID;
		this.isBot = isBot;
		this.online = false;
		this.hand = new ArrayList<>(initialHand);
		this.greenApples = new ArrayList<>();
	}
	*/

	/* 
	public Player(int playerID, boolean isBot, PlayerConnection connection) {
		this.playerID = playerID;
		this.isBot = isBot;
		this.online = true;
		this.connection = connection;
		this.hand = new ArrayList<>();
		this.greenApples = new ArrayList<>();
	}
	*/

	// Constructor for offline and bot players
	public Player(int playerID, ArrayList<Card> initialHand, boolean isBot) {
		this.playerID = playerID;
		this.hand = new ArrayList<>(initialHand);
		this.greenApples = new ArrayList<>();

		// Select appropriate strategy
		if (isBot) {
			this.strategy = new BotPlayerStrategy();
		} else {
			this.strategy = new OfflinePlayerStrategy();
		}
		this.strategy.setHand(new ArrayList<>(this.hand));
	}

	// Constructor for online players
	public Player(int playerID, boolean isBot, PlayerConnection connection) {
		this.playerID = playerID;
		this.connection = connection;
		this.hand = new ArrayList<>();
		this.greenApples = new ArrayList<>();

		if (isBot) {
			this.strategy = new BotPlayerStrategy();
		} else {
			this.strategy = new OnlinePlayerStrategy(connection);
		}
		this.strategy.setHand(this.hand);
	}

	/* 
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
	*/
	public void play(ArrayList<PlayedApple> playedApples) {
		strategy.play(playedApples, playerID);
	}

	public PlayedApple judge(ArrayList<PlayedApple> playedApples) {
		return strategy.judge(playedApples);
	}

	public void addCard(Card card) {
		hand.add(card); // Keep local hand in sync
		strategy.addCard(card);
	}

	// Used by GameManager to manage win condition
	public ArrayList<Card> getGreenApples() {
		return greenApples;
	}

	/**
	 * I AM UNSURE IF THIS IS EVER USED???
	 * 
	* Adds a green apple card to player's won cards collection.
	* Called when this player's red apple is chosen by the judge.
	*/
	/*
	public void addGreenApple(Card greenApple) {
		greenApples.add(greenApple);
	}
		 */

	public IClientOutput getOutToClient() {
		return connection;
	}
}