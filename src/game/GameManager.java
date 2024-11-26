package src.game;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import src.cards.Card;
import src.cards.PlayedApple;
import src.network.INetworkManager;
import src.player.Player;

public class GameManager {
	private DeckManager deckManager;
	private IPlayerManager playerManager;
	private IGameRules gameRules;
	private INetworkManager networkManager;
	private ArrayList<PlayedApple> playedApples;

	// Constructor with dependency injection
	public GameManager(DeckManager deckManager, IPlayerManager playerManager, IGameRules gameRules,
			INetworkManager networkManager) throws Exception {
		this.deckManager = deckManager;
		this.playerManager = playerManager;
		this.gameRules = gameRules;
		this.networkManager = networkManager;
		this.playedApples = new ArrayList<>();
	}

	public void setupPlayers(int numberOfOnlinePlayers) throws Exception {
		// Connect online players
		for (int i = 0; i < numberOfOnlinePlayers; i++) {
			Player player = networkManager.acceptConnection(i);
			// Convert Cards to strings for network transmission
			ArrayList<Card> hand = deckManager.dealInitialHand(Constants.INITIAL_HAND_SIZE);
			String handString = hand.stream()
					.map(Card::toString)
					.collect(Collectors.joining(";"));
			player.getOutToClient().writeMessage(handString);
			playerManager.addPlayer(player);
			System.out.println("Connected to Player ID: " + i);
		}

		// Add bots if needed
		for (int i = numberOfOnlinePlayers; i < Constants.MIN_PLAYERS - 1; i++) {
			ArrayList<Card> hand = deckManager.dealInitialHand(Constants.INITIAL_HAND_SIZE);
			playerManager.addPlayer(new Player(i, hand, true));
		}

		// Add server player
		ArrayList<Card> hand = deckManager.dealInitialHand(Constants.INITIAL_HAND_SIZE);
		playerManager.addPlayer(new Player(playerManager.getActivePlayers().size(), hand, false));
	}

	public void startGame() throws Exception {
		boolean finished = false;
		playerManager.initializeJudgeIndex();

		while (!finished) {
			playRound();
			finished = checkWinCondition();
			playerManager.rotateJudge();
		}
	}

	private void playRound() throws Exception {
		Player judge = playerManager.getJudge();

		System.out.println("*****************************************************");
		if (playerManager.getActivePlayers().indexOf(judge) == playerManager.getActivePlayers().size() - 1) {
			System.out.println("**                 NEW ROUND - JUDGE               **");
		} else {
			System.out.println("**                    NEW ROUND                    **");
		}
		System.out.println("*****************************************************");

		// Get green apple card and convert to string for display
		Card greenAppleCard = deckManager.drawGreenApple();
		System.out.println("Green apple: " + greenAppleCard + "\n");

		for (Player player : playerManager.getActivePlayers()) {
			if (player != judge) {
				player.play(playedApples);
			}
		}

		Collections.shuffle(playedApples, ThreadLocalRandom.current());

		System.out.println("\nThe following apples were played:");
		for (int i = 0; i < playedApples.size(); i++) {
			System.out.println("[" + i + "] " + playedApples.get(i).redApple);
		}

		PlayedApple winningApple = judge.judge(playedApples);
		playerManager.getActivePlayers().get(winningApple.playerID).getGreenApples().add(greenAppleCard);

		System.out.println("Player ID " + winningApple.playerID + " won with: " + winningApple.redApple + "\n");

		playedApples.clear();

		dealCards();
	}

	private void dealCards() {
		for (Player player : playerManager.getActivePlayers()) {
			if (player != playerManager.getJudge()) {
				Card redCard = deckManager.drawRedApple();
				player.addCard(redCard);
			}
		}
	}

	private boolean checkWinCondition() {
		return gameRules.isGameOver(playerManager.getActivePlayers());
	}
}