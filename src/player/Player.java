package src.player;

import java.util.*;
import src.cards.Card;
import src.cards.PlayedApple;
import src.network.IClientOutput;
import src.network.PlayerConnection;

public class Player {
	private int playerID;
	private PlayerConnection connection;
	private ArrayList<Card> hand;
	private ArrayList<Card> greenApples;
	private IPlayerStrategy strategy;

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

	public IClientOutput getOutToClient() {
		return connection;
	}
}