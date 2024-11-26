package src.game;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import src.cards.Card;
import src.cards.PlayedApple;
import src.network.NetworkManager;
import src.player.Player;

public class GameManager {
	//private ArrayList<Player> players;
	private IPlayerManager playerManager;
	private IDeckLoader deckLoader;
	private IShuffler shuffler;
	private IGameRules gameRules;
	private DeckManager deckManager;
	private NetworkManager networkManager;
	//private int judge;
	private ArrayList<PlayedApple> playedApples;

	public GameManager(int numberOfOnlinePlayers) throws Exception {
		//this.players = new ArrayList<>();
		this.playerManager = new PlayerManager();
		this.deckLoader = new DeckLoader();
		this.shuffler = new Shuffler();
		this.gameRules = new GameRules();
		this.deckManager = new DeckManager(deckLoader, shuffler);
		this.networkManager = new NetworkManager();
		this.playedApples = new ArrayList<>();
		setupPlayers(numberOfOnlinePlayers);
	}

	private void setupPlayers(int numberOfOnlinePlayers) throws Exception {
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
			//players.add(player);
			System.out.println("Connected to Player ID: " + i);
		}

		// Add bots if needed
		for (int i = numberOfOnlinePlayers; i < Constants.MIN_PLAYERS - 1; i++) {
			ArrayList<Card> hand = deckManager.dealInitialHand(Constants.INITIAL_HAND_SIZE);
			//players.add(new Player(i, hand, true));
			playerManager.addPlayer(new Player(i, hand, true));
		}

		// Add server player
		ArrayList<Card> hand = deckManager.dealInitialHand(Constants.INITIAL_HAND_SIZE);
		//players.add(new Player(players.size(), hand, false));
		playerManager.addPlayer(new Player(playerManager.getActivePlayers().size(), hand, false));
	}

	public void startGame() throws Exception {
		boolean finished = false;
		playerManager.initializeJudgeIndex();
		//judge = ThreadLocalRandom.current().nextInt(players.size());

		while (!finished) {
			playRound();
			finished = checkWinCondition();
			//judge = (judge + 1) % playerManager.getActivePlayers().size();
			playerManager.rotateJudge();
		}
	}

	private void playRound() throws Exception {
		Player judge = playerManager.getJudge();

		System.out.println("*****************************************************");
		//if (judge == players.size() - 1) {
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
			//if (player != playerManager.getActivePlayers().get(judge)) {
			if (player != judge) {
				player.play(playedApples);
			}
		}

		Collections.shuffle(playedApples, ThreadLocalRandom.current());

		System.out.println("\nThe following apples were played:");
		for (int i = 0; i < playedApples.size(); i++) {
			System.out.println("[" + i + "] " + playedApples.get(i).redApple);
		}

		//PlayedApple winningApple = playerManager.getActivePlayers().get(judge).judge(playedApples);
		PlayedApple winningApple = judge.judge(playedApples);
		//players.get(winningApple.playerID).getGreenApples().add(greenAppleCard);
		playerManager.getActivePlayers().get(winningApple.playerID).getGreenApples().add(greenAppleCard);

		System.out.println("Player ID " + winningApple.playerID + " won with: " + winningApple.redApple + "\n");

		playedApples.clear();

		//for (Player player : playerManager.getActivePlayers()) {
		//	if (player != playerManager.getActivePlayers().get(judge)) {
		//		Card redCard = deckManager.drawRedApple();
		//		player.addCard(redCard);
		//	}
		//}
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