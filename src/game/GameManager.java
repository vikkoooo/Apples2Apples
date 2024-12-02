package src.game;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import src.cards.Card;
import src.cards.PlayedApple;
import src.network.INetworkManager;
import src.player.Player;

/**
 * Manages the core game logic including player setup, round execution, and win conditions.
 * Coordinates between deck management, player management, networking, and game rules.
 */
public class GameManager {
	private DeckManager deckManager;
	private IPlayerManager playerManager;
	private IGameRules gameRules;
	private INetworkManager networkManager;
	private ArrayList<PlayedApple> playedApples;

	/**
	 * Creates a new game manager with all required dependencies.
	 * 
	 * @param deckManager Handles card deck operations
	 * @param playerManager Manages player state
	 * @param gameRules Enforces game rules
	 * @param networkManager Handles online player connections
	 */
	public GameManager(DeckManager deckManager, IPlayerManager playerManager, IGameRules gameRules,
			INetworkManager networkManager) throws Exception {
		this.deckManager = deckManager;
		this.playerManager = playerManager;
		this.gameRules = gameRules;
		this.networkManager = networkManager;
		this.playedApples = new ArrayList<>();
	}

	/**
	 * Sets up the game with a mix of online players and bots.
	 * Connects to online players, creates bots if needed, and deals initial hands.
	 * 
	 * @param numberOfOnlinePlayers Number of human players to connect
	 */
	public void setupPlayers(int numberOfOnlinePlayers) throws Exception {
		// Connect online players and send their initial hands
		for (int i = 0; i < numberOfOnlinePlayers; i++) {
			Player player = networkManager.acceptConnection(i);
			ArrayList<Card> hand = deckManager.dealInitialHand(Constants.INITIAL_HAND_SIZE);
			String handString = hand.stream()
					.map(Card::toString)
					.collect(Collectors.joining(";")); // Convert cards to network format
			player.getOutToClient().writeMessage(handString);
			playerManager.addPlayer(player);
			System.out.println("Connected to Player ID: " + i);
		}

		// Fill remaining slots with bots
		for (int i = numberOfOnlinePlayers; i < Constants.MIN_PLAYERS - 1; i++) {
			ArrayList<Card> hand = deckManager.dealInitialHand(Constants.INITIAL_HAND_SIZE);
			playerManager.addPlayer(new Player(i, hand, true));
		}

		// Add server player
		ArrayList<Card> hand = deckManager.dealInitialHand(Constants.INITIAL_HAND_SIZE);
		playerManager.addPlayer(new Player(playerManager.getActivePlayers().size(), hand, false));
	}

	/**
	 * Main game loop that runs rounds until win condition is met.
	 * Each round rotates the judge role among players.
	 */
	public void startGame() throws Exception {
		boolean finished = false;
		playerManager.initializeJudgeIndex();

		while (!finished) {
			playRound();
			finished = checkWinCondition();
			playerManager.rotateJudge();
		}
	}

	/**
	 * Executes a single round of the game.
	 * Draws a green apple card, collects player submissions, and determines round winner.
	 */
	private void playRound() throws Exception {
		Player judge = playerManager.getJudge();

		// Print round header
		System.out.println("*****************************************************");
		if (playerManager.getActivePlayers().indexOf(judge) == playerManager.getActivePlayers().size() - 1) {
			System.out.println("**                 NEW ROUND - JUDGE               **");
		} else {
			System.out.println("**                    NEW ROUND                    **");
		}
		System.out.println("*****************************************************");

		// Draw and display green apple card
		Card greenAppleCard = deckManager.drawGreenApple();
		System.out.println("Green apple: " + greenAppleCard + "\n");

		// Collect played cards from non-judge players
		for (Player player : playerManager.getActivePlayers()) {
			if (player != judge) {
				player.play(playedApples);
			}
		}

		// Randomize played cards order
		Collections.shuffle(playedApples, ThreadLocalRandom.current());

		// Display played cards
		System.out.println("\nThe following apples were played:");
		for (int i = 0; i < playedApples.size(); i++) {
			System.out.println("[" + i + "] " + playedApples.get(i).redApple);
		}

		// Judge selects winner and award green apple
		PlayedApple winningApple = judge.judge(playedApples);
		playerManager.getActivePlayers().get(winningApple.playerID).getGreenApples().add(greenAppleCard);

		System.out.println("Player ID " + winningApple.playerID + " won with: " + winningApple.redApple + "\n");

		playedApples.clear();

		dealCards();
	}

	/**
	 * Deals new red apple cards to all non-judge players.
	 */
	public void dealCards() {
		for (Player player : playerManager.getActivePlayers()) {
			if (player != playerManager.getJudge()) {
				Card redCard = deckManager.drawRedApple();
				player.addCard(redCard);
			}
		}
	}

	/**
	 * Checks if any player has met the win condition.
	 * 
	 * @return true if game is over, false otherwise
	 */
	private boolean checkWinCondition() {
		return gameRules.isGameOver(playerManager.getActivePlayers());
	}
}